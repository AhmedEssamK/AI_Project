package code;

public class State {
    int prosperity;
    // soli
    int hFunctionOne;
    int hFunctionTwo;

    int food;
    int materials;
    int energy;

    int money_spent;

    boolean isVisualize;

    Amount amounts;

    int currentDelay = 0;
    boolean isFoodRequested = false;
    boolean isMaterialsRequested = false;
    boolean isEnergyRequested = false;
    int turns = 0;

    int cost;
    int nodesExpanded;
    String plan;

    public State(String initialState, boolean isVisualize) {
        this.money_spent = 0;
        this.cost = 0;
        this.nodesExpanded = 0;
        this.plan = "";
        this.isVisualize = isVisualize;
        this.Start(initialState);
        this.amounts = new Amount(initialState);
    }

    public State(State oldState) {
        this.prosperity = oldState.prosperity;
        this.food = oldState.food;
        this.materials = oldState.materials;
        this.energy = oldState.energy;
        this.money_spent = oldState.money_spent;
        this.isVisualize = oldState.isVisualize;
        this.amounts = oldState.amounts;
        this.currentDelay = oldState.currentDelay;
        this.isFoodRequested = oldState.isFoodRequested;
        this.isMaterialsRequested = oldState.isMaterialsRequested;
        this.isEnergyRequested = oldState.isEnergyRequested;
        this.turns = oldState.turns;
        this.nodesExpanded = oldState.nodesExpanded;
        this.plan = oldState.plan;
        this.cost = 0;
    }

    private void Start(String initialState) {
        String[] attributes = initialState.split(";");
        this.prosperity = Integer.parseInt(attributes[0]);

        String[] resourcesAttributes = attributes[1].split(",");
        this.food = Integer.parseInt(resourcesAttributes[0]);
        this.materials = Integer.parseInt(resourcesAttributes[1]);
        this.energy = Integer.parseInt(resourcesAttributes[2]);

        if (isVisualize) {
            System.out.println("Step count: " + this.turns);
            System.out.println("Prosperity: " + this.prosperity);
            System.out.println("Food: " + this.food);
            System.out.println("Mats: " + this.materials);
            System.out.println("Energy: " + this.energy);
            System.out.println("Money spent: " + this.money_spent);
            System.out.println();
        }
    }

    private void DeductResources() {
        this.food--;
        this.materials--;
        this.energy--;

        int resourcesCost = this.amounts.unitPriceFood + this.amounts.unitPriceMaterials + this.amounts.unitPriceEnergy;
        this.money_spent += resourcesCost;
        this.cost += resourcesCost;
    }

    public void RequestFood() {
        this.currentDelay = this.amounts.delayRequestFood;

        this.DeductResources();

        // int priceCost = this.amounts.unitPriceFood * this.amounts.amountRequestFood;
        // this.money_spent += priceCost;
        // this.cost += priceCost;

        if (this.plan.length() < 1) {
            plan += "RequestFood";
        } else {
            plan += ",RequestFood";
        }

        this.nodesExpanded++;

        this.isFoodRequested = true;
        this.turns++;

        this.CalculateHeuristicOne();
        this.CalculateHeuristicTwo();
    }

    public void RequestMaterials() {
        this.currentDelay = this.amounts.delayRequestMaterials;

        this.DeductResources();

        // int priceCost = this.amounts.unitPriceMaterials *
        // this.amounts.amountRequestMaterials;
        // this.money_spent += priceCost;
        // this.cost += priceCost;

        if (this.plan.length() < 1) {
            plan += "RequestMaterials";
        } else {
            plan += ",RequestMaterials";
        }

        this.nodesExpanded++;

        this.isMaterialsRequested = true;
        this.turns++;

        this.CalculateHeuristicOne();
        this.CalculateHeuristicTwo();
    }

    public void RequestEnergy() {
        this.currentDelay = this.amounts.delayRequestEnergy;

        this.DeductResources();

        // int priceCost = this.amounts.unitPriceEnergy *
        // this.amounts.amountRequestEnergy;
        // this.money_spent += priceCost;
        // this.cost += priceCost;

        if (this.plan.length() < 1) {
            plan += "RequestEnergy";
        } else {
            plan += ",RequestEnergy";
        }

        this.nodesExpanded++;

        this.isEnergyRequested = true;
        this.turns++;

        this.CalculateHeuristicOne();
        this.CalculateHeuristicTwo();
    }

    public void Wait() {
        this.turns++;

        this.DeductResources();

        if (this.plan.length() < 1) {
            plan += "WAIT";
        } else {
            plan += ",WAIT";
        }

        this.nodesExpanded++;

        if (this.currentDelay > 0) {
            this.currentDelay--;
        }
        this.HandleTurns();

        this.CalculateHeuristicOne();
        this.CalculateHeuristicTwo();
    }

    public void Build1() {
        this.turns++;
        if (this.currentDelay > 0) {
            this.currentDelay--;
        }
        this.prosperity += this.amounts.prosperityBUILD1;
        // Function calculation
        // this.hFunction = (100 - this.prosperity) / 100;

        int priceResources = (this.amounts.unitPriceFood * this.amounts.foodUseBUILD1) +
                (this.amounts.unitPriceMaterials * this.amounts.materialsUseBUILD1)
                + (this.amounts.unitPriceEnergy * this.amounts.energyUseBUILD1);
        this.money_spent += this.amounts.priceBUILD1 + priceResources;
        this.cost += this.amounts.priceBUILD1 + priceResources;

        this.food -= this.amounts.foodUseBUILD1;
        this.materials -= this.amounts.materialsUseBUILD1;
        this.energy -= this.amounts.energyUseBUILD1;

        if (this.plan.length() < 1) {
            plan += "BUILD1";
        } else {
            plan += ",BUILD1";
        }

        this.nodesExpanded++;

        this.HandleTurns();

        this.CalculateHeuristicOne();
        this.CalculateHeuristicTwo();
    }

    public void Build2() {
        this.turns++;
        if (this.currentDelay > 0) {
            this.currentDelay--;
        }
        this.prosperity += this.amounts.prosperityBUILD2;
        // Function calculation
        // this.hFunction = (100 - this.prosperity) / 100;

        int priceResources = (this.amounts.unitPriceFood * this.amounts.foodUseBUILD2) +
                (this.amounts.unitPriceMaterials * this.amounts.materialsUseBUILD2)
                + (this.amounts.unitPriceEnergy * this.amounts.energyUseBUILD2);
        this.money_spent += this.amounts.priceBUILD2 + priceResources;
        this.cost += this.amounts.priceBUILD2 + priceResources;

        this.food -= this.amounts.foodUseBUILD2;
        this.materials -= this.amounts.materialsUseBUILD2;
        this.energy -= this.amounts.energyUseBUILD2;

        if (this.plan.length() < 1) {
            plan += "BUILD2";
        } else {
            plan += ",BUILD2";
        }

        this.nodesExpanded++;

        this.HandleTurns();

        this.CalculateHeuristicOne();
        this.CalculateHeuristicTwo();
    }

    private void HandleTurns() {
        if (this.currentDelay > 0) {
            return;
        }
        if (this.isFoodRequested) {
            this.isFoodRequested = false;
            this.food += this.amounts.amountRequestFood;
            if (this.food > 50) {
                this.food = 50;
            }
        } else if (this.isMaterialsRequested) {
            this.isMaterialsRequested = false;
            this.materials += this.amounts.amountRequestMaterials;
            if (this.materials > 50) {
                this.materials = 50;
            }
        } else if (this.isEnergyRequested) {
            this.isEnergyRequested = false;
            this.energy += this.amounts.amountRequestEnergy;
            if (this.energy > 50) {
                this.energy = 50;
            }
        }
    }

    private void CalculateHeuristicOne() {
        if (this.prosperity >= 100) {
            this.hFunctionOne = 0;
            return;
        }
        int prosperityLeft = 100 - this.prosperity;
        int maxIncreaseProperity = Math.max(this.amounts.prosperityBUILD1, this.amounts.prosperityBUILD2);

        int numberOfBuilds = prosperityLeft / maxIncreaseProperity;

        int costBuildOne = this.amounts.priceBUILD1 + (this.amounts.unitPriceFood * this.amounts.foodUseBUILD1) +
                (this.amounts.unitPriceMaterials * this.amounts.materialsUseBUILD1)
                + (this.amounts.unitPriceEnergy * this.amounts.energyUseBUILD1);

        int costBuildTwo = this.amounts.priceBUILD2 + (this.amounts.unitPriceFood * this.amounts.foodUseBUILD2) +
                (this.amounts.unitPriceMaterials * this.amounts.materialsUseBUILD2)
                + (this.amounts.unitPriceEnergy * this.amounts.energyUseBUILD2);

        this.hFunctionOne = numberOfBuilds * Math.min(costBuildOne, costBuildTwo);
    }

    private void CalculateHeuristicTwo() {
        if (this.prosperity >= 100) {
            this.hFunctionTwo = 0;
        }
        int prosperityLeft = 100 - this.prosperity;
        int maxIncreaseProperity = Math.max(this.amounts.prosperityBUILD1, this.amounts.prosperityBUILD2);

        int numberOfBuilds = prosperityLeft / maxIncreaseProperity;

        int materialsNeededOne = numberOfBuilds * this.amounts.materialsUseBUILD1;
        int materialsNeededTwo = numberOfBuilds * this.amounts.materialsUseBUILD2;

        int materialsNeeded = Math.min(materialsNeededOne, materialsNeededTwo);

        int requestsNeeded = materialsNeeded / this.amounts.amountRequestMaterials;
        this.hFunctionTwo = requestsNeeded * this.amounts.unitPriceMaterials;
    }

    public String getStateHash() {
        String hash = "" + this.food + this.materials + this.energy + this.prosperity + this.currentDelay +
                this.isFoodRequested + this.isMaterialsRequested + this.isEnergyRequested;

        return hash;
    }
}

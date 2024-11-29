package code;

public class Amount {
    int unitPriceFood;
    int unitPriceMaterials;
    int unitPriceEnergy;

    int amountRequestFood;
    int delayRequestFood;

    int amountRequestMaterials;
    int delayRequestMaterials;

    int amountRequestEnergy;
    int delayRequestEnergy;

    int priceBUILD1;
    int foodUseBUILD1;
    int materialsUseBUILD1;
    int energyUseBUILD1;
    int prosperityBUILD1;

    int priceBUILD2;
    int foodUseBUILD2;
    int materialsUseBUILD2;
    int energyUseBUILD2;
    int prosperityBUILD2;

    public Amount(String initialState) {
        start(initialState);
    }

    private void start(String initialState) {
        String[] attributes = initialState.split(";");
        String[] pricesResources = attributes[2].split(",");
        this.unitPriceFood = Integer.parseInt(pricesResources[0]);
        this.unitPriceMaterials = Integer.parseInt(pricesResources[1]);
        this.unitPriceEnergy = Integer.parseInt(pricesResources[2]);

        String[] foodAmounts = attributes[3].split(",");
        this.amountRequestFood = Integer.parseInt(foodAmounts[0]);
        this.delayRequestFood = Integer.parseInt(foodAmounts[1]);

        String[] materialsAmounts = attributes[4].split(",");
        this.amountRequestMaterials = Integer.parseInt(materialsAmounts[0]);
        this.delayRequestMaterials = Integer.parseInt(materialsAmounts[1]);

        String[] energyAmounts = attributes[5].split(",");
        this.amountRequestEnergy = Integer.parseInt(energyAmounts[0]);
        this.delayRequestEnergy = Integer.parseInt(energyAmounts[1]);

        String[] buildOnes = attributes[6].split(",");
        this.priceBUILD1 = Integer.parseInt(buildOnes[0]);
        this.foodUseBUILD1 = Integer.parseInt(buildOnes[1]);
        this.materialsUseBUILD1 = Integer.parseInt(buildOnes[2]);
        this.energyUseBUILD1 = Integer.parseInt(buildOnes[3]);
        this.prosperityBUILD1 = Integer.parseInt(buildOnes[4]);

        String[] buildTwos = attributes[7].split(",");
        this.priceBUILD2 = Integer.parseInt(buildTwos[0]);
        this.foodUseBUILD2 = Integer.parseInt(buildTwos[1]);
        this.materialsUseBUILD2 = Integer.parseInt(buildTwos[2]);
        this.energyUseBUILD2 = Integer.parseInt(buildTwos[3]);
        this.prosperityBUILD2 = Integer.parseInt(buildTwos[4]);
    }
}

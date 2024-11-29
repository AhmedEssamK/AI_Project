package code;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class LLAPSearch extends GenericSearch {
    private static long initialMemory;
    private static long initialTime;

    private static int nodesExpanded2 = 0;

    public static String solve(String initialState, String strategy, boolean visualize) {
        finalResult = "";
        hashSet = new HashSet<String>();
        hasChildren = false;
        finalResult = GeneralSearch(initialState, strategy);
        return finalResult;
    }

    public static void BreadthFirstQueuingFunction(ArrayList<Node> queue, Node node) {
        ArrayList<Node> children = CreateChildren(node);
        while (!children.isEmpty()) {
            Node newNode = children.removeFirst();
            String hash = newNode.state.getStateHash();
            if (!hashSet.contains(hash)) {
                hashSet.add(hash);
                queue.addLast(newNode);
            }
        }
    }

    public static void DepthFirstQueuingFunction(ArrayList<Node> queue, Node node) {
        ArrayList<Node> children = CreateChildren(node);
        while (!children.isEmpty()) {
            Node newNode = children.removeLast();
            String hash = newNode.state.getStateHash();
            if (!hashSet.contains(hash)) {
                hashSet.add(hash);
                queue.addFirst(newNode);
            }
        }
    }

    public static void IterativeDeepeningQueuingFunction(ArrayList<Node> queue, Node node) {
        ArrayList<Node> children = CreateChildren(node);
        if (node.level >= maxLevel) {
            if (!hasChildren) {
                hasChildren |= !children.isEmpty();
            }
            if (queue.isEmpty() && hasChildren) {
                queue.addFirst(problemRootNode);
                maxLevel += 2;
                hasChildren = false;
                hashSet.clear();
            }
            return;
        }
        while (!children.isEmpty()) {
            Node newNode = children.removeLast();
            String hash = newNode.state.getStateHash();
            if (!hashSet.contains(hash)) {
                hashSet.add(hash);
                queue.addFirst(newNode);
            }
        }
        if (queue.isEmpty() && hasChildren) {
            queue.addFirst(problemRootNode);
            maxLevel += 2;
            hasChildren = false;
            hashSet.clear();
        }
    }

    public static void UniformCostQueuingFunction(PriorityQueue<Node> pQueue, Node node) {
        ArrayList<Node> children = CreateChildren(node);

        for (Node child : children) {
            String stateHash = child.state.getStateHash();

            if (!hashSet.contains(stateHash)) {
                hashSet.add(stateHash);
                pQueue.add(child);
            }
        }
    }

    public static void GreedySearchQueuingFunction(PriorityQueue<Node> pQueue, Node node) {
        ArrayList<Node> children = CreateChildren(node);

        for (Node child : children) {
            String stateHash = child.state.getStateHash();

            if (!hashSet.contains(stateHash)) {
                hashSet.add(stateHash);
                pQueue.add(child);
            }
        }
    }

    public static void GreedySearchQueuingFunctionTwo(PriorityQueue<Node> pQueue, Node node) {
        ArrayList<Node> children = CreateChildren(node);

        for (Node child : children) {
            String stateHash = child.state.getStateHash();

            if (!hashSet.contains(stateHash)) {
                hashSet.add(stateHash);
                pQueue.add(child);
            }
        }
    }

    public static void AStarSearchQueuingFunction(PriorityQueue<Node> pQueue, Node node) {
        ArrayList<Node> children = CreateChildren(node);

        for (Node child : children) {
            String stateHash = child.state.getStateHash();

            if (!hashSet.contains(stateHash)) {
                hashSet.add(stateHash);
                pQueue.add(child);
            }
        }
    }

    public static void AStarSearchQueuingFunctionTwo(PriorityQueue<Node> pQueue, Node node) {
        ArrayList<Node> children = CreateChildren(node);

        for (Node child : children) {
            String stateHash = child.state.getStateHash();

            if (!hashSet.contains(stateHash)) {
                hashSet.add(stateHash);
                pQueue.add(child);
            }
        }
    }

    private static ArrayList<Node> CreateChildren(Node root) {
        ArrayList<Node> children = new ArrayList<Node>();
        State currentState = root.state;
        int currentMoney = 100000 - currentState.money_spent;
        if ((currentMoney < currentState.amounts.priceBUILD1 && currentMoney < currentState.amounts.priceBUILD2) ||
                currentState.food < 1 || currentState.materials < 1 || currentState.energy < 1) {
            return children;
        }
        if (!(currentState.isFoodRequested || currentState.isMaterialsRequested || currentState.isEnergyRequested) &&
                (currentMoney >= currentState.amounts.unitPriceFood + currentState.amounts.unitPriceMaterials
                        + currentState.amounts.unitPriceEnergy)) {
            Node requestFood = new Node(currentState, false, root.level + 1);
            requestFood.state.RequestFood();
            root.children.add(requestFood);
            children.add(requestFood);

            Node requestMaterials = new Node(currentState, false, root.level + 1);
            requestMaterials.state.RequestMaterials();
            root.children.add(requestMaterials);
            children.add(requestMaterials);

            Node requestEnergy = new Node(currentState, false, root.level + 1);
            requestEnergy.state.RequestEnergy();
            root.children.add(requestEnergy);
            children.add(requestEnergy);
        } else if (currentMoney >= currentState.amounts.unitPriceFood + currentState.amounts.unitPriceMaterials
                + currentState.amounts.unitPriceEnergy) {
            Node wait = new Node(currentState, false, root.level + 1);
            wait.state.Wait();
            root.children.add(wait);
            children.add(wait);
        }
        boolean canBuild1 = true;
        canBuild1 &= currentMoney >= currentState.amounts.priceBUILD1
                + (currentState.amounts.foodUseBUILD1 * currentState.amounts.unitPriceFood)
                + (currentState.amounts.materialsUseBUILD1 * currentState.amounts.unitPriceMaterials)
                + (currentState.amounts.energyUseBUILD1 * currentState.amounts.unitPriceEnergy);

        canBuild1 &= currentState.food >= currentState.amounts.foodUseBUILD1;
        canBuild1 &= currentState.materials >= currentState.amounts.materialsUseBUILD1;
        canBuild1 &= currentState.energy >= currentState.amounts.energyUseBUILD1;

        if (canBuild1) {
            Node build = new Node(currentState, false, root.level + 1);
            build.state.Build1();
            root.children.add(build);
            children.add(build);
        }

        boolean canBuild2 = true;
        canBuild2 &= currentMoney >= currentState.amounts.priceBUILD2
                + (currentState.amounts.foodUseBUILD2 * currentState.amounts.unitPriceFood)
                + (currentState.amounts.materialsUseBUILD2 * currentState.amounts.unitPriceMaterials)
                + (currentState.amounts.energyUseBUILD2 * currentState.amounts.unitPriceEnergy);

        canBuild2 &= currentState.food >= currentState.amounts.foodUseBUILD2;
        canBuild2 &= currentState.materials >= currentState.amounts.materialsUseBUILD2;
        canBuild2 &= currentState.energy >= currentState.amounts.energyUseBUILD2;

        if (canBuild2) {
            Node build = new Node(currentState, false, root.level + 1);
            build.state.Build2();
            root.children.add(build);
            children.add(build);
        }
        return children;
    }

    private static void performAlgorithm() {
        String initialState0 = "17;" +
                "49,30,46;" +
                "7,57,6;" +
                "7,1;20,2;29,2;" +
                "350,10,9,8,28;" +
                "408,8,12,13,34;";
        String initialState1 = "50;" +
                "12,12,12;" +
                "50,60,70;" +
                "30,2;19,2;15,2;" +
                "300,5,7,3,20;" +
                "500,8,6,3,40;";
        String initialState2 = "30;" +
                "30,25,19;" +
                "90,120,150;" +
                "9,2;13,1;11,1;" +
                "3195,11,12,10,34;" +
                "691,7,8,6,15;";
        String initialState3 = "0;" +
                "19,35,40;" +
                "27,84,200;" +
                "15,2;37,1;19,2;" +
                "569,11,20,3,50;" +
                "115,5,8,21,38;";

        String initialState4 = "21;" +
                "15,19,13;" +
                "50,50,50;" +
                "12,2;16,2;9,2;" +
                "3076,15,26,28,40;" +
                "5015,25,15,15,38;";
        String initialState5 = "72;" +
                "36,13,35;" +
                "75,96,62;" +
                "20,2;5,2;33,2;" +
                "30013,7,6,3,36;" +
                "40050,5,10,14,44;";
        String initialState6 = "29;" +
                "14,9,26;" +
                "650,400,710;" +
                "20,2;29,2;38,1;" +
                "8255,8,7,9,36;" +
                "30670,12,12,11,36;";
        String initialState7 = "1;" +
                "6,10,7;" +
                "2,1,66;" +
                "34,2;22,1;14,2;" +
                "1500,5,9,9,26;" +
                "168,13,13,14,46;";
        String initialState8 = "93;" +
                "46,42,46;" +
                "5,32,24;" +
                "13,2;24,1;20,1;" +
                "155,7,5,10,7;" +
                "5,5,5,4,4;";
        String initialState9 = "50;" +
                "20,16,11;" +
                "76,14,14;" +
                "7,1;7,1;7,1;" +
                "359,14,25,23,39;" +
                "524,18,17,17,38;";
        String initialState10 = "32;" +
                "20,16,11;" +
                "76,14,14;" +
                "9,1;9,2;9,1;" +
                "358,14,25,23,39;" +
                "5024,20,17,17,38;";
        String x0 = solve(initialState0, "GR1", false);
        String x1 = solve(initialState1, "GR1", false);
        String x2 = solve(initialState2, "GR1", false);
        String x3 = solve(initialState3, "GR1", false);
        String x4 = solve(initialState4, "GR1", false);
        String x5 = solve(initialState5, "GR1", false);
        String x6 = solve(initialState6, "GR1", false);
        String x7 = solve(initialState7, "GR1", false);
        String x8 = solve(initialState8, "GR1", false);
        String x9 = solve(initialState9, "GR1", false);
        String x10 = solve(initialState10, "GR1", false);
        alllNodesExpanded(x0);alllNodesExpanded(x1);alllNodesExpanded(x2);alllNodesExpanded(x3);
        alllNodesExpanded(x4);alllNodesExpanded(x5);alllNodesExpanded(x6);alllNodesExpanded(x7);
        alllNodesExpanded(x8);alllNodesExpanded(x9);alllNodesExpanded(x10);
    }

    private static void alllNodesExpanded(String x) {
        String[] y = x.split(";");
        nodesExpanded2 += Integer.parseInt(y[2]);
    }

    private static double calculateCpuUtilization(long startTime, long endTime) {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        // Note: The getSystemLoadAverage() method is used here for illustration.
        // For more accurate process-level CPU usage, platform-specific libraries may be required.
        double systemLoadAverage = osBean.getSystemLoadAverage();

        // For illustration purposes, assuming systemLoadAverage returns a value between 0 and 1.
        return systemLoadAverage * 100;
    }

    public static void main(String[] args) {
        // Record initial memory and time
        initialMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        initialTime = System.currentTimeMillis();

        // Your algorithm or code here
        performAlgorithm();

        // Calculate final memory and time
        long finalMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long finalTime = System.currentTimeMillis();

        // Print results
        System.out.println("Initial Memory: " + initialMemory / (1024 * 1024) + " MB");
        System.out.println("Final Memory: " + finalMemory / (1024 * 1024) + " MB");
        System.out.println("Memory Used: " + (finalMemory - initialMemory) / (1024 * 1024) + " MB");

        System.out.println("CPU Utilization: " + calculateCpuUtilization(initialTime, finalTime) + "%");

        System.out.println("Nodes Expanded: " + nodesExpanded2);
    }
}
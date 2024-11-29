package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class GenericSearch {
    static String finalResult;
    static HashSet<String> hashSet;
    static boolean hasChildren;
    static Node problemRootNode;
    static int nodesExpanded;
    static int maxLevel;

    public static String GeneralSearch(String problem, String queuingFunction) {
        ArrayList<Node> queue = new ArrayList<Node>();
        PriorityQueue<Node> pQueue;
        switch (queuingFunction) {
            case "UC":
                pQueue = new PriorityQueue<Node>(Comparator.comparingInt(node -> node.state.cost));break;
            case "GR1":
                pQueue = new PriorityQueue<Node>(Comparator.comparingInt(node -> node.state.hFunctionOne));break;
            case "GR2":
                pQueue = new PriorityQueue<Node>(Comparator.comparingInt(node -> node.state.hFunctionTwo));break;
            case "AS1":
                pQueue = new PriorityQueue<Node>(Comparator.comparingInt(node -> node.state.hFunctionOne + node.state.money_spent));break;
            case "AS2":
                pQueue = new PriorityQueue<Node>(Comparator.comparingInt(node -> node.state.hFunctionTwo + node.state.money_spent));break;
            default:
                pQueue = new PriorityQueue<Node>();break;
        }
        nodesExpanded = 0;
        maxLevel = 0;
        State state = new State(problem, false);
        Node root = new Node(state, true, 0);
        problemRootNode = root;
        if(queuingFunction.equals("ID") || queuingFunction.equals("BF") || queuingFunction.equals("DF")) {
            queue.add(root);
        }
        else {
            pQueue.add(root);
        }
        String hash = root.state.getStateHash();
        hashSet.add(hash);
        while (true) {
            if (queue.isEmpty() && pQueue.isEmpty()) {
                return "NOSOLUTION";
            }
            Node node;
            if(queuingFunction.equals("ID") || queuingFunction.equals("BF") || queuingFunction.equals("DF")) {
                node = queue.removeFirst();
            }
            else {
                node = pQueue.remove();
            }
            nodesExpanded++;
            if (node.state.prosperity >= 100) {
                return node.state.plan + ";" + node.state.money_spent + ";" + nodesExpanded;
            }
            if (queuingFunction.equals("BF")) {
                LLAPSearch.BreadthFirstQueuingFunction(queue, node);
            } else if (queuingFunction.equals("DF")) {
                LLAPSearch.DepthFirstQueuingFunction(queue, node);
            } else if (queuingFunction.equals("ID")) {
                LLAPSearch.IterativeDeepeningQueuingFunction(queue, node);
            }else if (queuingFunction.equals("UC")) {
                LLAPSearch.UniformCostQueuingFunction(pQueue, node);
            } else if (queuingFunction.equals("GR1")) {
                LLAPSearch.GreedySearchQueuingFunction(pQueue, node);
            } else if (queuingFunction.equals("AS1")) {
                LLAPSearch.AStarSearchQueuingFunction(pQueue, node);
            } else if (queuingFunction.equals("GR2")) {
                LLAPSearch.GreedySearchQueuingFunctionTwo(pQueue, node);
            } else if (queuingFunction.equals("AS2")) {
                LLAPSearch.AStarSearchQueuingFunctionTwo(pQueue, node);
            }
        }
    }
}
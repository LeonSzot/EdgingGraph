import java.util.*;

public class Graph {
    private List<Node> nodes = new ArrayList<Node>();
    private List<Edge> edges = new ArrayList<Edge>();

    public ArrayList<Edge> primMST(int i) {
        ArrayList<Edge> mst = new ArrayList<>();
        if (nodes.isEmpty()) return mst;

        HashMap<Node, Boolean> inMST = new HashMap<>();
        for (Node node : nodes) {
            inMST.put(node, false);
        }

        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
        Node startNode = getNode(i);

        addEdgesToQueue(startNode, edgeQueue, inMST);

        while (!edgeQueue.isEmpty()) {
            Edge minEdge = edgeQueue.poll();
            if (!inMST.get(minEdge.v2)) {
                mst.add(minEdge);
                addEdgesToQueue(minEdge.v2, edgeQueue, inMST);
            }
        }

        return mst;
    }

    private void addEdgesToQueue(Node node, PriorityQueue<Edge> edgeQueue, HashMap<Node, Boolean> inMST) {
        inMST.put(node, true);
        for (Edge edge : edges) {
            if (edge.v1 == node && !inMST.get(edge.v2)) {
                edgeQueue.add(edge);
            } else if (edge.v2 == node && !inMST.get(edge.v1)) {
                edgeQueue.add(new Edge(edge.v2, edge.v1, edge.weight));
            }
        }
    }

    public List<Edge> minimalSpanningTreeKruskal() {
        List<Edge> mst = new ArrayList<>();
        List<List<Node>> sets = new ArrayList<>();

        for (Node node : nodes) {
            List<Node> set = new ArrayList<>();
            set.add(node);
            sets.add(set);
        }

        edges.sort(Comparator.comparingInt(edge -> edge.weight));

        for (Edge edge : edges) {
            Node v1 = edge.v1;
            Node v2 = edge.v2;

            List<Node> set1 = findSet(sets, v1);
            List<Node> set2 = findSet(sets, v2);

            if (set1 != set2) {
                mst.add(edge);
                set1.addAll(set2);
                sets.remove(set2);
            }
        }

        return mst;
    }

    public int minimalSpanningTreeKruskalWeight(){
        List<Edge> minimalSpanningTree = new ArrayList<>();
        minimalSpanningTree = minimalSpanningTreeKruskal();
        int weight = 0;

        for (int i = 0; i < minimalSpanningTree.size(); i++){
            weight += minimalSpanningTree.get(i).weight;
        }

        return weight;
    }

    private List<Node> findSet(List<List<Node>> sets, Node node) {
        for (List<Node> set : sets) {
            if (set.contains(node)) {
                return set;
            }
        }
        return null;
    }

    public int countNodes(){
        return nodes.size();
    }

    public int countEdges(){
        return edges.size();
    }

    public int shortestPath(int startNodeID, int destinationNodeID){
        Node startNode = getNode(startNodeID);
        Node endNode = getNode(destinationNodeID);

        if (startNode != null && endNode != null) {
            for (Node node : nodes) {
                node.pathCost = Integer.MAX_VALUE;
                node.previousNode = null;
            }

            startNode.pathCost = 0;

            List<Node> unvisitedNodes = new ArrayList<>(nodes);

            while (!unvisitedNodes.isEmpty()) {
                Node currentNode = getMinimumCostNode(unvisitedNodes);

                if (currentNode.pathCost == Integer.MAX_VALUE) {
                    break;
                }

                unvisitedNodes.remove(currentNode);

                for (Edge edge : getEdges(currentNode.id)) {
                    Node neighbor = getOtherNode(currentNode, edge);
                    int newCost = currentNode.pathCost + edge.weight;

                    if (newCost < neighbor.pathCost) {
                        neighbor.pathCost = newCost;
                        neighbor.previousNode = currentNode;
                    }
                }
            }

            return endNode.pathCost;
        }

        return -1;
    }

    private Node getMinimumCostNode(List<Node> nodes) {
        Node minNode = null;
        int minCost = Integer.MAX_VALUE;

        for (Node node : nodes) {
            if (node.pathCost < minCost) {
                minCost = node.pathCost;
                minNode = node;
            }
        }

        return minNode;
    }

    private Node getOtherNode(Node a, Edge b){
        if (b.v1 == a){
            return b.v2;
        }
        else{
            return b.v1;
        }
    }

    public void createNode(int id){
        if (getNode(id) == null){
            nodes.add(new Node(id));
            // System.out.println("[Graph] Added node with id " + id);
        }
        else{
            // System.out.println("[Graph] There already is a node with id " + id);
        }
    }

    public void createEdge(int id1, int id2, int weight){
        Node v1 = getNode(id1);
        Node v2 = getNode(id2);
        if (weight >= 0){
            if (v1 != null){
                if (v2 != null){
                    edges.add(new Edge(v1, v2, weight));
                    // System.out.println("[Graph] Added edge with weight " + weight + ", and node ids " + id1 + " and " + id2);
                }
                else{
                    // System.out.println("[Graph] Couldn't find node with id " + id2);
                }
            }
            else{
                // System.out.println("[Graph] Couldn't find node with id " + id1);
            }
        }
        else{
            // System.out.println("[Graph] Weight must be 0 or above");
        }

    }

    public void removeEdge(int id1, int id2){
        if (getEdge(id1, id2) != null){
            edges.remove(getEdge(id1, id2));
            // System.out.println("[Graph] Removed edge with ids " + id1 + " and " + id2);
        }
        else if (getEdge(id2, id1) != null){
            edges.remove(getEdge(id2, id1));
            // System.out.println("[Graph] Removed edge with ids " + id2 + " and " + id1);
        }
        else{
            // System.out.println("[Graph] There is no edge with ids " + id1 + " and " + id2);
        }
    }

    public void removeNode(int id){
        Node node = getNode(id);

        if (node != null){
            List<Edge> edgesToRemove = getEdges(id);

            if (edgesToRemove != null){
                int i;
                for (i = 0; i < edgesToRemove.size(); i++){
                    // System.out.println("[Graph] Removed edge with weight " + edgesToRemove.get(i).weight + " and ids " + edgesToRemove.get(i).v1.id + " and " + edgesToRemove.get(i).v2.id);
                    edges.remove(edgesToRemove.get(i));
                }
            }

            nodes.remove(node);
            // System.out.println("[Graph] Removed node with id " + id);
        }
        else{
            // System.out.println("[Graph] There is no node with id " + id);
        }
    }

    private Node getNode(int id){
        if (nodes != null){
            int i;
            for (i = 0; i < nodes.size(); i++){
                if (id == nodes.get(i).id){
                    return nodes.get(i);
                }
            }
        }

        // System.out.println("[Graph] No node found with that id");
        return null;
    }

    private List<Edge> getEdges(int id){
        List<Edge> edgesTemp = new ArrayList<Edge>();

        if (edges != null){
            int i;
            for (i = 0; i < edges.size(); i++){
                if (id == edges.get(i).v1.id || id == edges.get(i).v2.id){
                    edgesTemp.add(edges.get(i));
                }
            }
        }
        else{
            return null;
        }

        return edgesTemp;
    }

    public Edge getEdge(int id1, int id2){
        if (edges != null){
            int i = 0;
            for (i = 0; i < edges.size(); i++){
                if (id1 == edges.get(i).v1.id && id2 == edges.get(i).v2.id){
                    return edges.get(i);
                }
                else if (id2 == edges.get(i).v1.id && id1 == edges.get(i).v2.id){
                    return edges.get(i);
                }
            }
        }

        // System.out.println("[Graph] No edge found with that ids");
        return null;
    }
    // Minimal Chromatic Number
    public int minimalChromaticNumber(int startNode) {
        if (nodes.isEmpty()) return 0;

        HashMap<Node, Integer> colorMap = new HashMap<>();
        colorMap.put(getNode(startNode), 0);

        for (int i = 1; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            Set<Integer> usedColors = new HashSet<>();
            for (Edge edge : edges) {
                if (edge.v1 == node) {
                    Node neighbor = edge.v2;
                    if (colorMap.containsKey(neighbor)) {
                        usedColors.add(colorMap.get(neighbor));
                    }
                } else if (edge.v2 == node) {
                    Node neighbor = edge.v1;
                    if (colorMap.containsKey(neighbor)) {
                        usedColors.add(colorMap.get(neighbor));
                    }
                }
            }

            int cr;
            for (cr = 0; cr < nodes.size(); cr++) {
                if (!usedColors.contains(cr)) {
                    break;
                }
            }

            colorMap.put(node, cr);
        }

        int maxColor = 0;
        for (int color : colorMap.values()) {
            if (color > maxColor) {
                maxColor = color;
            }
        }

        return maxColor + 1;
    }
}
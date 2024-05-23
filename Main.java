public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.createNode(1);
        graph.createNode(2);
        graph.createNode(3);
        graph.createNode(4);
        graph.createNode(5);
        graph.createNode(6);
        graph.createNode(7);
        graph.createEdge(1, 2, 3);
        graph.createEdge(1, 3, 2);
        graph.createEdge(3, 4, 6);
        graph.createEdge(4, 5, 9);
        graph.createEdge(5, 3, 1);
        graph.createEdge(3, 6, 3);
        graph.createEdge(6, 4, 1);
        graph.createEdge(6, 7, 7);
        graph.createEdge(7, 4, 4);

        System.out.println("[Main] Shortest path cost: " + graph.shortestPath(1, 7));
        System.out.println("[Main] Shortest path cost: " + graph.shortestPath(1, 4));
        System.out.println("[Main] Shortest path cost: " + graph.shortestPath(3, 6));
    }
}
public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.createEdge(1,1,2);
        graph.createEdge(1,2,3);
        graph.createEdge(2,1,3);
        graph.createEdge(1,3,3);

        graph.removeEdge(2,1);
        graph.removeNode(1);

    }
}
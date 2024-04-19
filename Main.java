public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.addNode(7);
        graph.addNode(8);
        graph.addNode(9);
        graph.addNode(10);

        graph.createEdge(1,2,5);
        graph.createEdge(1,3,2);
        graph.createEdge(2,3,2);
        graph.createEdge(2,4,7);
        graph.createEdge(3,4,6);
        graph.createEdge(3,5,8);
        graph.createEdge(4,5,1);
        graph.createEdge(4,8,4);
        graph.createEdge(5,6,5);
        graph.createEdge(6,7,1);
        graph.createEdge(8,9,6);
        graph.createEdge(8,10,2);
        graph.createEdge(9,10,6);

        graph.calculatePath(1,11);
        graph.calculatePath(10,5);
        graph.calculatePath(10,3);

    }
}
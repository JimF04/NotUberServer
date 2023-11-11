package com.apiproyect.NotUberServer.Map;

import java.util.HashSet;
import java.util.Set;

public class Grafo {
    private Set<Node> nodes = new HashSet<>();


    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public void calculateShortestPaths(Node source) {
        Node.calculateShortestPathFromSource(this, source);
    }

    public static void main(String[] args) {
        Grafo graph = new Grafo();

        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.addDestination(nodeB, 10);
        nodeA.addDestination(nodeC, 15);

        nodeB.addDestination(nodeD, 12);
        nodeB.addDestination(nodeF, 15);

        nodeC.addDestination(nodeE, 10);

        nodeD.addDestination(nodeE, 2);
        nodeD.addDestination(nodeF, 1);

        nodeF.addDestination(nodeE, 5);

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        graph.calculateShortestPaths(nodeA);

        for (Node node : graph.getNodes()) {
            System.out.println("Nodo: " + node.getName());
            System.out.println("Distancia: " + node.getDistance());

            System.out.print("Camino más corto: ");
            for (Node pathNode : node.getShortestPath()) {
                System.out.print(pathNode.getName() + " ");
            }

            System.out.println("\n-----");
        }

        // Puedes realizar más pruebas según tus necesidades
    }
}

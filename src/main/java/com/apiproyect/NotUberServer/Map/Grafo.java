package com.apiproyect.NotUberServer.Map;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;
import java.util.HashMap;

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
        nodeA.addDestination(nodeD, 5);
        nodeA.addDestination(nodeE, 20);
        nodeA.addDestination(nodeF, 15);

        nodeB.addDestination(nodeA, 10);
        nodeB.addDestination(nodeC, 5);
        nodeB.addDestination(nodeD, 12);
        nodeB.addDestination(nodeE, 25);
        nodeB.addDestination(nodeF, 30);

        nodeC.addDestination(nodeA, 15);
        nodeC.addDestination(nodeB, 5);
        nodeC.addDestination(nodeD, 10);
        nodeC.addDestination(nodeE, 30);
        nodeC.addDestination(nodeF, 20);

        nodeD.addDestination(nodeA, 5);
        nodeD.addDestination(nodeB, 12);
        nodeD.addDestination(nodeC, 10);
        nodeD.addDestination(nodeE, 2);
        nodeD.addDestination(nodeF, 1);

        nodeE.addDestination(nodeA, 20);
        nodeE.addDestination(nodeB, 25);
        nodeE.addDestination(nodeC, 30);
        nodeE.addDestination(nodeD, 2);
        nodeE.addDestination(nodeF, 5);

        nodeF.addDestination(nodeA, 15);
        nodeF.addDestination(nodeB, 30);
        nodeF.addDestination(nodeC, 20);
        nodeF.addDestination(nodeD, 1);
        nodeF.addDestination(nodeE, 5);

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        // Solicitar al usuario que ingrese el nodo de inicio
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del nodo de inicio: ");
        String startingNodeName = scanner.nextLine();

        // Encontrar el nodo de inicio en el grafo
        Node startingNode = null;
        for (Node node : graph.getNodes()) {
            if (node.getName().equals(startingNodeName)) {
                startingNode = node;
                break;
            }
        }

        if (startingNode != null) {
            // Calcular los caminos más cortos desde el nodo de inicio
            graph.calculateShortestPaths(startingNode);

            // Imprimir resultados
            for (Node node : graph.getNodes()) {
                System.out.println("Nodo: " + node.getName());
                System.out.println("Distancia: " + node.getDistance());

                System.out.print("Camino más corto: ");
                for (Node pathNode : node.getShortestPath()) {
                    System.out.print(pathNode.getName() + " ");
                }

                System.out.println("\n-----");
            }
        } else {
            System.out.println("Nodo de inicio no encontrado en el grafo.");
        }

        // Cerrar el escáner
        scanner.close();
    }
}


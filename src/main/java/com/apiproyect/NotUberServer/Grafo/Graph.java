package com.apiproyect.NotUberServer.Grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Node, List<Edge>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addNode(Node node) {
        adjacencyList.put(node, new ArrayList<>());
    }

    public void addEdge(Node source, Node destination, double weight) {
        Edge edge = new Edge(destination, weight);
        adjacencyList.get(source).add(edge);

        // Añadir la arista desde el nodo destination al nodo source (bidireccional)
        Edge reverseEdge = new Edge(source, weight);
        adjacencyList.get(destination).add(reverseEdge);
    }


    public List<Edge> getEdges(Node node) {
        return adjacencyList.get(node);
    }

    public List<Node> getNodes() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    public static class Edge {
        private Node destination;
        private double weight;

        public Edge(Node destination, double weight) {
            this.destination = destination;
            this.weight = weight;
        }

        public Node getDestination() {
            return destination;
        }

        public double getWeight() {
            return weight;
        }
    }
}



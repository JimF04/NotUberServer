package com.apiproyect.NotUberServer.Grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representación de un grafo ponderado no dirigido.
 */
public class Graph {
    private Map<Node, List<Edge>> adjacencyList;

    /**
     * Constructor que inicializa el grafo.
     */
    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Agrega un nuevo nodo al grafo.
     *
     * @param node Nodo a agregar al grafo.
     */
    public void addNode(Node node) {
        adjacencyList.put(node, new ArrayList<>());
    }

    /**
     * Agrega una arista ponderada entre dos nodos en el grafo (bidireccional).
     *
     * @param source      Nodo de origen.
     * @param destination Nodo de destino.
     * @param weight      Peso de la arista.
     */
    public void addEdge(Node source, Node destination, double weight) {
        Edge edge = new Edge(destination, weight);
        adjacencyList.get(source).add(edge);

        // Añadir la arista desde el nodo destination al nodo source (bidireccional)
        Edge reverseEdge = new Edge(source, weight);
        adjacencyList.get(destination).add(reverseEdge);
    }


    /**
     * Obtiene las aristas conectadas a un nodo en el grafo.
     *
     * @param node Nodo del cual obtener las aristas.
     * @return Lista de aristas conectadas al nodo.
     */
    public List<Edge> getEdges(Node node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    /**
     * Obtiene todos los nodos presentes en el grafo.
     *
     * @return Lista de todos los nodos en el grafo.
     */
    public List<Node> getNodes() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    /**
     * Clase interna que representa una arista en el grafo.
     */
    public static class Edge {
        private Node destination;
        private double weight;

        /**
         * Constructor de la clase Edge.
         *
         * @param destination Nodo de destino de la arista.
         * @param weight      Peso de la arista.
         */
        public Edge(Node destination, double weight) {
            this.destination = destination;
            this.weight = weight;
        }


        /**
         * Obtiene el nodo de destino de la arista.
         *
         * @return Nodo de destino.
         */
        public Node getDestination() {
            return destination;
        }

        /**
         * Obtiene el peso de la arista.
         *
         * @return Peso de la arista.
         */
        public double getWeight() {
            return weight;
        }
    }
}



package com.apiproyect.NotUberServer.Map;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.HashMap;

/**
 * Clase que representa un nodo en un grafo.
 */
public class Node {
    private String name;
    private List<Node> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;
    private Map<Node, Integer> adjacentNodes = new HashMap<>();

    /**
     * Agrega un nodo de destino con una distancia asociada.
     *
     * @param destination Nodo de destino.
     * @param distance    Distancia al nodo de destino.
     */
    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    // Getters y Setters para los atributos de la clase

    /**
     * Obtiene el nombre del nodo.
     *
     * @return Nombre del nodo.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del nodo.
     *
     * @param name Nombre del nodo.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la lista del camino más corto.
     *
     * @return Lista del camino más corto.
     */
    public List<Node> getShortestPath() {
        return shortestPath;
    }

    /**
     * Establece la lista del camino más corto.
     *
     * @param shortestPath Lista del camino más corto.
     */
    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    /**
     * Obtiene la distancia al nodo.
     *
     * @return Distancia al nodo.
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * Establece la distancia al nodo.
     *
     * @param distance Distancia al nodo.
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     * Obtiene los nodos adyacentes y sus distancias asociadas.
     *
     * @return Mapa de nodos adyacentes y distancias.
     */
    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    /**
     * Establece los nodos adyacentes y sus distancias asociadas.
     *
     * @param adjacentNodes Mapa de nodos adyacentes y distancias.
     */
    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    /**
     * Constructor que inicializa el nodo con un nombre.
     *
     * @param name Nombre del nodo.
     */
    public Node(String name) {
        this.name = name;
    }

    /**
     * Calcula los caminos más cortos desde el nodo fuente en un grafo dado.
     *
     * @param graph  Grafo en el que se realiza el cálculo.
     * @param source Nodo fuente.
     */
    public static void calculateShortestPathFromSource(Grafo graph, Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            Node currentNode = getLowestDistance(unsettledNodes);
            unsettledNodes.remove(currentNode);

            for (Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }

            settledNodes.add(currentNode);
        }
    }

    /**
     * Obtiene el nodo con la distancia más baja en un conjunto de nodos no resueltos.
     *
     * @param unsettledNodes Conjunto de nodos no resueltos.
     * @return Nodo con la distancia más baja.
     */
    private static Node getLowestDistance(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }

        return lowestDistanceNode;
    }

    /**
     * Calcula la distancia mínima desde el nodo fuente hasta el nodo de evaluación.
     *
     * @param evaluationNode Nodo que se está evaluando.
     * @param edgeWeight     Peso de la arista entre los nodos.
     * @param sourceNode     Nodo fuente.
     */
    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeight, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeight < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeight);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(evaluationNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }
}

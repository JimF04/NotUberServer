package com.apiproyect.NotUberServer.Grafo;

import java.util.*;

import static com.apiproyect.NotUberServer.Grafo.Destinations.createGraph;

/**
 * Implementación del algoritmo de Dijkstra para encontrar rutas más cortas en un grafo ponderado.
 */
public class DjkstraAlgorithm {
    private static Map<Node, Node> previousNodes;  // Movemos previousNodes a nivel de clase

    /**
     * Encuentra las distancias más cortas desde el nodo de origen a todos los demás nodos en el grafo.
     *
     * @param graph  Grafo ponderado.
     * @param source Nodo de origen.
     * @return Mapa que contiene los nodos y sus distancias más cortas desde el nodo de origen.
     */
    public static Map<Node, Double> findShortestPaths(Graph graph, Node source) {
        Map<Node, Double> distances = new HashMap<>();
        previousNodes = new HashMap<>();  // Inicializamos previousNodes
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        Set<Node> visited = new HashSet<>();

        // Inicializar distancias con infinito y el nodo de origen con distancia 0
        for (Node node : graph.getNodes()) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }
        distances.put(source, 0.0);

        priorityQueue.add(source);

        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.poll();

            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            for (Graph.Edge edge : graph.getEdges(current)) {
                Node neighbor = edge.getDestination();
                double newDistance = distances.get(current) + edge.getWeight();

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previousNodes.put(neighbor, current);  // Actualizamos el nodo anterior
                    priorityQueue.add(neighbor);
                }
            }
        }

        return distances;
    }

    /**
     * Obtiene el camino más corto desde un nodo de destino hasta la oficina.
     *
     * @param graph          Grafo ponderado.
     * @param destinationNode Nodo de destino.
     * @param office         Nodo de la oficina.
     * @return Lista de nodos que representan el camino más corto desde el nodo de destino hasta la oficina.
     */
    public static List<Node> getShortestPathToOffice(Graph graph, Node destinationNode, Node office) {

        Map<Node, Double> distances = findShortestPaths(graph, destinationNode);
        List<Node> path = new ArrayList<>();
        Node current = office;

        while (current != null) {
            path.add(current);
            current = previousNodes.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Calcula la distancia total de un camino en el grafo.
     *
     * @param graph Grafo ponderado.
     * @param path  Lista de nodos que representan el camino.
     * @return Distancia total del camino.
     */
    public static double calculateTotalDistance(Graph graph, List<Node> path) {
        double totalDistance = 0.0;

        for (int i = 0; i < path.size() - 1; i++) {
            Node current = path.get(i);
            Node next = path.get(i + 1);

            for (Graph.Edge edge : graph.getEdges(current)) {
                if (edge.getDestination().equals(next)) {
                    totalDistance += edge.getWeight();
                    break;
                }
            }
        }

        return totalDistance;
    }

    /**
     * Método principal para demostrar el uso del algoritmo de Dijkstra en un grafo.
     *
     * @param args Argumentos de línea de comandos (no utilizado en este caso).
     */
    public static void main(String[] args){

        Graph graph = new Graph();
        createGraph(graph);

        GraphVisualizer.visualizeGraph(graph);

        String destinationName = "Destino 1";


        Node office = graph.getNodes().stream()
                .filter(node -> node.getName().equals("Empresa"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró el nodo de la empresa"));

        Node destinationNode = graph.getNodes().stream()
                .filter(node -> node.getName().equals(destinationName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró el nodo con el nombre: " + destinationName));

        List<Node> shortestPath = DjkstraAlgorithm.getShortestPathToOffice(graph, destinationNode, office);

        System.out.println("La distancia más corta es: " + shortestPath);

        System.out.println("Tiempo: " + DjkstraAlgorithm.calculateTotalDistance(graph, shortestPath));

    }
}

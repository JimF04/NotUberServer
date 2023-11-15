package com.apiproyect.NotUberServer.Grafo;

import java.util.*;
import java.util.stream.Collectors;

import static com.apiproyect.NotUberServer.Grafo.Destinations.createGraph;

/**
 * Clase que implementa el algoritmo de Dijkstra para encontrar rutas más cortas en un grafo ponderado.
 */
public class DjkstraAlgorithm {

    /**
     * Mapa que almacena los nodos anteriores en el camino más corto desde el nodo de origen.
     */
    private static Map<Node, Node> previousNodes;

    /**
     * Encuentra las distancias más cortas desde el nodo de origen a todos los demás nodos en el grafo.
     *
     * @param graph  Grafo ponderado.
     * @param source Nodo de origen.
     * @return Mapa que contiene los nodos y sus distancias más cortas desde el nodo de origen.
     */
    public static Map<Node, Double> findShortestPaths(Graph graph, Node source) {
        Map<Node, Double> distances = new HashMap<>();
        previousNodes = new HashMap<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        Set<Node> visited = new HashSet<>();

        graph.getNodes().forEach(node -> distances.put(node, Double.POSITIVE_INFINITY));
        distances.put(source, 0.0);

        priorityQueue.add(source);

        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.poll();

            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            graph.getEdges(current).forEach(edge -> {
                Node neighbor = edge.getDestination();
                double newDistance = distances.get(current) + edge.getWeight();

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previousNodes.put(neighbor, current);
                    priorityQueue.add(neighbor);
                }
            });
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
     * Obtiene el camino más corto desde un nodo de origen hasta un nodo de destino.
     *
     * @param graph       Grafo ponderado.
     * @param source      Nodo de origen.
     * @param destination Nodo de destino.
     * @return Lista de nodos que representan el camino más corto desde el nodo de origen hasta el nodo de destino.
     */
    public static List<Node> getShortestPath(Graph graph, Node source, Node destination) {
        Map<Node, Double> distances = findShortestPaths(graph, source);
        List<Node> path = new ArrayList<>();
        Node current = destination;

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

            totalDistance += graph.getEdges(current).stream()
                    .filter(edge -> edge.getDestination().equals(next))
                    .mapToDouble(Graph.Edge::getWeight)
                    .findFirst()
                    .orElseThrow();
        }

        return totalDistance;
    }

    /**
     * Obtiene una lista de los caminos más cortos entre destinos consecutivos.
     *
     * @param graph        Grafo ponderado.
     * @param destinations Lista de nodos que representan los destinos.
     * @return Lista de caminos más cortos entre destinos consecutivos.
     */
    public static List<List<Node>> getShortestPathsBetweenDestinations(Graph graph, List<Node> destinations) {
        List<List<Node>> paths = new ArrayList<>();

        for (int i = 0; i < destinations.size() - 1; i++) {
            Node source = destinations.get(i);
            Node destination = destinations.get(i + 1);
            List<Node> path = getShortestPath(graph, source, destination);
            paths.add(path);
        }

        return paths;
    }


    /**
     * Obtiene el camino más corto desde una lista de nodos hasta la empresa.
     *
     * @param graph       Grafo ponderado.
     * @param nodes       Lista de nodos.
     * @param companyNode Nodo de la empresa.
     * @return Lista de nodos que representan el camino más corto desde la lista de nodos hasta la empresa.
     */
    public static List<Node> getShortestPathToCompany(Graph graph, List<Node> nodes, Node companyNode) {
        List<Node> completePath = new ArrayList<>();

        List<List<Node>> pathsBetweenDestinations = getShortestPathsBetweenDestinations(graph, nodes);

        pathsBetweenDestinations.forEach(path ->
                completePath.addAll(completePath.isEmpty() ? path : path.subList(1, path.size())));

        List<Node> lastPath = getShortestPathToOffice(graph, nodes.get(nodes.size() - 1), companyNode);

        if (!completePath.isEmpty() && lastPath.get(0).equals(completePath.get(completePath.size() - 1))) {
            completePath.addAll(lastPath.subList(1, lastPath.size()));
        } else {
            completePath.addAll(lastPath);
        }

        return completePath;
    }

    /**
     * Método principal para demostrar el uso del algoritmo de Dijkstra en un grafo.
     *
     * @param args Argumentos de línea de comandos (no utilizado en este caso).
     */
    public static void main(String[] args) {
        Graph graph = new Graph();
        createGraph(graph);

        GraphVisualizer.visualizeGraph(graph);

        List<String> destinationNames = List.of("Destino 6", "Destino 1");
        String companyName = "Empresa";

        Node companyNode = graph.getNodes().stream()
                .filter(node -> node.getName().equals(companyName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró el nodo de la empresa"));

        List<Node> destinations = destinationNames.stream()
                .map(name -> graph.getNodes().stream()
                        .filter(node -> node.getName().equals(name))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("No se encontró el nodo con el nombre: " + name)))
                .collect(Collectors.toList());

        List<Node> completePath = getShortestPathToCompany(graph, destinations, companyNode);

        double totalDistance = calculateTotalDistance(graph, completePath);
        System.out.println("La distancia total para la ruta completa es: " + completePath);
        System.out.println("Tiempo total: " + totalDistance);
    }
}

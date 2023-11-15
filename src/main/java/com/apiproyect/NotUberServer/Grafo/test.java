package com.apiproyect.NotUberServer.Grafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class test {
}





//    /**
//     * Obtiene el camino más corto desde una lista de nodos hasta la empresa.
//     *
//     * @param graph       Grafo ponderado.
//     * @param nodes       Lista de nodos.
//     * @param companyNode Nodo de la empresa.
//     * @return Lista de nodos que representan el camino más corto desde la lista de nodos hasta la empresa.
//     */
//    public static List<Node> getShortestPathToCompany(Graph graph, List<Node> nodes, Node companyNode) {
//        List<Node> completePath = new ArrayList<>();
//        List<Node> remainingNodes = new ArrayList<>(nodes);
//
//        Node current = companyNode;
//
//        for (int i = 0; i < nodes.size(); i++) {
//            int nextIndex = findClosestNodeIndex(graph, current, remainingNodes);
//
//            if (nextIndex != -1) {
//                List<Node> shortestPath = getShortestPath(graph, current, remainingNodes.get(nextIndex));
//                completePath.addAll(shortestPath.subList(0, shortestPath.size() - 1));  // Agregamos el camino sin el último nodo (repetido)
//                current = remainingNodes.get(nextIndex);
//                remainingNodes.remove(nextIndex);
//            } else {
//                break;  // No hay más nodos para considerar
//            }
//        }
//
//        // Agregamos el último tramo desde el último destino hasta la empresa
//        List<Node> lastPath = getShortestPathToOffice(graph, companyNode, current);
//        completePath.addAll(lastPath);
//
//        Collections.reverse(completePath);
//
//        return completePath;
//    }
//
//    /**
//     * Encuentra el índice del nodo más cercano a partir de un nodo de origen en una lista de nodos.
//     *
//     * @param graph       Grafo ponderado.
//     * @param source      Nodo de origen.
//     * @param nodes       Lista de nodos.
//     * @return Índice del nodo más cercano o -1 si la lista está vacía.
//     */
//    private static int findClosestNodeIndex(Graph graph, Node source, List<Node> nodes) {
//        if (nodes.isEmpty()) {
//            return -1;
//        }
//
//        Map<Node, Double> distances = findShortestPaths(graph, source);
//        double minDistance = Double.POSITIVE_INFINITY;
//        int minIndex = -1;
//
//        for (int i = 0; i < nodes.size(); i++) {
//            Node node = nodes.get(i);
//            double distance = distances.get(node);
//
//            if (distance < minDistance) {
//                minDistance = distance;
//                minIndex = i;
//            }
//        }
//
//        return minIndex;
//    }
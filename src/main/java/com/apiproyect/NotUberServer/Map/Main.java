package com.apiproyect.NotUberServer.Map;

/**
 * La clase principal (Main) para demostrar el cálculo de caminos más cortos en un grafo.
 */
public class Main {

    /**
     * Método principal para ejecutar la demostración del cálculo de caminos más cortos en un grafo.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Crear un nuevo grafo
        Grafo graph = new Grafo();

        // Crear nodos del grafo
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        // Establecer conexiones entre los nodos con distancias asociadas
        nodeA.addDestination(nodeB, 10);
        nodeA.addDestination(nodeC, 15);

        nodeB.addDestination(nodeD, 12);
        nodeB.addDestination(nodeF, 15);

        nodeC.addDestination(nodeE, 10);

        nodeD.addDestination(nodeE, 2);
        nodeD.addDestination(nodeF, 1);

        nodeF.addDestination(nodeE, 5);

        // Agregar nodos al grafo
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        // Calcular los caminos más cortos desde el nodo A
        graph.calculateShortestPaths(nodeA);
    }
}

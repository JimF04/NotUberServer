package com.apiproyect.NotUberServer.Map;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Clase Grafo representa un grafo ponderado dirigido.
 */
public class Grafo {
    private Set<Node> nodes = new HashSet<>();

    /**
     * Obtiene el conjunto de nodos en el grafo.
     *
     * @return El conjunto de nodos en el grafo.
     */
    public Set<Node> getNodes() {
        return nodes;
    }

    /**
     * Establece el conjunto de nodos en el grafo.
     *
     * @param nodes El conjunto de nodos a establecer.
     */
    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Agrega un nodo al grafo.
     *
     * @param nodeA El nodo a agregar.
     */
    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    /**
     * Calcula los caminos más cortos desde un nodo fuente dado.
     *
     * @param source El nodo fuente.
     */
    public void calculateShortestPaths(Node source) {
        Node.calculateShortestPathFromSource(this, source);
    }

    /**
     * Método principal para probar la funcionalidad del grafo.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        Grafo graph = new Grafo();

        // Crea nodos A a Z
        for (char nodeName = 'A'; nodeName <= 'Z'; nodeName++) {
            Node node = new Node(String.valueOf(nodeName));
            graph.addNode(node);
        }

        // Conectar todos los nodos con distancias aleatorias
        for (Node node1 : graph.getNodes()) {
            for (Node node2 : graph.getNodes()) {
                if (!node1.equals(node2)) {
                    int distanciaAleatoria = (int) (Math.random() * 10) + 1; // Distancia aleatoria entre 1 y 10
                    node1.addDestination(node2, distanciaAleatoria);
                }
            }
        }

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

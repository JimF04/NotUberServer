package com.apiproyect.NotUberServer.Grafo;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Clase que proporciona métodos para la creación de un grafo de destinos con nodos y conexiones aleatorias.
 */
public class Destinations {


    /**
     * Crea un grafo de destinos con nodos y conexiones aleatorias.
     *
     * @param graph Grafo al que se agregarán los nodos y conexiones.
     */
    public static void createGraph(Graph graph) {

        // Nodo de la oficina con coordenadas específicas
        Node office = new Node("Empresa", 9.854979, -83.908870);
        graph.addNode(office);

        // Agrega nodos adicionales con coordenadas específicas
        List<Node> destinationNodes = createDestinationNodes();
        for (Node node : destinationNodes) {
            graph.addNode(node);
        }

        // Conecta nodos de forma aleatoria con distancias de 1 a 10
        for (Node node : destinationNodes) {
            connectRandomNodes(graph, node);
        }

        makeGraphConnected(graph, office);

        // Accede a la información del grafo según tus necesidades
        for (Node node : graph.getNodes()) {
            List<Graph.Edge> edges = graph.getEdges(node);
            for (Graph.Edge edge : edges) {
                System.out.println("Desde " + node + " a " + edge.getDestination() + ", Distancia: " + (int) edge.getWeight());
            }
        }

    }

    /**
     * Crea una lista de nodos de destino con coordenadas específicas.
     *
     * @return Lista de nodos de destino.
     */
    private static List<Node> createDestinationNodes() {
        List<Node> destinationNodes = new ArrayList<>();

        destinationNodes.add(new Node("Destino 1", 9.858343, -83.915458));
        destinationNodes.add(new Node("Destino 2", 9.864273, -83.905035));
        destinationNodes.add(new Node("Destino 3", 9.859572, -83.920138));
        destinationNodes.add(new Node("Destino 4", 9.863619, -83.919033));
        destinationNodes.add(new Node("Destino 5", 9.865683, -83.924410));
        destinationNodes.add(new Node("Destino 6", 9.853459, -83.922113));
        destinationNodes.add(new Node("Destino 7", 9.867980, -83.917383));
        destinationNodes.add(new Node("Destino 8", 9.864197, -83.929414));
        destinationNodes.add(new Node("Destino 9", 9.859132, -83.930065));
        destinationNodes.add(new Node("Destino 10", 9.849725, -83.930101));
        destinationNodes.add(new Node("Destino 11", 9.870709, -83.925172));
        destinationNodes.add(new Node("Destino 12", 9.863665, -83.911851));
        destinationNodes.add(new Node("Destino 13", 9.859179, -83.902669));
        destinationNodes.add(new Node("Destino 14", 9.861586, -83.923324));
        destinationNodes.add(new Node("Destino 15", 9.852123, -83.916770));
        destinationNodes.add(new Node("Destino 16", 9.872757, -83.926667));
        destinationNodes.add(new Node("Destino 17", 9.856040, -83.935752));
        destinationNodes.add(new Node("Destino 18", 9.846535, -83.903945));
        destinationNodes.add(new Node("Destino 19", 9.872534, -83.914492));
        destinationNodes.add(new Node("Destino 20", 9.866471, -83.934230));
        destinationNodes.add(new Node("Destino 21", 9.873238, -83.944175));
        destinationNodes.add(new Node("Destino 22", 9.866647, -83.942570));
        destinationNodes.add(new Node("Destino 23", 9.870243, -83.930742));
        destinationNodes.add(new Node("Destino 24", 9.853667, -83.947281));
        destinationNodes.add(new Node("Destino 25", 9.854355, -83.942265));
        destinationNodes.add(new Node("Destino 26", 9.875559, -83.904170));
        destinationNodes.add(new Node("Destino 27", 9.875722, -83.931309));
        destinationNodes.add(new Node("Destino 28", 9.835290, -83.930100));
        destinationNodes.add(new Node("Destino 29", 9.838267, -83.902797));

        return destinationNodes;
    }

    /**
     * Conecta un nodo de origen con nodos aleatorios en el grafo.
     *
     * @param graph  Grafo al que se agregarán las conexiones.
     * @param source Nodo de origen al que se conectarán otros nodos.
     */
    private static void connectRandomNodes(Graph graph, Node source) {
        int minConnections = 1;
        int maxConnections = 3;
        int numOfConnections = ThreadLocalRandom.current().nextInt(minConnections, maxConnections + 1); // Entre 1 y 3 conexiones

        List<Node> allNodes = new ArrayList<>(graph.getNodes());
        allNodes.remove(source); // El nodo de origen ya está conectado

        Collections.shuffle(allNodes);

        for (int i = 0; i < numOfConnections && !allNodes.isEmpty(); i++) {
            Node randomNode = allNodes.remove(0);

            graph.addEdge(source, randomNode, ThreadLocalRandom.current().nextInt(10) + 1); // Número entero de 1 a 10
        }
    }

    /**
     * Conecta nodos no visitados en el grafo para asegurar la conectividad.
     *
     * @param graph Grafo al que se agregarán las conexiones.
     * @param office Nodo de la oficina que se utilizará como referencia.
     */
    private static void makeGraphConnected(Graph graph, Node office) {
        Set<Node> visited = new HashSet<>();
        dfs(graph, office, visited);

        List<Node> unvisitedNodes = new ArrayList<>(graph.getNodes());
        unvisitedNodes.removeAll(visited);

        for (Node unvisitedNode : unvisitedNodes) {
            connectRandomNodes(graph, unvisitedNode);
        }
    }

    /**
     * Realiza un recorrido en profundidad (DFS) en el grafo para marcar nodos visitados.
     *
     * @param graph   Grafo a recorrer.
     * @param node    Nodo de inicio del recorrido.
     * @param visited Conjunto que almacena nodos visitados.
     */
    private static void dfs(Graph graph, Node node, Set<Node> visited) {
        visited.add(node);
        for (Graph.Edge edge : graph.getEdges(node)) {
            if (!visited.contains(edge.getDestination())) {
                dfs(graph, edge.getDestination(), visited);
            }
        }
    }
}

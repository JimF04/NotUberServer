package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Grafo.DjkstraAlgorithm;
import com.apiproyect.NotUberServer.Grafo.Graph;
import com.apiproyect.NotUberServer.Grafo.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador que gestiona las operaciones relacionadas con el grafo y los destinos.
 */
@RestController
@RequestMapping("/api")
public class DestinationController {

    /**
     * Grafo utilizado para representar la red de destinos.
     */
    private final Graph graph;

    /**
     * Constructor que inyecta el grafo al controlador.
     *
     * @param graph Grafo utilizado en la aplicación.
     */
    @Autowired
    public DestinationController(Graph graph) {
        this.graph = graph;
    }

    /**
     * Obtiene el grafo completo.
     *
     * @return Grafo que representa la red de destinos.
     */
    @GetMapping("/graph")
    public Graph getGraph() {
        return graph;
    }

    /**
     * Obtiene el camino más corto desde un destino específico hasta la empresa.
     *
     * @param destinationName Nombre del destino al que se desea encontrar el camino más corto.
     * @return Lista de nodos que representan el camino más corto desde el destino hasta la empresa.
     * @throws RuntimeException Si no se encuentra el nodo de la empresa o el nodo con el nombre especificado.
     */
    @GetMapping("/shortestPath/{destinationName}")
    public List<Node> getShortestPathToOffice(@PathVariable String destinationName) {
        Node office = graph.getNodes().stream()
                .filter(node -> node.getName().equals("Empresa"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró el nodo de la empresa"));

        Node destinationNode = graph.getNodes().stream()
                .filter(node -> node.getName().equals(destinationName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró el nodo con el nombre: " + destinationName));

        return DjkstraAlgorithm.getShortestPathToOffice(graph, destinationNode, office);
    }

    /**
     * Obtiene la suma total de los pesos de los bordes en el camino más corto desde un destino específico hasta la empresa.
     *
     * @param destinationName Nombre del destino al que se desea encontrar el camino más corto.
     * @return Suma total de los pesos de los bordes en el camino más corto.
     * @throws RuntimeException Si no se encuentra el nodo de la empresa o el nodo con el nombre especificado.
     */
    @GetMapping("/totalDistance/{destinationName}")
    public double getTotalDistanceToOffice(@PathVariable String destinationName) {
        Node office = graph.getNodes().stream()
                .filter(node -> node.getName().equals("Empresa"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró el nodo de la empresa"));

        Node destinationNode = graph.getNodes().stream()
                .filter(node -> node.getName().equals(destinationName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró el nodo con el nombre: " + destinationName));

        List<Node> shortestPath = DjkstraAlgorithm.getShortestPathToOffice(graph, destinationNode, office);

        return DjkstraAlgorithm.calculateTotalDistance(graph, shortestPath);
    }
}

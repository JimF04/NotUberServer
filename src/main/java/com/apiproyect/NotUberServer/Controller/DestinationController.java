package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Grafo.DjkstraAlgorithm;
import com.apiproyect.NotUberServer.Grafo.Graph;
import com.apiproyect.NotUberServer.Grafo.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
     * Obtiene el camino más corto desde una lista de destinos hasta la empresa.
     *
     * @param destinationNames Lista de nombres de destinos para los cuales se desea encontrar el camino más corto.
     * @return Lista de nodos que representan el camino más corto desde los destinos hasta la empresa.
     * @throws RuntimeException Si no se encuentra el nodo de la empresa o algún nodo con el nombre especificado.
     */
    @PostMapping("/shortestPathToCompany")
    public List<Node> getShortestPathToCompany(@RequestBody List<String> destinationNames) {
        Node office = graph.getNodes().stream()
                .filter(node -> node.getName().equals("Empresa"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró el nodo de la empresa"));

        List<Node> destinations = destinationNames.stream()
                .map(name -> graph.getNodes().stream()
                        .filter(node -> node.getName().equals(name))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("No se encontró el nodo con el nombre: " + name)))
                .collect(Collectors.toList());

        return DjkstraAlgorithm.getShortestPathToCompany(graph, destinations, office);
    }


    /**
     * Obtiene la distancia total desde una lista de destinos hasta la empresa.
     *
     * @param destinationNames Lista de nombres de destinos.
     * @return Suma total de los pesos de los bordes en el camino más corto.
     * @throws RuntimeException Si no se encuentra el nodo de la empresa o algún nodo con el nombre especificado.
     */
    @GetMapping("/totalDistance")
    public double getTotalDistanceForDestinations(@RequestBody List<String> destinationNames) {
        Node office = graph.getNodes().stream()
                .filter(node -> node.getName().equals("Empresa"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró el nodo de la empresa"));

        List<Node> destinations = destinationNames.stream()
                .map(name -> graph.getNodes().stream()
                        .filter(node -> node.getName().equals(name))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("No se encontró el nodo con el nombre: " + name)))
                .collect(Collectors.toList());

        List<Node> shortestPath = DjkstraAlgorithm.getShortestPathToCompany(graph, destinations, office);

        return DjkstraAlgorithm.calculateTotalDistance(graph, shortestPath);
    }
}

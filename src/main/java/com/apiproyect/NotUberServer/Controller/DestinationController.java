package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Grafo.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DestinationController {

    private final Graph graph;

    @Autowired
    public DestinationController(Graph graph) {
        this.graph = graph;
    }

    @GetMapping("/graph")
    public Graph getGraph() {
        return graph;
    }
}

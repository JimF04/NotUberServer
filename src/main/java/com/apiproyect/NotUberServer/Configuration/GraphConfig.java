package com.apiproyect.NotUberServer.Configuration;

import com.apiproyect.NotUberServer.Grafo.Destinations;
import com.apiproyect.NotUberServer.Grafo.Graph;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphConfig {

    @Bean
    public Graph graph() {
        Graph graph = new Graph();

        Destinations.createGraph(graph);

        return graph;
    }
}

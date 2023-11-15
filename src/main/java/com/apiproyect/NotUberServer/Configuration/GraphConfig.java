package com.apiproyect.NotUberServer.Configuration;

import com.apiproyect.NotUberServer.Grafo.Destinations;
import com.apiproyect.NotUberServer.Grafo.Graph;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Spring para la creación y configuración del grafo en la aplicación.
 */
@Configuration
public class GraphConfig {

    /**
     * Define un bean de tipo Graph que representa la red de destinos en la aplicación.
     *
     * @return Instancia de la clase Graph configurada con los destinos definidos.
     */
    @Bean
    public Graph graph() {
        Graph graph = new Graph();

        Destinations.createGraph(graph);

        return graph;
    }
}

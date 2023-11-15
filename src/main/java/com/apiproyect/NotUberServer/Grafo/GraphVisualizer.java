package com.apiproyect.NotUberServer.Grafo;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase que proporciona métodos para visualizar un grafo utilizando JGraphX.
 */
public class GraphVisualizer {

    /**
     * Visualiza el grafo en una ventana utilizando JGraphX.
     *
     * @param graph Grafo a visualizar.
     */
    public static void visualizeGraph(Graph graph) {
        // Crear un objeto mxGraph
        mxGraph mxGraph = new mxGraph();
        Object parent = mxGraph.getDefaultParent();

        mxGraph.getModel().beginUpdate();

        try {
            // Crear un mapa para almacenar las celdas de los nodos
            Map<Node, Object> vertexCells = graph.getNodes().stream()
                    .collect(java.util.stream.Collectors.toMap(node -> node, node -> {
                        // Generar coordenadas x e y aleatorias dentro de un rango
                        int x = ThreadLocalRandom.current().nextInt(1000);
                        int y = ThreadLocalRandom.current().nextInt(1000);
                        return mxGraph.insertVertex(parent, null, node.toString(), x, y, 50, 30);
                    }));

            // Conectar nodos
            for (Node node : graph.getNodes()) {
                for (Graph.Edge edge : graph.getEdges(node)) {
                    Node destination = edge.getDestination();
                    double weight = edge.getWeight();
                    String label = String.valueOf((int) weight);  // Convierte el valor de la conexión a una cadena

                    mxGraph.insertEdge(parent, null, label, vertexCells.get(node), vertexCells.get(destination));
                }
            }
        } finally {
            mxGraph.getModel().endUpdate();
        }

        // Crear un componente de gráfico de JGraphX
        mxGraphComponent graphComponent = new mxGraphComponent(mxGraph);

        // Crear un marco y agregar el componente del gráfico
        JFrame frame = new JFrame();
        frame.getContentPane().add(graphComponent);
        frame.setTitle("Graph Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }
}


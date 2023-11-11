package com.apiproyect.NotUberServer.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class GrafoPanel extends JPanel {

    private Grafo grafo;
    private Node selectedNode;
    private Map<Node, Point> nodePositions = new HashMap<>();

    public GrafoPanel(Grafo grafo) {
        this.grafo = grafo;
        this.addMouseListener(new NodeSelectionListener());
    }

    public void calculateNodePositions() {
        int gridSize = 150; // Ajusta el tamaño de la cuadrícula para separar más los nodos
        int x = gridSize;
        int y = gridSize;

        for (Node node : grafo.getNodes()) {
            nodePositions.put(node, new Point(x, y));
            x += gridSize * 1.8; // Incrementa el espacio horizontal
            if (x > getWidth() - gridSize) {
                x = gridSize;
                y += gridSize * 1.8; // Incrementa el espacio vertical
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar nodos y conexiones
        for (Node node : grafo.getNodes()) {
            drawNode(g, node);
            drawConnections(g, node);
        }

        // Resaltar el nodo seleccionado
        if (selectedNode != null) {
            drawSelectionHighlight(g, selectedNode);

            // Mostrar resultados de los caminos más cortos
            drawShortestPathsInfo(g, selectedNode);
        }
    }

    private void drawNode(Graphics g, Node node) {
        Point position = nodePositions.get(node);

        if (position != null) {
            // Dibujar el nodo como un círculo
            g.setColor(Color.BLUE);
            g.fillOval(position.x, position.y, 30, 30);

            // Dibujar el nombre del nodo
            g.setColor(Color.BLACK);
            g.drawString(node.getName(), position.x + 5, position.y + 15);
        }
    }

    private void drawConnections(Graphics g, Node node) {
        Point startPoint = nodePositions.get(node);

        if (startPoint != null) {
            // Dibujar conexiones a otros nodos
            Map<Node, Integer> adjacentNodes = node.getAdjacentNodes();
            for (Map.Entry<Node, Integer> entry : adjacentNodes.entrySet()) {
                Node adjacentNode = entry.getKey();
                Point endPoint = nodePositions.get(adjacentNode);

                // Verificar si endPoint es null antes de intentar dibujar la conexión
                if (endPoint != null) {
                    // Dibujar la línea de conexión
                    g.setColor(Color.RED);
                    g.drawLine(startPoint.x + 15, startPoint.y + 15, endPoint.x + 15, endPoint.y + 15);

                    // Dibujar la distancia
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(entry.getValue()), (startPoint.x + endPoint.x) / 2, (startPoint.y + endPoint.y) / 2);
                }
            }
        }
    }

    private void drawSelectionHighlight(Graphics g, Node node) {
        Point position = nodePositions.get(node);

        if (position != null) {
            // Dibujar un círculo alrededor del nodo seleccionado
            g.setColor(Color.GREEN);
            g.drawOval(position.x - 5, position.y - 5, 40, 40);
        }
    }

    private void drawShortestPathsInfo(Graphics g, Node sourceNode) {
        // (Código para mostrar información sobre caminos más cortos igual que antes...)
    }

    private class NodeSelectionListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            // Iterar sobre los nodos para verificar si el clic está dentro de alguno
            for (Node node : grafo.getNodes()) {
                Point position = nodePositions.get(node);

                if (position != null && mouseX >= position.x && mouseX <= position.x + 30 && mouseY >= position.y && mouseY <= position.y + 30) {
                    selectedNode = node;

                    // Calcular los caminos más cortos desde el nodo seleccionado
                    grafo.calculateShortestPaths(selectedNode);

                    // Repintar el panel para mostrar los resultados
                    repaint();
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear una instancia de Grafo y configurarla
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

            JFrame frame = new JFrame("Grafo Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080); // Ajusta el tamaño del panel

            // Utilizar la instancia de Grafo creada anteriormente
            GrafoPanel grafoPanel = new GrafoPanel(graph);
            frame.add(grafoPanel);

            frame.setVisible(true);

            // Llamar a calculateNodePositions después de que el panel se vuelva visible
            grafoPanel.calculateNodePositions();
            grafoPanel.repaint();
        });
    }
}




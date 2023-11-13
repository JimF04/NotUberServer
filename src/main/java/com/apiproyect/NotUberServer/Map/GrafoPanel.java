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
    private final Node[] nodosAMostrar; // Array de nodos que quieres mostrar

    public GrafoPanel(Grafo grafo, Node[] nodosAMostrar) {
        this.grafo = grafo;
        this.nodosAMostrar = nodosAMostrar;
        this.addMouseListener(new NodeSelectionListener());
    }

    public void calculateNodePositions() {
        // Limpiar el mapa de posiciones antes de asignar nuevas posiciones
        nodePositions.clear();

        // Asignar manualmente las posiciones para cada nodo
        nodePositions.put(nodosAMostrar[0], new Point(100, 100));
        nodePositions.put(nodosAMostrar[1], new Point(200, 200));
        nodePositions.put(nodosAMostrar[2], new Point(250, 300));
        nodePositions.put(nodosAMostrar[3], new Point(400, 400));
        // ... Agregar más nodos según sea necesario
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Node node : nodosAMostrar) {
            if (shouldShowNode(node)) {
                drawNode(g, node);
                drawConnections(g, node);
            }
        }

        if (selectedNode != null) {
            drawSelectionHighlight(g, selectedNode);
            drawShortestPathsInfo(g, selectedNode);
        }
    }

    private void drawNode(Graphics g, Node node) {
        Point position = nodePositions.get(node);

        if (position != null) {
            g.setColor(Color.BLUE);
            g.fillOval(position.x, position.y, 30, 30);

            g.setColor(Color.BLACK);
            g.drawString(node.getName(), position.x + 5, position.y + 15);
        }
    }

    private void drawConnections(Graphics g, Node node) {
        Point startPoint = nodePositions.get(node);

        if (startPoint != null) {
            Map<Node, Integer> adjacentNodes = node.getAdjacentNodes();
            for (Map.Entry<Node, Integer> entry : adjacentNodes.entrySet()) {
                Node adjacentNode = entry.getKey();
                Point endPoint = nodePositions.get(adjacentNode);

                if (endPoint != null) {
                    g.setColor(Color.RED);
                    g.drawLine(startPoint.x + 15, startPoint.y + 15, endPoint.x + 15, endPoint.y + 15);

                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(entry.getValue()), (startPoint.x + endPoint.x) / 2, (startPoint.y + endPoint.y) / 2);
                }
            }
        }
    }

    private void drawSelectionHighlight(Graphics g, Node node) {
        Point position = nodePositions.get(node);

        if (position != null) {
            g.setColor(Color.GREEN);
            g.drawOval(position.x - 5, position.y - 5, 40, 40);
        }
    }

    private void drawShortestPathsInfo(Graphics g, Node sourceNode) {
        // Código para mostrar información sobre caminos más cortos igual que antes
    }

    private boolean shouldShowNode(Node node) {
        return true; // Puedes agregar tu lógica de filtro aquí si es necesario
    }

    private class NodeSelectionListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            for (Node node : nodosAMostrar) {
                Point position = nodePositions.get(node);

                if (position != null && mouseX >= position.x && mouseX <= position.x + 30 && mouseY >= position.y && mouseY <= position.y + 30) {
                    selectedNode = node;

                    grafo.calculateShortestPaths(selectedNode);

                    repaint();
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Grafo graph = new Grafo();

            // Crea nodos A, B, C
            Node nodeA = new Node("A");
            Node nodeB = new Node("B");
            Node nodeC = new Node("C");
            Node nodeD = new Node("D");

            graph.addNode(nodeA);
            graph.addNode(nodeB);
            graph.addNode(nodeC);
            graph.addNode(nodeD);

            // Conecta nodos con distancias aleatorias
            nodeA.addDestination(nodeB, 5);
            nodeB.addDestination(nodeC, 8);
            nodeC.addDestination(nodeD,8);

            JFrame frame = new JFrame("Grafo Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);

            GrafoPanel grafoPanel = new GrafoPanel(graph, new Node[]{nodeA, nodeB, nodeC, nodeD});
            frame.add(grafoPanel);

            frame.setVisible(true);

            grafoPanel.calculateNodePositions();
            grafoPanel.repaint();
        });
    }
}

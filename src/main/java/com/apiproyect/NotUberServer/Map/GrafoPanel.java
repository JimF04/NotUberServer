package com.apiproyect.NotUberServer.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GrafoPanel representa un panel gráfico para visualizar un grafo y calcular distancias y caminos más cortos.
 */
public class GrafoPanel extends JPanel {

    private Grafo grafo;
    private Node selectedNode;
    private Map<Node, Point> nodePositions = new HashMap<>();
    private final Node[] nodosAMostrar;

    /**
     * Constructor de GrafoPanel.
     *
     * @param grafo        El grafo que se va a visualizar.
     * @param nodosAMostrar La matriz de nodos a mostrar en el panel.
     */
    public GrafoPanel(Grafo grafo, Node[] nodosAMostrar) {
        this.grafo = grafo;
        this.nodosAMostrar = nodosAMostrar;
        this.addMouseListener(new NodeSelectionListener());
    }

    /**
     * Calcula las posiciones de los nodos en el panel.
     */
    public void calculateNodePositions() {
        nodePositions.clear();
        // Asigna posiciones predefinidas a algunos nodos (ajusta según sea necesario)
        nodePositions.put(nodosAMostrar[0], new Point(100, 100));
        nodePositions.put(nodosAMostrar[1], new Point(210, 160));
        nodePositions.put(nodosAMostrar[2], new Point(100, 250));
        nodePositions.put(nodosAMostrar[3], new Point(400, 400));
        // Agrega más asignaciones de posiciones según sea necesario...
    }

    /**
     * Método de dibujo del panel.
     *
     * @param g El objeto Graphics utilizado para dibujar.
     */
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
        }
    }

    private void drawNode(Graphics g, Node node) {
        Point position = nodePositions.get(node);

        if (position != null) {
            g.setColor(Color.BLUE);
            g.fillOval(position.x, position.y, 15, 15);

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

    private boolean shouldShowNode(Node node) {
        return true; // Puedes ajustar la lógica según sea necesario
    }

    private class NodeSelectionListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            for (Node node : nodosAMostrar) {
                Point position = nodePositions.get(node);

                if (position != null && mouseX >= position.x && mouseX <= position.x + 30 && mouseY >= position.y && mouseY <= position.y + 30) {
                    if (selectedNode == null) {
                        selectedNode = node;
                    } else {
                        Node endNode = node;
                        calculateDistanceAndShortestPath(selectedNode, endNode);
                        selectedNode = null;
                    }

                    repaint();
                    break;
                }
            }
        }
    }

    private void calculateDistanceAndShortestPath(Node startNode, Node endNode) {
        Node.calculateShortestPathFromSource(grafo, startNode);
        int distance = endNode.getDistance();
        List<Node> shortestPath = endNode.getShortestPath();

        System.out.println("Distancia entre " + startNode.getName() + " y " + endNode.getName() + ": " + distance);

        if (distance != Integer.MAX_VALUE) {
            StringBuilder pathStringBuilder = new StringBuilder("Camino más corto: ");
            for (Node node : shortestPath) {
                pathStringBuilder.append(node.getName()).append(" -> ");
            }
            pathStringBuilder.setLength(pathStringBuilder.length() - 4); // Elimina el último " -> "
            System.out.println(pathStringBuilder.toString());
        } else {
            System.out.println("No hay un camino válido.");
        }
    }

    public Node getSelectedNode() {
        return selectedNode;
    }

    /**
     * Método principal para probar la funcionalidad del panel de grafo.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Grafo graph = new Grafo();

            // Crear nodos
            Node[] allNodes = new Node[26];
            for (int i = 0; i < 26; i++) {
                allNodes[i] = new Node(Character.toString((char) ('A' + i)));
                graph.addNode(allNodes[i]);
            }

            // Conectar nodos con distancias aleatorias (ajustar según sea necesario)
            //...

            // Conectar nodos adicionales (ajustar según sea necesario)
            //...

            JFrame frame = new JFrame("Grafo Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);

            JButton boton = new JButton("Calcular Distancias");
            boton.setBounds(600, 300, 150, 20);

            JTextArea areadetexto = new JTextArea();
            areadetexto.setBounds(600, 100, 50, 20);

            GrafoPanel grafoPanel = new GrafoPanel(graph, allNodes);
            frame.add(grafoPanel);

            boton.addActionListener(e -> {
                Node selectedNode = grafoPanel.getSelectedNode();
                if (selectedNode != null) {
                    grafoPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecciona un nodo antes de calcular las distancias.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            frame.setVisible(true);

            grafoPanel.calculateNodePositions();
            grafoPanel.repaint();
        });
    }
}

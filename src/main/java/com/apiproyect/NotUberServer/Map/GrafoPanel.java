package com.apiproyect.NotUberServer.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrafoPanel extends JPanel {

    private Grafo grafo;
    private Node selectedNode;
    private Map<Node, Point> nodePositions = new HashMap<>();
    private final Node[] nodosAMostrar;

    public GrafoPanel(Grafo grafo, Node[] nodosAMostrar) {
        this.grafo = grafo;
        this.nodosAMostrar = nodosAMostrar;
        this.addMouseListener(new NodeSelectionListener());
    }

    public void calculateNodePositions() {
        nodePositions.clear();
        nodePositions.put(nodosAMostrar[0], new Point(100, 100));
        nodePositions.put(nodosAMostrar[1], new Point(210, 160));
        nodePositions.put(nodosAMostrar[2], new Point(100, 250));
        nodePositions.put(nodosAMostrar[3], new Point(400, 400));

        nodePositions.put(nodosAMostrar[4], new Point(580, 720 ));
        nodePositions.put(nodosAMostrar[5], new Point(800, 700));
        nodePositions.put(nodosAMostrar[6], new Point(270, 420));
        nodePositions.put(nodosAMostrar[7], new Point(1100, 730));
        nodePositions.put(nodosAMostrar[8], new Point(880, 600));
        nodePositions.put(nodosAMostrar[9], new Point(750, 120));
        nodePositions.put(nodosAMostrar[10], new Point(350, 180));
        nodePositions.put(nodosAMostrar[11], new Point(180, 320));
        nodePositions.put(nodosAMostrar[12], new Point(240, 560));
        nodePositions.put(nodosAMostrar[13], new Point(1010, 640));
        nodePositions.put(nodosAMostrar[14], new Point(730, 600));
        nodePositions.put(nodosAMostrar[15], new Point(460, 520));
        nodePositions.put(nodosAMostrar[16], new Point(650, 520));
        nodePositions.put(nodosAMostrar[17], new Point(810, 380));
        nodePositions.put(nodosAMostrar[18], new Point(520, 310));
        nodePositions.put(nodosAMostrar[19], new Point(650, 420));
        nodePositions.put(nodosAMostrar[20], new Point(630, 300));
        nodePositions.put(nodosAMostrar[21], new Point(710, 220));
        nodePositions.put(nodosAMostrar[22], new Point(930, 200));
        nodePositions.put(nodosAMostrar[23], new Point(1010, 225));
        nodePositions.put(nodosAMostrar[24], new Point(1145, 370));
        nodePositions.put(nodosAMostrar[25], new Point(1010, 400));
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
        return true;
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
        List<Node> shortestPath = endNode.getShortestPath();
        int distance = endNode.getDistance();

        System.out.println("Distancia entre " + startNode.getName() + " y " + endNode.getName() + ": " + distance);

        if (distance != Integer.MAX_VALUE) {
            StringBuilder pathStringBuilder = new StringBuilder("Camino más corto: ");
            for (Node node : shortestPath) {
                pathStringBuilder.append(node.getName()).append(" -> ");
            }
            pathStringBuilder.setLength(pathStringBuilder.length() - 4); // Eliminar el último " -> "
            System.out.println(pathStringBuilder.toString());
        } else {
            System.out.println("No hay un camino válido.");
        }
    }

    public Node getSelectedNode() {
        return selectedNode;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Grafo graph = new Grafo();

            // Crear nodos
            Node[] allNodes = new Node[26];
            for (int i = 0; i < 26; i++) {
                allNodes[i] = new Node(Character.toString((char) ('A' + i)));
                graph.addNode(allNodes[i]);
            }

            // Conectar nodos con distancias aleatorias (eliminando algunas conexiones)
            allNodes[0].addDestination(allNodes[1], 2);
            allNodes[0].addDestination(allNodes[2], 10);
            allNodes[0].addDestination(allNodes[3], 25);

            allNodes[1].addDestination(allNodes[2], 7);
            allNodes[1].addDestination(allNodes[3], 20);

            allNodes[2].addDestination(allNodes[3], 8);

            allNodes[3].addDestination(allNodes[4], 5);
            allNodes[4].addDestination(allNodes[5], 3);
            allNodes[5].addDestination(allNodes[6], 6);
            allNodes[6].addDestination(allNodes[7], 4);
            allNodes[7].addDestination(allNodes[8], 8);
            allNodes[8].addDestination(allNodes[9], 7);
            allNodes[9].addDestination(allNodes[10], 9);
            allNodes[10].addDestination(allNodes[11], 5);
            allNodes[11].addDestination(allNodes[12], 6);
            allNodes[12].addDestination(allNodes[13], 8);
            allNodes[13].addDestination(allNodes[14], 7);
            allNodes[14].addDestination(allNodes[15], 4);
            allNodes[15].addDestination(allNodes[16], 5);
            allNodes[16].addDestination(allNodes[17], 6);
            allNodes[17].addDestination(allNodes[18], 7);
            allNodes[18].addDestination(allNodes[19], 8);
            allNodes[19].addDestination(allNodes[20], 9);
            allNodes[20].addDestination(allNodes[21], 7);
            allNodes[0].addDestination(allNodes[1], 2);
            allNodes[0].addDestination(allNodes[2], 10);
            allNodes[0].addDestination(allNodes[3], 25);

            allNodes[1].addDestination(allNodes[2], 7);
            allNodes[1].addDestination(allNodes[3], 20);

            allNodes[2].addDestination(allNodes[3], 8);

            allNodes[3].addDestination(allNodes[4], 5);
            allNodes[4].addDestination(allNodes[5], 3);
            allNodes[5].addDestination(allNodes[6], 6);
            allNodes[6].addDestination(allNodes[7], 4);
            allNodes[7].addDestination(allNodes[8], 8);
            allNodes[8].addDestination(allNodes[9], 7);
            allNodes[9].addDestination(allNodes[10], 9);
            allNodes[10].addDestination(allNodes[11], 5);
            allNodes[11].addDestination(allNodes[12], 6);
            allNodes[12].addDestination(allNodes[13], 8);
            allNodes[13].addDestination(allNodes[14], 7);
            allNodes[14].addDestination(allNodes[15], 4);
            allNodes[15].addDestination(allNodes[16], 5);
            allNodes[16].addDestination(allNodes[17], 6);
            allNodes[17].addDestination(allNodes[18], 7);
            allNodes[18].addDestination(allNodes[19], 8);
            allNodes[19].addDestination(allNodes[20], 9);
            allNodes[20].addDestination(allNodes[21], 7);
            allNodes[21].addDestination(allNodes[22], 6);
            allNodes[22].addDestination(allNodes[23], 5);
            allNodes[23].addDestination(allNodes[24], 4);
            allNodes[24].addDestination(allNodes[25], 3);

            // Conectar nodos adicionales (puedes ajustar según sea necesario)
            allNodes[0].addDestination(allNodes[4], 10);
            allNodes[1].addDestination(allNodes[5], 12);
            allNodes[2].addDestination(allNodes[6], 15);
            allNodes[3].addDestination(allNodes[7], 8);
            allNodes[21].addDestination(allNodes[22], 6);
            allNodes[22].addDestination(allNodes[23], 5);
            allNodes[23].addDestination(allNodes[24], 4);
            allNodes[24].addDestination(allNodes[25], 3);

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

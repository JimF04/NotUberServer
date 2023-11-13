package com.apiproyect.NotUberServer.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        nodePositions.put(nodosAMostrar[1], new Point(200, 200));
        nodePositions.put(nodosAMostrar[2], new Point(250, 300));
        nodePositions.put(nodosAMostrar[3], new Point(400, 400));
        nodePositions.put(nodosAMostrar[4], new Point(400, 500));
        nodePositions.put(nodosAMostrar[5], new Point(400, 600));
        nodePositions.put(nodosAMostrar[6], new Point(400, 700));
        nodePositions.put(nodosAMostrar[7], new Point(400, 800));
        nodePositions.put(nodosAMostrar[8], new Point(400, 900));
        nodePositions.put(nodosAMostrar[9], new Point(500, 400));
        nodePositions.put(nodosAMostrar[10], new Point(600, 400));
        nodePositions.put(nodosAMostrar[11], new Point(700, 400));
        nodePositions.put(nodosAMostrar[12], new Point(800, 400));
        nodePositions.put(nodosAMostrar[13], new Point(900, 400));
        nodePositions.put(nodosAMostrar[14], new Point(550, 400));
        nodePositions.put(nodosAMostrar[15], new Point(650, 400));
        nodePositions.put(nodosAMostrar[16], new Point(750, 400));
        nodePositions.put(nodosAMostrar[17], new Point(850, 400));
        nodePositions.put(nodosAMostrar[18], new Point(950, 400));
        nodePositions.put(nodosAMostrar[19], new Point(620, 400));
        nodePositions.put(nodosAMostrar[20], new Point(720, 400));
        nodePositions.put(nodosAMostrar[21], new Point(820, 400));
        nodePositions.put(nodosAMostrar[22], new Point(920, 400));
        nodePositions.put(nodosAMostrar[23], new Point(1000, 400));
        nodePositions.put(nodosAMostrar[24], new Point(420, 400));
        nodePositions.put(nodosAMostrar[25], new Point(420, 400));


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

            Node nodeA = new Node("A");
            Node nodeB = new Node("B");
            Node nodeC = new Node("C");
            Node nodeD = new Node("D");
            Node nodeE = new Node("E");
            Node nodeF = new Node("F");
            Node nodeG = new Node("G");
            Node nodeH = new Node("H");
            Node nodeI = new Node("I");
            Node nodeJ = new Node("J");
            Node nodeK = new Node("K");
            Node nodeL = new Node("L");
            Node nodeM = new Node("M");
            Node nodeN = new Node("N");
            Node nodeO = new Node("O");
            Node nodeP = new Node("P");
            Node nodeQ = new Node("Q");
            Node nodeR = new Node("R");
            Node nodeS = new Node("S");
            Node nodeT = new Node("T");
            Node nodeU = new Node("U");
            Node nodeV = new Node("V");
            Node nodeW = new Node("W");
            Node nodeX = new Node("X");
            Node nodeY = new Node("Y");
            Node nodeZ = new Node("Z");
            Node nodeAA = new Node("AA");

            graph.addNode(nodeA);
            graph.addNode(nodeB);
            graph.addNode(nodeC);
            graph.addNode(nodeD);
            graph.addNode(nodeE);
            graph.addNode(nodeF);
            graph.addNode(nodeG);
            graph.addNode(nodeH);
            graph.addNode(nodeI);
            graph.addNode(nodeJ);
            graph.addNode(nodeK);
            graph.addNode(nodeL);
            graph.addNode(nodeM);
            graph.addNode(nodeN);
            graph.addNode(nodeO);
            graph.addNode(nodeP);
            graph.addNode(nodeQ);
            graph.addNode(nodeR);
            graph.addNode(nodeS);
            graph.addNode(nodeT);
            graph.addNode(nodeU);
            graph.addNode(nodeV);
            graph.addNode(nodeW);
            graph.addNode(nodeX);
            graph.addNode(nodeY);
            graph.addNode(nodeZ);
            graph.addNode(nodeAA);

            // Conectar todos los nodos entre sí con distancias aleatorias
            nodeA.addDestination(nodeB, 2);
            nodeA.addDestination(nodeC, 10);
            nodeA.addDestination(nodeD, 25); // Agrega esta conexión

            nodeB.addDestination(nodeA, 5);
            nodeB.addDestination(nodeC, 7);
            nodeB.addDestination(nodeD, 20);

            nodeC.addDestination(nodeA, 10);
            nodeC.addDestination(nodeB, 7);
            nodeC.addDestination(nodeD, 8);

            nodeD.addDestination(nodeA, 15); // Agrega esta conexión
            nodeD.addDestination(nodeB, 20);
            nodeD.addDestination(nodeC, 8);


            JFrame frame = new JFrame("Grafo Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);

            JButton boton = new JButton("Calcular Distancias");
            boton.setBounds(600, 300, 150, 20);

            JTextArea areadetexto = new JTextArea();
            areadetexto.setBounds(600, 100, 50, 20);

            frame.add(areadetexto);
            frame.add(boton);

            GrafoPanel grafoPanel = new GrafoPanel(graph, new Node[]{nodeA, nodeB, nodeC, nodeD,nodeE,nodeF,nodeG,nodeH,nodeI,nodeJ,nodeK,nodeL,nodeM,nodeN,nodeO,nodeP,nodeQ,nodeR,nodeS,nodeT,nodeU,nodeV,nodeW,nodeX,nodeY,nodeZ });
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

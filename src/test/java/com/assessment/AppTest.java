package com.assessment;

import com.assessment.pojo.Graph;
import com.assessment.pojo.Node;
import com.assessment.util.DijkstraUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void whenSPPSolved_thenCorrect() {
        /*ME	Stefan:100	Amir:1042	Martin:595	Adam:10	Philipp:128
        Stefan	Amir:850	Adam:85
        Adam	Philipp:7	Martin:400	Diana:33
        Diana	Amir:57	Martin:3*/

        Node nodeA = new Node("Me");
        Node nodeB = new Node("Stefan");
        Node nodeC = new Node("Amir");
        Node nodeD = new Node("Martin");
        Node nodeE = new Node("Adam");
        Node nodeF = new Node("Philipp");
        Node nodeG = new Node("Diana");

        nodeA.addDestination(nodeB, 100);
        nodeA.addDestination(nodeC, 1042);
        nodeA.addDestination(nodeD, 595);
        nodeA.addDestination(nodeE, 10);
        nodeA.addDestination(nodeF, 128);


        nodeB.addDestination(nodeC, 850);
        nodeB.addDestination(nodeE, 85);

        nodeE.addDestination(nodeF, 7);
        nodeE.addDestination(nodeD, 400);
        nodeE.addDestination(nodeG, 33);

        nodeG.addDestination(nodeC, 57);
        nodeG.addDestination(nodeD, 3);

        Graph graph = new Graph();

        graph.addNode("A",nodeA);
        graph.addNode("B",nodeB);
        graph.addNode("C",nodeC);
        graph.addNode("D",nodeD);
        graph.addNode("E",nodeE);
        graph.addNode("F",nodeF);

        DijkstraUtil.calculateShortestPathFromSource(graph, nodeA);

        List<Node> shortestPathForNodeB = Arrays.asList(nodeA);
        List<Node> shortestPathForNodeC = Arrays.asList(nodeA);
        List<Node> shortestPathForNodeD = Arrays.asList(nodeA, nodeB);
        List<Node> shortestPathForNodeE = Arrays.asList(nodeA, nodeB, nodeD);
        List<Node> shortestPathForNodeF = Arrays.asList(nodeA, nodeB, nodeD);

        System.out.println(graph.getNodes().get("D").getDistance());
        for (Node node : graph.getNodes().values()) {
            switch (node.getName()) {
                case "B":
                    assertTrue(node
                            .getShortestPath()
                            .equals(shortestPathForNodeB));
                    break;
                case "C":
                    assertTrue(node
                            .getShortestPath()
                            .equals(shortestPathForNodeC));
                    break;
                case "D":
                    assertTrue(node
                            .getShortestPath()
                            .equals(shortestPathForNodeD));
                    break;
                case "E":
                    assertTrue(node
                            .getShortestPath()
                            .equals(shortestPathForNodeE));
                    break;
                case "F":
                    assertTrue(node
                            .getShortestPath()
                            .equals(shortestPathForNodeF));
                    break;
            }
        }
    }

}

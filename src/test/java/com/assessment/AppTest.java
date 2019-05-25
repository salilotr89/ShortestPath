package com.assessment;

import com.assessment.constant.CoreConstants;
import com.assessment.pojo.Box;
import com.assessment.pojo.Graph;
import com.assessment.pojo.Node;
import com.assessment.util.DijkstraUtil;
import com.assessment.util.VolumeUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.assessment.util.CSVUtil.fetchReader;
import static com.assessment.util.CSVUtil.readAllLines;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static List<Path> listOfFiles = new ArrayList<>();

    @BeforeAll
    public static void setup() {
        try {
            listOfFiles = Files.walk(Paths.get(CoreConstants.TEST_FOLDER_PATH))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShortestPath() throws Exception {

        Graph graph = generateGraphFromCSV(listOfFiles.get(0));


    }


    private Graph generateGraphFromCSV(Path path) throws Exception {

        List<String[]> csvContentList = readAllLines(fetchReader(path));
        Graph graph = DijkstraUtil.createGraphFromCSV(csvContentList);
        DijkstraUtil.calculateShortestPathFromSource(graph, graph.getNodes().get(CoreConstants.SOURCE_TAG));

        for (String[] data : csvContentList) {
            assertShortestPath(data, graph);
        }
        return null;
    }

    private void assertShortestPath(String[] data, Graph graph) {
        String name = data[1];
        Box box = new Box(data[2].split(CoreConstants.CSV_MULTIPLY_TOKEN));
        double volume = VolumeUtil.calculateVolumetricWeight(box,graph.getNodes().get(name));
        assertEquals(volume, Double.valueOf(data[3]));
    }

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

        graph.addNode("A", nodeA);
        graph.addNode("B", nodeB);
        graph.addNode("C", nodeC);
        graph.addNode("D", nodeD);
        graph.addNode("E", nodeE);
        graph.addNode("F", nodeF);

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

    @Test
    public void testForShortestPath() {


    }

}

package com.assessment.util;

import com.assessment.constant.CoreConstants;
import com.assessment.pojo.Graph;
import com.assessment.pojo.Node;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class DijkstraUtil {

    public static void calculateShortestPathFromSource(Graph graph, Node source) {

        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeigh = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private static void CalculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    public static Graph createGraphFromCSV(List<String[]> csvData) {

        Graph graph = new Graph();

        if (csvData != null && csvData.size() > 0) {
            for (int i=0; i<csvData.size(); i++) {

                Node primaryNode = null;
                for (String node : csvData.get(i)) {
                    if(StringUtils.isBlank(node))
                        continue;
                    if(node.equals(CoreConstants.ASSERTION_TOKEN)){
                        return graph;
                    }

                    if (primaryNode == null) {
                        primaryNode = fetchNodeFromGraph(graph, node);
                    } else {
                        Node adjacentNode = fetchNodeFromGraph(graph, fetchNodeName(node));
                        primaryNode.getAdjacentNodes().put(adjacentNode, fetchNodeHard(node));
                    }
                }
                csvData.remove(i);
                i--;
            }
            return graph;
        } else {
            return null;
        }
    }

    private static Node fetchNodeFromGraph(Graph graph, String nodeName) {
        Node node;
        if (graph.getNodes().get(nodeName) == null) {
            node = new Node(nodeName);
            graph.addNode(nodeName, node);
        } else {
            node = graph.getNodes().get(nodeName);
        }

        return node;
    }

    private static String fetchNodeName(String node) {
        return node.split(CoreConstants.CSV_SPLIT_TOKEN)[0];
    }

    private static Integer fetchNodeHard(String node) {
        return Integer.parseInt(node.split(CoreConstants.CSV_SPLIT_TOKEN)[1]);
    }
}

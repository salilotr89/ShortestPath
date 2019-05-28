package com.assessment.util;

import com.assessment.constant.CoreConstants;
import com.assessment.pojo.Graph;
import com.assessment.pojo.Node;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class DijkstraUtil {

    /**
     * Takes a graph object, traverse it and calculate the shortest distance - hard for each node
     * Source : https://www.baeldung.com/java-dijkstra
     * @param graph Graph with all the nodes
     * @param source Source node to begin with.
     */
    public static void calculateShortestPathFromSource(Graph graph, Node source) {

        //Assign the source with the initial value - ME
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        //Keeps the loop going until every node is settled.
        while (unsettledNodes.size() != 0) {
            //Fetches the node with lowest distance from the unsettled nodes.
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode); // removes it from the set

            //For every adjacent node against the current node a shortest distance is calculated, the adjacent nodes are then added to the unsettled node set.
            //Current node is marked as settled.
            for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey(); // The node itself
                Integer edgeWeigh = adjacencyPair.getValue(); // Distance from parent node

                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode); //add to unsettled node, to be evaluated in the next cycles.
                }
            }
            settledNodes.add(currentNode);
        }
    }

    /**
     * Assign a new value to the to the evaluation based on its distance from the source node.
     * If the distance on the evaluated node is greater than the source + edge distance then new distance is assigned.
     * @param evaluationNode Node whose distance is going to be modified
     * @param edgeWeigh distance from source node.
     * @param sourceNode source node, used to fetch its current hard value.
     */
    private static void CalculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
        }
    }

    /**
     * Return the node with lowest distance from the set.
     * @param unsettledNodes
     * @return
     */
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

    /**
     * Takes a list of lines read from csv, generates a graph from them and returns it.
     * @param csvData list of lines read from csv, removes the lines read, so that only test cases are left.
     * @return Graph object with nodes and their adjacent node defined.
     */
    public static Graph createGraphFromCSV(List<String[]> csvData) {

        Graph graph = new Graph();

        if (csvData != null && csvData.size() > 0) {
            for (int i=0; i<csvData.size(); i++) {
                //creates a primary node for each line
                Node primaryNode = null;
                //Every line is split into a string array, parsing it to individually to create a graph
                for (String node : csvData.get(i)) {
                    //Checking incorrect scenario
                    if(StringUtils.isBlank(node))
                        continue;
                    //returns if it encounters @ assertion token.
                    if(node.equals(CoreConstants.ASSERTION_TOKEN)){
                        return graph;
                    }

                    //creates or retrieve the node from graph
                    Node currentNode = fetchNodeFromGraph(graph, fetchNodeName(node));
                    if (primaryNode == null) {
                        primaryNode = currentNode;
                    } else {
                        Node adjacentNode = currentNode;
                        primaryNode.getAdjacentNodes().put(adjacentNode, fetchNodeHard(node));
                    }
                }
                //remove the line, the list is used to run test cases later on
                csvData.remove(i);
                i--; // roll the counter back to keep up with the removal
            }
            return graph;
        } else {
            return null;
        }
    }

    /**
     * Creates a new node or fetches the node from Graph object
     * @param graph
     * @param nodeName used to search the node from the map help in graph
     * @return node retrieved or created
     */
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

    /**
     * Splits and return the node name
     * @param node Peter:100 sample value of node
     * @return name
     */
    private static String fetchNodeName(String node) {
        return node.split(CoreConstants.CSV_SPLIT_TOKEN)[0];
    }

    /**
     * Splits and return the node hard or distance
     * @param  node Peter:100 sample value of node
     * @return hard
     */
    private static Integer fetchNodeHard(String node) {
        return Integer.parseInt(node.split(CoreConstants.CSV_SPLIT_TOKEN)[1]);
    }
}

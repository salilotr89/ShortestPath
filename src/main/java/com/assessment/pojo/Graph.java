package com.assessment.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {

    private Map<String, Node> nodes = new HashMap<>();

    public void addNode(String name, Node nodeA) {
        nodes.put(name, nodeA);
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Node> nodes) {
        this.nodes = nodes;
    }
}

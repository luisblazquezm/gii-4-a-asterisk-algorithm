/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asterisk;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author lgt12
 */
public class Graph {

    private HashMap<Integer, State> vertices;
    private SortedMap<Integer, List<Integer>> adjacencyList;

    public Graph(HashMap<Integer, State> vertices, SortedMap<Integer, List<Integer>> adjacencyList) {
        this.vertices = vertices;
        this.adjacencyList = adjacencyList;
    }

    public Graph() {
        this.vertices = new HashMap<>();
        this.adjacencyList = new TreeMap<>();
    }
    
    public State get(int vertexId) {
        return vertices.get(vertexId);
    }
    
    public boolean hasVertex(int vertexId) {
        return vertices.containsKey(vertexId);
    }
    
    public boolean isParent(int parentId, int childId) {
        if (!hasVertex(parentId) || !hasVertex(childId))
            return false;
        
        return adjacencyList.get(childId).contains(parentId);
    }
    
    public boolean isChild(int childId, int parentId) {
        if (!hasVertex(parentId) || !hasVertex(childId))
            return false;
        
        for (int id : adjacencyList.get(childId)) {
            if (id == parentId)
                return true;
        }
        
        return false;
    }
    
    public void addVertex(State vertex) {
        State newVertex = vertex;
        if (!hasVertex(vertex.getId())) {
            vertices.put(vertex.getId(), vertex);
            adjacencyList.put(vertex.getId(), new ArrayList<>());
        }
    }
    
    private void addEdge(State child, State parent) {
        if (!hasVertex(child.getId()))
            addVertex(child);
        if (!hasVertex(parent.getId()))
            addVertex(parent);
        if (!isParent(parent.getId(), child.getId())) {
            adjacencyList.get(child.getId()).add(parent.getId());
        }
    }

}

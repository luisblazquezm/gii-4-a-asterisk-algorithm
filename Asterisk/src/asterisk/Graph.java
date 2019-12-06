/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asterisk;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author lgt12
 */
public class Graph {

    private SortedMap<Integer, HashSet<Integer>> adjacencyList;

    public Graph(SortedMap<Integer, HashSet<Integer>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }
    
    public Graph() {
        this.adjacencyList = new TreeMap<>();
    }
    
    public List<Integer> getChildren(int vertexId) {
        List<Integer> children = new ArrayList<>();
        for (int i : adjacencyList.keySet())
            if (isChild(i, vertexId))
                children.add(i);
        return children;
    }
    
    public boolean hasVertex(int vertexId) {
        return adjacencyList.containsKey(vertexId);
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
    
    public void addVertex(int vertexId) {
        if (!hasVertex(vertexId)) {
            adjacencyList.put(vertexId, new HashSet<>());
        }
    }
    
    public void printSelf() {
        for (Integer vertexId : adjacencyList.keySet()) {
            System.out.println(vertexId + " --- parents ---> ");
            for (int i : adjacencyList.keySet()){
                
            }
        }
    }
    
    private void addEdge(int childId, int parentId) {
        if (!hasVertex(childId))
            addVertex(childId);
        if (!hasVertex(parentId))
            addVertex(parentId);
        if (!isParent(parentId, childId)) {
            adjacencyList.get(childId).add(parentId);
        }
    }

}

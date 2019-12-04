/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asterisk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lgt12
 */
public class Graph<T> {

    private HashMap<GraphNode<T>, HashSet<GraphNode<T>>> adjacencyMap;
    private int numVertices;
    private int numEdges;

    public Graph() {
        this.adjacencyMap = new HashMap<>();
    }

    /**
    * Adds a vertex to the graph.
    * @param vertex vertex to add.
    * @return true if vertex was added successfully, false if otherwise.
    * @
    */
    public boolean addVertex(GraphNode<T> vertex) {
        if(!this.adjacencyMap.containsKey(vertex)) {
            this.adjacencyMap.put(vertex, new HashSet<>());
            this.numVertices++;
            return true;
        }
        return false;
    }

    /**
    * Removes a specified vertex from the graph.
    * @param vertex vertex to remove.
    * @return true if vertex was removed successfully, false if otherwise.
    * @
    */
    public boolean removeVertex(GraphNode<T> vertex) {
        if(this.adjacencyMap.containsKey(vertex)) {
            this.adjacencyMap.remove(vertex);
            for(Map.Entry<GraphNode<T>, HashSet<GraphNode<T>>> entry : this.adjacencyMap.entrySet()) {
                if(entry.getValue().contains(vertex)) {
                    this.adjacencyMap.get(entry.getKey()).remove(vertex);
                }
            }
            this.numVertices--;
            return true;
        }
        return false;
    }

    /**
    * Adds an edge between two vertices to the graph.
    * @param x source vertex of edge to add.
    * @param y destination vertex of edge to add.
    * @return true if the edge was added successfully, false if otherwise.
    * @
    */
    public boolean addEdge(GraphNode<T> x, GraphNode<T> y) {
        if(this.adjacencyMap.containsKey(x)) {
            if(!this.adjacencyMap.get(x).contains(y)) {
                this.adjacencyMap.get(x).add(y);
                this.numEdges++;
                return true;
            }
        }
        return false;
    }

    /**
    * Removes a specified edge between two vertices from the graph, if it already exists.
    * @param x source vertex of edge to remove.
    * @param y destination vertex of edge to remove.
    * @return true if the edge was removed successfully, false if otherwise.
    * @
    */
    public boolean removeEdge(GraphNode<T> x, GraphNode<T> y) {
        if(this.adjacencyMap.containsKey(x)) {
            if(this.adjacencyMap.get(x).contains(y)) {
                this.adjacencyMap.get(x).remove(y);
                this.numEdges--;
                return true;
            }
        }
        return false;
    }

    /**
    * Determines if two vertices are adjacent (or, if an edge exists between them).
    * @param x source vertex.
    * @param y destination vertex.
    * @return true if both vertices are adjacent, false if otherwise.
    * @
    */
    public boolean isAdjacent(GraphNode<T> x, GraphNode<T> y) {
        HashSet<GraphNode<T>> adjacencySet = this.adjacencyMap.get(x);
        if(adjacencySet != null) {
            if(adjacencySet.contains(y)) {
                return true;
            }
        }
        return false;
    }

    /**
    * Determines if graph contains a given vertex or not.
    * @param vertex vertex to search.
    * @return true if the graph contains the vertex, false if otherwise.
    * @
    */
    public boolean containsVertex(GraphNode<T> vertex) {
        if(this.adjacencyMap.containsKey(vertex)) {
            return true;
        }
        return false;
    }

    /**
    * Returns a HashSet containing all neighbors of a given vertex (or, all vertices with which the vertex shares an edge).
    * @param vertex vertex to search.
    * @return a HashSet containing all neighbors of the vertex.
    * @
    */
    public HashSet<GraphNode<T>> getNeighbors(GraphNode<T> vertex) {
        return this.adjacencyMap.get(vertex);
    }

    /**
    * Determines whether or not a path exists between two nodes, using Depth-First Search.
    * Uses a wrapper method to initialize objects required for search traversal.
    * @param source source node to be used in search.
    * @param destination destination node to be used in search.
    * @return true if a path exists between source and destination nodes, false if otherwise.
    * @
    */
    public boolean depthFirstSearch(GraphNode<T> source, GraphNode<T> destination) {
        if(!this.adjacencyMap.containsKey(source) || !this.adjacencyMap.containsKey(destination)) {
            return false;
        }
        Stack<GraphNode<T>> stack = new Stack<>();
        stack.push(source);
        return depthFirstSearch(stack, destination);
    }
    private boolean depthFirstSearch(Stack<GraphNode<T>> stack, GraphNode<T> destination) {
        HashMap<GraphNode<T>, VisitStatus> visited = new HashMap<>();
        while(!stack.isEmpty()) {
            GraphNode<T> current = stack.pop();
            visited.put(current, VisitStatus.VISITING);
            if(current.equals(destination)) {
                return true;
            }
            for(GraphNode<T> neighbor : this.adjacencyMap.get(current)) {
                if(visited.containsKey(neighbor)) {
                    if(visited.get(neighbor).equals(VisitStatus.UNVISITED)) {
                        stack.push(neighbor);
                    }
                } else {
                    stack.push(neighbor);
                }
            }
            visited.put(current, VisitStatus.VISITED);
        }
        return false;
    }

    /**
    * Determines whether or not a path exists between two nodes, using Breadth-First Search.
    * Uses a wrapper method to initialize objects required for search traversal.
    * @param source source node to be used in search.
    * @param destination destination node to be used in search.
    * @return true if a path exists between source and destination nodes, false if otherwise.
    * @
    */
    public boolean breadthFirstSearch(GraphNode<T> source, GraphNode<T> destination) {
        if(!this.adjacencyMap.containsKey(source) || !this.adjacencyMap.containsKey(destination)) {
            return false;
        }
        LinkedList<GraphNode<T>> queue = new LinkedList<>();
        queue.addLast(source);
        return breadthFirstSearch(queue, destination);
    }
    private boolean breadthFirstSearch(LinkedList<GraphNode<T>> queue, GraphNode<T> destination) {
        HashMap<GraphNode<T>, VisitStatus> visited = new HashMap<>();
        while(!queue.isEmpty()) {
            GraphNode<T> current = queue.removeFirst();
            visited.put(current, VisitStatus.VISITING);
            if(current.equals(destination)) {
                return true;
            }
            for(GraphNode<T> neighbor : this.adjacencyMap.get(current)) {
                if(visited.containsKey(neighbor)) {
                    if(visited.get(neighbor).equals(VisitStatus.UNVISITED)) {
                        queue.addLast(neighbor);
                    }
                } else {
                    queue.addLast(neighbor);
                }
            }
            visited.put(current, VisitStatus.VISITED);
        }
        return false;
    }

    /**
    * Returns the number of vertices within the graph.
    * @return an integer representing number of vertices contained within the graph.
    * @
    */
    public int getNumVertices() {
        return this.numVertices;
    }

    /**
    * Returns the number of edges within the graph.
    * @return an integer representing number of edges contained within the graph.
    * @
    */
    public int getNumEdges() {
        return this.numEdges;
    }
    
    /**
    * Determines whether or not a path exists between two nodes, using Depth-First Search.
    * Uses a wrapper method to initialize objects required for search traversal.
    * @param source source node to be used in search.
    * @param destination destination node to be used in search.
    * @return true if a path exists between source and destination nodes, false if otherwise.
    * @
    */
    public void depthFirstPrintPath(GraphNode<State> destination) {
        List<GraphNode> path = new ArrayList<>();
        if(!this.adjacencyMap.containsKey(destination)) {
            return ;
        }
        
        Util.printStorageHouseState(destination.getData().getStorageHouse());
        
        HashSet<GraphNode<T>> nodesConnected = this.adjacencyMap.get(destination);
        while(nodesConnected.iterator().hasNext()){
            
            GraphNode<State> antecesor = (GraphNode<State>) nodesConnected.iterator().next();
            Util.printStorageHouseState(antecesor.getData().getStorageHouse());
            
            nodesConnected = this.adjacencyMap.get(antecesor);
        }
    }

}

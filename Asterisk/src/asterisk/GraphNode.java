/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asterisk;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lgt12
 */
public class GraphNode<T> implements Comparable<GraphNode> {

    private T data;
    private int nodeID;
    private int evalFunction;
    private GraphNode<T> antecesor;

    public GraphNode(T data,
                     int nodeID,
                     int evalFunction,
                     GraphNode<T> antecesor) {

        this.data = data;
        this.nodeID = nodeID;
        this.evalFunction = evalFunction;
        this.antecesor = antecesor;
        
    }
    
    public GraphNode(GraphNode<T> graphNode, boolean clone) {
        this.data = graphNode.data;
    }
    
    public GraphNode() {}
    
    public GraphNode(GraphNode<T> graphNode) {
        this.data = graphNode.data;
    }

    public GraphNode(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getNodeID() {
        return this.nodeID;
    }

    public void setNodeID(int oldNodeID) {
        this.nodeID = oldNodeID + 1;
    }
    
    public int getEvalFunction() {
        return this.evalFunction;
    }

    public void setEvalFunction(int evalFunction) {
        this.evalFunction = evalFunction;
    }
    
    @Override
    public int compareTo(GraphNode n) {
        return this.evalFunction - n.evalFunction;
    }

    /**
     * @return the antecesor
     */
    public GraphNode<T> getAntecesor() {
        return antecesor;
    }

    /**
     * @param antecesor the antecesor to set
     */
    public void setAntecesor(GraphNode<T> antecesor) {
        this.antecesor = antecesor;
    }
    
}

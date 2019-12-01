/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asterisk;

/**
 *
 * @author lgt12
 */
public class GraphNode<T> implements Comparable<GraphNode> {

    private T data;
    private int nodeID;
    private int evalFunction;

    public GraphNode() {}

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
}

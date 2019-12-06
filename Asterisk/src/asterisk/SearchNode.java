/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asterisk;

/**
 *
 * @author i0910465
 */
public class SearchNode implements Comparable<SearchNode> {
    
    private int stateId;
    private int bestPathParent;
    private int heuristicMerit;

    public SearchNode(int stateId, int bestPathParent, int heuristicMerit) {
        this.stateId = stateId;
        this.bestPathParent = bestPathParent;
        this.heuristicMerit = heuristicMerit;
    }
    
    public SearchNode(int stateId) {
        this.stateId = stateId;
        this.bestPathParent = 0;
        this.heuristicMerit = 0;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getBestPathParent() {
        return bestPathParent;
    }

    public void setBestPathParent(int bestPathParent) {
        this.bestPathParent = bestPathParent;
    }

    public int getHeuristicMerit() {
        return heuristicMerit;
    }

    public void setHeuristicMerit(int heuristicMerit) {
        this.heuristicMerit = heuristicMerit;
    }

    @Override
    public int compareTo(SearchNode node) {
        return this.heuristicMerit - node.heuristicMerit;
    }
}

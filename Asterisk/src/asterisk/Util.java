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
 * @author i0910465
 */
public class Util {
    
    public static void heuristicOrder(List<GraphNode> list) throws Exception
    {
        throw new Exception("heuristicOrder: Not implemented yet.");
    }
    
    public static boolean isGoalState(State node){
        return node.getProductionList().isEmpty();
    }
    
    public static List<State> expand(State currentNode)
    {
        return expand(currentNode, true);
    }
    
    private static List<State> expand(State currentNode,
                                         boolean checkLoops)
    {
        
        List<State> successors = new ArrayList<>();
        
        List<Box> newProductionList =
                new ArrayList<>(currentNode.getProductionList());
        Box currentBox = newProductionList.remove(0);
        
        for (int i = 0; i < 5; ++i){
            StorageHouse newStorageHouse =
                    new StorageHouse(currentNode.getStorageHouse());
            newStorageHouse.addBox(currentBox, i);
            
            State nextNode = new State(
                            newProductionList,
                            newStorageHouse
                    );
            
            boolean shouldAdd = true;
            if (checkLoops){
                List<State> nextNodeSuccessors = expand(nextNode, false);
                for (State n : nextNodeSuccessors){
                    if (n.equals(currentNode))
                        shouldAdd = false;
                        break;
                }
            }
            if (shouldAdd) successors.add(nextNode);
        }
        
        return successors;
    }
    
}

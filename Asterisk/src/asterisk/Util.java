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
    
    public static boolean isGoalStateNode(StateNode node){
        return node.getProductionList().isEmpty();
    }
    
    public static List<StateNode> expand(
            StateNode currentNode,
            boolean checkLoops)
    {
        
        List<StateNode> successors = new ArrayList<>();
        
        List<Box> newProductionList =
                new ArrayList<>(currentNode.getProductionList());
        Box currentBox = newProductionList.remove(0);
        
        for (int i = 0; i < 5; ++i){
            StorageHouse newStorageHouse =
                    new StorageHouse(currentNode.getStorageHouse());
            newStorageHouse.addBox(currentBox, i);
            
            StateNode nextNode = new StateNode(
                            newProductionList,
                            newStorageHouse
                    );
            
            boolean shouldAdd = true;
            if (checkLoops){
                List<StateNode> nextNodeSuccessors = expand(nextNode, false);
                for (StateNode n : nextNodeSuccessors){
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asterisk;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author i0910465
 */
public class Util {
    
    public static void updateBestPathParent(Graph graph,
                                            int stateId,
                                            State newParent,
                                            List<SearchNode> list,
                                            boolean updateChildren)
    {
        SearchNode node = null;
        for (SearchNode n : list)
            if (n.getStateId() == stateId)
                node = n;
        if (node == null)  // Node not in list
            return;
        
        State oldParentState = State.getState(node.getStateId());
        int oldParentCost = Util.getStateCost(oldParentState);
        int newParentCost = Util.getStateCost(newParent);
        if (newParentCost < oldParentCost) {
            node.setBestPathParent(newParent.getId());
        }
        
        if (updateChildren) {
            for (int childId : graph.getChildren(stateId)) {
                updateBestPathParent(graph,
                                     childId,
                                     State.getState(stateId),
                                     list,
                                     false);
            }
        }
            
    }
    
    public static int getStateCost(State state) {
        
        int nodeCost = 0;
        for (Stack stack : state.getStorageHouse().getStacks()) {
            for (Box box : stack.getStack()) {
                nodeCost += box.getDepartureDate();
            }
        }
        
        return -1 * nodeCost;
    }
    
    public static void heuristicOrder(List<SearchNode> open)
    {
        // - (number of holes in stack + add(departureDate of inserted boxes))
        // Get the least (most negative)
        // h = number of holes in stack (info about the problem)
        // g = add(departureDate of inserted boxes)
        // f = g + h (value)
        
        // h
        int numberGaps = 0;
                
        // g
        int nodeCost = 0;
                
        // f
        int heuristicMerit = 0;
        
        for (SearchNode n : open){
            State currentState = State.getState(n.getStateId());
            for(Stack stack : currentState.getStorageHouse().getStacks()){
                numberGaps += StorageHouse.STACK_SIZE - stack.size();
                for (Box b : stack.getStack()){
                    nodeCost += b.getDepartureDate();
                }
            }
            //f = - (h + g);
            // Factor de 5 para priorizar las fechas de salida
            heuristicMerit = -(numberGaps/5 + (nodeCost/30)*10);
            n.setHeuristicMerit(heuristicMerit);
            
            // Very important to reset values
            numberGaps = 0;
            nodeCost = 0;
        }
        
        //Util.printStateOpen(open, "BEFORE"); //DEBUG
        Collections.sort(open);
        //Util.printStateOpen(open, "AFTER"); //DEBUG
               
    }
    
    public static boolean isGoalState(State node){
        return node.getProductionList().isEmpty() && 
               node.getStorageHouse().fullStacks();
    }
    
    public static List<State> expand(State currentState)
    {
        return expand(currentState, true);
    }
    
    private static List<State> expand(State currentState,
                                      boolean checkLoops)
    {
        List<State> successors = new ArrayList<>();
                
        for (int boxIndex = 0;
             boxIndex < currentState.getProductionList().size();
             boxIndex++){
            
            List<Box> newProductionList = currentState.cloneProductionList();
            Box currentBox = newProductionList.remove(boxIndex);
            
            // ITerate over stacks
            for (int stackIndex = 0;
                 stackIndex < currentState.getStorageHouse().NUMBER_OF_STACKS;
                 ++stackIndex){  
                
                // Clone state of storageHouse
                StorageHouse newStorageHouse = currentState.cloneStorageHouse();
                // Add new box in stack i
                newStorageHouse.addBox(currentBox, stackIndex);

                State nextState = new State(newProductionList, newStorageHouse);

                boolean shouldAdd = true;

                if (checkLoops){
                    List<State> grandChildren = expand(nextState, false);
                    for (State grandChild : grandChildren){
                        if (grandChild.getStorageHouse().equals(currentState.getStorageHouse())){
                            shouldAdd = false;
                            break;

                        }
                    }
                }
                
                // Check if nodes are not the same state
                if (shouldAdd && !(nextState.getStorageHouse().equals(currentState.cloneStorageHouse())))
                    successors.add(nextState);
                
            } // For stackIndex
        } // For boxIndex

        return successors;
    }

    public static void printList(List<SearchNode> list, String listName) {
        System.out.print(listName + ": [ ");
        for(SearchNode n : list){
            System.out.print(n.getStateId() + ", ");
        }
        
        System.out.print("]\n");
    }
/*
    private static void printStateOpen(List<GraphNode> open, String state) {
        System.out.print(state + " HEURISTIC OPEN LIST CONTENT: [ ");
        for(GraphNode<State> n : open){
            System.out.print("( ");
            System.out.print("id: " + n.getNodeID() + ", f(n) = " + n.getEvalFunction());
            System.out.print(" )");
        }
        
        System.out.print("] \n");
    }
    
    private static void printProductionList(List<Box> productionList, String productionListName, int level){
        int j = 1;
        System.out.print("\n\n" + "( LEVEL " + level +" ) " + productionListName + ": [\n");
        for (Box b : productionList){
            System.out.printf("( ID: " + b.getId() + ", Departure: " + b.getDepartureDate() + " ), ");
            j++;
            if (j >= 3){
                j = 0;
                System.out.print("\n");
            }
        }
        System.out.print("\n]\n\n");
    }
*/
    public static boolean invalidStack(Stack stack, Box newBoxToAdd) {
        Box stackUpperBox = stack.getStack().get(0);
        
        if (stackUpperBox.getDepartureDate() >= newBoxToAdd.getDepartureDate())
            return false;
        else
            return true;
    }
  /*  
    public static boolean compareNodes(State h1, State h2) {
        return h1.getStorageHouse().equals(h2.getStorageHouse());
    }

    public static void printStorageHouseState(StorageHouse storageHouse) {
        
        System.out.println("\n");
        
        int j = 1;
        int i = 1;
        for (Stack s : storageHouse.getStacks()){
            System.out.printf("Stack " + j + " = [ ");
            for (Box b : s.getStack()){
                if (i != 4) System.out.printf(b.getDepartureDate() + ", ");
                else System.out.printf(String.format("%d", b.getDepartureDate()));
                i++;
            }
            i = 1;
            System.out.printf(" ]\n");
            j++;
        }
        
        System.out.println("\n");
    }

    public static int getEvalFunctionValue(GraphNode<State> node) {
        
        int h = 0, g = 0, f = 0;
        
        for(Stack stack : node.getData().getStorageHouse().getStacks()){
            h += StorageHouse.STACK_SIZE - stack.size();
            for (Box b : stack.getStack()){
                g += b.getDepartureDate();
            }
        }
        f = - (h + g);
        
        return f;
    }*/
    
}

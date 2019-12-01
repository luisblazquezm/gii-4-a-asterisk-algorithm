/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asterisk;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author i0910465
 */
public class Util {
    
    public static void heuristicOrder(List<GraphNode> open)
    {
        // - (number of holes in stack + add(departureDate of inserted boxes))
        // Get the least (most negative)
        // h = number of holes in stack (info about the problem)
        // g = add(departureDate of inserted boxes)
        // f = g + h (value)
        
        int h = 0, g = 0, f = 0;
        
        for (GraphNode<State> n : open){
            for(Stack stack : n.getData().getStorageHouse().getStacks()){
                h += StorageHouse.STACK_SIZE - stack.size();
                for (Box b : stack.getStack()){
                    g += b.getDepartureDate();
                }
            }
            f = - (h + g);
            n.setEvalFunction(f);
            
            // Very important to reset values
            h = 0;
            g = 0;
        }
        
        Util.printStateOpen(open, "BEFORE"); //DEBUG
        Collections.sort(open);
        Util.printStateOpen(open, "AFTER"); //DEBUG
               
    }
    
    public static boolean isGoalState(State node){
        return node.getProductionList().isEmpty();
    }
    
    public static List<State> expand(State currentNode) throws CloneNotSupportedException
    {
        return expand(currentNode, 1, true);
    }
    
    private static List<State> expand(State currentNode,
                                         int recursivityLevel,
                                         boolean checkLoops) throws CloneNotSupportedException
    {
        int j = 1;
        List<State> successors = new ArrayList<>();
        
        List<Box> newProductionList = new ArrayList<>();
        
        // Copy the old production list into the new one we are going to test
        try {
            newProductionList.addAll(currentNode.getProductionList());
            Util.printProductionList(currentNode.getProductionList(), "NEW_PRODUCTION_LIST", recursivityLevel);
        } catch (Exception ex) {
            Logger.getLogger(Asterisk.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Box currentBox = newProductionList.remove(0);
        System.out.println("Remove(0) from New Production List returns = " + currentBox.getId());
        
        // ITerate over stacks
        for (int i = 0; i < 5; ++i){
            StorageHouse newStorageHouse = (StorageHouse) currentNode.getStorageHouse().clone();
            newStorageHouse.addBox(currentBox, i); // Add new box in stack i
            
            /* DEBUG */
            System.out.println("(NODE) STORAGEHOUSE INTENTO EN NIVEL " + recursivityLevel + " metiendo caja en la pila " + (i + 1));
            for (Stack s : newStorageHouse.getStacks()){
                System.out.printf("Stack " + j + " = [ ");
                for (Box b : s.getStack())
                    System.out.printf(b.getDepartureDate() + ", ");
                System.out.printf(" ]\n");
                j++;
            }
            j = 1;
            /* DEBUG */
            
            State nextNode = new State(
                            newProductionList,
                            newStorageHouse
                    );
           
            boolean shouldAdd = true;
            
            if (checkLoops){
                List<State> nextNodeSuccessors = expand(nextNode, recursivityLevel+1, false);
                for (State n : nextNodeSuccessors){
                    if (n.getStorageHouse().equals(currentNode.getStorageHouse())){
                        System.out.println("FOUND LOOP. BREAKING ... ");
                        shouldAdd = false;
                        break;
                    }
                }
            }
            
            if (shouldAdd){
                /*DEBUG
                System.out.println("\n---------------> Going to add nextNode in level " + recursivityLevel + "." + i); //DEBUG
                
                System.out.println("With StorageHouse:");
                for (Stack s : nextNode.getStorageHouse().getStacks()){
                    System.out.printf("Stack " + j + " = [ ");
                    for (Box b : s.getStack())
                        System.out.printf(b.getDepartureDate() + ", ");
                    System.out.printf(" ]\n");
                    j++;
                }
                j = 1;
                System.out.println("\n");
                DEBUG*/
                successors.add(nextNode);
            }
            
        }
        
        return successors;
    }

    public static void printLists(List<GraphNode> list, String listName) {
        int i = 1;
        int j = 1;
        
        System.out.print(" CONTENT: [ ");
        for(GraphNode<State> n : list){
            System.out.print(n.getNodeID() + ", ");
        }
        
        System.out.print("] \n");
        
        for(GraphNode<State> n : list){
            System.out.println("\n\nNODE " + n.getNodeID() + " from " + listName);
            i++;
            
            // Print productionList
            Util.printProductionList(n.getData().getProductionList(), "PRODUCTION_LIST", 0);
            
            // Print status of the storageHouse
            if (listName.equals("OPEN"))
                System.out.print(n.getData().getStorageHouse().toString());
        }
    }

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

    public static boolean invalidStack(Stack stack, Box newBoxToAdd) {
        Box stackUpperBox = stack.getStack().get(0);
        
        if (stackUpperBox.getDepartureDate() >= newBoxToAdd.getDepartureDate())
            return false;
        else
            return true;
    }
    
}

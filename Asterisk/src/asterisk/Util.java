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
            //f = - (h/5 + (g/30)*5); // Factor de 5 para priorizar las fechas de salida
            n.setEvalFunction(f);
            
            // Very important to reset values
            h = 0;
            g = 0;
        }
        
        //Util.printStateOpen(open, "BEFORE"); //DEBUG
        Collections.sort(open);
        //Util.printStateOpen(open, "AFTER"); //DEBUG
               
    }
    
    public static boolean isGoalState(State node){
        return node.getProductionList().isEmpty() && 
               node.getStorageHouse().fullStacks();
    }
    
    public static List<State> expand(State currentNode) throws CloneNotSupportedException
    {
        return expand(currentNode, 1, true);
    }
    
    private static List<State> expand(State currentNode,
                                         int recursivityLevel,
                                         boolean checkLoops) throws CloneNotSupportedException
    {
        List<State> successors = new ArrayList<>();
        List<Box> newProductionList = null;
                
        for (int b = 0; b < currentNode.getProductionList().size(); b++){
            
            // Copy the old production list into the new one we are going to test
            try {
                newProductionList = new ArrayList<>();
                newProductionList.addAll(currentNode.getProductionList());
                //Util.printProductionList(currentNode.getProductionList(), "NEW_PRODUCTION_LIST", recursivityLevel); //DEBUG
            } catch (Exception ex) {
                Logger.getLogger(Asterisk.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (newProductionList.isEmpty()) return Collections.emptyList();
            Box currentBox = newProductionList.remove(b);
            
            // ITerate over stacks
            for (int i = 0; i < currentNode.getStorageHouse().NUMBER_OF_STACKS; ++i){  
                
                // Clone state of storageHouse
                StorageHouse newStorageHouse = (StorageHouse) currentNode.getStorageHouse().clone();
                
                // Add new box in stack i
                newStorageHouse.addBox(currentBox, i); 

                /* DEBUG 
                System.out.println("(NODE) STORAGEHOUSE INTENTO EN NIVEL " + recursivityLevel + " metiendo caja en la pila " + (i + 1));
                Util.printStorageHouseState(newStorageHouse); //DEBUG
                DEBUG */

                State nextNode = new State(
                                newProductionList,
                                newStorageHouse
                        );

                boolean shouldAdd = true;

                if (checkLoops){
                    List<State> nextNodeSuccessors = expand(nextNode, recursivityLevel+1, false);
                    for (State n : nextNodeSuccessors){
                        if (Util.compareNodes(n, currentNode)){
                            //n.setValid(false);
                            //System.out.println("->>>>>>>>>>>>>>>>>> FOUND LOOP. BREAKING ... ");
                            shouldAdd = false;
                            break;

                        }
                    }
                    /*
                    if(Util.allSuccessorsInvalid(nextNodeSuccessors))
                        currentNode.setValid(false);
                    */
                }


                // Check if nodes are not the same state
                if (shouldAdd && !Util.compareNodes(nextNode, currentNode))
                    successors.add(nextNode);
                
            } // For i
        } // For b

        /* DEBUG 
        System.out.println("\nNumber of succesors in LEVEL " + recursivityLevel + ": " + successors.size());
        System.out.println("\n\nLIST OF SUCCESORS"); 
        System.out.println("--------------------------------------------------------"); 
        for (State s: successors){
            Util.printStorageHouseState(s.getStorageHouse()); //DEBUG
            System.out.println();
        }
        System.out.println("--------------------------------------------------------"); 
        System.out.println("\n\n"); 
        DEBUG */

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
    
}

package asterisk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Asterisk {
    
    public static void main(String[] args) throws CloneNotSupportedException
    {        
        /*
         * ====================================================================
         * STATE INFORMATION
         * ====================================================================
         */
        
        State initialState = new State(
            new ArrayList<>(
                    Arrays.asList(
                            new Box(9,1,17),
                            new Box(10,1,17),
                            new Box(11,1,17),
                            new Box(12,1,17),
                            new Box(13,1,17),
                            new Box(14,1,17),
                            new Box(15,1,17),
                            new Box(16,1,17),
                            new Box(17,1,17),
                            new Box(18,1,17),
                            new Box(19,1,17),
                            new Box(20,1,17),
                            new Box(3,1,18),
                            new Box(4,1,18),
                            new Box(5,1,18),
                            new Box(6,1,18),
                            new Box(7,1,18),
                            new Box(8,1,18),
                            new Box(1,1,19),
                            new Box(2,1,19)
                    )
            ),
            new StorageHouse()
        );
        
        
        /*
        State initialState = new State(
            new ArrayList<>(
                    Arrays.asList(
                            new Box(9,1,17),
                            new Box(10,1,17),
                            new Box(11,1,17),
                            new Box(3,1,18),
                            new Box(6,1,18),
                            new Box(7,1,18),
                            new Box(8,1,18),
                            new Box(2,1,19)
                    )
            ),
            new StorageHouse()
        );
        */
        
        /**
         * ====================================================================
         * ALGORITHM
         * ====================================================================
         * 
         *     1. Create GRAPH and OPEN list containing initial node 's'
         *     2. Create empty list: CLOSED
         *     3. Loop
         *         3.1. If OPEN is empty, EXIT and FAILURE
         *         3.2. Move first node 'n' from OPEN to CLOSED
         *         3.3. If 'n' is GOAL, EXIT and SUCCESS
         *             3.3.1. Return solution as an ordered list of states,
         *                    from 'n' to 's'.
         *         3.4. Expand 'n' generating children without loops set M, 
         *              and add M to GRAPH.
         *         3.5. Create pointer from 'm' to 'n', should 'm' belong to 
         *              M' = M - (OPEN U CLOSED) (elements of M not contained
         *              in OPEN or CLOSED).
         *         3.6. Add M' to OPEN
         *         3.7. For each 'm' in M' contained in OPEN or CLOSED
         *             3.7.1. If [CONDITION MUST BE SELECTED] change pointers
         *                    to 'n'.
         *                 3.7.1.1. If 'm' contained in CLOSED, for each child
         *                          of 'm' in GRAPH, change pointers if
         *                          [CONDITION MUST BE SELECTED]
         *         3.8. Reorder OPEN list according to heuristic function
         */
        
        Graph<State> graph = new Graph<>();
        List<GraphNode> closed = new ArrayList<>();
        List<GraphNode> open = new ArrayList<>();
        int countNodeID = 0; //DEBUG
        int iterationNum = 1; //DEBUG
        
        /* Initialize open with the first node of G */
        GraphNode<State> initialStateNode = new GraphNode<>(initialState);
        initialStateNode.setNodeID(countNodeID++); // DEBUG just to have a count of the ID of the nodes
        graph.addVertex(initialStateNode);
        open.add(initialStateNode);
        
        // ====================================================================
        // MAIN LOOP
        // ====================================================================

        boolean success = false;
        while(true){
            
            /* DEBUG */
            System.out.println("\n\n\t\t\t\t\t\t--------------------------------------");
            System.out.println("\t\t\t\t\t\t|       ITERATION " + iterationNum + "                  |");
            System.out.println("\t\t\t\t\t\t--------------------------------------");
            System.out.println("Looping with open.size: " + open.size());
            System.out.println("====================================================");
            System.out.println("-----------------     OPEN    ----------------- ");
            System.out.println("====================================================");
            /* DEBUG */
            
            // 3.1
            // If open is empty, FAILURE
            if (open.isEmpty()) {
                success = false;
                break;
            }
            
            // 3.2
            // Get next node from open list (nextNode = n)
            GraphNode<State> nextNode = open.remove(0);
            closed.add(0, nextNode);
            
            //DEBUG
            for(GraphNode<State> n : open){
                System.out.print(n.getNodeID() + ", ");
            }
            
            /* DEBUG */
            System.out.println("\n\n====================================================");
            System.out.println("-----------------     CLOSED    ----------------- ");  
            System.out.println("====================================================");
            /* DEBUG */

            //DEBUG
            for(GraphNode<State> n : closed){
                System.out.print(n.getNodeID() + ", ");
            }
            
            /* DEBUG */
            System.out.println("\n\n================================================================================");
            System.out.println(String.format("-----------------     ACTUAL STATE OF STORAGEHOUSE: NODE (%d)  ----------------- ", nextNode.getNodeID())); 
            System.out.println("================================================================================");
            System.out.print(nextNode.getData().getStorageHouse().toString());//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< KEEP THIS. DO NOT REMOVE. NOT DEBUG
            System.out.printf("\n\n");
            /* DEBUG */
            
            // 3.3
            // If next node is goal, SUCCESS
            if (Util.isGoalState(nextNode.getData())){ 
                success = true;
                break;
            }
            
            // 3.4
            // Expand node and add successors to graph (successors = M)
            List<State> successors = Util.expand(nextNode.getData());
            
            for (State s : successors){
                
                // Add to graph G
                GraphNode successorNode = new GraphNode<>(s);
                successorNode.setNodeID(countNodeID++);
                graph.addVertex(successorNode);
                
                /* DEBUG */
                System.out.println("\n\n===================================================="); 
                System.out.println("SUCCESSOR: " + successorNode.getNodeID() + " of FATHER: " + nextNode.getNodeID() + "\n");
                System.out.println("====================================================\n"); 
                /* DEBUG */
                
                // Create pointers from nextNode to every successor that was not
                // in open or closed
                String alreadyContains = "NONE";
                
                // Check if contained in open
                // Loops needed because cannot compare either State to
                // GraphNode nor pointers one to each other
                System.out.println("\n\nLet´s see if succesor is in OPEN"); //DEBUG
                for (GraphNode<State> n : open){
                    if (Util.compareNodes(n.getData(), s)){
                        System.out.println("IT IS IN OPEN\n"); //DEBUG
                        alreadyContains = "OPEN";
                        break;
                    }
                }
                
                System.out.println("\n\nLet´s see if succesor is in CLOSED\n"); //DEBUG
                if (alreadyContains.equals("NONE")){
                    for (GraphNode<State> n : closed){
                        System.out.println("\nTrying with node: " + n.getNodeID() +"\n"); //DEBUG
                        if (Util.compareNodes(n.getData(), s)){
                            System.out.println("IT IS IN CLOSED\n"); //DEBUG
                            alreadyContains = "CLOSED";
                            break;
                        }
                    }
                }
                
                // If it was not contained in open nor closed, create edges
                // from nextNode to s in graph
                if (alreadyContains.equals("NONE")) {
                    
                    // 3.5
                    // Create edge (here succesorNode is m')
                    graph.addEdge(successorNode, nextNode);
                    
                    // 3.6
                    // Use this loop to add to open as well
                    open.add(successorNode);
                    
                } else {
                    // 1 - Decide if pointers should be modified (-> nextNode)
                    // 2 - Decided if pointers of children of elements
                    //     contained in closed should be modified as well
                    
                    System.out.println("TREAT SUCCESOR in " + alreadyContains); //DEBUG
                    if (alreadyContains.equals("CLOSED")){
                        // Do point 2
                        System.out.println("Remove node " + nextNode.getNodeID() + " from CLOSE"); //DEBUG
                        closed.remove(nextNode); ////////////////////////// Check if returns -1 or not
                        continue;
                    } 
                }
                 
            }//End of for
            
            // 3.8 Sort the Open list in order applying the heuristic evaluation function
            try {
                System.out.println("Apply heuristic");
                Util.heuristicOrder(open);  // Complains because of Exception
            } catch (Exception ex) {
                Logger.getLogger(Asterisk.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            iterationNum++; //DEBUG
            
        }// End of while
        
        if (success){
            // Return solution, SUCCESS
            System.out.println("SUCCESS");
        } else {
            // Return FALIURE
            System.out.println("FAILURE");
        }

    }
}

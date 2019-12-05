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
        
        Graph graph = new Graph();
        List<SearchNode> closed = new ArrayList<>();
        List<SearchNode> open = new ArrayList<>();
        
        /* Initialize open with the first node of G */
        graph.addVertex(initialState);
        open.add(new SearchNode(initialState.getId()));
        
        // ====================================================================
        // MAIN LOOP
        // ====================================================================

        boolean solutionFound = false;
        SearchNode goalNode = null;
        while(true){
            
            // 3.1
            // If open is empty, FAILURE
            if (open.isEmpty()) {
                solutionFound = false;
                break;
            }
            
            // 3.2
            // Get next node from open list (nextNode = n)
            SearchNode currentNode = open.remove(0);
            State currentState = graph.get(currentNode.getStateId());
            closed.add(0, currentNode);
            
            // 3.3
            // If next node is goal, SUCCESS
            if (Util.isGoalState(currentState)){ 
                goalNode = currentNode;
                solutionFound = true;
                break;
            }
            
            // 3.4
            // Expand node and add successors to graph (successors = M)
            List<State> children = Util.expand(currentState);
            
            for (State child : children){
                
                // Add to graph G
                graph.addVertex(child);
                
                // Create pointers from nextNode to every successor that was not
                // in open or closed
                String alreadyContains = "NONE";
                
                // Check if contained in open
                // Loops needed because cannot compare either State to
                // GraphNode nor pointers one to each other
                //System.out.println("\n\nLet´s see if succesor is in OPEN"); //DEBUG
                for (SearchNode n : open){
                    if (n.getStateId() == child.getId()){
                        alreadyContains = "OPEN";
                        break;
                    }
                }
                
                for (SearchNode n : closed){
                    if (n.getStateId() == child.getId()){
                        alreadyContains = "OPEN";
                        break;
                    }
                }
                
                // If it was not contained in open nor closed, create edges
                // from nextNode to s in graph
                if (alreadyContains.equals("NONE")) {
                    
                } else if (alreadyContains.equals("OPEN")) {
                    
                } else if (alreadyContains.equals("CLOSED")){
                    
                }
                 
            }//End of for
            
            // 3.8 Sort the Open list in order applying the heuristic evaluation function
            try {
                //System.out.println("Apply heuristic");
                Util.heuristicOrder(open);  // Complains because of Exception
            } catch (Exception ex) {
                Logger.getLogger(Asterisk.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }// End of while
        
        if (solutionFound){
            
            // Print states from goalState node to initialState node
            graph.depthFirstPrintPath(goalNode);
            
            System.out.println("SUCCESS");
        } else {
            // Return FALIURE
            System.out.println("FAILURE");
        }

    }
}

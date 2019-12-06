package asterisk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Asterisk {
    
    public static void main(String[] args) throws CloneNotSupportedException, Exception
    {        
        /*
         * ====================================================================
         * STATE INFORMATION
         * ====================================================================
         */
        
        boolean DEBUG_ON = false;
        
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
         *         3.7. For each 'w' in M contained in OPEN or CLOSED
         *             3.7.1. If [CONDITION MUST BE SELECTED] change pointers
         *                    to 'n'.
         *                 3.7.1.1. If 'w' contained in CLOSED, for each child
         *                          of 'w' in GRAPH, change pointers if
         *                          [CONDITION MUST BE SELECTED]
         *         3.8. Reorder OPEN list according to heuristic function
         */
        
        Graph graph = new Graph();
        List<SearchNode> closed = new ArrayList<>();
        List<SearchNode> open = new ArrayList<>();
        
        /* Initialize open with the first node of G */
        State.addState(initialState);
        graph.addVertex(initialState.getId());
        open.add(new SearchNode(initialState.getId()));
        
        // ====================================================================
        // MAIN LOOP
        // ====================================================================
        System.out.println("Esto debería tardar como mucho 1 minuto (dependiendo de la máquina).");
        System.out.println("Calculando...");
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
            State currentState = State.getState(currentNode.getStateId());
            closed.add(0, currentNode);
            
            if (DEBUG_ON) {
                System.out.println("Current node: " + currentNode.getStateId());
                currentState.printSelf();
                System.out.println("After moving it from 'open' to 'closed'...");
                Util.printList(open, "Open");
                Util.printList(closed, "Closed");
            }
            
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
            if (DEBUG_ON) {
                System.out.print("EXPANDED CHILDREN: ");
                if (children.isEmpty()) {
                    System.out.println("empty.");
                } else {
                    System.out.println("");
                }
                for (State child : children) {
                    System.out.println("Child: " + child.getId());
                    child.printSelf();
                }
            }
            
            for (State child : children){
                
                // Add to graph G
                State.addState(child);
                graph.addVertex(child.getId());
                
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
                        if (alreadyContains.equals("OPEN")) {  // This case should not happen
                            alreadyContains = "OPEN AND CLOSED";
                            throw new Exception("Very bad this thing.");
                        } else {
                            alreadyContains = "CLOSED";
                        }
                        break;
                    }
                }
                
                // If it was not contained in open nor closed, create edges
                // from nextNode to s in graph
                
                SearchNode childSearchNode = new SearchNode(child.getId());
                if (alreadyContains.equals("NONE")) {
                    
                    // Add parent
                    childSearchNode.setBestPathParent(currentState.getId());
                    // Add to open
                    open.add(childSearchNode);
                    
                } else {
                    
                    /*if (alreadyContains.equals("OPEN")){
                        Util.updateBestPathParent(graph,
                                              childSearchNode.getStateId(),
                                              currentState,
                                              open,
                                              false);
                    } else if (alreadyContains.equals("CLOSED")){
                        Util.updateBestPathParent(graph,
                                              childSearchNode.getStateId(),
                                              currentState,
                                              open,
                                              true);
                    }*/
                    
                    // Cleaner than up above
                    Util.updateBestPathParent(graph,
                                              childSearchNode.getStateId(),
                                              currentState,
                                              open,
                                              alreadyContains.equals("CLOSED"));
                }
                 
            }//End of for
            
            // 3.8 Sort the Open list in order applying the heuristic evaluation function
            Util.heuristicOrder(open);
            
        }// End of while
        
        if (solutionFound){
            
            System.out.println("¡Solución encontrada!");
            
            System.out.println("La solución es (marcha atrás): ");
            SearchNode currentNode = goalNode;
            // Print states from goalState node to initialState node
            while(true) {
                System.out.println("Node " +
                                                currentNode.getStateId() +
                                                "'s best path parent: " +
                                                currentNode.getBestPathParent());
                State.getState(currentNode.getStateId()).printSelf();
                for (SearchNode n : closed){
                    if (n.getStateId() == currentNode.getBestPathParent()){
                        currentNode = n;
                        break;
                    }
                }
                
                if (currentNode.getBestPathParent() == 0) {
                    System.out.println("Node " +
                                                currentNode.getStateId() +
                                                "'s best path parent: " +
                                                currentNode.getBestPathParent());
                    State.getState(currentNode.getStateId()).printSelf();
                    break;
                }
            }
        } else {
            // Return FALIURE
            System.out.println("FAILURE");
        }
        
        System.out.println("Se puede ver la solución observando los estados de abajo a arriba.");
    }
}

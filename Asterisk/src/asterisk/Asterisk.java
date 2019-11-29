package asterisk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Asterisk {
    public static void main(String[] args)
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
         *         3.4. Expand 'n' generating children without loops M, and add
         *              M to GRAPH.
         *         3.5. Create pointer from 'm' to 'n', should 'm' belong to 
         *              M' = M - (OPEN U CLOSED) (elements of M not contained
         *              in OPEN or CLOSED).
         *         3.6. Add M' to OPEN
         *         3.7. For each 'm' in M' contained in OPEN or CLOSED
         *             3.7.1. If (CONDITION MUST BE SELECTED) change pointers
         *                    to 'n'.
         *                 3.7.1.1. If 'm' contained in CLOSED, for each child
         *                          of 'm' in GRAPH, change pointers if
         *                          CONDITION MUST BE SELECTED
         *         3.8. Reorder OPEN list according to heuristic function
         */
        
        Graph<State> graph = new Graph<>();
        List<GraphNode> closed = new ArrayList<>();
        List<GraphNode> open = new ArrayList<>();
        
        /* Initialize open with the first node of G */
        GraphNode<State> initialStateNode = new GraphNode<>(initialState);
        graph.addVertex(initialStateNode);
        open.add(initialStateNode);
        
        // ====================================================================
        // MAIN LOOP
        // ====================================================================

        boolean success = false;
        while(true){
            
            // If open is empty, FAILURE
            if (open.size() > 0) {
                success = false;
                break;
            }
            
            // Get next node from open list
            GraphNode<State> nextNode = open.remove(0);
            closed.add(nextNode);
            
            // If next node is goal, SUCCESS
            if (Util.isGoalState(nextNode.getData())){ 
                success = true;
                break;
            }
            
            // Expand node and add successors to graph
            List<State> successors = Util.expand(nextNode.getData());
            for (State s : successors){
                
                // Add to graph
                GraphNode successorNode = new GraphNode<>(s);
                graph.addVertex(successorNode);
                
                // Create pointers from nextNode to every successor that was not
                // in open or closed
                String alreadyContains = "NONE";
                // Check if contained in open
                // Loops needed because cannot compare either State to
                // GraphNode nor pointers one to each other
                for (GraphNode n : open){
                    if (n.getData().equals(s)){
                        alreadyContains = "OPEN";
                        break;
                    }
                }
                if (alreadyContains.equals("NONE")){
                    for (GraphNode n : closed){
                        if (n.getData().equals(s)){
                            alreadyContains = "CLOSED";
                            break;
                        }
                    }
                }
                
                // If it was not contained in open nor closed, create edges
                // from nextNode to s in graph
                if (alreadyContains.equals("NONE")) {
                    // Create edge
                    graph.addEdge(successorNode, nextNode);
                    // Use this loop to add to open as well
                    open.add(successorNode);
                } else {
                    // 1 - Decide if pointers should be modified (-> nextNode)
                    // 2 - Decided if pointers of children of elements
                    //     contained in closed should be modified as well
                    if (alreadyContains.equals("CLOSED")){
                        // Do point 2
                    }
                }
                 
                try {
                    Util.heuristicOrder(open);  // Complains because of Exception
                } catch (Exception ex) {
                    Logger.getLogger(Asterisk.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if (success){
            // Return solution, SUCCESS
        } else {
            // Return FALIURE
        }

    }
}

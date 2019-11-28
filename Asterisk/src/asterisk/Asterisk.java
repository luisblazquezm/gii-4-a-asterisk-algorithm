package asterisk;

import java.util.ArrayList;
import java.util.List;

public class Asterisk {
    public static void main(String[] args)
    {

        StateNode initialStateNode = new StateNode(
                new ArrayList<Box>(){
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
                    new Box(2,1,19),
                },
                new StorageHouse());
        List<StateNode> closed = new ArrayList<StateNode>();
        List<StateNode> open = new ArrayList<StateNode>();
        
        /* Initialize open with the first node of G */
        open.add(initialStateNode);
        
        boolean success = false;
        while(open.size() > 0){
            StateNode currentNode = open.remove(0);
            closed.add(currentNode);
            if (Util.isGoalStateNode(currentNode)){
                success = true;
                break;
            }
            
            List<StateNode> successors = Util.expand(currentNode);
        }
        
        // Si se sale con éxito, dar la solución
        if (success){
            
        }
    }
}

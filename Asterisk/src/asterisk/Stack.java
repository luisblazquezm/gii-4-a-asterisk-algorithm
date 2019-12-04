package asterisk;

import java.util.List;
import java.util.ArrayList;

public class Stack implements Cloneable{

    private List<Box> stack;

    public Stack() {
        this.stack = new ArrayList<Box>();
    }
    
    public Stack(List<Box> newStack) {
        this.stack = newStack;
    }
    
    public List<Box> getStack() {
        return this.stack;
    }

    public void push(Box box) {
        getStack().add(0, box);
    }

    public Box pop() {
        if(!stack.isEmpty()){
            Box box = getStack().get(0);
            getStack().remove(0);
            return box;
        } else {
            return null;
        }
    }
    
    public Box printPop() {
        if(!stack.isEmpty()){
            Box box = getStack().get(0);
            return box;
        } else {
            return null;
        }
    }

    public Box peek() {
        if(!stack.isEmpty()) {
            return getStack().get(0);
        } else {
            return null;// Or any invalid value
        }
    }

    public Box peek(int i) {
        if(!stack.isEmpty()) {
            return getStack().get(i);
        } else {
            return null;// Or any invalid value
        }
    }

    public boolean isEmpty() {
        return getStack().isEmpty();
    }

    public int size(){
        return getStack().size();
    }
    
    public boolean equals(Stack stack){
        for (int i = 0; i < stack.size(); i++){
            if (!this.stack.get(i).equals(stack.getStack().get(i)))
                return false;
        }
        
        return true;
    }
    
    public Stack clone() throws CloneNotSupportedException 
    { 
        int i;
        List<Box> newStack = new ArrayList<Box>();
        
        for (Box b : this.stack)
            newStack.add(b.clone());
            
        return new Stack(newStack); 
    } 
}

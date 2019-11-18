package main;

import java.util.List;
import java.util.ArrayList;

public class Stack {

    private List<Box> stack;

    public Stack() {
         stack = new ArrayList<Box>();
    }

    public void push(Box box) {
        stack.add(0, box);
    }

    public Box pop() {
        if(!stack.isEmpty()){
            Box box = stack.get(0);
            stack.remove(0);
            return box;
        } else {
            return null;
        }
    }

    public Box peek() {
        if(!stack.isEmpty()) {
            return stack.get(0);
        } else {
            return null;// Or any invalid value
        }
    }

    public Box peek(int i) {
        if(!stack.isEmpty()) {
            return stack.get(i);
        } else {
            return null;// Or any invalid value
        }
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size()
    {
        return stack.size();
    }
}

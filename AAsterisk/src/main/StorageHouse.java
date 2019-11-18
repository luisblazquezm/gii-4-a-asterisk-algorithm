package main;

public class StorageHouse {

    public static int NUMBER_OF_STACKS = 5;
    public static int STACK_SIZE = 4;

    private Stack[] stacks;

    public StorageHouse() {
        stacks = new Stack[NUMBER_OF_STACKS];
        for (int i = 0; i < NUMBER_OF_STACKS; ++i)
            stacks[i] = new Stack();
    }

    public boolean addBox(Box box) {
        for (Stack s : stacks) {
            if (s.size() < STACK_SIZE) {
                s.push(box);
                return true;
            }
        }
        return false;
    }

    public boolean addBox(Box box, int position) {
        if (position >= 0 && position < NUMBER_OF_STACKS) {
            if (stacks[position].size() < STACK_SIZE) {
                stacks[position].push(box);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Pila 1     Pila 2     Pila 3     Pila 4     Pila 5\n");
        sb.append("________   ________   ________   ________   ________\n");
        for (int i = 0; i < STACK_SIZE; ++i) {
            for (int j = 0; j < NUMBER_OF_STACKS; ++j) {
                if (stacks[j].size() > i && stacks[j].peek(i) != null) {
                    sb.append(String.format("|_ %2d _|   ", stacks[j].peek(i).getDepartureDate()));
                } else {
                    sb.append("|_    _|   ");
                }
            }
            sb.append('\n');
        }
        sb.append("====================================================\n");
        return new String(sb);
    }
}

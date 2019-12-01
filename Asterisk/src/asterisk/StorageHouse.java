package asterisk;

public class StorageHouse implements Cloneable{

    public static int NUMBER_OF_STACKS = 5;
    public static int STACK_SIZE = 4;

    private Stack[] stacks;

    public StorageHouse() {
        this.stacks = new Stack[NUMBER_OF_STACKS];
        for (int i = 0; i < NUMBER_OF_STACKS; ++i)
            this.stacks[i] = new Stack();
    }
    
    public StorageHouse(StorageHouse storageHouse){
        this.stacks = new Stack[NUMBER_OF_STACKS];
        for (int i = 0; i < NUMBER_OF_STACKS; ++i)
            this.stacks[i] = storageHouse.getStacks()[i];
    }
    
    public StorageHouse(Stack[] newStacks){
        this.stacks = new Stack[NUMBER_OF_STACKS];
        for (int i = 0; i < NUMBER_OF_STACKS; ++i)
            this.stacks[i] = newStacks[i];
    }
    
    public Stack[] getStacks() {
        return this.stacks;
    }

    public boolean addBox(Box box) {
        for (Stack s : this.stacks) {
            if (s.size() < STACK_SIZE) {
                s.push(box);
                return true;
            }
        }
        return false;
    }

    public boolean addBox(Box box, int position) {
        if (position >= 0 && position < NUMBER_OF_STACKS) {
            if (this.stacks[position].size() < STACK_SIZE) {
                this.stacks[position].push(box);
                return true;
            }
        }
        return false;
    }

    public boolean equals(StorageHouse storageHouse){
        if (this.stacks.length != storageHouse.getStacks().length) return false;
        for (int i = 0; i < this.stacks.length; ++i){
            if (!this.stacks[i].equals(storageHouse.getStacks()[i]))
                return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("       Pila 1     Pila 2     Pila 3     Pila 4     Pila 5\n");
        sb.append("      ________   ________   ________   ________   ________\n");
        for (int i = 0; i < STACK_SIZE; ++i) {
            sb.append(String.format("Pos %d ", i + 1));
            for (int j = 0; j < NUMBER_OF_STACKS; ++j) {
                //System.out.printf(String.format("\n\nDEGUG: stacks[%d].size() = %d &&", j, stacks[j].size())); //DEBUG
                //System.out.printf(" stacks[" + j + "].peek(" + i + ") = " + stacks[j].peek(i) + "\n\n"); //DEBUG
                if (stacks[j].size() > i && stacks[j].peek(i) != null) {
                    sb.append(String.format("|_ %2d _|   ", stacks[j].peek(i).getDepartureDate()));
                } else {
                    sb.append("|_    _|   ");
                }
            }
            sb.append('\n');
        }
        sb.append("==========================================================\n");
        return new String(sb);
    }
    
    public StorageHouse clone() throws CloneNotSupportedException 
    { 
        int i;
        Stack[] newStacks = new Stack[NUMBER_OF_STACKS];
        
        for (i = 0; i < NUMBER_OF_STACKS; i++ )
            newStacks[i] = this.stacks[i].clone();
            
        return new StorageHouse(newStacks); 
    } 
}

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
            if (this.stacks[position].size() == 0){
                this.stacks[position].push(box);
                return true;
            } else if (this.stacks[position].size() < STACK_SIZE && !Util.invalidStack(this.stacks[position], box)) { 
                this.stacks[position].push(box);
                return true;
            }
        }
        return false;
    }

    public boolean equals(StorageHouse storageHouse){
        for (int i = 0; i < this.stacks.length; i++){
            if (this.stacks[i].size() != storageHouse.getStacks()[i].size()) return false;
            if (!this.stacks[i].equals(storageHouse.getStacks()[i])){
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("         Pila 1     Pila 2     Pila 3     Pila 4     Pila 5\n");
        sb.append("        ________   ________   ________   ________   ________\n");
        
        
        for (int i = 0; i < STACK_SIZE; ++i) {
            
            if (i == 3) sb.append("Top     ");
            else if (i == 0) sb.append("Bottom  ");
            else sb.append("        ");
            
            for (int j = 0; j < NUMBER_OF_STACKS; ++j) {
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

    public boolean fullStacks() {
        
        for (int i = 0; i < NUMBER_OF_STACKS; i++ )
            if (this.stacks[i].getStack().size() != STACK_SIZE)
                return false;
        
        return true;
    }
}

package asterisk;

public class Box implements Cloneable{
    private int id;
    private int arrivalDate;
    private int departureDate;

    public Box(int id, int arrivalDate, int departureDate) {
        this.id = id;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }

    public int getId() {
        return this.id;
    }

    public int getArrivalDate() {
        return this.arrivalDate;
    }

    public int getDepartureDate() {
        return this.departureDate;
    }
    
    public boolean equals(Box b2) {
        return (this.getArrivalDate() == b2.getArrivalDate()
                && this.getDepartureDate() == b2.getDepartureDate()
                );
    }
    
    public Box clone() throws CloneNotSupportedException 
    { 
        return new Box(this.id, this.arrivalDate, this.departureDate); 
    } 
}

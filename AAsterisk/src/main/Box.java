package main;

public class Box {
    private int id;
    private int arrivalDate;
    private int departureDate;

    public Box(int id, int arrivalDate, int departureDate) {
        this.id = id;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }

    public int getId() {
        return id;
    }

    public int getArrivalDate() {
        return arrivalDate;
    }

    public int getDepartureDate() {
        return departureDate;
    }
}

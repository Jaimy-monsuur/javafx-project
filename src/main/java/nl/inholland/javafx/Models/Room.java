package nl.inholland.javafx.Models;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private List<Showing> showings;
    private int seats;

    public Room(int seats) {
        this.seats = seats;
        this.showings = new ArrayList<Showing>();
    }

    public int getSeats() {
        return seats;
    }

    public void AddShowing(Showing s) {
        showings.add(s);
    }

    public List<Showing> getShowings() {
        return showings;
    }

    public void setShowings(List<Showing> showings) {
        this.showings = showings;
    }
}

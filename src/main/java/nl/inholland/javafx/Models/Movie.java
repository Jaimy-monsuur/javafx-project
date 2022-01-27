package nl.inholland.javafx.Models;

import java.time.Duration;

public class Movie {
    private String name;
    private double ticketPrice;
    private Duration duration;

    public Movie(String name, double ticketPrice, Duration duration) {
        this.name = name;
        this.ticketPrice = ticketPrice;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}

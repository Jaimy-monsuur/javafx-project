package nl.inholland.javafx.Models;

import java.time.LocalDateTime;

public class Showing {
    private Movie movie;
    private int numberOfTickets;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;

    public Showing(Movie movie, LocalDateTime beginTime, LocalDateTime endTime, int numberOfTickets) {
        this.movie = movie;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.numberOfTickets = numberOfTickets;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getNumberOfTickets() {
        return this.numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}

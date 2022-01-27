package nl.inholland.javafx.Data;


import nl.inholland.javafx.Models.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Room room1;
    private Room room2;
    private List<User> users;
    private List<Movie> movies;

    public Database() {
        this.room1 = new Room(200);
        this.room2 = new Room(100);
        this.users = new ArrayList<User>();
        this.movies = new ArrayList<Movie>();
        this.users.add(new User("Kevin", "niveK", UserType.User));
        this.users.add(new User("Shawn", "nwahS", UserType.Admin));
        Movie hobbit1 = new Movie("The Hobbit: An Unexpected Journey", 12.50, Duration.ofMinutes(169));
        Movie hobbit2 = new Movie("The Hobbit: The Desolation of Smaug", 12.50, Duration.ofMinutes(161));
        Movie hobbit3 = new Movie("The Hobbit: The Battle of the Five Armies", 12.50, Duration.ofMinutes(144));
        movies.add(hobbit1);
        movies.add(hobbit2);
        movies.add(hobbit3);
        Showing showing1 = new Showing(hobbit1, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)), LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 49)), room1.getSeats());
        Showing showing2 = new Showing(hobbit2, LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 10)), LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 51)), room1.getSeats());
        Showing showing3 = new Showing(hobbit3, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0)), LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 21)), room1.getSeats());
        Showing showing4 = new Showing(hobbit1, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)), LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 49)), room2.getSeats());
        Showing showing5 = new Showing(hobbit2, LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 10)), LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 51)), room2.getSeats());
        Showing showing6 = new Showing(hobbit3, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 10)), LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 21)), room2.getSeats());
        room1.getShowings().add(showing1);
        room1.getShowings().add(showing2);
        room1.getShowings().add(showing3);
        room2.getShowings().add(showing4);
        room2.getShowings().add(showing5);
        room2.getShowings().add(showing6);
    }

    public Room getRoom1() {
        return room1;
    }

    public Room getRoom2() {
        return room2;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Movie> getMovies() {
        return movies;
    }

}

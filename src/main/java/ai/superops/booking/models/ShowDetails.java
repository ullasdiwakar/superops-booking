package ai.superops.booking.models;

import javax.persistence.*;

@Entity
public class ShowDetails {

    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column
    private String time;

    @OneToOne(cascade = CascadeType.ALL)
    private Movie movie;

    @OneToOne(cascade = CascadeType.ALL)
    private Cinema cinema;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }
}

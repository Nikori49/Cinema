package DB.entity;

import DB.Utils;

import java.sql.Date;
import java.sql.Time;
import java.util.TreeMap;

public class Showtime {
    Long id;
    Long filmId;
    java.sql.Date date;
    String status;
    Time startTime;
    Time endTime;
    TreeMap<String,String> seats;

    public Showtime() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public TreeMap<String, String> getSeats() {
        return seats;
    }

    public void setSeats(TreeMap<String, String> seats) {
        this.seats = seats;
    }
}

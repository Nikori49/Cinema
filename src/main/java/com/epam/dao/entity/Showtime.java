package com.epam.dao.entity;



import java.sql.Date;
import java.sql.Time;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Represents entry from DB table <code>show_times</code> .
 * <p>
 * Has the following fields:
 * <ul>
 *     <li><code>Long id</code></li>
 *     <li><code>Long filmId</code></li>
 *     <li><code>Date date</code></li>
 *     <li><code>String status</code></li>
 *     <li><code>Time startTime</code></li>
 *     <li><code>Time endTime</code></li>
 *     <li><code>TreeMap < String,String > seats</code>: TreeMap representing seats.
 *     Where key is seat id from A1 to J24 and value is this seats status.</li>
 * </ul>
 * Setter and getter methods are provided.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 * @see com.epam.dao.ShowtimeDAO
 */
public class Showtime extends Entity{
    Long id;
    Long filmId;
    java.sql.Date date;
    String status;
    Time startTime;
    Time endTime;
    TreeMap<String, String> seats;

    public Showtime() {
    }

    @Override
    public String toString() {
        return "Showtime{" +
                "id=" + id +
                ", filmId=" + filmId +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", seats=" + seats +
                '}';
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Showtime showtime = (Showtime) o;
        return Objects.equals(id, showtime.id) && Objects.equals(filmId, showtime.filmId) && Objects.equals(date, showtime.date) && Objects.equals(status, showtime.status) && Objects.equals(startTime, showtime.startTime) && Objects.equals(endTime, showtime.endTime) && Objects.equals(seats, showtime.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmId, date, status, startTime, endTime, seats);
    }
}

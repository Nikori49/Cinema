package com.epam.dao.entity;

import com.epam.dao.DBManager;

/**
 * Represents entry from DB table <code>tickets</code> .
 * <p>
 * Has the following fields:
 * <ul>
 *     <li><code>Long id</code></li>
 *     <li><code>Long userId</code></li>
 *     <li><code>Long showtimeId</code></li>
 *     <li><code>String seat</code>: seat id in range from A1 to J24.</li>
 * </ul>
 * Setter and getter methods are provided.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 * @see DBManager
 */
public class Ticket extends Entity{
    Long id;
    Long userId;
    Long showTimeId;
    String seat;

    public Ticket() {
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", userId=" + userId +
                ", showTimeId=" + showTimeId +
                ", seat='" + seat + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(Long showTimeId) {
        this.showTimeId = showTimeId;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}

package com.epam.dao.entity;



import java.util.Objects;

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
 * @see com.epam.dao.TicketDAO
 */
public class Ticket extends Entity {
    Long id;
    Long userId;
    Long showTimeId;
    String seat;
    String status;

    public Ticket() {
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", userId=" + userId +
                ", showTimeId=" + showTimeId +
                ", seat='" + seat + '\'' +
                ", status='" + status + '\'' +
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(userId, ticket.userId) && Objects.equals(showTimeId, ticket.showTimeId) && Objects.equals(seat, ticket.seat) && Objects.equals(status, ticket.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, showTimeId, seat, status);
    }
}

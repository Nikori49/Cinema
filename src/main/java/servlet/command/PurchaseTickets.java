package servlet.command;

import DB.DBManager;
import DB.Utils;
import DB.entity.Showtime;
import DB.entity.Ticket;
import DB.entity.User;
import DB.exception.DBException;
import service.ShowtimeService;
import service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Command which execute method validates and inserts Ticket object into DB.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
public class PurchaseTickets implements Command {
    private final TicketService ticketService;
    private final ShowtimeService showtimeService;

    public PurchaseTickets(TicketService ticketService, ShowtimeService showtimeService) {
        this.ticketService = ticketService;
        this.showtimeService = showtimeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("loggedUser");

        if (user == null) {
            return "login.jsp";
        }
        if (!Objects.equals(user.getRole(), "client")) {
            return "index.jsp";
        }
        String showtimeId = request.getParameter("showtimeId");
        if (showtimeId == null) {
            return "error.jsp";
        }
        Showtime showtime;
        showtime = showtimeService.getShowtime(Long.valueOf(showtimeId));
        if (showtime == null) {
            System.out.println(1);
            return "error.jsp";
        }
        List<String> seatsPurchased = new ArrayList<>();
        Set<String> keySet = new  Utils().fillSeatMap().keySet();
        for (String s : keySet) {
            seatsPurchased.add(request.getParameter(s));
        }
        seatsPurchased.removeIf(Objects::isNull);
        if (seatsPurchased.isEmpty()) {
            return "index.jsp";
        }
        for (String s : seatsPurchased) {
            if (Objects.equals(showtimeService.getSeatStatus(s, showtime.getId()), "occupied")) {
                System.out.println(s);
                System.out.println(showtime.getId());
                System.out.println(showtimeService.getSeatStatus(s,showtime.getId()));
                System.out.println(2);
                return "error.jsp";
            }
        }
        for (String s : seatsPurchased) {
            Ticket ticket = new Ticket();
            ticket.setSeat(s);
            ticket.setShowTimeId(showtime.getId());
            ticket.setUserId(user.getId());

               ticketService.createTicket(ticket);

        }




        return "client.jsp";
    }
}

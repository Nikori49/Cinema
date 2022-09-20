package com.epam.servlet.command;

import com.epam.annotation.MyInject;
import com.epam.service.ShowtimeService;
import com.epam.dao.Utils;
import com.epam.dao.entity.Showtime;
import com.epam.dao.entity.Ticket;
import com.epam.dao.entity.User;
import com.epam.service.TicketService;
import com.epam.service.UserService;

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
public class PurchaseTicketsCommand implements Command {
    private final TicketService ticketService;
    private final ShowtimeService showtimeService;
    private final UserService userService;

    @MyInject
    public PurchaseTicketsCommand(TicketService ticketService, ShowtimeService showtimeService, UserService userService) {
        this.ticketService = ticketService;
        this.showtimeService = showtimeService;
        this.userService = userService;
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
        if (Objects.equals(showtime.getStatus(), "finished") || Objects.equals(showtime.getStatus(), "canceled") ){
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
        if (user.getBalance()-(seatsPurchased.size()*75L)<0){
            return "error.jsp";
        }
        for (String s : seatsPurchased) {
            if (Objects.equals(showtimeService.getSeatStatus(s, showtime.getId()), "occupied")) {
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

        request.getSession().setAttribute("loggedUser",userService.findUserById(user.getId()));

        return "client.jsp";
    }
}

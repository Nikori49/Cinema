package com.epam.servlet.command;

import com.epam.annotation.MyInject;
import com.epam.dao.entity.Ticket;
import com.epam.service.TicketService;
import com.epam.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RefundTicketCommand implements Command{
    private final TicketService ticketService;
    private final UserService userService;

    @MyInject
    public RefundTicketCommand(TicketService ticketService, UserService userService) {
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Ticket ticket = ticketService.getTicket(Long.valueOf(request.getParameter("id")));
        ticketService.refundTicket(ticket);
        request.getSession().setAttribute("loggedUser"
                ,userService.findUserById(
                        ticket.getUserId()));
        return "client.jsp";
    }
}

package com.epam.servlet.command;

import com.epam.dao.DBManager;
import com.epam.dao.entity.Ticket;
import com.epam.dao.entity.User;
import com.epam.service.TicketService;
import com.epam.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RefundTicketCommandTest {

    @Test
    public void refundTicketCommandTest() throws ServletException, IOException {

        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);

        TicketService ticketService = mock(TicketService.class);
        Ticket ticket = new Ticket();
        ticket.setUserId(1L);

        when(ticketService.getTicket(anyLong())).thenReturn(ticket);
        when(userService.findUserById(anyLong())).thenReturn(new User());
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("id")).thenReturn(String.valueOf(1L));
        when(request.getSession()).thenReturn(session);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RefundTicketCommand refundTicketCommand = new RefundTicketCommand(ticketService,userService);
        Assertions.assertDoesNotThrow(()->refundTicketCommand.execute(request,response));
    }
}

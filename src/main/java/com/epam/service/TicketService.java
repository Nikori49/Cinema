package com.epam.service;

import com.epam.annotation.Service;

import com.epam.dao.TicketDAO;
import com.epam.dao.entity.Ticket;
import com.epam.dao.exception.DBException;

import java.util.List;

@Service
public class TicketService {
    private final TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }
    public void createTicket(Ticket ticket)  {

        try {
            ticketDAO.insert(ticket);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Ticket> getUserTickets(Long userId){
        try {
            return ticketDAO.getUserTickets(userId);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Ticket getTicket(Long ticketId){
        try {
            return ticketDAO.findById(ticketId);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void refundTicket(Ticket ticket){
        try {
            ticketDAO.refundTicket(ticket);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }




}

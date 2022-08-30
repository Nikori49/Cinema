package service;

import DB.DBManager;
import DB.entity.Ticket;
import DB.exception.DBException;

import java.util.List;

public class TicketService {
    private final DBManager dbManager;

    public TicketService(DBManager dbManager) {
        this.dbManager = dbManager;
    }
    public void createTicket(Ticket ticket)  {

        try {
            dbManager.insertTicket(ticket);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Ticket> getUserTickets(Long userId){
        try {
            return dbManager.getUserTickets(userId);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Ticket getTicket(Long ticketId){
        try {
            return dbManager.getTicket(ticketId);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }




}

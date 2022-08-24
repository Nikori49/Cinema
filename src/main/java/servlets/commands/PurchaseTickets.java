package servlets.commands;

import DB.DBManager;
import DB.Utils;
import DB.entity.Showtime;
import DB.entity.Ticket;
import DB.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class PurchaseTickets implements Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("loggedUser");
        if (user==null){
            return "login.jsp";
        }
        if (!Objects.equals(user.getRole(), "client")){
            return "index.jsp";
        }
        String showtimeId = request.getParameter("showtimeId");
        if (showtimeId==null){
            return "error.jsp";
        }
        Showtime showtime = DBManager.getInstance().getShowTime(Long.valueOf(showtimeId));
        if (showtime==null){
            return "error.jsp";
        }
        List<String> seatsPurchased = new ArrayList<>();
        Set<String> keySet = Utils.fillSeatMap().keySet();
        for (String s:keySet) {
            seatsPurchased.add(request.getParameter(s));
        }
        seatsPurchased.removeIf(Objects::isNull);
        if (seatsPurchased.isEmpty()){
            return "index.jsp";
        }
        System.out.println(seatsPurchased);
        for (String s:seatsPurchased) {
            if(Objects.equals(DBManager.getInstance().getSeatStatus(s, showtime.getId()), "occupied")){
                return "error.jsp";
            }
        }
        for (String s:seatsPurchased) {
            Ticket ticket = new Ticket();
            ticket.setSeat(s);
            ticket.setShowTimeId(showtime.getId());
            ticket.setUserId(user.getId());
            DBManager.getInstance().insertTicket(ticket);
        }

        request.getSession().setAttribute("userTickets",DBManager.getInstance().getUserTickets(user));


        return "client.jsp";
    }
}

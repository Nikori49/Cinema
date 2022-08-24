package servlets.commands;

import DB.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CancelShowtimeCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringId = request.getParameter("film");
        if (!stringId.isEmpty()){
            Long id = Long.valueOf(stringId);
            DBManager.getInstance().cancelShowtime(id);
        }


        return "manager.jsp";
    }
}

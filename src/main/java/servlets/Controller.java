package servlets;


import DB.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import servlets.commands.Command;
import servlets.commands.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(Controller.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

            req.setCharacterEncoding("UTF-8");

        String address = "error.jsp";
        String commandName = req.getParameter("command");

        Command command = CommandContainer.getCommand(commandName);

        try {
            address = command.execute(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("errorMessage", ex.getMessage());
        }
        User user = (User) req.getSession().getAttribute("loggedUser");

        logger.info("commandName ==> " + commandName);
        if(user!=null){
            logger.info("loggedUser ==> " + user.getLogin());
        }
        logger.info("command ==> " + command);
        logger.info("forward ==> " + address);
        logger.info("===============================================");

        req.getRequestDispatcher(address).forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
            req.setCharacterEncoding("UTF-8");

        String address = "error.jsp";
        String commandName = req.getParameter("command");
        logger.info("commandName ==> " + commandName);

        Command command = CommandContainer.getCommand(commandName);




        try {
            address = command.execute(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
            req.getSession()
                    .setAttribute("errorMessage", ex.getMessage());
        }
        logger.info("redirect ==> " + address);
        resp.sendRedirect(address);
    }

}

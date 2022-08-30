package servlet.command;

import DB.DBManager;
import DB.entity.User;
import DB.exception.DBException;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Command which execute method validates users login attempt and if successful sets "loggedUser" attribute.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
public class LoginCommand implements Command {
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String address = "index.jsp";
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        byte[] digest;
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes(StandardCharsets.UTF_8));
            digest = md.digest();

            for (int i = 0; i < digest.length; i++) {
                byte b = digest[i];
                String hex = Integer.toHexString((int) 0x00FF & b);
                if (hex.length() == 1) {
                    sb.append("0");
                }
                sb.append(hex);
            }
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }


        String passHash = sb.toString();


        User user = null;
        user = userService.findUserByLogin(login);

        if (user != null && user.getPassword().equals(passHash)) {
            request.getSession().setAttribute("loggedUser", user);
            /*if (Objects.equals(user.getRole(), "client")) {
                try {
                    request.getSession().setAttribute("userTickets",request.getServletContext().getAttribute("DBManager").getUserTickets(user));
                } catch (DBException e) {
                    response.sendRedirect("error.jsp");
                }
            }*/
            if (Objects.equals(user.getRole(), "manager")) {
                address = "manager.jsp";
            }
        }
        if (user == null) {
            request.getSession().setAttribute("loginError", 1);
            return "login.jsp";
        }
        if (!user.getPassword().equals(passHash)) {
            request.getSession().setAttribute("loginError", 2);
            return "login.jsp";
        }
        return address;

    }
}

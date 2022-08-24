package servlets.commands;

import DB.DBManager;
import DB.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String address = "index.jsp";
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        byte[] digest;
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            md.update(password.getBytes(StandardCharsets.UTF_8));
            digest = md.digest();

            for( int i=0; i<digest.length; i++ ) {
                byte b = digest[ i ];
                String hex = Integer.toHexString((int) 0x00FF & b);
                if (hex.length() == 1)
                {
                    sb.append("0");
                }
                sb.append(hex);
            }
        }catch (NoSuchAlgorithmException exception){
            exception.printStackTrace();
        }


        String passHash=sb.toString();
        System.out.println("login ==> " + login);

        User user = DBManager.getInstance().findUserByLogin(login);

        if (user != null && user.getPassword().equals(passHash)) {
            request.getSession().setAttribute("loggedUser", user);
            if(Objects.equals(user.getRole(), "client")){
                request.getSession().setAttribute("userTickets",DBManager.getInstance().getUserTickets(user));
            }
            if(Objects.equals(user.getRole(), "manager")){
                address="manager.jsp";
            }


        }
        if (user==null ){
            System.out.println("login error");
            request.getSession().setAttribute("loginError",1);
            return address;
        }
        if (!user.getPassword().equals(passHash)){
            System.out.println("login error");
            request.getSession().setAttribute("loginError",2);
            return address;
        }
        return address;

    }
}

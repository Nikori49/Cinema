package com.epam.servlet.command;

import com.epam.annotation.MyInject;
import com.epam.dao.entity.User;
import com.epam.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddBalanceCommand implements Command{
    private final UserService userService;

    @MyInject
    public AddBalanceCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("loggedUser");
        Long fundsToAdd = Long.valueOf(request.getParameter("sum"));
        userService.updateBalance(user.getId(),fundsToAdd);
        request.getSession().setAttribute("loggedUser",userService.findUserById(user.getId()));
        return "client.jsp";
    }
}

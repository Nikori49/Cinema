package com.epam.servlet.command;

import com.epam.annotation.MyInject;
import com.epam.service.ShowtimeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command which execute method updates Showtime status to canceled in DB.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
public class CancelShowtimeCommand implements Command {
    private final ShowtimeService showtimeService;

    @MyInject
    public CancelShowtimeCommand(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringId = request.getParameter("film");
        if (!stringId.isEmpty()) {
            Long id = Long.valueOf(stringId);
            showtimeService.cancelShowtime(id);
        }



        return "manager.jsp";
    }
}

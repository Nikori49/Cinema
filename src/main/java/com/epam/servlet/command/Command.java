package com.epam.servlet.command;

import com.epam.dao.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Interface that describes <code>Controller</code> commands.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DBException;
}

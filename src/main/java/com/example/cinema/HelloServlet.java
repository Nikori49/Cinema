package com.example.cinema;

import DB.Utils;

import java.io.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
       TreeMap<String, String> test = Utils.fillSeatMap();

        out.println(test);

        List<Date> dateList = Utils.getWeekDates();
        out.println(dateList);
        List<String> weekDayList = Utils.getWeekDays();
        out.println(weekDayList);
    }

    public void destroy() {
    }
}
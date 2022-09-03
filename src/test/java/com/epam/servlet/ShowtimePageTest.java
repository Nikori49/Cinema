package com.epam.servlet;

import com.epam.dao.entity.Film;
import com.epam.dao.entity.Showtime;
import com.epam.service.FilmService;
import com.epam.service.ShowtimeService;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShowtimePageTest {

    @Test
    public void showtimePageTest() throws IOException, ServletException {
        ShowtimePage showtimePage = new ShowtimePage();
        String HTML = "<html>\n" +
                "<head>\n" +
                "    <title>testName</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\">\n" +
                "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\n" +
                "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>\n" +
                "<script>\n" +
                "    $(document).ready(function(){\n" +
                "        $('[data-toggle=\"popover\"]').popover();\n" +
                "    });\n" +
                "</script>\n" +
                "<script>function changeLanguage(lang) {\n" +
                "            let request\n" +
                "            const url = \"null/ChangeLanguage?lang=\" + lang;\n" +
                "\n" +
                "            if(window.XMLHttpRequest){\n" +
                "                request=new XMLHttpRequest();\n" +
                "            }\n" +
                "            else if(window.ActiveXObject){\n" +
                "                request=new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                "            }\n" +
                "\n" +
                "            try\n" +
                "            {\n" +
                "                request.open(\"GET\",url,false);\n" +
                "                request.send();\n" +
                "            }\n" +
                "            catch(e)\n" +
                "            {\n" +
                "                alert(\"Unable to connect to server\");\n" +
                "            }\n" +
                "            document.location.reload();\n" +
                "        }</script></head>\n" +
                "<body><nav class=\"navbar navbar-inverse\">\n" +
                "    <div class=\"container-fluid\">\n" +
                "        <div class=\"navbar-header\">\n" +
                "            <a class=\"navbar-brand\" href=\"index.jsp\">Cinema</a>\n" +
                "        </div>\n" +
                "        <ul class=\"nav navbar-nav\">\n" +
                "            <li><a href=\"films.jsp\">Films</a></li>\n" +
                "            <li class=\"active\"><a href=\"schedule.jsp\">Schedule</a></li>\n" +
                "</ul>\n" +
                "<ul class=\"nav navbar-nav navbar-right\">\n" +
                "<li class=\"active\" > <a href=\"#\" onclick=\"changeLanguage('en')\" >En</a></li>\n" +
                "<li > <a href=\"#\" onclick=\"changeLanguage('uk')\" >Укр</a></li><li><a href=\"register.jsp\"><span class=\"glyphicon glyphicon-user\"></span>Sign Up</a></li>\n" +
                "<li><a href=\"login.jsp\"><span class=\"glyphicon glyphicon-log-in\"></span>Log In</a></li>\n" +
                "</ul>\n" +
                "</div>\n" +
                "</nav><div class=\"container\"<h3>testName 10:10-12:12</h3><img   id=\"poster\" class=\"img-responsive\" alt=\"Missing poster\"  width=\"200\" height=\"300\"  src=\"testPath\"></div><script>let request;\n" +
                "        function purchaseTicketPreview() {\n" +
                "const A1 = document.getElementById(\"A1\"); \n" +
                "let A1_status='false';\n" +
                "if (A1.checked == true && A1.disabled!=true ){  \n" +
                "A1_status ='true' \n" +
                "  }  \n" +
                "const A2 = document.getElementById(\"A2\"); \n" +
                "let A2_status='false';\n" +
                "if (A2.checked == true && A2.disabled!=true ){  \n" +
                "A2_status ='true' \n" +
                "  }  \n" +
                "            const url = \"null/TicketPreview?A1=\"+A1_status+\"&A2=\"+A2_status+\"&id=\"+1; \n" +
                "            if (window.XMLHttpRequest) {\n" +
                "                request = new XMLHttpRequest();\n" +
                "            } else if (window.ActiveXObject) {\n" +
                "                request = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                "            }\n" +
                "\n" +
                "            try {\n" +
                "                request.onreadystatechange = getPreview;\n" +
                "                request.open(\"GET\", url, true);\n" +
                "                request.send();\n" +
                "            } catch (e) {\n" +
                "                alert(\"Unable to connect to server\");\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        function getPreview() {\n" +
                "            if (request.readyState === 4) {\n" +
                "                document.getElementById('ticketPreview').innerHTML = request.responseText;\n" +
                "            }\n" +
                "        }</script><div style=\"position: absolute; top: 15%; right: 0%\" class=\"container\">\n" +
                "    <form role=\"form\" action=\"controller\" method=\"post\">\n" +
                "        <input type=\"hidden\" name=\"command\" value=\"purchaseTickets\">\n" +
                "        <input type=\"hidden\" id=\"showtimeId\" name=\"showtimeId\" value=\"1\">\n" +
                "        <table class=\"table-responsive\">\n" +
                "            <tr>\n" +
                "<td><input class=\"checkbox-inline\"  data-placement=\"top\" data-toggle=\"popover\" data-trigger=\"hover\" data-content=\"A1\" id=\"A1\" value=\"A1\" name=\"A1\" type=\"checkbox\"></td>\n" +
                "<td><input class=\"checkbox-inline\"  data-placement=\"top\" data-toggle=\"popover\" data-trigger=\"hover\" data-content=\"A2\" id=\"A2\" value=\"A2\" name=\"A2\" type=\"checkbox\"></td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "<span id=\"ticketPreview\"></span>    </form>\n" +
                "</div></body";

        FilmService filmService = mock(FilmService.class);
        Film film = new Film();
        film.setId(1L);
        film.setPosterImgPath("testPath");
        film.setDescription("testDesc");
        film.setGenre("testGenre");
        film.setName("testName");
        film.setDirector("testDirector");
        film.setYoutubeTrailerId("12345678901");
        film.setRunningTime(10L);
        when(filmService.getFilm(anyLong())).thenReturn(film);

        ShowtimeService showtimeService = mock(ShowtimeService.class);
        Showtime showtime1 = new Showtime();
        showtime1.setDate(Date.valueOf("2222-08-12"));
        showtime1.setId(1L);
        showtime1.setStatus("planned");
        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("A1","vacant");
        treeMap.put("A2","vacant");
        showtime1.setSeats(treeMap);
        showtime1.setFilmId(1L);
        showtime1.setStartTime(Time.valueOf("10:10:10"));
        showtime1.setEndTime(Time.valueOf("12:12:12"));

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(2048);
        PrintWriter mockWriter = new PrintWriter(outputStream);
        ServletContext servletContext = mock(ServletContext.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("loggedUser")).thenReturn(null);
        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getParameter("id")).thenReturn("1");
        when(servletContext.getAttribute("filmService")).thenReturn(filmService);
        when(servletContext.getAttribute("showtimeService")).thenReturn(showtimeService);






        when(showtimeService.getShowtime(anyLong())).thenReturn(showtime1);

        when(response.getWriter()).thenReturn(mockWriter);
        showtimePage.doGet(request, response);
        mockWriter.flush();
        assertEquals(HTML, outputStream.toString());
        mockWriter.close();
    }
}

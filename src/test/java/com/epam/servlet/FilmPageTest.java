package com.epam.servlet;

import com.epam.dao.entity.Film;
import com.epam.dao.entity.Showtime;
import com.epam.service.FilmService;
import com.epam.service.ShowtimeService;
import com.epam.servlet.ajax.GetFilm;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilmPageTest {

    @Test
    public void filmPageTest() throws IOException, ServletException {
        FilmPage filmPage = new FilmPage();
        String HTML = "<html >\n" +
                "<head>\n" +
                "    <title>testName</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\">\n" +
                "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\n" +
                "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>\n" +
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
                "            <li class=\"active\"><a href=\"films.jsp\">Films</a></li>\n" +
                "            <li><a href=\"schedule.jsp\">Schedule</a></li>\n" +
                "</ul>\n" +
                "<ul class=\"nav navbar-nav navbar-right\">\n" +
                "<li class=\"active\" > <a href=\"#\" onclick=\"changeLanguage('en')\" >En</a></li>\n" +
                "<li > <a href=\"#\" onclick=\"changeLanguage('uk')\" >Укр</a></li><li><a href=\"register.jsp\"><span class=\"glyphicon glyphicon-user\"></span>Sign Up</a></li>\n" +
                "<li><a href=\"login.jsp\"><span class=\"glyphicon glyphicon-log-in\"></span>Log In</a></li>\n" +
                "</ul>\n" +
                "</div>\n" +
                "</nav><div style=\"width: 720px; height: 405px; position: absolute; top: 10%; right: 10%\"  class=\"container\">\n" +
                "    <h2>Trailer</h2>\n" +
                "    <div class=\"embed-responsive embed-responsive-16by9\">\n" +
                "        <iframe  class=\"embed-responsive\" src=\"https://www.youtube.com/embed/12345678901\"></iframe>\n" +
                "    </div>\n" +
                "</div><div class=\"container\">\n" +
                "    <img  alt=\"Missing poster\" src=\"testPath\" width=\"400\" height=\"600\">\n" +
                "    <h3>testName</h3>\n" +
                "    <div class=\"container\">\n" +
                "        <h5>Director</h5>\n" +
                "        <div class=\"well\">testDirector</div>\n" +
                "    </div>\n" +
                "    <div class=\"container\">\n" +
                "        <h5>Genre</h5>\n" +
                "        <div class=\"well\">testGenre</div>\n" +
                "    </div>\n" +
                "    <div class=\"container\">\n" +
                "        <h5>Running time</h5>\n" +
                "        <div class=\"well\">10</div>\n" +
                "    </div>\n" +
                "    <div class=\"container\">\n" +
                "        <h5>Description</h5>\n" +
                "        <div class=\"well\">testDesc</div>\n" +
                "    </div>\n" +
                "</div><div class=\"container\"><label for=\"showtime\">Available showtimes</label><form id=\"showtime\" role=\"presentation\">\n" +
                "<button class=\"btn btn-block btn-info\" formmethod=\"get\" formaction=\"null/ShowtimePage\" name=\"id\" value=\"1\">"+LocalDate.now()+"  10:10:10-12:12:12</button>\n" +
                "</form></div></body>";

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


        Showtime showtime1 = new Showtime();
        showtime1.setDate(Date.valueOf(LocalDate.now()));
        showtime1.setId(1L);
        showtime1.setStatus("planned");
        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("A1","vacant");
        treeMap.put("A2","vacant");
        showtime1.setSeats(treeMap);
        showtime1.setFilmId(1L);
        showtime1.setStartTime(Time.valueOf("10:10:10"));
        showtime1.setEndTime(Time.valueOf("12:12:12"));


        List<Showtime> showtimeList = new ArrayList<>();
        showtimeList.add(showtime1);

        when(showtimeService.getShowtimeForFilm(anyLong())).thenReturn(showtimeList);

        when(response.getWriter()).thenReturn(mockWriter);
        filmPage.doGet(request, response);
        mockWriter.flush();
        assertEquals(HTML, outputStream.toString());
        mockWriter.close();
    }
}

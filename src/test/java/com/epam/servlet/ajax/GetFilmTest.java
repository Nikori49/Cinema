package com.epam.servlet.ajax;

import com.epam.dao.ConnectionPool;
import com.epam.dao.DBManager;
import com.epam.dao.entity.Film;
import com.epam.service.FilmService;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetFilmTest {






    @Test
    void test() throws ServletException, IOException {
        GetFilm getFilm = new GetFilm();
        String HTML = "<img class=\"img-responsive\" alt=\"Missing poster\" width=\"300\" height=\"450\" src=\"amogus\">";
        FilmService filmService = mock(FilmService.class);
        Film film = new Film();
        film.setPosterImgPath("amogus");
        when(filmService.getFilm(anyLong())).thenReturn(film);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);
        PrintWriter mockWriter = new PrintWriter(outputStream);
        ServletContext servletContext = mock(ServletContext.class);
        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getParameter("film")).thenReturn("1");
        when(servletContext.getAttribute("filmService")).thenReturn(filmService);
        when(response.getWriter()).thenReturn(mockWriter);
        getFilm.doGet(request, response);
        mockWriter.flush();
        assertEquals(String.format(HTML, "amogus"), outputStream.toString());
        mockWriter.close();
    }
}

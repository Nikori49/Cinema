package com.epam.servlet.command;

import com.epam.dao.entity.Film;
import com.epam.dao.entity.Showtime;
import com.epam.service.FilmService;
import com.epam.service.ShowtimeService;
import org.junit.jupiter.api.Test;
import org.mockito.ReturnValues;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrintStatsCommandTest {

    @Test
    public void printStatsCommandTest() throws IOException, ServletException {

        String pdf = "%PDF-1.4\n" +
                "%����\n" +
                "1 0 obj\n" +
                "<<\n" +
                "/Type /Catalog\n" +
                "/Version /1.4\n" +
                "/Pages 2 0 R\n" +
                ">>\n" +
                "endobj\n" +
                "2 0 obj\n" +
                "<<\n" +
                "/Type /Pages\n" +
                "/Kids [3 0 R]\n" +
                "/Count 1\n" +
                ">>\n" +
                "endobj\n" +
                "3 0 obj\n" +
                "<<\n" +
                "/Type /Page\n" +
                "/MediaBox [0.0 0.0 612.0 792.0]\n" +
                "/Parent 2 0 R\n" +
                "/Contents 4 0 R\n" +
                "/Resources 5 0 R\n" +
                ">>\n" +
                "endobj\n" +
                "4 0 obj\n" +
                "<<\n" +
                "/Length 231\n" +
                "/Filter /FlateDecode\n" +
                ">>\n" +
                "stream\n" +
                "x�m��n�0\u0010E���Y�\u0005�\u001E� X�궪�\u0003\u0016\u0019T\u0017|bR2-��I\\\u0013P�\u0017~̹w���ހ���j�\u0015�nQ\"�JMP���7��j��L83��J����+�3�_�\u001B�\u000B�+��o8\u001EN�r�\u001Bl�7\u001E��}\n" +
                "\u001B�4t�m��[��3�q�D�E�4��ZH/�zm\u0003S�3��n���\u000E�>�/\u0019�b��u\u001C\u001A��\u0001Ya�Xa͠���3�\"�}��(p\u000B\u001CNTgI�cIa�B�O�iחű����Ě�Z��\u007F�˩�\u0019�\u0003��m�\n" +
                "endstream\n" +
                "endobj\n" +
                "5 0 obj\n" +
                "<<\n" +
                "/Font 6 0 R\n" +
                ">>\n" +
                "endobj\n" +
                "6 0 obj\n" +
                "<<\n" +
                "/F1 7 0 R\n" +
                ">>\n" +
                "endobj\n" +
                "7 0 obj\n" +
                "<<\n" +
                "/Type /Font\n" +
                "/Subtype /Type1\n" +
                "/BaseFont /Times-Roman\n" +
                "/Encoding /WinAnsiEncoding\n" +
                ">>\n" +
                "endobj\n" +
                "xref\n" +
                "0 8\n" +
                "0000000000 65535 f\n" +
                "0000000015 00000 n\n" +
                "0000000078 00000 n\n" +
                "0000000135 00000 n\n" +
                "0000000247 00000 n\n" +
                "0000000552 00000 n\n" +
                "0000000585 00000 n\n" +
                "0000000616 00000 n\n" +
                "trailer\n" +
                "<<\n" +
                "/Root 1 0 R\n" +
                "/ID [<E0DC877F9797127EC8460DF9C13D2DFE> <E0DC877F9797127EC8460DF9C13D2DFE>]\n" +
                "/Size 8\n" +
                ">>\n" +
                "startxref\n" +
                "715\n" +
                "%%EOF\n";

        pdf = pdf.substring(0, 300);

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
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("A1", "vacant");
        treeMap.put("A2", "vacant");
        showtime1.setSeats(treeMap);
        showtime1.setFilmId(1L);
        showtime1.setStartTime(Time.valueOf("10:10:10"));
        showtime1.setEndTime(Time.valueOf("12:12:12"));

        PrintStatsCommand printStatsCommand = new PrintStatsCommand(filmService, showtimeService);


        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("loggedUser")).thenReturn(null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(2048);
        PrintWriter mockWriter = new PrintWriter(outputStream);
        ServletContext servletContext = mock(ServletContext.class);
        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getParameter("month")).thenReturn("2222-10-10");
        when(servletContext.getAttribute("filmService")).thenReturn(filmService);
        when(servletContext.getAttribute("showtimeService")).thenReturn(showtimeService);


        List<Showtime> showtimeList = new ArrayList<>();
        showtimeList.add(showtime1);


        Date date = mock(Date.class);


        when(showtimeService.getShowtimeForMonth(date)).thenReturn(showtimeList);

        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        };

        when(response.getOutputStream()).thenReturn(servletOutputStream);
        printStatsCommand.execute(request, response);
        mockWriter.flush();
        assertEquals(pdf, outputStream.toString().substring(0, 300));
        mockWriter.close();

    }
}

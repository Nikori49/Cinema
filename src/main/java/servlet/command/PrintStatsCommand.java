package servlet.command;

import DB.DBManager;
import DB.entity.Film;
import DB.entity.Showtime;
import DB.exception.DBException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import service.FilmService;
import service.ShowtimeService;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Command which execute method creates pdf file with requested month report.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
public class PrintStatsCommand implements Command {
    private final FilmService filmService;
    private final ShowtimeService showtimeService;

    public PrintStatsCommand(FilmService filmService, ShowtimeService showtimeService) {
        this.filmService = filmService;
        this.showtimeService = showtimeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringDate = request.getParameter("month");
        String language = (String) request.getSession().getAttribute("language");
        if (language == null) {
            language = "en";
        }
        Locale locale = new Locale(language);
        List<Film> filmList = filmService.getAllFilms();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("General", locale);
        List<Showtime> showtimeList = null;
        showtimeList = showtimeService.getShowtimeForMonth(Date.valueOf(stringDate));
        showtimeList.sort((o1, o2) -> {
            if (o1.getDate().compareTo(o2.getDate()) == 0) {
                return (int) (o1.getStartTime().getTime() - o2.getStartTime().getTime());
            }
            return o1.getDate().compareTo(o2.getDate());
        });
        String fileName = stringDate.substring(0, 7);
        int totalTicketsSold = 0;
        for (Showtime showtime : showtimeList) {
            for (String string : showtime.getSeats().keySet()) {
                if (Objects.equals(showtime.getSeats().get(string), "occupied")) {
                    totalTicketsSold = totalTicketsSold + 1;
                }
            }
        }
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        int pageHeight = (int) page.getTrimBox().getHeight();
        int pageWidth = (int) page.getTrimBox().getWidth();


        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 30);
        contentStream.beginText();
        contentStream.newLineAtOffset(230, pageHeight - 30);
        contentStream.showText(fileName);
        contentStream.endText();

        contentStream.setFont(PDType1Font.TIMES_ROMAN, 15);
        contentStream.beginText();
        contentStream.newLineAtOffset(60, pageHeight - 50);
        contentStream.showText(resourceBundle.getString("label.totalShowtimes") + showtimeList.toArray().length + " " + resourceBundle.getString("label.totalTickets") + totalTicketsSold);
        contentStream.endText();

        contentStream.setStrokingColor(Color.BLACK);
        contentStream.setLineWidth(1);

        int initX = 60;
        int initY = pageHeight - 100;
        int cellHeight = 30;
        int cellWidth = 60;

        int colCount = 6;
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);

        for (int j = 0; j < colCount; j++) {
            if (j == 1) {
                contentStream.addRect(initX, initY, cellWidth + 60, cellHeight);
            } else {
                contentStream.addRect(initX, initY, cellWidth, cellHeight);
            }


            contentStream.beginText();
            contentStream.newLineAtOffset(initX + 10, initY + 10);
            if (j == 0) {
                contentStream.showText(resourceBundle.getString("label.date"));
            }
            if (j == 1) {
                contentStream.showText(resourceBundle.getString("label.film"));
            }
            if (j == 2) {
                contentStream.showText(resourceBundle.getString("label.startTime"));
            }
            if (j == 3) {
                contentStream.showText(resourceBundle.getString("label.endTime"));
            }
            if (j == 4) {
                contentStream.showText(resourceBundle.getString("label.seatsTaken"));
            }
            if (j == 5) {
                contentStream.showText(resourceBundle.getString("label.status"));
            }

            contentStream.endText();
            if (j == 1) {
                initX += cellWidth + 60;
            } else {
                initX += cellWidth;
            }
        }
        initX = 60;
        initY -= cellHeight;
        for (Showtime s : showtimeList) {
            for (int j = 0; j < colCount; j++) {
                if (j == 1) {
                    contentStream.addRect(initX, initY, cellWidth + 60, cellHeight);
                } else {
                    contentStream.addRect(initX, initY, cellWidth, cellHeight);
                }


                contentStream.beginText();
                contentStream.newLineAtOffset(initX + 10, initY + 10);
                if (j == 0) {
                    contentStream.showText(String.valueOf(s.getDate()));
                }
                if (j == 1) {
                    String filmName = "";
                    for (Film f : filmList) {
                        if (Objects.equals(s.getFilmId(), f.getId())) {
                            filmName = f.getName();
                        }
                    }
                    contentStream.showText(String.valueOf(filmName));
                }
                if (j == 2) {
                    contentStream.showText(String.valueOf(s.getStartTime()));
                }
                if (j == 3) {
                    contentStream.showText(String.valueOf(s.getEndTime()));
                }
                if (j == 4) {
                    int ticketsSold = 0;
                    for (String key : s.getSeats().keySet()) {
                        if (Objects.equals(s.getSeats().get(key), "occupied")) {
                            ticketsSold = ticketsSold + 1;
                        }
                    }
                    contentStream.showText(ticketsSold + "/288");
                }
                if (j == 5) {
                    contentStream.showText(s.getStatus());
                }

                contentStream.endText();
                if (j == 1) {
                    initX += cellWidth + 60;
                } else {
                    initX += cellWidth;
                }
            }
            initX = 60;
            initY -= cellHeight;
        }
        contentStream.stroke();

        contentStream.beginText();
        contentStream.newLineAtOffset(470, initY - 30);
        contentStream.showText(resourceBundle.getString("label.printed") + LocalDate.now());
        contentStream.endText();
        contentStream.close();
        response.setContentType("application/pdf");
        document.save(response.getOutputStream());
        document.close();
        return "manager.jsp";
    }
}

package com.epam.service;

import com.epam.annotation.Service;

import com.epam.dao.ShowtimeDAO;
import com.epam.dao.entity.Showtime;
import com.epam.dao.exception.DBException;

import java.sql.Date;
import java.util.List;

@Service
public class ShowtimeService {
    private final ShowtimeDAO showtimeDAO;

    public ShowtimeService(ShowtimeDAO showtimeDAO) {
        this.showtimeDAO = showtimeDAO;
    }

    public void createShowtime(Showtime showtime){
        try {
            showtimeDAO.insert(showtime);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Showtime> getShowtimeForDate(Date date){
        try {
            return showtimeDAO.getShowtimeForDate(date);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Showtime> getShowtimeForFilm(Long filmId){
        try {
            return showtimeDAO.getShowtimeForFilm(filmId);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Showtime getShowtime(Long showtimeId){
        try {
            return showtimeDAO.findById(showtimeId);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void cancelShowtime(Long showtimeId){
        try {
            showtimeDAO.cancelShowtime(showtimeId);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Showtime> getPlannedShowtime(){
        try {
            return showtimeDAO.getPlannedShowtimes();
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public String getSeatStatus(String seat,Long showtimeId){
        try {
            return showtimeDAO.getSeatStatus(seat,showtimeId);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Showtime> getShowtimeForMonth(Date firstDay){
        try {
            return showtimeDAO.getShowtimesForMonth(firstDay);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<List<Showtime>> getShowtimeForWeek(){
        try {
            return showtimeDAO.getShowtimesForWeek();
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void finishPastShowtime(){
        try {
            showtimeDAO.updatePastShowtimeStatuses();
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void updateSeatStatus(Long showtimeId,String seat,String status){
        try {
            showtimeDAO.updateSeatStatus(showtimeId,seat,status);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}

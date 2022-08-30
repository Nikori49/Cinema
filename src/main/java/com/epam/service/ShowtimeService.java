package com.epam.service;

import com.epam.dao.DBManager;
import com.epam.dao.entity.Showtime;
import com.epam.dao.exception.DBException;

import java.sql.Date;
import java.util.List;

public class ShowtimeService {
    private final DBManager dbManager;

    public ShowtimeService(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void createShowtime(Showtime showtime){
        try {
            dbManager.createShowTime(showtime);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Showtime> getShowtimeForDate(Date date){
        try {
            return dbManager.getShowtimeForDate(date);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Showtime> getShowtimeForFilm(Long filmId){
        try {
            return dbManager.getShowtimeForFilm(filmId);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Showtime getShowtime(Long showtimeId){
        try {
            return dbManager.getShowTime(showtimeId);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void cancelShowtime(Long showtimeId){
        try {
            dbManager.cancelShowtime(showtimeId);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Showtime> getPlannedShowtime(){
        try {
            return dbManager.getPlannedShowtimes();
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public String getSeatStatus(String seat,Long showtimeId){
        try {
            return dbManager.getSeatStatus(seat,showtimeId);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Showtime> getShowtimeForMonth(Date firstDay){
        try {
            return dbManager.getShowtimesForMonth(firstDay);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<List<Showtime>> getShowtimeForWeek(){
        try {
            return dbManager.getShowtimesForWeek();
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void finishPastShowtime(){
        try {
            dbManager.updatePastShowtimeStatuses();
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}

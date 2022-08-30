package DB;

import DB.entity.User;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

/**
 * Class housing a few utility methods.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
public class Utils {
    /**
     * Returns TreeMap object with keys from A1 to J24 and value "vacant".
     *
     * @return <code>TreeMap</code> object
     * @see TreeMap
     */
    public TreeMap<String, String> fillSeatMap() {
        TreeMap<String, String> seatMap = new TreeMap<>();

        for (char rowLetter = 'A'; rowLetter <= 'L'; rowLetter++) {
            for (int i = 1; i <= 24; i++) {
                seatMap.put(String.valueOf((rowLetter)) + i, ("vacant"));
            }
        }

        return seatMap;
    }

    /**
     * Returns <code>List</code> of date objects for the current week.
     *
     * @return <code>List</code> object
     * @see LocalDate,Date
     */
    public List<Date> getWeekDates() {
        List<Date> dateList = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);
        long dateLong = date.getTime();
        for (int i = 0; i < 7; i++) {
            dateList.add(new Date(dateLong));
            dateLong = dateLong + 86_400_000;
        }
        return dateList;
    }

    /**
     * Returns <code>List</code> of String object representing weekday indexes.
     *
     * @return <code>List</code> object
     * @see User,DBManager
     */
    public List<String> getWeekDays() {
        List<Date> dateList = getWeekDates();
        List<String> weekDays = new ArrayList<>();
        for (Date d : dateList) {
            DayOfWeek day = d.toLocalDate().getDayOfWeek();
            weekDays.add(String.valueOf(day.getValue()));
        }
        return weekDays;
    }

}

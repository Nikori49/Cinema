package DB;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class Utils {

    public static TreeMap<String, String> fillSeatMap() {
        TreeMap<String, String> seatMap = new TreeMap<>();

        for (char rowLetter = 'A'; rowLetter <= 'L'; rowLetter++) {
            for (int i = 1; i <= 24; i++) {
                seatMap.put(String.valueOf((rowLetter)) + i, ("vacant"));
            }
        }

        return seatMap;
    }

    public static List<Date> getWeekDates() {
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

    public static List<String> getWeekDays(){
        List<Date> dateList = getWeekDates();
        List<String> weekDays = new ArrayList<>();
        for (Date d:dateList) {
            DayOfWeek day = d.toLocalDate().getDayOfWeek();
            weekDays.add(String.valueOf(day.getValue()));
        }
        return weekDays;
    }

}

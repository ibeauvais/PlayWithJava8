package fr.xebia.xke.java7.step1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {

    public static Date parseDateTime(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.");
            return format.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("bad format " + date);
        }
    }

    public static Date parseDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("bad format " + date);
        }
    }

    public static int age(Date birthday, Date now) {
        Calendar calBirthday = Calendar.getInstance();
        calBirthday.setTime(birthday);

        Calendar calNow = Calendar.getInstance();
        calNow.setTime(now);

        int age = calNow.get(Calendar.YEAR) - calBirthday.get(Calendar.YEAR);
        if (calNow.get(Calendar.DAY_OF_YEAR) < calBirthday.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    public static Date dayDateWithTime(Date dayDate, int hour, int minute, int second) {
        Calendar calendarDayDate = Calendar.getInstance();
        calendarDayDate.setTime(dayDate);

        calendarDayDate.set(Calendar.HOUR, hour);
        calendarDayDate.set(Calendar.MINUTE, minute);
        calendarDayDate.set(Calendar.SECOND, second);

        return calendarDayDate.getTime();
    }

    public static Date addDuration(Date date, int minute) {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);

        calendarDate.add(Calendar.MINUTE, minute);

        return calendarDate.getTime();
    }

    public static boolean dayAreEquals(Date date1, Date date2) {
        Calendar calendarDay1 = Calendar.getInstance();
        calendarDay1.setTime(date1);

        Calendar calendarDay2 = Calendar.getInstance();
        calendarDay2.setTime(date2);

        return calendarDay1.get(Calendar.YEAR) == calendarDay2.get(Calendar.YEAR) &&
                calendarDay1.get(Calendar.DAY_OF_YEAR) == calendarDay2.get(Calendar.DAY_OF_YEAR);
    }
}
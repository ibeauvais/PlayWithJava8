package fr.xebia.xke.java7.step3;

import fr.xebia.xke.java7.domain.Appointment;
import fr.xebia.xke.java7.domain.MyCalendar;
import fr.xebia.xke.java7.step2.DateUtils;

import java.util.*;

public class CalendarManagement {

    private MyCalendar calendar;

    public CalendarManagement() {
        calendar = new MyCalendar();
    }

    public String createAppointment(String startDateTime, int durationInMinute, String title, String description) {
        Date start = DateUtils.parseDateTime(startDateTime);
        Date end = DateUtils.addDuration(start, durationInMinute);

        return createAppointment(title, description, start, end).getId();

    }

    public String createAppointmentForDay(String date, String title, String description) {
        Date dayDate = DateUtils.parseDate(date);

        Appointment appointment = createAppointment(title, description, updateDateWithTime(dayDate, 0, 0, 0), updateDateWithTime(dayDate, 23, 59, 59));
        appointment.setAllTheDay(true);

        return appointment.getId();

    }

    private Date updateDateWithTime(Date dayDate, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayDate);

        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        return calendar.getTime();
    }

    public List<Appointment> findAppointmentOfDay(Date day) {
        List<Appointment> appointments = new ArrayList<>();

        for (Appointment appointment : calendar.appointments()) {
            if (DateUtils.dayAreEquals(appointment.getStart(), day)) {
                appointments.add(appointment);
            }
        }

        Collections.sort(appointments, new Comparator<Appointment>() {
            public int compare(Appointment appointment1, Appointment appointment2) {
                return appointment1.getStart().compareTo(appointment2.getStart());
            }
        });
        return appointments;
    }

    public Appointment findNextAppointmentAfter(Date start) {
        List<Appointment> appointments = new ArrayList<>(calendar.appointments());

        Collections.sort(appointments, new Comparator<Appointment>() {
            public int compare(Appointment appointment1, Appointment appointment2) {
                return appointment1.getStart().compareTo(appointment2.getStart());
            }
        });

        for (Appointment appointment : appointments) {
            if (appointment.getStart().after(start)) {
                return appointment;
            }
        }

        return null;
    }

    public Appointment findAppointmentById(String id) {
        return calendar.getById(id);
    }

    private Appointment createAppointment(String title, String description, Date start, Date end) {
        Appointment appointment = new Appointment(start, end, title, description);

        calendar.addAppointment(appointment);

        return appointment;
    }


}

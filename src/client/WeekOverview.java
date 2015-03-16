package client;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by andrealouise on 12.03.15.
 */
public class WeekOverview {

    ArrayList<Appointment> appointments = new ArrayList<Appointment>();
    ArrayList<Appointment> weekAppointments = new ArrayList<Appointment>();
    int week;
    int year;
    ArrayList<Appointment> mondays = new ArrayList<Appointment>();
    ArrayList<Appointment> tuesdays = new ArrayList<Appointment>();
    ArrayList<Appointment> wednesdays = new ArrayList<Appointment>();
    ArrayList<Appointment> thursdays = new ArrayList<Appointment>();
    ArrayList<Appointment> fridays = new ArrayList<Appointment>();
    ArrayList<Appointment> saturdays = new ArrayList<Appointment>();
    ArrayList<Appointment> sundays = new ArrayList<Appointment>();

    ArrayList<ArrayList> weekdays = new ArrayList<ArrayList>();



    public WeekOverview(String username, int week, int year) {
        User u = User.getUserFromDB(username);
        this.appointments = u.getAppointmentsForUser(u);
        this.week = week;
        this.year = year;
    }


    public ArrayList<Appointment> getAppointmentsInWeek() {
        for (Appointment appointment : appointments) {
            String s = String.valueOf(appointment.getStart());
            String[] parts = s.split("-");
            String y = parts[0];
            String m = parts[1];


            String[] parts2 = parts[2].split(" ");
            String d = parts2[0];

            int year1 = Integer.valueOf(y);
            int month1 = Integer.valueOf(m) - 1;
            int date1 = Integer.valueOf(d);

            Calendar cal = Calendar.getInstance();

            cal.set(year1, month1, date1);

            int week_of_year = cal.get(Calendar.WEEK_OF_YEAR);

            if (week_of_year == this.week) {
                weekAppointments.add(appointment);
            }

        }
        return weekAppointments;
    }

    public int getWeekday(Appointment appointment) {

        String s = String.valueOf(appointment.getStart());
        String[] parts = s.split("-");
        String y = parts[0];
        String m = parts[1];

        String[] parts2 = parts[2].split(" ");
        String d = parts2[0];

        int year1 = Integer.valueOf(y);
        int month1 = Integer.valueOf(m);
        int date1 = Integer.valueOf(d);

        Calendar cal = Calendar.getInstance();

        System.out.println(year1);
        System.out.println(month1);
        System.out.println(date1);

        cal.set(year1, month1 + 1, date1);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);


        return dayOfWeek + 1;
    }

    public void printDay(String day) {

        String leftAlignFormat = "| %-25s | %-4d |%n";

        System.out.format("+--------------------+------+%n");
        String dayHeadline = String.format(("| %s    %n"), day);
        System.out.printf(dayHeadline);
        System.out.format("+--------------------+------+%n");


        if (day == "monday") {
            for (Appointment a : mondays){
                System.out.println(a);
            }
        }

        if (day == "tuesday") {
            for (Appointment a : tuesdays){
                System.out.println(a);
            }
        }

        if (day == "wednesday") {
            for (Appointment a : wednesdays){
                System.out.println(a);
            }
        }

        if (day == "thursday") {
            for (Appointment a : thursdays){
                System.out.println(a);
            }
        }
        if (day == "friday") {
            for (Appointment a : fridays){
                System.out.println(a);
            }
        }

        if (day == "saturday") {
            for (Appointment a : saturdays){
                System.out.println(a);
            }
        }

        if (day == "sunday") {
            for (Appointment a : sundays) {
                System.out.println(a);
            }
        }
    }

    public String toString() {

        this.getAppointmentsInWeek();
        this.sortDays();

        this.printDay("monday");
        this.printDay("tuesday");
        this.printDay("tuesday");
        this.printDay("wednesday");
        this.printDay("thursday");
        this.printDay("friday");
        this.printDay("saturday");
        this.printDay("sunday");

    return "";
    }


    public void sortDays(){
        for (Appointment a: weekAppointments){
            int weekday = this.getWeekday(a);
            System.out.println(weekday);
            if (weekday == 1){
                mondays.add(a);
            }
            else if (weekday == 2){
                tuesdays.add(a);
            }
            else if (weekday == 3){
                wednesdays.add(a);
            }
            else if (weekday == 4){
                thursdays.add(a);
            }
            else if (weekday == 5){
                fridays.add(a);
            }
            else if (weekday == 6){
                saturdays.add(a);
            }
            else if (weekday == 7){
                sundays.add(a);
            }
        }
    }
}
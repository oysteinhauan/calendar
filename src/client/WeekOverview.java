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
        System.out.println(weekAppointments);
        return weekAppointments;
    }

    public String toString() {
        this.getAppointmentsInWeek();
        String returnstring = "";
        for (Appointment appointment : weekAppointments) {
            returnstring += ("\nSubject: " + appointment.subject + "\nDescription: " + appointment.description + "\nRoom: " + appointment.room + "\nStart: " + appointment.start + "\nEnd: " + appointment.end + "\n\n");
        }

        if (weekAppointments.size() == 0) {
            returnstring += "You don't have any appointments that week.";
        }
        return returnstring;
    }
}

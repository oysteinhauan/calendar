package client;

import java.util.ArrayList;

/**
 * Created by oysteinhauan on 24/02/15.
 */
public class Calendar {

    private User user = new User();
    private ArrayList<Appointment> appointments;

    public Calendar(String username) {
        this.user = user.getUserFromDB(username);
        appointments = user.getAppointmentsForUser(user);
    }

    public void viewCalendar(){
        for (Appointment appointment : appointments) {
            System.out.println(appointment.toString());
        }
    }
}

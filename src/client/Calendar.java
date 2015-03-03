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
        System.out.println("**** Welcome! ****");
        System.out.println("This is " + user.getUsername() + "s appointments:");
        System.out.println("   ");
        for (Appointment appointment : appointments) {
            System.out.println("Subject: " + appointment.getSubject());
            System.out.println("Description: " + appointment.getDescription());
            System.out.println("Room: " + appointment.getRoomId());
            System.out.println("Start: " + appointment.getStart());
            System.out.println("End: " + appointment.getEnd());
            System.out.println("__________________________");
            System.out.println("  ");

        }
    }
}

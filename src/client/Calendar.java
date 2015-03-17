package client;

import database.Database;

import java.util.ArrayList;

/**
 * Created by oysteinhauan on 24/02/15.
 */
public class Calendar {

    public User user = new User();
    private ArrayList<Appointment> appointments;
    private Group group;

    public Calendar(String username, Database db) {


        this.user = user.getUserFromDB(username, db);
        appointments = user.getAppointmentsForUser(user, db);
        group = null;
    }

    public Calendar(Group group, Database db){

        //ny konstruktør som skal hente ut avtalene til en gruppe.
        user = null;
        this.group = group;
        appointments = group.getAppointmentsForGroup(group, db);

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

    public void viewGroupCalendar(){
        System.out.println("**** Welcome! ****");
        System.out.println("This is " + group.getGroupname() + "s appointments:");
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

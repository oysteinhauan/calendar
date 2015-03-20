package test;

import client.Appointment;
import client.User;
import database.Database;

/**
 * Created by Henrik on 06.03.2015.
 */
public class UserNotificationTest {

    public static void main(String[] args) {

        Appointment ap = Appointment.getAppointment(48);
        User participant = User.getUserFromDB("henloef");
        User owner = User.getUserFromDB(ap.getOwner());
        System.out.println(owner.getUsername());
        Database db = new Database();
        db.connectDb("all_s_gruppe40", "qwerty");
        //ap.inviteAttendant("mummi", db);
       // ap.inviteAttendant("thebastard", db);
//        ap.addAttendant("oddmrog", db);
//        ap.addAttendant("andrealr", db);
        ap.sendAppointmentCanceledNotification();


        //ap.sendAppointmenUpdateNotification();
//        System.out.println(owner.getUsername());
  //      ap.inviteAttendant("mummi");
 //       ap.addAttendant("henloef");
        //ap.sendAppointmenUpdateNotification();
//       ap.addAttendant(owner.getUsername());
        participant.fetchNotifications(db);
        System.out.println("\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪ ");
        System.out.println("\t\tYou have " + participant.getNumberOfNewNotifications() + " new notification(s)!");
        System.out.println("\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪\n ");
        db.closeConnection();



/*        Notification n = Notification.getNotificationFromDB(20);
        System.out.println(n.toString());

        ap.inviteAttendant(participant.getUsername());
        participant.fetchNotifications();


        System.out.println("\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪ ");
        System.out.println("   \t\tYou have " + participant.getNumberOfNewNotifications() + " new notification(s)!");
        System.out.println("\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪\n ");


        participant.replyToInvite(participant.getFirstNotification());


        owner.fetchNotifications();
        System.out.println("\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪ ");
        System.out.println("   \t\tYou have " + owner.getNumberOfNewNotifications() + " new notification(s)!");
        System.out.println("\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪\n ");*/




    }


}

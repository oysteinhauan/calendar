package test;

import client.Appointment;
import client.User;

/**
 * Created by Henrik on 06.03.2015.
 */
public class UserNotificationTest {

    public static void main(String[] args) {
        Appointment ap = Appointment.getAppointment(37);
        User participant = User.getUserFromDB("henloef");
        User owner = User.getUserFromDB(ap.getOwner());
        System.out.println(owner.getUsername());
        ap.inviteAttendant("mummi");
//        ap.addAttendant("henloef");
//        ap.sendAppointmenUpdateNotification();
//        ap.addAttendant(owner.getUsername());

        System.out.println("\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪ ");
        System.out.println("   \t\tYou have " + owner.getNumberOfNewNotifications() + " new notification(s)!");
        System.out.println("\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪\n ");



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

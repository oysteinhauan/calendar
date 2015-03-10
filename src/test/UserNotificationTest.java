package test;

import client.Appointment;
import client.User;
import notification.InviteNotification;
import notification.Notification;

/**
 * Created by Henrik on 06.03.2015.
 */
public class UserNotificationTest {

    public static void main(String[] args) {
        Appointment ap = Appointment.getAppointment(9);
        User participant = User.getUserFromDB("henloef");
        User owner = User.getUserFromDB(ap.getOwner());

        ap.inviteAttendant(participant.getUsername());
        Notification n = new InviteNotification("henloef", "oddmrog" , 9);
        participant.addNotification(n);


        System.out.println("\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪ ");
        System.out.println("   \t\tYou have " + participant.getNumberOfNotifications() + " new notification(s)!");
        System.out.println("\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪\n ");



        participant.replyToInvite(participant.getFirstNotification());


    }


}

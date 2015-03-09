package notification;

import client.Appointment;

/**
 * Created by Henrik on 09.03.2015.
 */
public class ReplyFromInvitedUserNotification extends Notification{



    boolean replyFromInvitedUser;

    ReplyFromInvitedUserNotification(String recieverUsername, String senderUsername, int appointmentId, boolean replyFromInvitedUser){
        setNotificationType();
        setRecieverUsername(recieverUsername);
        setAppointmentId(appointmentId);
        setMessage();
        setSenderUsername(senderUsername);
        this.replyFromInvitedUser = replyFromInvitedUser;
    }

    @Override
    public void setNotificationType() {
        notificationType = 2;
    }

    @Override
    public void setMessage() {
        Appointment appointment = Appointment.getAppointment(appointmentId);
        message = ("" + senderUsername + " has " + replyFromInvitedUserToString() + " your invite to attend the appointment: " + appointment.toString() + "");

    }

    String replyFromInvitedUserToString() {
        if (replyFromInvitedUser){
            return "accepted";
        } else {
            return "declined";
        }
    }
}

package notification;

import client.Appointment;

/**
 * Created by Henrik on 09.03.2015.
 */
public class ReplyFromInvitedUserNotification extends Notification{

    boolean replyFromInvitedUser;

    public ReplyFromInvitedUserNotification(String recieverUsername, String senderUsername, int appointmentId, boolean replyFromInvitedUser){
        setNotificationType();
        setRecipientUsername(recieverUsername);
        setAppointmentId(appointmentId);
        setSenderUsername(senderUsername);
        setMessage();
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

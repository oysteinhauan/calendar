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
        this.replyFromInvitedUser = replyFromInvitedUser;
        setMessage();

    }

    @Override
    public void setNotificationType() {
        notificationType = 2;
    }

    @Override
    public void setMessage() {
        Appointment appointment = Appointment.getAppointment(appointmentId);
        message = ("" + senderUsername + " har " + replyFromInvitedUserToString() + " din invitasjon til å delta på: " + appointment.toString() + "");
    }

    String replyFromInvitedUserToString() {
        if (replyFromInvitedUser){
            return "godtatt";
        } else {
            return "avslått";
        }
    }
}

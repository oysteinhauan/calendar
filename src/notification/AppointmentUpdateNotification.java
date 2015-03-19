package notification;

import client.Appointment;

/**
 * Created by Henrik on 09.03.2015.
 */
public class AppointmentUpdateNotification extends Notification {

    public AppointmentUpdateNotification(String senderUsername, String recieverUsername, int appointmentId){
        setNotificationType();
        setAppointmentId(appointmentId);
        setRecipientUsername(recieverUsername);
        setSenderUsername(senderUsername);
        setMessage();
        handled = false;
    }

    @Override
    public void setNotificationType() {
        notificationType = 3;
    }

    @Override
    public void setMessage() {
        Appointment appointment = Appointment.getAppointment(appointmentId);
        message = ("" + senderUsername + " har oppdatert en avtale du skulle, eller var invitert til å, delta på.\n Her er dn oppdaterte avtalen: " + appointment.toString() + "");
    }
}

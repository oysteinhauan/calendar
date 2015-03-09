package notification;

import client.Appointment;

/**
 * Created by Henrik on 09.03.2015.
 */
public class AttendanceCanceledNotification extends Notification{

    AttendanceCanceledNotification(String recieverUsername, String senderUsername, int appointmentId){
        setNotificationType();
        setAppointmentId(appointmentId);
        setRecieverUsername(recieverUsername);
        setSenderUsername(senderUsername);
        setMessage();
    }

    @Override
    public void setNotificationType() {
        notificationId = 4;
    }

    @Override
    public void setMessage() {
        Appointment appointment = Appointment.getAppointment(appointmentId);
        message = ("" + senderUsername + " is no longer able to attend your appointment: " + appointment.toString()+ ".");
    }
}

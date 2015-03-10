package test;

import notification.InviteNotification;
import notification.Notification;

/**
 * Created by Henrik on 10.03.2015.
 */
public class NotificationTest {

    public static void main(String[] args) {
        Notification n = new InviteNotification("henloef", "oddmrog" , 9);
        System.out.println(n.getMessage());
        n.createNotificationInDB();
    }

}

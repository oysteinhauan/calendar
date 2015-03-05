package client;

import database.Database;

/**
 * Created by andrealouise on 24.02.15.
 */
public class Group implements AppointmentListener {

    @Override
    public void appointmentNotification(Appointment appointment) {
        //send notification til alle medlemmer
    }

    String groupname;
    Database db;
    String sql;

    public Group(String groupname) {
        this.groupname = groupname;
    }


    public void createGroup(Group group) {
        db = new Database("all_s_gruppe40_calendar");
        db.connectDb("all_s_gruppe40", "qwerty");
        sql = ("INSERT INTO group_1 (name) values('" + (group.groupname) + "');");
        db.updateQuery(sql);
        db.closeConnection();
    }
}

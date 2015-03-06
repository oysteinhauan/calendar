package client;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by andrealouise on 24.02.15.
 */
public class Group implements AppointmentListener {

    @Override
    public void appointmentNotification(Appointment appointment) {
        //send notification til alle medlemmer
    }

    String groupname;
    int groupID;
    Database db;
    String sql;
    ArrayList<String> members = new ArrayList<String>();

    public Group(String groupname) {
        this.groupname = groupname;
    }

    public Group() {

    }
    public void createGroup(Group group) {
        db = new Database("all_s_gruppe40_calendar");
        db.connectDb("all_s_gruppe40", "qwerty");
        sql = ("INSERT INTO group_1 (name) values('" + (group.groupname) + "');");
        db.updateQuery(sql);
        db.closeConnection();
    }

    //public getGroupnameFromDB() {


    //public getGroupIDFromDB() {


    public static void addMember(User user, int groupID) {
        Database db2 = new Database("all_s_gruppe40_calendar");
        db2.connectDb("all_s_gruppe40", "qwerty");
        String sql = ("INSERT INTO userGroup (username, groupID) values ('" + user.getUsername() + "', " + groupID + ");");
        db2.updateQuery(sql);
        db2.closeConnection();
    }

    public static void removeMember(User user, int groupID) {
        Database db2 = new Database("all_s_gruppe40_calendar");
        db2.connectDb("all_s_gruppe40", "qwerty");
        String sql = ("DELETE FROM userGroup WHERE username = '" + user.getUsername() + "' AND groupID = " + groupID + ";");
        db2.updateQuery(sql);
        db2.closeConnection();
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupNameFromDB(int groupID) {
        try {
            db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            String sql = ("SELECT name FROM group_1 WHERE groupID = " + groupID +" ;");
            ResultSet rs = db.readQuery(sql);
            while(rs.next()) {
                this.groupname = rs.getString("name");
            }
            db.closeConnection();
            rs.close();

            }
        catch (SQLException e){

        }
        return this.groupname;
    }

    public int getGroupIDFromDB(String groupname) {
        try {
            db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            String sql = ("SELECT groupID FROM group_1 WHERE name = '" + groupname +"' ;");
            ResultSet rs = db.readQuery(sql);
            while(rs.next()) {
                this.groupID = rs.getInt("groupID");
            }
            db.closeConnection();
            rs.close();

        }
        catch (SQLException e){

        }
        return this.groupID;
    }


    public ArrayList<String> getMembers(int groupID) {

        try {
            Database db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            String sql = ("SELECT username FROM userGroup WHERE groupId = " + groupID + ";");
            ResultSet rs = db.readQuery(sql);
            while (rs.next()) {

                members.add(rs.getString("username"));
            }

            db.closeConnection();
            rs.close();
        } catch (SQLException e) {

        }
        return members;

    }

    public ArrayList<String> getMembers(String groupname) {
        this.groupID = getGroupIDFromDB(groupname);
        try {
            Database db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            String sql = ("SELECT username FROM userGroup WHERE groupId = " + this.groupID + ";");
            ResultSet rs = db.readQuery(sql);
            while (rs.next()) {

                members.add(rs.getString("username"));
            }

            db.closeConnection();
            rs.close();
        } catch (SQLException e) {

        }
        return members;

    }

}

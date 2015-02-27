package client;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Henrik on 24.02.2015.
 */


public class Room {

    Database db;
    int roomID;
    String roomName, sql;
    ResultSet rs;

    int size;
    boolean vacant;

    public Room(int roomId, String roomName, int size, boolean vacant){
        this.roomID = roomId;
        this.roomName = roomName;
        this.size = size;
        this.vacant = vacant;

    }

    public void createRoom(Room room){
        db = new Database();
        sql = "insert into room values (" + String.valueOf(room.roomID) +
                ", " + String.valueOf(room.size) + ", " + String.valueOf(room.vacant) + ", "
                + room.roomName + ");";
        db.connectDb("all_s_gruppe40", "qwerty");
        db.updateQuery(sql);
        db.closeConnection();

    }

    public void setRoomFromDb(int roomID){
        try {
            db = new Database();
            db.connectDb("all_s_gruppe40", "qwerty");
            sql = "select * from room where roomId = " + String.valueOf(roomID) + ";";
            ResultSet rs = db.readQuery(sql);
            while (rs.next()){
                //setter shit

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
    }






}

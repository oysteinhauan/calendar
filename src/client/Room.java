package client;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by Henrik on 24.02.2015.
 */


public class Room {

    int roomID;
    int seats;
    String roomName;
    Database db;
    ResultSet rs;
    String sql;

    public Room() {
    }

    public Room(int seats, String roomName ){
        setSeats(seats);
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setRoomID(int roomID){
        this.roomID = roomID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setSeats(int seats){
        this.seats = seats;
    }

    public int getSeats() {
        return seats;
    }




    public void createRoom(Room room){

            db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            sql = ("INSERT INTO room (size, roomName) values  (" + room.seats  +  ", '" + room.roomName + "');");
            db.updateQuery(sql);
            db.closeConnection();

    }


    //bor kanskje sjekke om roomID finnes
    public void deleteRoom(int roomID){
        db = new Database("all_s_gruppe40_calendar");
        db.connectDb("all_s_gruppe40", "qwerty");
        sql = "DELETE FROM room WHERE roomId = " + String.valueOf(roomID) + ";";
        db.updateQuery(sql);
        db.closeConnection();

    }


    public Room getRoom(int roomID) {
        try {
            db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            sql = "SELECT * FROM room where roomID = " + String.valueOf(roomID) + ";";
            ResultSet rs = db.readQuery(sql);
            while(rs.next()) {
                this.roomID = roomID;
                this.seats = rs.getInt("size");
                this.roomName = rs.getString("roomName");
                }
            db.closeConnection();
            rs.close();


            }
        catch (SQLException e){

            }

        return this;
    }


    //bor kanskje sjekke at roomId faktisk ligger i db, ha en sjekk aller forst
    public void updateRoom(int roomID, int seats, String roomName){
        db = new Database("all_s_gruppe40_calendar");
        db.connectDb("all_s_gruppe40", "qwerty");
        sql = "UPDATE room SET size = " + String.valueOf(seats) + ", roomName = '" + roomName + "' WHERE roomID = " + String.valueOf(roomID) + ";";
        db.updateQuery(sql);
        db.closeConnection();

    }



}

package client;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oysteinhauan on 24/02/15.
 */
public class Login {

    Database db;
    String password, sql;
    boolean loggedin = false;

    public Login(){

    }

    public Login(String username){
        db = new Database("all_s_gruppe40_calendar");
        db.connectDb("all_s_gruppe40", "qwerty");
        sql = "SELECT password FROM user WHERE username = '" + username + "';";
        ResultSet rs = db.readQuery(sql);
        try {
            if(rs.next()) {
                this.password = rs.getString("password");
                rs.close();
            }

            else {
                throw new IllegalArgumentException();
            }

        } catch (SQLException e){
            e.printStackTrace();

        } finally{
            db.closeConnection();
        }

    }

    public void login(String password){
        if (!password.equals(this.password)){
            throw new IllegalArgumentException("Incorrect password");
        }
        else loggedin = true;
    }






}

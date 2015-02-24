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

    public Login(String username){
        try {
            db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            sql = "SELECT password FROM users WHERE username = '" + username + "';";
            ResultSet rs = db.readQuery(sql);
            while(rs.next()) {
                if (rs.getString("password") == null) {
                    throw new IllegalArgumentException("Invalid username");
                } else {
                    this.password = rs.getString("password");
                }
            }

        } catch (SQLException e){
        }

    }

    public void login(String password){
        if (!password.equals(this.password)){
            throw new IllegalArgumentException("Incorrect password");
        }
        else loggedin = true;
    }

    public String getPassword(){
        return this.password;
    }




}

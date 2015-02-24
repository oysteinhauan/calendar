package database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oysteinhauan on 24/02/15.
 */
public class DatabaseTester {

    public static void main(String[] args) {

        Database db = new Database("all_s_gruppe40_calendar");
        db.connectDb("all_s_gruppe40","qwerty");
        ResultSet rs = db.readQuery("select password from users where username = 'oysteibh'");
        try{
            while(rs.next()) {
                System.out.println(rs.getString("password"));
                System.out.println("lol2");
            }
        } catch (SQLException e){
            System.out.println("lol");
    }
}}


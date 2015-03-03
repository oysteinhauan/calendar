package client;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by oysteinhauan on 24/02/15.
 */
public class User {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String position;
    database.Database db;
    String sql;

    public User(){

    }

    public User(String username, String password, String firstname,
                String lastname, String email, String position) {
        setUsername(username);
        setPassword(password);
        setFirstname(firstname);
        setLastname(lastname);
        setEmail(email);
        setPosition(position);

    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullName(){
        return "" + getFirstname() + " " + getLastname();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public void addUserToDB(){
        db = new Database("all_s_gruppe40_calendar");
        sql = "INSERT INTO user VALUES( '" + getUsername() + "', '" + getPassword() + "', '" + getFirstname() + "', '"
                + getLastname() + "', '" + getPosition() + "', '" + getEmail() + "');";
        db.connectDb("all_s_gruppe40", "qwerty");
        db.updateQuery(sql);
        db.closeConnection();
    }

    public User getUserFromDB(String username){

        try {
            db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            sql = "SELECT * FROM user WHERE username='" + username + "';";
            ResultSet rs = db.readQuery(sql);

            while (rs.next()){
                setUsername(username);

                this.firstname = rs.getString("firstname");
                this.lastname = rs.getString("lastname");
                this.position = rs.getString("position");
                this.email =rs.getString("email");
            }
            db.closeConnection();
            rs.close();

        }
        catch (SQLException e){

        }


        return this;

    }
}

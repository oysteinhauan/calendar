package client;

import database.Database;

import java.sql.ResultSet;
import java.util.Date;

/**
 * Created by oysteinhauan on 24/02/15.
 */
public class Person {


    private String username, password, firstname, lastname, email, position;

    private Date dateOfBirth;

    public Person(String username, String password, String firstname,
                  String lastname, String email, String position, Date dateOfBirth) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.position = position;
        this.dateOfBirth = dateOfBirth;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
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
}

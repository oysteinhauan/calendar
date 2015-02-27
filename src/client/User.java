package client;


import java.util.Date;

/**
 * Created by oysteinhauan on 24/02/15.
 */
public class User {

    private String username, password, firstname, lastname, email, position;
    database.Database db;
    private Date dateOfBirth;




    //lager tomt Personobjekt
    public User(String username){

        this.username = username;
    }

//    public String getPassword() {
//        return password;
//    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

//    public boolean isValidUsername(String username){
//
//
//    }

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






}

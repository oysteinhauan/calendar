package client;

import database.Database;
import notification.Notification;
import notification.ReplyFromInvitedUserNotification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by oysteinhauan on 24/02/15.
 */
public class User{

    public String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String position;
    database.Database db;
    String sql;
    public ArrayList<Notification> notifications = new ArrayList<Notification>();

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


    public void addUserToDB(){
        db = new Database("all_s_gruppe40_calendar");
        sql = "INSERT INTO user VALUES( '" + getUsername() + "', '" + getPassword() + "', '" + getFirstname() + "', '"
                + getLastname() + "', '" + getPosition() + "', '" + getEmail() + "');";

        db.connectDb("all_s_gruppe40", "qwerty");
        try {
            db.updateQuery(sql);
        } catch(RuntimeException e){
            System.out.println("Something went haywire!");
        } finally {
            db.closeConnection();
        }
    }

    public ArrayList<Appointment> getAppointmentsForUser(User user){

        try {
            ArrayList<Integer> appIdList = new ArrayList<Integer>();
            ArrayList<Appointment> appList = new ArrayList<Appointment>();

            Database db = new Database();
            db.connectDb("all_s_gruppe40", "qwerty");
            String sql = "select appointment.appointmentId, start from userAppointment, appointment " +
                    "where username = '" + user.getUsername() + "' and appointment.appointmentId = userAppointment.appointmentId " +
                            "order by start;";
            ResultSet rs = db.readQuery(sql);
            while (rs.next()) {
                appIdList.add(rs.getInt("appointmentId"));
            }
            db.closeConnection();

            for (Integer id: appIdList){
                appList.add(Appointment.getAppointment(id));
            }
            return appList;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

//    public User getUserFromDB(String username){
//        //henter ut informasjonen om en bruker fra databasen, basert på burkernavnet som skrives
//        try {
//            db = new Database("all_s_gruppe40_calendar");
//            db.connectDb("all_s_gruppe40", "qwerty");
//            sql = "SELECT * FROM user WHERE username='" + username + "';";
//            ResultSet rs = db.readQuery(sql);
//
//            while (rs.next()){
//                setUsername(username);
//                this.firstname = rs.getString("firstname");
//                this.lastname = rs.getString("lastname");
//                this.position = rs.getString("position");
//                this.email =rs.getString("email");
//            }
//            db.closeConnection();
//            rs.close();
//
//        } catch (SQLException e){
//        }
//        return this;
//    }

    public static boolean existsCheck(String username){
       User user = getUserFromDB(username);
        return (user != null);
    }

    public static User getUserFromDB(String username){
        //henter ut informasjonen om en bruker fra databasen, basert på burkernavnet som skrives

        Database db;
        User user = null;
        try {
            db = new Database("all_s_gruppe40_calendar");
            db.connectDb("all_s_gruppe40", "qwerty");
            String sql = "SELECT * FROM user WHERE username='" + username + "';";
            ResultSet rs = db.readQuery(sql);

            while (rs.next()){
                user = new User(username, rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"),
                        rs.getString("email"), rs.getString("position"));
            }
            db.closeConnection();
            rs.close();

        } catch (SQLException e){
        }
        return user;
    }

    public static boolean usernameTaken(String username){
        Database db = new Database();
        db.connectDb();
        String sql = "select username from user where username = '" + username +"';";
        ResultSet rs = db.readQuery(sql);
        try {
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            db.closeConnection();
        }
        return false;

    }

    public void updateUserInfoInDB(String columnToUpdate, String updatedInfo){
        //skriv inn hvilken kolonne som skal få sin informasjon oppdatert, og hva den nye informasjonen skal være
        db = new Database("all_s_gruppe40_calendar");
        sql = "UPDATE user SET " + columnToUpdate + "='" + updatedInfo + "' WHERE username = '" + username + "';";
        db.connectDb("all_s_gruppe40", "qwerty");
        db.updateQuery(sql);
        db.closeConnection();
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





    public void deleteUserFromDb(String username) {
        db = new Database("all_s_gruppe40_calendar");
        sql = "DELETE FROM user WHERE username='" + username + "';";
        db.connectDb("all_s_gruppe40", "qwerty");
        db.updateQuery(sql);
        db.closeConnection();
    }

    public boolean isAdmin(){
        db = new Database();
        db.connectDb();
        sql = "select admin from user where username = '" + this.getUsername() +"';";
        ResultSet rs = db.readQuery(sql);
        try {
            while(rs.next()){
                if(rs.getInt(1) == 1){
                    return true;
                }
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Oops.");
            e.printStackTrace();
        } finally {
            db.closeConnection();

        }
        return false;

    }

    public static void setAdmin(String username){
        Database db;
        db = new Database();
        db.connectDb();
        String sql = "update user set admin = 1 where username = '" + username +"';";

        db.updateQuery(sql);
        db.closeConnection();
    }

    //NOTIFICATION

    public ArrayList<Notification> getNotificationsForUser(String username){
    try{
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        ArrayList<Integer> notificationIds = new ArrayList<Integer>();

        Database db = new Database();
        db.connectDb("all_s_gruppe40", "qwerty");
        String sql = "SELECT notificationId FROM notification" +
                " WHERE recipient = '" + username + "';";
        ResultSet rs = db.readQuery(sql);
        while (rs.next()) {
            notificationIds.add(rs.getInt("notificationId"));
        }
        db.closeConnection();

        for (Integer id: notificationIds){
            notifications.add(Notification.getNotificationFromDB(id));
        }
        return notifications;

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
    }

    public void fetchNotifications(){
        notifications.clear();
        notifications = getNotificationsForUser(username);
    }


    public void addNotification(Notification notification){
        notifications.add(notification);
    }

    public Notification getFirstNotification(){
        return notifications.get(0);
    }

    public int getNumberOfNewNotifications(){
        int size = 0;
        for (Notification notification: notifications) {
            if (!notification.isHandled()){
                size++;
            }
        }
        return size;
    }

    public void removeAppointmentNotification(Appointment appointment){
        notifications.remove(appointment);
    }



    public void replyToInvite(Notification inviteNotification){
        int swValue;
        boolean replied = false;
        Boolean reply = null;
        Appointment ap = Appointment.getAppointment(inviteNotification.getAppointmentId());
        Notification replyToInviteNotification;


            // Display menu graphics
            System.out.println("============================");
            System.out.println("|       YOUR OPTIONS       |");
            System.out.println("============================");
            System.out.println("|        1. Accept         |");
            System.out.println("|        2. Decline        |");
            System.out.println("============================");


           // Switch construct
        while(!replied){
            swValue = KeyIn.inInt("Select option: ");

            switch (swValue) {
                case 1:
                    System.out.println("Option 1 selected: You have accepted the invitation.\n\n");
                    reply = true;
                    ap.addAttendant(username);
                    replied = true;
                    inviteNotification.handle();
                    replyToInviteNotification = new ReplyFromInvitedUserNotification(ap.getOwner(), username, ap.appointmentId, reply);
                    replyToInviteNotification.createNotificationInDB();
                    System.out.println("" + ap.getOwner() + " will now be notified about your reply.");
                    break;
                case 2:
                    System.out.println("Option 2 selected: You have declined the invitation.\n\n");
                    reply = false;
                    replied = true;
                    inviteNotification.handle();
                    replyToInviteNotification = new ReplyFromInvitedUserNotification(ap.getOwner(), username, ap.appointmentId, reply);
                    replyToInviteNotification.createNotificationInDB();
                    System.out.println("" + ap.getOwner() + " will now be notified about your reply.\n\n");
                    break;
                default:
                    System.out.println("Invalid selection (ノಠ益ಠ)ノ彡┻━┻");
                    break;
                    // This break is not really necessary
            }
        }
    }

/*    public boolean replyToNotification(Appointment appointment){
        Scanner sc = new Scanner(System.in);
        Boolean replyToAppointer = null;

        int choiceEntry = -1;
        System.out.println("You have bee invited to attend the appointment:");
        System.out.println("****************************************************");
        System.out.println(" You have been invited to attend the appointment: ");
        System.out.println("      Subject: " + appointment.getSubject() + "         ");
        System.out.println("      Description: " + appointment.getDescription());
        System.out.println("      Room: " + appointment.getRoomId());
        System.out.println("      Start: " + appointment.getStart());
        System.out.println("      End: " + appointment.getEnd());
        System.out.println("****************************************************");
        do {
            System.out.println("============================");
            System.out.println("|       YOUR OPTIONS       |");
            System.out.println("============================");
            System.out.println("|        1. Accept         |");
            System.out.println("|        2. Decline        |");
            System.out.println("============================");
            try {
                choiceEntry = sc.nextInt();
            } catch (InputMismatchException e) {
                choiceEntry = -1;
            }


                switch (choiceEntry) {
                    case 1:
                        replyToAppointer = true;
                        break;
                    case 2:
                        replyToAppointer = false;
                        break;
                    default:
                        System.out.println("(╯°□°）╯︵ ┻━┻ Invalid reply.");
                        break;
                }

        }while (choiceEntry != 1 && choiceEntry != 2);


        removeAppointmentNotification(appointment);
        return replyToAppointer;*/
}


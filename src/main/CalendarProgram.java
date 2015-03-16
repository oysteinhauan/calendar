package main;

import client.*;
import database.Database;
import notification.Notification;

import java.io.Console;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by oysteinhauan on 10/03/15.
 */
public class CalendarProgram {

    public static Calendar calendar;
    public static String username;
    public static User user;

    public CalendarProgram(){
        this.user = null;
    }

    public void init() {

        Scanner scn = new Scanner(System.in);
        System.out.println("Wilkommen! Bitte schreiben sie Ihren Name!");
        Login login = new Login();
        User user;
        username = "";

        while (scn.hasNext()) {
            try {
                username = scn.nextLine();
                login = new Login(username);
                //clearConsole();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("\nInvalid usrname. Try again plz\n\n");
            }
        }

        System.out.println("\nPlease give me ur passwd!");

        while (true) {
            String password = scn.next();
            //String password = passwordMasker();
            try {
                login.login(password);
                this.user = User.getUserFromDB(username);
                //clearConsole();
                System.out.println("Welcome to our fantastic calendar, " + this.user.getFullName() + "\n\n\n\n");
                TimeUnit.SECONDS.sleep(2);
                clearConsole();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong password!");
            } catch (InterruptedException e) {
                System.out.println("How did you get here???");
            }
        }
        calendar = new Calendar(username);
    }

    public void run() {

        boolean loggedIn = true;

        while (loggedIn) {

            int swValue;
            Database db = new Database();
            db.connectDb("all_s_gruppe40", "qwerty");

            this.user.fetchNotifications();

            System.out.println("\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪ ");
            System.out.println("   \t\tYou have " + user.getNumberOfNewNotifications() + " new notification(s)!");
            System.out.println("\t♪┏(°.°)┛┗(°.°)┓┗(°.°)┛┏(°.°)┓ ♪\n ");




            System.out.println("hva vil du gjøre? bruk tallene for å navigere i menyene \n" +

                            "1. opprett / endre avtaler \n" +
                            "2. Vis en gruppekalender\n" +
                            "3. Se en annens private kalender\n" +
                            "4. Endre min brukerinfo\n" +
                            "5. Vis min personlige kalender\n" +
                            "6. Oprett ny bruker\n" +
                            "7. Logg ut\n\n"
            );

            if(this.user.isAdmin()){
                System.out.println("8. ADMIN");

            }

            swValue = KeyIn.inInt(" Select Option \n");
            Timestamp start, slutt;
            String subject, description;
            int antall;

            switch (swValue) {
                case 1:
                    clearConsole();
                    appNav();
                    continue;

                case 2:
                    clearConsole();

                    String groupname = KeyIn.inString("Vennligst skriv inn navnet paa gruppen du vil utforske!");
                    try {
                        int id = Group.getGroupIDFromDB(groupname);
                        Group group = Group.getGroup(id);
                        Calendar groupCalendar = new Calendar(group);
                        groupCalendar.viewGroupCalendar();
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid groupname. Try again.");
                    }
                    break;

                case 3:
                    clearConsole();

                    String otherUser = KeyIn.inString("hvem sin kalender vil du se? skriv inn brukernavnet\n\n");
                    if (User.existsCheck(otherUser)) {
                        Calendar otherCalendar = new Calendar(otherUser);
                        otherCalendar.viewCalendar();
                    } else {
                        System.out.println("ugyldig brukernavn");
                        continue;
                    }

                    break;

                case 4:
                    clearConsole();

                    editUserInfo(username);

                    break;

                case 5:
                    clearConsole();

                    calendar.viewCalendar();
                    System.out.println("1. Gå tilbake");

                    int c = Integer.valueOf((5 + "") + KeyIn.inInt("Select option"));

                    switch (c) {

                        case 51:
                            continue;
                    }
                case 6:
                    clearConsole();
                    System.out.println("-----------------------------------------");
                    for (Notification notification : this.user.notifications){
                        if (!notification.isHandled()){

                            System.out.println(notification.getMessage());
                            System.out.println("-----------------------------------------");
                            switch(notification.getNotificationType()){
                                case 1:
                                    this.user.replyToInvite(notification);
                                    break;
                                case 3:
                                    this.user.replyToInvite(notification);
                                    break;
                                default:
                                    notification.handle();
                                    break;
                            }
                        }

                    }
                    System.out.println("Do you want to handle your invitations");

                case 8:

                    if(!this.user.isAdmin()){
                        continue;
                    }
                    admin();
                    continue;

                case 7:
                    clearConsole();
                    System.out.printf("snx m8s!!!!1!\n\n\n\n");

                    db.closeConnection();
                    loggedIn = false;

                    break;
            }
        }

    }

    public void clearConsole(){
        System.out.println("\n\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n\n" +
                "\n");
    }

    public static void main(String[] args) {
        CalendarProgram cp = new CalendarProgram();
        cp.init();
        cp.run();
    }

    public static void createAppointment() {
        Timestamp start = Timestamp.valueOf(( "2015-" + KeyIn.inString("Legg inn avtaleinformasjon: starttidspunkt (MM-DD HH:MM)")) + ":00");
        //kjør sjekk her
        Timestamp timeNow = new Timestamp((new java.util.Date()).getTime());
        if (start.before(timeNow)) {
            throw new IllegalArgumentException("for tidlig!!!");
        }
        Timestamp slutt = Timestamp.valueOf(("2015-" + KeyIn.inString("Legg inn avtaleinformasjon: slutttidspunkt (MM-DD HH:MM)")) + ":00");
        //kjør sjekk her
        if (slutt.before(timeNow) || slutt.before(start)) {
            throw new IllegalArgumentException("for TIDLIG SA JEG!!");
        }
        String subject = KeyIn.inString("Legg inn subject:");
        String description = KeyIn.inString("Legg inn description:");
        int antall = KeyIn.inInt("Legg inn antall møtedeltakere");
        System.out.println(username);



        boolean useSystem;
        Appointment appointment;

        while (true) {

            int roomChoice = KeyIn.inInt("1. La systemet finne rom\n" +
                    "2. Avtalen er et annet sted\n");

            if (roomChoice == 1) {
                useSystem = true;
                appointment = Appointment.createAppointment(start, slutt, subject, description, antall, username, useSystem);
                break;

            } else if (roomChoice == 2) {
                useSystem = false;
                appointment = Appointment.createAppointment(start, slutt, subject, description, antall, username, useSystem);
                break;

            }
            System.out.println("prøv igjen!");
        }

        appointment.addAttendant(username);

        while (appointment.invitedUsers.size() + 1 < antall) {

            String bruker = KeyIn.inString("skriv inn username. 'cancel' to cancel");
            if (bruker.compareToIgnoreCase("cancel") == 0){
                break;
            }
            try {
                appointment.getRoom().getRoomName();
                appointment.inviteAttendant(bruker);
                System.out.println(bruker + "ble lagt til");
            } catch (IllegalArgumentException e){
                //dritt
                System.out.println("Try again.");
            }
        }
    }

    public void editAppointment() {


        boolean bol = true;
        boolean skip = false;
        int idToChange = -1;

        while (bol) {
            try {
                idToChange = KeyIn.inInt("Skriv inn avtaleID (0 for å gå tilbake):\n");
                if(idToChange == 0){
                    bol = false;
                    skip = true;
                    break;

                }
                Appointment app = Appointment.getAppointment(idToChange);
                if (!app.hasRecord(idToChange)) {
                    System.out.println("id'en finnes ikke, prøv igjen!!");
                } else if (!(Appointment.checkIfOwner(username, app, idToChange))) {
                    throw new IllegalArgumentException("Du må være eieren for å endre.");
                } else {
                    System.out.println("du må være eieren av en avtale for å endre på den!! prøv igjen!!!" + idToChange);
                }
            } catch (SQLException e) {
                System.out.println("blabla");
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                System.out.println("Du må være eieren for å kunne endre avtalen");
                e.printStackTrace();

            }
        }
        if (!skip) {

            Appointment appointmentToChange = Appointment.getAppointment(idToChange);

            boolean stay = true;

            while (stay) {
                System.out.println("1. Endre starttid\n" +
                        "2. Endre sluttid\n" +
                        "3. legg til deltaker\n" +
                        "4. fjern deltaker\n" +
                        "5. endre description\n" +
                        "6. endre rom\n" +
                        "7. legg til gruppe\n" +
                        "8. Sjekk deltakere\n" +
                        "9. Slett event" +
                        "10 Gå tilbake");

                int value2 = KeyIn.inInt("Select option.\n ");
                switch (value2) {
                    case 1:
                        String newStartTime = "2015-" + KeyIn.inString("skriv inn nytt tidspunkt: (MM-DD HH:MM)") + ":00";
                        appointmentToChange.updateAppointmentInDB("start", newStartTime);
                        continue;

                    case 2:

                    String newEndTime = "2015-" + KeyIn.inString("skriv inn nytt tidspunkt: (MM-DD HH:MM)") + ":00";
                    try {
                        appointmentToChange.updateAppointmentInDB("slutt", newEndTime);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e);
                    }
                    continue;
                case 3:
                    String newAttendant = KeyIn.inString("Hvilken deltaker vil du legge til? skriv inn username");
                    appointmentToChange.addAttendant(newAttendant);
                    continue;
                case 4:
                    String attendantToRemove = KeyIn.inString("Hvilken deltaker vil du fjerne? skriv inn username");
                    appointmentToChange.removeAttendant(attendantToRemove);
                    continue;

                    case 5:
                        String newDescription = KeyIn.inString("Skriv inn ny description:");
                        appointmentToChange.updateAppointmentInDB("description", newDescription);
                        continue;
                    case 6:
                        //endre romstr
                    case 7:
                        System.out.println("Not yet implemented.");
                        continue;
                    case 8:
                        ArrayList<String> applist = appointmentToChange.getAttendingPeople();
                        int index = 1;
                        for (String usr : applist) {
                            System.out.println((index + "") + ". " + User.getUserFromDB(usr).getFullName() + "\n");
                            index++;
                        }
                        continue;
                    case 9:
                        //slett event
                        Appointment.removeAppointmentInDB(appointmentToChange.getAppointmentId());
                        System.out.println("Appointment removed.");
                        break;
                    case 10:
                        stay = false;
                        break;
                    default:
                        continue;

                }
            }
        }
    }



    public void appNav() {

        System.out.println("hva vil du gjøre? bruk tallene for å navigere i menyene \n" +
                        "1. Opprett ny avtale \n" +
                        "2. Endre eksisterende avtale\n" +
                        "3. Gå tilbake\n\n\n"
        );

        int value1 = KeyIn.inInt("Select option. \n ");
        boolean stay = true;
        while (stay) {

            switch (value1) {
                case 1:
                    createAppointment();
                    stay = false;
                    break;
                case 2:
                    editAppointment();
                    stay = false;
                    break;
                case 3:
                    stay = false;
                    break;
                default:
                    System.out.println("Skriv et gyldig valg!!!");
                    continue;

            }

        }
    }

    public void editUserInfo(String username) {


        Database db = new Database();
        db.connectDb();
        boolean stay = true;

        while (stay) {
            System.out.println("du er innlogget som:" + username + "\n");
            System.out.println("1. Endre Email\n" +
                    "2. Endre passord\n" +
                    "3. Slett min bruker\n" +
                    "4. Tilbake\n");
            int option = KeyIn.inInt("hvilken profildata vil du endre\n");

            switch (option) {

                case 1:
                    String newEmail = KeyIn.inString("skriv inn ny email");
                    String sql = "UPDATE  all_s_gruppe40_calendar.user SET email='" + newEmail + "' WHERE username ='" + username + "';";
                    db.updateQuery(sql);
                    continue;
                case 2:
                    String newPassword = KeyIn.inString("skriv inn nytt passord");
                    String sql2 = "UPDATE all_s_gruppe40_calendar.user SET password='" + newPassword + "' WHERE username ='" + username + "';";
                    db.updateQuery(sql2);
                    continue;
                case 3:
                    System.out.println("Not yet implemented.");
                    continue;
                case 4:
                    stay = false;

                    break;
                default:
                    System.out.println("skriv et gyldig valg!!");
                    continue;
            }
        }
    }

    public void viewUser(String username) {


        if (User.existsCheck(username)) {
            Calendar otherCalendar = new Calendar(username);
            otherCalendar.viewCalendar();
        } else {
            throw new IllegalArgumentException("ugyldig bruker");

        }
    }
    public void viewGroup(){

        String groupname = KeyIn.inString("Vennligst skriv inn navnet paa gruppen du vil utforske!");
        try {
            int id = Group.getGroupIDFromDB(groupname);
            Group group = Group.getGroup(id);
            Calendar groupCalendar = new Calendar(group);
            groupCalendar.viewCalendar();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid groupname. Try again.");
        }

    }

    public void admin(){
        System.out.println("ADMIN: \n1: Lag ny bruker\n2. Slett bruker\n3. Opprett rom\n4" +
                ". Slett rom\n5. Gå tilbake\n6. Gjør bruker til admin\n");

        int value = KeyIn.inInt("Select option:\n");
        boolean stay= true;
        while (stay) {


            switch (value) {

                case 1:

                    clearConsole();
                    boolean taken = true;
                    String un = "";
                    while (taken) {
                        un = KeyIn.inString("Skriv inn nytt brukernavn: ");
                        if (User.usernameTaken(un)) {
                            System.out.println("Username taken!");
                            break;
                        }
                        taken = false;
                    }
                    String fn = KeyIn.inString("Skriv inn fornavn: ");
                    String en = KeyIn.inString("Skriv inn etternavn: ");
                    String pswd = KeyIn.inString("Skriv inn nytt passord: ");
                    String position = KeyIn.inString("Skriv inn stilling ('None' hvis ingen)");
                    String email = KeyIn.inString("Skriv inn e-post: ");
                    User user = new User(un, pswd, fn, en, email, position);
                    user.addUserToDB();
                    String admin = KeyIn.inString("Make user admin? y/n");
                    if(admin.equalsIgnoreCase("y") || admin.equalsIgnoreCase("yes")) {
                        User.setAdmin(un);
                    }

                    continue;

                case 2:
                    //slett bruker

                case 3:
                    //oprett rom

                case 4:
                    //slett rom
                case 5:
                    stay = false;
                    break;
                case 6:
                    String usr = KeyIn.inString("Type in username for new admin: ");
                    try{
                        User.setAdmin(usr);
                    } catch(RuntimeException e) {
                        System.out.println("User doesnt exist.");
                    }
                    continue;
                default:
                    continue;
            }
        }
    }

    public String passwordMasker() {

        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }
        char passwordArray[] = console.readPassword();
        //console.printf("Password entered was: %s%n", new String(passwordArray));
        return new String(passwordArray);
    }
}


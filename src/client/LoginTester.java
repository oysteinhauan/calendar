package client;

import java.util.IllegalFormatCodePointException;
import java.util.Scanner;

/**
 * Created by oysteinhauan on 24/02/15.
 */
public class LoginTester {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Wilkommen! Bitte schreiben sie Ihren Name!");
        Login login = new Login();
        User user;
        String username = "";

        while (scn.hasNext()) {
            try{
                username = scn.nextLine();
                login = new Login(username);

                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("\nInvalid usrname. Try again plz\n\n");
            }
        }
        System.out.println("\nPlease give me ur passwd!");

        while (scn.hasNext()) {
            String password = scn.next();
            try {
                login.login(password);
                user = User.getUserFromDB(username);
                System.out.println("Welcome to our fantastic calendar, " + user.getFullName());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong password!");
            }
        }
    }
}

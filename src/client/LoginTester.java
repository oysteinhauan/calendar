package client;

/**
 * Created by oysteinhauan on 24/02/15.
 */
public class LoginTester {

    public static void main(String[] args) {
        Login login = new Login("oysteibh");
        login.login("qwerty");
        System.out.println(login.loggedin);


        //login.login("qwerty");

    }
}

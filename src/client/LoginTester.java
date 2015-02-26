package client;

/**
 * Created by oysteinhauan on 24/02/15.
 */
public class LoginTester {

    public static void main(String[] args) {
        Login login = new Login("henloef");
        login.login("qwerty");
        System.out.println("Logget inn: " + login.loggedin);
        login.db.closeConnection();
    }
}

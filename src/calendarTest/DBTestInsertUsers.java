package calendarTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by oysteinhauan on 18/02/15.
 */
public class DBTestInsertUsers {

    public static void main(String[] args) {
        try {
            System.out.println("Loading driver...");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find the driver in the classpath!", e);
        }

        String url = "jdbc:mysql://mysql.stud.ntnu.no:3306/oysteibh_calendartest";
        String username = "oysteibh";
        String password = "qwerty";
        Connection connection = null;
        Statement statement = null;

        try{

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            statement = connection.createStatement();
            int user_id = 0;
            String firstname = null, lastname = null, asdf = null;
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNext()){
                user_id = scanner.nextInt();
                firstname = scanner.next();
                lastname = scanner.next();
                String sql = "INSERT INTO users " + "VALUES (" + user_id +", " + firstname + ", " + lastname + ", 1234, 1234)";
                statement.executeUpdate(sql);

            }

//            String sql = "INSERT INTO users " +
//                    "VALUES (1294, 'Zara', 'Ali', 18161514, 1555)";
//            statement.executeUpdate(sql);
//            sql = "INSERT INTO users " +
//                    "VALUES (101, 'Mahnaz', 'Fatma', 25444444, 3333)";
//            statement.executeUpdate(sql);
//            sql = "INSERT INTO users " +
//                    "VALUES (102, 'Zaid', 'Khan', 30434343, 4333)";
//            statement.executeUpdate(sql);
//            sql = "INSERT INTO users " +
//                    "VALUES(103, 'Sumit', 'Mittal', 28544545, 1233)";
//            statement.executeUpdate(sql);
//            System.out.println("Inserted records into the table...");

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(statement!=null)
                    connection.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(connection!=null)
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end JDBCExample



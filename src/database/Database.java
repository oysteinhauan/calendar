package database;

import java.sql.*;

/**
 * Created by oysteinhauan on 24/02/15.
 */
public class Database {

    private String db_name, username, password;
    private Connection connection = null;
    private Statement statement = null;
    private boolean connected = false;

    public Database(String db_name, String username, String password){
        String url = "jdbc:mysql://mysql.stud.ntnu.no:3306/" + db_name;
        this.username = username;
        this.password = password;
    }


    public void connectDb(String url, String username, String password) {
        try {
            System.out.println("Loading driver...");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot locate the driver!", e);
        }
        try {
            System.out.println("Connecting database...");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected! :)");
            connected = true;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect the database! :(", e);
        }
    }

    public void closeConnection(){

        System.out.println("Closing connection.");
        if (connected = true) try {
            connection.close();
        } catch (SQLException ignore) {
        }
    }


    public ResultSet readQuery(String sql){
        if (connected = false){
            throw new RuntimeException("No database connected!");
        }
        else{
            try {
                statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                return rs;
            }
            catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    public void updateQuery(String sql){
        if (connected = false){
            throw new RuntimeException("No database connected!");
        }
        else{
            try{
                statement = connection.createStatement();
                statement.executeUpdate(sql);
            }
            catch(SQLException e ){
                throw new RuntimeException(e);
            }
        }
    }



}
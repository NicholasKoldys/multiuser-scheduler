package dev.nicholaskoldys.multiuserscheduler.service;

import com.mysql.jdbc.Connection;
import java.lang.invoke.MethodHandles;
import java.sql.DriverManager;


/**
 *
 * @author Nicholas Koldys
 * 
 *      service.DatabaseConnection
 *  created to keep the Database connection through the JDBC driver
 *  separate from the rest of the application.
 * 
 */
public class DatabaseConnection {


    /**
     * CONSTANTS used in the connection methods
     */
    // TODO Temp change
//    private final static String SERVER_NAME = "3.227.166.251";
    private final static String SERVER_NAME = "localhost";
//    private final static String DATABASE_NAME = "U05iV3";
    private final static String DATABASE_NAME = "test";
    private final static String DB_URL = "jdbc:mysql://" + SERVER_NAME + "/" + DATABASE_NAME;
//    private final static String USERNAME = "U05iV3";
//    private final static String PASSWORD = "53688515843";
    private final static String USERNAME = "guest";
    private final static String PASSWORD = "secret";
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private static Connection instance;
    
    
    /**
     *  Method to return Connection made through connectToDatase method
     * 
     * @return 
     */
    public static Connection getDatabaseConnection() {
        
        try {
            return instance;
        } catch (Exception ex) {
            System.out.println("Database connection was not found."
            + "\n" + ex.getMessage());
        }
        return null;
    }
    
    
    /**
     *  Method to connect to the database and name the instance for
     *  simple method calls.
     * 
     */
    public static void connectToDatabase() {
        
        try {
            Class.forName(DRIVER);
        } catch (Exception ex) {
            System.out.println(
                    "The class: " + DRIVER + " was not found."
            + "\nFAILED in class " + MethodHandles.lookup().getClass()
            + "\n" + ex.getMessage());
        }
        
        try {
            instance = (Connection) DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (Exception ex) {
            System.out.println(
                    "URL, Username, and Password : " + DB_URL
            + "\nFAILED in class " + MethodHandles.lookup().getClass()
            + "\n" + ex.getMessage());
        }
        System.out.println("Database Connection Successful");
    }
    
    
    /**
     *  Method to remove instance and close the connection to the database.
     * 
     */
    public static void disconnectFromDatabase() {
        
        try {
            instance.close();
        } catch (Exception ex) {
            System.out.println(
                    "Unable to disconnect from Database connection."
            + "\nFAILED in class " + MethodHandles.lookup().getClass()
            + "\n" + ex.getMessage());
        }
        
        System.out.println("Database Disconnect Sucessful");
    }
}
package dev.nicholaskoldys.multiuserscheduler.service;

// TODO TEMPREMOVE import com.mysql.jdbc.Connection;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
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
    // TODO TEMPREMOVE private final static String SERVER_NAME = "3.227.166.251";
    //private final static String SERVER_NAME = "localhost";
    // TODO TEMPREMOVE private final static String DATABASE_NAME = "U05iV3";
    private final static String DATABASE_NAME = "db_schedules";
    // TODO TEMPREMOVE private final static String DB_URL = "jdbc:mysql://" + SERVER_NAME + "/" + DATABASE_NAME;
    //private final static String DB_URL = "jdbc:hsqldb:mem:" + DATABASE_NAME;
    //private final static String DB_URL = "jdbc:hsqldb:hsql//" + SERVER_NAME + "/" + DATABASE_NAME;
    private final static String DB_URL = "jdbc:hsqldb:file:" + DATABASE_NAME + "/";
    // TODO TEMPREMOVE private final static String USERNAME = "U05iV3";
    // TODO TEMPREMOVE private final static String PASSWORD = "53688515843";
    private final static String USERNAME = "sa";
    private final static String PASSWORD = "";
    // TODO TEMPREMOVE private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    private static Connection connectionInstance;
    
    
    /**
     *  Method to return Connection made through connectToDatase method
     * 
     * @return 
     */
    public static Connection getDatabaseConnection() {
        
        try {
            return connectionInstance;
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
                    "FAILED to load the jdbc"
            + "\n" + "The class: " + DRIVER + " was NOT found."
            + "\n" + ex.getCause());
        }
        
        try {
            connectionInstance = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Database Connection Successful");
        } catch (Exception ex) {
            System.out.println(
                    "FAILED to Connect with: "
            + "\n" + "URL : " + DB_URL
            + "\n" + "Please check url, username and password."
            + "\n" + ex.getCause());
        }
    }
    
    
    /**
     *  Method to remove instance and close the connection to the database.
     * 
     */
    public static void disconnectFromDatabase() {
        
        try {
            connectionInstance.close();
            System.out.println("Database Disconnect Successful");
        } catch (Exception ex) {
            System.out.println(
                    "Unable to disconnect from Database connection."
            + "\nFAILED in class " + MethodHandles.lookup().getClass()
            + "\n" + ex.getMessage());
        }
    }
}
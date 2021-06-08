package dev.nicholaskoldys.multiuserscheduler;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import dev.nicholaskoldys.multiuserscheduler.service.DatabaseConnection;
import dev.nicholaskoldys.multiuserscheduler.service.LoggingHandler;

/**
 *
 *    @author Nicholas Koldys
 * 
 *    This Application was created for 
 *    Western Governors University's
 *    Software Development Program.
 * 
 *      Its main function is to create a Graphical User Interface for a 
 * Database. It calls from a database with Appointment, Customer, User,
 * Address, City, and Country Tables. It then produces a Calendar, 
 * AddressBook, and Schedule Object Models to handle Create, Update, Read,
 * and Delete operations.
 * 
 *   The Database can be used with MYSQL,
 * but the design that implemented allows multiple database interpretation 
 * as long as the JDBC driver is functional with the associated database.
 * 
 * 
 * 
 */
public class Main extends Application {
    
    /**
     * Stage static variable allows single line method call to switch scenes.
     */
    private static Stage mainWindow;
    
    
    /**
     * For a few methods that are outside of 'loadScenes' Parameters.
     * @return 
     */
    public static Stage getMainStage() {
        
        return mainWindow;
    }
    
    
    /**
     * Single line method to switch scenes.
     * Controller is not being passed in this application 
     * as an operable entity
     * 
     * @param sceneFileName 
     */
    public static void loadScene(String sceneFileName) {
        
        try {
            Parent root = FXMLLoader.load(MethodHandles.lookup()
                    .lookupClass().getResource("view/" + sceneFileName));
            
            Scene scene = new Scene(root);
            mainWindow.setScene(scene);
            mainWindow.show();
            
        } catch (IOException ex) {
            System.out.println(
            "ERROR OCCURED : scene files were not located" + ex.getMessage());
        }
        
    }
    
    
    /**
     * init method associated with Application Super Class.
     * 
     * loads the connection to the database and tests LoggingHandler.
     * 
     * @throws Exception 
     */
    @Override
    public void init() throws Exception {
        
        super.init();
        
//        TODO try {
//            // TODO DatabaseConnection.connectToDatabase();
//            LoggingHandler.getInstance().test();
//        } catch (Exception ex) {
//            System.out.println(
//            "FATAL ERROR OCCURED : database initilization failed : " + ex.getMessage());
//            System.exit(-1);
//        }
//
    }
    
    
    /**
     *  stop method associated with Application Super Class
     * 
     *  stops the connection to the database and final time stamp to LoggingHandler.
     */
    @Override
    public void stop() {
        
//        TODO try {
//            // TODO DatabaseConnection.disconnectFromDatabase();
//            LoggingHandler.getInstance().userSignOut();
//            LoggingHandler.getInstance().userCloseApp();
//        } catch (Exception ex) {
//            System.out.println(
//            "ERROR SHUTTING DOWN DATABASE CONNECTION... ");
//            System.exit(-1);
//        }

    }
    
    
    /**
     *  start method associated with Application Super Class
     * 
     *  Loads the main window || main screen || application window 
     * 
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        mainWindow = primaryStage;
        loadScene("LoginScene.fxml");
        System.out.println("Application Launched");
    }
    
    
    /**
     *  Main application loop 
     * 
     *  starts the Application methods -> init -> start
     * and finalizes if closed -> stop
     * 
     * @param argv 
     */
    public static void main(String[] argv) {
        
        launch(argv);
    }
}

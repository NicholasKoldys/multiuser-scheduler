package dev.nicholaskoldys.multiuserscheduler.controller;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import dev.nicholaskoldys.multiuserscheduler.Main;
import dev.nicholaskoldys.multiuserscheduler.service.LocalizationService;
import dev.nicholaskoldys.multiuserscheduler.service.LoggingHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import dev.nicholaskoldys.multiuserscheduler.model.Appointment;
import dev.nicholaskoldys.multiuserscheduler.model.AppointmentCalendar;

/**
 * FXML Controller class for User Login
 * functions to a GUI.
 * 
 *  First Scene loaded
 * 
 * @author Nicholas Koldys
 */
public class LoginSceneController implements Initializable {
                
    File file = new File("/userFile.txt");
    
    @FXML private Label titleLabel;
    @FXML private Label warningLabel;
    @FXML private Label nameLabel;
    @FXML private Label passwordLabel;
    
    @FXML private TextField nameTextField;
    @FXML private PasswordField passwordTextField;

    @FXML private Button loginButton;
    @FXML private Button cancelButton;
    
    @FXML private MenuButton localeMenuButton;
    @FXML private MenuItem localeMenuItem1;
    @FXML private MenuItem localeMenuItem2;
    
    
    /**
     * search active users against entered userName and password
     * 
     * if correct load next scene and mark user as current
     * else throw alert box.
     * 
     * @param event 
     */
    @FXML
    private void loginButtonAction(ActionEvent event) {
        
        
        if (AppointmentCalendar.getInstance().loginUser(nameTextField.getText(),
                passwordTextField.getText())) {
            
            System.out.println("ACTIVE USER CONFIRMED");
            
            AppointmentCalendar.getInstance().setupApplicationCalendar();
            Main.loadScene("CalendarScene.fxml");
            
            checkForAppointmentLogin();
            
            LoggingHandler.getInstance().userSignIn();
        } else {
            
            Alert a = new Alert(AlertType.ERROR);
            
            a.setHeaderText(LocalizationService.activeResourceBundle.getString("incorrectStatement"));
            a.setContentText(LocalizationService.activeResourceBundle.getString("pleaseStatement"));
            
            a.showAndWait();
            
            LoggingHandler.getInstance().userSignInAttempt(
                    nameTextField.getText());
        }
    }
    
    
    /**
     * Remove all entered information
     * 
     * @param event 
     */
    @FXML
    private void cancelButtonAction(ActionEvent event) {

            nameTextField.clear();
            passwordTextField.clear();
    }
    
    
    /**
     * Sets the menu buttons actions.
     * 
     * @return 
     */
    private EventHandler<ActionEvent> localeSelectionAction() {
        
        return (event -> {
            LocalizationService.changeLocale(
                ((MenuItem)event.getSource()).getId());
            Main.loadScene("LoginScene.fxml");
            
        });
    }
    
    
    /**
     * check Active Appointment List for appointments within 15 min.
     * if login successful.
     * 
     */
    private void checkForAppointmentLogin() {
        
        Appointment app =
                AppointmentCalendar.getInstance().lookupAppointmentWithTime(LocalDateTime.now());
        
        if(app != null) {
            Alert a = new Alert(AlertType.INFORMATION);
            
            a.setHeaderText("An Appointment is about to Start");
            a.setContentText("The appointment : " + app.getTitle() + " starts at " 
                    + app.getStartTime()
                            .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
                    + ".\n"
                    + "Customer : " + app.getCustomerName()
            );
            
            a.show();
            
            LoggingHandler.getInstance().userSignInAttempt(
                    nameTextField.getText());
        }
    }
    
    
    /**
     * Initializer Method for Initializable Super Class
     * 
     * method controlling javaFX entity starting traits
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        /**
         * Sets Locale Specific menu items.
         */
        localeMenuItem1 =
                new MenuItem(LocalizationService.activeResourceBundle.getString("english"));
        localeMenuItem1.setId("englishMenuItem");
        localeMenuItem2 = 
                new MenuItem(LocalizationService.activeResourceBundle.getString("french"));
        localeMenuItem2.setId("frenchMenuItem");
        
        
        /**
         * Sets all labels to Locale specific text
         */
        titleLabel.setText(LocalizationService.activeResourceBundle.getString("title"));
        warningLabel.setText(LocalizationService.activeResourceBundle.getString("warning"));
        nameLabel.setText(LocalizationService.activeResourceBundle.getString("name"));
        passwordLabel.setText(LocalizationService.activeResourceBundle.getString("password"));
        loginButton.setText(LocalizationService.activeResourceBundle.getString("login"));
        cancelButton.setText(LocalizationService.activeResourceBundle.getString("cancel"));
        localeMenuButton.setText(LocalizationService.activeResourceBundle.getString("language"));
                
        nameTextField.setPromptText("enter name");
        passwordTextField.setPromptText("enter password");
        
        
        /**
         * Sets Menu button actions
         */
        localeMenuItem1.setOnAction(localeSelectionAction());
        localeMenuItem2.setOnAction(localeSelectionAction());
        
        localeMenuButton.getItems().addAll(localeMenuItem1, localeMenuItem2);
        
        
        /**
         * TEMPORARY MEASURE - allows quick traversal of application text mode.
         */
        nameTextField.setText("test");
        passwordTextField.setText("test");
    }
}
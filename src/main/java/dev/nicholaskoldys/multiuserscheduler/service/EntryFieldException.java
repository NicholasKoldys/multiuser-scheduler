package dev.nicholaskoldys.multiuserscheduler.service;

import javafx.scene.control.Alert;

/**
 *
 * @author nicho
 */
public class EntryFieldException extends Exception {
    
    public EntryFieldException(String errorMessage, int flag, String fieldValue) {
        super(errorMessage);
        if(flag == 1) {
            throwCustomerAlert();
        } 
        if (flag == 2) {
            throwAppointmentAlert();
        }
        if (flag == 3) {
            throwCustomerNameAlert(fieldValue);
        }
        if (flag == 4) {
            throwCustomerPhoneAlert(fieldValue);
        }
        if (flag == 5) {
            throwCustomerPostalAlert(fieldValue);
        }
    }
    
    private void throwCustomerAlert() {
        Alert a = new Alert(Alert.AlertType.WARNING);
            
        a.setHeaderText("Please Enter a Valid Customer\n\n\n"
                + "ALL following Fields must have a value\n\n"
                + " Name:\n Address:\n Postal Code:\n Phone Number:\n City:\n Country:\n\n");
        a.setContentText("Please review entry and fix missing values");

        a.showAndWait();
    }
    
    private void throwCustomerNameAlert(String name) {
        Alert a = new Alert(Alert.AlertType.WARNING);
            
        a.setHeaderText("Please Enter a Valid Customer Name\n\n\n"
                + "Customer Name field can only include\n\n"
                + " Letters:\n Roman Numerals:\n Firstname Lastname:\n\n");
        a.setContentText("Please review entry and fix the Customer Name: \n" + name);

        a.showAndWait();
    }
    
    private void throwCustomerPhoneAlert(String phone) {
        Alert a = new Alert(Alert.AlertType.WARNING);
            
        a.setHeaderText("Please Enter a Valid Customer Phone Number\n\n\n"
                + "Customer Phone Number field can only follow\n\n"
                + " US Standard: 333-333-4444\n Prefix Intl: 0000 22 1 333 4444\n\n");
        a.setContentText("Please review entry and fix the Customer Phone Number: \n" + phone);

        a.showAndWait();
    }
    
    private void throwCustomerPostalAlert(String postalCode) {
        Alert a = new Alert(Alert.AlertType.WARNING);
            
        a.setHeaderText("Please Enter a Valid Customer Postal Code\n\n\n"
                + "Customer Postal Code field can only include\n\n"
                + " Digits: 0-9\n\n\n");
        a.setContentText("Please review entry and fix Customer Postal Code: \n" + postalCode);

        a.showAndWait();
    }
    
    private void throwAppointmentAlert() {
        Alert a = new Alert(Alert.AlertType.WARNING);
            
        a.setHeaderText("Please Enter a Valid Appointment\n\n\n"
                + "ALL following Fields must have a value\n\n"
        + " Title:\n Type:\n Customer:\n Date:\n Start/End Time:\n\n");
        a.setContentText("Please review entry and fix missing values");

        a.showAndWait();
    }
}

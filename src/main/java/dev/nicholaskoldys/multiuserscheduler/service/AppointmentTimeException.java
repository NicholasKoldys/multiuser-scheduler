package dev.nicholaskoldys.multiuserscheduler.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.scene.control.Alert;

/**
 *
 * @author nicho
 */
public class AppointmentTimeException extends Exception {
    
    public AppointmentTimeException(String errorMessage, LocalDateTime time, int flag) {
        super(errorMessage);
        
        if(flag == 1) {
            throwOutOfBusinessHourAlert(time);
        }
        if(flag == 2) {
            throwOverlappingStartTimeAlert(time);
        }
        if(flag == 3) {
            throwTimePassed(time);
        }
    }
    
    private void throwTimePassed(LocalDateTime illegalTime) {
         Alert a = new Alert(Alert.AlertType.WARNING);
            
        a.setHeaderText("Please Check the Entered a Value for Appointment Date\n\n\n"
                + "Entered Date and Time is has expired:\n\n"
                + illegalTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
                + "\n\nCurrent Time:\n\n"
                + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
        a.setContentText("Please review entry and fix Date and Start/End Time values");

        a.showAndWait();
    }
    
    private void throwOutOfBusinessHourAlert(LocalDateTime illegalTime) {
        Alert a = new Alert(Alert.AlertType.WARNING);
            
        a.setHeaderText("Please Check the Entered a Value for Appointment Date\n\n\n"
                + "Entered Date and Time is Outside of Business Hours:\n\n"
                + illegalTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
                + "\n\nBusiness Hours:\n\n 8AM - 5PM : MONDAY - FRIDAY");
        a.setContentText("Please review entry and fix Date and Start/End Time values");

        a.showAndWait();
    }
    
    private void throwOverlappingStartTimeAlert(LocalDateTime overlappedTime){
        Alert a = new Alert(Alert.AlertType.WARNING);
            
        a.setHeaderText("Please Check the Entered Value for Appointment Date/Time\n\n\n"
                + "Entered Date and Time Seems to be Taken:\n\n"
                + overlappedTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
        a.setContentText("Please review entry and fix Date and Start/End Time values");

        a.showAndWait();
    }
}

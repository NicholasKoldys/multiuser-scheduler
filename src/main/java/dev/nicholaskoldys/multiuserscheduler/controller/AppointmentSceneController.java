package dev.nicholaskoldys.multiuserscheduler.controller;

import dev.nicholaskoldys.multiuserscheduler.Main;
import dev.nicholaskoldys.multiuserscheduler.model.Appointment;
import dev.nicholaskoldys.multiuserscheduler.model.Customer;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import dev.nicholaskoldys.multiuserscheduler.model.AddressBook;
import dev.nicholaskoldys.multiuserscheduler.model.AppointmentCalendar;
import dev.nicholaskoldys.multiuserscheduler.model.Schedule;
import dev.nicholaskoldys.multiuserscheduler.service.AppointmentTimeException;
import dev.nicholaskoldys.multiuserscheduler.service.EntryFieldException;

/**
 * FXML Controller class for Appointments Create / Read / Update / Delete : CRUD
 * functions to a GUI.
 * 
 * @author Nicholas Koldys
 * 
 */
public class AppointmentSceneController implements Initializable {
    
    @FXML TableView<Appointment> appointmentTableView;
    @FXML TableColumn titleCol;
    @FXML TableColumn typeCol;
    @FXML TableColumn customerCol;
    @FXML TableColumn dateCol;
    
    @FXML TextField titleTextField;
    @FXML ComboBox typeComboBox;
    @FXML TextField customerTextField;
    @FXML TextField contactTextField;
    @FXML TextField locationTextField;
    
    @FXML DatePicker appointmentDatePicker;
    @FXML ComboBox<LocalTime> startTimeComboBox;
    @FXML ComboBox<LocalTime> endTimeComboBox;
    
    @FXML Hyperlink urlHyperlink;
    @FXML TextArea descriptionTextField;
    
    @FXML Button addButton;
    @FXML Button updateButton;
    @FXML Button deleteButton;
    @FXML Button backButton;
    
    @FXML AnchorPane customerSelectionPane;
    @FXML TableView<Customer> customerSelectionTableView;
    @FXML TableColumn customerNameCol;
    @FXML TableColumn customerAddressCol;
    @FXML TableColumn customerPhoneCol;
    @FXML Button customerSelectionCancelButton;
    @FXML Button customerSelectionSelectButton;
    
    Customer selectedCustomer;
    Appointment selectedAppointment;
    
    
    /*
    *   Fill in fields with Appointment Object's fields
    *   selectedAppointment becomes this object
    */
    @FXML
    private void tableViewSelectAction() {

        if(appointmentTableView.getSelectionModel().getSelectedItem() != null) {

            selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

            titleTextField.setText(selectedAppointment.getTitle());
            typeComboBox.getSelectionModel().select(selectedAppointment.getType());
            selectedCustomer = selectedAppointment.getCustomer();        
            customerTextField.setText(selectedCustomer.getCustomerName());
            contactTextField.setText(selectedAppointment.getContact());
            locationTextField.setText(selectedAppointment.getLocation());

            /*
             *  If description shows undesirable text change it.
             */
            if(selectedAppointment.getDescription().equals("") 
                    || selectedAppointment.getDescription().equals("null")) {

                descriptionTextField.setText("Enter a description here..");
            } else {
                descriptionTextField.setText(selectedAppointment.getDescription());
            }

            /*
            *   Time pulled from appointment object is UTC
            *   time is changed into defaultZone of JVM
            */
            appointmentDatePicker.setValue(selectedAppointment.getStartTime().toLocalDate());
            datePickerSelectionAction();
            startTimeComboBox.setValue(selectedAppointment.getStartTime().toLocalTime());
            startTimeComboAction();
            endTimeComboBox.setValue(selectedAppointment.getEndTime().toLocalTime());
            urlHyperlink.setDisable(false);
            urlHyperlink.setText(selectedCustomer.getCustomerName() + " : GO to Customer Record");
            addButton.setDisable(false);
            updateButton.setDisable(false);
            deleteButton.setDisable(false);
        }
    }
    
    
    /*
    *   When customerTextField is selected, it behaves like a popup window.
    *   Requiring selection of a customer from the customerSelectionPane.
    */
    @FXML
    private void customerTextFieldAction() {
        customerSelectionPane.setVisible(true);
        customerTextField.setDisable(true);
    }
    
    
    /*
    *   The HyperLink under the appointment table view, 
    *   launches the customer CRUD scene,
    *   for the respective customer.
    */
    @FXML
    private void customerHyperLinkAction() {
        
        if (!urlHyperlink.isDisabled()) {
            try {
                FXMLLoader loader = new FXMLLoader();
                URL url = Paths.get("src/main/resources/view/CustomerScene.fxml").toUri().toURL();
                loader.setLocation(url);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Main.getMainStage().setScene(scene);
                CustomerSceneController controller = loader.getController();
                controller.setCustomerOnLoad(selectedCustomer);
                Main.getMainStage().show();

            } catch (IOException ex) {
                System.out.println(
                "FAILED TO LOAD SCENE : from Hyperlink" + ex.getMessage());
            }
        }
    }
    
    
    /*
    *   Confirms selection of customer in customer selection pane.
    */
    @FXML
    private void customerSelectionSelectButtonAction() {
        selectedCustomer = customerSelectionTableView.getSelectionModel().getSelectedItem();
        customerTextField.setText(selectedCustomer.getCustomerName());
        customerSelectionPane.setVisible(false);
        customerTextField.setDisable(false);
        urlHyperlink.setDisable(false);
        urlHyperlink.setText(selectedCustomer.getCustomerName() + " : GO to Customer Record");
    }
    
    
    /*
    *   Hides the customer selection pane.
    */
    @FXML
    private void customerSelectionCancelButtonAction() {
        customerSelectionPane.setVisible(false);
        customerTextField.setDisable(false);
    }
    
    
    /*
    *   allows addition of new Types
    */
    @FXML
    private void typeComboBoxAction() {
        typeComboBox.setEditable(true);
    }
    
    
    /**
     *  loads dates into the startTime after a date is selected.
     */
    @FXML
    private void datePickerSelectionAction() {

        LocalDate selectedDate = appointmentDatePicker.getValue();
        startTimeComboBox.setDisable(false);
        List<LocalTime> startTimeList = new ArrayList();

        if (selectedDate != null) {

            int timeOfDay;

            //Changed timeOfDay from 8 (Beginning of work day) to actual
            //time's hour to prevent common expired time exception
            if(LocalDateTime.now().toLocalDate().equals(selectedDate)) {
                 timeOfDay = LocalDateTime.now().getHour();
            } else {
                timeOfDay = 8;
            }

            for(int hour = 0; hour < 9; hour++) {

                //Don't exceed 5:00PM - Workers shouldn't work this late.
                if(timeOfDay + hour >= 17) {
                   break;
                }

                //Increment minutes every 15.
                for(int min = 0; min < 60; min++) {

                    if(min % 15 == 0) {
                        startTimeList.add(LocalTime.of(timeOfDay + hour, min));
                    }
                }
            }

            if(!startTimeList.isEmpty()) {

                ObservableList<LocalTime> startTimesList =
                        FXCollections.observableArrayList(startTimeList);

                startTimeComboBox.setItems(startTimesList);
            }
        }
    }
    
    
    /**
     *  load dates into the endTime after startTime has been selected
     */
    @FXML
    private void startTimeComboAction() {

        LocalDate selectedDate = appointmentDatePicker.getValue();
        LocalTime selectedTime = startTimeComboBox.getValue();
        endTimeComboBox.setDisable(false);
        List<LocalTime> endTimeList = new ArrayList();       
        
        if(selectedTime != null) {

            int timeOfDay = selectedTime.getHour();

            for(int hour = 0; hour < 10; hour++) {

                if(timeOfDay + hour == 17) {
                    endTimeList.add(LocalTime.of(timeOfDay + hour, 0));
                    break;
                }

                for(int min = 0; min < 60; min++) {

                    if(LocalTime.of(timeOfDay + hour, min).isBefore(selectedTime)
                            || LocalTime.of(timeOfDay + hour, min).equals(selectedTime)) {
                        continue;
                    }
                    if(min == 0) {
                        endTimeList.add(LocalTime.of(timeOfDay + hour, min));
                        continue;
                    }
                    if(min % 15 == 0) {
                        endTimeList.add(LocalTime.of(timeOfDay + hour, min));
                    }
                }
            }

            if(!endTimeList.isEmpty()) {

                ObservableList<LocalTime> endTimesList =
                        FXCollections.observableArrayList(endTimeList);

                endTimeComboBox.setItems(endTimesList);
            }
        }
    }
    
    
    /*
    *   Adds the appointment to the active Appointment List and Database
    *   for the logged in user.
    */
    @FXML
    private void addButtonAction() {
        try {
            if(!checkFieldsForEntry()) {
                throw new EntryFieldException(
                        "Warning::attempt to add invalid appointment::", 2, "");
            }

            LocalDateTime startTime, endTime;
            startTime = appointmentDatePicker.getValue().atTime(startTimeComboBox.getValue());
            endTime = appointmentDatePicker.getValue().atTime(endTimeComboBox.getValue());

            /*
             * Basic Exception catches
             */
            if(startTime.getDayOfWeek() == DayOfWeek.SATURDAY 
                    || startTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
                throw new AppointmentTimeException(
                        "Warning::attempt to add appoointment outside of business hours", startTime, 1);
            }
            if(startTime.isBefore(LocalDateTime.now())) {
                throw new AppointmentTimeException(
                        "Warning::attempt to add appoointment with expired time", startTime, 3);
            }
            if(Schedule.getInstance().testBetweenAppointments(startTime, endTime, false) == false) {
                throw new AppointmentTimeException(
                        "Warning::attempt to add an overlapping appointment date::", startTime, 2);
            }

            /*
             * Set unused fields to null
             */
            if(contactTextField.getText().equals("")) {
                contactTextField.setText(AppointmentCalendar
                        .getCurrentUser().getUserName());
            }
            if(descriptionTextField.getText().equals("") 
                    || descriptionTextField.getText().equals("null")
                    || descriptionTextField.getText().equals(
                            "Enter a description here..")) {

                descriptionTextField.setText("");
            }

            /*
             * Assign textFields to new Appointment
             */
            Appointment appointment = new Appointment(
                    selectedCustomer,
                    AppointmentCalendar.getCurrentUser(),
                    titleTextField.getText(),
                    descriptionTextField.getText(),
                    locationTextField.getText(),
                    contactTextField.getText(),
                    typeComboBox.getValue().toString(),
                    selectedCustomer.getCustomerName(),
                    startTime,
                    endTime
            );

            /*
             * need to create appointment to get appointment id to perform time check.
             */
            AppointmentCalendar.getInstance().addAppointment(appointment);
            appointmentTableView.refresh();
            setupAppointmentTypeListComboBox();

        } catch (EntryFieldException tex) {
            System.out.println(tex.getMessage());
        } catch (AppointmentTimeException oex) {
            System.out.println(oex.getMessage());
        }
    }
    
    
    /*
    *   Updates the appointment in the active Appointment List and Database
    *   for the logged in user.
    */
    @FXML
    private void updateButtonAction() {
        try {
            if(!checkFieldsForEntry()) {
                throw new EntryFieldException(
                        "Warning::attempt to add invalid appointment::", 2, "");
            }

            LocalDateTime startTime, endTime;
            startTime = appointmentDatePicker.getValue()
                    .atTime(startTimeComboBox.getValue());
            endTime = appointmentDatePicker.getValue()
                    .atTime(endTimeComboBox.getValue());

            /*
             * Basic exceptions catching
             */
            if(startTime.getDayOfWeek() == DayOfWeek.SATURDAY 
                    || startTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
                throw new AppointmentTimeException(
                        "Warning::attempt to add appoointment outside of business hours",
                        startTime, 1);
            }
            if(startTime.isBefore(LocalDateTime.now())) {
                throw new AppointmentTimeException(
                        "Warning::attempt to add appoointment with expired time", startTime, 3);
            }

            /*
             * set contact to current user if non-other specified
             */
            if(contactTextField.getText().equals("")) {
                    contactTextField.setText(AppointmentCalendar
                            .getCurrentUser().getUserName());
            }

            /*
             * Set description text back to null if not used
             */
            if(descriptionTextField.getText().equals("") 
                    || descriptionTextField.getText().equals("null")
                    || descriptionTextField.getText().equals(
                                "Enter a description here..")) {

                descriptionTextField.setText("");
            }

            /*
             * Test Time Availability
             */
            LocalDateTime oldStartTime = selectedAppointment.getStartTime(),
                    oldEndTime = selectedAppointment.getEndTime();
            
            selectedAppointment.setStartTime(startTime);
            selectedAppointment.setEndTime(endTime);
            
            if(Schedule.getInstance().isTimeOpen(selectedAppointment) == false) {
                selectedAppointment.setStartTime(oldStartTime);
                selectedAppointment.setEndTime(oldEndTime);
                
                throw new AppointmentTimeException(
                        "Warning::attempt to add an overlapping appointment date Final Check::",
                        startTime, 2);
            }

            /*
             * set appointment fields from values in textFields
             */
            selectedAppointment.setCustomerId(selectedCustomer.getCustomerId());
            selectedAppointment.setUserId(AppointmentCalendar.getCurrentUser().getUserId());
            selectedAppointment.setTitle(titleTextField.getText());
            selectedAppointment.setDescription(descriptionTextField.getText());
            selectedAppointment.setLocation(locationTextField.getText());
            selectedAppointment.setContact(contactTextField.getText());
            selectedAppointment.setType(typeComboBox.getValue().toString());
            selectedAppointment.setCustomer(selectedCustomer);

            AppointmentCalendar.getInstance().updateAppointment(selectedAppointment);

            appointmentTableView.refresh();
            setupAppointmentTypeListComboBox();

        } catch (EntryFieldException tex) {
            System.out.println(tex.getMessage());
        } catch (AppointmentTimeException oex) {
            System.out.println(oex.getMessage());
        }
    }
    
    /*
    *   Deletes the appointment from the Appointment List and Database.
    */
    @FXML
    private void deleteButtonAction() {
        
        if(appointmentTableView.getSelectionModel().getSelectedItem() != null) {

            Alert a = new Alert(AlertType.CONFIRMATION);

            a.setContentText(
                    "You selected to DELETE the Appointment: \n\n"
                    + "Title: " + selectedAppointment.getTitle() + "\n"
                    + "Date: " + selectedAppointment.getAppointmentDate() + "\n"
                    + "Customer: " + selectedAppointment.getCustomerName() + "\n\n"
                    + "DELETE the Appointment by Pressing Ok..");

            a.showAndWait()
                    /*
                     * LAMBDA EXPRESSION - simple to read expression
                     *
                     * .filter -> filters response to ButtonType.OK
                     * .ifPresent -> for response of filter "OK Button"...
                     *                 ---- RUN THIS ----
                     *
                     * is better than the alternative which
                     * goes into various methods and in-line
                     * class calls.  it makes it very readable.
                     */
                .filter(response -> response == ButtonType.OK)
                .ifPresent(reponse -> {
                    AppointmentCalendar.getInstance().deleteAppointment(selectedAppointment);
                    appointmentTableView.refresh();
                });
        }
    }
    
    @FXML
    private void backButtonAction() {
        Main.loadScene("CalendarScene");
    }
    
    @FXML
    private void calendarMenuButtonAction() {
        Main.loadScene("CalendarScene");
    }
    
    @FXML
    private void customerRecordsMenuButtonAction() {
        Main.loadScene("CustomerScene");
    }
    
    @FXML
    private void reportsMenuButtonAction() {
        Main.loadScene("ReportsScene");
    }
    
    
    /**
     * Change the style and select-ability of days of the month on the
     * datePicket
     */
    private void setupAppointmentDatePicker() {
        
        final Callback<DatePicker, DateCell> dayCellFactory = 
            new Callback<DatePicker, DateCell>() {

                @Override
                public DateCell call(final DatePicker datePicker) {

                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);

                            if (item.isBefore(LocalDate.now()) 
                                    || item.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                                    || item.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                                
                                    setDisable(true);
                                    setStyle("-fx-background-color: Lightgrey;");
                            }
                        }
                    };
                }
            };
        appointmentDatePicker.setDayCellFactory(dayCellFactory);
    }
    
    protected void setAppointmentOnLoad(Appointment appointment) {
        selectedAppointment = appointment;
        selectedCustomer = selectedAppointment.getCustomer();

        titleTextField.setText(selectedAppointment.getTitle());
        typeComboBox.getSelectionModel().select(selectedAppointment.getType());
        customerTextField.setText(selectedCustomer.getCustomerName());
        contactTextField.setText(selectedAppointment.getContact());
        locationTextField.setText(selectedAppointment.getLocation());

        /*
         *  If description shows undesirable text change it.
         */
        if(selectedAppointment.getDescription().equals("") || selectedAppointment.getDescription().equals("null")) {
            descriptionTextField.setText("Enter a description here..");
        } else {
            descriptionTextField.setText(selectedAppointment.getDescription());
        }

        /*
        *   Time pulled from appointment object is UTC
        *   time is changed into defaultZone of JVM
        */
        appointmentDatePicker.setValue(selectedAppointment.getStartTime().toLocalDate());
        startTimeComboBox.setValue(selectedAppointment.getStartTime().toLocalTime());
        endTimeComboBox.setValue(selectedAppointment.getEndTime().toLocalTime());

        urlHyperlink.setDisable(false);
        urlHyperlink.setText(selectedCustomer.getCustomerName() + " : GO to Customer Record");
    }
    
    /**
     *   Fill in type List values with previously entered values.
     */
    private void setupAppointmentTypeListComboBox() {
        List<Appointment> appointmentsList = AppointmentCalendar.getInstance().getAllAppointments();
        Set<String> typeSet = new HashSet<>();

        for(Appointment appointment : appointmentsList) {
            typeSet.add(appointment.getType());
        }

        List<String> transferList = new ArrayList<>(typeSet);
        ObservableList<String> allTypesList = FXCollections.observableArrayList(transferList);

        typeComboBox.setItems(allTypesList);
        typeComboBox.getSelectionModel().selectFirst();
    }
    
    
    private Boolean checkFieldsForEntry() {
        if (titleTextField.getText().isEmpty()
                || typeComboBox.getSelectionModel().getSelectedItem() == null
                || customerTextField.getText().isEmpty()
                || appointmentDatePicker.getValue() == null
                || startTimeComboBox.getSelectionModel().getSelectedItem() == null
                || endTimeComboBox.getSelectionModel().getSelectedItem() == null) {
            return false;
        }
        return true;
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

        /*
         * Sets ADD / UPDATE / DELETE buttons disabled
         */
        addButton.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);

        /*
         *  Setup click-able entities
         */
        setupAppointmentDatePicker();
        startTimeComboBox.setDisable(true);
        endTimeComboBox.setDisable(true);
        setupAppointmentTypeListComboBox();

        /*
         * LAMBDA EXPRESSION -
         *  
         *  very quick setting of event. But it does not follow strict coding guidelines.
         * useful for prototyping very quickly. *If you don't lose it*
         */
        titleTextField.setOnKeyTyped(event -> addButton.setDisable(false));

        /*
        *   Load all Customers from ScheduleModel List
        */
        customerSelectionTableView.setItems(AddressBook.getInstance().getAllActiveCustomers());
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("AddressName"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        /*
        *   Load all Appointments from ScheduleModel List
        *   For the Active User.
        */
        appointmentTableView.setItems(AppointmentCalendar.getInstance().getAllAppointmentsForUser());
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentDate"));
    }
}

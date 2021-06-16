package dev.nicholaskoldys.multiuserscheduler.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import dev.nicholaskoldys.multiuserscheduler.Main;
import dev.nicholaskoldys.multiuserscheduler.model.dao.implementation.AppointmentTypeReportDAOImpl;
import dev.nicholaskoldys.multiuserscheduler.model.dao.implementation.CustomerReportDAOImpl;
import dev.nicholaskoldys.multiuserscheduler.model.dao.implementation.UserReportDAOImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import dev.nicholaskoldys.multiuserscheduler.model.Appointment;
import dev.nicholaskoldys.multiuserscheduler.model.AppointmentCalendar;
import dev.nicholaskoldys.multiuserscheduler.model.AppointmentTypeReport;
import dev.nicholaskoldys.multiuserscheduler.model.CustomerReport;
import dev.nicholaskoldys.multiuserscheduler.model.User;
import dev.nicholaskoldys.multiuserscheduler.model.UserReport;


/**
 * FXML Controller class for the reports scene.
 *
 * @author Nicholas Koldys
 */
public class ReportsSceneController implements Initializable {

    
    @FXML private TableView reportsTable;
    
    @FXML private BarChart reportsGraph;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    
    @FXML private Label reportLabel;
    
    @FXML private ChoiceBox menuComboBox;
    @FXML private ComboBox columnComboBox;
    
    @FXML private TableColumn col0;
    @FXML private TableColumn col1;
    @FXML private TableColumn col2;
    @FXML private TableColumn col3;
    @FXML private TableColumn col4;
    @FXML private TableColumn col5;
    @FXML private TableColumn col6;
    @FXML private TableColumn col7;
    
    @FXML private MenuItem customers;
    @FXML private MenuItem appointments;
    @FXML private MenuItem customerAndAppointments;
    @FXML private MenuItem appointmentTypesByMonth;
    @FXML private MenuItem appointmentCountPerCustomer;
    @FXML private MenuItem consultantSchedule;
    @FXML private MenuItem users;
    @FXML private MenuItem editByDate;
    
    @FXML private Button exitButton;
    
    
    Comparator<Appointment> comparatorTime =
                        Comparator.comparing(Appointment::getStartTime);
    Comparator<Appointment> comparatorType =
                        Comparator.comparing(Appointment::getType);
    
    
    /**
     * setup each report with Customer and Appointments Data.
     * loads table view and chart view with relevant data
     * 
     */
    private void setUpByCustomerReport() {
        
        reportLabel.setText("Customer and Appointments Report");
        
        setComboBoxCustomerList();

        ObservableList<Appointment> sortedList = AppointmentCalendar.getInstance().getAllAppointments();

        FXCollections.sort(sortedList, comparatorTime);

        reportsTable.setItems(sortedList);

        col0.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        col0.setText("Customer");
        col1.setCellValueFactory(new PropertyValueFactory<>("Title"));
        col1.setText("Title");
        col2.setCellValueFactory(new PropertyValueFactory<>("Location"));
        col2.setText("Location");
        col3.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        col3.setText("Contact");
        col4.setCellValueFactory(new PropertyValueFactory<>("Type"));
        col4.setText("Type");
        col5.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        col5.setText("StartTime");
        col6.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        col6.setText("EndTime");
        col7.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        col7.setText("User");
        
        reportsGraph.getData().clear();
        xAxis.setLabel("Customer");
        yAxis.setLabel("Appointment Count");
        reportsGraph.setTitle("Customer Appointments Per Year");
        
        ObservableList<CustomerReport> countList = FXCollections.observableArrayList(CustomerReportDAOImpl.getInstance().queryReport());

        List<XYChart.Series> listOfSeries = new ArrayList();

        for(CustomerReport userCount : countList) {
            
            String yearString = userCount.getYear().toString();

            if(listOfSeries.isEmpty()) {

                XYChart.Series newSeries = new XYChart.Series<>();

                newSeries.setName(yearString);

                listOfSeries.add(newSeries);
            }

            for(int i = 0; i < listOfSeries.size(); i++) {

                XYChart.Series check = listOfSeries.get(i);

                if(check.getName().equals(yearString)) {
                    check.getData().add(
                            new XYChart.Data(
                                    userCount.getCustomer(),
                                    userCount.getCount()));
                    listOfSeries.set(i, check);
                    break;
                }

                if(i == listOfSeries.size()-1) {

                    XYChart.Series newSeries = new XYChart.Series<>();

                    newSeries.setName(yearString);
                    newSeries.getData().add(
                            new XYChart.Data(
                                    userCount.getCustomer(),
                                    userCount.getCount()));
                    listOfSeries.add(newSeries);
                    break;
                }
            }
        }

        reportsGraph.getData().addAll(listOfSeries);
    }
    
    
    /**
     * setup report with Appointment Type Data.
     * loads table view and chart view with relevant data
     * 
     */
    private void setUpByTypeReport() {
        
        reportLabel.setText("Appointment Type By Month Report");
        
        setComboBoxMonthList();
        
        ObservableList<Appointment> sortedList =
                AppointmentCalendar.getInstance().getAllAppointments();
                
        FXCollections.sort(sortedList, comparatorTime);
        FXCollections.sort(sortedList, comparatorType);

        reportsTable.setItems(sortedList);

        col0.setCellValueFactory(new PropertyValueFactory<>("Title"));
        col0.setText("Title");
        col1.setCellValueFactory(new PropertyValueFactory<>("Type"));
        col1.setText("Type");
        col2.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        col2.setText("Customer");
        col3.setCellValueFactory(new PropertyValueFactory<>("Location"));
        col3.setText("Location");
        col4.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        col4.setText("Contact");
        col5.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        col5.setText("User");
        col6.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        col6.setText("StartTime");
        col7.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        col7.setText("EndTime");
             
        reportsGraph.getData().clear();
        xAxis.setLabel("Appointment Type");
        yAxis.setLabel("Appointment Count");
        reportsGraph.setTitle("Appointment Type Per Month");

        ObservableList<AppointmentTypeReport> countList = FXCollections.observableArrayList(AppointmentTypeReportDAOImpl.getInstance().queryReport());
        

        List<XYChart.Series> listOfSeries = new ArrayList();

        for(AppointmentTypeReport typeCount : countList) {

            String monthString = typeCount.getMonth().toString();

            if(listOfSeries.isEmpty()) {

                XYChart.Series newSeries = new XYChart.Series<>();

                newSeries.setName(monthString);

                listOfSeries.add(newSeries);
            }

            for(int i = 0; i < listOfSeries.size(); i++) {

                XYChart.Series check = listOfSeries.get(i);

                if(monthString.equals(check.getName())) {
                    check.getData().add(
                            new XYChart.Data(
                                    typeCount.getType(), 
                                    typeCount.getCount())
                    );
                    listOfSeries.set(i, check);
                    break;
                }

                if(i == listOfSeries.size()-1) {

                    XYChart.Series newSeries = new XYChart.Series<>();

                    newSeries.setName(monthString);
                    newSeries.getData().add(
                            new XYChart.Data(
                                    typeCount.getType(), 
                                    typeCount.getCount()));
                    listOfSeries.add(newSeries);
                    break;
                }
            }
        }
        reportsGraph.getData().addAll(listOfSeries);
    }
    
    
    /**
     * setup report with User Month Data.
     * loads table view and chart view with relevant data
     * 
     */
    private void setUpByUserReport() {
        reportLabel.setText("Consultant Schedule Report");
        
        setComboBoxUserList();
        
        ObservableList<Appointment> sortedList2 = AppointmentCalendar.getInstance().getAllAppointments();

        FXCollections.sort(sortedList2, comparatorTime);

        reportsTable.setItems(sortedList2);

        col0.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        col0.setText("User");
        col1.setCellValueFactory(new PropertyValueFactory<>("Title"));
        col1.setText("Title");
        col2.setCellValueFactory(new PropertyValueFactory<>("Location"));
        col2.setText("Location");
        col3.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        col3.setText("Contact");
        col4.setCellValueFactory(new PropertyValueFactory<>("Type"));
        col4.setText("Type");
        col5.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        col5.setText("Customer");
        col6.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        col6.setText("StartTime");
        col7.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        col7.setText("EndTime");

        reportsGraph.getData().clear();
        xAxis.setLabel("Appointment Consultant");
        yAxis.setLabel("Appointment Count");
        reportsGraph.setTitle("Consultant's Appointments Per Month");

        ObservableList<UserReport> countList = FXCollections
                .observableArrayList(UserReportDAOImpl.getInstance()
                        .queryReport());

        List<XYChart.Series> listOfSeries = new ArrayList();

        for(UserReport userCount : countList) {

            String monthString = userCount.getMonth().toString();

            if(listOfSeries.isEmpty()) {

                XYChart.Series newSeries = new XYChart.Series<>();

                newSeries.setName(monthString);

                listOfSeries.add(newSeries);
            }

            for(int i = 0; i < listOfSeries.size(); i++) {

                XYChart.Series check = listOfSeries.get(i);

                if(monthString.equals(check.getName())) {
                    check.getData().add(
                            new XYChart.Data(
                                    userCount.getUser(),
                                    userCount.getCount()));
                    listOfSeries.set(i, check);
                    break;
                }

                if(i == listOfSeries.size()-1) {

                    XYChart.Series newSeries = new XYChart.Series<>();

                    newSeries.setName(monthString);
                    newSeries.getData().add(
                            new XYChart.Data(
                                    userCount.getUser(),
                                    userCount.getCount()));
                    listOfSeries.add(newSeries);
                    break;
                }
            }
        }

        reportsGraph.getData().addAll(listOfSeries);
                
    }
    
    
    /**
     * Set comboBox items with Months
     */
    private void setComboBoxMonthList() {
        
        ObservableList<Month> monthList =
                FXCollections.observableArrayList(Month.values());
        
        /*
        Reset columnBox
        */
        columnComboBox.setOnAction(null);
        columnComboBox.setItems(null);
        columnComboBox.valueProperty().unbind();

        columnComboBox.setItems(monthList);
        columnComboBox.valueProperty().setValue(LocalDate.now().getMonth());
        
        /*
        Set Action to reset the tableView
        */
        columnComboBox.setOnAction((event) -> {
                Month month = (Month) columnComboBox
                        .getSelectionModel().getSelectedItem();
                
                List<Appointment> appointmentsList = 
                        AppointmentCalendar.getInstance().getAllAppointments();
                List<Appointment> appointmentsByMonth = new ArrayList<>();
                
                for(Appointment appointment : appointmentsList) {
                    
                    LocalDate appStartDate = appointment.getStartTime().toLocalDate();
                    
                    if(appStartDate.getMonth() == month) {
                        appointmentsByMonth.add(appointment);
                    }
                }
                
                ObservableList<Appointment> sortedList =
                        FXCollections.observableArrayList(appointmentsByMonth);

                FXCollections.sort(sortedList, comparatorTime);
                FXCollections.sort(sortedList, comparatorType);

                reportsTable.setItems(sortedList);
            }
        );
    }
    
    
    /**
     * set comboBox with Active Users
     */
    private void setComboBoxUserList() {
        
        ObservableList<User> allUsers =
                        FXCollections.observableArrayList(AppointmentCalendar
                                .getInstance().getUsersList());
        
        /*
        Reset columnBox
        */
        columnComboBox.setOnAction(null);
        columnComboBox.setItems(null);
        columnComboBox.valueProperty().unbind();

        columnComboBox.setItems(allUsers);
        columnComboBox.valueProperty().setValue(
                AppointmentCalendar.getCurrentUser().getUserName());
        
        /*
        Set Action to reset the tableView
        */
        columnComboBox.setOnAction((event) -> {
                User user = (User) columnComboBox
                        .getSelectionModel().getSelectedItem();
                
                List<Appointment> appointmentsList = 
                        AppointmentCalendar.getInstance().getAllAppointments();
                List<Appointment> appointmentsForUser = new ArrayList<>();
                
                for(Appointment appointment : appointmentsList) {
                    
                    if(appointment.getUser() == user) {
                        appointmentsForUser.add(appointment);
                    }
                }
                
                ObservableList<Appointment> sortedList =
                        FXCollections.observableArrayList(appointmentsForUser);

                FXCollections.sort(sortedList, comparatorTime);

                reportsTable.setItems(sortedList);
            }
        );
    }
    
    
    /**
     * set comboBox with active customers
     */
    private void setComboBoxCustomerList() {
        
        
        List<Appointment> appointmentsListOnce = AppointmentCalendar.getInstance()
                .getAllAppointments();
        Set<String> customerSet = new HashSet<>();
        for(Appointment appointment : appointmentsListOnce) {
            customerSet.add(appointment.getCustomerName());
        }
        
        List<String> transferList = new ArrayList<>(customerSet);
        
        ObservableList<String> customerList =
                FXCollections.observableArrayList(transferList);
 
        /*
        Reset columnBox
        */
        columnComboBox.setOnAction(null);
        columnComboBox.setItems(null);
        columnComboBox.valueProperty().unbind();

        columnComboBox.setItems(customerList);
        columnComboBox.valueProperty().setValue("Select...");
        
        /*
        Set Action to reset the tableView
        */
        columnComboBox.setOnAction((event) -> {
                String customer = (String) columnComboBox
                        .getSelectionModel().getSelectedItem();
                
                List<Appointment> appointmentsList = 
                        AppointmentCalendar.getInstance().getAllAppointments();
                List<Appointment> appointmentsByCustomer = new ArrayList<>();
                
                for(Appointment appointment : appointmentsList) {
                    
                    if(appointment.getCustomerName().equals(customer)) {
                        appointmentsByCustomer.add(appointment);
                    }
                }
                
                ObservableList<Appointment> sortedList =
                        FXCollections.observableArrayList(appointmentsByCustomer);

                FXCollections.sort(sortedList, comparatorTime);
                FXCollections.sort(sortedList, comparatorType);

                reportsTable.setItems(sortedList);
            }
        );
    }
    
    
    /**
     * Switch statement to call the report load methods.
     * 
     * @param reportSelected 
     */
    @FXML
    private void reportSelectionLoad(String reportSelected) {
        
        switch (reportSelected) {
                
            case "Customer and Appointments" :
                setUpByCustomerReport();
                break;
                
            case "Appointment Type By Month" :
                setUpByTypeReport();
                break;
                
            case "Consultant Schedule" :
                setUpByUserReport();
                break;
        }
    }

    
    /**
     * Navigational buttons
     * 
     * @param event 
     */
    @FXML
    private void exitButtonAction(ActionEvent event) {
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
    private void appointmentsScheduleMenuButtonAction() {
        Main.loadScene("AppointmentScene");
    }
    
    @FXML
    private void appointmentsReportsMenuButtonAction() {
        menuComboBox.setValue("Appointment Type By Month");
    }
    
    @FXML
    private void customerAndAppointmentsReportsMenuButtonAction() {
        menuComboBox.setValue("Customer and Appointments");
    }
    
    @FXML
    private void usersReportsMenuButtonAction() {
        menuComboBox.setValue("Consultant Schedule");
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
         * Set options for Reports switch statement
         */
        ObservableList<String> menuItemsList 
                = FXCollections.observableArrayList(
                        "Please Select a Report",
                        "Customer and Appointments",
                        "Appointment Type By Month", 
                        "Consultant Schedule");
        menuComboBox.setItems(menuItemsList);
        menuComboBox.setValue("Please Select a Report");
        
        
        /**
         * Set Action for report option selection
         */
        menuComboBox.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                @Override public void changed(
                        ObservableValue<? extends String> observableValue,
                        String item, String item2) {
                    
                    reportSelectionLoad(
                            menuComboBox.getSelectionModel().getSelectedItem().toString());
                    
                }
            }
        );
    }
}
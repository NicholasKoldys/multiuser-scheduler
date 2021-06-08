package dev.nicholaskoldys.multiuserscheduler.controller;

import java.io.IOException;

import dev.nicholaskoldys.multiuserscheduler.Main;
import dev.nicholaskoldys.multiuserscheduler.service.LoggingHandler;
import dev.nicholaskoldys.multiuserscheduler.model.Appointment;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.ListSpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import dev.nicholaskoldys.multiuserscheduler.model.AppointmentCalendar;

/**
 * FXML Controller class
 * 
 * displays the calendar and week view for the logged in user.
 * Considered the main page in the application.
 *
 * @author Nicholas Koldys
 * 
 */
public class CalendarSceneController implements Initializable {        
    
    @FXML Spinner selectWeekSpinner;
    ObservableList<Integer> weekList;
    ListSpinnerValueFactory weekSpinnerList;
    
    @FXML Spinner selectMonthSpinner;
    Month month;
    ObservableList<Month> monthList;
    ListSpinnerValueFactory monthSpinnerList;

    @FXML Spinner selectYearSpinner;
    Year year;
    ObservableList<Year> yearList;
    ListSpinnerValueFactory yearSpinnerList;
    
    Year calendarYear;
    Month calendarMonth;
    
    LocalDate todaysDate;
    LocalTime timeNow;
    
    DateTimeFormatter timeFormatter = DateTimeFormatter      
                                      .ofLocalizedTime(FormatStyle.SHORT)
                                      .withZone(ZoneId.systemDefault());
    
    DateTimeFormatter dateFormatter = DateTimeFormatter      
                                      .ofLocalizedDate(FormatStyle.FULL)
                                      .withZone(ZoneId.systemDefault());
    
    @FXML Label timeZoneLabel;
    
    @FXML Label monthLabel;
    @FXML Label dateLabel;
    @FXML Label timeLabel;
    
    @FXML Label dayOfWeek0;
    @FXML Label dayOfWeek1;
    @FXML Label dayOfWeek2;
    @FXML Label dayOfWeek3;
    @FXML Label dayOfWeek4;
    @FXML Label dayOfWeek5;
    @FXML Label dayOfWeek6;
    
    @FXML Label dayOfMonth0;
    @FXML Label dayOfMonth1;
    @FXML Label dayOfMonth2;
    @FXML Label dayOfMonth3;
    @FXML Label dayOfMonth4;
    @FXML Label dayOfMonth5;
    @FXML Label dayOfMonth6;
    
    @FXML GridPane appointmentCalendar;
    List<Label> addDaysList;
    ArrayList<Label> days;
    
    @FXML GridPane appointmentWeek;
    
    @FXML AnchorPane dayOfMonthCalendarListPane;
    @FXML Label dayOfMonthCalendarDateLabel;
    @FXML ListView<Appointment> dayOfMonthCalendarListView;
    @FXML Button dayOfMonthCalendarBackButton;
    @FXML Button dayOfMonthCalendarSelectButton;
    
    @FXML Button todayButton;
    @FXML Button monthButton;
    @FXML Button weekButton;
    
    @FXML Button customerRecordsButton;
    @FXML Button appointmentsScheduleButton;
    
    @FXML Button reportsButton;
    @FXML Button signOutButton;
    
    
    /**
     * Load the calendar view with the current day of the month default.
     * 
     * @param selectedMonth
     * @param selectedYear 
     */
    private void loadMonthIntoCalendar(Month selectedMonth, Year selectedYear) {
        
        DayOfWeek dayOfWeekText;
        int dayOfWeekInt;
        
        Boolean startOfMonth = true;
        Integer x = 0;
        
        //Cheat with group creation using FXMLBuilder
        Node groupCopy = (Node)appointmentCalendar.getChildren().get(0);
        
        calendarYear = selectedYear;
        calendarMonth = selectedMonth;
        
        monthLabel.setText(selectedMonth.toString());
        
        dayOfWeekText = DayOfWeek.SUNDAY;
        
        //1 is to get the first day in the month
        dayOfWeekInt = LocalDate.of(
                calendarYear.getValue(), calendarMonth, 1)
                .getDayOfWeek().getValue();

        appointmentCalendar.getChildren().clear();
        
        for(int i = 0; i <= 6; i++) {
            
            for(int j = 0; j <= 6; j++) {
                
                if (i == 0) {
                    
                    if (j == 0) {
                        
                        appointmentCalendar.add(groupCopy, j, i);
                    }
                    
                    Label weekDayLabel = new Label(dayOfWeekText.toString());
                    dayOfWeekText = dayOfWeekText.plus(1);
                    AnchorPane weekDayBox = new AnchorPane();

                    weekDayLabel.setPrefHeight(35);
                    weekDayLabel.setPrefWidth(130);
                    weekDayLabel.setAlignment(Pos.CENTER);
                    appointmentCalendar.add(weekDayLabel, j, i);
                    continue;
                }
                
                if (i == 1 && startOfMonth == true) {
                    
                    if (j != dayOfWeekInt) {
                        
                        continue;
                    } else {
                        
                        startOfMonth = false;
                    }
                }
                x++;
                Label day = new Label(x.toString());
                day.setId("day" + x.toString());
                day.setFont(Font.font("Veranda", FontWeight.NORMAL, 18));
                day.setLayoutX(4);
                day.setLayoutY(4);
                
                AnchorPane box = new AnchorPane();
                box.setId("box" + x.toString());
                
                /*
                *   Boxes make for a good target 
                *   for the mouse to change the Day Label text
                */
                
                /**
                 * LAMBDA EXPRESSION ---
                 * 
                 * These calls formed into Lambda help keep the code clean looking and since
                 * each new element/object is being added to the calendar, the lambda can be 
                 * created in the method call - that is creating each element. 
                 * 
                 *   * keeps it all in one location*
                 */
                box.addEventHandler(MouseEvent.MOUSE_ENTERED, 
                        (event) -> day.setFont(
                                Font.font("Veranda", FontWeight.BOLD, 19)
                        )
                );
                
                box.addEventHandler(MouseEvent.MOUSE_EXITED, 
                        (event) -> {
                            if (selectedMonth == todaysDate.getMonth() 
                                && day.getId().contains(
                                        Integer.toString(todaysDate.getDayOfMonth())) 
                                && selectedYear.getValue() == todaysDate.getYear()) {
                    
                                day.setFont(Font.font("Veranda", FontWeight.BOLD, 18));
                            } else {
                                
                                day.setFont(Font.font("Veranda", FontWeight.NORMAL, 18));
                            }
                        }
                );
                
                box.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        (event) -> dayOfMonthCalendarAction(
                                LocalDate.of(
                                        selectedYear.getValue(),
                                        selectedMonth, parseInt(day.getText())
                                )
                        )
                );
                
                box.setPrefSize(200, 200);
                box.setMaxSize(200, 200);
                box.getChildren().add(day);
                appointmentCalendar.add(box, j, i);
                
                if (selectedMonth == todaysDate.getMonth() 
                        && x == todaysDate.getDayOfMonth() 
                        && selectedYear.getValue() == todaysDate.getYear()) {
                    
                    day.setFont(Font.font("Veranda", FontWeight.BOLD, 18));
                    box.setBackground(new Background(new BackgroundFill(
                            Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                
                if (x == calendarMonth.length(
                        LocalDate.of(
                                calendarYear.getValue(),
                                calendarMonth, 1).isLeapYear())) {
                    
                    break;
                }
            }
            
            if (x == calendarMonth.length(
                    LocalDate.of(
                            calendarYear.getValue(),
                            calendarMonth, 1).isLeapYear())) {
                
                break;
            }
        }
    }
    
    
    /**
     * 
     * load the week view with the current day of the week and day of the month default.
     * 
     * 
     * @param selectedDate
     * @param plusDay 
     */
    private void loadWeekView(LocalDate selectedDate, int plusDay) {
        
        Comparator<Appointment> comparator = Comparator.comparing(Appointment::getStartTime);
        
        LocalDate calculatedDate = selectedDate.plusDays(plusDay);
        DayOfWeek dayOfWeek = calculatedDate.getDayOfWeek();
        
        for (int i = 0; i < 5; i++) {
            
            ((Label) appointmentWeek.getChildren()
                    .get(i)).setText(dayOfWeek.plus(i).toString());
            ((Label) appointmentWeek.getChildren().get(i))
                        .setBackground(new Background(new BackgroundFill(
                                Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            ((Label) appointmentWeek.getChildren().get(i))
                        .setFont(Font.font("Veranda", FontWeight.NORMAL, 16));

            ((Label) appointmentWeek.getChildren().get(i+5))
                    .setText(String.valueOf(calculatedDate.plusDays(i).getDayOfMonth()));
            ((Label) appointmentWeek.getChildren().get(i+5))
                        .setBackground(new Background(new BackgroundFill(
                                Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            ((Label) appointmentWeek.getChildren().get(i+5))
                        .setFont(Font.font("Veranda", FontWeight.NORMAL, 16));
            
            
            if (selectedDate.getMonth() == todaysDate.getMonth()
                    && calculatedDate.plusDays(i).getDayOfMonth() 
                    == todaysDate.getDayOfMonth() 
                    && calculatedDate.getYear() == todaysDate.getYear()) {
                
                ((Label) appointmentWeek.getChildren().get(i))
                        .setBackground(new Background(new BackgroundFill(
                                Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
                ((Label) appointmentWeek.getChildren().get(i))
                        .setFont(Font.font("Veranda", FontWeight.BOLD, 18));
                
                ((Label) appointmentWeek.getChildren().get(i+5))
                        .setBackground(new Background(new BackgroundFill(
                                Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
                ((Label) appointmentWeek.getChildren().get(i+5))
                        .setFont(Font.font("Veranda", FontWeight.BOLD, 18));
            }
            
            if (calculatedDate.plusDays(i).getMonth() != selectedDate.getMonth()) {
                
                ((Label) appointmentWeek.getChildren().get(i))
                        .setBackground(new Background(new BackgroundFill(
                                Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                ((Label) appointmentWeek.getChildren().get(i+5))
                        .setBackground(new Background(new BackgroundFill(
                                Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            
            
            List<Appointment> appointmentList =
                    AppointmentCalendar.getInstance().getAllAppointmentsForUser();
            
            List<Appointment> appointmentonDateList = new ArrayList<>();
            
            for(Appointment appointment : appointmentList) {
                
                if(appointment.getUser().getUserName().equals(AppointmentCalendar.getCurrentUser().getUserName())) {
                    LocalDate appStartDate = appointment.getStartTime().toLocalDate();
                    if(appStartDate.isEqual(calculatedDate.plusDays(i))){
                        appointmentonDateList.add(appointment);
                    }
                }
            }
            
            /**
             * LAMBDA EXPRESSION ---- 
             * 
             * this expression helps with non-readable code.
             * The method is recommended by oracle.
             * 
             */
            ((ListView) appointmentWeek.getChildren().get(i+10))
            .setCellFactory(param -> new ListCell<Appointment>() {
                @Override
                protected void updateItem(Appointment item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getTitle()== null) {
                        setText(null);
                    } else {
                        setText(item.getStartTime().format(timeFormatter)
                                + " : " + item.getTitle()
                                + " : " + item.getType()
                                + " : " + item.getCustomer().getCustomerName()
                        );
                    }
                }
            });

            ObservableList<Appointment> sortedList 
                    = FXCollections.observableArrayList(appointmentonDateList);

            FXCollections.sort(sortedList, comparator);

            ((ListView) appointmentWeek.getChildren().get(i+10)).setItems(sortedList);
        }
    }
    
    
    /**
     * set spinner to -31 days and +31 days to allow extended week view
     */
    private void setupWeekSpinner() {
        
        weekList = FXCollections.observableArrayList(
                -31, -30, -29, -28, -27, -26, -25, -24, -23, -22, -21, -20,
                -19, -18, -17, -16, -15, -14, -13, -12, -11, -10,
                -9, -8, -7, -6, -5, -4, -3, -2, -1,
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31
        );
        
        weekSpinnerList = new ListSpinnerValueFactory(weekList);
        selectWeekSpinner.setValueFactory(weekSpinnerList);
        selectWeekSpinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        selectWeekSpinner.getValueFactory().setValue(0);
    }
    
    
    /**
     * Create a Month list and apply it to the spinner
     * 
     * Uses Java offered Month Enum
     * 
     */
    private void setupMonthSpinner() {
        
        /*
        month.JANUARY, month.FEBRUARY, month.MARCH, month.APRIL,
                month.MAY, month.JUNE, month.JULY, month.AUGUST,
                month.SEPTEMBER, month.OCTOBER, month.NOVEMBER, month.DECEMBER
        */
        
        monthList = FXCollections.observableArrayList(Month.values());
        
        monthSpinnerList = new ListSpinnerValueFactory(monthList);
        selectMonthSpinner.setValueFactory(monthSpinnerList);
        selectMonthSpinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        selectMonthSpinner.getValueFactory().setValue(todaysDate.getMonth());
    }
    
    
    /**
     * Create a Year list and apply it to the spinner
     * Only setup to use 3 years. 
     * 
     */
    private void setupYearSpinner() {
        
        yearList = FXCollections.observableArrayList(
                Year.of(todaysDate.getYear()-1),
                Year.of(todaysDate.getYear()),
                Year.of(todaysDate.getYear()+1)
        );
        
        yearSpinnerList = new ListSpinnerValueFactory(yearList);
        selectYearSpinner.setValueFactory(yearSpinnerList);
        selectYearSpinner.getValueFactory().setValue(Year.of(todaysDate.getYear()));
    }
    
    
    /**
     * Focus on the user's LOCAL DATE AND TIME NOW
     */
    @FXML
    private void todayButtonAction() {
        
        selectMonthSpinner.getValueFactory().setValue(todaysDate.getMonth());
        monthLabel.setText(selectMonthSpinner.getValue().toString());
        
        selectYearSpinner.getValueFactory().setValue(Year.of(todaysDate.getYear()));
        
        loadMonthIntoCalendar(todaysDate.getMonth(), Year.of(todaysDate.getYear()));
        loadWeekView(todaysDate, 0);
    }
    
    
    /**
     * Switches back to calendar view
     */
    @FXML
    private void monthButtonAction() {
        
        appointmentCalendar.setVisible(true);
        appointmentWeek.setVisible(false);
        selectWeekSpinner.setDisable(true);
    }
    
    
    /**
     * Switched to week view
     */
    @FXML
    private void weekButtonAction() {
        
        appointmentWeek.setVisible(true);
        appointmentCalendar.setVisible(false);
        selectWeekSpinner.setDisable(false);
    }
    
    
    /**
     * loads the appointment list for the designated calendar date
     */
    @FXML
    private void dayOfMonthCalendarAction(LocalDate calendarDate) {
        
        List<Appointment> appointmentsGoThroughList =
                AppointmentCalendar.getInstance().getAllAppointmentsForUser();
        
        List<Appointment> appointmentList = new ArrayList<>();
        
        for(Appointment appointment : appointmentsGoThroughList) {

            LocalDate appStartDate = appointment.getStartTime().toLocalDate();
            if(appStartDate.isEqual(calendarDate)){
                appointmentList.add(appointment);
            }
        }
        
        Comparator<Appointment> comparator = 
                Comparator.comparing(Appointment::getStartTime);
        
        /**
         * LAMBDA EXPRESSION -----
         * 
         * This expression helps with non-readable code.
         * 
         */
        dayOfMonthCalendarListView.setCellFactory(param -> new ListCell<Appointment>() {
            @Override
            protected void updateItem(Appointment item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getTitle()== null) {
                    setText(null);
                } else {
                    setText(item.getTitle() 
                            + " : " + item.getType() 
                            + " : " + item.getStartTime().format(timeFormatter)
                            + " - " + item.getEndTime().format(timeFormatter)
                            + " : " + item.getCustomer().getCustomerName()
                    );
                }
            }
        });
        
        ObservableList<Appointment> sortedList =
                FXCollections.observableArrayList(appointmentList);
        
        FXCollections.sort(sortedList, comparator);
        
        dayOfMonthCalendarListView.setItems(sortedList);
        
        dayOfMonthCalendarDateLabel.setText(calendarDate.toString());
        dayOfMonthCalendarListPane.setVisible(true);
    }
    
    
    /**
     * jumps to the appointment edit screen for the appointment selected in the appointment list view
     */
    @FXML
    private void dayOfMonthCalendarSelectButton() {
        Appointment app = dayOfMonthCalendarListView.getSelectionModel().getSelectedItem();
        
        try {
            FXMLLoader loader = new FXMLLoader();

            URL url = Paths.get(
               "src/schedulingapplication/view/AppointmentScene.fxml").toUri().toURL();

            loader.setLocation(url);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Main.getMainStage().setScene(scene);

            AppointmentSceneController controller = loader.getController();
            controller.setAppointmentOnLoad(app);

            Main.getMainStage().show();

        } catch (IOException ex) {
            System.out.println(
            "FAILED TO LOAD SCENE : from Hyperlink" + ex.getMessage());
        }
    }
    
    
    /**
     * closes the pop up of the appointment list view
     */
    @FXML
    private void dayOfMonthCalendarBackButton() {
        
        dayOfMonthCalendarListPane.setVisible(false);
    }
    
    
    /**
     * PLANNED TODO - possible appointment list view action for the week view
     */
    @FXML
    private void dayOfWeekCalendarAction() {
        
        //Select From List
    }
    
    
    /**
     * Navigation buttons and menus
     */
    @FXML
    private void appointmentsScheduleButtonAction() {
        
        Main.loadScene("AppointmentScene.fxml");
    }
    
    @FXML
    private void customerRecordsButtonAction() {
        
        Main.loadScene("CustomerScene.fxml");
    }
    
    @FXML
    private void reportsButtonAction() {
        
        Main.loadScene("ReportsScene.fxml");
    }
    
    @FXML
    private void signOutButtonAction() {
        
        LoggingHandler.getInstance().userSignOut();
        Main.loadScene("LoginScene.fxml");
    }
    
    @FXML
    private void calendarMenuButtonAction() {
        
        Main.loadScene("CalendarScene.fxml");
    }
    
    @FXML
    private void customerRecordsMenuButtonAction() {
        
        Main.loadScene("CustomerScene.fxml");
    }
    
    @FXML
    private void appointmentsScheduleMenuButtonAction() {
        
        Main.loadScene("AppointmentScene.fxml");
    }
    
    @FXML
    private void reportsMenuButtonAction() {
        
        Main.loadScene("ReportsScene.fxml");
    }
    
    @FXML
    private void weekCalendarMenuButtonAction() {
        
        Main.loadScene("CalendarScene.fxml");
    }
    
    
    /**
     * Initializer Method for Initializable Super Class
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /*
        Set todaysDate and timeNow.
        
        Apply Date and Time to appopriate labels with particular formats
        */
        todaysDate = LocalDate.now();
        timeNow = LocalTime.now();
        
        dateLabel.setText(dateFormatter.format(todaysDate));
        timeLabel.setText(timeFormatter.format(timeNow));  
        timeZoneLabel.setText(ZoneId.systemDefault().toString());

        /**
         *  Updates the Week Spinner with Numbers to allow simple traversal.
         *  Limits traversal -31 days and +31 days
        */
        setupWeekSpinner();
        
        /**
         *  Create a Month list and apply it to the spinner
        */
        setupMonthSpinner();
        
        
        /**
         * Create a Year list and apply it to the spinner
        */
        setupYearSpinner();
        
        
        /**
         * First Load Page set to todays date;
        */
        loadMonthIntoCalendar(todaysDate.getMonth(), Year.of(todaysDate.getYear()));
        loadWeekView(todaysDate, 0);
        
        
        /**
         * Add Listener Action for the month spinner list to 
         * load the appropriate calendarMonth
        */
        selectWeekSpinner.valueProperty().addListener(
                /**
                 * LAMBDA EXPRESSION -----
                 * 
                 * Each listener method is simpler with lambda as the event can be generic.
                 * If listener is declared through regular methods, the event has to be named,
                 * and if a custom listener is required, you must create an interface.
                 * 
                 */
                event -> {
                    monthLabel.setText(selectMonthSpinner.getValue().toString());
                    loadWeekView(
                            LocalDate.of(
                                    ((Year) selectYearSpinner.getValue()).getValue(),
                                    (Month)selectMonthSpinner.getValue(),
                                    todaysDate.getDayOfMonth()),
                            (int) selectWeekSpinner.getValue()
                    );
                }
        );
        
        
        /**
         *  Add Listener Action for the month spinner list
         *  to load the appropriate calendarMonth
        */
        selectMonthSpinner.valueProperty().addListener(
                
                event -> {
                    monthLabel.setText(selectMonthSpinner.getValue().toString());
                    selectWeekSpinner.getValueFactory().setValue(0);
                    loadMonthIntoCalendar(
                            (Month)selectMonthSpinner.getValue(), (Year)selectYearSpinner.getValue());
                    loadWeekView(LocalDate.of(
                            ((Year) selectYearSpinner.getValue()).getValue(),
                            (Month)selectMonthSpinner.getValue(),
                            todaysDate.getDayOfMonth()),0);
                }
        );
        
        
        /**
         * Add Listener Action for the year spinner list
         * to load the appropriate calendarMonth
        */
        selectYearSpinner.valueProperty().addListener(
                event -> {
                    monthLabel.setText(selectMonthSpinner.getValue().toString());
                    selectWeekSpinner.getValueFactory().setValue(0);
                    loadMonthIntoCalendar(
                            (Month)selectMonthSpinner.getValue(),
                            (Year)selectYearSpinner.getValue());
                    loadWeekView(LocalDate.of(
                            ((Year) selectYearSpinner.getValue()).getValue(),
                            (Month)selectMonthSpinner.getValue(),
                            todaysDate.getDayOfMonth()), 0);
                }
        );
    }  
}
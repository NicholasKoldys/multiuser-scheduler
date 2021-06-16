package dev.nicholaskoldys.multiuserscheduler.controller;

import dev.nicholaskoldys.multiuserscheduler.Main;
import dev.nicholaskoldys.multiuserscheduler.service.EntryFieldException;
import dev.nicholaskoldys.multiuserscheduler.model.Customer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import dev.nicholaskoldys.multiuserscheduler.model.AddressBook;
import dev.nicholaskoldys.multiuserscheduler.model.Country;

/**
 * FXML Controller class for Customer Create / Read / Update / Delete : CRUD
 * functions to a GUI.
 *
 * @author 
 * 
 */
public class CustomerSceneController implements Initializable {

    List<Country> countrysList;
    ObservableList<Country> allCountrysList;
    
    @FXML TableView<Customer> customerTable;
    @FXML TableColumn nameTableCol;
    @FXML TableColumn addressTableCol;
    @FXML TableColumn phoneTableCol;
    
    @FXML TextField nameTextField;
    @FXML TextField addressTextField;
    @FXML TextField address2TextField;
    @FXML TextField postalCodeTextField;
    @FXML TextField phoneNumTextField;
    @FXML TextField cityTextField;
    @FXML ComboBox countryComboBox;
    @FXML TextField phoneTextField;
    
    @FXML Button searchButton;
    @FXML TextField searchTextField;
    
    @FXML Button addButton;
    @FXML Button updateButton;
    @FXML Button deleteButton;
    @FXML Button backButton;
    
    
    /**
     * Simple search only allows names ATM
     * 
     */
    @FXML
    private void searchButtonAction() {
        
        customerTable.setItems(AddressBook.getInstance().lookupCustomers(
                        searchTextField.getText().trim()));
    }
    
    
    /**
     * sets all fields to create customer in Active Customer List and Database
     * 
     */
    @FXML
    private void addButtonAction() {
        
        try {
            
            if(!checkFieldsForEntry()) {
                throw new EntryFieldException(
                        "Warning::Attempted to enter invalid Customer::",
                        1, nameTextField.getText());
            }
            
            if(!nameTextField.getText().matches("[A-Z]+[a-z]+\\s[A-Za-z]+")) {
                throw new EntryFieldException(
                        "Warning::Attempted to enter invalid Customer Name::",
                        3, nameTextField.getText());
            }
            
            if(!postalCodeTextField.getText().matches("[0-9]{3,8}")) {
                throw new EntryFieldException(
                        "Warning::Attempted to enter invalid Customer Postal Code::",
                        5, postalCodeTextField.getText());
            }
            
            if(!phoneNumTextField.getText().matches("[0-9]{3}-[0-9]{3}-[0-9]{4}")) {
                
                if(phoneNumTextField.getText()
                        .matches("[0-9]+ [0-9]{2} [0-9]{1} [0-9]{3} [0-9]{4}")) {

                } else {
                    throw new EntryFieldException(
                            "Warning::Attempted to enter invalid Customer Phone Number::",
                            4, phoneNumTextField.getText());
                }
            }
            
            AddressBook.getInstance().addCustomer(
                    nameTextField.getText(),
                    addressTextField.getText(),
                    address2TextField.getText(),
                    postalCodeTextField.getText(),
                    phoneNumTextField.getText(),
                    cityTextField.getText(),
                    countryComboBox.getValue().toString(),
                    false
            );
            
            customerTable.refresh();
        
            
        } catch (EntryFieldException tex) {

            System.out.println(tex.getMessage());
            
        }
    }
    
    
    /**
     * sets all fields to save the current customer with the values in the Active
     * Customer List and Database
     * 
     */
    @FXML
    private void updateButtonAction() {
        
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        
        try {
            
            if(!checkFieldsForEntry()) {
                throw new EntryFieldException(
                        "Warning::Attempted to enter invalid Customer::",
                        1, nameTextField.getText());
            }
            
            if(!nameTextField.getText().matches("[A-Z]+[a-z]+\\s[A-Za-z]+")) {
                throw new EntryFieldException(
                        "Warning::Attempted to enter invalid Customer Name::",
                        3, nameTextField.getText());
            }
            
            if(!postalCodeTextField.getText().matches("[0-9]{3,8}")) {
                throw new EntryFieldException(
                        "Warning::Attempted to enter invalid Customer Postal Code::",
                        5, postalCodeTextField.getText());
            }
            
            if(!phoneNumTextField.getText().matches("[0-9]{3}-[0-9]{3}-[0-9]{4}")) {
                
                if(phoneNumTextField.getText()
                        .matches("[0-9]+ [0-9]{2} [0-9]{1} [0-9]{3} [0-9]{4}")) {

                } else {
                    throw new EntryFieldException(
                            "Warning::Attempted to enter invalid Customer Phone Number::",
                            4, phoneNumTextField.getText());
                }
            }
            
            AddressBook.getInstance().updateCustomer(
                    customer, 
                    nameTextField.getText(),
                    addressTextField.getText(),
                    address2TextField.getText(),
                    postalCodeTextField.getText(),
                    phoneNumTextField.getText(),
                    cityTextField.getText(),
                    countryComboBox.getValue().toString()
            );
            customerTable.refresh();
        } catch (EntryFieldException tex) {

            System.out.println(tex.getMessage());
            
        }
    }
    
    
    /**
     * Deactivates the customer from the list and Database
     */
    @FXML
    private void deleteButtonAction() {
        
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        
        if(customer != null) {
            Alert a = new Alert(AlertType.CONFIRMATION);
                a.setContentText(
                        "You selected to DELETE the Customer: \n\n" 
                        + customer.getCustomerName()
                        + "\n\n"
                        + "Delete the Customer by Pressing Ok..");
                a.showAndWait()
                        
                        /**
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
                        AddressBook.getInstance().deleteCustomer(customer);
                        customerTable.refresh();
                    });
        }
    }
    
    
    /**
     * loads a customer into the textFields by click of the mouse.
     */
    @FXML
    private void tableViewSelectAction() {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        
        if(customer != null) {
            
            
            nameTextField.setText(customer.getCustomerName());
            addressTextField.setText(customer.getAddress().getAddress());
            address2TextField.setText(customer.getAddress().getAddress2());
            postalCodeTextField.setText(customer.getAddress().getPostalCode());
            phoneNumTextField.setText(customer.getAddress().getPhoneNum());
            cityTextField.setText(customer.getAddress().getCity().getCity());
            countryComboBox.getSelectionModel().select(customer.getAddress()
                    .getCity().getCountry().getCountry());
            
            addButton.setDisable(false);
            updateButton.setDisable(false);
            deleteButton.setDisable(false);
        }
    }
    
    
    /**
     * When name field is typed into the buttons become active
     */
    @FXML
    private void nameTextFieldAction() {
        
        addButton.setDisable(false);
    }
    
    
    /**
     * Navigational Buttons and Menus
     */
    @FXML
    private void backButtonAction() {
        Main.loadScene("CalendarScene");
    }
    
    @FXML
    private void calendarMenuButtonAction() {
        Main.loadScene("CalendarScene");
    }
    
    @FXML
    private void appointmentsScheduleMenuButtonAction() {
        Main.loadScene("AppointmentScene");
    }
    
    @FXML
    private void reportsMenuButtonAction() {
        Main.loadScene("ReportsScene");
    }
    
    
    /**
     * loads a customer from appointment scene from a HyperLink
     * 
     * @param customer 
     */
    protected void setCustomerOnLoad(Customer customer) {
        
        searchTextField.setText(customer.getCustomerName());
        
        nameTextField.setText(customer.getCustomerName());
            addressTextField.setText(customer.getAddress().getAddress());
            address2TextField.setText(customer.getAddress().getAddress2());
            postalCodeTextField.setText(customer.getAddress().getPostalCode());
            phoneNumTextField.setText(customer.getAddress().getPhoneNum());
            cityTextField.setText(customer.getAddress().getCity().getCity());
            countryComboBox.getSelectionModel().select(customer.getAddress()
                    .getCity().getCountry().getCountry());
            
        addButton.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    
    
    private Boolean checkFieldsForEntry() {
        
        if (nameTextField.getText().isEmpty()
                || addressTextField.getText().isEmpty()
                || postalCodeTextField.getText().isEmpty()
                || phoneNumTextField.getText().isEmpty()
                || cityTextField.getText().isEmpty()
                || countryComboBox.getSelectionModel().getSelectedItem() == null) {
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
        
        /**
         * Sets ADD / UPDATE / DELETE buttons disabled
         */
        addButton.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        
        /**
         * set Country ComboBox with values from previously used countries.
         */
        countrysList = new ArrayList(); 
        countrysList = AddressBook.getInstance().getCountriesList();
        allCountrysList = FXCollections.observableArrayList(countrysList);
        countryComboBox.setItems(allCountrysList);
        countryComboBox.getSelectionModel().select("United States,US");
        
        
        /**
         * set customer view with all current active customers
         */
        customerTable.setItems(AddressBook.getInstance().getAllActiveCustomers());
            nameTableCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
            addressTableCol.setCellValueFactory(new PropertyValueFactory<>("AddressName"));
            phoneTableCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
    }    
}

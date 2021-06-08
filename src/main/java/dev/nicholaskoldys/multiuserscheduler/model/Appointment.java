package dev.nicholaskoldys.multiuserscheduler.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


/**
 *
 * @author nicho
 */
public class Appointment {
    
    private int appointmentId;
    private int customerId;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String customerRecordUrl;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Customer customer;
    private User user;
    
    
    public Appointment(Customer customer, User user, String title, String description, String location, String contact, String type, String customerRecordUrl, LocalDateTime startTime, LocalDateTime endTime) {
        
        
//        System.out.println(customer.getCustomerName() + " : " + user.getUserName() + " : " + title + " : " + description + " : " + location + " : " + type + " : " + customerRecordUrl + " : " + startTime + " : " + endTime);
        
        this.customerId = customer.getCustomerId();
        this.customer = customer;
        this.userId = user.getUserId();
        this.user = user;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.customerRecordUrl = customerRecordUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentId = 0;
    }
    
    
    public Appointment(int appointmentId, Customer customer, User user, String title, String description, String location, String contact, String type, String customerRecordUrl, LocalDateTime startTime, LocalDateTime endTime) {
        
        this(customer, user, title, description, location, contact, type, customerRecordUrl, startTime, endTime);
        this.appointmentId = appointmentId;

    }

    /**
     * @return the appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @param appointmentId the appointmentId to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the customerRecordUrl
     */
    public String getCustomerRecordUrl() {
        return customerRecordUrl;
    }

    /**
     * @param customerRecordUrl the customerRecordUrl to set
     */
    public void setCustomerRecordUrl(String customerRecordUrl) {
        this.customerRecordUrl = customerRecordUrl;
    }

    /**
     * @return the startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getCustomerName() {
        return customer.getCustomerName();
    }
    
    public String getAppointmentDate() {
        return startTime.format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)) 
                + " - " 
                + endTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
    }
    
    public String getUserName() {
        return user.getUserName();
    }
}
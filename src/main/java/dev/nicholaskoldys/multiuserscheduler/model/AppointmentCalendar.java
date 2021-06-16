package dev.nicholaskoldys.multiuserscheduler.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dev.nicholaskoldys.multiuserscheduler.model.dao.implementation.AddressDAOImpl;
import dev.nicholaskoldys.multiuserscheduler.model.dao.implementation.AppointmentDAOImpl;
import dev.nicholaskoldys.multiuserscheduler.model.dao.implementation.CityDAOImpl;
import dev.nicholaskoldys.multiuserscheduler.model.dao.implementation.CountryDAOImpl;
import dev.nicholaskoldys.multiuserscheduler.model.dao.implementation.UserDAOImpl;

/**
 *
 * @author nicho
 */
public class AppointmentCalendar {
    
    private static ObservableList<Appointment> appointmentsList =
            FXCollections.observableArrayList();
    private static ObservableList<Appointment> userAppointmentsList =
            FXCollections.observableArrayList();
    
    private final AppointmentDAOImpl appointmentData;
    private final UserDAOImpl userData;
    private final AddressBook addressBook;
    private final Schedule schedule;
    
    private List<User> usersList;
    private static User currentUser;
    
    private static final AppointmentCalendar instance= new AppointmentCalendar();

    
    private AppointmentCalendar() {
        userData = UserDAOImpl.getInstance();
        setUsersList(UserDAOImpl.getInstance().getAllUsers());

        addressBook = AddressBook.getInstance();
        addressBook.setCountriesList(
                CountryDAOImpl.getInstance().getAllCountries());
        addressBook.setCitiesList(
                CityDAOImpl.getInstance().getAllCities());
        addressBook.setAddressesList(
                AddressDAOImpl.getInstance().getAllAddresses());
        addressBook.loadAllCustomers();

        appointmentData = AppointmentDAOImpl.getInstance();

        schedule = Schedule.getInstance();
    }

    public static AppointmentCalendar getInstance() { return instance; }

    /**
     * 
     * @param userName
     * @param password
     * @return 
     */
    public Boolean loginUser(String userName, String password) {
        User loginUser = UserDAOImpl.getInstance().userLoginRequest(userName, password);
        if(loginUser != null) {
            if (loginUser.isActive()) {
                setCurrentUser(loginUser);
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @param loginUser 
     */
    private void setCurrentUser(User loginUser) { currentUser = loginUser; }

    /**
     * 
     * @return 
     */
    public static User getCurrentUser() { return currentUser; }
    
    /**
     * 
     * @param usersList 
     */
    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }
     
    /**
     * 
     * @return 
     */
    public List<User> getUsersList() {
        return usersList;
    }

    /**
     * 
     * @param user 
     */
    public User addUser(User user) {
        User lookup = lookupUser(user.getUserName(), user.getPassword(), user.getActive());
        if(lookup == null) {
            UserDAOImpl.getInstance().create(user);
            user.setUserId(UserDAOImpl.getInstance().getUserId(user));
            usersList.add(user);
            return user;
        }
        return lookup;
    }
    
    /**
     * When removing User from database remove all appointments from user and place it in the admin user.
     * 
     * Admin user; test id 1
     * 
     * @param user 
     */
    public void deleteUser(User user) {
        
        usersList.remove(user);
        
        for(Appointment appointment : getAllAppointments()) {
            if(appointment.getUserId() == user.getUserId()) {
                appointment.setUser(lookupUser(1));
                appointment.setUserId(1);
            }
        }
        userData.delete(user);
    }

    /**
     * 
     * @param userName
     * @param password
     * @param active
     * @return 
     */
    public User lookupUser(String userName, String password, Boolean active) {
        for(User user : usersList) {
            if(user.getUserName().equals(userName)
                    && user.getPassword().equals(password)
                    && user.getActive().equals(active)) {
                
                return user;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param userId
     * @return 
     */
    public User lookupUser(int userId) {
        for(User user : usersList) {
            if(user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    /**
     * 
     * @param appointment 
     */
    public void addAppointment(Appointment appointment) {
        appointmentData.create(appointment);
        appointment.setAppointmentId(
                appointmentData.getAppointmentId(
                        appointment.getCustomerId(), appointment.getUserId(),
                        appointment.getTitle(), appointment.getLocation(),
                        appointment.getType(), appointment.getStartTime(),
                        appointment.getEndTime())
        );
        schedule.addToSchedule(appointment);
        appointmentsList.add(appointment);
        userAppointmentsList.add(appointment);
    }

    /**
     * 
     * @param appointment 
     */
    public void updateAppointment(Appointment appointment) {
        //remove appointments previous startTime
        Schedule.getInstance().removeFromSchedule(
                lookupAppointment(appointment.getAppointmentId()));
        
        appointmentsList.remove(appointment);
        userAppointmentsList.remove(appointment);
        appointmentData.update(appointment);
        
        //add new startTime
        schedule.addToSchedule(appointment);
        appointmentsList.add(appointment);
        userAppointmentsList.add(appointment);
    }
    
    /**
     * 
     * @param appointment 
     */
    public void deleteAppointment(Appointment appointment) {
        schedule.removeFromSchedule(appointment);
        appointmentsList.remove(appointment);
        appointmentData.delete(appointment);
        userAppointmentsList.remove(appointment);
    }
    
    /**
     * 
     * @param specificTime
     * @return 
     */
    public Appointment lookupAppointmentWithTime(LocalDateTime specificTime) {
        LocalDate specDate = specificTime.toLocalDate();
        LocalTime specTime = specificTime.toLocalTime();
        
        for (Appointment appointment : userAppointmentsList) {
            LocalDate appDate = appointment.getStartTime().toLocalDate();
            LocalTime appTime = appointment.getStartTime().toLocalTime();
            if (specDate.equals(appDate) 
                    && specTime.isBefore(appTime)
                    && specTime.plusMinutes(15).isAfter(appTime)) {
                return appointment;
            }
        }
        return null;
    }

    /**
     * 
     * @param appointmentId
     * @return 
     */
    public Appointment lookupAppointment(int appointmentId) {
        for (Appointment appointment : userAppointmentsList) {
            if (appointmentId == appointment.getAppointmentId()) {
                return appointment;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param appointmentIdList
     * @return 
     */
    public ObservableList<Appointment> lookupAppointments(
            List<Integer> appointmentIdList) {
       ObservableList<Appointment> lookupAppointmentList =
               FXCollections.observableArrayList();
        
        for (Integer appointmentId : appointmentIdList) {
            if (lookupAppointment(appointmentId) != null) {
                lookupAppointmentList.add(lookupAppointment(appointmentId));
            }
        }
        return lookupAppointmentList;
    }

    /**
     * 
     * @param appointmentTitle
     * @return 
     */
    public ObservableList<Appointment> lookupAppointments(
            String appointmentTitle) {
       ObservableList<Appointment> lookupAppointmentList =
               FXCollections.observableArrayList();
        
        for (Appointment appointment : userAppointmentsList) {
            if (appointment.getTitle().contains(appointmentTitle)) {
                lookupAppointmentList.add(appointment);
            }
        }
        return lookupAppointmentList;
    }
    
    /**
     * 
     * @param customer
     * @return 
     */
    public ObservableList<Appointment> lookupAppointments(Customer customer) {
        ObservableList<Appointment> lookupAppointmentList =
                FXCollections.observableArrayList();
        
        for (Appointment appointment : userAppointmentsList) {
            if (appointment.getCustomerId() == customer.getCustomerId()) {
                lookupAppointmentList.add(appointment);
            }
        }
        return lookupAppointmentList;
    }

    /**
     * 
     * @return 
     */
    public Appointment lookupAppointment(Customer customer, User user, 
            LocalDateTime startTime, LocalDateTime endTime) {
        for(Appointment appointment : userAppointmentsList) {
            if(appointment.getCustomerId() == customer.getCustomerId() 
                    && appointment.getUserId() == user.getUserId()
                    && appointment.getStartTime().equals(startTime)
                    && appointment.getEndTime().equals(endTime)) {
                
                return appointment;
            }
        }
        return null;
    }
    
    /**
     * 
     * @return 
     */
    public void setupApplicationCalendar(){
        loadAllAppointments();
        schedule.createSchedule();
    }

    /**
     * 
     */
    private Boolean loadAllAppointments() {
        appointmentsList = FXCollections.observableArrayList(
                appointmentData.getAllAppointments());
        List<Appointment> userOnlyAppointments = new ArrayList<>();
        for(Appointment appointment : appointmentsList) {
            if(appointment.getUserId() == currentUser.getUserId()) {
                if(appointment.getCustomer().getActive() != false) {
                    userOnlyAppointments.add(appointment);
                }
            }
        }
        userAppointmentsList = FXCollections.observableArrayList(userOnlyAppointments);
        return (appointmentsList != null);
    }

    /**
     * 
     * @return 
     */
    public ObservableList<Appointment> getAllAppointments() { return appointmentsList; }
    
    /**
     * 
     * @return 
     */
    public ObservableList<Appointment> getAllAppointmentsForUser() { return userAppointmentsList; }
}

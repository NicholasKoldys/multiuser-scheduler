package dev.nicholaskoldys.multiuserscheduler.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Schedule Model
 * 
 * for current used startTime and endTime of currentUsers appointments.
 * 
 * Will later add in support for appointment linkage but the running model works
 * as intended.
 * 
 *
 * @author Nicholas Koldys
 */
public class Schedule {
    
    private static Set<LocalDateTime> schedule;
    
    private static final Schedule instance = new Schedule();
    
    
    /**
     * 
     * @return 
     */
    public static Schedule getInstance() {
        return instance;
    }
    
    
    /**
     * 
     */
    public void createSchedule() {
        
        schedule = new HashSet<>();
        List<Appointment> appointmentsList 
                = AppointmentCalendar.getInstance().getAllAppointmentsForUser();
        
        for(Appointment appointment : appointmentsList) {
            schedule.add(appointment.getStartTime());
        }
    }
    
    
    /**
     * 
     * @param appointment
     * @return 
     */
    public Boolean isTimeOpen(Appointment appointment) {
        
        List<Appointment> appointmentsList 
                = AppointmentCalendar.getInstance().getAllAppointmentsForUser();
        
        for(Appointment app : appointmentsList) {
            if(appointment.getStartTime().isEqual(app.getStartTime())
                    && appointment.getAppointmentId() != app.getAppointmentId()) {
                return false;
            }
            if(appointment.getEndTime().isEqual(app.getEndTime())
                    && appointment.getAppointmentId() != app.getAppointmentId()) {
                return false;
            }
        }
        return testBetweenAppointments(appointment.getStartTime(), appointment.getEndTime(), true);
    }
    
    
    /**
     * 
     * @param start
     * @param end
     * @return 
     */
    public Boolean testBetweenAppointments(LocalDateTime start, LocalDateTime end, Boolean timeOpenTested) {
        
        List<Appointment> appointmentsList 
                = AppointmentCalendar.getInstance().getAllAppointmentsForUser();
        
        for(Appointment appointment : appointmentsList) {
            if(timeOpenTested == false) {
                if(appointment.getStartTime().equals(start) || appointment.getEndTime().equals(end)) {
                    return false;
                }
            }
            if(appointment.getStartTime().isBefore(start) 
                    && appointment.getEndTime().isAfter(start)) {
                return false;
            }
            if(appointment.getStartTime().isBefore(end)
                    && appointment.getEndTime().isAfter(end)){
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * 
     * @param appointment 
     */
    public void addToSchedule(Appointment appointment) {
        schedule.add(appointment.getStartTime());
    }
    
    
    /**
     * 
     * @param appointment
     */
    public void removeFromSchedule(Appointment appointment) {
        schedule.remove(appointment.getStartTime());
    }
}
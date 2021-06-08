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
        
//    private static Map<LocalDate, Map<LocalTime, Duration>>  appointmentSchedule;
//    
//    private static Map<LocalTime, Duration> durationMap;
    
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
        //schedule.put(appointment.getStartTime(), appointment);
    }
    
    
    /**
     * 
     * @param appointment
     */
    public void removeFromSchedule(Appointment appointment) {
        
        schedule.remove(appointment.getStartTime());
        //schedule.remove(appointment.getStartTime(), appointment);
    }
    
    
//    /**
//     * Initializes the Schedule mappings
//     * 
//     */
//    public void createAppointmentSchedule() {
//        
//        appointmentSchedule = new HashMap<>();
//        
//        for (Appointment appointment : 
//                AppointmentCalendar.getInstance().getAllAppointmentsForUser()) {
//            
//            LocalDate appDate =
//                    appointment.getStartTime().toLocalDate();
//            LocalTime appStartTime =
//                    appointment.getStartTime().toLocalTime();
//            LocalTime appEndTime =
//                    appointment.getEndTime().toLocalTime();
//            
//            if(appointmentSchedule.containsKey(appDate)) {
//                
//                if(appointmentSchedule.get(appDate)
//                        .containsKey(appStartTime)) {
//                    
//                    System.out.println(
//                    "PLEASE UPDATE DATE AND TIME FOR : " 
//                        + appointment.getAppointmentId());
//                } else {
//                    
//                    durationMap = new HashMap<>();
//                    
//                    Duration length = 
//                            Duration.between(appStartTime, appEndTime);
//                
//                    durationMap.put(appStartTime, length);
//                    
//                    appointmentSchedule.put(appDate, durationMap);
//                }
//            } else {
//                
//                durationMap = new HashMap<>();
//                    
//                Duration length =
//                        Duration.between(appStartTime, appEndTime);
//                
//                durationMap.put(appStartTime, length);
//                    
//                appointmentSchedule.put(appDate, durationMap);
//            }
//        }
//    }
//    
//    
//    /**
//     * 
//     * Checks the specified Times and tests inside the mappings for availability
//     * 
//     * @param startDateTime
//     * @param endDateTime
//     * @return 
//     */
//    public Boolean isAppointmentTimeOpen(
//            LocalDateTime startDateTime, LocalDateTime endDateTime) {
//        
//        LocalDate Date = startDateTime.toLocalDate();
//        LocalTime startTime = startDateTime.toLocalTime();
//        
//        
//        if(appointmentSchedule.containsKey(Date)) {
//            
//            if(appointmentSchedule.get(Date)
//                        .containsKey(startTime)) {
//                
//                return false;
//            } else {
//
//                Map<LocalTime, Duration> map =
//                        appointmentSchedule.get(Date);
//                
//                Duration selectDuration = Duration.between(startTime, endDateTime);
//                
//                for(Map.Entry<LocalTime, Duration> entry :
//                        map.entrySet()) {
//                    
//                    if(entry.getKey().isBefore(startTime) 
//                            && entry.getKey().plus(entry.getValue())
//                                    .isAfter(startTime)){
//                        return false;
//                    }
//                    if(startTime.isBefore(entry.getKey()) 
//                            && startTime.plus(selectDuration)
//                                    .isAfter(entry.getKey())) {
//                        return false;
//                    }
//                }
//                return true;
//            }
//        }
//        return true;
//    }
    
    
    
    
    
//    /**
//     * 
//     * Add to schedule
//     * 
//     * @param appointment 
//     */
//    public void addAppointmentToSchedule(
//            Appointment appointment) {
//        
//        LocalDate appDate =
//                appointment.getStartTime().toLocalDate();
//        LocalTime appStartTime =
//                appointment.getStartTime().toLocalTime();
//        LocalTime appEndTime = 
//                appointment.getEndTime().toLocalTime();
//        
//        if(appointmentSchedule.containsKey(appDate)) {
//            
//            appointmentSchedule.get(appDate).put(
//                    appStartTime, Duration.between(
//                            appStartTime, appEndTime));
//        } else {
//            
//            durationMap = new HashMap();
//            
//            durationMap.put(
//                    appStartTime, Duration.between(
//                            appStartTime,appEndTime));
//            
//            appointmentSchedule.put(
//                    appDate, durationMap);
//        }
//    }
    
    
//    /**
//     * Remove old apply new to schedule
//     * 
//     * @param appointment 
//     */
//    public void updateAppointmentSchedule(
//            Appointment appointment) {
//        
//        LocalDate appDate =
//                appointment.getStartTime().toLocalDate();
//        LocalTime appStartTime =
//                appointment.getStartTime().toLocalTime();
//        LocalTime appEndTime = 
//                appointment.getEndTime().toLocalTime();
//        
//        if(appointmentSchedule.containsKey(appDate)) {
//            
//            if(appointmentSchedule.get(appDate)
//                    .containsKey(appStartTime)){
//                
//                appointmentSchedule.get(appDate)
//                        .replace(appStartTime, Duration
//                                .between(appStartTime, appEndTime));   
//            } else {
//                appointmentSchedule.get(appDate).put(
//                    appStartTime, Duration.between(
//                            appStartTime, appEndTime));
//            }
//        } else {
//            durationMap = new HashMap();
//            
//            durationMap.put(
//                    appStartTime, Duration.between(
//                            appStartTime,appEndTime));
//            
//            appointmentSchedule.put(
//                    appDate, durationMap);
//        }
//    }
    
    
//    /**
//     * 
//     * remove from schedule
//     * 
//     * @param appointment 
//     */
//    public void deleteAppointmentFromSchedule(
//            Appointment appointment) {
//        
//        LocalDate appDate =
//                appointment.getStartTime().toLocalDate();
//        LocalTime appStartTime =
//                appointment.getStartTime().toLocalTime();
//        LocalTime appEndTime = 
//                appointment.getEndTime().toLocalTime();
//        
//        if(appointmentSchedule.containsKey(appDate)) {
//            
//            if(appointmentSchedule.get(appDate)
//                    .containsKey(appStartTime)){
//                
//                appointmentSchedule.get(appDate)
//                        .remove(appStartTime, Duration.between(
//                                appStartTime, appEndTime));
//            }
//        }
//    }
    
//    public void soundOutSchedule() {
//        
//        Map<LocalDate, Map<LocalTime, Duration>> map =
//                        appointmentSchedule;
//                
//        for( entry :
//                map.entrySet()) {
//
//            if(entry.getKey().isBefore(startTime) 
//                    && entry.getKey().plus(entry.getValue())
//                            .isAfter(startTime)){
//                return false;
//            }
//        }
//    }
}
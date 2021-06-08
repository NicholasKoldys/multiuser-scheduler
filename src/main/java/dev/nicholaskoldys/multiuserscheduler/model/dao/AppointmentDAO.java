package dev.nicholaskoldys.multiuserscheduler.model.dao;

import java.util.List;
import dev.nicholaskoldys.multiuserscheduler.model.Appointment;

/**
 *
 * @author nicho
 */
public interface AppointmentDAO {
    
    List<Appointment> getAllAppointments();
    Appointment getAppointment(int appointmentId);
    Boolean create(Appointment appointment);
    Boolean update(Appointment appointment);
    Boolean delete(Appointment appointment);
}

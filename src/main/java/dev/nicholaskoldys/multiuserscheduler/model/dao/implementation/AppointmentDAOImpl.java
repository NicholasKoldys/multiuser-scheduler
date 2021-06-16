package dev.nicholaskoldys.multiuserscheduler.model.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import dev.nicholaskoldys.multiuserscheduler.model.AddressBook;
import dev.nicholaskoldys.multiuserscheduler.model.Appointment;
import dev.nicholaskoldys.multiuserscheduler.model.AppointmentCalendar;
import dev.nicholaskoldys.multiuserscheduler.model.dao.AppointmentDAO;
import dev.nicholaskoldys.multiuserscheduler.service.ConvertTimeService;
import dev.nicholaskoldys.multiuserscheduler.service.DatabaseConnection;
import dev.nicholaskoldys.multiuserscheduler.service.EnvironmentVariables;

/**
 *
 * @author nicho
 */
public class AppointmentDAOImpl implements AppointmentDAO {
        
    private final String TABLE_APPOINTMENT = "appointment";
    private final String APPOINTMENTID_COLUMN = "appointmentId";
    private final String CUSTOMERID_COLUMN = "customerId";
    private final String USERID_COLUMN = "userId";
    private final String APPOINTMENT_TITLE_COLUMN = "title";
    private final String APPOINTMENT_DESCRIPTION_COLUMN = "description";
    private final String APPOINTMENT_LOCATION_COLUMN = "location";
    private final String APPOINTMENT_CONTACT_COLUMN = "contact";
    private final String APPOINTMENT_TYPE_COLUMN = "type";
    private final String APPOINTMENT_URL_COLUMN = "url";
    private final String APPOINTMENT_STARTTIME_COLUMN = "start";
    private final String APPOINTMENT_ENDTIME_COLUMN = "end";
    
    private final String CREATEDATE_COLUMN = "createDate";
    private final String CREATEDBY_COLUMN = "createdBy";
    private final String LASTUPDATE_COLUMN = "lastUpdate";
    private final String LASTUPDATEBY_COLUMN = "lastUpdateBy";
    
    private final String SELECT_ALL_APPOINTMENT =
            "SELECT * FROM " + TABLE_APPOINTMENT;
    
    private final String SELECT_SPECIFIC_APPOINTMENT = 
            "SELECT * FROM " + TABLE_APPOINTMENT
            + " WHERE "  + APPOINTMENTID_COLUMN + " = ?";
    
    private final String SELECT_SPECIFIC_APPOINTMENT_BY_ID = 
            "SELECT " + APPOINTMENTID_COLUMN + " FROM " 
            + TABLE_APPOINTMENT
            + " WHERE " + CUSTOMERID_COLUMN + " = ? AND "
            + USERID_COLUMN + " = ? AND "
            + APPOINTMENT_TITLE_COLUMN + " = ? AND "
            + APPOINTMENT_LOCATION_COLUMN + " = ? AND "
            + APPOINTMENT_TYPE_COLUMN + " = ? AND "
            + APPOINTMENT_STARTTIME_COLUMN + " = ? AND "
            + APPOINTMENT_ENDTIME_COLUMN + " = ?";
    
    private final String INSERT_APPOINTMENT = 
            "INSERT INTO " + TABLE_APPOINTMENT + " ("
            + CUSTOMERID_COLUMN + ", "
            + USERID_COLUMN + ", "
            + APPOINTMENT_TITLE_COLUMN + ", "
            + APPOINTMENT_DESCRIPTION_COLUMN + ", "
            + APPOINTMENT_LOCATION_COLUMN + ", "
            + APPOINTMENT_CONTACT_COLUMN + ", "
            + APPOINTMENT_TYPE_COLUMN + ", "
            + APPOINTMENT_URL_COLUMN + ", "
            + APPOINTMENT_STARTTIME_COLUMN + ", "
            + APPOINTMENT_ENDTIME_COLUMN + ", "
            + CREATEDATE_COLUMN + ", "
            + CREATEDBY_COLUMN + ", "
            + LASTUPDATE_COLUMN + ", "
            + LASTUPDATEBY_COLUMN + ") "
            + "VALUES ( ?, ?, ?, ?, ?, ?, ?,"
            + " ?, ?, ?, "
            + EnvironmentVariables.CURRENTTIME_METHOD + ", " + "?" + ","
            + EnvironmentVariables.CURRENTTIME_METHOD + ", " + "?" + ")";
    
    private final String UPDATE_APPOINTMENT = 
            "UPDATE " + TABLE_APPOINTMENT + " SET " 
            + CUSTOMERID_COLUMN + " = ?, " 
            + USERID_COLUMN + " = ?, " 
            + APPOINTMENT_TITLE_COLUMN + " = ?, "
            + APPOINTMENT_DESCRIPTION_COLUMN + " = ?, "
            + APPOINTMENT_LOCATION_COLUMN + " = ?, "
            + APPOINTMENT_CONTACT_COLUMN + " = ?, "
            + APPOINTMENT_TYPE_COLUMN + " = ?, "
            + APPOINTMENT_URL_COLUMN + " = ?, "
            + APPOINTMENT_STARTTIME_COLUMN + " = ?, "
            + APPOINTMENT_ENDTIME_COLUMN + " = ?, "
            + LASTUPDATE_COLUMN + " = " + EnvironmentVariables.CURRENTTIME_METHOD + ", "
            + LASTUPDATEBY_COLUMN + " = ?"
            + " WHERE " + APPOINTMENTID_COLUMN + " = ?";
    
    private final String DELETE_APPOINTMENT = 
            "DELETE FROM " + TABLE_APPOINTMENT
            + " WHERE " + APPOINTMENTID_COLUMN + " = ?";
    
    private static final AppointmentDAOImpl instance =
            new AppointmentDAOImpl();
    
 
    /**
     * 
     * @return 
     */
    public static AppointmentDAOImpl getInstance() {
        return instance;
    }
    
    
    /**
     * 
     * @return 
     */
    @Override
    public List<Appointment> getAllAppointments() {
 
        List<Appointment> appointmentList = new ArrayList<>();
        
        try (PreparedStatement selectStatement =
                DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_ALL_APPOINTMENT);
                ResultSet results = selectStatement.executeQuery()) {
            while (results.next()) {
                Appointment appointment = new Appointment(
                        results.getInt(APPOINTMENTID_COLUMN),
                        AddressBook.getInstance().lookupCustomer(
                                results.getInt(CUSTOMERID_COLUMN)),
                        AppointmentCalendar.getInstance().lookupUser(
                                results.getInt(USERID_COLUMN)),
                        results.getString(APPOINTMENT_TITLE_COLUMN),
                        results.getString(APPOINTMENT_DESCRIPTION_COLUMN),
                        results.getString(APPOINTMENT_LOCATION_COLUMN),
                        results.getString(APPOINTMENT_CONTACT_COLUMN),
                        results.getString(APPOINTMENT_TYPE_COLUMN),
                        results.getString(APPOINTMENT_URL_COLUMN),
                        ConvertTimeService.fromUTC(results.getTimestamp(
                                APPOINTMENT_STARTTIME_COLUMN).toLocalDateTime()),
                        ConvertTimeService.fromUTC(results.getTimestamp(
                                APPOINTMENT_ENDTIME_COLUMN).toLocalDateTime())
                );

                if(AddressBook.getInstance().lookupCustomer(results.getInt(CUSTOMERID_COLUMN)) == null) {
                    delete(appointment);
                    continue;
                }
                appointmentList.add(appointment);
            }
            return appointmentList;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 
     * @param appointmentId
     * @return 
     */
    @Override
    public Appointment getAppointment(int appointmentId) {
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_APPOINTMENT)) {

            selectStatement.setInt(1, appointmentId);
            ResultSet results = selectStatement.executeQuery();
            results.next();

            Appointment appointment = new Appointment(
                    results.getInt(APPOINTMENTID_COLUMN),
                    AddressBook.getInstance().lookupCustomer(
                            results.getInt(CUSTOMERID_COLUMN)),
                    AppointmentCalendar.getInstance().lookupUser(
                            results.getInt(USERID_COLUMN)),
                    results.getString(APPOINTMENT_TITLE_COLUMN),
                    results.getString(APPOINTMENT_DESCRIPTION_COLUMN),
                    results.getString(APPOINTMENT_LOCATION_COLUMN),
                    results.getString(APPOINTMENT_CONTACT_COLUMN),
                    results.getString(APPOINTMENT_TYPE_COLUMN),
                    results.getString(APPOINTMENT_URL_COLUMN),
                    ConvertTimeService.fromUTC(results.getTimestamp(
                            APPOINTMENT_STARTTIME_COLUMN).toLocalDateTime()),
                    ConvertTimeService.fromUTC(results.getTimestamp(
                            APPOINTMENT_ENDTIME_COLUMN).toLocalDateTime())
            );

            if(AddressBook.getInstance().lookupCustomer(results.getInt(CUSTOMERID_COLUMN)) == null) {
                    delete(appointment);
                    return null;
            }
            return appointment;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Appointment getAppointment(int appointmentId, boolean isAltMethod) {
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_APPOINTMENT)) {

            selectStatement.setInt(1, appointmentId);
            ResultSet results = selectStatement.executeQuery();
            results.next();

            Appointment appointment = new Appointment(
                    results.getInt(APPOINTMENTID_COLUMN),
                    CustomerDAOImpl.getInstance().getCustomer(
                            results.getInt(CUSTOMERID_COLUMN), true),
                    UserDAOImpl.getInstance().getUser(
                            results.getInt(USERID_COLUMN)),
                    results.getString(APPOINTMENT_TITLE_COLUMN),
                    results.getString(APPOINTMENT_DESCRIPTION_COLUMN),
                    results.getString(APPOINTMENT_LOCATION_COLUMN),
                    results.getString(APPOINTMENT_CONTACT_COLUMN),
                    results.getString(APPOINTMENT_TYPE_COLUMN),
                    results.getString(APPOINTMENT_URL_COLUMN),
                    ConvertTimeService.fromUTC(results.getTimestamp(
                            APPOINTMENT_STARTTIME_COLUMN).toLocalDateTime()),
                    ConvertTimeService.fromUTC(results.getTimestamp(
                            APPOINTMENT_ENDTIME_COLUMN).toLocalDateTime())
            );

            //Delete the Appointment if customer was removed.
            if(CustomerDAOImpl.getInstance().getCustomer(results.getInt(CUSTOMERID_COLUMN), true) == null) {
                delete(appointment);
                return null;
            }
            return appointment;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 
     * @param customerId
     * @param userId
     * @param title
     * @param location
     * @param type
     * @param startTime
     * @param endTime
     * @return 
     */
    public int getAppointmentId(int customerId, int userId, String title, String location, String type, LocalDateTime startTime, LocalDateTime endTime) {

        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_APPOINTMENT_BY_ID)) {

            selectStatement.setInt(1, customerId);
            selectStatement.setInt(2, userId);
            selectStatement.setString(3, title);
            selectStatement.setString(4, location);
            selectStatement.setString(5, type);
            selectStatement.setTimestamp(6, ConvertTimeService.toMySQLDB(startTime));
            selectStatement.setTimestamp(7, ConvertTimeService.toMySQLDB(endTime));
            ResultSet results = selectStatement.executeQuery();

            results.next();
            return results.getInt(APPOINTMENTID_COLUMN);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    
    /**
     * 
     * @param appointment
     * @return 
     */
    @Override
    public Boolean create(Appointment appointment) {
        try (PreparedStatement insertStatement = 
                DatabaseConnection.getDatabaseConnection()
                        .prepareStatement(INSERT_APPOINTMENT)) {
            /*
            CUSTOMERID_COLUMN 
            + ", " + USERID_COLUMN 
            + ", " + APPOINTMENT_TITLE_COLUMN 
            + ", " + APPOINTMENT_DESCRIPTION_COLUMN
            + ", " + APPOINTMENT_LOCATION_COLUMN
            + ", " + APPOINTMENT_CONTACT_COLUMN
            + ", " + APPOINTMENT_TYPE_COLUMN
            + ", " + APPOINTMENT_URL_COLUMN
            + ", " + APPOINTMENT_STARTTIME_COLUMN
            + ", " + APPOINTMENT_ENDTIME_COLUMN
            + ", " + CREATEDATE_COLUMN 
            + ", " + CREATEDBY_COLUMN 
            + ", " + LASTUPDATE_COLUMN 
            + ", " + LASTUPDATEBY_COLUMN + ") "
            */
            
            insertStatement.setInt(1, appointment.getCustomerId());
            insertStatement.setInt(2, appointment.getUserId());
            insertStatement.setString(3, appointment.getTitle());
            insertStatement.setString(4, appointment.getDescription());
            insertStatement.setString(5, appointment.getLocation());
            insertStatement.setString(6, appointment.getContact());
            insertStatement.setString(7, appointment.getType());
            insertStatement.setString(8, 
                    appointment.getCustomerRecordUrl());
            insertStatement.setTimestamp(9, 
                    ConvertTimeService.toMySQLDB(appointment.getStartTime()));
            insertStatement.setTimestamp(10, 
                    ConvertTimeService.toMySQLDB(appointment.getEndTime()));
            insertStatement.setString(11, AppointmentCalendar.getCurrentUser().getUserName());
            insertStatement.setString(12, AppointmentCalendar.getCurrentUser().getUserName());
            
            if (insertStatement.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    
    /**
     * 
     * @param appointment
     * @return 
     */
    @Override
    public Boolean update(Appointment appointment) {
        try (PreparedStatement updateStatement
                     = DatabaseConnection.getDatabaseConnection().prepareStatement(UPDATE_APPOINTMENT)) {

            updateStatement.setInt(1, appointment.getCustomerId());
            updateStatement.setInt(2, appointment.getUserId());
            updateStatement.setString(3, appointment.getTitle());
            updateStatement.setString(4, appointment.getDescription());
            updateStatement.setString(5, appointment.getLocation());
            updateStatement.setString(6, appointment.getContact());
            updateStatement.setString(7, appointment.getType());
            updateStatement.setString(8, 
                    appointment.getCustomerRecordUrl());
            updateStatement.setTimestamp(9, 
                    ConvertTimeService.toMySQLDB(appointment.getStartTime()));
            updateStatement.setTimestamp(10, 
                    ConvertTimeService.toMySQLDB(appointment.getEndTime()));
            updateStatement.setInt(11, appointment.getAppointmentId());
            updateStatement.setString(12, AppointmentCalendar.getCurrentUser().getUserName());
            
            if (updateStatement.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    
    /**
     * 
     * @param appointment
     * @return 
     */
    @Override
    public Boolean delete(Appointment appointment) {
        try (PreparedStatement deleteStatement
                     = DatabaseConnection.getDatabaseConnection().prepareStatement(DELETE_APPOINTMENT)) {
            
            deleteStatement.setInt(1, appointment.getAppointmentId());
            
            if (deleteStatement.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

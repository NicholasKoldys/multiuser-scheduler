//package schedulingapplication.model.dao;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import schedulingapplication.model.Appointment;
//import schedulingapplication.model.User;
//import schedulingapplication.service.DatabaseConnection;
//
///**
// *
// * @author nicho
// */
//class Datasource {
//    
//    private static final String TABLE_APPOINTMENT = "appointment";
//    private static final String APPOINTMENTID_COLUMN = "appointmentId";
//    private static final String APPOINTMENT_TITLE_COLUMN = "title";
//    private static final String APPOINTMENT_DESCRIPTION_COLUMN = "description";
//    private static final String APPOINTMENT_LOCATION_COLUMN = "location";
//    private static final String APPOINTMENT_CONTACT_COLUMN = "contact";
//    private static final String APPOINTMENT_TYPE_COLUMN = "type";
//    private static final String APPOINTMENT_URL_COLUMN = "url";
//    private static final String APPOINTMENT_STARTTIME_COLUMN = "start";
//    private static final String APPOINTMENT_ENDTIME_COLUMN = "end";
//    
//    
//    
//    private static final String ADDRESSID_COLUMN_VIEW = "address_Id";
//    private static final String CITYID_COLUMN_VIEW = "city_Id";
//    private static final String COUNTRYID_COLUMN_VIEW = "country_Id";
//
//    private static final String CUSTOMER_TO_ADDRESS_VIEW = "customerToAddressView";
//    
//    
//    private static final String USER_SELECT_STATEMENT = 
//            "SELECT * "
//            +"FROM " + TABLE_USER;
//    
//    private static final String APPOINTMENT_SELECT_STATEMENT = 
//            "SELECT * "
//            +"FROM " + TABLE_APPOINTMENT;
//    
//    
//    List<User> getAllUsersDatabase() {
//        
//        try (PreparedStatement selectUser = DatabaseConnection.getDatabaseConnection()
//                .prepareStatement(USER_SELECT_STATEMENT);
//                ResultSet results = selectUser.executeQuery()) {
//            
//            List<User> userList = new ArrayList<>();
//            
//            while (results.next()) {
//                
//                User user = new User(
//                        results.getInt(USERID_COLUMN),
//                        results.getString(USER_NAME_COLUMN),
//                        results.getBoolean(USER_ACTIVE_COLUMN)
//                );
//                userList.add(user);
//            }
//            return userList;
//            
//        } catch(SQLException ex) {
//            
//        }
//        return null;
//    }
//    
//    List<Appointment> getAllAppointmentsDatabase() {
//        
//        try (PreparedStatement selectAppointment = DatabaseConnection.getDatabaseConnection()
//                .prepareStatement(APPOINTMENT_SELECT_STATEMENT);
//                ResultSet results = selectAppointment.executeQuery()) {
//            
//            List<Appointment> appointmentList = new ArrayList<>();
//            
//            while (results.next()) {
//                
//                Appointment appointment = new Appointment(
//                        results.getInt(USERID_COLUMN),
//                        results.getString(USER_NAME_COLUMN),
//                        results.getBoolean(USER_ACTIVE_COLUMN)
//                );
//                appointmentList.add(appointment);
//            }
//            return appointmentList;
//            
//        } catch(SQLException ex) {
//            
//        }
//        return null;        
//    }
//}
//

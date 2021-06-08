//package schedulingapplication.model.dao;
//
//import schedulingapplication.model.Appointment;
//import schedulingapplication.model.Report;
//import schedulingapplication.model.Customer;
//import com.nicholaskoldys.schedulingapplication.model.ScheduleModel;
//import com.nicholaskoldys.schedulingapplication.model.User;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.Month;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author nicho
// */
//public class DataSource2 {
//    
//    private static final String TABLE_COUNTRY = "country";
//    private static final String COUNTRYID_COLUMN = "countryId";
//    private static final String COUNTRY_COLUMN = "country";
//    private static final int COUNTRY_INDEX = 1;
//    
//    private static final String TABLE_CITY = "city";
//    private static final String CITYID_COLUMN = "cityId";
//    private static final String CITY_COLUMN = "city";
//    private static final int CITY_INDEX = 1;
//    
//    private static final String TABLE_ADDRESS = "address";
//    private static final String ADDRESSID_COLUMN = "addressId";
//    private static final String ADDRESS_COLUMN = "address";
//     private static final int ADDRESS_INDEX = 1;
//    private static final String ADDRESS2_COLUMN = "address2";
//    private static final String ADDRESS_POSTALCODE_COLUMN = "postalCode";
//    private static final String ADDRESS_PHONE_COLUMN = "phone";
//    
//    private static final String TABLE_CUSTOMER = "customer";
//    private static final String CUSTOMERID_COLUMN = "customerId";
//    private static final String CUSTOMER_NAME_COLUMN = "customerName";
//    private static final int CUSTOMER_NAME_INDEX = 1;
//    private static final String CUSTOMER_ACTIVE_COLUMN = "active";
//    
//    private static final String TABLE_APPOINTMENT = "appointment";
//    private static final String APPOINTMENTID_COLUMN = "appointmentId";
//    private static final String APPOINTMENT_TITLE_COLUMN = "title";
//    private static final int APPOINTMENT_TITLE_INDEX = 1;
//    private static final String APPOINTMENT_DESCRIPTION_COLUMN = "description";
//    private static final String APPOINTMENT_LOCATION_COLUMN = "location";
//    private static final String APPOINTMENT_CONTACT_COLUMN = "contact";
//    private static final String APPOINTMENT_TYPE_COLUMN = "type";
//    private static final String APPOINTMENT_URL_COLUMN = "url";
//    private static final String APPOINTMENT_STARTTIME_COLUMN = "start";
//    private static final String APPOINTMENT_ENDTIME_COLUMN = "end";
//
//    private static final String TABLE_USER = "user";
//    private static final String USERID_COLUMN = "userId";
//    private static final String USER_NAME_COLUMN = "userName";
//    private static final int USER_NAME_INDEX = 1;
//    private static final String USER_PASSWORD_COLUMN = "password";
//    private static final String USER_ACTIVE_COLUMN = "active";
//    
//    private static final String CREATEDATE_COLUMN = "createDate";
//    private static final String CREATEDBY_COLUMN = "createdBy";
//    private static final String LASTUPDATE_COLUMN = "lastUpdate";
//    private static final String LASTUPDATEBY_COLUMN = "lastUpdateBy";
//    
//    private static final String ADDRESSID_COLUMN_VIEW = "address_Id";
//    private static final String CITYID_COLUMN_VIEW = "city_Id";
//    private static final String COUNTRYID_COLUMN_VIEW = "country_Id";
//
//    private static final String CUSTOMER_TO_ADDRESS_VIEW = "customerToAddressView";
//    
//    /*
//    *  
//    */
//    private static Object selectStatement(
//            String columnName, String tableName, String columnArguement, int arguement) {
//        
//        StringBuilder selectSB = new StringBuilder();
//            selectSB.append("SELECT ");
//            selectSB.append(columnName);
//            selectSB.append(" FROM ");
//            selectSB.append(tableName);
//            selectSB.append(" WHERE ");
//            selectSB.append(columnArguement);
//            selectSB.append(" = ");
//            selectSB.append("?");
//        
//            try(PreparedStatement selectStatement = 
//                    DatabaseConnection.getDatabaseConnection().prepareStatement(selectSB.toString())){
//
//                selectStatement.setInt(1, arguement);
//                                    
//                ResultSet results = selectStatement.executeQuery();
//                results.next();
//                return results.getObject(columnName);
//                
//            } catch (SQLException ex) {
//                Logger.getLogger(DataSource2.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//        return null;
//    }
//    
//    /**************
//    *  CUSTOMER  *
//     * @param customerId
//     * @return 
//    ***************/
//    
//    /*
//    *
//    */
//    public static Customer getCustomer(int customerId) {
//        
//        StringBuilder selectCustomer = new StringBuilder();
//            selectCustomer.append("SELECT ");
//            selectCustomer.append(" * ");
//            selectCustomer.append(" FROM ");
//            selectCustomer.append(TABLE_CUSTOMER);
//            selectCustomer.append(" WHERE ");
//            selectCustomer.append(CUSTOMERID_COLUMN);
//            selectCustomer.append(" = ");
//            selectCustomer.append(customerId);
//            selectCustomer.append(" AND ");
//            selectCustomer.append(CUSTOMER_ACTIVE_COLUMN);
//            selectCustomer.append(" = ");
//            selectCustomer.append(true);
//        
//            try (
//                PreparedStatement selectStatement = DatabaseConnection
//                .getDatabaseConnection().prepareStatement(selectCustomer.toString());
//                ResultSet results = selectStatement.executeQuery()) {
//            
//            Customer customer = null;
//            
//            while(results.next()) {
//                customer = new Customer();
//                customer.setCustomerId(results.getInt(CUSTOMERID_COLUMN));
//                customer.setName(results.getString(CUSTOMER_NAME_COLUMN));
//                customer.setAddressId(results.getInt(ADDRESSID_COLUMN));
//            }
//            
//            return customer;
//            
//        } catch (SQLException ex) {
//            System.out.println("SQL ERROR : getCustomer - from Id" + ex.getMessage());
//        }
//        
//        return null;
//    }
//    
//    /**
//     * 
//     * @param name
//     * @param addressId
//     * @return 
//     */
//    public static int getCustomerId(String name, int addressId) {
//        
//        StringBuilder selectCustomers = new StringBuilder();
//            selectCustomers.append("SELECT ");
//            selectCustomers.append(CUSTOMERID_COLUMN);
//            selectCustomers.append(" FROM ");
//            selectCustomers.append(TABLE_CUSTOMER);
//            selectCustomers.append(" WHERE ");
//            selectCustomers.append(ADDRESSID_COLUMN);
//            selectCustomers.append(" = ");
//            selectCustomers.append(addressId);
//            selectCustomers.append(" AND ");
//            selectCustomers.append(CUSTOMER_NAME_COLUMN);
//            selectCustomers.append(" = ");
//            selectCustomers.append("\"");
//            selectCustomers.append(name);
//            selectCustomers.append("\"");
//            selectCustomers.append(" AND ");
//            selectCustomers.append(CUSTOMER_ACTIVE_COLUMN);
//            selectCustomers.append(" = ");
//            selectCustomers.append(true);
//            
//        try (PreparedStatement selectStatement =
//                DatabaseConnection.getDatabaseConnection().prepareStatement(selectCustomers.toString());
//                ResultSet results = selectStatement.executeQuery()) {
//
//            if (results.next()) {
//                return results.getInt(CUSTOMERID_COLUMN);
//            } else {
//                return -1;
//            }
//
//        } catch (SQLException ex) {
//            System.out.println("SQL ERROR : getCustomerId" +  ex.getMessage());
//        }
//        
//        return -1;
//    }
//    
//    /*
//    *  
//    */
//    public static List<Customer> getAllCustomers() {
//        
//        createCustomerAddressView();
//        
//        List<Customer> customersList;
//                
//        StringBuilder selectCustomers = new StringBuilder();
//            selectCustomers.append("SELECT ");
//            selectCustomers.append("*");
//            selectCustomers.append(" FROM ");
//            selectCustomers.append(CUSTOMER_TO_ADDRESS_VIEW);
//            selectCustomers.append(" WHERE ");
//            selectCustomers.append(CUSTOMER_ACTIVE_COLUMN);
//            selectCustomers.append(" = ");
//            selectCustomers.append(true);
//            
//            try (PreparedStatement selectStatement =
//                    DatabaseConnection.getDatabaseConnection().prepareStatement(selectCustomers.toString());
//                    ResultSet results = selectStatement.executeQuery()) {
//                
//                customersList = new ArrayList<>();
//                
//                while (results.next()) {
//                    
//                    Customer customer = new Customer();
//                    
//                    customer.setCustomerId(results.getInt(CUSTOMERID_COLUMN));
//                    customer.setName(results.getString(CUSTOMER_NAME_COLUMN));
//                    customer.setAddressId(results.getInt(ADDRESSID_COLUMN_VIEW));
//                    customer.setActive();
//                    customer.setAddress(results.getString(ADDRESS_COLUMN));
//                    customer.setAddress2(results.getString(ADDRESS2_COLUMN));
//                    customer.setPostalCode(results.getString(ADDRESS_POSTALCODE_COLUMN));
//                    customer.setPhoneNum(results.getString(ADDRESS_PHONE_COLUMN));
//                    customer.setCity(results.getString(CITY_COLUMN));
//                    customer.setCountry(results.getString(COUNTRY_COLUMN));
//                    
//                    customersList.add(customer);
//                }
//                
//                return customersList;
//                
//            } catch (SQLException ex) {
//                ex.getStackTrace();
//            }
//        
//        return null;
//    }
//    
//    
//    /*
//    *   
//    */
//    public static void addCustomer(String customerName, int addressId) {
//        
//        StringBuilder insertCustomerSB = new StringBuilder();
//            insertCustomerSB.append("INSERT INTO ");
//            insertCustomerSB.append(TABLE_CUSTOMER);
//            insertCustomerSB.append("(");
//            insertCustomerSB.append(CUSTOMER_NAME_COLUMN );
//            insertCustomerSB.append(", ");
//            insertCustomerSB.append(ADDRESSID_COLUMN);
//            insertCustomerSB.append(", ");
//            insertCustomerSB.append(CUSTOMER_ACTIVE_COLUMN);
//            insertCustomerSB.append(", ");
//            insertCustomerSB.append(CREATEDATE_COLUMN);
//            insertCustomerSB.append(", ");
//            insertCustomerSB.append(CREATEDBY_COLUMN);
//            insertCustomerSB.append(", ");
//            insertCustomerSB.append(LASTUPDATE_COLUMN);
//            insertCustomerSB.append(", ");
//            insertCustomerSB.append(LASTUPDATEBY_COLUMN);
//            insertCustomerSB.append(") ");
//            insertCustomerSB.append("VALUES (");
//            insertCustomerSB.append("\"");
//            insertCustomerSB.append(customerName);
//            insertCustomerSB.append("\"");
//            insertCustomerSB.append(", ");
//            insertCustomerSB.append(addressId);
//            insertCustomerSB.append(", ");
//            insertCustomerSB.append(true);
//            insertCustomerSB.append(", ");
//            insertCustomerSB.append("current_timestamp()");
//            insertCustomerSB.append(", ");
//            insertCustomerSB.append("\"");
//            insertCustomerSB.append(ScheduleModel.activeUser.getUserName());
//            insertCustomerSB.append("\"");
//            insertCustomerSB.append(", ");
//            insertCustomerSB.append("current_timestamp()");
//            insertCustomerSB.append(", ");
//            insertCustomerSB.append("\"");
//            insertCustomerSB.append(ScheduleModel.activeUser.getUserName());
//            insertCustomerSB.append("\"");
//            insertCustomerSB.append(")");
//        
//        try (PreparedStatement insertStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(insertCustomerSB.toString())) {
//            
//            insertStatement.executeUpdate();
//            
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//        }
//    }
//
//    
//    /*
//    *  
//    */
//    public static void updateCustomer(String customerName, int addressId, int customerId) {
//        
//        StringBuilder selectCustomerSB = new StringBuilder();
//            selectCustomerSB.append("UPDATE ");
//            selectCustomerSB.append(TABLE_CUSTOMER);
//            selectCustomerSB.append(" SET ");
//            selectCustomerSB.append(CUSTOMER_NAME_COLUMN);
//            selectCustomerSB.append(" = ");
//            selectCustomerSB.append("\"");
//            selectCustomerSB.append(customerName);
//            selectCustomerSB.append("\"");
//            selectCustomerSB.append(", ");
//            selectCustomerSB.append(ADDRESSID_COLUMN);
//            selectCustomerSB.append(" = ");
//            selectCustomerSB.append(addressId);
//            selectCustomerSB.append(", ");
//            selectCustomerSB.append(LASTUPDATE_COLUMN);
//            selectCustomerSB.append(" = ");
//            selectCustomerSB.append("current_timestamp()");
//            selectCustomerSB.append(", ");
//            selectCustomerSB.append(LASTUPDATEBY_COLUMN);
//            selectCustomerSB.append(" = ");
//            selectCustomerSB.append("\"");
//            selectCustomerSB.append(ScheduleModel.activeUser.getUserName());
//            selectCustomerSB.append("\"");
//            selectCustomerSB.append(" WHERE ");
//            selectCustomerSB.append(CUSTOMERID_COLUMN);
//            selectCustomerSB.append(" = ");
//            selectCustomerSB.append(customerId);
//            
//        try (PreparedStatement updateStatement =
//                DatabaseConnection.getDatabaseConnection().prepareStatement(selectCustomerSB.toString())) {
//
//            updateStatement.executeUpdate();
//            
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//        }
//    }
//    
//    
//    /*
//    *  
//    */
//    public static void deleteCustomer(int customerId) {
//        
//        StringBuilder selectCustomerSB = new StringBuilder();
//            selectCustomerSB.append("UPDATE ");
//            selectCustomerSB.append(TABLE_CUSTOMER);
//            selectCustomerSB.append(" SET ");
//            selectCustomerSB.append(CUSTOMER_ACTIVE_COLUMN);
//            selectCustomerSB.append(" = ");
//            selectCustomerSB.append(false);
//            selectCustomerSB.append(", ");
//            selectCustomerSB.append(LASTUPDATE_COLUMN);
//            selectCustomerSB.append(" = ");
//            selectCustomerSB.append("current_timestamp()");
//            selectCustomerSB.append(", ");
//            selectCustomerSB.append(LASTUPDATEBY_COLUMN);
//            selectCustomerSB.append(" = ");
//            selectCustomerSB.append("\"");
//            selectCustomerSB.append(ScheduleModel.activeUser.getUserName());
//            selectCustomerSB.append("\"");
//            selectCustomerSB.append(" WHERE ");
//            selectCustomerSB.append(CUSTOMERID_COLUMN);
//            selectCustomerSB.append(" = ");
//            selectCustomerSB.append(customerId);
//            
//            try (PreparedStatement updateStatement = 
//                    DatabaseConnection.getDatabaseConnection()
//                            .prepareStatement(selectCustomerSB.toString())) {
//            
//            updateStatement.executeUpdate();
//            
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//        }
//    }
//    
//    
//    /**************
//    *   COUNTRY   *
//     * @param addressId
//     * @return 
//    ***************/
//    
//    
//    /*
//    *  
//    */
//    public static String getCountry(int addressId) {
//        
//        int cityId = (Integer) selectStatement(
//                CITYID_COLUMN, TABLE_ADDRESS, ADDRESSID_COLUMN, addressId);
//        int countryId = (Integer) selectStatement(
//                COUNTRYID_COLUMN, TABLE_CITY, CITYID_COLUMN, cityId);
//        
//        return (String) selectStatement(COUNTRY_COLUMN, TABLE_COUNTRY, COUNTRYID_COLUMN, countryId);
//    }
//    
//    
//    /*
//    * Special type method - used to export a list for customerSceneControllers ComboBox.
//    */
//    public static List<String> getCountrys() {
//        
//        List<String> countrysList = new ArrayList<>();
//        
//        try (Statement statement = DatabaseConnection.getDatabaseConnection().createStatement()) {
//            
//            ResultSet results = statement.executeQuery(
//                    "SELECT " + COUNTRY_COLUMN + " FROM " + COUNTRY_COLUMN);
//            
//            while(results.next()) {
//                String countryName = results.getString(COUNTRY_INDEX);
//                countrysList.add(countryName);
//            }
//            
//            return countrysList;
//            
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//            return null;
//        }
//    }
//    
//    
//    /*
//    *   
//    */
//    public static int getCountryId(String country) {
//        
//        int countryId;
//            
//        StringBuilder selectCountrySB = new StringBuilder();
//            selectCountrySB.append("SELECT ");
//            selectCountrySB.append(COUNTRYID_COLUMN);
//            selectCountrySB.append(" FROM ");
//            selectCountrySB.append(TABLE_COUNTRY);
//            selectCountrySB.append(" WHERE ");
//            selectCountrySB.append(COUNTRY_COLUMN);
//            selectCountrySB.append(" = ");
//            selectCountrySB.append("\"");
//            selectCountrySB.append(country);
//            selectCountrySB.append("\"");
//        
//
//        try(PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(selectCountrySB.toString());
//                ResultSet results = selectStatement.executeQuery()) {
//
//            results.next();
//            countryId = results.getInt(COUNTRYID_COLUMN);
//            return countryId;
//            
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//        }
//        return 0;
//    }
//    
//    
//    /************
//    *   CITY    *
//     * @param addressId
//     * @return 
//    *************/
//    
//    
//    /*
//    *  
//    */
//    public static String getCity(int addressId) {
//        
//        int cityId = (Integer) selectStatement(
//                CITYID_COLUMN, TABLE_ADDRESS, ADDRESSID_COLUMN, addressId);
//        
//        return (String) selectStatement(CITY_COLUMN, TABLE_CITY, CITYID_COLUMN, cityId);
//    }
//    
//    
//    /*
//    *   
//    */
//    public static void addCity(String city, String country) {
//        
//        StringBuilder insertCitySB = new StringBuilder();
//            insertCitySB.append("INSERT INTO ");
//            insertCitySB.append(TABLE_CITY);
//            insertCitySB.append("(");
//            insertCitySB.append(CITY_COLUMN);
//            insertCitySB.append(", ");
//            insertCitySB.append(COUNTRYID_COLUMN);
//            insertCitySB.append(", ");
//            insertCitySB.append(CREATEDATE_COLUMN);
//            insertCitySB.append(", ");
//            insertCitySB.append(CREATEDBY_COLUMN);
//            insertCitySB.append(", ");
//            insertCitySB.append(LASTUPDATE_COLUMN);
//            insertCitySB.append(", ");
//            insertCitySB.append(LASTUPDATEBY_COLUMN);
//            insertCitySB.append(") ");
//            insertCitySB.append("VALUES (");
//            insertCitySB.append("\"");
//            insertCitySB.append(city);
//            insertCitySB.append("\"");
//            insertCitySB.append(", ");
//            insertCitySB.append(getCountryId(country));
//            insertCitySB.append(", ");
//            insertCitySB.append("current_timestamp()");
//            insertCitySB.append(", ");
//            insertCitySB.append("\"");
//            insertCitySB.append(ScheduleModel.activeUser.getUserName());
//            insertCitySB.append("\"");
//            insertCitySB.append(", ");
//            insertCitySB.append("current_timestamp()");
//            insertCitySB.append(", ");
//            insertCitySB.append("\"");
//            insertCitySB.append(ScheduleModel.activeUser.getUserName());
//            insertCitySB.append("\"");
//            insertCitySB.append(")");
//        
//        try (PreparedStatement insertStatement =
//                DatabaseConnection.getDatabaseConnection().prepareStatement(insertCitySB.toString())) {
//            
//            insertStatement.executeUpdate();
//            
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//        }
//    }
//    
//    
//    /*
//    *   
//    */
//    public static int getCityId(String city, String country) {
//        
//        int cityId;
//        
//        StringBuilder countCitySB = new StringBuilder();
//            countCitySB.append("SELECT COUNT(");
//            countCitySB.append(CITYID_COLUMN);
//            countCitySB.append(") ");
//            countCitySB.append(" FROM ");
//            countCitySB.append(TABLE_CITY);
//            countCitySB.append(" WHERE ");
//            countCitySB.append(CITY_COLUMN);
//            countCitySB.append(" = ");
//            countCitySB.append("\"");
//            countCitySB.append(city);
//            countCitySB.append("\"");
//            countCitySB.append(" AND ");
//            countCitySB.append(COUNTRYID_COLUMN);
//            countCitySB.append(" = ");
//            countCitySB.append(getCountryId(country));
//            
//        StringBuilder selectCitySB = new StringBuilder();
//            selectCitySB.append("SELECT ");
//            selectCitySB.append(CITYID_COLUMN);
//            selectCitySB.append(" FROM ");
//            selectCitySB.append(TABLE_CITY);
//            selectCitySB.append(" WHERE ");
//            selectCitySB.append(CITY_COLUMN);
//            selectCitySB.append(" = ");
//            selectCitySB.append("\"");
//            selectCitySB.append(city);
//            selectCitySB.append("\"");
//            selectCitySB.append(" AND ");
//            selectCitySB.append(COUNTRYID_COLUMN);
//            selectCitySB.append(" = ");
//            selectCitySB.append(getCountryId(country));
//        
//        try(PreparedStatement countStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(countCitySB.toString());
//                ResultSet results1 = countStatement.executeQuery()) {
//            
//            if (results1.next()) {
//                if (results1.getInt(1) == 0) {
//                    addCity(city, country);
//                }
//                if (results1.getInt(1) > 1) {
//                    System.out.println(
//                            "AN UNEXPECTED ISSUE OCCURED WITH GETCITYID : "
//                                    + city + " and " + country
//                    );
//                    return 0;
//                }
//            } else {
//                System.out.println("ERROR OCCURED WITH GETCITYID");
//                return 0;
//            }
//            
//            try(PreparedStatement selectStatment =
//                    DatabaseConnection.getDatabaseConnection().prepareStatement(selectCitySB.toString());
//                    ResultSet results2 = selectStatment.executeQuery()) {
//                
//                results2.next();
//                cityId = results2.getInt(CITYID_COLUMN);
//            
//                return cityId;
//            }
//            
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//        }
//        
//        return 0;
//    }
//    
//    
//    //ADDRESS
//    
//    
//    /*
//    *   
//    */
//    public static void addAddress(String address, String address2, String postalCode, String phoneNum, String city, String country) {
//        
//        StringBuilder insertAddressSB = new StringBuilder();
//            insertAddressSB.append("INSERT INTO ");
//            insertAddressSB.append(TABLE_ADDRESS);
//            insertAddressSB.append("(");
//            insertAddressSB.append(ADDRESS_COLUMN);
//            insertAddressSB.append(", ");
//            insertAddressSB.append(ADDRESS2_COLUMN);
//            insertAddressSB.append(", ");
//            insertAddressSB.append(CITYID_COLUMN);
//            insertAddressSB.append(", ");
//            insertAddressSB.append(ADDRESS_POSTALCODE_COLUMN);
//            insertAddressSB.append(", ");
//            insertAddressSB.append(ADDRESS_PHONE_COLUMN);
//            insertAddressSB.append(", ");
//            insertAddressSB.append(CREATEDATE_COLUMN);
//            insertAddressSB.append(", ");
//            insertAddressSB.append(CREATEDBY_COLUMN);
//            insertAddressSB.append(", ");
//            insertAddressSB.append(LASTUPDATE_COLUMN);
//            insertAddressSB.append(", ");
//            insertAddressSB.append(LASTUPDATEBY_COLUMN);
//            insertAddressSB.append(") ");
//            insertAddressSB.append("VALUES (");
//            insertAddressSB.append("\"");
//            insertAddressSB.append(address);
//            insertAddressSB.append("\"");
//            insertAddressSB.append(", ");
//            insertAddressSB.append("\"");
//            insertAddressSB.append(address2);
//            insertAddressSB.append("\"");
//            insertAddressSB.append(", ");
//            insertAddressSB.append(getCityId(city, country));
//            insertAddressSB.append(", ");
//            insertAddressSB.append("\"");
//            insertAddressSB.append(postalCode);
//            insertAddressSB.append("\"");
//            insertAddressSB.append(", ");
//            insertAddressSB.append("\"");
//            insertAddressSB.append(phoneNum);
//            insertAddressSB.append("\"");
//            insertAddressSB.append(", ");
//            insertAddressSB.append("current_timestamp()");
//            insertAddressSB.append(", ");
//            insertAddressSB.append("\"");
//            insertAddressSB.append(ScheduleModel.activeUser.getUserName());
//            insertAddressSB.append("\"");
//            insertAddressSB.append(", ");
//            insertAddressSB.append("current_timestamp()");
//            insertAddressSB.append(", ");
//            insertAddressSB.append("\"");
//            insertAddressSB.append(ScheduleModel.activeUser.getUserName());
//            insertAddressSB.append("\"");
//            insertAddressSB.append(")");
//        
//        try (PreparedStatement insertStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(insertAddressSB.toString())) {
//            
//            insertStatement.executeUpdate();
//            
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//        }
//    }
//    
//    
//    /*
//    *   
//    */
//    public static int getAddressId(String address, String address2, String postalCode, String phoneNum, String city, String country) {
//        
//        int addressId;
//        
//        StringBuilder countAddressSB = new StringBuilder();
//            countAddressSB.append("SELECT COUNT(");
//            countAddressSB.append(ADDRESSID_COLUMN);
//            countAddressSB.append(") ");
//            countAddressSB.append(" FROM ");
//            countAddressSB.append(TABLE_ADDRESS);
//            countAddressSB.append(" WHERE ");
//            countAddressSB.append(ADDRESS_COLUMN);
//            countAddressSB.append(" = ");
//            countAddressSB.append("\"");
//            countAddressSB.append(address);
//            countAddressSB.append("\"");
//            countAddressSB.append(" AND ");
//            countAddressSB.append(ADDRESS2_COLUMN);
//            countAddressSB.append(" = ");
//            countAddressSB.append("\"");
//            countAddressSB.append(address2);
//            countAddressSB.append("\"");
//            countAddressSB.append(" AND ");
//            countAddressSB.append(ADDRESS_POSTALCODE_COLUMN);
//            countAddressSB.append(" = ");
//            countAddressSB.append("\"");
//            countAddressSB.append(postalCode);
//            countAddressSB.append("\"");
//            countAddressSB.append(" AND ");
//            countAddressSB.append(ADDRESS_PHONE_COLUMN);
//            countAddressSB.append(" = ");
//            countAddressSB.append("\"");
//            countAddressSB.append(phoneNum);
//            countAddressSB.append("\"");
//            countAddressSB.append(" AND ");
//            countAddressSB.append(CITYID_COLUMN);
//            countAddressSB.append(" = ");
//            countAddressSB.append(getCityId(city, country));
//            
//        StringBuilder selectAddressSB = new StringBuilder();
//            selectAddressSB.append("SELECT ");
//            selectAddressSB.append(ADDRESSID_COLUMN);
//            selectAddressSB.append(" FROM ");
//            selectAddressSB.append(TABLE_ADDRESS);
//            selectAddressSB.append(" WHERE ");
//            selectAddressSB.append(ADDRESS_COLUMN);
//            selectAddressSB.append(" = ");
//            selectAddressSB.append("\"");
//            selectAddressSB.append(address);
//            selectAddressSB.append("\"");
//            selectAddressSB.append(" AND ");
//            selectAddressSB.append(ADDRESS2_COLUMN);
//            selectAddressSB.append(" = ");
//            selectAddressSB.append("\"");
//            selectAddressSB.append(address2);
//            selectAddressSB.append("\"");
//            selectAddressSB.append(" AND ");
//            selectAddressSB.append(ADDRESS_POSTALCODE_COLUMN);
//            selectAddressSB.append(" = ");
//            selectAddressSB.append("\"");
//            selectAddressSB.append(postalCode);
//            selectAddressSB.append("\"");
//            selectAddressSB.append(" AND ");
//            selectAddressSB.append(ADDRESS_PHONE_COLUMN);
//            selectAddressSB.append(" = ");
//            selectAddressSB.append("\"");
//            selectAddressSB.append(phoneNum);
//            selectAddressSB.append("\"");
//            selectAddressSB.append(" AND ");
//            selectAddressSB.append(CITYID_COLUMN);
//            selectAddressSB.append(" = ");
//            selectAddressSB.append(getCityId(city, country));
//        
//        try (PreparedStatement countStatement =
//                DatabaseConnection.getDatabaseConnection().prepareStatement(countAddressSB.toString());
//                ResultSet results1 = countStatement.executeQuery()) {
//            
//            
//            if (results1.next()) {
//                if (results1.getInt(1) == 0) {
//                    addAddress(address, address2, postalCode,
//                            phoneNum, city, country);
//                }
//                if (results1.getInt(1) > 1) {
//                    System.out.println(
//                            "AN UNEXPECTED ISSUE OCCURED WITH GETCITYID : "
//                                    + city);
//                    return 0;
//                }
//            } else {
//                System.out.println("ERROR OCCURED WITH GETADDRESSID");
//                return 0;
//            }
//            
//            try (PreparedStatement selectStatement =
//                    DatabaseConnection.getDatabaseConnection().prepareStatement(selectAddressSB.toString());
//                    ResultSet results2 = selectStatement.executeQuery()) {
//                
//                results2.next();
//                addressId = results2.getInt(ADDRESSID_COLUMN);
//                return addressId;
//            }
//
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//        }
//        
//        return 0;
//    }
//    
//    
//    /*
//    *
//    */
//    public static void getAddressSetup(Customer customer, int addressId) {
//        
//        String selectAddress = 
//                "SELECT address." + ADDRESS_COLUMN 
//                + ", address." + ADDRESS2_COLUMN
//                + ", address." + ADDRESS_POSTALCODE_COLUMN 
//                + ", address." + ADDRESS_PHONE_COLUMN
//                + ", city." + CITY_COLUMN
//                + ", country." + COUNTRY_COLUMN
//                + " FROM " + TABLE_ADDRESS
//                + " INNER JOIN " + TABLE_CITY + " ON " 
//                + " address." + CITYID_COLUMN + " = " + "city."+ CITYID_COLUMN
//                + " INNER JOIN " + TABLE_COUNTRY + " ON " 
//                + " city." + COUNTRYID_COLUMN + " = " + "country."+ COUNTRYID_COLUMN;
//        
//        try (PreparedStatement selectStatement = DatabaseConnection
//                .getDatabaseConnection().prepareStatement(selectAddress);
//                ResultSet results = selectStatement.executeQuery()) {
//            
//            while(results.next()) {
//                
//                customer.setAddress(results.getString(ADDRESS_COLUMN));
//                customer.setAddress2(results.getString(ADDRESS2_COLUMN));
//                customer.setPostalCode(results.getString(ADDRESS_POSTALCODE_COLUMN));
//                customer.setPhoneNum(results.getString(ADDRESS_PHONE_COLUMN));
//                customer.setCity(results.getString(CITY_COLUMN));
//                customer.setCountry(results.getString(COUNTRY_COLUMN));
//            }
//            
//        } catch (SQLException ex) {
//            
//            System.out.println("SQL ERROR : getAddress from addressId join" 
//                    + ex.getMessage());
//        }
//    }
//    
//    
//    
//    /****************
//    *   APPOINTMENTs *
//     * 
//     *  
//     *
//     * @return 
//    *****************/
//    
//    /*
//    *
//    *   
//    */
//    public static List<String> getAppointmentTypes() {
//        
//        List<String> typesList = new ArrayList();
//        
//        StringBuilder selectType = new StringBuilder();
//            selectType.append("SELECT ");
//            selectType.append(" DISTINCT ");
//            selectType.append(APPOINTMENT_TYPE_COLUMN);
//            selectType.append(" FROM ");
//            selectType.append(TABLE_APPOINTMENT);
//            
//        try (PreparedStatement selectStatement =
//                DatabaseConnection.getDatabaseConnection().prepareStatement(selectType.toString());
//                ResultSet results = selectStatement.executeQuery()) {
//            
//            while(results.next()) {
//                typesList.add(results.getString(APPOINTMENT_TYPE_COLUMN));
//            }
//            return typesList;
//            
//        } catch (SQLException ex) {
//            System.out.println("GET APPOINTMENT TYPES ERROR : " + ex.getMessage());
//            ex.getStackTrace();
//        }
//        return null;
//    }
//    
//    
//    /*
//    
//    */
//    public static List<Integer> getAllAppointmentIdsForDate(int userId, LocalDate parameterDate) {
//        
//        List<Integer> appointmentIdsList = new ArrayList();
//        
//        StringBuilder selectAppointmentsSB = new StringBuilder();
//            selectAppointmentsSB.append("SELECT ");
//            selectAppointmentsSB.append(APPOINTMENTID_COLUMN);
//            selectAppointmentsSB.append(" FROM ");
//            selectAppointmentsSB.append(TABLE_APPOINTMENT);
//            selectAppointmentsSB.append(" WHERE ");
//            selectAppointmentsSB.append(USERID_COLUMN);
//            selectAppointmentsSB.append(" = ");
//            selectAppointmentsSB.append(userId);
//            selectAppointmentsSB.append(" AND ");
//            selectAppointmentsSB.append(APPOINTMENT_STARTTIME_COLUMN);
//            selectAppointmentsSB.append(" >= ");
//            selectAppointmentsSB.append("'");
//            selectAppointmentsSB.append(parameterDate);
//            selectAppointmentsSB.append("'");
//            selectAppointmentsSB.append(" AND ");
//            selectAppointmentsSB.append(APPOINTMENT_STARTTIME_COLUMN);
//            selectAppointmentsSB.append(" < ");
//            selectAppointmentsSB.append("'");
//            selectAppointmentsSB.append(parameterDate.plusDays(1));
//            selectAppointmentsSB.append("'");
//        
//            
//        try (PreparedStatement selectStatement 
//                = DatabaseConnection.getDatabaseConnection().prepareStatement(selectAppointmentsSB.toString());
//                ResultSet results = selectStatement.executeQuery()) {
//            
//            while(results.next()) {
//                appointmentIdsList.add(results.getInt(APPOINTMENTID_COLUMN));
//            }
//            
//            return appointmentIdsList;
//            
//        } catch (SQLException ex) {
//            System.out.println("SQL QUERY FAILURE : getAllAppointmentMatch : " + ex.getMessage());
//            ex.getStackTrace();
//        }
//        
//        return null;
//    }
//    
//    /*
//    *  
//    */
//    public static List<Appointment> getAllAppointmentsForUser(int userId) {
//                
//        List<Appointment> appointmentsList = new ArrayList<>();
//        
//        StringBuilder selectAppointmentsSB = new StringBuilder();
//            selectAppointmentsSB.append("SELECT ");
//            selectAppointmentsSB.append("*");
//            selectAppointmentsSB.append(" FROM ");
//            selectAppointmentsSB.append(TABLE_APPOINTMENT);
//            selectAppointmentsSB.append(" WHERE ");
//            selectAppointmentsSB.append(USERID_COLUMN);
//            selectAppointmentsSB.append(" = ");
//            selectAppointmentsSB.append(userId);
//            
//        try(PreparedStatement selectStatement =
//                DatabaseConnection.getDatabaseConnection().prepareStatement(selectAppointmentsSB.toString());
//                ResultSet results = selectStatement.executeQuery()) {
//
//            while(results.next()){
//                
//                Appointment appointment = new Appointment();
//                
//                appointment.setAppointmentId(results.getInt(APPOINTMENTID_COLUMN));
//                appointment.setTitle(results.getString(APPOINTMENT_TITLE_COLUMN));
//                appointment.setType(results.getString(APPOINTMENT_TYPE_COLUMN));
//                appointment.setCustomerId(results.getInt(CUSTOMERID_COLUMN));
//                appointment.setContact(results.getString(APPOINTMENT_CONTACT_COLUMN));
//                appointment.setLocation(results.getString(APPOINTMENT_LOCATION_COLUMN));
//                appointment.setCustomerRecordUrl(results.getString(APPOINTMENT_URL_COLUMN));
//                appointment.setStartTime(
//                        results.getTimestamp(APPOINTMENT_STARTTIME_COLUMN).toLocalDateTime());
//                appointment.setEndTime(
//                        results.getTimestamp(APPOINTMENT_ENDTIME_COLUMN).toLocalDateTime());
//                appointment.setUserId(results.getInt(USERID_COLUMN));
//                
//                appointment.setCustomer(results.getInt(CUSTOMERID_COLUMN));
//                
//                appointmentsList.add(appointment);
//            }
//            
//            return appointmentsList;
//
//                
//        } catch (SQLException ex) {
//            System.out.println("ERROR : with getAllCustomersMethod " + ex.getMessage());
//            ex.getStackTrace();
//        }
//        
//        return null;
//    }
//    
//    /*
//    *   
//    */
//    public static void addAppointment(int userId, String title, String description, String location, String contact, String type, String customerRecordUrl, LocalDateTime startTime, LocalDateTime endTime, int customerId) {
//        
//        StringBuilder insertAppointment = new StringBuilder();
//            insertAppointment.append("INSERT INTO ");
//            insertAppointment.append(TABLE_APPOINTMENT);
//            insertAppointment.append("(");
//            insertAppointment.append(CUSTOMERID_COLUMN );
//            insertAppointment.append(", ");
//            insertAppointment.append(USERID_COLUMN);
//            insertAppointment.append(", ");
//            insertAppointment.append(APPOINTMENT_TITLE_COLUMN);
//            insertAppointment.append(", ");
//            insertAppointment.append(APPOINTMENT_DESCRIPTION_COLUMN);
//            insertAppointment.append(", ");
//            insertAppointment.append(APPOINTMENT_LOCATION_COLUMN);
//            insertAppointment.append(", ");
//            insertAppointment.append(APPOINTMENT_CONTACT_COLUMN);
//            insertAppointment.append(", ");
//            insertAppointment.append(APPOINTMENT_TYPE_COLUMN);
//            insertAppointment.append(", ");
//            insertAppointment.append(APPOINTMENT_URL_COLUMN);
//            insertAppointment.append(", ");
//            insertAppointment.append(APPOINTMENT_STARTTIME_COLUMN);
//            insertAppointment.append(", ");
//            insertAppointment.append(APPOINTMENT_ENDTIME_COLUMN);
//            insertAppointment.append(", ");
//            insertAppointment.append(CREATEDATE_COLUMN);
//            insertAppointment.append(", ");
//            insertAppointment.append(CREATEDBY_COLUMN);
//            insertAppointment.append(", ");
//            insertAppointment.append(LASTUPDATE_COLUMN);
//            insertAppointment.append(", ");
//            insertAppointment.append(LASTUPDATEBY_COLUMN);
//            insertAppointment.append(") ");
//            insertAppointment.append("VALUES (");
//            insertAppointment.append(customerId);
//            insertAppointment.append(", ");
//            insertAppointment.append(userId);
//            insertAppointment.append(", ");
//            insertAppointment.append("\"");
//            insertAppointment.append(title);
//            insertAppointment.append("\"");
//            insertAppointment.append(", ");
//            insertAppointment.append("\"");
//            insertAppointment.append(description);
//            insertAppointment.append("\"");
//            insertAppointment.append(", ");
//            insertAppointment.append("\"");
//            insertAppointment.append(location);
//            insertAppointment.append("\"");
//            insertAppointment.append(", ");
//            insertAppointment.append("\"");
//            insertAppointment.append(contact);
//            insertAppointment.append("\"");
//            insertAppointment.append(", ");
//            insertAppointment.append("\"");
//            insertAppointment.append(type);
//            insertAppointment.append("\"");
//            insertAppointment.append(", ");
//            insertAppointment.append("\"");
//            insertAppointment.append(customerRecordUrl);
//            insertAppointment.append("\"");
//            insertAppointment.append(", ");
//            insertAppointment.append("\'");
//            insertAppointment.append(ConvertTimeService.convertToDatabse(startTime));
//            insertAppointment.append("\'");
//            insertAppointment.append(", ");
//            insertAppointment.append("\'");
//            insertAppointment.append(ConvertTimeService.convertToDatabse(endTime));
//            insertAppointment.append("\'");
//            insertAppointment.append(", ");
//            insertAppointment.append("current_timestamp()");
//            insertAppointment.append(", ");
//            insertAppointment.append("\"");
//            insertAppointment.append(ScheduleModel.activeUser.getUserName());
//            insertAppointment.append("\"");
//            insertAppointment.append(", ");
//            insertAppointment.append("current_timestamp()");
//            insertAppointment.append(", ");
//            insertAppointment.append("\"");
//            insertAppointment.append(ScheduleModel.activeUser.getUserName());
//            insertAppointment.append("\"");
//            insertAppointment.append(")");
//                    
//        try (PreparedStatement insertStatement =
//                DatabaseConnection.getDatabaseConnection().prepareStatement(insertAppointment.toString())) {
//            
//            insertStatement.executeUpdate();
//            
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//        }
//    }
//    
//    /*
//    *  
//    */
//    public static int getAppointmentId(int customerId, int userId, String title, String type, LocalDateTime startTime) {
//        
//        StringBuilder selectAppointmentSB = new StringBuilder();
//            selectAppointmentSB.append("SELECT ");
//            selectAppointmentSB.append(APPOINTMENTID_COLUMN);
//            selectAppointmentSB.append(" FROM ");
//            selectAppointmentSB.append(TABLE_APPOINTMENT);
//            selectAppointmentSB.append(" WHERE ");
//            selectAppointmentSB.append(CUSTOMERID_COLUMN);
//            selectAppointmentSB.append(" = ");
//            selectAppointmentSB.append(customerId);
//            selectAppointmentSB.append(" AND ");
//            selectAppointmentSB.append(USERID_COLUMN);
//            selectAppointmentSB.append(" = ");
//            selectAppointmentSB.append(userId);
//            selectAppointmentSB.append(" AND ");
//            selectAppointmentSB.append(APPOINTMENT_TITLE_COLUMN);
//            selectAppointmentSB.append(" = ");
//            selectAppointmentSB.append("\"");
//            selectAppointmentSB.append(title);
//            selectAppointmentSB.append("\"");
//            selectAppointmentSB.append(" AND ");
//            selectAppointmentSB.append(APPOINTMENT_TYPE_COLUMN);
//            selectAppointmentSB.append(" = ");
//            selectAppointmentSB.append("\"");
//            selectAppointmentSB.append(type);
//            selectAppointmentSB.append("\"");
//            selectAppointmentSB.append(" AND ");
//            selectAppointmentSB.append(APPOINTMENT_STARTTIME_COLUMN);
//            selectAppointmentSB.append(" = ");
//            selectAppointmentSB.append("\'");
//            selectAppointmentSB.append(ConvertTimeService.convertToDatabse(startTime));
//            selectAppointmentSB.append("\'");
//            
//        try (PreparedStatement selectStatement =
//                DatabaseConnection.getDatabaseConnection().prepareStatement(selectAppointmentSB.toString());
//                ResultSet results2 = selectStatement.executeQuery()) {
//
//            if (results2.next()) {
//                
//                return results2.getInt(APPOINTMENTID_COLUMN);
//            } else {
//                
//                return 0;
//            }
//
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//        }
//        
//        return -1;
//    }
//    
//    
//    /*
//    *  
//    */
//    public static void updateAppointment(int appointmentId, int customerId, int userId, String title, String description, String location, String contact, String type, String customerRecordUrl, LocalDateTime startTime, LocalDateTime endTime) {
//        
//        StringBuilder updateAppointment = new StringBuilder();
//            updateAppointment.append("UPDATE ");
//            updateAppointment.append(TABLE_APPOINTMENT);
//            updateAppointment.append(" SET ");
//            updateAppointment.append(CUSTOMERID_COLUMN);
//            updateAppointment.append(" = ");
//            updateAppointment.append(customerId);
//            updateAppointment.append(", ");
//            updateAppointment.append(USERID_COLUMN);
//            updateAppointment.append(" = ");
//            updateAppointment.append(userId);
//            updateAppointment.append(", ");
//            updateAppointment.append(APPOINTMENT_TITLE_COLUMN);
//            updateAppointment.append(" = ");
//            updateAppointment.append("\"");
//            updateAppointment.append(title);
//            updateAppointment.append("\"");
//            updateAppointment.append(", ");
//            updateAppointment.append(APPOINTMENT_DESCRIPTION_COLUMN);
//            updateAppointment.append(" = ");
//            updateAppointment.append("\"");
//            updateAppointment.append(description);
//            updateAppointment.append("\"");
//            updateAppointment.append(", ");
//            updateAppointment.append(APPOINTMENT_LOCATION_COLUMN);
//            updateAppointment.append(" = ");
//            updateAppointment.append("\"");
//            updateAppointment.append(location);
//            updateAppointment.append("\"");
//            updateAppointment.append(", ");
//            updateAppointment.append(APPOINTMENT_CONTACT_COLUMN);
//            updateAppointment.append(" = ");
//            updateAppointment.append("\"");
//            updateAppointment.append(contact);
//            updateAppointment.append("\"");
//            updateAppointment.append(", ");
//            updateAppointment.append(APPOINTMENT_TYPE_COLUMN);
//            updateAppointment.append(" = ");
//            updateAppointment.append("\"");
//            updateAppointment.append(type);
//            updateAppointment.append("\"");
//            updateAppointment.append(", ");
//            updateAppointment.append(APPOINTMENT_URL_COLUMN);
//            updateAppointment.append(" = ");
//            updateAppointment.append("\"");
//            updateAppointment.append(customerRecordUrl);
//            updateAppointment.append("\"");
//            updateAppointment.append(", ");
//            updateAppointment.append(APPOINTMENT_STARTTIME_COLUMN);
//            updateAppointment.append(" = ");
//            updateAppointment.append("\'");
//            updateAppointment.append(ConvertTimeService.convertToDatabse(startTime));
//            updateAppointment.append("\'");
//            updateAppointment.append(", ");
//            updateAppointment.append(APPOINTMENT_ENDTIME_COLUMN);
//            updateAppointment.append(" = ");
//            updateAppointment.append("\'");
//            updateAppointment.append(ConvertTimeService.convertToDatabse(endTime));
//            updateAppointment.append("\'");
//            updateAppointment.append(", ");
//            updateAppointment.append(LASTUPDATE_COLUMN);
//            updateAppointment.append(" = ");
//            updateAppointment.append("current_timestamp()");
//            updateAppointment.append(", ");
//            updateAppointment.append(LASTUPDATEBY_COLUMN);
//            updateAppointment.append(" = ");
//            updateAppointment.append("\"");
//            updateAppointment.append(ScheduleModel.activeUser.getUserName());
//            updateAppointment.append("\"");
//            updateAppointment.append(" WHERE ");
//            updateAppointment.append(APPOINTMENTID_COLUMN);
//            updateAppointment.append(" = ");
//            updateAppointment.append(appointmentId);
//            
//        try (PreparedStatement updateStatement =
//                DatabaseConnection.getDatabaseConnection().prepareStatement(updateAppointment.toString())) {
//
//            updateStatement.executeUpdate();
//            
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//        }
//    }
//    
//    /*
//    *  
//    */
//    public static void deleteAppointment(int appointmentId) {
//        
//        StringBuilder selectAppointmentSB = new StringBuilder();
//            selectAppointmentSB.append("DELETE ");
//            selectAppointmentSB.append(" FROM ");
//            selectAppointmentSB.append(TABLE_APPOINTMENT);
//            selectAppointmentSB.append(" WHERE ");
//            selectAppointmentSB.append(APPOINTMENTID_COLUMN);
//            selectAppointmentSB.append(" = ");
//            selectAppointmentSB.append(appointmentId);
//            
//        try (PreparedStatement updateStatement = 
//                DatabaseConnection.getDatabaseConnection()
//                        .prepareStatement(selectAppointmentSB.toString())) {
//
//            updateStatement.executeUpdate();
//            
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//        }
//    }
//    
//    //*USER
//    
//    /*
//    *  @param userId
//    */
//    public static String getUserPassword(int userId) {
//        
//        return (String) selectStatement(
//                USER_PASSWORD_COLUMN, TABLE_USER, USERID_COLUMN, userId);
//    }
//    
//    
//    public static String getUserName(int userId) {
//        
//        return (String) selectStatement(
//                USER_NAME_COLUMN, TABLE_USER, USERID_COLUMN, userId);
//    }
//    
//    /*
//    *  
//    */
//    public static Boolean isUserActive(int userId) {
//        
//        Integer bool = (Integer) selectStatement(
//                USER_ACTIVE_COLUMN, TABLE_USER, USERID_COLUMN, userId);
//        return bool.equals(1);
//    }
//    
//    /*
//    *  
//    */
//    public static int getUserId(String userName) {
//        
//        int userId;
//        
//        StringBuilder selectUserSB = new StringBuilder();
//            selectUserSB.append("SELECT ");
//            selectUserSB.append(USERID_COLUMN);
//            selectUserSB.append(" FROM ");
//            selectUserSB.append(TABLE_USER);
//            selectUserSB.append(" WHERE ");
//            selectUserSB.append(USER_NAME_COLUMN);
//            selectUserSB.append(" = ");
//            selectUserSB.append("\"");
//            selectUserSB.append(userName);
//            selectUserSB.append("\"");
//        
//        try (PreparedStatement selectStatement = 
//                DatabaseConnection.getDatabaseConnection().prepareStatement(selectUserSB.toString());
//                ResultSet results1 = selectStatement.executeQuery()) {
//            
//            results1.next();
//            userId = results1.getInt(USERID_COLUMN);
//            return userId;
//            
//        } catch (SQLException ex) {
//            System.out.println("Query Failure: " + ex.getMessage());
//        }
//        return 0;
//    }
//    
//    public static List<User> getAllUsers() {
//        
//        List<User> usersList;
//        
//        StringBuilder selectUsers = new StringBuilder();
//            selectUsers.append("SELECT ");
//            selectUsers.append(" * ");
//            selectUsers.append(" FROM ");
//            selectUsers.append(TABLE_USER);
//            selectUsers.append(" WHERE ");
//            selectUsers.append(USER_ACTIVE_COLUMN);
//            selectUsers.append(" = ");
//            selectUsers.append(true);
//            
//        try (PreparedStatement selectStatement = 
//                DatabaseConnection
//                        .getDatabaseConnection().prepareStatement(selectUsers.toString());
//                ResultSet results = selectStatement.executeQuery()) {
//            
//            usersList = new ArrayList();
//            
//            while(results.next()) {
//                
//                User user = new User();
//                
//                user.setActive(results.getBoolean(USER_ACTIVE_COLUMN));
//                user.setUserId(results.getInt(USERID_COLUMN));
//                user.setUserName(results.getString(USER_NAME_COLUMN));
//                
//                usersList.add(user);
//            }
//            
//            return usersList;
//            
//        } catch (SQLException ex) {
//            System.out.println("QUERY FAILURE : getAllUsers " + ex.getMessage());
//        }
//        
//        return null;
//    }
//    
//    //REPORTS
//    
//    /*
//    *   @returnGet All Appointments in the database
//    */
//    public static List<Appointment> allDatabaseAppointments() {
//        
//        List<Appointment> allAppointmentsList = new ArrayList<>();
//        
//        StringBuilder allAppointments = new StringBuilder();
//            allAppointments.append("SELECT ");
//            allAppointments.append("*");
//            allAppointments.append(" FROM ");
//            allAppointments.append(TABLE_APPOINTMENT);
//            
//        try (PreparedStatement selectStatement 
//                = DatabaseConnection.getDatabaseConnection().prepareStatement(allAppointments.toString());
//                ResultSet results = selectStatement.executeQuery()) {
//            
//            while(results.next()) {
//                
//                Appointment appointment = new Appointment();
//                
//                appointment.setAppointmentId(results.getInt(APPOINTMENTID_COLUMN));
//                appointment.setTitle(results.getString(APPOINTMENT_TITLE_COLUMN));
//                appointment.setType(results.getString(APPOINTMENT_TYPE_COLUMN));
//                appointment.setCustomerId(results.getInt(CUSTOMERID_COLUMN));
//                appointment.setContact(results.getString(APPOINTMENT_CONTACT_COLUMN));
//                appointment.setLocation(results.getString(APPOINTMENT_LOCATION_COLUMN));
//                appointment.setCustomerRecordUrl(results.getString(APPOINTMENT_URL_COLUMN));
//                appointment.setStartTime(
//                        results.getTimestamp(APPOINTMENT_STARTTIME_COLUMN).toLocalDateTime());
//                appointment.setEndTime(
//                        results.getTimestamp(APPOINTMENT_ENDTIME_COLUMN).toLocalDateTime());
//                appointment.setUserId(results.getInt(USERID_COLUMN));
//                
//                appointment.setCustomer(results.getInt(CUSTOMERID_COLUMN));
//                
//                allAppointmentsList.add(appointment);
//            }
//            
//            return allAppointmentsList;
//            
//        } catch (SQLException ex) {
//            System.out.println("REPORT ERROR : getAllAppointments " + ex.getMessage());
//            ex.getStackTrace();
//        }
//        
//        return null;
//    }
//    
//    
//    /*
//    
//    */
//    public static List<Report> allDatabaseAppointmentsCountPerCustomer(LocalDate issuedDate) {
//        
//        int year = issuedDate.getYear();
//        
//        List<Report> countList;
//        
//        String yearCount = "select "
//                + "customer.customerName as Customer,"
//                + " count(*) AS Count,"
//                + " year(start) as Year\n" 
//                + "from appointment\n" 
//                + "inner join customer ON customer.customerId = appointment.customerId\n" 
//                + "group by Year, Customer\n" 
//                + "having Year = " + year;
//            
//        try (PreparedStatement countStatement = DatabaseConnection
//                .getDatabaseConnection().prepareStatement(yearCount);
//                ResultSet results = countStatement.executeQuery()) {
//            
//            countList = new ArrayList();
//            
//            while(results.next()) {
//                
//                Report count = new Report();
//                
//                count.setCustomer(results.getString("Customer"));
//                count.setCount(results.getInt("Count"));
//                count.setYear(year);
//                
//                countList.add(count);
//            }
//            
//            return countList;
//            
//        } catch (SQLException ex) {
//            System.out.println(
//            "REPORT ERROR : allDatabaseAppointmentsCountPerCustomer " + ex.getMessage());
//            ex.getStackTrace();
//        }
//        
//        return null;
//    }
//    
//    
//    /*
//    
//    */
//    public static List<Appointment> allDatabaseAppointmentsForDate(LocalDate date) {
//        
//        List<Appointment> allAppointmentsList = new ArrayList<>();
//        LocalDateTime dateTime = date.atStartOfDay();
//        LocalDateTime dateTimePlus = date.plusDays(1).atStartOfDay();
//        
//        StringBuilder allAppointments = new StringBuilder();
//            allAppointments.append("SELECT ");
//            allAppointments.append("*");
//            allAppointments.append(" FROM ");
//            allAppointments.append(TABLE_APPOINTMENT);
//            allAppointments.append(" WHERE ");
//            allAppointments.append(APPOINTMENT_STARTTIME_COLUMN);
//            allAppointments.append(" >= ");
//            allAppointments.append("\'");
//            allAppointments.append(Timestamp.valueOf(dateTime));
//            allAppointments.append("\'");
//            allAppointments.append(" AND ");
//            allAppointments.append(APPOINTMENT_STARTTIME_COLUMN);
//            allAppointments.append(" < ");
//            allAppointments.append("\'");
//            allAppointments.append(Timestamp.valueOf(dateTimePlus));
//            allAppointments.append("\'");
//            
//            
//        try (PreparedStatement selectStatement 
//                = DatabaseConnection.getDatabaseConnection().prepareStatement(allAppointments.toString());
//                ResultSet results = selectStatement.executeQuery()) {
//            
//            while(results.next()) {
//                
//                Appointment appointment = new Appointment();
//                
//                appointment.setAppointmentId(results.getInt(APPOINTMENTID_COLUMN));
//                appointment.setTitle(results.getString(APPOINTMENT_TITLE_COLUMN));
//                appointment.setType(results.getString(APPOINTMENT_TYPE_COLUMN));
//                appointment.setCustomerId(results.getInt(CUSTOMERID_COLUMN));
//                appointment.setContact(results.getString(APPOINTMENT_CONTACT_COLUMN));
//                appointment.setLocation(results.getString(APPOINTMENT_LOCATION_COLUMN));
//                appointment.setCustomerRecordUrl(results.getString(APPOINTMENT_URL_COLUMN));
//                appointment.setStartTime(
//                        results.getTimestamp(APPOINTMENT_STARTTIME_COLUMN).toLocalDateTime());
//                appointment.setEndTime(
//                        results.getTimestamp(APPOINTMENT_ENDTIME_COLUMN).toLocalDateTime());
//                appointment.setUserId(results.getInt(USERID_COLUMN));
//                
//                appointment.setCustomer(results.getInt(CUSTOMERID_COLUMN));
//                
//                allAppointmentsList.add(appointment);
//            }
//            
//            return allAppointmentsList;
//            
//        } catch (SQLException ex) {
//            System.out.println("REPORT ERROR : getAllAppointmentsByMonth " + ex.getMessage());
//            ex.getStackTrace();
//        }
//        
//        return null;
//    }
//    
//    
//    /*
//    *   @returnGet All Appointments in the database
//    */
//    public static List<Appointment> allDatabaseAppointmentsByMonth(Month month) {
//        
//        List<Appointment> allAppointmentsList = new ArrayList<>();
//        
//        LocalDateTime monthDatePlus, monthDate = LocalDate.of(
//                LocalDate.now().getYear(), month, 1).atStartOfDay();
//        if (month == Month.DECEMBER) {
//            monthDatePlus = LocalDate.of(
//                LocalDate.now().plusYears(1).getYear(), month.plus(1), 1).atStartOfDay();
//        } else {
//            monthDatePlus = LocalDate.of(
//                LocalDate.now().getYear(), month.plus(1), 1).atStartOfDay();
//        }
//        
//        StringBuilder allAppointments = new StringBuilder();
//            allAppointments.append("SELECT ");
//            allAppointments.append("*");
//            allAppointments.append(" FROM ");
//            allAppointments.append(TABLE_APPOINTMENT);
//            allAppointments.append(" WHERE ");
//            allAppointments.append(APPOINTMENT_STARTTIME_COLUMN);
//            allAppointments.append(" >= ");
//            allAppointments.append("\'");
//            allAppointments.append(Timestamp.valueOf(monthDate));
//            allAppointments.append("\'");
//            allAppointments.append(" AND ");
//            allAppointments.append(APPOINTMENT_STARTTIME_COLUMN);
//            allAppointments.append(" < ");
//            allAppointments.append("\'");
//            allAppointments.append(Timestamp.valueOf(monthDatePlus));
//            allAppointments.append("\'");
//            
//            
//        try (PreparedStatement selectStatement 
//                = DatabaseConnection.getDatabaseConnection().prepareStatement(allAppointments.toString());
//                ResultSet results = selectStatement.executeQuery()) {
//            
//            while(results.next()) {
//                
//                Appointment appointment = new Appointment();
//                
//                appointment.setAppointmentId(results.getInt(APPOINTMENTID_COLUMN));
//                appointment.setTitle(results.getString(APPOINTMENT_TITLE_COLUMN));
//                appointment.setType(results.getString(APPOINTMENT_TYPE_COLUMN));
//                appointment.setCustomerId(results.getInt(CUSTOMERID_COLUMN));
//                appointment.setContact(results.getString(APPOINTMENT_CONTACT_COLUMN));
//                appointment.setLocation(results.getString(APPOINTMENT_LOCATION_COLUMN));
//                appointment.setCustomerRecordUrl(results.getString(APPOINTMENT_URL_COLUMN));
//                appointment.setStartTime(
//                        results.getTimestamp(APPOINTMENT_STARTTIME_COLUMN).toLocalDateTime());
//                appointment.setEndTime(
//                        results.getTimestamp(APPOINTMENT_ENDTIME_COLUMN).toLocalDateTime());
//                appointment.setUserId(results.getInt(USERID_COLUMN));
//                
//                appointment.setCustomer(results.getInt(CUSTOMERID_COLUMN));
//                
//                allAppointmentsList.add(appointment);
//            }
//            
//            return allAppointmentsList;
//            
//        } catch (SQLException ex) {
//            System.out.println("REPORT ERROR : getAllAppointmentsByMonth " + ex.getMessage());
//            ex.getStackTrace();
//        }
//        
//        return null;
//    }
//   
//    
//    /*
//    *   Get All Customers in the database
//    */
//    public static List<Customer> allDatabaseCustomers() {
//        
//        List<Customer> allCustomersList = new ArrayList<>();
//        
//        StringBuilder allCustomers = new StringBuilder();
//            allCustomers.append("SELECT ");
//            allCustomers.append("*");
//            allCustomers.append(" FROM ");
//            allCustomers.append(TABLE_CUSTOMER);
//            
//        try (PreparedStatement selectStatement 
//                = DatabaseConnection.getDatabaseConnection().prepareStatement(allCustomers.toString());
//                ResultSet results = selectStatement.executeQuery()) {
//            
//            while(results.next()) {
//                
//                Customer customer = new Customer();
//                
//                customer.setCustomerId(results.getInt(CUSTOMERID_COLUMN));
//                customer.setName(results.getString(CUSTOMER_NAME_COLUMN));
//                customer.setAddressId(results.getInt(ADDRESSID_COLUMN));
//                
//                allCustomersList.add(customer);
//            }
//            
//            return allCustomersList;
//            
//        } catch (SQLException ex) {
//            System.out.println("REPORT ERROR : getAllCustomers " + ex.getMessage());
//            ex.getStackTrace();
//        }
//        
//        return null;
//    }
//    
//    
//    /*
//    *
//    */
//    public static List<Report> countAllUserAppointmentsPerMonth() {
//        
//        List<Report> list;
//        
//        String countQuery = "select \n"
//                    + "	user.userName AS Consultant,\n"
//                    + " count(appointment.appointmentId) AS Count,\n"
//                    + " month(appointment.start) AS Month\n"
//                    + "from appointment\n"
//                    + "inner join user ON user.userId = appointment.userId\n"
//                    + "group by user.userName, Month";
//        
//        try (PreparedStatement countStatement = DatabaseConnection.getDatabaseConnection()
//                .prepareStatement(countQuery);
//                ResultSet results = countStatement.executeQuery()) {
//            
//            list = new ArrayList();
//            
//            while(results.next()) {
//                
//                Report count = new Report();
//                
//                count.setMonth(results.getInt("Month"));
//                count.setUser(results.getString("Consultant"));
//                count.setCount(results.getInt("Count"));
//                
//                list.add(count);
//            }
//            
//            return list;
//            
//        } catch (SQLException ex) {
//            System.out.println("SQL QUERY FAILURE : countALLTypes appointment " + ex.getMessage());
//        }
//        
//        return null;
//    }
//    
//    
//    /*
//    *
//    */
//    public static List<Report> countAllTypesPerMonthOfAppointments() {
//        
//        List<Report> list;
//        
//        String countQuery = 
//                "select type as Type, count(*) AS Count, month(start) as Month\n"
//               + "from appointment\n"
//               + "group by month(start), type";
//        
//        try (PreparedStatement countStatement = DatabaseConnection.getDatabaseConnection()
//                .prepareStatement(countQuery);
//                ResultSet results = countStatement.executeQuery()) {
//            
//            list = new ArrayList();
//            
//            while(results.next()) {
//                
//                Report count = new Report();
//                
//                count.setMonth(results.getInt("Month"));
//                count.setType(results.getString("Type"));
//                count.setCount(results.getInt("Count"));
//                
//                list.add(count);
//            }
//            
//            return list;
//            
//        } catch (SQLException ex) {
//            System.out.println("SQL QUERY FAILURE : countALLTypes appointment " + ex.getMessage());
//        }
//        
//        return null;
//    }
//    
//    /*
//    *   CREATE OR REPLACE VIEW  customerToAddressView
//    */
//    public static void createCustomerAddressView() {
//                
//        String createView =
//            "CREATE OR REPLACE VIEW " + CUSTOMER_TO_ADDRESS_VIEW
//            + " AS " + "SELECT "
//            + "customer." + CUSTOMERID_COLUMN +", customer." + CUSTOMER_ACTIVE_COLUMN 
//            + ", customer." + CUSTOMER_NAME_COLUMN
//            + ", address." + ADDRESSID_COLUMN + " AS address_Id, address." + ADDRESS_COLUMN
//            + ", address." + ADDRESS2_COLUMN + ", address." + ADDRESS_POSTALCODE_COLUMN
//            + ", address." + ADDRESS_PHONE_COLUMN 
//            + ", city." + CITYID_COLUMN + " AS city_Id, city." + CITY_COLUMN 
//            + ", country." + COUNTRYID_COLUMN + " AS country_Id, country." + COUNTRY_COLUMN
//            + " FROM customer "
//            + " INNER JOIN address ON customer." + ADDRESSID_COLUMN + " = address." + ADDRESSID_COLUMN
//            + " INNER JOIN city ON address." + CITYID_COLUMN + " = city." + CITYID_COLUMN
//            + " INNER JOIN country ON city." + COUNTRYID_COLUMN + " = country." + COUNTRYID_COLUMN;
//                
//        try (PreparedStatement createViewStatement =
//                DatabaseConnection.getDatabaseConnection().prepareStatement(createView)) {
//            
//            createViewStatement.executeUpdate();
//            
//        } catch (SQLException ex) {
//            System.out.println("FAILED TO CREATE VIEW : " + ex.getMessage());
//        }
//    } 
//}
//
//
//
///*  OLD ADD COUNTRYS FROM LIST METHOD - WAS WAY TOO MANY TO USE
//
//    public static void addAllCountrys() {
//        
//        
//        File file = new File("src/com/nicholaskoldys/"
//                + "schedulingapplication/resources/countryList.txt");
//        
//        String country;
//        String updater = "Nicholas Koldys";
//        
//        try (Scanner scanner = new Scanner(file)) {
//            try (Statement statement = 
//                    DatabaseConnection.getDatabaseConnection().createStatement();) {
//                while(scanner.hasNextLine()) {
//                    
//                    if(scanner.equals("Name,Code")) {
//                        continue;
//                    }
//                    String line = scanner.nextLine();
//                    if(line.contains("\"")) {
//                        //line.replace('\"', '');
//                        line = line.replace("\"", "");
//                    }
//                    country = line.substring(0,line.lastIndexOf(','));
//                    statement.executeUpdate(
//                            "INSERT INTO country("
//                                    + "country, createDate, createdBy,"
//                                    + " lastUpdate, lastUpdateBy)"
//                                    + "VALUES (\"" + country + "\", current_timestamp(),"
//                                    + " \"" + updater + "\", current_timestamp(),"
//                                    + " \"" + updater + "\")");
//                    
//                }
//
//            } catch (SQLException ex) {
//                Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//*/
//
//    
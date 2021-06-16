package dev.nicholaskoldys.multiuserscheduler.model.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dev.nicholaskoldys.multiuserscheduler.model.AddressBook;
import dev.nicholaskoldys.multiuserscheduler.model.Customer;
import dev.nicholaskoldys.multiuserscheduler.model.AppointmentCalendar;
import dev.nicholaskoldys.multiuserscheduler.model.dao.CustomerDAO;
import dev.nicholaskoldys.multiuserscheduler.service.DatabaseConnection;
import dev.nicholaskoldys.multiuserscheduler.service.EnvironmentVariables;

/**
 *
 * @author nicho
 */
public class CustomerDAOImpl implements CustomerDAO {
        
    private final String TABLE_CUSTOMER = "customer";
    private final String CUSTOMERID_COLUMN = "customerId";
    private final String CUSTOMER_NAME_COLUMN = "customerName";
    private final String ADDRESSID_COLUMN = "addressId";
    private final String CUSTOMER_ACTIVE_COLUMN = "active";
    
    private final String CREATEDATE_COLUMN = "createDate";
    private final String CREATEDBY_COLUMN = "createdBy";
    private final String LASTUPDATE_COLUMN = "lastUpdate";
    private final String LASTUPDATEBY_COLUMN = "lastUpdateBy";
    
    private final String SELECT_ALL_CUSTOMER =
            "SELECT * FROM " + TABLE_CUSTOMER;
//            + " WHERE " + CUSTOMER_ACTIVE_COLUMN + " = true";
    
    private final String SELECT_SPECIFIC_CUSTOMER = 
            "SELECT * FROM " + TABLE_CUSTOMER
            + " WHERE "  + CUSTOMERID_COLUMN + " = ?";
    
    private final String SELECT_SPECIFIC_CUSTOMER_BY_ID = 
            "SELECT * FROM " + TABLE_CUSTOMER
            + " WHERE "  + CUSTOMER_NAME_COLUMN + " = ? AND "
            + ADDRESSID_COLUMN + " = ?";
    
    private final String INSERT_CUSTOMER = 
            "INSERT INTO " + TABLE_CUSTOMER + " ("
            + CUSTOMER_NAME_COLUMN + ", "
            + ADDRESSID_COLUMN + ", "
            + CUSTOMER_ACTIVE_COLUMN + ", "
            + CREATEDATE_COLUMN + ", "
            + CREATEDBY_COLUMN + ", "
            + LASTUPDATE_COLUMN + ", "
            + LASTUPDATEBY_COLUMN + ") "
            + "VALUES (?, ?, ?, "
            + EnvironmentVariables.CURRENTTIME_METHOD + ", " + "?" + ","
            + EnvironmentVariables.CURRENTTIME_METHOD + ", " + "?" + ")";
    
    private final String UPDATE_CUSTOMER = 
            "UPDATE " + TABLE_CUSTOMER + " SET "
            + CUSTOMER_NAME_COLUMN + " = ?, " 
            + ADDRESSID_COLUMN + " = ?, " 
            + CUSTOMER_ACTIVE_COLUMN + " = ?, " 
            + LASTUPDATE_COLUMN + " = " + EnvironmentVariables.CURRENTTIME_METHOD + ", "
            + LASTUPDATEBY_COLUMN + " = ?"
            + " WHERE " + CUSTOMERID_COLUMN + "= ?";
    
    private final String DELETE_CUSTOMER = 
            "UPDATE " + TABLE_CUSTOMER + " SET "
            + CUSTOMER_ACTIVE_COLUMN + " = false"
            + " WHERE " + CUSTOMERID_COLUMN + " = ?";
    
    private static final CustomerDAOImpl instance =
            new CustomerDAOImpl();

    /**
     * 
     * @return 
     */
    public static CustomerDAOImpl getInstance() {
        return instance;
    }
    
    
    /**
     * 
     * @return 
     */
    @Override
    public List<Customer> getAllCustomers() {

        List<Customer> customerList = new ArrayList<>();
        
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_ALL_CUSTOMER);
                ResultSet results = selectStatement.executeQuery()) {
            
            while (results.next()) {
                
                Customer customer = new Customer(
                        results.getInt(CUSTOMERID_COLUMN),
                        results.getString(CUSTOMER_NAME_COLUMN),
                        AddressBook.getInstance().lookupAddress(results.getInt(ADDRESSID_COLUMN)),
                        results.getBoolean(CUSTOMER_ACTIVE_COLUMN)
                );
                customerList.add(customer);
            }
            return customerList;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 
     * @param customerId
     * @return 
     */
    @Override
    public Customer getCustomer(int customerId) {
        
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_CUSTOMER)) {
            
            selectStatement.setInt(1, customerId);
            ResultSet results = selectStatement.executeQuery();
            
            results.next();
                        
            Customer customer = new Customer(
                    results.getInt(CUSTOMERID_COLUMN),
                    results.getString(CUSTOMER_NAME_COLUMN),
                    AddressBook.getInstance().lookupAddress(results.getInt(ADDRESSID_COLUMN)),
                    results.getBoolean(CUSTOMER_ACTIVE_COLUMN)
            );
            return customer;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Customer getCustomer(int customerId, boolean isAltMethod) {

        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_CUSTOMER)) {

            selectStatement.setInt(1, customerId);
            ResultSet results = selectStatement.executeQuery();

            results.next();

            Customer customer = new Customer(
                    results.getInt(CUSTOMERID_COLUMN),
                    results.getString(CUSTOMER_NAME_COLUMN),
                    //AddressBook.getInstance().lookupAddress(results.getInt(ADDRESSID_COLUMN)),
                    AddressDAOImpl.getInstance().getAddress(results.getInt(ADDRESSID_COLUMN), true),
                    results.getBoolean(CUSTOMER_ACTIVE_COLUMN)
            );
            return customer;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 
     * @param customer
     * @return 
     */
    public int getCustomerId(Customer customer) {
        
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_CUSTOMER_BY_ID)) {
            
            selectStatement.setString(1, customer.getCustomerName());
            selectStatement.setInt(2, customer.getAddressId());
            ResultSet results = selectStatement.executeQuery();
            
            results.next();
            
            return results.getInt(CUSTOMERID_COLUMN);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    
    /**
     * 
     * @param customer
     * @return 
     */
    @Override
    public Boolean create(Customer customer) {
        
        try (PreparedStatement insertStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(INSERT_CUSTOMER)) {
            
            insertStatement.setString(1, customer.getCustomerName());
            insertStatement.setInt(2, customer.getAddressId());
            if(customer.getActive() == true) {
                insertStatement.setInt(3, 1);
            } else {
                insertStatement.setInt(3, 0);
            }
            // TODO TEMP REMOVE AppointmentCalendar.getCurrentUser().getUserName()
            insertStatement.setString(4, "NKoldys");
            insertStatement.setString(5, "NKoldys");
            
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
     * @param customer
     * @return 
     */
    @Override
    public Boolean update(Customer customer) {
        
        try (PreparedStatement updateStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(UPDATE_CUSTOMER)) {
            
            updateStatement.setString(1, customer.getCustomerName());
            updateStatement.setInt(2, customer.getAddressId());
            if(customer.getActive() == true) {
                updateStatement.setInt(3, 1);
            } else {
                updateStatement.setInt(3, 0);
            }
            updateStatement.setInt(4, customer.getCustomerId());
            updateStatement.setString(5, AppointmentCalendar.getCurrentUser().getUserName());
            
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
     * @param customer
     * @return 
     */
    @Override
    public Boolean delete(Customer customer) {
        
        try (PreparedStatement deleteStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(DELETE_CUSTOMER)) {
            
            deleteStatement.setInt(1, customer.getCustomerId());
            
            if (deleteStatement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

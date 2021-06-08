package dev.nicholaskoldys.multiuserscheduler.model.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dev.nicholaskoldys.multiuserscheduler.model.Address;
import dev.nicholaskoldys.multiuserscheduler.model.AddressBook;
import dev.nicholaskoldys.multiuserscheduler.model.AppointmentCalendar;
import dev.nicholaskoldys.multiuserscheduler.model.dao.AddressDAO;
import dev.nicholaskoldys.multiuserscheduler.service.DatabaseConnection;

/**
 *
 * @author nicho
 */
public class AddressDAOImpl implements AddressDAO {
        
    private final String TABLE_ADDRESS = "address";
    private final String ADDRESSID_COLUMN = "addressId";
    private final String ADDRESS_COLUMN = "address";
    private final String ADDRESS2_COLUMN = "address2";
    private final String CITYID_COLUMN = "cityId";
    private final String ADDRESS_POSTALCODE_COLUMN = "postalCode";
    private final String ADDRESS_PHONE_COLUMN = "phone";
    
    private final String CREATEDATE_COLUMN = "createDate";
    private final String CREATEDBY_COLUMN = "createdBy";
    private final String LASTUPDATE_COLUMN = "lastUpdate";
    private final String LASTUPDATEBY_COLUMN = "lastUpdateBy";
    
    private final String SELECT_ALL_ADDRESS =
            "SELECT * FROM " + TABLE_ADDRESS;
    
    private final String SELECT_SPECIFIC_ADDRESS = 
            "SELECT * FROM " + TABLE_ADDRESS
            + " WHERE "  + ADDRESSID_COLUMN + " = ?";
    
    private final String SELECT_SPECIFIC_ADDRESS_BY_ID = 
            "SELECT " + ADDRESSID_COLUMN + " FROM " + TABLE_ADDRESS
            + " WHERE "  
            + ADDRESS_COLUMN + " = ? AND "
            + ADDRESS2_COLUMN + " = ? AND "
            + CITYID_COLUMN + " = ? AND "
            + ADDRESS_POSTALCODE_COLUMN + " = ? AND "
            + ADDRESS_PHONE_COLUMN + " = ?";
    
    private final String INSERT_ADDRESS = 
            "INSERT INTO " + TABLE_ADDRESS
            + " (" + ADDRESS_COLUMN + ", " + ADDRESS2_COLUMN 
            + ", " + CITYID_COLUMN + ", " + ADDRESS_POSTALCODE_COLUMN 
            + ", " + ADDRESS_PHONE_COLUMN 
            + ", " + CREATEDATE_COLUMN + ", " + CREATEDBY_COLUMN 
            + ", " + LASTUPDATE_COLUMN + ", " + LASTUPDATEBY_COLUMN + ") "
            + "VALUES (?, ?, ?, ?, ?" 
            + ", current_timestamp(), " + "?" 
            + ", current_timestamp(), " + "?" + ")";
    
    private final String UPDATE_ADDRESS = 
            "UPDATE " + TABLE_ADDRESS + " SET "
            + ADDRESS_COLUMN + " = ?, "
            + ADDRESS2_COLUMN + " = ?, "
            + CITYID_COLUMN + " = ?, " 
            + ADDRESS_POSTALCODE_COLUMN + " = ?, " 
            + ADDRESS_PHONE_COLUMN + " = ?, "
            + LASTUPDATE_COLUMN + " = current_timestamp(), " 
            + LASTUPDATEBY_COLUMN + " = ?"
            + " WHERE " + ADDRESSID_COLUMN + "= ?";
    
    private final String DELETE_ADDRESS = 
            "DELETE FROM " + TABLE_ADDRESS + " WHERE "
            + ADDRESSID_COLUMN + " = ?";
    
    
    private static final AddressDAOImpl instance =
            new AddressDAOImpl();
    

    
    private AddressDAOImpl() {
        
    }
    
    
    /**
     * 
     * @return 
     */
    public static AddressDAOImpl getInstance() {
        return instance;
    }
    
    
    /**
     * 
     * @return 
     */
    @Override
    public List<Address> getAllAddresses() {

        List<Address> addressList = new ArrayList<>();
        
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_ALL_ADDRESS);
                ResultSet results = selectStatement.executeQuery()) {
            
            while (results.next()) {
                
                Address address = new Address(
                        results.getInt(ADDRESSID_COLUMN),
                        results.getString(ADDRESS_COLUMN),
                        results.getString(ADDRESS2_COLUMN),
                        AddressBook.getInstance().lookupCity(results.getInt(CITYID_COLUMN)),
                        results.getString(ADDRESS_POSTALCODE_COLUMN),
                        results.getString(ADDRESS_PHONE_COLUMN)
                );
                addressList.add(address);
            }
            return addressList;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 
     * @param addressId
     * @return 
     */
    @Override
    public Address getAddress(int addressId) {
        
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_ADDRESS)) {
            
            selectStatement.setInt(1, addressId);
            ResultSet results = selectStatement.executeQuery();
            
            results.next();
            Address address = new Address(
                    results.getInt(ADDRESSID_COLUMN),
                    results.getString(ADDRESS_COLUMN),
                    results.getString(ADDRESS2_COLUMN),
                    AddressBook.getInstance().lookupCity(results.getInt(CITYID_COLUMN)),
                    results.getString(ADDRESS_POSTALCODE_COLUMN),
                    results.getString(ADDRESS_PHONE_COLUMN)
            );
            return address;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 
     * @param address
     * @return 
     */
    public int getAddressId(Address address) {
        
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_ADDRESS_BY_ID)) {
            
            selectStatement.setString(1, address.getAddress());
            selectStatement.setString(2,address.getAddress2());
            selectStatement.setInt(3, address.getCityId());
            selectStatement.setString(4, address.getPostalCode());
            selectStatement.setString(5, address.getPhoneNum());
            ResultSet results = selectStatement.executeQuery();
            
            results.next();
            
            return results.getInt(ADDRESSID_COLUMN);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    
    /**
     * 
     * @param address
     * @return 
     */
    @Override
    public Boolean create(Address address) {
        
        try (PreparedStatement insertStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(INSERT_ADDRESS)) {
            
            insertStatement.setString(1, address.getAddress());
            insertStatement.setString(2, address.getAddress2());
            insertStatement.setInt(3, address.getCityId());
            insertStatement.setString(4, address.getPostalCode());
            insertStatement.setString(5, address.getPhoneNum());
            insertStatement.setString(6, AppointmentCalendar.getCurrentUser().getUserName());
            insertStatement.setString(7, AppointmentCalendar.getCurrentUser().getUserName());
            
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
     * @param address
     * @return 
     */
    @Override
    public Boolean update(Address address) {
        
        try (PreparedStatement updateStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(UPDATE_ADDRESS)) {
            
            updateStatement.setString(1, address.getAddress());
            updateStatement.setString(2, address.getAddress2());
            updateStatement.setInt(3, address.getCityId());
            updateStatement.setString(4, address.getPostalCode());
            updateStatement.setString(5, address.getPhoneNum());
            updateStatement.setInt(6, address.getAddressId());
            updateStatement.setString(7, AppointmentCalendar.getCurrentUser().getUserName());
            
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
     * @param address
     * @return 
     */
    @Override
    public Boolean delete(Address address) {
        
        try (PreparedStatement deleteStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(DELETE_ADDRESS)) {
            
            deleteStatement.setInt(1, address.getAddressId());
            
            if (deleteStatement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

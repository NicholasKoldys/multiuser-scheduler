package dev.nicholaskoldys.multiuserscheduler.model.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dev.nicholaskoldys.multiuserscheduler.model.Country;
import dev.nicholaskoldys.multiuserscheduler.model.AppointmentCalendar;
import dev.nicholaskoldys.multiuserscheduler.model.dao.CountryDAO;
import dev.nicholaskoldys.multiuserscheduler.service.DatabaseConnection;

/**
 *
 * @author nicho
 */
public class CountryDAOImpl implements CountryDAO {
        
    private final String TABLE_COUNTRY = "country";
    private final String COUNTRYID_COLUMN = "countryId";
    private final String COUNTRY_COLUMN = "country";
    
    private final String CREATEDATE_COLUMN = "createDate";
    private final String CREATEDBY_COLUMN = "createdBy";
    private final String LASTUPDATE_COLUMN = "lastUpdate";
    private final String LASTUPDATEBY_COLUMN = "lastUpdateBy";
    
    private final String SELECT_ALL_COUNTRY =
            "SELECT * FROM " + TABLE_COUNTRY;
    
    private final String SELECT_SPECIFIC_COUNTRY = 
            "SELECT * FROM " + TABLE_COUNTRY
            + " WHERE "  + COUNTRYID_COLUMN + " = ?";
    
    private final String SELECT_SPECIFIC_COUNTRY_BY_ID = 
            "SELECT " + COUNTRYID_COLUMN + " FROM " + TABLE_COUNTRY
            + " WHERE "  + COUNTRY_COLUMN + " = ?";
    
    private final String INSERT_COUNTRY = 
            "INSERT INTO " + TABLE_COUNTRY
            + " (" + COUNTRY_COLUMN + ", " + CREATEDATE_COLUMN + ", "
            + CREATEDBY_COLUMN + ", " + LASTUPDATE_COLUMN + ", " 
            + LASTUPDATEBY_COLUMN + ") "
            + "VALUES (?, current_timestamp(), " 
            + "?" + ", current_timestamp(), " 
            + "?" + ")";
    
    private final String UPDATE_COUNTRY = 
            "UPDATE " + TABLE_COUNTRY + " SET "
            + COUNTRY_COLUMN + " = ?, " 
            + LASTUPDATE_COLUMN + " = current_timestamp(), " 
            + LASTUPDATEBY_COLUMN + " = ?"
            + " WHERE " + COUNTRYID_COLUMN + " = ?";
    
    private final String DELETE_COUNTRY = 
            "DELETE FROM " + TABLE_COUNTRY + " WHERE "
            + COUNTRYID_COLUMN + " = ?";
    
    
    private static final CountryDAOImpl instance = new CountryDAOImpl();

    
    /**
     * 
     * @return 
     */
    public static CountryDAOImpl getInstance() {
        return instance;
    }
    
    
    /**
     * 
     * @return 
     */
    @Override
    public List<Country> getAllCountries() {
        
        List<Country> countryList = new ArrayList<>();
        
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_ALL_COUNTRY);
                ResultSet results = selectStatement.executeQuery()) {
            
            while (results.next()) {
                
                Country country = new Country(
                        results.getInt(COUNTRYID_COLUMN),
                        results.getString(COUNTRY_COLUMN)
                );
                countryList.add(country);
            }
            return countryList;
            
        } catch (SQLException ex) {
            
        }
        return null;
    }
    
    
    /**
     * 
     * @param countryId
     * @return 
     */
    @Override
    public Country getCountry(int countryId) {
        
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_COUNTRY)) {
            
            selectStatement.setInt(1, countryId);
            ResultSet results = selectStatement.executeQuery();
            
            results.next();
            Country country = new Country(
                    results.getInt(COUNTRYID_COLUMN),
                    results.getString(COUNTRY_COLUMN)
            );
            return country;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 
     * @param country
     * @return 
     */
    public int getCountryId(Country country) {
        
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_COUNTRY_BY_ID)) {
            
            selectStatement.setString(1, country.getCountry());
            ResultSet results = selectStatement.executeQuery();
            
            results.next();
            
            return results.getInt(COUNTRYID_COLUMN);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    
    /**
     * 
     * @param country
     * @return 
     */
    @Override
    public Boolean create(Country country) {
        
        try (PreparedStatement insertStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(INSERT_COUNTRY)) {
            
            insertStatement.setString(1, country.getCountry());
            insertStatement.setString(2, AppointmentCalendar.getCurrentUser().getUserName());
            insertStatement.setString(3, AppointmentCalendar.getCurrentUser().getUserName());
            
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
     * @param country
     * @return 
     */
    @Override
    public Boolean update(Country country) {
        
        try (PreparedStatement updateStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(UPDATE_COUNTRY)) {
            
            updateStatement.setString(1, country.getCountry());
            updateStatement.setInt(2, country.getCountryId());
            updateStatement.setString(3, AppointmentCalendar.getCurrentUser().getUserName());
            
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
     * @param country
     * @return 
     */
    @Override
    public Boolean delete(Country country) {
        
        try (PreparedStatement deleteStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(DELETE_COUNTRY)) {
            
            deleteStatement.setInt(1, country.getCountryId());
            
            if (deleteStatement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
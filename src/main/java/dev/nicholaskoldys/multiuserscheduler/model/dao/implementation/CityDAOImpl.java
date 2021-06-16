package dev.nicholaskoldys.multiuserscheduler.model.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dev.nicholaskoldys.multiuserscheduler.model.AddressBook;
import dev.nicholaskoldys.multiuserscheduler.model.City;
import dev.nicholaskoldys.multiuserscheduler.model.AppointmentCalendar;
import dev.nicholaskoldys.multiuserscheduler.model.dao.CityDAO;
import dev.nicholaskoldys.multiuserscheduler.service.DatabaseConnection;
import dev.nicholaskoldys.multiuserscheduler.service.EnvironmentVariables;

/**
 *
 * @author nicho
 */
public class CityDAOImpl implements CityDAO {
        
    private final String TABLE_CITY = "city";
    private final String CITYID_COLUMN = "cityId";
    private final String CITY_COLUMN = "city";
    private final String COUNTRYID_COLUMN = "countryId";
    
    private final String CREATEDATE_COLUMN = "createDate";
    private final String CREATEDBY_COLUMN = "createdBy";
    private final String LASTUPDATE_COLUMN = "lastUpdate";
    private final String LASTUPDATEBY_COLUMN = "lastUpdateBy";
    
    private final String SELECT_ALL_CITY =
            "SELECT * FROM " + TABLE_CITY;
    
    private final String SELECT_SPECIFIC_CITY = 
            "SELECT * FROM " + TABLE_CITY
            + " WHERE "  + CITYID_COLUMN + " = ?";
    
    private final String SELECT_SPECIFIC_CITY_BY_ID = 
            "SELECT " + CITYID_COLUMN + " FROM " + TABLE_CITY
            + " WHERE "  + CITY_COLUMN + " = ? AND "
            + COUNTRYID_COLUMN + " = ?";
    
    private final String INSERT_CITY = 
            "INSERT INTO " + TABLE_CITY + " ("
            + CITY_COLUMN + ", "
            + COUNTRYID_COLUMN + ", "
            + CREATEDATE_COLUMN + ", "
            + CREATEDBY_COLUMN + ", "
            + LASTUPDATE_COLUMN + ", "
            + LASTUPDATEBY_COLUMN + ") "
            + "VALUES (?, ?, "
            + EnvironmentVariables.CURRENTTIME_METHOD + ", " + "?" + ","
            + EnvironmentVariables.CURRENTTIME_METHOD + ", " + "?" + ")";
    
    private final String UPDATE_CITY = 
            "UPDATE " + TABLE_CITY + " SET "
            + CITY_COLUMN + " = ?, " 
            + COUNTRYID_COLUMN + " = ?, " 
            + LASTUPDATE_COLUMN + " = " + EnvironmentVariables.CURRENTTIME_METHOD + ", "
            + LASTUPDATEBY_COLUMN + " = ?"
            + " WHERE " + CITYID_COLUMN + "= ?";
    
    private final String DELETE_CITY = 
            "DELETE FROM " + TABLE_CITY
            + " WHERE " + CITYID_COLUMN + " = ?";

    private static final CityDAOImpl instance = new CityDAOImpl();
    

    /**
     * 
     * @return 
     */
    public static CityDAOImpl getInstance() {
        return instance;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public List<City> getAllCities() {
        
        List<City> cityList = new ArrayList();
        
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_ALL_CITY);
                ResultSet results = selectStatement.executeQuery()) {
            
            while (results.next()) {
                
                City city = new City(
                        results.getInt(CITYID_COLUMN),
                        results.getString(CITY_COLUMN),
                        AddressBook.getInstance().lookupCountry(results.getInt(COUNTRYID_COLUMN))
                );
                
                cityList.add(city);
            }
            return cityList;
            
        } catch (SQLException ex) {
            
        }
        return null;
    }

    /**
     * 
     * @param cityId
     * @return 
     */
    @Override
    public City getCity(int cityId) {
        
        try (PreparedStatement selectStatement
                     = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_CITY)) {
            
            selectStatement.setInt(1, cityId);
            ResultSet results = selectStatement.executeQuery();
            
            results.next();
            City city = new City(
                    results.getInt(CITYID_COLUMN),
                    results.getString(CITY_COLUMN),
                    AddressBook.getInstance().lookupCountry(results.getInt(COUNTRYID_COLUMN))
            );
            return city;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public City getCity(int cityId, boolean isAltMethod) {

        try (PreparedStatement selectStatement
                     = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_CITY)) {

            selectStatement.setInt(1, cityId);
            ResultSet results = selectStatement.executeQuery();

            results.next();
            City city = new City(
                    results.getInt(CITYID_COLUMN),
                    results.getString(CITY_COLUMN),
                    //AddressBook.getInstance().lookupCountry(results.getInt(COUNTRYID_COLUMN))
                    CountryDAOImpl.getInstance().getCountry(results.getInt(COUNTRYID_COLUMN))
            );
            return city;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * @param city
     * @return 
     */
    public int getCityId(City city) {
        
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_CITY_BY_ID)) {
            
            selectStatement.setString(1, city.getCity());
            selectStatement.setInt(2, city.getCountryId());
            ResultSet results = selectStatement.executeQuery();
            
            results.next();
            
            return results.getInt(CITYID_COLUMN);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * 
     * @param city
     * @return 
     */
    @Override
    public Boolean create(City city) {
        
        try (PreparedStatement insertStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(INSERT_CITY)) {
            
            insertStatement.setString(1, city.getCity());
            insertStatement.setInt(2, city.getCountryId());
            insertStatement.setString(3, AppointmentCalendar.getCurrentUser().getUserName());
            insertStatement.setString(4, AppointmentCalendar.getCurrentUser().getUserName());
            
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
     * @param city
     * @return
     */
    public Boolean create(String city, int countryId) {

        try (PreparedStatement insertStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(INSERT_CITY)) {

            insertStatement.setString(1, city);
            insertStatement.setInt(2, countryId);
            insertStatement.setString(3, AppointmentCalendar.getCurrentUser().getUserName());
            insertStatement.setString(4, AppointmentCalendar.getCurrentUser().getUserName());

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
     * @param city
     * @return 
     */
    @Override
    public Boolean update(City city) {
        
        try (PreparedStatement updateStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(UPDATE_CITY)) {
            
            updateStatement.setString(1, city.getCity());
            updateStatement.setInt(2, city.getCountryId());
            updateStatement.setInt(3, city.getCityId());
            updateStatement.setString(4, AppointmentCalendar.getCurrentUser().getUserName());
            
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
     * @param city
     * @return 
     */
    @Override
    public Boolean delete(City city) {
        
        try (PreparedStatement deleteStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(DELETE_CITY)) {
            
            deleteStatement.setInt(1, city.getCityId());
            
            if (deleteStatement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

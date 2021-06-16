package dev.nicholaskoldys.multiuserscheduler.model.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.nicholaskoldys.multiuserscheduler.model.dao.UserDAO;
import dev.nicholaskoldys.multiuserscheduler.model.User;
import dev.nicholaskoldys.multiuserscheduler.service.DatabaseConnection;
import dev.nicholaskoldys.multiuserscheduler.service.EnvironmentVariables;

/**
 *
 * @author nicho
 */
public class UserDAOImpl implements UserDAO {
        
    private static final String TABLE_USER = "user";
    private static final String USERID_COLUMN = "userId";
    private static final String USER_NAME_COLUMN = "userName";
    private static final String USER_PASSWORD_COLUMN = "password";
    private static final String USER_ACTIVE_COLUMN = "active";
    
    private final String CREATEDATE_COLUMN = "createDate";
    private final String CREATEDBY_COLUMN = "createdBy";
    private final String LASTUPDATE_COLUMN = "lastUpdate";
    private final String LASTUPDATEBY_COLUMN = "lastUpdateBy";
    
    private final String SELECT_ALL_USER =
            "SELECT * FROM " + TABLE_USER;
    
    private final String SELECT_SPECIFIC_USER = 
            "SELECT * FROM " + TABLE_USER
            + " WHERE "  + USERID_COLUMN + " = ?";
    
    private final String SELECT_SPECIFIC_USER_BY_ID = 
            "SELECT * FROM " + TABLE_USER
            + " WHERE "  + USER_NAME_COLUMN + " = ? AND "
            + USER_PASSWORD_COLUMN + " = ? AND "
            + USER_ACTIVE_COLUMN + " = ?";
    
    private final String INSERT_USER = 
            "INSERT INTO " + TABLE_USER + " ("
            + USER_NAME_COLUMN + ", "
            + USER_PASSWORD_COLUMN + ", "
            + USER_ACTIVE_COLUMN + ", "
            + CREATEDATE_COLUMN + ", "
            + CREATEDBY_COLUMN + ", "
            + LASTUPDATE_COLUMN + ", "
            + LASTUPDATEBY_COLUMN + ") "
            + "VALUES (?, ?, ?, "
            + EnvironmentVariables.CURRENTTIME_METHOD + ", " + "?" + ","
            + EnvironmentVariables.CURRENTTIME_METHOD + ", " + "?" + ")";

    private final String UPDATE_USER = 
            "UPDATE " + TABLE_USER + " SET "
            + USER_NAME_COLUMN + " = ?, " 
            + USER_PASSWORD_COLUMN + " = ?, " 
            + USER_ACTIVE_COLUMN + " = ?, " 
            + LASTUPDATE_COLUMN + " = " + EnvironmentVariables.CURRENTTIME_METHOD + ", "
            + LASTUPDATEBY_COLUMN + " = " + "?"
            + " WHERE " + USERID_COLUMN + "= ?";
    
    private final String DELETE_USER = 
            "UPDATE " + TABLE_USER + " SET "
            + USER_ACTIVE_COLUMN + " = false"
            + " WHERE " + USERID_COLUMN + " = ?";

    private static final UserDAOImpl instance = new UserDAOImpl();
    
    
    private UserDAOImpl() { }
    
    
    /**
     * 
     * @return 
     */
    public static UserDAOImpl getInstance() {
        return instance;
    }
    
    
    /**
     * 
     * @return 
     */
    @Override
    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();
        
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_ALL_USER);
                ResultSet results = selectStatement.executeQuery()) {
            
            while (results.next()) {
                
                User user = new User(
                        results.getInt(USERID_COLUMN),
                        results.getString(USER_NAME_COLUMN),
                        results.getString(USER_PASSWORD_COLUMN),
                        results.getBoolean(USER_ACTIVE_COLUMN)
                );
                userList.add(user);
            }
            return userList;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 
     * @param userId
     * @return 
     */
    @Override
    public User getUser(int userId) {
        
        try (PreparedStatement selectStatement
                     = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_USER)) {
            
            selectStatement.setInt(1, userId);
            ResultSet results = selectStatement.executeQuery();
            
            results.next();
            User user = new User(
                    results.getInt(USERID_COLUMN),
                    results.getString(USER_NAME_COLUMN),
                    results.getString(USER_PASSWORD_COLUMN),
                    results.getBoolean(USER_ACTIVE_COLUMN)
            );
            return user;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 
     * @param user
     * @return 
     */
    public int getUserId(User user) {
        
        try (PreparedStatement selectStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(SELECT_SPECIFIC_USER_BY_ID)) {
            
            selectStatement.setString(1, user.getUserName());
            selectStatement.setString(2, user.getPassword());
            selectStatement.setBoolean(3, user.isActive());
            ResultSet results = selectStatement.executeQuery();
            
            results.next();
            
            return results.getInt(USERID_COLUMN);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    
    /**
     * 
     * @param userName
     * @param password
     * @return 
     */
    public User userLoginRequest(
            String userName, String password) {
        
        List<User> userList = getAllUsers();
        
        for(User user : userList) {
            
            if (user.getUserName().equals(userName) 
                    && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    
    /**
     * 
     * @param user
     * @return 
     */
    @Override
    public Boolean create(User user) {
        
        try (PreparedStatement insertStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(INSERT_USER)) {
            
            insertStatement.setString(1, user.getUserName());
            insertStatement.setString(2, user.getPassword());
            if(user.getActive() == true) {
                insertStatement.setInt(3, 1);
            } else {
                insertStatement.setInt(3, 0);
            }
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
     * @param user
     * @return 
     */
    @Override
    public Boolean update(User user) {
        
        try (PreparedStatement updateStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(UPDATE_USER)) {
            
            updateStatement.setString(1, user.getUserName());
            updateStatement.setString(2, user.getPassword());
            if(user.getActive() == true) {
                updateStatement.setInt(3, 1);
            } else {
                updateStatement.setInt(3, 0);
            }
            updateStatement.setString(4, "NKoldys");
            updateStatement.setInt(5, user.getUserId());
            
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
     * @param user
     * @return 
     */
    @Override
    public Boolean delete(User user) {
        
        try (PreparedStatement deleteStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(DELETE_USER)) {
            
            deleteStatement.setInt(1, user.getUserId());
            
            if (deleteStatement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

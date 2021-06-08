package dev.nicholaskoldys.multiuserscheduler.model;

/**
 *
 * @author nicho
 */
public class User {
    
    private int userId;
    private String userName;
    private String password;
    private Boolean active;
    
    
    
    public User(String userName, String password, Boolean active) {
        
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.userId = 0;
    }
    
    
    
    public User(int userId, String userName, String password, Boolean active) {
        
        this(userName, password, active);
        this.userId = userId;
    }
    
    
    /**
     * @return the userName
     */
    public String getUserName() {
        
        return userName;
    }
    

    /**
     * @return the userId
     */
    public int getUserId() {
        
        return userId;
    }
    
    
    /**
     * @return the active
     */
    public Boolean isActive() {
        
        return getActive();
    }
    
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        
        return this.getUserName();
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }
}

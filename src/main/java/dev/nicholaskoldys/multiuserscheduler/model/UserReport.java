package dev.nicholaskoldys.multiuserscheduler.model;

import java.time.Month;

/**
 *
 * @author nicho
 */
public class UserReport extends Report {
    
    private int count;
    private Month month;
    private String user;
    
    public UserReport(int count, Month month, String user) {
        
        this.count = count;
        this.month = month;
        this.user = user;
    }
    
    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    /**
     * @return the month
     */
    public Month getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }
    
}

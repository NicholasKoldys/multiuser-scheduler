package dev.nicholaskoldys.multiuserscheduler.model;

import java.time.Month;

/**
 *
 * @author nicho
 */
public class AppointmentTypeReport extends Report {

    private int count;
    private Month month;
    private String type;
    
    public AppointmentTypeReport(int count, Month month, String type) {
        
        this.count = count;
        this.month = month;
        this.type = type;
    }
    
    /**
     * 
     * @param count 
     */
    @Override
    public void setCount(int count) {
        this.count = count;
    }
    
    /**
     * 
     * @return 
     */
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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
}

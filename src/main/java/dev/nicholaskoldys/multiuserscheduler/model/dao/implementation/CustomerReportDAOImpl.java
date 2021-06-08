package dev.nicholaskoldys.multiuserscheduler.model.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import dev.nicholaskoldys.multiuserscheduler.model.dao.ReportDAO;
import dev.nicholaskoldys.multiuserscheduler.model.CustomerReport;
import dev.nicholaskoldys.multiuserscheduler.service.DatabaseConnection;

/**
 *
 * @author nicho
 */
public class CustomerReportDAOImpl implements ReportDAO<CustomerReport> {

    private final String COUNT_APPOINMENT_PER_CUSTOMER_YEAR = 
            "SELECT customer.customerName as Customer,"
            + "count(*) AS Count, year(start) as Year\n" 
            + "from appointment\n"
            + "inner join customer ON customer.customerId"
            + " = appointment.customerId\n" 
            + "group by Year, Customer\n" 
            + "having Year = ?";
    
    private static final CustomerReportDAOImpl instance = new CustomerReportDAOImpl();
    
    
    /**
     * 
     * @return 
     */
    public static CustomerReportDAOImpl getInstance() {
        return instance;
    }
    
    
    /**
     * 
     * @param date
     * @return 
     */
    public List<CustomerReport> queryReport(LocalDate date) {
        
        int year = date.getYear();
        
        try (PreparedStatement countStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(COUNT_APPOINMENT_PER_CUSTOMER_YEAR)) {
            
            countStatement.setInt(1, year);
            ResultSet results = countStatement.executeQuery();
            List<CustomerReport> reportList = new ArrayList<>();
            
            while(results.next()) {
                
                CustomerReport count = new CustomerReport(
                        results.getInt("Count"),
                        Year.of(results.getInt("Year")),
                        results.getString("Customer")
                );
                reportList.add(count);
            }
            return reportList;
            
        } catch (SQLException ex) {
            
            ex.getStackTrace();
        }
        return null;
    }
    
    
    /**
     * 
     * @return 
     */
    @Override
    public List<CustomerReport> queryReport() {
        
        int year = LocalDateTime.now().getYear();
        
        try (PreparedStatement countStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(COUNT_APPOINMENT_PER_CUSTOMER_YEAR)) {
            
            countStatement.setInt(1, year);
            ResultSet results = countStatement.executeQuery();
            List<CustomerReport> reportList = new ArrayList<>();
            
            while(results.next()) {
                
                CustomerReport count = new CustomerReport(
                        results.getInt("Count"),
                        Year.of(results.getInt("Year")),
                        results.getString("Customer")
                );
                reportList.add(count);
            }
            return reportList;
            
        } catch (SQLException ex) {
            
            ex.getStackTrace();
        }
        return null;
    }
}

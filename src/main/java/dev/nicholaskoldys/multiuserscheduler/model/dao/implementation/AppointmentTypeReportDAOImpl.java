/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.nicholaskoldys.multiuserscheduler.model.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import dev.nicholaskoldys.multiuserscheduler.model.AppointmentTypeReport;
import dev.nicholaskoldys.multiuserscheduler.model.dao.ReportDAO;
import dev.nicholaskoldys.multiuserscheduler.service.DatabaseConnection;

/**
 *
 * @author nicho
 */
public class AppointmentTypeReportDAOImpl implements ReportDAO<AppointmentTypeReport> {

    private final String COUNT_APPOINTMNET_TYPE_PER_MONTH = 
            "SELECT type as Type, count(*) AS Count,"
            + " month(start) as Month\n"
            + "from appointment\n"
            + "group by month(start), type";
    
    
    private static final AppointmentTypeReportDAOImpl instance = new AppointmentTypeReportDAOImpl();
    
    
    /**
     * 
     * @return 
     */
    public static AppointmentTypeReportDAOImpl getInstance() {
        return instance;
    }
    
    
    @Override
    public List<AppointmentTypeReport> queryReport() {
        
        try (PreparedStatement countStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(COUNT_APPOINTMNET_TYPE_PER_MONTH);
                ResultSet results = countStatement.executeQuery()) {
            
            List<AppointmentTypeReport> reportList = new ArrayList();
            
            while(results.next()) {
                
                AppointmentTypeReport count = new AppointmentTypeReport(
                        results.getInt("Count"),
                        Month.of(results.getInt("Month")),
                        results.getString("Type")
                );
                
                reportList.add(count);
            }
            
            return reportList;
            
        } catch (SQLException ex) {
            System.out.println("SQL QUERY FAILURE : countALLTypes appointment " + ex.getMessage());
        }
        
        return null;
    }
}

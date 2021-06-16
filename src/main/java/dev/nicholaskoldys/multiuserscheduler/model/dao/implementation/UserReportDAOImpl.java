package dev.nicholaskoldys.multiuserscheduler.model.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import dev.nicholaskoldys.multiuserscheduler.model.dao.ReportDAO;
import dev.nicholaskoldys.multiuserscheduler.model.UserReport;
import dev.nicholaskoldys.multiuserscheduler.service.DatabaseConnection;

/**
 *
 * @author nicho
 */
public class UserReportDAOImpl implements ReportDAO<UserReport> {
    
    private final String COUNT_APPOINTMENTS_PER_USER_MONTH =
            "SELECT user.userName AS Consultant,\n"
            + " count(appointment.appointmentId) AS 'Count',\n"
            + " month(appointment.start) AS Month\n"
            + "FROM appointment\n"
            + "inner join user ON user.userId = appointment.userId\n"
            + "group by user.userName, Month";
    
    private static final UserReportDAOImpl instance = new UserReportDAOImpl();
    
    
    /**
     * 
     * @return 
     */
    public static UserReportDAOImpl getInstance() {
        return instance;
    }
    

    @Override
    public List<UserReport> queryReport() {
        
        try (PreparedStatement countStatement = DatabaseConnection.getDatabaseConnection().prepareStatement(COUNT_APPOINTMENTS_PER_USER_MONTH);
                ResultSet results = countStatement.executeQuery()) {
            
            List<UserReport> reportList = new ArrayList<>();
            
            while(results.next()) {
                
                UserReport count = new UserReport(
                        // TODO HSQLDB uses count long, need to change if mysql
                        Long.valueOf(results.getLong("Count")).intValue(),
                        Month.of(results.getInt("Month")),
                        results.getString("Consultant")
                );
                reportList.add(count);
            }
            return reportList;
            
        } catch (SQLException ex) {
            System.out.println("SQL QUERY FAILURE : countALLTypes "
                    + "appointment " + ex.getMessage());
        }
        return null;
    }
}

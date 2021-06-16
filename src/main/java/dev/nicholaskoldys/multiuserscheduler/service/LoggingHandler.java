package dev.nicholaskoldys.multiuserscheduler.service;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import dev.nicholaskoldys.multiuserscheduler.model.AppointmentCalendar;

/**
 *
 * @author nicho
 */
public class LoggingHandler {
    
    File logFile;
    String LOG_NAME = "log.txt";
    FileWriter writer;
    BufferedWriter buffer;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssa z")
            .withZone(ZoneId.of("UTC"));
    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssa zzzz")
            .withZone(ZoneId.systemDefault());
    
    private static final LoggingHandler instance = new LoggingHandler();
    
    private LoggingHandler() {
        File logDir = new File("log");
        Boolean isDirExist = false;
        if(!logDir.exists()) {
            isDirExist = logDir.mkdirs();
        } else {
            isDirExist = true;
        }

        if(isDirExist) {
            this.logFile = new File(Paths.get(logDir + "/" + LOG_NAME).toString());
        } else {
            this.logFile = new File(Paths.get(LOG_NAME).toString());
            System.out.println(
                    "Failed to create logging folder. Please see " + LOG_NAME + ", for all logs.");

        }
    }

    public static LoggingHandler getInstance() {
        
        return instance;
    }
    
    
    public void test(){
        
        try {
            if(!logFile.exists()) {
                logFile.createNewFile();
            }
            
            write(ConvertTimeService.toUTC(LocalDateTime.now()).format(formatter)
                + " : Application Launch\n" );
            
        } catch (FileAlreadyExistsException ex) {
            System.out.println("Log failed to create new file");
        } catch (IOException ex) {
            System.out.println(ex + "\nLog failed to open");
        }
    }
    
    
    public Boolean check() {
        
        return logFile.exists();
    }
    
    
    private void openLog() {
        
        try {
            writer = new FileWriter(logFile, true);
            buffer = new BufferedWriter(writer);
        } catch (IOException ex) {
            
            System.out.println("Log failed to open");
            writer = null;
            buffer = null;
        }
    }
    
    
    private void closeLog() {
        
        try {
            if(buffer != null) {
                buffer.close();
                if(writer != null) {
                    writer.close();
                }
            }
        } catch (IOException ex) {
            System.out.println("Log failed to close");
        }
    }
    
    private void write(String text) {
        if (check()) {
            try {
                openLog();
                buffer.append(text);
            } catch (IOException ex) {
                System.out.println("Log failed to write");
            } finally {
                closeLog();
            }
        }
    }
    
    
    /*
    * 
    */
    public void userSignIn() {
        
        StringBuilder text = new StringBuilder();
            text.append(ConvertTimeService.toUTC(
                    LocalDateTime.now()).format(formatter));
            text.append(" : User : ");
            text.append(AppointmentCalendar.getCurrentUser().getUserName());
            text.append(" signed in at - ");
            text.append(LocalDateTime.now().format(formatter2
            ));
            text.append(" - Local Time.\n");
        write(text.toString());
    }
    
    
    /*
    * 
    */
    public void userSignOut() {

        StringBuilder text = new StringBuilder();
            text.append(ConvertTimeService.toUTC(
                    LocalDateTime.now()).format(formatter));
            text.append(" : User : ");
            text.append(AppointmentCalendar.getCurrentUser().getUserName());
            text.append(" signed out at - ");
            text.append(LocalDateTime.now().format(formatter2));
            text.append(" - Local Time.\n");
        write(text.toString());
    }
    
    
    /*
    * 
    */
    public void userSignInAttempt(String userName) {

        StringBuilder text = new StringBuilder();
            text.append(ConvertTimeService.toUTC(
                    LocalDateTime.now()).format(formatter));
            text.append(" : There was an unsuccessful login attempt to : ");
            text.append(userName);
            text.append("\n");
        write(text.toString());
    }
    
    
    /*
    * 
    */
    public void userRecordAdd() {

        StringBuilder text = new StringBuilder();
            text.append(ConvertTimeService.toUTC(
                    LocalDateTime.now()).format(formatter));
            text.append(" : User : ");
            text.append(AppointmentCalendar.getCurrentUser().getUserName());
            text.append(" added record : ");
            text.append("toStringMethod");
            text.append("\n");
        write(text.toString());
    }
    
    
    /*
    * 
    */
    public void userRecordChange() {

        StringBuilder text = new StringBuilder();
            text.append(ConvertTimeService.toUTC(
                    LocalDateTime.now()).format(formatter));
            text.append(" : User : ");
            text.append(AppointmentCalendar.getCurrentUser().getUserName());
            text.append(" changed record : ");
            text.append("toStringMethod");
            text.append("\n");
        write(text.toString());
    }
    
    
    /*
    * 
    */
    public void userRecordDelete() {

        StringBuilder text = new StringBuilder();
            text.append(ConvertTimeService.toUTC(
                    LocalDateTime.now()).format(formatter));
            text.append(" : User : ");
            text.append(AppointmentCalendar.getCurrentUser().getUserName());
            text.append(" removed record : ");
            text.append("toStringMethod");
            text.append("\n");
        write(text.toString());
    }
    
    /**
     * 
     */
    public void userCloseApp() {
        
        StringBuilder text = new StringBuilder();
            text.append(ConvertTimeService.toUTC(
                    LocalDateTime.now()).format(formatter));
            text.append(" : Application Closed\n");
        write(text.toString());
    }
}

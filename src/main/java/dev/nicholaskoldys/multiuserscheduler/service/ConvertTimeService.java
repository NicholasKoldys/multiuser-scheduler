package dev.nicholaskoldys.multiuserscheduler.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 *
 * @author nicho
 */
public class ConvertTimeService {
    
    
    public static LocalDateTime toUTC(LocalDateTime time) {
        
        ZonedDateTime universalTime = ZonedDateTime.of(time, ZoneId.systemDefault());
        
        return universalTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }
    
    public static Timestamp toMySQLDB(LocalDateTime time) {
        
        return Timestamp.valueOf(toUTC(time));
    }
    
    public static LocalDateTime fromUTC(LocalDateTime time) {
        
        ZonedDateTime timeInUtc = ZonedDateTime.of(time, ZoneId.of("UTC"));
        
        return timeInUtc.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }
}

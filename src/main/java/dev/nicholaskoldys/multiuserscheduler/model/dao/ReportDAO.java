package dev.nicholaskoldys.multiuserscheduler.model.dao;

import java.util.List;

/**
 * 
 * @author nicho
 * @param <T> 
 */
public interface ReportDAO<T> {
    
    abstract List<T> queryReport();
}

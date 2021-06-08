package dev.nicholaskoldys.multiuserscheduler.model.dao;

import java.util.List;
import dev.nicholaskoldys.multiuserscheduler.model.User;

/**
 *
 * @author nicho
 */
public interface UserDAO {
    
    List<User> getAllUsers();
    User getUser(int userId);
    Boolean create(User user);
    Boolean update(User user);
    Boolean delete(User user);
}

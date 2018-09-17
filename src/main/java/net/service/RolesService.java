package net.service;

import net.model.User;
import java.util.Map;

public interface RolesService {

    Map<String, Boolean> getAllRoles();
    Map<String, Boolean> getAllRoles(User user);
    Map<User, Map<String, Boolean>> getAllRolesAllUsers(Iterable<User> users);

    void setUserRoles(User user, String[] userRoles);
}

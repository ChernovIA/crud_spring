package net.service;

import net.dao.userDAO.UsersDAO;
import net.model.Role;
import net.model.RolesTypes;
import net.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ServiceHelper {

    private static UsersDAO usersDAO;

    @Autowired
    public void setUsersDAO(UsersDAO usersDAO){
        ServiceHelper.usersDAO = usersDAO;
    }
    public static void addTestUsers()  {

        Set<Role> roles = new HashSet<>();
        Role admin = new Role();
        admin.setType(RolesTypes.ADMIN);
        roles.add(admin);

        User uds1 = new User("Admin", "admin","Ilya Chernov", roles);
        User uds2 = new User("Moderator", "mod");
        User uds3 = new User("JavaProger", "java");

        usersDAO.addUser(uds1);
        usersDAO.addUser(uds2);
        usersDAO.addUser(uds3);
    }

}

package net.service;

import net.dao.userDAO.UsersDAO;
import net.model.Roles;
import net.model.User;

public class ServiceHelper {

    private UsersDAO usersDAO;

    public void addTestUsers()  {
        User uds1 = new User("Admin", "admin","Ilya Chernov", Roles.ADMIN);
        User uds2 = new User("Moderator", "mod");
        User uds3 = new User("JavaProger", "java");

        usersDAO.addUser(uds1);
        usersDAO.addUser(uds2);
        usersDAO.addUser(uds3);
    }


}

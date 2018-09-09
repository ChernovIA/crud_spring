package net.service;

import net.dao.userDAO.UsersDAO;
import net.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@DependsOn("serviceHelper")
public class UserServiceImp implements UserService {

    private UsersDAO usersDAO;

    @Autowired
    public void setUsersDAO(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    public User getUser(long id) {
        return usersDAO.get(id);
    }

    public User getUser(String login) {
        return usersDAO.get(login);
    }

    public void addUser(User user) {
        usersDAO.addUser(user);
    }

    public void deleteUser(long id) {
        usersDAO.deleteUser(id);
    }

    public void upDateUser(User user) {
        usersDAO.upDateUser(user);
    }

    public List<User> getUsersDataTable() {
        return usersDAO.getTable();
    }

    @PostConstruct
    public void createUsers(){
        ServiceHelper.addTestUsers();
    }
}

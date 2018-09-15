package net.service;

import net.dao.userDAO.UsersDAO;
import net.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private UsersDAO usersDAO;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersDAO.addUser(user);
    }

    public void deleteUser(long id) {
        usersDAO.deleteUser(id);
    }

    public void upDateUser(User user) {
        User current = usersDAO.get(user.getId());
        if (!current.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        usersDAO.upDateUser(user);
    }

    public List<User> getUsersDataTable() {
        return usersDAO.getTable();
    }

}

package net.service;

import net.model.User;


public interface UserService {

    User getUser(long id);

    User getUser(String login);

    void addUser(User user);

    void deleteUser(long id);

    void upDateUser(User user);

    Iterable<User> getUsersDataTable();
}

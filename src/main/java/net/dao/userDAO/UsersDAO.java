package net.dao.userDAO;

import net.model.User;

import java.util.List;

public interface UsersDAO {

    User get(long id);

    User get(String login);

    List<User> getTable();

    void addUser(User user);

    void upDateUser(User user);

    void deleteUser(long id);
}

package net.service;

import net.dao.userDAO.UserRepository;
import net.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    private UserRepository repository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public User getUser(long id) {
        return repository.findOne(id);
    }

    public User getUser(String login) {
        return repository.findByLogin(login);
    }

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    public void deleteUser(long id) {
        repository.delete(id);
    }

    public void upDateUser(User user) {
        User current = getUser(user.getId());
        if (!current.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        repository.save(user);
    }

    public Iterable<User> getUsersDataTable() {
        //return repository.findByRole(RolesTypes.USER);
        return repository.findAll();
    }

}

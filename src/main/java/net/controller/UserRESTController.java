package net.controller;

import net.model.User;
import net.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserRESTController {

    private final UserService userService;

    @Autowired
    public UserRESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public Iterable<User> getUsers(){

        return userService.getUsersDataTable();
    }

    @GetMapping("{id}")
    public User getUsers(@PathVariable("id") long id){

        return userService.getUser(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user){
        userService.addUser(user);
        return user;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") long id, @RequestBody User user){
        User userBase = userService.getUser(id);

        if (userBase == null){
            return;
        }

        if (user.getName() != null) {
            userBase.setName(user.getName());
        }
        if (user.getPassword() != null) {
            userBase.setRoles(user.getRoles());
        }
        userService.upDateUser(userBase);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deUser(@PathVariable long id){
        userService.deleteUser(id);
    }
}

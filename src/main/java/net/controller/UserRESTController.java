package net.controller;

import net.model.User;
import net.service.RolesService;
import net.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserRESTController {

    private final UserService userService;
    private final RolesService rolesService;

    @Autowired
    public UserRESTController(RolesService rolesService, UserService userService) {
        this.rolesService = rolesService;
        this.userService = userService;
    }

    @GetMapping("all")
    public Iterable<User> getUsers(){

        return userService.getUsersDataTable();
    }

    @GetMapping("roles")
    public Map<String, Boolean> getRoles(){

        return rolesService.getAllRoles();
    }

    @GetMapping("roles/{id}")
    public Map<String, Boolean> getRoles(@PathVariable("id") long id){

        return rolesService.getAllRoles(userService.getUser(id));
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") long id){

        return userService.getUser(id);
    }

//    @GetMapping("/login/{login}")
//    public User getUser(@PathVariable("login") String login){
//
//        return userService.getUser(login);
//    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user){
        userService.addUser(user);
        return user;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public User update(@PathVariable("id") long id, @RequestBody User user){
        User userBase = userService.getUser(id);

        if (userBase == null){
            return null;
        }

        if (user.getName() != null) {
            userBase.setName(user.getName());
        }
        if (user.getPassword() != null) {
            userBase.setRoles(user.getRoles());
        }
        userService.upDateUser(userBase);
        return userBase;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deUser(@PathVariable long id){
        userService.deleteUser(id);
    }
}

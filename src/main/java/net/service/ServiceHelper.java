package net.service;

import net.model.Role;
import net.model.RolesTypes;
import net.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class ServiceHelper {

    private UserService userService;

    @Autowired
    public ServiceHelper(UserService userService) {
        this.userService = userService;
    }


    public void addTestUsers()  {

        Set<Role> roles = new HashSet<>();
        Role admin = new Role();
        admin.setType(RolesTypes.ADMIN);
        roles.add(admin);

        User uds1 = new User("Admin", "admin","Ilya Chernov", roles);
        User uds2 = new User("Moderator", "mod");
        User uds3 = new User("JavaProger", "java");

        userService.addUser(uds1);
        userService.addUser(uds2);
        userService.addUser(uds3);
    }

    @PostConstruct
    public void createUsers(){
        addTestUsers();
    }
}

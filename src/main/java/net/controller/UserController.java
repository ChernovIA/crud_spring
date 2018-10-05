package net.controller;

import net.model.User;
import net.model.oAuth2.AccessToken;
import net.service.RolesService;
import net.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Controller
public class UserController {

    private UserService userService;
    private final RolesService rolesService;

    //Client ID  74511676955-q4ba6phik8m3uk6t1uu496nie4vdod61.apps.googleusercontent.com
    //Secret key ZQmkT14-G9oe6bfA01j6HB08

    @Autowired
    public UserController(RolesService rolesService, UserService userService) {
        this.rolesService = rolesService;
        this.userService = userService;
    }

    @GetMapping(value = {"/"})
    public String welcomePage() {
        return "redirect:/login";
    }


    @GetMapping(value = "/administrator/usersList")
    public String listUserListGet(Model model) {

        Iterable<User> users = userService.getUsersDataTable();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        model.addAttribute("roles",rolesService.getAllRoles());
        model.addAttribute("usersRoles",rolesService.getAllRolesAllUsers(users));

        return "usersList";

    }

    @PostMapping(value = "/administrator/addUser")
    public String addUserPost(@ModelAttribute("user") User user, @RequestParam("userRoles") String[] userRoles) {

        rolesService.setUserRoles(user, userRoles);
        userService.addUser(user);

        return "redirect:/administrator/usersList";

    }

    @RequestMapping(value = "/administrator/deleteUser", method = RequestMethod.GET)
    public String deleteUser(@RequestParam("id") int id) {

        userService.deleteUser(id);
        return "redirect:/administrator/usersList";

    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/administrator/editUser", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("user") User user, @RequestParam("userRoles") String[] userRoles) {

        rolesService.setUserRoles(user, userRoles);
        userService.upDateUser(user);

        return "redirect:/administrator/usersList";

    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {

        return "profilePage";

    }

}

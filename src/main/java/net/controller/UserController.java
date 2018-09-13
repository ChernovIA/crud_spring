package net.controller;

import net.model.User;
import net.service.RolesService;
import net.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("roles")
public class UserController {

    private UserService userService;
    private final RolesService rolesService;

    @Autowired
    public UserController(RolesService rolesService, UserService userService) {
        this.rolesService = rolesService;
        this.userService = userService;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String welcomePage() {
        return "redirect:/profile";
    }

    @RequestMapping(value = "/administrator/usersList", method = RequestMethod.GET)
    public String listUserListGet(Model model) {

        model.addAttribute("users", userService.getUsersDataTable());

        return "usersList";

    }

    @RequestMapping(value = "/administrator/usersList", method = RequestMethod.POST)
    public String listUserListPost(Model model) {

        model.addAttribute("users", userService.getUsersDataTable());

        return "usersList";

    }

    @RequestMapping(value = "/administrator/addUser", method = RequestMethod.GET)
    public String addUserGet(Model model) {

        model.addAttribute(new User());
        model.addAttribute("roles",rolesService.getAllRoles());

        return "addUserPage";

    }

    @RequestMapping(value = "/administrator/addUser", method = RequestMethod.POST)
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

    @RequestMapping(value = "/administrator/editUser", method = RequestMethod.GET)
    public String editUser(@RequestParam("id") int id, Model model) {

        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roles",rolesService.getAllRoles(user));

        return "editUserPage";

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

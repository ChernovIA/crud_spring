package net.controller;

import net.model.Roles;
import net.model.RolesTypes;
import net.model.User;
import net.service.RolesService;
import net.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserServiceImp(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String welcomePage() {
        return "redirect:/usersList";
    }

    @RequestMapping(value = "/usersList", method = RequestMethod.GET)
    public String listUserList(Model model) {

        model.addAttribute("users", userService.getUsersDataTable());

        return "usersList";

    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUserGet(Model model) {

        model.addAttribute(new User());
        model.addAttribute("roles",RolesService.getAllRoles());

        return "addUserPage";

    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUserPost(@ModelAttribute("user") User user, @RequestParam("userRoles") String[] userRoles) {

        RolesService.setUserRoles(user, userRoles);
        userService.addUser(user);

        return "redirect:/usersList";

    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser(@RequestParam("id") int id) {

        userService.deleteUser(id);
        return "redirect:/usersList";

    }

    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public String editUser(@RequestParam("id") int id, Model model) {

        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roles",RolesService.getAllRoles(user));

        return "editUserPage";

    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("user") User user, @RequestParam("userRoles") String[] userRoles) {

        RolesService.setUserRoles(user, userRoles);
        userService.upDateUser(user);

        return "redirect:/usersList";

    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {

        return "redirect:/usersList";

    }

}

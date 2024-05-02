package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.handleGetAllUsers();
        System.out.println(arrUsers);
        return "hello";
    }

    @GetMapping("/admin/user/create")
    public String getAddUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String handleCreateUser(Model model, @ModelAttribute("newUser") User hoidanit) {
        System.out.println(">>> Run here " + hoidanit);
        this.userService.handleSaveUser(hoidanit);
        return "hello";
    }

}

package vn.hoidanit.laptopshop.controller.admin;

import jakarta.servlet.ServletContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;

    public UserController(UserService userService, UploadService uploadService) {
        this.userService = userService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = userService.handleGetAllUsers();
        model.addAttribute("users", users);
        return "admin/user/show";
    }

    @GetMapping("/admin/user/detail/{id}")
    public String getUserDetailPage(@PathVariable long id, Model model) {
        User user = userService.handleGetUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String handleCreateUser(Model model, @ModelAttribute("newUser") User hoidanit,
                                   @RequestParam("hoidanitFile") MultipartFile file) {
        String avatar = this.uploadService.handleSaveUpLoadFile(file, "avatar");
        System.out.println("Check avatar: " + avatar);
//        this.userService.handleSaveUser(hoidanit);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(@PathVariable long id, Model model) {
        User currentUser = userService.handleGetUserById(id);
        model.addAttribute("user", currentUser);
        return "/admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String handleUpdateUser(Model model, @ModelAttribute("user") User user) {
        User currentUser = userService.handleGetUserById(user.getId());
        if (currentUser != null) {
            currentUser.setFullName(user.getFullName());
            currentUser.setAddress(user.getAddress());
            currentUser.setPhone(user.getPhone());

            this.userService.handleSaveUser(currentUser);
        }

        return "redirect:/admin/user";
    }

    @GetMapping("admin/user/delete/{id}")
    public String getDeleteUserPage(@PathVariable long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "/admin/user/delete";
    }

    @PostMapping("admin/user/delete")
    public String handleDeleteUserPage(@ModelAttribute("newUser") User user, Model model) {
        this.userService.deleteUser(user.getId());
        return "redirect:/admin/user";
    }
}

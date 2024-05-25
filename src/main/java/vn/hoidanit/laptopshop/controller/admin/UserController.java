package vn.hoidanit.laptopshop.controller.admin;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          UploadService uploadService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;

        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
            // else => page = 1
        } catch (Exception e) {
            // page = 1
            // Todo: handle exception
        }

        Pageable pageable = PageRequest.of(page - 1, 2);
        Page<User> users = userService.handleFetchAllUsers(pageable);
        List<User> userList = users.getContent();

        model.addAttribute("users", userList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());

        return "admin/user/show";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String handleCreateUser(Model model, @ModelAttribute("newUser") @Valid User hoidanit,
                                   BindingResult newUserBindingResult,
                                   @RequestParam("hoidanitFile") MultipartFile file) {
//        System.out.println(file.getOriginalFilename());
        // Validate
//        List<FieldError> errors = newUserBindingResult.getFieldErrors();
//        for (FieldError error : errors) {
//            System.out.println(">>>> " + error.getField() + " - " + error.getDefaultMessage());
//        }

        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }

        String avatar = this.uploadService.handleSaveUpLoadFile(file, "avatar");
//        System.out.println(avatar);
        String hashPassword = this.passwordEncoder.encode(hoidanit.getPassword());

        // Re-assign with new values
        hoidanit.setAvatar(avatar);
        hoidanit.setPassword(hashPassword);
        hoidanit.setRole(userService.handleFetchRoleByName(hoidanit.getRole().getName()));

        // Save user
        this.userService.handleSaveUser(hoidanit);

        // Redirect to user table
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/detail/{id}")
    public String getUserDetailPage(@PathVariable long id, Model model) {
        User user = userService.handleFetchUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }


    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(@PathVariable long id, Model model) {
        User currentUser = userService.handleFetchUserById(id);
        model.addAttribute("user", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String handleUpdateUser(Model model, @ModelAttribute("user") User user) {
        User currentUser = userService.handleFetchUserById(user.getId());
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
        return "admin/user/delete";
    }

    @PostMapping("admin/user/delete")
    public String handleDeleteUserPage(@ModelAttribute("newUser") User user, Model model) {
        this.userService.handleDeleteUser(user.getId());
        return "redirect:/admin/user";
    }
}

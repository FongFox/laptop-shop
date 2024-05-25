package vn.hoidanit.laptopshop.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

import java.util.List;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder, OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String getHomePage(Model model, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> products = productService.handleFetchAllProducts(pageable);
        List<Product> productList = products.getContent();

        model.addAttribute("products", productList);
        HttpSession session = request.getSession(false);
//        System.out.println(">>> Check session fullName: " + session.getAttribute("fullName"));
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
                                 BindingResult bindingResult) {
        // Validate
//        List<FieldError> errors = bindingResult.getFieldErrors();
//        for (FieldError error : errors) {
//            System.out.println(">>>> " + error.getField() + " - " + error.getDefaultMessage());
//        }

        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }

        User user = this.userService.registerDTOtoUser(registerDTO);
//        System.out.println(user);

        String hashPassword = this.passwordEncoder.encode(user.getPassword());

        // Re-assign with new values
        user.setPassword(hashPassword);
        user.setRole(this.userService.handleFetchRoleByName("USER"));

        // Save user
        this.userService.handleSaveUser(user);

        // Redirect to user table
        return "redirect:/login";

//        return "client/auth/register";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
//        model.addAttribute("loginUser", new RegisterDTO());
        return "client/auth/login";
    }

    @PostMapping("/login")
    public String handleLogin(Model model) {
//        model.addAttribute("loginUser", new RegisterDTO());
        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getAccessDenyPage() {
        return "client/auth/access-deny";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User currentUser = new User();
        long userId = (long) session.getAttribute("id");
        currentUser.setId(userId);

        List<Order> orders = this.orderService.handleFetchOrderByUser(currentUser);
        model.addAttribute("orders", orders);

        return "client/cart/order-history";
    }
}

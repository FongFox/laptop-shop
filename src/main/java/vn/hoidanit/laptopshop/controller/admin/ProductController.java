package vn.hoidanit.laptopshop.controller.admin;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProductPage(Model model) {
        List<Product> products = productService.handleFetchAllProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String handleCreateUser(Model model, @ModelAttribute("newProduct") @Valid Product hoidanit,
                                   BindingResult newProductBindingResult,
                                   @RequestParam("hoidanitFile") MultipartFile file) {
//        System.out.println(file.getOriginalFilename());
        // Validate
//        List<FieldError> errors = newProductBindingResult.getFieldErrors();
//        for (FieldError error : errors) {
//            System.out.println(">>>> " + error.getField() + " - " + error.getDefaultMessage());
//        }
//      Return this form if errors appeared while validating
        if (newProductBindingResult.hasErrors()) {
            return "/admin/product/create";
        }

        String proImg = this.uploadService.handleSaveUpLoadFile(file, "product");
//        System.out.println(avatar);

        // Re-assign with new values
        hoidanit.setImage(proImg);

        // Save user
        this.productService.handleSaveProduct(hoidanit);

        // Redirect to user table
        return "redirect:/admin/product";
    }

}

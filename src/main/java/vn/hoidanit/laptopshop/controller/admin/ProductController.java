package vn.hoidanit.laptopshop.controller.admin;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.hoidanit.laptopshop.domain.Product;
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
    public String getProductPage(Model model, @RequestParam("page") int page) {
        Pageable pageable = PageRequest.of(page - 1, 2);
        Page<Product> products = productService.handleFetchAllProducts(pageable);
        List<Product> productList = products.getContent();

        model.addAttribute("products", productList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());

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

    @GetMapping("/admin/product/detail/{id}")
    public String getProductDetailPage(@PathVariable long id, Model model) {
        Product product = productService.handleFetchProductById(id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(@PathVariable long id, Model model) {
        Product currentProduct = productService.handleFetchProductById(id);
//        System.out.println(currentProduct.get());
        model.addAttribute("product", currentProduct);
        return "/admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String handleUpdateUser(Model model,
                                   @ModelAttribute("product") @Valid Product product,
                                   BindingResult newProductBindingResult,
                                   @RequestParam("hoidanitFile") MultipartFile file) {
//        System.out.println(product.toString());
//        System.out.println(product.getId());

        //      Return this form if errors appeared while validating
        if (newProductBindingResult.hasErrors()) {
            return "/admin/product/update";
        }

        Product currentProduct = productService.handleFetchProductById(product.getId());
//        System.out.println(currentProduct.toString());
        if (currentProduct != null) {
//          Save product source image if file isn't empty
            if (!file.isEmpty()) {
                String img = this.uploadService.handleSaveUpLoadFile(file, "product");
                currentProduct.setImage(img);
            }

            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setShortDesc(product.getShortDesc());
            currentProduct.setDetailDesc(product.getDetailDesc());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setFactory(product.getFactory());
            currentProduct.setTarget(product.getTarget());

            this.productService.handleSaveProduct(currentProduct);
        }

        return "redirect:/admin/product";
    }

    @GetMapping("admin/product/delete/{id}")
    public String getDeleteProductPage(@PathVariable long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "/admin/product/delete";
    }

    @PostMapping("admin/product/delete")
    public String handleDeleteUserPage(@ModelAttribute("newProduct") Product product, Model model) {
        this.productService.handleDeleteProduct(product.getId());
        return "redirect:/admin/product";
    }

}

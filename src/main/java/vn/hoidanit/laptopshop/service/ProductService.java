package vn.hoidanit.laptopshop.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository,
                          CartRepository cartRepository,
                          CartDetailRepository cartDetailRepository,
                          UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    public List<Product> handleFetchAllProducts() {
        return this.productRepository.findAll();
    }

    public Product handleFetchProductById(long id) {
        return this.productRepository.findById(id).get();
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public void handleDeleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public void handeSaveProductToCart(String email, long productId, HttpSession session) {
        User user = this.userService.handleFetchUserByEmail(email);
        if (user != null) {
//          Check if user have cart already ? If not -> create new cart
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
//              Create new cart
                Cart tempCart = new Cart();
                tempCart.setUser(user);
                tempCart.setSum(0);
                cart = this.cartRepository.save(tempCart);
            }
//          Find product by id
            Optional<Product> tempProduct = this.productRepository.findById(productId);
            if (tempProduct.isPresent()) {
                Product product = tempProduct.get();
//              Check if product had been added to cart ?
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);
                if (oldDetail == null) {
//                  Save cart_detail
                    CartDetail newDetail = new CartDetail();
                    newDetail.setCart(cart);
                    newDetail.setProduct(product);
                    newDetail.setPrice(product.getPrice());
                    newDetail.setQuantity(1);
                    this.cartDetailRepository.save(newDetail);
//                  Update card sum
                    int sum = cart.getSum() + 1;
                    cart.setSum(sum);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", sum);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldDetail);
                }
            }
        }
    }

    public Cart handleFetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }
}

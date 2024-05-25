package vn.hoidanit.laptopshop.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hoidanit.laptopshop.domain.*;
import vn.hoidanit.laptopshop.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository,
                          CartRepository cartRepository,
                          CartDetailsRepository cartDetailsRepository,
                          UserService userService, OrderRepository orderRepository,
                          OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailsRepository = cartDetailsRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Page<Product> handleFetchAllProducts(Pageable pageable) {
        return this.productRepository.findAll(pageable);
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

    public void handeSaveProductToCart(String email, long productId, HttpSession session, long quantity) {
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
                CartDetail oldDetail = this.cartDetailsRepository.findByCartAndProduct(cart, product);
                if (oldDetail == null) {
//                  Save cart_detail
                    CartDetail newDetail = new CartDetail();
                    newDetail.setCart(cart);
                    newDetail.setProduct(product);
                    newDetail.setPrice(product.getPrice());
                    newDetail.setQuantity(quantity);
                    this.cartDetailsRepository.save(newDetail);
//                  Update card sum
                    int sum = cart.getSum() + 1;
                    cart.setSum(sum);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", sum);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailsRepository.save(oldDetail);
                }
            }
        }
    }

    public Cart handleFetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleDeleteProductFromCart(long cardDetailId, HttpSession session) {
        int defaultCartSum = 0;
        Optional<CartDetail> dbCardDetail = this.cartDetailsRepository.findById(cardDetailId);
        if (dbCardDetail.isPresent()) {
            CartDetail tempCartDetail = dbCardDetail.get();
            Cart dbCart = this.cartRepository.findByCartDetails(tempCartDetail);
            int currentDBCartSum = dbCart.getSum();
//        System.out.println(dbCart.toString());
//      Delete card details from database
            this.cartDetailsRepository.deleteById(cardDetailId);
            if (currentDBCartSum == 1) {
                this.cartRepository.delete(dbCart);
                session.setAttribute("sum", defaultCartSum);
            } else {
                currentDBCartSum -= 1;
                dbCart.setSum(currentDBCartSum);
                session.setAttribute("sum", currentDBCartSum);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailsRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailsRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(
            User user, HttpSession session,
            String receiverName, String receiverAddress, String receiverPhone) {

        // step 1: get cart by user
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();

            if (cartDetails != null) {

                // create order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += cd.getPrice();
                }
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);

                // create orderDetail

                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());

                    this.orderDetailRepository.save(orderDetail);
                }

                // step 2: delete cart_detail and cart
                for (CartDetail cd : cartDetails) {
                    this.cartDetailsRepository.deleteById(cd.getId());
                }

                this.cartRepository.deleteById(cart.getId());

                // step 3 : update session
                session.setAttribute("sum", 0);
            }
        }

    }
}

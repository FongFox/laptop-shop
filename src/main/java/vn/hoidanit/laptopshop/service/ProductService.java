package vn.hoidanit.laptopshop.service;

import org.springframework.stereotype.Service;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> handleFetchAllProducts() {
        return this.productRepository.findAll();
    }

    public Product handleFetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public void handleDeleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

}

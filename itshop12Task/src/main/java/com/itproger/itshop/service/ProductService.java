package com.itproger.itshop.service;

import com.itproger.itshop.entity.Product;
import com.itproger.itshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProductsByAuthorId(Long authorId) {
        return productRepository.findByAuthorId(authorId);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

package com.ms.Ecomm.service;

import com.ms.Ecomm.model.Product;
import com.ms.Ecomm.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public List<Product> getAllProducts() {

        // here productRepo call findAll() method and will bring product details from DB.
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {

        // here productRepo will bring product Details by id & return .  if id found,
        // if product id not found then return null
        return productRepository.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {

        productRepository.deleteById(id);
    }
}

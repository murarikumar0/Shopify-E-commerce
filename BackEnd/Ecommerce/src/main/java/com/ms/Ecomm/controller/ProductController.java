package com.ms.Ecomm.controller;

import com.ms.Ecomm.model.Product;
import com.ms.Ecomm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
   private ProductService productService;

    @GetMapping()
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts();
    }

    // API for getting product by Id.
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id)
    {
        return productService.getProductById(id);
    }

    // API for adding product
    @PostMapping
    public Product addProduct(@RequestBody Product product)
    {
        // by the hlp of productService we are calling addProduct() method
        // and we are passing product into addProduct().
        return productService.addProduct(product);
    }

    // API for delete Product by id.
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
    }
}

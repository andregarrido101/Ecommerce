package com.example.ecommerce.product_service.controller;

import com.example.ecommerce.product_service.service.ProductService;
import com.example.ecommerce.product_service.response.ProductResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController() {
        this.productService = productService;
    }

    @GetMapping("/products")
    public @ResponseBody List<ProductResponse> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public @ResponseBody ProductResponse getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    // Test
    @GetMapping("/test")
    public String test() {
        return "Product Service is up and running!";
    }
}

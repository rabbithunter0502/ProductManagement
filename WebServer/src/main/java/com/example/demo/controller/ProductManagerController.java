package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductManagerController {

    private ProductService productService;

    @Autowired
    public ProductManagerController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> findAllProduct() {
        List<Product> products = productService.findAllProduct();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductById(
            @PathVariable("id") Integer id) {
        Optional<Product> product = productService.findById(id);

        if (!product.isPresent()) {
            return new ResponseEntity<>(product.get(),
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/products",
            method = RequestMethod.POST)
    public ResponseEntity<Product> createProduct(
            @RequestBody Product product,
            UriComponentsBuilder builder) {
        productService.save(product);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/products/{id}")
                .buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/sell/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Product> sellProduct(
            @PathVariable("id") Integer id,
            @RequestBody Product product) {
        Optional<Product> currentProduct = productService
                .findById(id);

        if (!currentProduct.isPresent()) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        Double quantity = currentProduct.get().getQuantity() - product.getQuantity();
        currentProduct.get().setQuantity(quantity);

        productService.save(currentProduct.get());
        return new ResponseEntity<>(currentProduct.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteProduct(
            @PathVariable("id") Integer id) {
        Optional<Product> product = productService.findById(id);
        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.remove(product.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

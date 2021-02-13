package com.example.demo.web.api;

import com.example.demo.domain.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductContaroller {

    private ProductRepository productRepository;

    public ProductContaroller(@Autowired ProductRepository productRepository) {

        this.productRepository = productRepository;
    }


    @GetMapping()
    public ResponseEntity<Iterable<Product>> getProducts() {
        Iterable<Product> all = productRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);

        return ResponseEntity.of(productOptional);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        if (product.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        product = productRepository.save(product);

        return ResponseEntity.ok(product);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {

        if (product.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        product = productRepository.save(product);

        return ResponseEntity.ok(product);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Integer id) {

        productRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

}

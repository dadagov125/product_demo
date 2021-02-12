package com.example.demo.web.api;

import com.example.demo.domain.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.web.api.vm.CreateProductVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductContaroller {

    @Autowired
    ProductRepository productRepository;


    @GetMapping()
    public ResponseEntity<Iterable<Product>> getProducts() {
        Iterable<Product> all = productRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Product> getProduct(@PathVariable("code") String code) {
        Optional<Product> productOptional = productRepository.findById(code);

        return ResponseEntity.of(productOptional);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductVM model) {
        Product product = new Product();
        product.setName(model.name);
        product.setPrice(model.price);
        product.setArticle(model.article);
        product.setProduced(model.produced);
        product.setCount(model.count);

        product = productRepository.save(product);

        return ResponseEntity.ok(product);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {

        if (product.getCode() == null) {
            return ResponseEntity.badRequest().build();
        }
        product = productRepository.save(product);

        return ResponseEntity.ok(product);
    }


    @DeleteMapping("/{code}")
    public ResponseEntity deleteProduct(@PathVariable("code") String code) {

        productRepository.deleteById(code);

        return ResponseEntity.ok().build();
    }

}

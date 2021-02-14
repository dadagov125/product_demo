package com.example.demo.web.api;

import com.example.demo.service.ProductService;
import com.example.demo.service.dto.CreateProductDTO;
import com.example.demo.service.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductContaroller {


    private ProductService productService;

    public ProductContaroller(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping()
    public ResponseEntity<Page<ProductDTO>> getProducts(Pageable pageable) {
        Page<ProductDTO> page = productService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Integer id) {
        Optional<ProductDTO> productOptional = productService.findOneById(id);
        return ResponseEntity.of(productOptional);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ProductDTO> getProductByCode(@PathVariable("code") String code) {
        Optional<ProductDTO> productOptional = productService.findOneByCode(code);
        return ResponseEntity.of(productOptional);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody CreateProductDTO dto) {
        ProductDTO productDTO = productService.create(dto);
        return ResponseEntity.ok(productDTO);
    }

    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO dto) {
        ProductDTO productDTO = productService.update(dto);
        return ResponseEntity.ok(productDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Integer id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

}

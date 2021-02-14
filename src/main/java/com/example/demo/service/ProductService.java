package com.example.demo.service;

import com.example.demo.service.dto.CreateProductDTO;
import com.example.demo.service.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    ProductDTO create(CreateProductDTO dto);

    ProductDTO update(ProductDTO dto);

    Optional<ProductDTO> findOneById(Integer id);

    Optional<ProductDTO> findOneByCode(String code);

    Page<ProductDTO> findAll(Pageable pageable);

    void delete(Integer id);
}

package com.example.demo.service.impl;

import com.example.demo.domain.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.dto.CreateProductDTO;
import com.example.demo.service.dto.ProductDTO;
import com.example.demo.service.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Override
    public ProductDTO create(CreateProductDTO dto) {
        Product product = productMapper.toEntity(dto);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDTO update(ProductDTO dto) {
        Product product = productMapper.toEntity(dto);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public Optional<ProductDTO> findOneById(Integer id) {
        return productRepository.findById(id).map(productMapper::toDto);
    }

    @Override
    public Optional<ProductDTO> findOneByCode(String code) {
        return productRepository.findByCode(code).map(productMapper::toDto);
    }


    @Override
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}

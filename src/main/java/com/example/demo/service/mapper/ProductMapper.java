package com.example.demo.service.mapper;

import com.example.demo.domain.entity.Product;
import com.example.demo.service.dto.CreateProductDTO;
import com.example.demo.service.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    Product toEntity(CreateProductDTO dto);
}

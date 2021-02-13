package com.example.demo.service.mapper;

import com.example.demo.domain.entity.Product;
import com.example.demo.service.dto.CreateProductDTO;
import com.example.demo.service.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(target = "id", ignore = true)
    Product toEntity(CreateProductDTO dto);
}

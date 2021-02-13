package com.example.demo;

import com.example.demo.domain.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.web.api.ProductContaroller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
class ProductControllerTests {

    private final String contentType = "application/json";
    private final String api = "/api/product";
    @Autowired
    ObjectMapper mapper;
    @Autowired
    ProductRepository productRepository;
    Product product;
    private MockMvc productRestMvcMock;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @BeforeEach
    public void beforeEach() {

        ProductContaroller productContaroller = new ProductContaroller(productRepository);
        productRestMvcMock = MockMvcBuilders.standaloneSetup(productContaroller)
                .setMessageConverters(jacksonMessageConverter)
                .build();

        product = new Product();
        String code = UUID.randomUUID().toString().replace("-", "");
        product.setCode(code);
        product.setArticle("123");
        product.setName("test product");
        product.setCount(1);
        product.setPrice(BigDecimal.valueOf(100.00));
        Date produced = new Date();
        product.setProduced(produced);

    }

    @AfterEach
    public void afterEach() {
        productRepository.deleteAll();
    }


    private Product spawnProduct() {
        return productRepository.save(product);
    }

    @Test
    public void tryCreateProductWithEmptyContent() throws Exception {
        productRestMvcMock.perform(post(api)
                .contentType(contentType)
                .content(""))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void tryCreateProductWithLongCode() throws Exception {
        product.setCode(product.getCode() + "123");
        productRestMvcMock.perform(post(api)
                .contentType(contentType)
                .content(mapper.writeValueAsString(product)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createProduct() throws Exception {
        productRestMvcMock.perform(post(api)
                .contentType(contentType)
                .content(mapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.code").value(product.getCode()))
                .andExpect(jsonPath("$.article").value(product.getArticle()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.count").value(product.getCount()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.produced").value(product.getProduced()));

        List<Product> createdList = (List<Product>) productRepository.findAll();
        assertThat(createdList).hasSize(1);
        Product createdProduct = createdList.get(0);
        assertThat(createdProduct.getCode()).isEqualTo(product.getCode());
        assertThat(createdProduct.getArticle()).isEqualTo(product.getArticle());
        assertThat(createdProduct.getCount()).isEqualTo(product.getCount());
        assertThat(createdProduct.getName()).isEqualTo(product.getName());
        assertThat(createdProduct.getPrice()).isEqualTo(product.getPrice());
        assertThat(createdProduct.getProduced().toInstant()).isEqualTo(product.getProduced().toInstant());
    }


    @Test
    public void tryUpdateProductWithLongCode() throws Exception {
        spawnProduct();
        product.setCode(product.getCode() + "111");
        productRestMvcMock.perform(put(api)
                .contentType(contentType)
                .content(mapper.writeValueAsString(product)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateProduct() throws Exception {
        spawnProduct();
        product.setName("changed");
        product.setCount(9);
        product.setCode("code2");
        productRestMvcMock.perform(put(api)
                .contentType(contentType)
                .content(mapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(mapper.writeValueAsString(product)));

        List<Product> createdList = (List<Product>) productRepository.findAll();
        assertThat(createdList).hasSize(1);
        Product createdProduct = createdList.get(0);
        assertThat(createdProduct.getCode()).isEqualTo(product.getCode());
        assertThat(createdProduct.getArticle()).isEqualTo(product.getArticle());
        assertThat(createdProduct.getCount()).isEqualTo(product.getCount());
        assertThat(createdProduct.getName()).isEqualTo(product.getName());
        assertThat(createdProduct.getPrice()).isEqualTo(product.getPrice());
        assertThat(createdProduct.getProduced().toInstant()).isEqualTo(product.getProduced().toInstant());
    }

    @Test
    public void getOneNotFoundTest() throws Exception {
        productRestMvcMock.perform(get(api + "/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getOneTest() throws Exception {
        spawnProduct();
        productRestMvcMock.perform(get(api + "/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(mapper.writeValueAsString(product)));
    }

    @Test
    public void getAllIsEmptyTest() throws Exception {
        productRestMvcMock.perform(get(api))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(mapper.writeValueAsString(Collections.EMPTY_LIST)));
    }

    @Test
    public void getAllTest() throws Exception {
        spawnProduct();
        productRestMvcMock.perform(get(api))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(mapper.writeValueAsString(Collections.singletonList(product))));
    }


}

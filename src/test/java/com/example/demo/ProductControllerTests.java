package com.example.demo;

import com.example.demo.domain.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.CreateProductDTO;
import com.example.demo.service.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

    ProductDTO productDTO;

    CreateProductDTO createProductDTO;

    private MockMvc productRestMvcMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void beforeEach() {
        productRestMvcMock =
                MockMvcBuilders.webAppContextSetup(webApplicationContext)
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


        productDTO = new ProductDTO();
        productDTO.setCode(product.getCode());
        productDTO.setArticle(product.getArticle());
        productDTO.setName(product.getName());
        productDTO.setCount(product.getCount());
        productDTO.setPrice(product.getPrice());
        productDTO.setProduced(product.getProduced());

        createProductDTO = new CreateProductDTO();
        createProductDTO.setCode(product.getCode());
        createProductDTO.setArticle(product.getArticle());
        createProductDTO.setName(product.getName());
        createProductDTO.setCount(product.getCount());
        createProductDTO.setPrice(product.getPrice());
        createProductDTO.setProduced(product.getProduced());

    }

    @AfterEach
    public void afterEach() {
        productRepository.deleteAll();
    }


    private void spawnProduct() {
        productRepository.save(product);
        productDTO.setId(product.getId());
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
        createProductDTO.setCode(createProductDTO.getCode() + "123");
        productRestMvcMock.perform(post(api)
                .contentType(contentType)
                .content(mapper.writeValueAsString(createProductDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createProduct() throws Exception {
        productRestMvcMock.perform(post(api)
                .contentType(contentType)
                .content(mapper.writeValueAsString(createProductDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.code").value(createProductDTO.getCode()))
                .andExpect(jsonPath("$.article").value(createProductDTO.getArticle()))
                .andExpect(jsonPath("$.name").value(createProductDTO.getName()))
                .andExpect(jsonPath("$.count").value(createProductDTO.getCount()))
                .andExpect(jsonPath("$.price").value(createProductDTO.getPrice()))
                .andExpect(jsonPath("$.produced").value(createProductDTO.getProduced()));

        List<Product> createdList = (List<Product>) productRepository.findAll();
        assertThat(createdList).hasSize(1);
        Product createdProduct = createdList.get(0);
        assertThat(createdProduct.getCode()).isEqualTo(createProductDTO.getCode());
        assertThat(createdProduct.getArticle()).isEqualTo(createProductDTO.getArticle());
        assertThat(createdProduct.getCount()).isEqualTo(createProductDTO.getCount());
        assertThat(createdProduct.getName()).isEqualTo(createProductDTO.getName());
        assertThat(createdProduct.getPrice()).isEqualTo(createProductDTO.getPrice());
        assertThat(createdProduct.getProduced().toInstant()).isEqualTo(createProductDTO.getProduced().toInstant());
    }


    @Test
    public void tryUpdateProductWithLongCode() throws Exception {
        spawnProduct();
        productDTO.setCode(productDTO.getCode() + "111");
        productRestMvcMock.perform(put(api)
                .contentType(contentType)
                .content(mapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateProduct() throws Exception {
        spawnProduct();

        productDTO.setName("changed");
        productDTO.setCount(9);
        productDTO.setCode("code2");
        productRestMvcMock.perform(put(api)
                .contentType(contentType)
                .content(mapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(mapper.writeValueAsString(productDTO)));

        List<Product> createdList = (List<Product>) productRepository.findAll();
        assertThat(createdList).hasSize(1);
        Product createdProduct = createdList.get(0);
        assertThat(createdProduct.getCode()).isEqualTo(productDTO.getCode());
        assertThat(createdProduct.getArticle()).isEqualTo(productDTO.getArticle());
        assertThat(createdProduct.getCount()).isEqualTo(productDTO.getCount());
        assertThat(createdProduct.getName()).isEqualTo(productDTO.getName());
        assertThat(createdProduct.getPrice()).isEqualTo(productDTO.getPrice());
        assertThat(createdProduct.getProduced().toInstant()).isEqualTo(productDTO.getProduced().toInstant());
    }

    @Test
    public void getOneNotFoundTest() throws Exception {
        productRestMvcMock.perform(get(api + "/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getOneTest() throws Exception {
        spawnProduct();
        productRestMvcMock.perform(get(api + "/" + productDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(mapper.writeValueAsString(productDTO)));
    }

    @Test
    public void getOneByCodeNotFoundTest() throws Exception {
        productRestMvcMock.perform(get(api + "/code/12312312312"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getOneByCodeTest() throws Exception {
        spawnProduct();
        productRestMvcMock.perform(get(api + "/code/" + productDTO.getCode()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(mapper.writeValueAsString(productDTO)));
    }

    @Test
    public void getAllIsEmptyTest() throws Exception {

        productRestMvcMock.perform(get(api))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(mapper.writeValueAsString(
                        new PageImpl(Collections.EMPTY_LIST, PageRequest.ofSize(20), 0)
                )));
    }

    @Test
    public void getAllTest() throws Exception {
        spawnProduct();
        productRestMvcMock.perform(get(api))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string(mapper.writeValueAsString(
                        new PageImpl(Collections.singletonList(productDTO), PageRequest.ofSize(20), 0)

                )));
    }


}

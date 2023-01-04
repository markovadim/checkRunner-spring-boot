package by.markov.checkrunnerspringboot.controllers;

import by.markov.checkrunnerspringboot.CheckRunnerSpringBootApplication;
import by.markov.checkrunnerspringboot.mapping.ProductMapper;
import by.markov.checkrunnerspringboot.services.products.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CheckRunnerSpringBootApplication checkRunnerSpringBootApplication;
    @MockBean
    private ProductMapper productMapper;
    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("Product Controller Test")
    public void shouldReturnStatusOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
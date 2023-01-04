package by.markov.checkrunnerspringboot.controllers;

import by.markov.checkrunnerspringboot.CheckRunnerSpringBootApplication;
import by.markov.checkrunnerspringboot.services.commandline.CommandLineArgumentsParser;
import by.markov.checkrunnerspringboot.services.orders.OrderManager;
import by.markov.checkrunnerspringboot.services.payment.CheckManager;
import by.markov.checkrunnerspringboot.services.payment.CheckPrinter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(ShopManagerController.class)
class ShopManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommandLineArgumentsParser commandLineArgumentsParser;
    @MockBean
    private OrderManager orderManager;
    @MockBean
    private CheckManager checkManager;
    @MockBean
    private CheckPrinter checkPrinter;

    @Test
    @DisplayName("ShopManager Controller Test")
    void shouldReturnStatusOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/check")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
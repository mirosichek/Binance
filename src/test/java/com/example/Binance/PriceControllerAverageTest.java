package com.example.Binance;

import com.example.Binance.controller.PriceController;
import com.example.Binance.service.BinanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
public class PriceControllerAverageTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BinanceService binanceService;

    @Test
    void shouldReturnBadRequestWhenFromAfterTo() throws Exception {

        mockMvc.perform(get("/api/v1/prices/average")
                        .param("from", "2026-11-05T12:00:00Z")
                        .param("to", "2026-11-05T10:00:00Z"))
                .andExpect(status().isBadRequest());
    }
}

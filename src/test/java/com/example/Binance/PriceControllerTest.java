package com.example.Binance;

import com.example.Binance.DTO.TradingPairPageResponse;
import com.example.Binance.controller.PriceController;
import com.example.Binance.service.BinanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PriceController.class)
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BinanceService binanceService;

    @Test
    void returns400WhenFromAfterTo() throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                        .param("from", "2024-01-02T00:00:00Z")
                        .param("to", "2024-01-01T00:00:00Z"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returns400WhenPageNegative() throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                        .param("from", "2024-01-01T00:00:00Z")
                        .param("to", "2024-01-02T00:00:00Z")
                        .param("page", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returns400WhenSizeZero() throws Exception {
        mockMvc.perform(get("/api/v1/prices")
                        .param("from", "2024-01-01T00:00:00Z")
                        .param("to", "2024-01-02T00:00:00Z")
                        .param("size", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returns200AndDelegatesToService() throws Exception {
        when(binanceService.getPrices(any(), any(), eq(PageRequest.of(0, 20))))
                .thenReturn(new TradingPairPageResponse(List.of(), 0, 20, 0,false));

        mockMvc.perform(get("/api/v1/prices")
                        .param("from", "2024-01-01T00:00:00Z")
                        .param("to", "2024-01-02T00:00:00Z"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20));
    }
}
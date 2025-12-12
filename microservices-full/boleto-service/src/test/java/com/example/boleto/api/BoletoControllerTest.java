package com.example.boleto.api;

import com.example.boleto.api.dto.BoletoRequestDto;
import com.example.boleto.api.dto.BoletoResponseDto;
import com.example.boleto.domain.BoletoStatus;
import com.example.boleto.service.BoletoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoletoController.class)
class BoletoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoletoService boletoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveRetornar201AoCriarBoleto() throws Exception {
        BoletoRequestDto dto = new BoletoRequestDto(
                1L,
                new BigDecimal("100.00"),
                LocalDate.now().plusDays(1),
                null,
                null,
                BoletoStatus.PENDENTE
        );
        BoletoResponseDto response = new BoletoResponseDto(
                1L,
                1L,
                new BigDecimal("100.00"),
                LocalDate.now().plusDays(1),
                null,
                null,
                BoletoStatus.PENDENTE
        );
        Mockito.when(boletoService.create(dto)).thenReturn(response);
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/boletos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}

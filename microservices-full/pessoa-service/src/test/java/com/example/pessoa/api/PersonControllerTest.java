package com.example.pessoa.api;

import com.example.pessoa.api.dto.PersonRequestDto;
import com.example.pessoa.api.dto.PersonResponseDto;
import com.example.pessoa.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveRetornar201AoCriarPessoa() throws Exception {
        PersonRequestDto dto = new PersonRequestDto(
                "João",
                "12345678901",
                LocalDate.of(1990, 1, 1),
                null,
                null,
                null,
                null,
                null
        );
        PersonResponseDto response = new PersonResponseDto(
                1L,
                "João",
                "12345678901",
                LocalDate.of(1990, 1, 1),
                null,
                null,
                null,
                null,
                null,
                List.of()
        );
        Mockito.when(personService.create(dto)).thenReturn(response);
        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}

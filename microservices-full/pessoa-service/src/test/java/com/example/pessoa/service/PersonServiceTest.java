package com.example.pessoa.service;

import com.example.pessoa.api.dto.PersonRequestDto;
import com.example.pessoa.domain.BusinessException;
import com.example.pessoa.domain.PersonRepository;
import com.example.pessoa.infrastructure.client.BoletoClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private BoletoClient boletoClient;

    @InjectMocks
    private PersonService personService;

    @Test
    void deveLancarExcecaoQuandoIdadeMenorQue18() {
        PersonRequestDto dto = new PersonRequestDto(
                "João",
                "12345678901",
                LocalDate.now().minusYears(17),
                null,
                null,
                null,
                null,
                null
        );
        assertThrows(BusinessException.class, () -> personService.create(dto));
    }

    @Test
    void deveLancarExcecaoQuandoCpfDuplicado() {
        PersonRequestDto dto = new PersonRequestDto(
                "João",
                "12345678901",
                LocalDate.now().minusYears(20),
                null,
                null,
                null,
                null,
                null
        );
        when(personRepository.existsByCpf("12345678901")).thenReturn(true);
        assertThrows(BusinessException.class, () -> personService.create(dto));
    }
}

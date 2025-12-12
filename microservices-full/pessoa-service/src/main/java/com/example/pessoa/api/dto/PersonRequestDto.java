package com.example.pessoa.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record PersonRequestDto(
        @NotBlank String nome,
        @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
        @NotNull LocalDate dataNascimento,
        String cep,
        String logradouro,
        String bairro,
        String uf,
        String cidade
) {
}

package com.example.pessoa.api.dto;

import java.time.LocalDate;
import java.util.List;

public record PersonResponseDto(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String cep,
        String logradouro,
        String bairro,
        String uf,
        String cidade,
        List<BoletoDto> boletos
) {
}

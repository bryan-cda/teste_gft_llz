package com.example.pessoa.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BoletoDto(
        Long id,
        Long pessoaId,
        BigDecimal valorDocumento,
        LocalDate dataVencimento,
        BigDecimal valorPago,
        LocalDate dataPagamento,
        String status
) {
}

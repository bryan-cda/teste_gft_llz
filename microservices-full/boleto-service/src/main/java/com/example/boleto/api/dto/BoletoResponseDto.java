package com.example.boleto.api.dto;

import com.example.boleto.domain.BoletoStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record BoletoResponseDto(
        Long id,
        Long pessoaId,
        BigDecimal valorDocumento,
        LocalDate dataVencimento,
        BigDecimal valorPago,
        LocalDate dataPagamento,
        BoletoStatus status
) {
}

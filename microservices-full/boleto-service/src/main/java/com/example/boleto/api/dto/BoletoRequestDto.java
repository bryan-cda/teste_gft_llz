package com.example.boleto.api.dto;

import com.example.boleto.domain.BoletoStatus;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record BoletoRequestDto(
        @NotNull Long pessoaId,
        @NotNull BigDecimal valorDocumento,
        @NotNull LocalDate dataVencimento,
        BigDecimal valorPago,
        LocalDate dataPagamento,
        @NotNull BoletoStatus status
) {
}

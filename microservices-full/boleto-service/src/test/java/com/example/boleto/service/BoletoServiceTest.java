package com.example.boleto.service;

import com.example.boleto.api.dto.BoletoPagamentoDto;
import com.example.boleto.api.dto.BoletoRequestDto;
import com.example.boleto.domain.Boleto;
import com.example.boleto.domain.BoletoRepository;
import com.example.boleto.domain.BoletoStatus;
import com.example.boleto.domain.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoletoServiceTest {

    @Mock
    private BoletoRepository boletoRepository;

    @InjectMocks
    private BoletoService boletoService;

    @Test
    void deveLancarExcecaoQuandoValorDocumentoZero() {
        BoletoRequestDto dto = new BoletoRequestDto(
                1L,
                BigDecimal.ZERO,
                LocalDate.now().plusDays(1),
                null,
                null,
                BoletoStatus.PENDENTE
        );
        assertThrows(BusinessException.class, () -> boletoService.create(dto));
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoComValorDiferente() {
        Boleto boleto = new Boleto();
        boleto.setId(1L);
        boleto.setValorDocumento(new BigDecimal("100.00"));
        boleto.setStatus(BoletoStatus.PENDENTE);
        when(boletoRepository.findById(1L)).thenReturn(Optional.of(boleto));
        BoletoPagamentoDto pagamento = new BoletoPagamentoDto(new BigDecimal("90.00"), LocalDate.now());
        assertThrows(BusinessException.class, () -> boletoService.pagar(1L, pagamento));
    }
}

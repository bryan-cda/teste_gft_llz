package com.example.boleto.service;

import com.example.boleto.api.dto.BoletoPagamentoDto;
import com.example.boleto.api.dto.BoletoRequestDto;
import com.example.boleto.api.dto.BoletoResponseDto;
import com.example.boleto.domain.Boleto;
import com.example.boleto.domain.BoletoRepository;
import com.example.boleto.domain.BoletoStatus;
import com.example.boleto.domain.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BoletoService {

    private final BoletoRepository boletoRepository;

    public BoletoService(BoletoRepository boletoRepository) {
        this.boletoRepository = boletoRepository;
    }

    @Transactional
    public BoletoResponseDto create(BoletoRequestDto dto) {
        validateNovaDataVencimento(dto.dataVencimento());
        validateValorDocumento(dto.valorDocumento());
        Boleto boleto = toEntity(dto);
        boleto = boletoRepository.save(boleto);
        return toResponse(boleto);
    }

    @Transactional(readOnly = true)
    public List<BoletoResponseDto> findByPessoaIdOrderByDataVencimento(Long pessoaId) {
        return boletoRepository.findByPessoaIdOrderByDataVencimentoAsc(pessoaId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BoletoResponseDto findById(Long id) {
        Boleto boleto = boletoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Boleto não encontrado"));
        return toResponse(boleto);
    }

    @Transactional
    public BoletoResponseDto pagar(Long id, BoletoPagamentoDto dto) {
        Boleto boleto = boletoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Boleto não encontrado"));
        if (boleto.getStatus() != BoletoStatus.PENDENTE) {
            throw new BusinessException("Pagamento permitido apenas para boletos pendentes");
        }
        if (boleto.getValorDocumento().compareTo(dto.valorPago()) != 0) {
            throw new BusinessException("Valor pago deve ser igual ao valor do documento");
        }
        if (!LocalDate.now().equals(dto.dataPagamento())) {
            throw new BusinessException("Data de pagamento deve ser a data atual");
        }
        boleto.setValorPago(dto.valorPago());
        boleto.setDataPagamento(dto.dataPagamento());
        boleto.setStatus(BoletoStatus.PAGO);
        boleto = boletoRepository.save(boleto);
        return toResponse(boleto);
    }

    @Transactional
    public void delete(Long id) {
        Boleto boleto = boletoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Boleto não encontrado"));
        if (boleto.getStatus() == BoletoStatus.PAGO) {
            throw new BusinessException("Não é permitido excluir boleto pago");
        }
        boletoRepository.delete(boleto);
    }

    @Transactional
    public void marcarVencidos() {
        LocalDate hoje = LocalDate.now();
        List<Boleto> boletos = boletoRepository.findByStatusAndDataVencimentoBefore(BoletoStatus.PENDENTE, hoje);
        boletos.forEach(b -> b.setStatus(BoletoStatus.VENCIDO));
    }

    private void validateNovaDataVencimento(LocalDate dataVencimento) {
        if (dataVencimento.isBefore(LocalDate.now())) {
            throw new BusinessException("Data de vencimento deve ser igual ou posterior à data atual");
        }
    }

    private void validateValorDocumento(BigDecimal valorDocumento) {
        if (valorDocumento == null || valorDocumento.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Valor do documento deve ser maior que zero");
        }
    }

    private Boleto toEntity(BoletoRequestDto dto) {
        Boleto b = new Boleto();
        b.setPessoaId(dto.pessoaId());
        b.setValorDocumento(dto.valorDocumento());
        b.setDataVencimento(dto.dataVencimento());
        b.setValorPago(dto.valorPago());
        b.setDataPagamento(dto.dataPagamento());
        b.setStatus(dto.status());
        return b;
    }

    private BoletoResponseDto toResponse(Boleto b) {
        return new BoletoResponseDto(
                b.getId(),
                b.getPessoaId(),
                b.getValorDocumento(),
                b.getDataVencimento(),
                b.getValorPago(),
                b.getDataPagamento(),
                b.getStatus()
        );
    }
}

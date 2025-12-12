package com.example.boleto.api;

import com.example.boleto.api.dto.BoletoPagamentoDto;
import com.example.boleto.api.dto.BoletoRequestDto;
import com.example.boleto.api.dto.BoletoResponseDto;
import com.example.boleto.service.BoletoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/boletos")
public class BoletoController {

    private final BoletoService boletoService;

    public BoletoController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @PostMapping
    public ResponseEntity<BoletoResponseDto> create(@Valid @RequestBody BoletoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boletoService.create(dto));
    }

    @GetMapping("/pessoa/{pessoaId}")
    public List<BoletoResponseDto> findByPessoa(@PathVariable Long pessoaId) {
        return boletoService.findByPessoaIdOrderByDataVencimento(pessoaId);
    }

    @GetMapping("/{id}")
    public BoletoResponseDto findById(@PathVariable Long id) {
        return boletoService.findById(id);
    }

    @PostMapping("/{id}/pagamento")
    public BoletoResponseDto pagar(@PathVariable Long id, @Valid @RequestBody BoletoPagamentoDto dto) {
        return boletoService.pagar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boletoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

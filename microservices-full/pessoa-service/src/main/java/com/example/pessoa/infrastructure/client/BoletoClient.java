package com.example.pessoa.infrastructure.client;

import com.example.pessoa.api.dto.BoletoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "boleto-service", url = "${boleto.service.url}")
public interface BoletoClient {

    @GetMapping("/boletos/pessoa/{pessoaId}")
    List<BoletoDto> buscarPorPessoaId(@PathVariable("pessoaId") Long pessoaId);
}

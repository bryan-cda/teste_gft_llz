package com.example.boleto.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BoletoVencimentoScheduler {

    private final BoletoService boletoService;

    public BoletoVencimentoScheduler(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void atualizarBoletosVencidos() {
        boletoService.marcarVencidos();
    }
}

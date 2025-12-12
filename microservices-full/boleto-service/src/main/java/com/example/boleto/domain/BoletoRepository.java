package com.example.boleto.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface BoletoRepository extends JpaRepository<Boleto, Long> {

    List<Boleto> findByPessoaIdOrderByDataVencimentoAsc(Long pessoaId);

    List<Boleto> findByStatusAndDataVencimentoBefore(BoletoStatus status, LocalDate data);
}

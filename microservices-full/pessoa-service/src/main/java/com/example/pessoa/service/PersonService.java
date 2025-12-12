package com.example.pessoa.service;

import com.example.pessoa.api.dto.BoletoDto;
import com.example.pessoa.api.dto.PersonRequestDto;
import com.example.pessoa.api.dto.PersonResponseDto;
import com.example.pessoa.domain.BusinessException;
import com.example.pessoa.domain.Person;
import com.example.pessoa.domain.PersonRepository;
import com.example.pessoa.infrastructure.client.BoletoClient;
import jakarta.persistence.EntityNotFoundException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final BoletoClient boletoClient;

    public PersonService(PersonRepository personRepository, BoletoClient boletoClient) {
        this.personRepository = personRepository;
        this.boletoClient = boletoClient;
    }

    @Transactional
    public PersonResponseDto create(PersonRequestDto dto) {
        validateCpf(dto.cpf(), null);
        validateAge(dto.dataNascimento());
        Person person = toEntity(dto);
        person = personRepository.save(person);
        return toResponse(person, List.of());
    }

    @Transactional(readOnly = true)
    public List<PersonResponseDto> findAll() {
        return personRepository.findAll().stream()
                .map(p -> toResponse(p, List.of()))
                .toList();
    }

    @Transactional(readOnly = true)
    public PersonResponseDto findByIdWithBoletos(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
        List<BoletoDto> boletos = boletoClient.buscarPorPessoaId(id);
        return toResponse(person, boletos);
    }

    @Transactional
    public PersonResponseDto update(Long id, PersonRequestDto dto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
        validateCpf(dto.cpf(), id);
        validateAge(dto.dataNascimento());
        person.setNome(dto.nome());
        person.setCpf(dto.cpf());
        person.setDataNascimento(dto.dataNascimento());
        person.setCep(dto.cep());
        person.setLogradouro(dto.logradouro());
        person.setBairro(dto.bairro());
        person.setUf(dto.uf());
        person.setCidade(dto.cidade());
        person = personRepository.save(person);
        return toResponse(person, List.of());
    }

    @Transactional
    public void delete(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
        List<BoletoDto> boletos = boletoClient.buscarPorPessoaId(id);
        boolean temPendente = boletos.stream().anyMatch(b -> "PENDENTE".equalsIgnoreCase(b.status()));
        if (temPendente) {
            throw new BusinessException("Não é permitido excluir pessoa com boletos pendentes");
        }
        personRepository.delete(person);
    }

    @Transactional(readOnly = true)
    public byte[] gerarRelatorioPessoas() {
        try {
            List<Person> pessoas = personRepository.findAll();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pessoas);
            Map<String, Object> params = new HashMap<>();
            InputStream jrxml = getClass().getResourceAsStream("/reports/pessoas.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxml);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, params, dataSource);
            return JasperExportManager.exportReportToPdf(print);
        } catch (JRException e) {
            throw new BusinessException("Erro ao gerar relatório de pessoas");
        }
    }

    private void validateCpf(String cpf, Long id) {
        if (!cpf.matches("\\d{11}")) {
            throw new BusinessException("CPF deve conter 11 dígitos numéricos");
        }
        if (id == null && personRepository.existsByCpf(cpf)) {
            throw new BusinessException("CPF já cadastrado");
        }
        if (id != null && personRepository.existsByCpfAndIdNot(cpf, id)) {
            throw new BusinessException("CPF já cadastrado");
        }
    }

    private void validateAge(LocalDate dataNascimento) {
        LocalDate hoje = LocalDate.now();
        Period period = Period.between(dataNascimento, hoje);
        if (period.getYears() < 18) {
            throw new BusinessException("Pessoa deve ter pelo menos 18 anos");
        }
    }

    private Person toEntity(PersonRequestDto dto) {
        Person p = new Person();
        p.setNome(dto.nome());
        p.setCpf(dto.cpf());
        p.setDataNascimento(dto.dataNascimento());
        p.setCep(dto.cep());
        p.setLogradouro(dto.logradouro());
        p.setBairro(dto.bairro());
        p.setUf(dto.uf());
        p.setCidade(dto.cidade());
        return p;
    }

    private PersonResponseDto toResponse(Person p, List<BoletoDto> boletos) {
        return new PersonResponseDto(
                p.getId(),
                p.getNome(),
                p.getCpf(),
                p.getDataNascimento(),
                p.getCep(),
                p.getLogradouro(),
                p.getBairro(),
                p.getUf(),
                p.getCidade(),
                boletos
        );
    }
}

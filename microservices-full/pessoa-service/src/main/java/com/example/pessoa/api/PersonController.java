package com.example.pessoa.api;

import com.example.pessoa.api.dto.PersonRequestDto;
import com.example.pessoa.api.dto.PersonResponseDto;
import com.example.pessoa.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonResponseDto> create(@Valid @RequestBody PersonRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.create(dto));
    }

    @GetMapping
    public List<PersonResponseDto> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public PersonResponseDto findByIdWithBoletos(@PathVariable Long id) {
        return personService.findByIdWithBoletos(id);
    }

    @PutMapping("/{id}")
    public PersonResponseDto update(@PathVariable Long id, @Valid @RequestBody PersonRequestDto dto) {
        return personService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio")
    public ResponseEntity<byte[]> gerarRelatorio() {
        byte[] pdf = personService.gerarRelatorioPessoas();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pessoas.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}

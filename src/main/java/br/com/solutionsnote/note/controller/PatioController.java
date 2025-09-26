package br.com.solutionsnote.note.controller;

import br.com.solutionsnote.note.model.Patio;
import br.com.solutionsnote.note.repository.PatioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patios")
public class PatioController {

    @Autowired
    private PatioRepository repository;

    @PostMapping
    public Patio salvar(@RequestBody @Valid Patio patio) {
        return repository.save(patio);
    }

    @GetMapping
    public List<Patio> listar() {
        return repository.findAll();
    }
}
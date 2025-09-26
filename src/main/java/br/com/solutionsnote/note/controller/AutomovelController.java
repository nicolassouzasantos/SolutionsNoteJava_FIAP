package br.com.solutionsnote.note.controller;

import br.com.solutionsnote.note.dto.AutomovelCreateDTO;
import br.com.solutionsnote.note.dto.AutomovelDTO;
import br.com.solutionsnote.note.service.AutomovelService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/automoveis")
public class AutomovelController {

    @Autowired
    private AutomovelService service;

    @PostMapping
    public AutomovelDTO salvar(@RequestBody @Valid AutomovelCreateDTO dto) {
        return service.salvar(dto);
    }

    @GetMapping
    public Page<AutomovelDTO> listar(
            @RequestParam(required = false) String tipo,
            @PageableDefault(size = 10, sort = "placa") Pageable pageable
    ) {
        return service.listar(tipo, pageable);
    }
}
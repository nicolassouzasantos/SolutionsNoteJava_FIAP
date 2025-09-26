package br.com.solutionsnote.note.dto;

public record AutomovelDTO(
        Long id,
        String placa,
        String chassi,
        String tipo,
        String cor,
        String localizacaoNoPatio,
        String comentarios,
        Long patioId
) {}

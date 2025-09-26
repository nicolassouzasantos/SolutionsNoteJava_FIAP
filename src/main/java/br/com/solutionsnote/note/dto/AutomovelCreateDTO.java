package br.com.solutionsnote.note.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AutomovelCreateDTO(
        @NotBlank String placa,
        @NotBlank String chassi,
        @NotBlank String tipo,
        String cor,
        String localizacaoNoPatio,
        String comentarios,
        @NotNull Long patioId
) {}
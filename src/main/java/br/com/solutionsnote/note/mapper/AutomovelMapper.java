package br.com.solutionsnote.note.mapper;

import br.com.solutionsnote.note.dto.AutomovelDTO;
import br.com.solutionsnote.note.model.Automovel;

public class AutomovelMapper {

    public static AutomovelDTO toDTO(Automovel a) {
        return new AutomovelDTO(
                a.getId(),
                a.getPlaca(),
                a.getChassi(),
                a.getTipo(),
                a.getCor(),
                a.getLocalizacaoNoPatio(),
                a.getComentarios(),
                a.getPatio().getId()
        );
    }
}
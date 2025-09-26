package br.com.solutionsnote.note.dto;

import br.com.solutionsnote.note.model.Automovel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO utilizado para trafegar os dados do formulário de criação/edição de automóveis
 * nas páginas Thymeleaf.
 */
public class AutomovelForm {

    @NotBlank(message = "Informe a placa")
    private String placa;

    @NotBlank(message = "Informe o chassi")
    private String chassi;

    @NotBlank(message = "Informe o tipo")
    private String tipo;

    private String cor;

    private String localizacaoNoPatio;

    private String comentarios;

    @NotNull(message = "Selecione um pátio")
    private Long patioId;

    public AutomovelForm() {
    }

    public AutomovelForm(String placa, String chassi, String tipo, String cor,
                          String localizacaoNoPatio, String comentarios, Long patioId) {
        this.placa = placa;
        this.chassi = chassi;
        this.tipo = tipo;
        this.cor = cor;
        this.localizacaoNoPatio = localizacaoNoPatio;
        this.comentarios = comentarios;
        this.patioId = patioId;
    }

    public static AutomovelForm fromEntity(Automovel automovel) {
        return new AutomovelForm(
                automovel.getPlaca(),
                automovel.getChassi(),
                automovel.getTipo(),
                automovel.getCor(),
                automovel.getLocalizacaoNoPatio(),
                automovel.getComentarios(),
                automovel.getPatio() != null ? automovel.getPatio().getId() : null
        );
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getLocalizacaoNoPatio() {
        return localizacaoNoPatio;
    }

    public void setLocalizacaoNoPatio(String localizacaoNoPatio) {
        this.localizacaoNoPatio = localizacaoNoPatio;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Long getPatioId() {
        return patioId;
    }

    public void setPatioId(Long patioId) {
        this.patioId = patioId;
    }
}

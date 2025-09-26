package br.com.solutionsnote.note.service;

import br.com.solutionsnote.note.dto.AutomovelCreateDTO;
import br.com.solutionsnote.note.dto.AutomovelDTO;
import br.com.solutionsnote.note.dto.AutomovelForm;
import br.com.solutionsnote.note.mapper.AutomovelMapper;
import br.com.solutionsnote.note.model.Automovel;
import br.com.solutionsnote.note.model.Patio;
import br.com.solutionsnote.note.repository.AutomovelRepository;
import br.com.solutionsnote.note.repository.PatioRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutomovelService {

    @Autowired
    private AutomovelRepository repository;

    @Autowired
    private PatioRepository patioRepository;

    public AutomovelDTO salvar(AutomovelCreateDTO dto) {
        Patio patio = patioRepository.findById(dto.patioId())
                .orElseThrow(() -> new EntityNotFoundException("Pátio não encontrado"));

        Automovel automovel = new Automovel();
        preencherDadosBasicos(automovel, dto.placa(), dto.chassi(), dto.tipo(), dto.cor(),
                dto.localizacaoNoPatio(), dto.comentarios(), patio);

        return AutomovelMapper.toDTO(repository.save(automovel));
    }

    public Page<AutomovelDTO> listar(String tipo, Pageable pageable) {
        Page<Automovel> page = tipo == null ?
                repository.findAll(pageable) :
                repository.findByTipoContainingIgnoreCase(tipo, pageable);

        return page.map(AutomovelMapper::toDTO);
    }

    public List<Automovel> listarTodos() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "placa"));
    }

    public Automovel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Automóvel não encontrado"));
    }

    public Automovel salvar(AutomovelForm form) {
        Patio patio = patioRepository.findById(form.getPatioId())
                .orElseThrow(() -> new EntityNotFoundException("Pátio não encontrado"));

        Automovel automovel = new Automovel();
        preencherDadosBasicos(automovel, form.getPlaca(), form.getChassi(), form.getTipo(),
                form.getCor(), form.getLocalizacaoNoPatio(), form.getComentarios(), patio);

        return repository.save(automovel);
    }

    public Automovel atualizar(Long id, AutomovelForm form) {
        Automovel existente = buscarPorId(id);
        Patio patio = patioRepository.findById(form.getPatioId())
                .orElseThrow(() -> new EntityNotFoundException("Pátio não encontrado"));

        preencherDadosBasicos(existente, form.getPlaca(), form.getChassi(), form.getTipo(),
                form.getCor(), form.getLocalizacaoNoPatio(), form.getComentarios(), patio);

        return repository.save(existente);
    }

    public void excluir(Long id) {
        Automovel existente = buscarPorId(id);
        repository.delete(existente);
    }

    private void preencherDadosBasicos(Automovel automovel, String placa, String chassi, String tipo,
                                       String cor, String localizacao, String comentarios, Patio patio) {
        automovel.setPlaca(placa);
        automovel.setChassi(chassi);
        automovel.setTipo(tipo);
        automovel.setCor(cor);
        automovel.setLocalizacaoNoPatio(localizacao);
        automovel.setComentarios(comentarios);
        automovel.setPatio(patio);
    }
}

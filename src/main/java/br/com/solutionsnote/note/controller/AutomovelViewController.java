package br.com.solutionsnote.note.controller;

import br.com.solutionsnote.note.dto.AutomovelForm;
import br.com.solutionsnote.note.model.Automovel;
import br.com.solutionsnote.note.repository.PatioRepository;
import br.com.solutionsnote.note.service.AutomovelService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/automoveis-ui")
public class AutomovelViewController {

    private final AutomovelService automovelService;
    private final PatioRepository patioRepository;

    public AutomovelViewController(AutomovelService automovelService, PatioRepository patioRepository) {
        this.automovelService = automovelService;
        this.patioRepository = patioRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("automoveis", automovelService.listarTodos());
        return "automoveis/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        if (!model.containsAttribute("automovelForm")) {
            model.addAttribute("automovelForm", new AutomovelForm());
        }
        prepararFormulario(model, false, null);
        return "automoveis/form";
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute("automovelForm") AutomovelForm form,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            prepararFormulario(model, false, null);
            return "automoveis/form";
        }

        automovelService.salvar(form);
        redirectAttributes.addFlashAttribute("mensagem", "Automóvel cadastrado com sucesso!");
        return "redirect:/automoveis-ui";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Automovel automovel = automovelService.buscarPorId(id);
            if (!model.containsAttribute("automovelForm")) {
                model.addAttribute("automovelForm", AutomovelForm.fromEntity(automovel));
            }
            prepararFormulario(model, true, id);
            return "automoveis/form";
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
            return "redirect:/automoveis-ui";
        }
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("automovelForm") AutomovelForm form,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            prepararFormulario(model, true, id);
            return "automoveis/form";
        }

        try {
            automovelService.atualizar(id, form);
            redirectAttributes.addFlashAttribute("mensagem", "Automóvel atualizado com sucesso!");
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        }
        return "redirect:/automoveis-ui";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            automovelService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Automóvel excluído com sucesso!");
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        }
        return "redirect:/automoveis-ui";
    }

    private void prepararFormulario(Model model, boolean edicao, Long id) {
        model.addAttribute("modoEdicao", edicao);
        model.addAttribute("automovelId", id);
        model.addAttribute("patios", patioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome")));
        model.addAttribute("tituloPagina", edicao ? "Editar automóvel" : "Novo automóvel");
    }
}

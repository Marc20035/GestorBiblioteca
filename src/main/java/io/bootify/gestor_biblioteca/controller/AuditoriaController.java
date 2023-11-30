package io.bootify.gestor_biblioteca.controller;

import io.bootify.gestor_biblioteca.model.AuditoriaDTO;
import io.bootify.gestor_biblioteca.service.AuditoriaService;
import io.bootify.gestor_biblioteca.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/auditorias")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(final AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("auditorias", auditoriaService.findAll());
        return "auditoria/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("auditoria") final AuditoriaDTO auditoriaDTO) {
        return "auditoria/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("auditoria") @Valid final AuditoriaDTO auditoriaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auditoria/add";
        }
        auditoriaService.create(auditoriaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("auditoria.create.success"));
        return "redirect:/auditorias";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("auditoria", auditoriaService.get(id));
        return "auditoria/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("auditoria") @Valid final AuditoriaDTO auditoriaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auditoria/edit";
        }
        auditoriaService.update(id, auditoriaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("auditoria.update.success"));
        return "redirect:/auditorias";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        auditoriaService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("auditoria.delete.success"));
        return "redirect:/auditorias";
    }

}

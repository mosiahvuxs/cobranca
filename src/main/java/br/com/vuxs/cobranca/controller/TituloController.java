package br.com.vuxs.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.vuxs.cobranca.model.StatusTitulo;
import br.com.vuxs.cobranca.model.Titulo;
import br.com.vuxs.cobranca.repository.Titulos;
import br.com.vuxs.cobranca.repository.filter.TituloFilter;
import br.com.vuxs.cobranca.service.TituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	@Autowired
	private TituloService tituloService;

	@Autowired
	private Titulos titulos;

	private static final String CADASTRO_VIEW = "cadastroTitulo";

	@RequestMapping("/novo")
	public ModelAndView novo() {

		ModelAndView modelAndView = new ModelAndView(CADASTRO_VIEW);
		modelAndView.addObject(new Titulo());

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes redirectAttributes) {

		if (errors.hasErrors()) {

			return CADASTRO_VIEW;
		}

		try {

			this.tituloService.salvar(titulo);

			redirectAttributes.addFlashAttribute("mensagem", "Título salvo com sucesso.");

			return "redirect:/titulos/novo";

		} catch (IllegalArgumentException e) {

			errors.rejectValue("dataVencimento", null, e.getMessage());

			return CADASTRO_VIEW;
		}

	}

	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro) {
		
		List<Titulo> todosTitulos = this.tituloService.filtrar(filtro);
		
		ModelAndView mv = new ModelAndView("pesquisaTitulos");
		mv.addObject("titulos", todosTitulos);
		return mv;
	}

	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {

		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);

		return mv;
	}

	@RequestMapping(value = "{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {

		this.tituloService.excluir(codigo);

		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");

		return "redirect:/titulos";
	}

	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		return this.tituloService.receber(codigo);
	}

	@ModelAttribute("listaStatus")
	public List<StatusTitulo> listaStatus() {

		return Arrays.asList(StatusTitulo.values());
	}

}

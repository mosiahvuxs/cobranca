package br.com.vuxs.cobranca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.vuxs.cobranca.model.Titulo;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	@RequestMapping("/novo")
	public String novo() {
		return "cadastroTitulo";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(Titulo titulo) {
		
		System.out.println(">>> " + titulo.getDescricao());
		
		return "cadastroTitulo";
	}
	
}

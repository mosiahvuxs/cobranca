package br.com.vuxs.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.vuxs.cobranca.model.StatusTitulo;
import br.com.vuxs.cobranca.model.Titulo;
import br.com.vuxs.cobranca.repository.Titulos;
import br.com.vuxs.cobranca.repository.filter.TituloFilter;

@Service
public class TituloService {

	@Autowired
	private Titulos titulos;

	public void salvar(Titulo titulo) {

		try {
			
			this.titulos.save(titulo);
			
		} catch (DataIntegrityViolationException e) {
			
			throw new IllegalArgumentException("Formato de data inválido.");
		}
	}

	public void excluir(Long codigo) {
		
		this.titulos.delete(codigo);
	}

	public String receber(Long codigo) {
		
		Titulo titulo = titulos.findOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter filtro) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		return titulos.findByDescricaoContaining(descricao);
	}

}

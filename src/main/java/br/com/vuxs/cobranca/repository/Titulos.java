package br.com.vuxs.cobranca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.vuxs.cobranca.model.Titulo;

public interface Titulos extends JpaRepository<Titulo, Long>{
	
	@Query("select t from Titulo t Order By t.dataVencimento")
    public List<Titulo> findOrderDataVencimento();
	
	public List<Titulo> findByDescricaoContaining(String descricao);

}

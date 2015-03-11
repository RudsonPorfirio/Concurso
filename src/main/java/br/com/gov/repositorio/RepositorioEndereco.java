package br.com.gov.repositorio;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.gov.model.Endereco;
import br.com.gov.model.Usuario;

public class RepositorioEndereco implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	
	
	public void CadastrarEndereco(Endereco usuario) {

		EntityTransaction trx = manager.getTransaction();
		trx.begin();
		manager.merge(usuario);
		trx.commit();

	}
	
	public Endereco porId(Long id) {
		return manager.find(Endereco.class, id);
	}

}

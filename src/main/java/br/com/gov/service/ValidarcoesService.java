package br.com.gov.service;


import java.io.Serializable;

import javax.inject.Inject;

import br.com.gov.model.Usuario;
import br.com.gov.repositorio.RepositorioUsuario;

public class ValidarcoesService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	RepositorioUsuario repositorioUsuario;
/**
	@Inject
	RepositorioUsuario repositorioUsuario;
	@Inject
	RepositorioMotorista repositorioMotorista;
	/**
	 * 
	 * @param Email
	 * 
	 * Verifica se O email ja esta cadastrado
	 * 
	 * @return
	 *
	
	
	
	public Boolean VerificaEmail(String email) {
		Usuario usuarioExistente = repositorioUsuario.porEmail(email);
		Motorista motoristaExistente = repositorioMotorista.porEmail(email);	
		
		if (usuarioExistente != null) {
			email();
			throw new NegocioException("Já existe um usuario com o email informado.")
			
			;
		}else if (motoristaExistente != null) {
			email();
			throw new NegocioException("Já existe um usuario com o email informado.");
		}else{
			return true;
		}
		
	}
	
	public void email(){
		
		
		FacesUtil.addInfoMessage("Já existe um usuario com o email informado.");
		
	}
	
	*/
	public Boolean VerificaCpf(String cpf) {
		Usuario usuarioExistente = repositorioUsuario.porEmail(cpf);
	//	Motorista motoristaExistente = repositorioMotorista.porEmail(cpf);	
		
		if (usuarioExistente != null) {
			throw new NegocioException("Já existe um usuario com o CPF informado.")
			
			;
		
		}else{
			return true;
		}
		
	}
	
	
}

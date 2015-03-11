package br.com.gov.bean;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.primefaces.event.FlowEvent;

import br.com.gov.model.Endereco;
import br.com.gov.model.Usuario;

@Named
@SessionScoped
public class ConsultarInscricaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuario usuario;

	@Inject
	private Endereco endereco;
	
	@CPF
	private String cpf;
	@NotBlank
	private String senha;

	private void limpar() {
		endereco = new Endereco();
		usuario = new Usuario();

	}

	public void salvar() {

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}

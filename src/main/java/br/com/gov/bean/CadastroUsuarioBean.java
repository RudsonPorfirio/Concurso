package br.com.gov.bean;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FlowEvent;

import br.com.gov.model.Endereco;
import br.com.gov.model.Usuario;
import br.com.gov.repositorio.RepositorioEndereco;
import br.com.gov.repositorio.RepositorioUsuario;
import br.com.gov.util.jsf.FacesUtil;
import br.com.gov.util.jsf.SessionUtil;

@Named
@SessionScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuario usuario;
	

	@Inject
	private Endereco endereco;
	@Inject
	private RepositorioUsuario repositorioUsuario;

	@Inject
	private RepositorioEndereco rEndereco;

	private Object tipoRg;
	private Object ufEmissor;
	private Object cargo;
	private Object estadoEndereco;
	private Object defespeciais;
	private Object defeVisual;
	private Object defeAuditiva;
	private Object defeFisica;

	public void limpar() {
		System.out.println("limpar ..........");
		endereco = new Endereco();
		usuario = new Usuario();
		tipoRg = new Object();
		ufEmissor = new Object();
		cargo = new Object();
		estadoEndereco = new Object();
		defespeciais = new Object();
		defeVisual = new Object();
		defeAuditiva = new Object();
		defeFisica = new Object();
	}

	public String confirmacao() {

		return "Boleto?faces-redirect=true";

	}

	public void jogarsession() {
		System.out.println("jogarsession....");

		SessionUtil.setParam("DadosUsuario", this.usuario);
	}

	public void preencher() {

		System.out.println("meto de preencher ............");

		usuario.setCargo((String) cargo);
		usuario.setTipo((String) tipoRg);
		usuario.setUF((String) ufEmissor);
		usuario.setDataCadastro(new Date());
		usuario.setEspeciais((String) defespeciais);
		usuario.setDefVisual((String) defeVisual);
		usuario.setDefAuditiva((String) defeAuditiva);
		usuario.setDefFisica((String) defeFisica);

		endereco.setEstado((String) estadoEndereco);

		usuario.setEndereco(endereco);

		if (usuario.getIniz() == null || usuario.getIniz().isEmpty()) {

			usuario.setSituacao("Pendente");

		} else {

			usuario.setSituacao("Isenção Pendente");

		}
	}

	public String salvar() {

		System.out.println("meto de salvar  ............");

		usuario.setSenha("a");
		
		if (VerificaCpf(usuario.getCpf())) {

			if (usuario.getCargo() == null || usuario.getCargo().isEmpty()) {
				preencher();
				 repositorioUsuario.CadastrarUsuario(usuario);
				System.out.println(usuario.toString());
			} else {
				 repositorioUsuario.CadastrarUsuario(usuario);
				System.out.println(usuario.toString());
			}

			jogarsession();

			limpar();

			return "Boleto?faces-redirect=true";
		}

		return "";
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

	private boolean skip;

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	public Object getTipoRg() {
		return tipoRg;
	}

	public void setTipoRg(Object tipoRg) {
		this.tipoRg = tipoRg;
	}

	public Object getUfEmissor() {
		return ufEmissor;
	}

	public void setUfEmissor(Object ufEmissor) {
		this.ufEmissor = ufEmissor;
	}

	public Object getCargo() {
		return cargo;
	}

	public void setCargo(Object cargo) {
		this.cargo = cargo;
	}

	public Object getEstadoEndereco() {
		return estadoEndereco;
	}

	public void setEstadoEndereco(Object estadoEndereco) {
		this.estadoEndereco = estadoEndereco;
	}

	public Object getDefespeciais() {
		return defespeciais;
	}

	public void setDefespeciais(Object defespeciais) {
		this.defespeciais = defespeciais;
	}

	public Object getDefeVisual() {
		return defeVisual;
	}

	public void setDefeVisual(Object defeVisual) {
		this.defeVisual = defeVisual;
	}

	public Object getDefeAuditiva() {
		return defeAuditiva;
	}

	public void setDefeAuditiva(Object defeAuditiva) {
		this.defeAuditiva = defeAuditiva;
	}

	public Object getDefeFisica() {
		return defeFisica;
	}

	public void setDefeFisica(Object defeFisica) {
		this.defeFisica = defeFisica;
	}

	public Boolean VerificaCpf(String cpf) {
		Usuario usuarioExistente = repositorioUsuario.porCpf(cpf);

		if (usuarioExistente != null) {
			FacesUtil
					.addErrorMessage("Já existe um usuario com o CPF informado.");
			
			return false;
			// throw new
			// NegocioException("Já existe um usuario com o CPF informado.");

		} else {
			return true;
		}
		

	}

}

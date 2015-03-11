package br.com.gov.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gov.model.Usuario;
import br.com.gov.repositorio.RepositorioUsuario;
import br.com.gov.util.jsf.FacesUtil;
import br.com.gov.util.jsf.SessionUtil;

@Named
@ViewScoped
public class PesquisaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private RepositorioUsuario repositorioUsuario;
	@Inject
	private Usuario usuario;
	@Inject
	private Usuario usuariop;
	@Inject
	private Usuario usuarioSelecionada;

	private String localProva;

	private String descri;

	private String cpf;

	private String cargo;

	private List<Usuario> usuarioFiltrados;

	private void limpar() {

		usuario = new Usuario();
		usuarioSelecionada = new Usuario();

	}

	public void pesquisar() {

		if (usuario != null) {
			usuarioFiltrados = repositorioUsuario.todos();
		} else {
			// usuarioFiltrados = repositorioUsuario.filtrados(usuario);
		}

	}

	public void ListarCPFDuplicado() {

		List<Usuario> usuarioTodos = repositorioUsuario.todos();
		List<Usuario> usuarioDuplicado = new ArrayList<>();
		int con = 0;

		for (Usuario usuario : usuarioTodos) {

			for (Usuario usuario2 : usuarioTodos) {
				if (usuario.getCpf().equals(usuario2.getCpf())) {

					con++;
					if (con != 1) {
						usuarioDuplicado.add(usuario2);

					}

				}

			}

		}

		usuarioFiltrados = usuarioDuplicado;

	}

	public void alterarAuxiliarAdministrativo() {

		usuarioSelecionada.setCargo("Auxiliar administrativo  - Ensino medio");
		repositorioUsuario.CadastrarUsuario(usuarioSelecionada);
		limpar();

	}

	public void deletarUsuario() {
		repositorioUsuario.remover(usuarioSelecionada);
		usuarioFiltrados.remove(usuarioSelecionada);

		FacesUtil.addInfoMessage("uSUARIO "
				+ usuarioSelecionada.getNomeCandidato()
				+ " excluído com sucesso.");

		limpar();
	}

	public void alterarDigitador() {
		usuarioSelecionada.setCargo("Digitador  - Ensino medio");
		repositorioUsuario.CadastrarUsuario(usuarioSelecionada);
		limpar();

	}
	
	public void consultarInscricao1() {
		usuariop = repositorioUsuario.porCpf(cpf);
		jogarsession();
	}

	public void consultarInscricao() {

		Usuario u = repositorioUsuario.porCpf(cpf);

		if (u.getSituacao().equals("Confirmado")
				|| u.getSituacao().equals("Isento")) {

			localProva = "COLÉGIO REAL – Rua: Ascenso Ferreira, n° 112 , São Sebastião Centro, Palmares.";

			setDescri(" A abertura dos portões do local de prova será às 08:00h. \n Os portões do local da prova serão fechados, impreterivelmente, às 9:00h (horário de Pernambuco)."
					+ "\n O candidato deverá comparecer ao local da prova munido de comprovante de inscrição e documento de identificação com foto e currilum.");

			usuariop = u;
			
		} else {
			System.out.println("Apenas o candidato isento ou com pagamento confirmado pode imprimir o cartão");
			
			FacesUtil
			.addErrorMessage("Apenas o candidato isento ou com pagamento confirmado pode imprimir o cartão");
			
		}

		
	}

	public void jogarsession() {
		System.out.println("jogarsession....");

		SessionUtil.setParam("DadosUsuario", this.usuariop);

	}

	public void listarPorCargo() {

		usuarioFiltrados = repositorioUsuario.porCargo(cargo);

		FacesUtil.addInfoMessage("Total de " + cargo + " : "
				+ usuarioFiltrados.size());

	}

	public void aceitarPagamento() {

		usuarioSelecionada.setSituacao("Confirmado");
		repositorioUsuario.CadastrarUsuario(usuarioSelecionada);
		limpar();

	}

	public void rejeitarNiz() {

		usuarioSelecionada.setSituacao("Pendente");
		repositorioUsuario.CadastrarUsuario(usuarioSelecionada);
		limpar();

	}

	public void isencaoPagamento() {

		usuarioSelecionada.setSituacao("Isento");
		repositorioUsuario.CadastrarUsuario(usuarioSelecionada);
		limpar();
	}

	public void Aprovado() {

		usuarioSelecionada.setSituacao("Aprovado");
		repositorioUsuario.CadastrarUsuario(usuarioSelecionada);
		limpar();
	}

	public void listarPagos() {

		String situacao = "Confirmado";

		usuarioFiltrados = repositorioUsuario.porPagamentoSituacao(situacao);

		FacesUtil.addInfoMessage("Total pagos : " + usuarioFiltrados.size());

	}

	public void listarPagamentoPendente() {

		String situacao = "Pendente";

		usuarioFiltrados = repositorioUsuario.porPagamentoSituacao(situacao);
		FacesUtil.addInfoMessage("Total Pendente : " + usuarioFiltrados.size());

	}

	public void listarIsencaoPendente() {
		usuarioFiltrados = repositorioUsuario
				.porPagamentoSituacao("Isenção Pendente");
	}

	public void listarTodos() {

		usuarioFiltrados = repositorioUsuario.todos();
		FacesUtil.addInfoMessage("Total de candidatos : "
				+ usuarioFiltrados.size());

	}

	public void listarDeficiente() {

		List<Usuario> todos = repositorioUsuario.todos();

		usuarioFiltrados = new ArrayList<Usuario>();

		for (Usuario usuario : todos) {

			System.out.println(usuario.toString());

			if (usuario.getDefAuditiva() != "" || usuario.getDefFisica() != ""
					|| usuario.getDefVisual() != ""
					|| usuario.getEspeciais() != "") {

				usuarioFiltrados.add(usuario);

			}
		}

	}

	public void listarAprovado() {

		String situacao = "Aprovado";

		usuarioFiltrados = repositorioUsuario.porPagamentoSituacao(situacao);

		FacesUtil
				.addInfoMessage("Total Aprovados : " + usuarioFiltrados.size());

	}

	public void listarIsento() {

		String situacao = "Isento";

		usuarioFiltrados = repositorioUsuario.porPagamentoSituacao(situacao);

		FacesUtil.addInfoMessage("Total isentos : " + usuarioFiltrados.size());

	}

	public void excluir() {
		repositorioUsuario.remover(usuarioSelecionada);
		usuarioFiltrados.remove(usuarioSelecionada);

		FacesUtil.addInfoMessage("uSUARIO "
				+ usuarioSelecionada.getNomeCandidato()
				+ " excluído com sucesso.");

		limpar();

	}

	public List<Usuario> getUsuarioFiltrados() {
		return usuarioFiltrados;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Usuario getUsuarioSelecionada() {
		return usuarioSelecionada;
	}

	public void setUsuarioSelecionada(Usuario usuarioSelecionada) {
		this.usuarioSelecionada = usuarioSelecionada;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Usuario getUsuariop() {
		return usuariop;
	}

	public void setUsuariop(Usuario usuariop) {
		this.usuariop = usuariop;
	}

	public String getLocalProva() {
		return localProva;
	}

	public void setLocalProva(String localProva) {
		this.localProva = localProva;
	}

	public String getDescri() {
		return descri;
	}

	public void setDescri(String descri) {
		this.descri = descri;
	}

}
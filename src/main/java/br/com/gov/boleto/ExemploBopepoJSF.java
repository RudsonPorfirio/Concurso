package br.com.gov.boleto;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

import br.com.gov.model.Usuario;

@Named
@ViewScoped
public class ExemploBopepoJSF implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario u;

	public Usuario dadoSession() {

		System.out.println("dadoSession...");

		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		Usuario u = (Usuario) session.getAttribute("DadosUsuario");

		System.out.println(u.toString());

		this.u = u;

		return u;

	}

	public String download() {

		BoletoViewer boletoViewer = boletoCriado();

		byte[] pdfAsBytes = boletoViewer.getPdfAsByteArray();

		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		try {

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename=boleto.pdf");

			OutputStream output = response.getOutputStream();
			output.write(pdfAsBytes);
			response.flushBuffer();

			FacesContext.getCurrentInstance().responseComplete();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public BoletoViewer boletoCriado() {

		u = dadoSession();

		/*
		 * INFORMANDO DADOS SOBRE O CEDENTE.
		 */
		// Cedente cedente = new
		// Cedente("Seleção de cargos Palmatre"+" . 00.171.7816/0001-01" );

		Cedente cedente = new Cedente("Fundo municipal de Assistência Social",
				"01.717.816/0001-01");

		/*
		 * INFORMANDO DADOS SOBRE O SACADO.
		 */
		Sacado sacado = new Sacado(u.getNomeCandidato() + ". CPF : "
				+ u.getCpf() + ". Cargo : " + u.getCargo());

		// sacado.se
		// Informando o endereço do sacado.
		// Endereco enderecoSac = new Endereco();
		// enderecoSac.setUF(UnidadeFederativa.RN);
		// enderecoSac.setLocalidade("Natal");
		// enderecoSac.setCep(new CEP("59064-120"));
		// enderecoSac.setBairro("Grande Centro");
		// enderecoSac.setLogradouro("Rua poeta dos programas");
		// enderecoSac.setNumero("1");
		// sacado.addEndereco(enderecoSac);

		/*
		 * INFORMANDO OS DADOS SOBRE O TÍTULO.
		 */

		// 20-23 4 9(4) Código da agência (sem dígito) Código da agência (sem
		// dígito)
		// 24-25 2 9(2) Código da Carteira Código da Carteira
		// 26-36 11 9(11) Nosso Número (sem dígito) Nosso Número (sem dígito)
		// 37-43 7 9(7) Conta do cedente (sem dígito) Código do Cedente (sem
		// dígito)
		// 44-44 1 9(1) Zero fixo Zero fixo

		// Informando dados sobre a conta bancária do título.
		ContaBancaria contaBancaria = new ContaBancaria(
				BancosSuportados.BANCO_BRADESCO.create());

		// contaBancaria.setNumeroDaConta(new NumeroDaConta(123456, "0"));
		// contaBancaria.setCarteira(new Carteira(30));
		// contaBancaria.setAgencia(new Agencia(1234, "1"));

		// contaBancaria.setNumeroDaConta(new NumeroDaConta(43036)); //Não
		// colocar o zero
		contaBancaria.setNumeroDaConta(new NumeroDaConta(4303, "6"));

		contaBancaria.setCarteira(new Carteira(16));
		// contaBancaria.setAgencia(new Agencia(3214));
		contaBancaria.setAgencia(new Agencia(3214));
		// contaBancaria.setAgencia(new Agencia(1234, "0"));

		Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
		String cpf2 = u.getCpf().replace(".", "").replace("-", "");
		titulo.setNumeroDoDocumento(cpf2);

		titulo.setNossoNumero(cpf2);
		titulo.setDigitoDoNossoNumero("5");

		titulo.setDataDoDocumento(new Date());
		titulo.setDataDoVencimento(new Date("03/02/2015"));
		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		// titulo.setAceite(Aceite.A);
		// titulo.setDesconto(new BigDecimal(0.05));
		titulo.setDeducao(BigDecimal.ZERO);
		titulo.setMora(BigDecimal.ZERO);
		titulo.setAcrecimo(BigDecimal.ZERO);

		if (u.getCargo().equals("Educador social - Ensino superior")) {
			titulo.setValor(BigDecimal.valueOf(25.00));
			titulo.setValorCobrado(BigDecimal.valueOf(25.00));
		} else {
			titulo.setValor(BigDecimal.valueOf(15.00));
			titulo.setValorCobrado(BigDecimal.valueOf(15.00));
		}
		/*
		 * INFORMANDO OS DADOS SOBRE O BOLETO.
		 */
		Boleto boleto = new Boleto(titulo);

		boleto.setLocalPagamento("Pagável na loterica ou Banco Bradesco.");
		// boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor "
		// + "cobrado não é o esperado, aproveite o DESCONTÃO!");
		// boleto.setInstrucao1("PARA PAGAMENTO 1 até Hoje não cobrar nada!");
		// boleto.setInstrucao2("PARA PAGAMENTO 2 até Amanhã Não cobre!");
		// boleto.setInstrucao3("PARA PAGAMENTO 3 até Depois de amanhã, OK, não cobre.");
		// boleto.setInstrucao4("PARA PAGAMENTO 4 até 04/xx/xxxx de 4 dias atrás COBRAR O VALOR DE: R$ 01,00");
		// boleto.setInstrucao5("PARA PAGAMENTO 5 até 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
		// boleto.setInstrucao6("PARA PAGAMENTO 6 até 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
		// boleto.setInstrucao7("PARA PAGAMENTO 7 até xx/xx/xxxx COBRAR O VALOR QUE VOCÊ QUISER!");
		// boleto.setInstrucao8("APÓS o Vencimento, Pagável Somente na Rede X.");

		/*
		 * GERANDO O BOLETO BANCÁRIO.
		 */
		// Instanciando um objeto "BoletoViewer", classe responsável pela
		// geração do boleto bancário.
		BoletoViewer boletoViewer = new BoletoViewer(boleto);

		// Gerando o arquivo. No caso o arquivo mencionado será salvo na mesma
		// pasta do projeto. Outros exemplos:
		// WINDOWS: boletoViewer.getAsPDF("C:/Temp/MeuBoleto.pdf");
		// LINUX: boletoViewer.getAsPDF("/home/temp/MeuBoleto.pdf");
		// File arquivoPdf = boletoViewer.getPdfAsFile("MeuPrimeiroBoleto.pdf");

		// Mostrando o boleto gerado na tela.
		// mostreBoletoNaTela(arquivoPdf);

		return boletoViewer;
	}

	public Usuario getU() {
		return u;
	}

	public void setU(Usuario u) {
		this.u = u;
	}

	/**
	 * Exibe o arquivo na tela.
	 * 
	 * @param arquivoBoleto
	 */
	// private static void mostreBoletoNaTela(File arquivoBoleto) {
	//
	// java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
	//
	// try {
	// desktop.open(arquivoBoleto);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

}
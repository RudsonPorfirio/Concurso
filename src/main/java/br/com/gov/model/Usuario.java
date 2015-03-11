package br.com.gov.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

/**
 * 
 * Criado e desenvolvido por rudsonporfirio
 * 
 * Designer, Teste, DBA, Desenvolvedor...
 *
 * @author Rudson Porfirio Do Nascimento
 * 
 * Contato : rudsonporfirio@gmail.com
 * 
 */
@Entity
public class Usuario  implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	
	
	private String nomeCandidato;
	@NotBlank @NotNull
	private String nomePai;
	@NotBlank @NotNull
	private String nomeMae;
	
	private String especiais;
	
	private String dataDeNascimento;
	
	@CPF  
	private String cpf;
	private String cidadeNascimento;
	
	private String telefone;
	private String celular;
	private String celular2;
	private String email;
	private String senha;
	
	private String Situacao;
	

	private String iniz;
	
	
	@NotBlank @NotNull
	private String rg;
	@NotBlank @NotNull
	private String tipo; //SDS
	@NotBlank @NotNull
	private String UF; //SDS
	@NotBlank @NotNull
	private String dataEmissaoRG;
	@NotBlank @NotNull
	private String sexo;

	@NotBlank @NotNull
	private String cargo;
	private String defVisual;
	private String defAuditiva;
	private String defFisica;
	
	private Endereco endereco;

	private Date dataCadastro;
	
	
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	


	@OneToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name="endereco_usuario")
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getCelular2() {
		return celular2;
	}
	public void setCelular2(String celular2) {
		this.celular2 = celular2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNomeCandidato() {
		return nomeCandidato;
	}
	public void setNomeCandidato(String nomeCandidato) {
		this.nomeCandidato = nomeCandidato;
	}
	public String getNomePai() {
		return nomePai;
	}
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	public String getDataDeNascimento() {
		return dataDeNascimento;
	}
	public void setDataDeNascimento(String dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}
	public String getCidadeNascimento() {
		return cidadeNascimento;
	}
	public void setCidadeNascimento(String cidadeNascimento) {
		this.cidadeNascimento = cidadeNascimento;
	}
	public String getIniz() {
		return iniz;
	}
	public void setIniz(String iniz) {
		this.iniz = iniz;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getUF() {
		return UF;
	}
	public void setUF(String uF) {
		UF = uF;
	}
	public String getDataEmissaoRG() {
		return dataEmissaoRG;
	}
	public void setDataEmissaoRG(String dataEmissaoRG) {
		this.dataEmissaoRG = dataEmissaoRG;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getDefVisual() {
		return defVisual;
	}
	public void setDefVisual(String defVisual) {
		this.defVisual = defVisual;
	}
	public String getDefAuditiva() {
		return defAuditiva;
	}
	public void setDefAuditiva(String defAuditiva) {
		this.defAuditiva = defAuditiva;
	}
	public String getDefFisica() {
		return defFisica;
	}
	public void setDefFisica(String defFisica) {
		this.defFisica = defFisica;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getSituacao() {
		return Situacao;
	}
	public void setSituacao(String situacao) {
		Situacao = situacao;
	}
	
	
	
	public String getEspeciais() {
		return especiais;
	}
	public void setEspeciais(String especiais) {
		this.especiais = especiais;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nomeCandidato=" + nomeCandidato
				+ ", nomePai=" + nomePai + ", nomeMae=" + nomeMae
				+ ", especiais=" + especiais + ", dataDeNascimento="
				+ dataDeNascimento + ", cpf=" + cpf + ", cidadeNascimento="
				+ cidadeNascimento + ", telefone=" + telefone + ", celular="
				+ celular + ", celular2=" + celular2 + ", email=" + email
				+ ", senha=" + senha + ", Situacao=" + Situacao + ", iniz="
				+ iniz + ", rg=" + rg + ", tipo=" + tipo + ", UF=" + UF
				+ ", dataEmissaoRG=" + dataEmissaoRG + ", sexo=" + sexo
				+ ", cargo=" + cargo + ", defVisual=" + defVisual
				+ ", defAuditiva=" + defAuditiva + ", defFisica=" + defFisica
				+ ", endereco=" + endereco.toString() + ", dataCadastro=" + dataCadastro
				+ "]";
	}
	
	
	
	
	
}

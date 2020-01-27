package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "UsuarioPessoa.todos", query = "select u from UsuarioPessoa u") })
public class UsuarioPessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String login;
	private String senha;
	private int idade;
	
	private Double salario;
	private String sexo;
	
	private String cep;
	private String logradouro;
	private String bairro;
	private String uf;
	
	@Column(columnDefinition = "text")
	private String imagem;
	
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public String getImagem() {
		return imagem;
	}
	
	public Double getSalario() {
		return salario;
	}
	
	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getSexo() {
		return sexo;
	}

	@OneToMany(mappedBy = "usuarioPessoa", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<TelefoneUser> telefoneUser = new ArrayList<TelefoneUser>();
	
	@OneToMany(mappedBy = "usuarioPessoa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<EmailUser> emails = new ArrayList<EmailUser>();

	public List<TelefoneUser> getTelefoneUser() {
		return telefoneUser;
	}
	
	public List<EmailUser> getEmails() {
		return emails;
	}
	
	public void setEmails(List<EmailUser> emails) {
		this.emails = emails;
	}

	public void setTelefoneUser(List<TelefoneUser> telefoneUser) {
		this.telefoneUser = telefoneUser;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "UsuarioPessoa [id=" + id + ", nome=" + nome + ", login=" + login + ", senha="
				+ senha + ", idade=" + idade + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioPessoa other = (UsuarioPessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}

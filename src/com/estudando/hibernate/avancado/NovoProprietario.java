package com.estudando.hibernate.avancado;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "novo_proprietario")
public class NovoProprietario {

	@Id
	@GeneratedValue
	private Long codigo;
	
	private String nome;
	
	@ElementCollection
	@CollectionTable(name = "proprietario_telefones", joinColumns = @JoinColumn(name = "cod_proprietario"))
	@Column(name = "numero_telefone")
	private List<String> telefones = new ArrayList<String>();
	
	
	@ElementCollection
	@CollectionTable(name="proprietario_telefones", joinColumns = @JoinColumn(name = "cod_porprietario"))
	@AttributeOverrides({@AttributeOverride(name = "numero", column = @Column(name = "num_telefone"))})
	private List<Telefone> telefonesClasse = new ArrayList<>();

	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<String> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<String> telefones) {
		this.telefones = telefones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		NovoProprietario other = (NovoProprietario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}

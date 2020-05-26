package com.estudando.hibernate.classes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({	
	@NamedQuery(name = "buscarTodos", query = "SELECT c FROM Carro c inner JOIN fetch c.modelo"),
	@NamedQuery(name = "buscarCarroComAcessorios",
		query = "SELECT c FROM Carro c JOIN c.acessorios a WHERE c.codigo = :codigo")
}) 
@Entity
@Table(name = "carro")
public class Carro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	private String placa;
	private String chassi;
	private String cor;
	private BigDecimal valorDiario;
	
	@Temporal(TemporalType.TIMESTAMP) // obs: utilazar bibliotecas do java 8
	private Date dataCriacao;
	
	@Temporal(TemporalType.TIMESTAMP) // obs: utilazar bibliotecas do java 8
	private Date dataModificacao;
	
	// Default EAGER
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "codigo_modelo")
	private ModeloCarro modelo;
	
	// Default LAZY
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "carro_acessorio", joinColumns = @JoinColumn(name = "codigo_carro"),
			inverseJoinColumns = @JoinColumn(name = "codigo_acessorio"))
	private List<Acessorio> acessorios;
	
	@OneToMany(mappedBy = "carro", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private Aluguel aluguel;

	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getChassi() {
		return chassi;
	}
	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}

	public BigDecimal getValorDiario() {
		return valorDiario;
	}
	public void setValorDiario(BigDecimal valorDiario) {
		this.valorDiario = valorDiario;
	}

	public ModeloCarro getModelo() {
		return modelo;
	}
	public void setModelo(ModeloCarro modelo) {
		this.modelo = modelo;
	}

	public List<Acessorio> getAcessorios() {
		return acessorios;
	}
	public void setAcessorios(List<Acessorio> acessorios) {
		this.acessorios = acessorios;
	}

	public Aluguel getAluguel() {
		return aluguel;
	}
	public void setAluguel(Aluguel aluguel) {
		this.aluguel = aluguel;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public Date getDataModificacao() {
		return dataModificacao;
	}
	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
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
		Carro other = (Carro) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	// Anotações do ciclo de vida do hibernate
	@PrePersist
	@PreUpdate
	public void configuraDatasCriacaoAlteracao() {
		this.dataModificacao = new Date();
		
		if(this.dataCriacao == null) {
			this.dataCriacao = new Date();
		}
	}
	
}

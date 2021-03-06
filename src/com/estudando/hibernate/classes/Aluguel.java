package com.estudando.hibernate.classes;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "aluguel")
public class Aluguel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_pedido")
	private Calendar dataPedido; // obs: utilazar bibliotecas do java 8
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_entrega")
	private Date dataEntrga; // obs: utilazar bibliotecas do java 8
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_devolucao")
	private Date dataDevolucao; // obs: utilazar bibliotecas do java 8
	
	@Column(name = "valor_total")
	private BigDecimal valorTotal;
	
	@ManyToOne
	@JoinColumn(name = "codigo_carro")
	private Carro carro;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "codigo_apolice")
	private ApoliceSeguro apolice;

	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Calendar getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(Calendar dataPedido) {
		this.dataPedido = dataPedido;
	}
	
	public Date getDataEntrga() {
		return dataEntrga;
	}
	public void setDataEntrga(Date dataEntrga) {
		this.dataEntrga = dataEntrga;
	}
	
	public Date getDataDevolucao() {
		return dataDevolucao;
	}
	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Carro getCarro() {
		return carro;
	}
	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public ApoliceSeguro getApolice() {
		return apolice;
	}
	public void setApolice(ApoliceSeguro apolice) {
		this.apolice = apolice;
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
		Aluguel other = (Aluguel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}

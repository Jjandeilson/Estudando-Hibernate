package com.estudando.hibernate.avancado;

import javax.persistence.Embeddable;

@Embeddable
public class VeciculoId {

	private String placa;
	private String cidade;
	
	public VeciculoId() {
		
	}
	
	public VeciculoId(String plava, String cidade) {
		this.placa = plava;
		this.cidade = cidade;
	}
	
	public String getPlava() {
		return placa;
	}
	public void setPlava(String plava) {
		this.placa = plava;
	}
	
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((placa == null) ? 0 : placa.hashCode());
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
		VeciculoId other = (VeciculoId) obj;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (placa == null) {
			if (other.placa != null)
				return false;
		} else if (!placa.equals(other.placa))
			return false;
		return true;
	}
		
}

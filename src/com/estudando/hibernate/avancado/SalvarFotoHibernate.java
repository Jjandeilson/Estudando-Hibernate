package com.estudando.hibernate.avancado;

import javax.persistence.Lob;

public class SalvarFotoHibernate {

	@Lob
	private byte[] foto;

	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
}

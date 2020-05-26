package com.estudando.hibernate.classes;

import javax.persistence.DiscriminatorValue;

@DiscriminatorValue("FUNCIONARIO")
public class Funcionario extends Pessoa{

	private String matricula;

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
}

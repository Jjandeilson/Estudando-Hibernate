package com.estudando.hibernate.conexao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConexaoBanco {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("locadoraOP");
	
	public static EntityManager getConectionFactory() {
		EntityManager em = emf.createEntityManager();
		return em;
	}
}

package com.estudando.hibernate.consultas.nativa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.estudando.hibernate.classes.Carro;
import com.estudando.hibernate.conexao.ConexaoBanco;

public class ConsultaNativa {

	EntityManager em = ConexaoBanco.getConectionFactory();
	
	@SuppressWarnings("unchecked")
	public void nativa() {
		Query query = em.createNamedQuery("SELECT * FROM Carro", Carro.class);
		List<Carro> carros = query.getResultList();
		
		for(Carro carro: carros) {
			System.out.println(carro.getPlaca());
		}
		em.close();
	}
}

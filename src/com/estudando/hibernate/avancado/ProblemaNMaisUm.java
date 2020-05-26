package com.estudando.hibernate.avancado;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.estudando.hibernate.classes.Carro;
import com.estudando.hibernate.conexao.ConexaoBanco;

public class ProblemaNMaisUm {

	EntityManager em = ConexaoBanco.getConectionFactory();
	
	public void resolver() {
		TypedQuery<Carro> query = em.createQuery("from Carro c inner JOIN fecth c.modelo", Carro.class);
		List<Carro> carros =query.getResultList();
		
		for(Carro carro: carros) {
			System.out.println(carro.getPlaca() + " - " + carro.getModelo().getDescricao());
		}
		em.close();
	}
}

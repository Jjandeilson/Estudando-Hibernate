package com.estudando.hibernate.avancado;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.estudando.hibernate.classes.Carro;
import com.estudando.hibernate.conexao.ConexaoBanco;

public class CachePrimeiroNivel {

	EntityManager em = ConexaoBanco.getConectionFactory();
	
	public void cache() {
		TypedQuery<Carro> query = em.createQuery("from Carro c inner JOIN fecth c.modelo", Carro.class);
		List<Carro> carros =query.getResultList();
		
		for(Carro carro: carros) {
			System.out.println(carro.getPlaca() + " - " + carro.getModelo().getDescricao());
		}
		
		System.out.println("------------------------------------------");
		Carro carro = em.find(Carro.class, 10L);
		System.out.println(carro.getCodigo() + " - " + carro.getPlaca());
		em.close();
	}
}

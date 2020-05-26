package com.estudando.hibernate.consultas.Criteria;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.estudando.hibernate.classes.Aluguel;
import com.estudando.hibernate.classes.ModeloCarro;
import com.estudando.hibernate.conexao.ConexaoBanco;

public class ConsultasCriteria {
	
	EntityManager em = ConexaoBanco.getConectionFactory();
	
	// Consultas com criteria
	
	public void filtroPesquisa(Date dataEntrega, ModeloCarro modelo) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Aluguel> criteria = builder.createQuery(Aluguel.class);
		Root<Aluguel> a = criteria.from(Aluguel.class);
		criteria.select(a);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(dataEntrega != null) {
			ParameterExpression<Date> dataEntregaInicial = builder.parameter(Date.class, "dataEntregaInicial");
			ParameterExpression<Date> dataEntregaFinal = builder.parameter(Date.class, "dataEntregaFinal");
			
			predicates.add(builder.between(a.get("dataEntrega"), dataEntregaInicial, dataEntregaFinal));
		}
		
		if(modelo != null) {
			ParameterExpression<ModeloCarro> modeloExpression = builder.parameter(ModeloCarro.class, "modelo");
			predicates.add(builder.equal(a.get("carro").get("modelo"), modeloExpression));
		}
		
		criteria.where(predicates.toArray(new Predicate[predicates.size()]));
		
		TypedQuery<Aluguel> query = em.createQuery(criteria);
		
		if(dataEntrega != null) {
			Calendar dataEntregaInicial = Calendar.getInstance();
			dataEntregaInicial.setTime(dataEntrega);
			dataEntregaInicial.set(Calendar.HOUR_OF_DAY, 0);
			dataEntregaInicial.set(Calendar.MINUTE, 0);
			dataEntregaInicial.set(Calendar.SECOND, 0);
			
			Calendar dataEntregaFinal = Calendar.getInstance();
			dataEntregaFinal.setTime(dataEntrega);
			dataEntregaFinal.set(Calendar.HOUR_OF_DAY, 0);
			dataEntregaFinal.set(Calendar.MINUTE, 0);
			dataEntregaFinal.set(Calendar.SECOND, 0);
			
			query.setParameter("dataEntragaInicial", dataEntregaInicial.getTime());
			query.setParameter("dataEntregaFinal", dataEntregaFinal.getTime());
		}
		
		if(modelo != null) {
			query.setParameter("modelo", modelo);
		}
		
		query.getResultList();
		
		em.close();
	}
}

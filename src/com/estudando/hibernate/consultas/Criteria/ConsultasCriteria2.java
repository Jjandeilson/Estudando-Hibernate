package com.estudando.hibernate.consultas.Criteria;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.estudando.hibernate.classes.Aluguel;
import com.estudando.hibernate.classes.Carro;
import com.estudando.hibernate.classes.ModeloCarro;
import com.estudando.hibernate.conexao.ConexaoBanco;
import com.estudando.hibernate.consultas.Criteria.construtor.PrecoCarro;

public class ConsultasCriteria2 {

	EntityManager em = ConexaoBanco.getConectionFactory();
	
	//consultas de agragação com criteria
	public void funcaoAgregada() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteria = builder.createQuery(BigDecimal.class);
		Root<Aluguel> aluguel = criteria.from(Aluguel.class);
		
		criteria.select(builder.sum(aluguel.get("valorTotal")));
		TypedQuery<BigDecimal> query = em.createQuery(criteria);
		BigDecimal total = query.getSingleResult();
		System.out.println("Soma de todos os alugueis: " + total);
		em.close();
	}

	// retorna mais de um atributo
	public void resultadoComplexo() {
		CriteriaBuilder builde = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteria = builde.createQuery(Object[].class);
		
		Root<Carro> carro = criteria.from(Carro.class);
		criteria.multiselect(carro.get("placa"), carro.get("valorDiaria"));
		
		TypedQuery<Object[]> query = em.createQuery(criteria);
		List<Object[]> resultado = query.getResultList();
		
		for(Object[] valores: resultado) {
			System.out.println(valores[0] + " - " + valores[1]);
		}
		em.close();
	}
	
	
	public void resultadoTupla() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteria = builder.createTupleQuery();
		Root<Carro> carro = criteria.from(Carro.class);
		
		criteria.multiselect(carro.get("placa").alias("placaCarro"), carro.get("valorDiario").alias("valorCarro"));
		
		TypedQuery<Tuple> query = em.createQuery(criteria);
		List<Tuple> resultado = query.getResultList();
		
		for(Tuple tupla: resultado) {
			System.out.println(tupla.get("placaCarro") + " - " + tupla.get("valorCarro"));
		}
		em.close();
	}
	
	public void construtor() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PrecoCarro> criteria = builder.createQuery(PrecoCarro.class);
		
		Root<Carro> carro = criteria.from(Carro.class);
		criteria.select(builder.construct(PrecoCarro.class, carro.get("placa"), carro.get("valorDiaria")));
		
		TypedQuery<PrecoCarro> query = em.createQuery(criteria);
		List<PrecoCarro> resultado = query.getResultList();
		
		for(PrecoCarro precoCarrpo: resultado) {
			System.out.println(precoCarrpo.getPlaca() + " - " + precoCarrpo.getValor());
		}
	}
	
	// Funçções
	public void funcao() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Carro> criteria = builder.createQuery(Carro.class);
		Root<Carro> carro = criteria.from(Carro.class);
		Predicate predicate = builder.equal(builder.upper(carro.get("cor")), "prata".toUpperCase());
		
		criteria.select(carro);
		criteria.where(predicate);
		
		TypedQuery<Carro> query = em.createQuery(criteria);
		List<Carro> carros = query.getResultList();
		
		for(Carro cr: carros) {
			System.out.println(cr.getPlaca() + " - " + cr.getCor());
		}
		em.close();
	}
	
	// Ordena resultados
	public void ordena() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Carro> criteria = builder.createQuery(Carro.class);
		
		Root<Carro> carro = criteria.from(Carro.class);
		Order order = builder.desc(carro.get("valorDiario"));
		
		criteria.select(carro);
		criteria.orderBy(order);
		
		TypedQuery<Carro> query = em.createQuery(criteria);
		List<Carro> carros = query.getResultList();
		
		for(Carro cr: carros) {
			System.out.println(cr.getPlaca() + " - " + cr.getValorDiario());
		}
		em.close();
	}
	
	// Join e Fetch
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void juncao() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Carro> criteria = builder.createQuery(Carro.class);
		Root<Carro> carro = criteria.from(Carro.class);
		Join<Carro, ModeloCarro> modelo = (Join) carro.join("modelo");
		
		criteria.select(carro);
		criteria.where(builder.equal(modelo.get("descricao"), "Fiat"));
		
		TypedQuery<Carro> query = em.createQuery(criteria);
		List<Carro> carros = query.getResultList();
		
		for(Carro c: carros) {
			System.out.println(c.getPlaca());
		}
		em.close();
	}
	
	//subqueries
	
	public void mediaDasDiariasDosCarros() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Double> criteria = builder.createQuery(Double.class);
		
		Root<Carro> carro = criteria.from(Carro.class);
		criteria.select(builder.avg(carro.get("valorDiari")));
		
		TypedQuery<Double> query = em.createQuery(criteria);
		Double total = query.getSingleResult();
		
		System.out.println("Média da diária: " + total);
		em.close();
	}
	
	public void carrosComValoresAcimaDaMedia() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Carro> criteria = builder.createQuery(Carro.class);
		Subquery<Double> subquery = criteria.subquery(Double.class);
		
		Root<Carro> carro = criteria.from(Carro.class);
		Root<Carro> carroSub = subquery.from(Carro.class);
		
		subquery.select(builder.avg(carroSub.get("valorDiario")));
		
		criteria.select(carro);
		criteria.where(builder.greaterThanOrEqualTo(carro.get("valorDiario"), subquery));
		
		TypedQuery<Carro> query = em.createQuery(criteria);
		List<Carro> carros = query.getResultList();
		
		for(Carro c: carros) {
			System.out.println(c.getPlaca() + " - " + c.getValorDiario());
		}
		em.close();
	}
	
}

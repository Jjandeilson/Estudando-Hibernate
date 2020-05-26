package com.estudando.hibernate.consultas.JPQL;

import java.util.List;

import javax.persistence.EntityManager;

import com.estudando.hibernate.classes.Carro;
import com.estudando.hibernate.conexao.ConexaoBanco;

public class ConsultasJPQL {

	// Nome de fabricantes
	public void nomeFabricante() {
		EntityManager em = ConexaoBanco.getConectionFactory();
		
		List<String> nomeFabricantes = em.createQuery("SELECT f.nome from Fabricante f", String.class)
													.getResultList();
		
		for(String nome: nomeFabricantes) {
			System.out.println("Nome: " + nome);
		}
		em.close();
	}
	
	// Buscar nome do fabricante pelo modelo carro
	public void buscarModulo() {
		EntityManager em = ConexaoBanco.getConectionFactory();
		
	 	List<String>nomeFabricantes = em.createQuery("SELECT mc.fabricante.nome FROM ModeloCarro mc", String.class)
	 										.getResultList();
	 	
	 	for(String nome: nomeFabricantes) {
			System.out.println("Nome: " + nome);
		}
	 	em.close();
	}
	
	// Buscar nome do fabricante filtrado com WHERE
	public void nomeDoFabricante() {
		EntityManager em = ConexaoBanco.getConectionFactory();
		
		List<String>modelos = em.createQuery("SELECT mc.descricao FROM ModeloCarro mc WHERE mc.fabricante.nome = 'Honda'", String.class)
							.getResultList();
		
		for(String nome: modelos) {
			System.out.println("Nome: " + nome);
		}
	 	em.close();
	}
	
	// buscar nome de alguns campos
	@SuppressWarnings("unchecked")
	public void buscarCampos() {
		EntityManager em = ConexaoBanco.getConectionFactory();
		
		String jpql = "SELECT mc.descricao, mc.categoria FROM ModeloCarro mc";
		List<Object[]>resultados = em.createQuery(jpql).getResultList();
		for(Object[] obj: resultados) {
			System.out.println("Descrição: " + obj[0] + ". E categorio: " + obj[1]);
		}
		em.close();
	}
	
	// Buscar acessorios pelo carro
	public void buscarAcessorios() {
		EntityManager em = ConexaoBanco.getConectionFactory();
		
		String jpql = "SELECT  a.descricao FROM Carro c JOIN c.acessorios a WHERE c.modelo.descricao = 'Cruze'";
		List<String> acessorios = em.createQuery(jpql, String.class).getResultList();
		for(String acessorio: acessorios) {
			System.out.println("Acessório: " + acessorio);
		}
		em.close();
	}
	
	// Consultas agregadas
	
	@SuppressWarnings("unchecked")
	public void quantidadeAluguel() {
		EntityManager em = ConexaoBanco.getConectionFactory();
		 
		String jpql = "SELECT c, count(a), max(a.valorTotal), avg(a.valorTotal) "
				+ "FROM Carro c JOIN c.alugueis a GROUP BY c "
				+ "HAVING count(a) > 1";
		List<Object[]>resultados = em.createQuery(jpql).getResultList();
		for(Object[] obj: resultados) {
			System.out.println("Modelo: " + ((Carro) obj[0]).getModelo().getDescricao());
			System.out.println("Quantidade de alegueis: " + obj[1]);
			System.out.println("Valor máximo: " + obj[2]);
			System.out.println("Valor médio: " + obj[3]);
		}
		em.close();
	}
	
	// Passa parâmetros para query
	
	// parâmetro por posição
	public void queryParametroPosicao() {
		EntityManager em = ConexaoBanco.getConectionFactory();
		String modelo = "Chevrolet";
		String jpql = "SELECT mc.descricao FROM ModeloCarro mc "
				+ "WHERE mc.fabricante.nome = ?1";
		List<String> modelos = em.createQuery(jpql, String.class)
				.setParameter(1, modelo)
				.getResultList();
		for(String m: modelos) {
			System.out.println(m);
		}
		em.close();
	}
	
	//parametrizado
	public void queryParametroParametrizado() {
		EntityManager em = ConexaoBanco.getConectionFactory();
		String modelo = "Chevrolet";
		String jpql = "SELECT mc.descricao FROM ModeloCarro mc "
				+ "WHERE mc.fabricante.nome = :modelo";
		List<String> modelos = em.createQuery(jpql, String.class)
				.setParameter("modelo", modelo)
				.getResultList();
		for(String m: modelos) {
			System.out.println(m);
		}
		em.close();
	}
	
	// namedQuery
	public void listarTodos() {
		EntityManager em = ConexaoBanco.getConectionFactory();
		em.createNamedQuery("buscarTodos").getResultList(); // retorna uma lista
		em.close();
	}
	
	// NamedQuery com parâmetro
	public void listaAcessorios(Long codigo) {
		EntityManager em = ConexaoBanco.getConectionFactory();
		em.createNamedQuery("buscarCarroComAcessorios", Carro.class) // retorna um registro de carro
			.setParameter("codigo", codigo)
			.getFirstResult();
		em.close();
	}
	
	// Paginação
	@SuppressWarnings("unchecked")
	public List<Carro> buscarComPaginacao(int first, int pagiSiza) {
		EntityManager em = ConexaoBanco.getConectionFactory();
		return em.createNamedQuery("buscarTodos")
				.setFirstResult(first)
				.setMaxResults(pagiSiza)
				.getResultList();
	}
	
	// Quantidade para paginação
	public Long encontrarQuantidadeDeCarros() {
		EntityManager em = ConexaoBanco.getConectionFactory();
		return em.createQuery("SELECT count(c) FROM Carro c", Long.class).getSingleResult();
	}
}

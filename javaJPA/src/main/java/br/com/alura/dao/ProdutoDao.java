package br.com.alura.dao;

import br.com.alura.modelo.Produto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProdutoDao {

    private EntityManager em;
    public ProdutoDao(EntityManager entityManager) {
        this.em = entityManager;
    }
    public void cadastrar(Produto produto) {
        this.em.persist(produto);
    }

    public void atualizar(Produto produto) {
        this.em.merge(produto);
    }
    public void remover(Produto produto) {
        produto = this.em.merge(produto);
        this.em.remove(produto);
    }
    public Produto buscarPorID(Long id) {
        return this.em.find(Produto.class, id);
    }
    public List<Produto> buscarTodos() {
        return this.em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }

    public List<Produto> buscarPorNome(String nome) {
        return this.em.createQuery("SELECT p FROM Produto p WHERE p.nome = ?1", Produto.class)
                .setParameter(1, nome)
                .getResultList();
    }

    public List<Produto> buscarPorNomeCategoria(String nome) {
        return this.em.createQuery("SELECT p FROM Produto p WHERE p.categoria.nome = ?1", Produto.class)
                .setParameter(1, nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoPorID(Long id) {
        return this.em.createQuery("SELECT p.preco FROM Produto p WHERE p.id = ?1", BigDecimal.class)
                .setParameter(1, id)
                .getSingleResult();
    }

    public List<Produto> buscaPorListaDeParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {

        String jpql = "SELECT p FROM Produto p WHERE 1=1 ";

        if( nome != null && nome.trim().isEmpty()) {
            jpql += "AND p.nome= :nome";
        }
        if(preco !=null) {
            jpql += "AND p.preco = :preco";
        }
        if(dataCadastro!= null) {
            jpql += "AND p.dataCadastro = :dataCadastro";
        }

        TypedQuery<Produto> query = em.createQuery(jpql , Produto.class);

        if( nome != null && nome.trim().isEmpty()) {
            query.setParameter("nome", nome);
        }
        if(preco !=null) {
            query.setParameter("preco", preco);
        }
        if(dataCadastro!= null) {
            query.setParameter("dataCatastro", dataCadastro);
        }

        return query.getResultList();
    }

    public List<Produto> buscarPorParametrosComCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {

        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        Root<Produto> from = query.from(Produto.class);

        Predicate filtros = builder.and();
        if( nome != null && nome.trim().isEmpty()) {
            filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
        }
        if(preco !=null) {
            filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
        }
        if(dataCadastro != null) {
            filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
        }

        return this.em.createQuery(query).getResultList();
    }

}

package br.com.alura.dao;

import br.com.alura.modelo.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoDAO {

    private EntityManager em;
    public ProdutoDAO (EntityManager entityManager) {
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
}

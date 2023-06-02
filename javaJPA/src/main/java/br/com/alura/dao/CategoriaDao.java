package br.com.alura.dao;

import br.com.alura.modelo.Categoria;

import javax.persistence.EntityManager;

public class CategoriaDao {

    private EntityManager em;
    public CategoriaDao(EntityManager entityManager) {
        this.em = entityManager;
    }
    public void cadastrar(Categoria categoria) {
        this.em.persist(categoria);
    }

    public void atualizar(Categoria categoria) {
        this.em.merge(categoria);
    }

    public void remover (Categoria categoria) {
        categoria = this.em.merge(categoria);
        this.em.remove(categoria);
    }
}

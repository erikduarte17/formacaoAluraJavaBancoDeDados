package br.com.alura.dao;

import br.com.alura.modelo.Categoria;

import javax.persistence.EntityManager;

public class CategoriaDAO {

    private EntityManager em;
    public CategoriaDAO(EntityManager entityManager) {
        this.em = entityManager;
    }
    public void cadastrar(Categoria categoria) {
        this.em.persist(categoria);
    }
}

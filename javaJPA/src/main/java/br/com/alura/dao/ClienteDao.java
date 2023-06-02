package br.com.alura.dao;

import br.com.alura.modelo.Cliente;

import javax.persistence.EntityManager;

public class ClienteDao {

    private EntityManager em;

    public ClienteDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar (Cliente cliente) {
        this.em.persist(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return this.em.find(Cliente.class, id);
    }

}

package br.com.alura.testes;

import br.com.alura.dao.CategoriaDAO;
import br.com.alura.dao.ProdutoDAO;
import br.com.alura.modelo.Categoria;
import br.com.alura.modelo.Produto;
import br.com.alura.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class CadastroDeProdutos {

    public static void main(String[] args) {

        cadastrarNovoProduto();
        cadastrarNovoProduto();
        cadastrarNovoProduto();
        EntityManager em = JPAUtil.getEM();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);

        produtoDAO.remover(produtoDAO.buscarPorID(1l));

        produtoDAO.buscarPorNome("iPhone 14 ProMax").forEach(s -> System.out.println(s.getNome()));
        produtoDAO.buscarPorNomeCategoria("CELULARES").forEach(s -> System.out.println(s.getNome()));

        System.out.println("Preco do Produto: " + produtoDAO.buscarPrecoPorID(1l));

    }

    private static void cadastrarNovoProduto() {
        Categoria celulares = new Categoria("CELULARES");
        Produto celular = new Produto("iPhone 14 ProMax", "Novo lançamento no mercado!",
                new BigDecimal(15000), celulares);

        EntityManager em = JPAUtil.getEM();

        em.getTransaction().begin();

        new CategoriaDAO(em).cadastrar(celulares);
        new ProdutoDAO(em).cadastrar(celular);

        em.getTransaction().commit();
        em.close();
    }
}

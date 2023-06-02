package br.com.alura.testes;

import br.com.alura.dao.CategoriaDao;
import br.com.alura.dao.ProdutoDao;
import br.com.alura.modelo.Categoria;
import br.com.alura.modelo.CategoriaId;
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
        ProdutoDao produtoDAO = new ProdutoDao(em);

        produtoDAO.remover(produtoDAO.buscarPorID(1l));

        produtoDAO.buscarPorNome("iPhone 14 ProMax").forEach(s -> System.out.println(s.getNome()));
        produtoDAO.buscarPorNomeCategoria("CELULARES").forEach(s -> System.out.println(s.getNome()));

        System.out.println("Preco do Produto: " + produtoDAO.buscarPrecoPorID(1l));


        // FAZENDO BUSCA PELO ID DA CATEGORIA

        em.find(Categoria.class, new CategoriaId("CELULARES", "abcd"));

    }

    private static void cadastrarNovoProduto() {
        Categoria celulares = new Categoria("CELULARES");
        Produto celular = new Produto("iPhone 14 ProMax", "Novo lançamento no mercado!",
                new BigDecimal(15000), celulares);

        EntityManager em = JPAUtil.getEM();

        em.getTransaction().begin();

        new CategoriaDao(em).cadastrar(celulares);
        new ProdutoDao(em).cadastrar(celular);

        em.getTransaction().commit();
        em.close();
    }
}

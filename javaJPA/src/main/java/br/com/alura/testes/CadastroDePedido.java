package br.com.alura.testes;

import br.com.alura.dao.CategoriaDao;
import br.com.alura.dao.ClienteDao;
import br.com.alura.dao.PedidoDao;
import br.com.alura.dao.ProdutoDao;
import br.com.alura.modelo.*;
import br.com.alura.util.JPAUtil;
import br.com.alura.vo.RelatorioVendasVO;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDePedido {

    public static void main(String[] args) {
        popularBancoDeDados();
        EntityManager em = JPAUtil.getEM();

        Produto produto1 = new ProdutoDao(em).buscarPorID(1L);
        Produto produto2 = new ProdutoDao(em).buscarPorID(2L);
        Produto produto3 = new ProdutoDao(em).buscarPorID(3L);
        Cliente cliente = new ClienteDao(em).buscarPorId(1L);


        em.getTransaction().begin();

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(5, pedido, produto1));
        pedido.adicionarItem(new ItemPedido(9, pedido, produto2));
        pedido.adicionarItem(new ItemPedido(2, pedido, produto3));

        new PedidoDao(em).cadastrar(pedido);

        em.getTransaction().commit();

        System.out.println("Valor total vendido foi de R$" + new PedidoDao(em).valorTotalVendido());

        System.out.println();
        System.out.println();
        System.out.println();

        List<RelatorioVendasVO> relatorio = new PedidoDao(em).relatorioVendas();

        relatorio.forEach(System.out::println);

    }

    private static void popularBancoDeDados() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria notebooks = new Categoria("NOTEBOOKS");
        Categoria videogames = new Categoria("CONSOLES");

        Produto celular = new Produto("iPhone 14 ProMax", "Novo lançamento no mercado!",
                new BigDecimal(15000), celulares);
        Produto notebook = new Produto("Lenovo AMD Ryzen 5500U", "Notebook Lenovo Custo-Benefício",
                new BigDecimal(2500), notebooks);
        Produto ps5 = new Produto("Playstation 5", "Console da última geração",
                new BigDecimal(4000), videogames);

        Cliente cliente = new Cliente("Rodrigo", "123456");

        EntityManager em = JPAUtil.getEM();

        em.getTransaction().begin();

        new CategoriaDao(em).cadastrar(celulares);
        new CategoriaDao(em).cadastrar(notebooks);
        new CategoriaDao(em).cadastrar(videogames);
        new ProdutoDao(em).cadastrar(celular);
        new ProdutoDao(em).cadastrar(notebook);
        new ProdutoDao(em).cadastrar(ps5);
        new ClienteDao(em).cadastrar(cliente);

        em.getTransaction().commit();
        em.close();
    }
}

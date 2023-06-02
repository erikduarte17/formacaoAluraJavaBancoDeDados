package br.com.alura.dao;

import br.com.alura.modelo.Pedido;
import br.com.alura.vo.RelatorioVendasVO;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDao {

    private EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    public Pedido buscarPorId(Long id) {
        return this.em.find(Pedido.class, id);
    }

    public void cadastrar(Pedido pedido) {
        this.em.persist(pedido);
    }

    public BigDecimal valorTotalVendido () {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
        return this.em.createQuery(jpql, BigDecimal.class)
                .getSingleResult();
    }

    public List<RelatorioVendasVO> relatorioVendas () {
        String jpql = "SELECT new br.com.alura.vo.RelatorioVendasVO(" +
                "produto.nome, " +
                "SUM(item.quantidade), " +
                "MAX(pedido.data)) " +
                "FROM Pedido pedido " +
                "JOIN pedido.itens item " +
                "JOIN item.produto produto " +
                "GROUP BY produto.nome " +
                "ORDER BY SUM(item.quantidade) DESC";
        return this.em.createQuery(jpql, RelatorioVendasVO.class)
                .getResultList();
    }

    public Pedido buscarPedidoComCliente (Long id) {
        return this.em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}

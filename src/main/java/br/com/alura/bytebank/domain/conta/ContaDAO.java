package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {

    private Connection connection;

    ContaDAO (Connection connection) {
        this.connection = connection;
    }
    public void save(DadosAberturaConta dadosDaConta) {
        var cliente = new Cliente(dadosDaConta.dadosCliente());
        var conta = new Conta(dadosDaConta.numero(), BigDecimal.ZERO, cliente, true);

        String sql = "INSERT INTO conta (numero, saldo, cliente_nome, cliente_cpf, cliente_email, esta_ativa)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, conta.getNumero());
            preparedStatement.setBigDecimal(2, BigDecimal.ZERO);
            preparedStatement.setString(3, conta.getTitular().getNome());
            preparedStatement.setString(4, conta.getTitular().getCpf());
            preparedStatement.setString(5, conta.getTitular().getEmail());
            preparedStatement.setBoolean(6, conta.getEstaAtiva());

            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Conta> listar() {
        Set<Conta> contas = new HashSet<>();
        String sql = "SELECT * FROM conta WHERE esta_ativa = true;";

        try {
            ResultSet rs;
            PreparedStatement ps;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                int num = rs.getInt(1);
                BigDecimal saldo = rs.getBigDecimal(2);
                String cpf = rs.getString(3);
                String nome = rs.getString(4);
                String email = rs.getString(5);
                Boolean estaAtiva = rs.getBoolean(6);

                Conta conta = new Conta(num, saldo, new Cliente(new DadosCadastroCliente(nome, cpf, email)), estaAtiva);
                contas.add(conta);
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return contas;
    }

    public Conta listarPorNumero(Integer numero) {
        String sql = "SELECT * FROM conta WHERE numero = ? AND esta_ativa = true;";
        Conta conta = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int num = rs.getInt(1);
                BigDecimal saldo = rs.getBigDecimal(2);
                String cpf = rs.getString(3);
                String nome = rs.getString(4);
                String email = rs.getString(5);
                Boolean estaAtiva = rs.getBoolean(6);

                conta = new Conta(num, saldo, new Cliente(new DadosCadastroCliente(nome, cpf, email)), estaAtiva);
            }

            ps.close();
            rs.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conta;

    }
    
    public void alterar(Integer numero, BigDecimal valor) {
        String sql = "UPDATE conta SET saldo = ? WHERE numero = ?;";
        PreparedStatement ps;

        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(sql);
            ps.setBigDecimal(1, valor);
            ps.setInt(2, numero);

            ps.execute();
            connection.commit();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        
    }

    public void deletar(Integer numero) {
        String sql = "DELETE FROM conta WHERE numero = ?";
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, numero);

            ps.execute();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void desativaOuAtiva(Integer numero, Boolean estado) {
        String sql = "UPDATE conta SET esta_ativa = ? WHERE numero = ?;";
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setBoolean(1, false);
            ps.setInt(2, numero);

            ps.execute();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

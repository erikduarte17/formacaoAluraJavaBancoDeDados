package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.ConnectionFactory;
import br.com.alura.bytebank.domain.RegraDeNegocioException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

public class ContaService {

    private Set<Conta> contas = new HashSet<>();
    private ConnectionFactory connection;
    public ContaService () {
        this.connection = new ConnectionFactory();
    }

    public Set<Conta> listarContasAbertas() {
        Connection con = connection.returnConnections();
        return new ContaDAO(con).listar();
    }

    public BigDecimal consultarSaldo(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        return conta.getSaldo();
    }

    public void abrir(DadosAberturaConta dadosDaConta) {
        Connection con = connection.returnConnections();
        new ContaDAO(con).save(dadosDaConta);
    }

    public void realizarSaque(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do saque deve ser superior a zero!");
        }

        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new RegraDeNegocioException("Saldo insuficiente!");
        }

        if (conta.getEstaAtiva() == false) {
            throw new RegraDeNegocioException("Conta Inativa!");
        }

        alterar(conta.getNumero(), conta.getSaldo().subtract(valor));
    }

    public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do deposito deve ser superior a zero!");
        }

        if (conta.getEstaAtiva() == false) {
            throw new RegraDeNegocioException("Conta Inativa!");
        }

        alterar(conta.getNumero(), conta.getSaldo().add(valor));
    }

    public void realizarTransferencia(int numeroDaContaOrigem, int numeroDaContaDestino, BigDecimal valor) {
        realizarSaque(numeroDaContaOrigem, valor);
        realizarDeposito(numeroDaContaDestino, valor);
    }

    private void alterar(Integer numero, BigDecimal valor) {
        new ContaDAO(connection.returnConnections()).alterar(numero, valor);
    }

    public void encerrar(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (conta.possuiSaldo()) {
            throw new RegraDeNegocioException("Conta não pode ser encerrada pois ainda possui saldo!");
        }

        new ContaDAO(connection.returnConnections()).deletar(conta.getNumero());
    }

    public void alternarEstadoConta(Integer numeroDaConta, Boolean estadoConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if(conta.possuiSaldo()) {
            throw new RegraDeNegocioException("Conta não pode ser desativada pois ainda possui saldo!");
        }

        new ContaDAO(connection.returnConnections()).desativaOuAtiva(conta.getNumero(), estadoConta);
    }

    private Conta buscarContaPorNumero(Integer numero) {
        Connection con = this.connection.returnConnections();
        Conta conta = new ContaDAO(con).listarPorNumero(numero);
        if(conta != null) {
            return conta;
        } else {
            throw new RegraDeNegocioException("Não existe conta cadastrada com esse número!");
        }
    }
}

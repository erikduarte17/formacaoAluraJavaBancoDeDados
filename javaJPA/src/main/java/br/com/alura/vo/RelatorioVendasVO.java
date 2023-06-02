package br.com.alura.vo;

import java.time.LocalDate;

public class RelatorioVendasVO {

    private String nomeProduto;
    private Long quantidade;
    private LocalDate dataUltimaVenda;

    public RelatorioVendasVO () {
    }
    public RelatorioVendasVO (String nomeProduto, Long quantidade, LocalDate dataUltimaVenda) {
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.dataUltimaVenda = dataUltimaVenda;
    }

    @Override
    public String toString() {
        return nomeProduto + " vendidos ao todo " + quantidade + " itens, a última venda foi em: " + dataUltimaVenda;
    }
}

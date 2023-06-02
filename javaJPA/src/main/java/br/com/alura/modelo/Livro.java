package br.com.alura.modelo;

import javax.persistence.Entity;

@Entity
public class Livro extends Produto {

    private String autor;
    private Integer numeroPaginas;

    public Livro () {
    }

    public Livro(String autor, Integer numeroPaginas) {
        this.autor = autor;
        this.numeroPaginas = numeroPaginas;
    }

    public String getAutor() {
        return autor;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }
}

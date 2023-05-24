package br.com.alura.modelo;

import javax.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public Categoria() {
    }

    public Categoria(String nome) {
        this.nome = nome.toUpperCase();
    }

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }
}

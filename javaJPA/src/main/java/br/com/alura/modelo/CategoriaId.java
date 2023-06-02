package br.com.alura.modelo;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CategoriaId implements Serializable {

    private String nome;
    private String tipo;

    public CategoriaId(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }
}

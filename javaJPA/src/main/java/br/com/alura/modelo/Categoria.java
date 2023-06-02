package br.com.alura.modelo;

import javax.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    @EmbeddedId
    private CategoriaId id;

    public Categoria() {
    }

    public Categoria(String nome) {
        id = new CategoriaId(nome, "abcd");
    }

    public String getNome() {
        return this.id.getNome();
    }
}

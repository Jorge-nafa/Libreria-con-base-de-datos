package com.aluracursos.desafio.Modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name="libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    private Double numDescargas;


    private String idiomas;


    @OneToMany(mappedBy = "libro",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autores>autor;



    public Libro(DatosLibros libroAguardar) {
        this.titulo=libroAguardar.titulo();
        this.numDescargas=libroAguardar.numDescargas();
        this.idiomas=libroAguardar.idiomas().stream().findFirst().get();
        this.autor=libroAguardar.autor().stream()
                .map(e-> new Autores(e))
                .collect(Collectors.toList());
    }
    public Libro(){}

    @Override
    public String toString() {

        String retornar= """
                \n************************ Libro ************************"""
                + "\nTitulo: "+titulo+"\nNumero Descargas: "+numDescargas
                +"\nIdiomas: "+idiomas+"\nAutores: "+autor.stream().map(Autores::getNombreAutor).findFirst().get()
                +"\n*****************************************************";
        return retornar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getNumDescargas() {
        return numDescargas;
    }

    public void setNumDescargas(Double numDescargas) {
        this.numDescargas = numDescargas;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public List<Autores> getAutor() {
        return autor;
    }

    public void setAutor(List<Autores> autor) {
        autor.forEach(e->e.setLibro(this));
        this.autor = autor;
    }
}

package com.aluracursos.desafio.Modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreAutor;
    private String nacimiento;
    private String fallecimiento;

    @ManyToOne
    private Libro libro;

    public Autores(){}

    public Autores(DatosAutor e) {
        this.nombreAutor=e.nombreAutor();
        this.nacimiento=e.nacimiento();
        this.fallecimiento=e.fallecimiento();
    }

    @Override
    public String toString() {

        return "Autores { " +
                "id=" + id +
                ", nombreAutor='" + nombreAutor + '\'' +
                ", nacimiento='" + nacimiento + '\'' +
                ", fallecimiento='" + fallecimiento +
                " }";
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public String getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(String fallecimiento) {
        this.fallecimiento = fallecimiento;
    }
}

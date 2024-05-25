package com.aluracursos.desafio.repository;

import com.aluracursos.desafio.Modelos.Autores;
import com.aluracursos.desafio.Modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface repository extends JpaRepository <Libro,Long> {


        @Query("SELECT a FROM Autores a")
        List<Autores> muestraAutores();

        @Query("SELECT a FROM Autores a WHERE  a.fallecimiento >= :busqueda AND a.nacimiento <= :busqueda")
        List<Autores> buscarPorFecha(Integer busqueda);

        List<Libro> findByIdiomasContainsIgnoreCase(String lowerCase);

        List<Libro> findTop3ByOrderByNumDescargasDesc();

}

package com.aluracursos.desafio.Modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
public record datos(
        @JsonAlias ("results") List<DatosLibros> listaDeLibros
) {

}

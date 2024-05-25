package com.aluracursos.desafio.servicios;

public interface IconvierteDatos {
    <T> T obtenerDatos(String json,Class<T> Clase);
}

package com.aluracursos.desafio_literatura.service;

public interface IConvierteDatos {

    //Metodo para obtener datos de tipo Genericos
    <T> T obtenerDatos(String json, Class<T> clase);
}

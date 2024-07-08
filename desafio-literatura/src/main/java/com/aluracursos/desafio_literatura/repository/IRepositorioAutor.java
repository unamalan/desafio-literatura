package com.aluracursos.desafio_literatura.repository;

import com.aluracursos.desafio_literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IRepositorioAutor extends JpaRepository<Autor, Long> {

    Autor findByNombreIgnoreCase(String nombre);


    @Query("SELECT a  FROM  Autor a WHERE :anio BETWEEN a.fechaNacimiento AND a.fechaMuerte")
    List<Autor> autoresVivosPorAnio(int anio);
}

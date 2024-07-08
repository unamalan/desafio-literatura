package com.aluracursos.desafio_literatura.repository;

import com.aluracursos.desafio_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IRepositorioLibros extends JpaRepository<Libro, Long> {
    Libro findByTituloContainsIgnoreCase(String titulo);


    @Query("SELECT b FROM Libro b WHERE b.lenguajes = :lenguajes")
    List<Libro> findByLenguajes(String lenguajes);


//    @Query("SELECT b FROM Libro b ORDER BY b.totalDescargas DESC LIMIT 5")
//    List<Libro> top5Descargas();
}

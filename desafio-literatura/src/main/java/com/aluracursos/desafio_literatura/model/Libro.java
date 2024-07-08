package com.aluracursos.desafio_literatura.model;


import jakarta.persistence.*;


@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String titulo;
    private String lenguajes;
    private double totalDescargas;

    @ManyToOne
    private Autor autor;

    public Libro() {
    }

    public Libro(DatosLibros datosLibros, Autor datosAutor) {
        this.titulo = datosLibros.titulo();
        this.autor = datosAutor;
        this.lenguajes = datosLibros.lenguajes().get(0);
        this.totalDescargas = datosLibros.totalDescargas();
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(String lenguajes) {
        this.lenguajes = lenguajes;
    }

    public double getTotalDescargas() {
        return totalDescargas;
    }

    public void setTotalDescargas(double totalDescargas) {
        this.totalDescargas = totalDescargas;
    }

    @Override
    public String toString() {
        return """
                ------ LIBRO ------
                Titulo: %s
                Autor: %s
                Idioma: %s 
                Numero de descargas: %s
                """.formatted(titulo, autor.getNombre(), lenguajes, totalDescargas);
    }
}


package com.aluracursos.desafio_literatura.principal;

import com.aluracursos.desafio_literatura.model.*;
import com.aluracursos.desafio_literatura.repository.IRepositorioAutor;
import com.aluracursos.desafio_literatura.repository.IRepositorioLibros;
import com.aluracursos.desafio_literatura.service.ConvierteDatos;
import com.aluracursos.desafio_literatura.service.ConsumoAPI;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private IRepositorioLibros repositorioLibros;
    private IRepositorioAutor repositorioAutor;
    private List<Autor> autores;
    private List<Libro> libros;

    public Principal(IRepositorioLibros repositorioLibros, IRepositorioAutor repositorioAutor) {
        this.repositorioLibros = repositorioLibros;
        this.repositorioAutor = repositorioAutor;
    }


    public void mostrarMenu() {

        int opcion = -1;
        while (opcion != 0) {
            String menu = """
                    
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores registrados vivos en un determinado año
                    5 - Listar libros por idioma                
                    0 - Salir
                    
                    """;
            System.out.println("-------------------------------------");
            System.out.println("Seleccione una opción del menu");
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();

                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosPorAnio();
                    break;

                case 5:
                    mostrarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    //Metodo principal de libros
    private Datos buscarLibro() {
        System.out.println("Ingrese el nombre del libro que quiere buscar: ");
        String nombreLibro = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.toLowerCase().replace(" ", "+"));
        return convierteDatos.obtenerDatos(json, Datos.class);

    }

    //Metodo para buscar libros por titulo
    private void buscarLibroPorTitulo() {
        Datos datos = buscarLibro();
        if (!datos.resultados().isEmpty()) {
            DatosLibros datosLibros = datos.resultados().get(0);
            DatosAutor datosAutor = datosLibros.autor().get(0);
            System.out.println("""
                    =============LIBRO==============
                    Nombre: %s
                    Autor: %s
                    Idioma: %s
                    Numero de descargas: %s
                    =================================
                    """.formatted(datosLibros.titulo(), datosAutor.nombre(), datosLibros.lenguajes(), datosLibros.totalDescargas()));
            Libro busquedaLibro = repositorioLibros.findByTituloContainsIgnoreCase(datosLibros.titulo());

            //Buscar libro existente
            if (busquedaLibro != null) System.out.println("El libro ya existe");
            else {
                Autor busquedaAutor = repositorioAutor.findByNombreIgnoreCase(datosAutor.nombre());

                if (busquedaAutor == null) {
                    Autor autor = new Autor(datosAutor);
                    repositorioAutor.save(autor);
                    Libro libro = new Libro(datosLibros, autor);
                    repositorioLibros.save(libro);

                } else {
                    Libro libro = new Libro(datosLibros, busquedaAutor);
                    repositorioLibros.save(libro);

                }
            }
        } else System.out.println("Libro no encontrado");
    }

    //Metodo para listar los libros registrados
    private void mostrarLibrosRegistrados() {
        try {
            libros = repositorioLibros.findAll();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
            libros.forEach(System.out::println);
        }
    }

    //Metodo para listar los autores registrados
    private void mostrarAutoresRegistrados() {
        autores = repositorioAutor.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados");
        autores.forEach(System.out::println);
        }
    }

    private void mostrarAutoresVivosPorAnio() {
        try {
            System.out.println("Ingrese el año de autor(es) que desea buscar: ");
            int anio = teclado.nextInt();
            autores = repositorioAutor.autoresVivosPorAnio(anio);
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        if (autores.isEmpty()) System.out.println("No hay autores registrados");
        autores.forEach(System.out::println);
    }


    public void mostrarLibrosPorIdioma() {
        List<String> lenguajes = new ArrayList<>();
        lenguajes.add("es");
        lenguajes.add("en");
        lenguajes.add("fr");
        lenguajes.add("pt");

        System.out.println("""
                Ingrese el idioma para buscar los libros
                [ES] - ESPAÑOL
                [EN] - INGLES
                [FR] - FRANCES
                [PT] - PORTUGUES
                """);
        String abreviatura = teclado.nextLine();
        if (!lenguajes.contains(abreviatura.toLowerCase())) System.out.println("Idioma incorrecto");
        repositorioLibros.findByLenguajes(abreviatura).forEach(System.out::println);
    }

}


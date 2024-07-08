package com.aluracursos.desafio_literatura;


import com.aluracursos.desafio_literatura.principal.Principal;
import com.aluracursos.desafio_literatura.repository.IRepositorioAutor;
import com.aluracursos.desafio_literatura.repository.IRepositorioLibros;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioLiteraturaApplication implements CommandLineRunner {
    private final IRepositorioLibros repositorioLibros;
    private final IRepositorioAutor repositorioAutor;

    public DesafioLiteraturaApplication(IRepositorioLibros repositorioLibros, IRepositorioAutor repositorioAutor) {
        this.repositorioLibros = repositorioLibros;
        this.repositorioAutor = repositorioAutor;
    }


    public static void main(String[] args) {
        SpringApplication.run(DesafioLiteraturaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repositorioLibros,repositorioAutor);
        principal.mostrarMenu();
    }
}

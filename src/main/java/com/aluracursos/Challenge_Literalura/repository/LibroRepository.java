package com.aluracursos.Challenge_Literalura.repository;

import com.aluracursos.Challenge_Literalura.model.Lenguajes;
import com.aluracursos.Challenge_Literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long>{

    List<Libro> findByLenguaje_Idioma(String idioma);
    List<Libro> findTop10ByOrderByNumeroDescargasDesc();

}

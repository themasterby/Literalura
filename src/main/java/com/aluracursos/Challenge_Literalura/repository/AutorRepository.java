package com.aluracursos.Challenge_Literalura.repository;

import com.aluracursos.Challenge_Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    List<Autor> findByAñoDeNacimientoLessThanAndAñoDeFallecimientoGreaterThanEqual(int añoDeInicio, int añoDeFinal);
}

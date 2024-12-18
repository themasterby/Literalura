package com.aluracursos.Challenge_Literalura.repository;

import com.aluracursos.Challenge_Literalura.model.Lenguajes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LenguajesRepository extends JpaRepository<Lenguajes, Long> {
    Lenguajes findByIdioma(String idioma);
}

package com.aluracursos.Challenge_Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
         @JsonAlias("title") String titulo,
         @JsonAlias("authors") List<DatosAutores> autores,
         @JsonAlias("languages") List<String> lenguajes,
         @JsonAlias("download_count") double numeroDescargas) {
}

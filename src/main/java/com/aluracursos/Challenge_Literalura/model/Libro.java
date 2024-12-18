package com.aluracursos.Challenge_Literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "lenguaje_id", nullable = false)
    private Lenguajes lenguaje;
    private double numeroDescargas;

    public Libro(){}

    public Libro(DatosLibro d, Autor autor, Lenguajes lenguaje){
        this.titulo = d.titulo();
        this.autor = autor;
        this.lenguaje = lenguaje;
        this.numeroDescargas = d.numeroDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Lenguajes getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(Lenguajes lenguajes) {
        this.lenguaje = lenguajes;
    }

    public double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "====================================================\n" +
                "\n********************** Libro **********************\n" +
                "\nTitulo: " + titulo +
                "\nAutor: " + autor.getNombre() +
                "\nLenguajes: " + lenguaje +
                "\nNumero de Descargas: " + numeroDescargas + "\n";
    }
}

package com.aluracursos.Challenge_Literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int añoDeNacimiento;
    private int añoDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor(){}

    public Autor(DatosAutores datosAutores){
        this.nombre = datosAutores.nombre();
        this.añoDeNacimiento = Integer.valueOf(datosAutores.añoDeNacimiento());
        this.añoDeFallecimiento = Integer.valueOf(datosAutores.añoDeFallecimiento());
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAñoDeNacimiento() {
        return añoDeNacimiento;
    }

    public void setAñoDeNacimiento(int añoDeNacimiento) {
        this.añoDeNacimiento = añoDeNacimiento;
    }

    @Override
    public String toString() {
        return "====================================================\n" +
                "\n********************** Autores **********************\n" +
                "\nNombre: " + nombre +
                "\nAñoDeNacimiento: " + añoDeNacimiento +
                "\nAñoDeFallecimiento: " + añoDeFallecimiento + "\n";
    }
}

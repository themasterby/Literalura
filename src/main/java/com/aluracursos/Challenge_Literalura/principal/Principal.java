package com.aluracursos.Challenge_Literalura.principal;

import com.aluracursos.Challenge_Literalura.model.*;
import com.aluracursos.Challenge_Literalura.services.ConsumoAPI;
import com.aluracursos.Challenge_Literalura.services.ConvierteDatos;
import com.aluracursos.Challenge_Literalura.repository.AutorRepository;
import com.aluracursos.Challenge_Literalura.repository.LenguajesRepository;
import com.aluracursos.Challenge_Literalura.repository.LibroRepository;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_LIBROS = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private AutorRepository autorRepositorio;
    private LenguajesRepository lenguajeRepositorio;
    private List<Libro> libros;
    private List<DatosAutores> datosAutores;
    List<Autor> autores;

    public Principal(LibroRepository repositorio, AutorRepository autorRepositorio, LenguajesRepository lenguajeRepositorio){
        this.repositorio = repositorio;
        this.autorRepositorio = autorRepositorio;
        this.lenguajeRepositorio = lenguajeRepositorio;
    }

    public void muestraElMenu(){
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n==================== Menú ====================
                    
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Listar top 10 Libros por número de Descargas
                    7 - Mostrar estadísticas
                 
                    0 - Salir
                    
                    Escriba la opción deseada:
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                   listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnDeterminadoAño();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                   listarTop5Libros();
                    break;
                case 7:
                    mostrarEstadisticas();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_LIBROS+"?search="+ nombreLibro.replace(" ", "%20"));
        var datosBusqueda = conversor.obtenerDatos(json, DatosGenerales.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.listaDeLibros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        if(libroBuscado.isPresent()){
            DatosLibro libro = libroBuscado.get();
            System.out.println("Libro encontrado");
            System.out.println(libroBuscado.get());
            datosAutores = libroBuscado.get().autores();

            return libro;
        }else {
            System.out.println("Libro no encontrado");
        }
        return null;
    }

    private void buscarLibroWeb() {
        DatosLibro datos = getDatosLibro();
        if (datos == null) return;

        Autor autor;
        if (datosAutores.isEmpty()) {
            // Si no se encontraron autores para este libro. Se asignará un autor por defecto
            autor = new Autor();
            autor.setNombre("Autor desconocido");
            autorRepositorio.save(autor);
        } else {
            autor = new Autor(datosAutores.get(0));
            autor = autorRepositorio.save(autor);
        }

        String idioma;
        Lenguajes lenguaje;
        if (datos.lenguajes().isEmpty()) {
            // Si no se encontraron lenguajes para este libro. Se asignará un idioma por defecto
            idioma = "es";
            lenguaje = lenguajeRepositorio.findByIdioma(idioma);
            if (lenguaje == null) {
                lenguaje = new Lenguajes(idioma);
                lenguaje = lenguajeRepositorio.save(lenguaje);
            }
        } else {
            idioma = datos.lenguajes().get(0);
            lenguaje = lenguajeRepositorio.findByIdioma(idioma);
            if (lenguaje == null) {
                lenguaje = new Lenguajes(idioma);
                lenguaje = lenguajeRepositorio.save(lenguaje);
            }
        }
        Libro libro = new Libro(datos, autor, lenguaje);
        repositorio.save(libro);
        System.out.println("Libro guardado exitosamente.");
    }

    private void listarLibros(){
        libros = repositorio.findAll();
        libros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados(){
       autores = autorRepositorio.findAll();

       autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void listarAutoresVivosEnDeterminadoAño(){
        System.out.println("Indique el año de inicio: ");
        var añoDeInicio = teclado.nextInt();
        System.out.println("Indique el año de final: ");
        var añoDeFinal = teclado.nextInt();

        autores = autorRepositorio.findByAñoDeNacimientoLessThanAndAñoDeFallecimientoGreaterThanEqual(añoDeInicio, añoDeFinal);
        if (autores.isEmpty()){
            System.out.println("No se encontraron autores.");
        }else {
            autores.forEach(System.out::println);
        }
    }

    private void listarLibrosPorIdioma(){
        System.out.println("Indique el Idioma que desea buscar: ");
        System.out.println("""
            es - Español
            en - Ingles
            fr - Frances
            pt - portugués
            """);
        var idiomaLibro = teclado.nextLine();
        List<Libro> librosEnLenguaje = repositorio.findByLenguaje_Idioma(idiomaLibro);

        if (librosEnLenguaje.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma " + idiomaLibro);
        } else {
            System.out.println("Libros en el idioma " + idiomaLibro + ":");
            librosEnLenguaje.forEach(System.out::println);
        }
    }

    private void listarTop5Libros(){
        List<Libro> top5Libros =repositorio.findTop10ByOrderByNumeroDescargasDesc();
        top5Libros.forEach(l ->
                System.out.println("\nLibro: " + l.getTitulo() + "\nNúmero de Descargas: " + l.getNumeroDescargas()));
    }

    private void mostrarEstadisticas(){
        libros = repositorio.findAll();
        DoubleSummaryStatistics est = libros.stream()
                .filter(l -> l.getNumeroDescargas() > 1)
                .collect(Collectors.summarizingDouble(Libro::getNumeroDescargas));

        System.out.println("=========== Estadísticas ===========");
        System.out.println("Media de descargas: "+ est.getAverage());
        System.out.println("Número mayor de descargas: "+ est.getMax());
        System.out.println("Número menor de descargas: "+ est.getMin());
        System.out.println("Número de registros contados: "+ est.getCount());
    }

}

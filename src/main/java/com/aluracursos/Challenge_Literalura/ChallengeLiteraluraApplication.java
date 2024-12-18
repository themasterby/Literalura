	package com.aluracursos.Challenge_Literalura;

	import com.aluracursos.Challenge_Literalura.principal.Principal;
	import com.aluracursos.Challenge_Literalura.repository.AutorRepository;
	import com.aluracursos.Challenge_Literalura.repository.LenguajesRepository;
	import com.aluracursos.Challenge_Literalura.repository.LibroRepository;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.CommandLineRunner;
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;

	@SpringBootApplication
	public class ChallengeLiteraluraApplication implements CommandLineRunner {

		@Autowired
		private LibroRepository repositorio;
		@Autowired
		private AutorRepository autorRepositorio;
		@Autowired
		private LenguajesRepository lenguajeRepositorio;

		public static void main(String[] args) {
			SpringApplication.run(ChallengeLiteraluraApplication.class, args);
		}

		@Override
		public void run(String... args) throws Exception {
			// pasamos el repository como parametro aquí para poder utilizarlo en nuestra clase principal
			Principal principal = new Principal(repositorio, autorRepositorio, lenguajeRepositorio);
			principal.muestraElMenu();
		}
	}

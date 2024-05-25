package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.Modelos.*;
import com.aluracursos.desafio.repository.repository;
import com.aluracursos.desafio.servicios.ConsumoAPI;
import com.aluracursos.desafio.servicios.ConversorDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private  repository repository;
    private Scanner teclado=new Scanner(System.in);
    private ConsumoAPI consumoAPI= new ConsumoAPI();
    private ConversorDatos conversorDatos= new ConversorDatos();
    private final String URL_BASE="https://gutendex.com/books/?search=";

    public void menu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    
                    
                    1 - Buscar libro   
                    2 - Mostrar Libros Buscados
                    3 - Mostrar Autores
                    4 - Buscar autores por fecha 
                    5 - Buscar libros por idioma
                    6 - Top 3 libros
                    
                    
                    0 - Salir
             
                    Escoja una opcion:
                    """;
            try {
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();
            }catch (Exception e){
                System.out.println("Error opcion no valida");
                teclado.nextLine();
            }

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    mostrarAutores();
                    break;
                case 4:
                    buscarAutoresPorFecha();
                    break;
                case 5:
                    buscarLibroPorIdioma();
                    break;
                case 6:
                    top5libros();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;

                default:
                    System.out.println("Opción inválida");
            }
        }
    }


    public Principal(repository repository) {
        this.repository=repository;
    }

    private void buscarLibro(){
         System.out.println("Nombre del libro a buscar:");
         var libro =teclado.nextLine();
         var json=consumoAPI.obtenerDatos(URL_BASE+libro.replace(" ","+"));
         try {
             var Busqueda=conversorDatos.obtenerDatos(json, datos.class);
             Optional<DatosLibros> libroBuscado=Busqueda.listaDeLibros().stream()
                     .filter(l->l.titulo().toLowerCase().contains(libro.toLowerCase()))
                     .findFirst();
             if (libroBuscado.isPresent()){

                 System.out.println("Libro encontrado");
                 DatosLibros libroAguardar= libroBuscado.get();
                 Libro guardarLibro=new Libro(libroAguardar);

                 List<DatosAutor> guardar=libroBuscado.get().autor();
                 List<Autores> autoresAguarda=guardar.stream().map(e-> new Autores(e))
                         .collect(Collectors.toList());
                 guardarLibro.setAutor(autoresAguarda);
                 repository.save(guardarLibro);

                 System.out.println(guardarLibro.toString());
             }else {
                 System.out.println("Libro no encontrado o ya se encuentra registrado");
             }
         }catch (Exception e){}
     }

    private void mostrarLibrosBuscados() {

        List<Libro> libros=repository.findAll();
        libros.stream().sorted(Comparator.comparing(Libro::getId)).forEach(System.out::println);

    }

    private void mostrarAutores() {
        List<Autores> autores=repository.muestraAutores();
        imprimirAutores(autores);
    }

    private void buscarAutoresPorFecha() {
        try {
            System.out.println("Fecha de busqueda: ");
            Integer busqueda= teclado.nextInt();
            List <Autores> autores=repository.buscarPorFecha(busqueda);
            if (autores.isEmpty()){
                System.out.println("No hay autores");
            }else {
                System.out.println("Autores que vivian en el año "+busqueda +" : \n");
                imprimirAutores(autores);
            }
        }catch (Exception e){
            System.out.println("Año no valido");
            teclado.nextLine();
        }

    }

    private  void imprimirAutores(List<Autores> autores){
        autores.stream().forEach(e-> System.out.println(
                "-----------Autor----------- \nAutor: "+e.getNombreAutor()+
                        "\nNacimiento: "+e.getNacimiento()+"\nFallecimiento: "
                        +e.getFallecimiento()+ "\nLibro: "+
                        e.getLibro().getTitulo()+"\n--------------------------\n"
        ));
    }

    private void buscarLibroPorIdioma() {
        System.out.println("\nes-Español" +
                "\nfr-Frances" +
                "\nen-Ingles" +
                "\nInserte el idioma a buscar:");
        String idiomaABuscar= teclado.nextLine();
        idiomaABuscar.toLowerCase();
        List<Libro> libros=repository.findByIdiomasContainsIgnoreCase(idiomaABuscar);

        if (libros.isEmpty()){
            System.out.println("No hay libros registrados en "+idiomaABuscar);
        }else {
            libros.stream().sorted(Comparator.comparing(Libro::getId)).forEach(System.out::println);
        }

    }

    private void top5libros() {
        List<Libro> top=repository.findTop3ByOrderByNumDescargasDesc();
        top.stream().forEach(System.out::println);
    }

}

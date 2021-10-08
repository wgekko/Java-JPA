/*
 * Sistema de Guardado de una Librería
El objetivo de este ejercicio es el desarrollo de un sistema de guardado de libros en JAVA
utilizando una base de datos MySQL y JPA como framework de persistencia.
Creación de la Base de Datos MySQL:
Lo primero que se debe hacer es crear la base de datos sobre el que operará el sistema de
reservas de libros. Para ello, se debe abrir el IDE de base de datos que se está utilizando
(Workbench) y ejecutar la siguiente sentencia:
CREATE DATABASE libreria;
De esta manera se habrá creado una base de datos vacía llamada librería.
Paquetes del Proyecto Java:
Los paquetes que se utilizarán para este proyecto son los siguientes:
Ø entidades: en este paquete se almacenarán aquellas clases que se quiere
persistir en la base de datos.
Ø servicios: en este paquete se almacenarán aquellas clases que llevarán adelante
la lógica del negocio. En general se crea un servicio para administrar las
operaciones CRUD (Create, Remove, Update, Delele) cada una de las entidades
y las consultas de cada entidad.
Nota: En este proyecto vamos a eliminar entidades, pero no es considerado una
buena practica. Por esto, además de eliminar nuestras entidades, vamos a practicar
que nuestras entidades estén dados de alta o de baja. Por lo que las entidades
tendrán un atributo alta booleano, que estará en true al momento de crearlas y en
false cuando las demos de baja, que sería cuando se quiere eliminar esa entidad
Entidad Libro
La entidad libro modela los libros que están disponibles en la biblioteca para ser
prestados. En esta entidad, el atributo “ejemplares” contiene la cantidad total de
ejemplares de ese libro, mientras que el atributo “ejemplaresPrestados” contiene
cuántos de esos ejemplares se encuentran prestados en este momento y el atributo
“ejemplaresRestantes” contiene cuántos de esos ejemplares quedan para prestar.
Entidad Autor
La entidad autor modela los autores de libros.
Entidad Editorial
La entidad editorial modela las editoriales que publican libros.
b) Unidad de Persistencia
Para configurar la unidad de persistencia del proyecto, se recomienda leer el Instructivo
Unidad de Persistencia recuerde hacer click con el botón derecho sobre el proyecto y
seleccionar nuevo. A continuación, se debe seleccionar la opción de Persistence Unit
como se indica en la siguiente imagen.
Base de Datos
Para este proyecto nos vamos a conectar a la base de datos Librería, que creamos
previamente.
Generación de Tablas
La estrategia de generación de tablas define lo que hará JPA en cada ejecución, si debe
crear las tablas faltantes, si debe eliminar todas las tablas y volver a crearlas o no hacer
nada. Recomendamos en este proyecto utilizar la opción: “Create”
Librería de Persistencia
Se debe seleccionar para este proyecto la librería “EclipseLink”.
c) Servicios
AutorServicio
Esta clase tiene la responsabilidad de llevar adelante las funcionalidades necesarias
para administrar autores (consulta, creación, modificación y eliminación).
EditorialServicio
Esta clase tiene la responsabilidad de llevar adelante las funcionalidades necesarias
para administrar editoriales (consulta, creación, modificación y eliminación).
LibroServicio
Esta clase tiene la responsabilidad de llevar adelante las funcionalidades necesarias
para administrar libros (consulta, creación, modificación y eliminación).
18
d) Main
Esta clase tiene la responsabilidad de llevar adelante las funcionalidades necesarias
para interactuar con el usuario. En esta clase se muestra el menú de opciones con las
operaciones disponibles que podrá realizar el usuario.
e) Tareas a Realizar
Al alumno le toca desarrollar, las siguientes funcionalidades:
1) Crear base de datos Librería
2) Crear unidad de persistencia
3) Crear entidades previamente mencionadas ( excepto Préstamo )
4) Generar las tablas con JPA
5) Crear servicios previamente mencionados.
6) Crear los métodos para persistir entidades en la base de datos librería
7) Crear los métodos para dar de alta/bajo o editar dichas entidades.
8) Búsqueda de un Autor por nombre.
9) Búsqueda de un libro por ISBN.
10) Búsqueda de un libro por Título.
11) Búsqueda de un libro/s por nombre de Autor.
12) Búsqueda de un libro/s por nombre de Editorial.
13) Agregar las siguientes validaciones a todas las funcionalidades de la aplicación:
• Validar campos obligatorios.
• No ingresar datos duplicados.
 */
package Servicio;

import Entidad.Autor;
import Entidad.Editorial;
import Entidad.Libro;
import Pesistencia.AutorPersistencia;
import Pesistencia.EditorialPersistencia;
import Pesistencia.LibroPersistencia;
import java.util.List;

/**
 *
 * @author WALTER GOMEZ
 */
public class LibroServicio {

    private final LibroPersistencia libroPer;
    private final AutorPersistencia autorPer;
    private final EditorialPersistencia editorialPer;

    public LibroServicio() {
        this.libroPer = new LibroPersistencia();
        this.autorPer = new AutorPersistencia();
        this.editorialPer = new EditorialPersistencia();
    }
    
    public Integer ejemplaresRestantes(){
        Libro lib = new Libro();        
        Integer ejRestantes = lib.getNroEjemplares()-lib.getEjemplaresPrestados();
        lib.setEjemplaresRestantes(ejRestantes);        
        return ejRestantes;
    }
    public void crearLibro(Long isbn, String titulo, Integer anio, Integer nroEjemplares, Integer ejemplaresPrestados, boolean alta,  Autor autor, Editorial editorial) throws Exception {

        try {
            //Validamos            
            if (isbn == null || isbn.intValue()<10 || isbn.intValue()>13 ) {
                throw new Exception("Debe ingresar un valor no nulo indicar el correo electrónico");
            }
            if (titulo== null || titulo.trim().isEmpty() || titulo.length()<3) {
                throw new Exception("El titulo es incorrecto");
            }
            if ( anio < 1 ) {
                throw new Exception("el año es incorrecto ");
            }
             if (nroEjemplares == null || nroEjemplares<=0) {
                throw new Exception("el numero de ejemplares  es incorrecto ");
            }
               if (ejemplaresPrestados == null || ejemplaresPrestados <=0 || ejemplaresPrestados>nroEjemplares) {
                throw new Exception("el numero de ejemplares  prestados  es incorrecto ");
            }
              if (autor == null ) {
                throw new Exception("el autor es incorrecto ");
            }   
            if (editorial == null) {
                throw new Exception(" el editorial es incorrecto ");
            }
            //Creamos el libro
            Libro libro = new Libro();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setNroEjemplares(nroEjemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setAlta(alta);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libroPer.guardarLibro(libro);       
            
        } catch (Exception e) {
            System.out.println("No se creeo el usuario"+ e.getMessage());
        }
    }
    public void modificarTituloLibro(Long isbn, String tituloActual, String nuevoTitulo) throws Exception {   
        try {
            //Validamos
            if (isbn == null || isbn <=0) {
                throw new Exception("Debe indicar isbn correctamente ");
            }
            if (tituloActual == null || tituloActual.trim().isEmpty()) {
                throw new Exception("Debe indicar el titulo a modificar ");
            }
            if (nuevoTitulo == null || nuevoTitulo.trim().isEmpty()) {
                throw new Exception("Debe indicar el nuevo titulo correctamente ");
            }
            //Buscamos
            Libro libro = buscarLibroPorIsbn(isbn);    
            //Validamos
            if (!libro.getTitulo().equals(tituloActual)) {
                throw new Exception("El titulo actual no esta resgistado en el sistema para el ISBN  indicado");
            }           
            //Modificamos
            libro.setTitulo(nuevoTitulo);           
            libroPer.modificarLibro(libro);
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }
    public void eliminarLibro(String titulo) throws Exception {
        try {
            //Validamos 
            if (titulo == null || titulo.trim().isEmpty()) {
                throw new Exception("Debe indicar el isbn coeectamente");
            }
            libroPer.eliminarLibroTitulo(titulo); 
            System.out.println("Libro fue eliminado con exito ");
        } catch (Exception e) {
            throw e;
        }
    }
    public Libro buscarLibroPorTitulo(String titulo) throws Exception {
        try {

            //Validamos
            if (titulo == null || titulo.trim().isEmpty()) {
                throw new Exception("Debe indicar el nombre del libro correctamente ");
            }

            Libro libro = libroPer.buscarLibroPorTitulo(titulo); 
            return libro;
        } catch (Exception e) {
            throw e;
        }
    }
    public Libro buscarLibroPorIsbn(Long isbn) throws Exception {

        try {
            //Validamos
            if (isbn == null) {
                throw new Exception("Debe indicar el ISBN correctamente ");
            }
            Libro libro = libroPer.buscarLibroPorIsbn(isbn);
            return libro;
        } catch (Exception e) {
            throw e;
        }
    }
    public List<Libro> listaLibros() throws Exception {

        try {
            List<Libro> libros = libroPer.listarLibros();
            return libros;
        } catch (Exception e) {
            throw e;
        }
    }
    public void imprimirLibros() throws Exception {
        try {
            //Listamos los usuarios
            List<Libro> libros = listaLibros();

            //Imprimimos los usuarios - Solo algunos atributos....
            if (libros.isEmpty()) {
                throw new Exception("No existen libros para imprimir");
            } else {
                for (Libro l : libros) {
                    System.out.println("---------------------------------------------------------");
                    System.out.println(l.toString());
                    System.out.println(" ISBN :" + l.getIsbn()+
                                   "\n Titulo :" + l.getTitulo() +
                                   "\n Año : :" + l.getAnio()+ 
                                   "\n  ejemplares en stock :" + l.getEjemplaresRestantes());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }
    public void imprimirUnLibro(String titulo) throws Exception {
        System.out.println(libroPer.buscarLibroPorTitulo(titulo));
    }

 
//    public void cargarAutorLibro(String nombre, String titulo ) throws Exception{
//          Libro libro = libroPer.buscarLibroPorTitulo(titulo);
//          Autor autor = AutorServicio
//          Usuario usuario = daoUsuario.buscarUsuarioPorId(idUsuario);
//          Mascota nueva = mascotaService.crearMascota(apodo, raza);
//          usuario.setMascota(nueva);
//          daoUsuario.modificarUsuario(usuario);// Utilizo el metodo del DAO           
//    }
//    
    

}

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
package Principal;

import Entidad.Autor;
import Entidad.Editorial;
import Entidad.Libro;
import Pesistencia.LibroPersistencia;
import Servicio.EditorialServicio;
import Servicio.LibroServicio;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.*;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author WALTER GOMEZ
 */
public class MenuLibro {

    private Scanner sc;
    private final Libro libro;
    private final Autor autor;
    private final Editorial editorial;
    private final EditorialServicio ediServ;
    private final LibroServicio libroServ;
    private final LibroPersistencia libroPer;

    public MenuLibro() {
        this.sc = new Scanner(System.in);
        this.libro = new Libro();
        this.autor = new Autor();
        this.editorial = new Editorial();
        this.ediServ = new EditorialServicio();
        this.libroServ = new LibroServicio();
        this.libroPer = new LibroPersistencia();
    }

    public void menuLibro() throws Exception {
        String respuesta;
        do {
            System.out.println("------------------------------------------------------------------------------");
            System.out.println(" 1- Crear/Ingresar Libro");
            System.out.println(" 2- Modificar/Editar Libro");
            System.out.println(" 3- Eliminar Libro");
            System.out.println(" 4- Mostrar/Listar a todos  los Libros");
            System.out.println(" 5- Buscar Libro por Titulo");
            System.out.println(" 6- Buscar Libro por ISBN");
            System.out.println(" 7- Búsqueda de un libro/s por nombre de Autor.");
            System.out.println(" 8- Búsqueda de un libro/s por nombre de Editorial.");
            System.out.println(" 9- Salir al  menú principal");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.print("Seleccione la opción : ");

            int opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    limpiarPantalla();
                    //libroServ.crearLibro(cargarIsbnLibro(), cargarTitulo(), cargarAnio(), cargarNroEjemplares(), cargarNroEjemplaresPrestados(), cargarAlta(), cargarAutor(), cargarEditorial());
                    break;
                case 2:
                    limpiarPantalla();
                    //libroServ.modificarTituloLibro(cargarIsbnLibro(),cargarTitulo(), );
                    break;
                case 3:
                    limpiarPantalla();
                    libroServ.eliminarLibro(cargarTitulo());
                    break;
                case 4:
                    limpiarPantalla();
                    libroServ.imprimirLibros();
                    break;
                case 5:
                    limpiarPantalla();
                    libroPer.buscarLibroPorTitulo(cargarTitulo());
                    break;
                case 6:
                    limpiarPantalla();
                    libroPer.buscarLibroPorIsbn(cargarIsbnLibro());
                    break;
                case 7:
                    limpiarPantalla();
                  
                   
                    break;
                      case 8:
                    limpiarPantalla();
                   
                    break;
                      case 9:
                    limpiarPantalla();
                    System.out.println("Salió del menú  de gestión de libros correctamente...\n");
                    cargarMenu();
                    break;
                default:
                    System.out.println("ERROR, ingresó una opción incorrecta...");
                    break;
            }
            System.out.println("Desea realizar una nueva consulta o gestión ... SI=S /NO=N");
            respuesta = sc.next();
            respuesta = respuesta.toUpperCase().trim();
            limpiarPantalla();
        } while ("S".equals(respuesta));
    }

    public void limpiarPantalla() throws AWTException {
        //Dejo esre metodo para ir borrando la consola.. y que no sea un desorden.
        Robot pressbot = new Robot();
        pressbot.setAutoDelay(30); // Tiempo de espera antes de borrar
        pressbot.keyPress(17); // Orden para apretar CTRL key
        pressbot.keyPress(76);// Orden para apretar L key
        pressbot.keyRelease(17); // Orden para soltar CTRL key
        pressbot.keyRelease(76); // Orden para soltar L key

    }

    public void cargarMenu() throws Exception {
        Menu menu = new Menu();
        menu.menu();
    }

    public Long cargarIsbnLibro() throws Exception {
        System.out.println("Ingrese el ISBN del Libro ");
        Long isbn = sc.nextLong();
        try {
            if (validarIsbn(isbn)) {
                System.out.println("ISBN  ingresado NO registrado  anteriormente, continue con el registro de  datos...");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            cargarIsbnLibro();
        }
        return isbn;
    }

    public boolean validarIsbn(Long isbn) throws Exception {
        boolean validar = false;
        try {
            List<Libro> libros = libroPer.listarLibros();
            for (Libro l : libros) {
                if (Objects.equals(l.getIsbn(), isbn)) {
                    throw new Exception("ISBN ya registrado, ingrese un ISBN valido...");
                }
            }
            validar = true;
        } catch (Exception e) {
            throw e;
        }
        return validar;
    }

    public String cargarTitulo() {
        System.out.println("Ingrese el titulo del Libro  ...");
        String titulo = sc.next();
        return titulo;
    }

    public Integer cargarAnio() {
        System.out.println("Ingrese el año del libro  ...");
        Integer anio = sc.nextInt();
        return anio;
    }

    public Integer cargarNroEjemplares() {
        System.out.println("Ingrese en número de ejemplares de libros  ...");
        Integer nroEjemplares = sc.nextInt();
        return nroEjemplares;
    }

    public Integer cargarNroEjemplaresPrestados() {
        System.out.println("Ingrese en número de ejemplares prestados de libros  ...");
        Integer nroEjPrestados = sc.nextInt();
        return nroEjPrestados;
    }

    public Boolean cargarAlta() {
        System.out.println("Ingrese el valor de alta ...");
        boolean alta = sc.nextBoolean();
        return alta;
    }

//    public Autor cargarAutor() {
//        System.out.println("Ingrese el nombre del autor  del Libro  ...");
//        String nombreAutor = sc.next();
//        
//          libro.setAutor(autor.getNombre());
//        return autor;
//    }
//
//    public Editorial cargarEditorial() {
//        System.out.println("Ingrese el nombre de la Editorial  del Libro  ...");
//        String nombre= sc.next();
//        ediServ.buscarEditorialPorNombre(nombre);
//        
//        libro.setEditorial();
//        return editorial;
//    }
}

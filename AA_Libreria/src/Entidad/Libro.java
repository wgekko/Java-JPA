/*
 *Sistema de Guardado de una Librería
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
package Entidad;

import com.sun.istack.internal.NotNull;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 *
 * @author WALTER GOMEZ
 */
@Entity
public class Libro implements Serializable {

    // @Id
    // @GeneratedValue(strategy = IDENTITY)
    // private String id;
    //@NotNull
    //@Column(unique = true)
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long isbn;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private Integer anio;
    @Column(nullable = false)
    private Integer nroEjemplares;
    @Column(nullable = false)
    private Integer ejemplaresPrestados;
    @Transient
//    public Integer ejemplaresRestantes(Integer nroEjemplares, Integer ejemplaresPrestados ){
//        Integer ejrestantes = nroEjemplares-ejemplaresPrestados;
//        return ejrestantes; 
//    }
    private Integer ejemplaresRestantes;
    private Boolean alta;
    @JoinColumn(nullable= false)
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Autor autor;
    @OneToOne(cascade = CascadeType.REMOVE)
    private Editorial editorial;

    public Libro() {
        //alta = true;
    }

    public Libro( Long isbn, String titulo, Integer anio, Integer nroEjemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Boolean alta, Autor autor, Editorial editorial) {
        //this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
        this.nroEjemplares = nroEjemplares;
        this.ejemplaresPrestados = ejemplaresPrestados;
        this.ejemplaresRestantes = ejemplaresRestantes;
        this.alta = alta;
        this.autor = autor;
        this.editorial = editorial;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getNroEjemplares() {
        return nroEjemplares;
    }

    public void setNroEjemplares(Integer nroEjemplares) {
        this.nroEjemplares = nroEjemplares;
    }

    public Integer getEjemplaresPrestados() {
        return ejemplaresPrestados;
    }

    public void setEjemplaresPrestados(Integer ejemplaresPrestados) {
        this.ejemplaresPrestados = ejemplaresPrestados;
    }

    public Integer getEjemplaresRestantes() {
        return ejemplaresRestantes;
    }

    public void setEjemplaresRestantes(Integer ejemplaresRestantes) {
        this.ejemplaresRestantes = ejemplaresRestantes;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    @Override
    public String toString() {
        return "Libro{" +"\n isbn : " + isbn
                + "\n titulo : " + titulo + "\n año : " + anio + "\n nro de Ejemplares : " + nroEjemplares + "\n ejemplares Prestados : " + ejemplaresPrestados
                + "\n ejemplaresRestantes : " + ejemplaresRestantes + "\n alta : " + alta + "\n autor : " + autor + "\n editorial : " + editorial;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.isbn);
        hash = 11 * hash + Objects.hashCode(this.titulo);
        hash = 11 * hash + Objects.hashCode(this.anio);
        hash = 11 * hash + Objects.hashCode(this.nroEjemplares);
        hash = 11 * hash + Objects.hashCode(this.ejemplaresPrestados);
        hash = 11 * hash + Objects.hashCode(this.ejemplaresRestantes);
        hash = 11 * hash + Objects.hashCode(this.alta);
        hash = 11 * hash + Objects.hashCode(this.autor);
        hash = 11 * hash + Objects.hashCode(this.editorial);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Libro other = (Libro) obj;
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.isbn, other.isbn)) {
            return false;
        }
        if (!Objects.equals(this.anio, other.anio)) {
            return false;
        }
        if (!Objects.equals(this.nroEjemplares, other.nroEjemplares)) {
            return false;
        }
        if (!Objects.equals(this.ejemplaresPrestados, other.ejemplaresPrestados)) {
            return false;
        }
        if (!Objects.equals(this.ejemplaresRestantes, other.ejemplaresRestantes)) {
            return false;
        }
        if (!Objects.equals(this.alta, other.alta)) {
            return false;
        }
        if (!Objects.equals(this.autor, other.autor)) {
            return false;
        }
        if (!Objects.equals(this.editorial, other.editorial)) {
            return false;
        }
        return true;
    }

   
 

}

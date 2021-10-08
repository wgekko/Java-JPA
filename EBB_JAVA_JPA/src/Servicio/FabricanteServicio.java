/*
 * 
 */
package Servicio;

import Controlador.FabricanteJpaController;
import Entidad.Fabricante;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author WALTER GOMEZ
 */
public class FabricanteServicio {

    Fabricante fb;
    Scanner leer = new Scanner(System.in, "ISO-8859-1");
    FabricanteJpaController CFab = new FabricanteJpaController();

    private void Cargar_InfoF() {
        try {
            Object F[] = null;
            List<Fabricante> listF = CFab.findFabricanteEntities();
            for (int i = 0; i < listF.size(); i++) {
                System.out.println("\t Codigo :" + listF.get(i).getCodigo() + " - Nombre : " + listF.get(i).getNombre());
            }
//         listF.forEach((f) -> {
//             System.out.println(f);
//            });              

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void crearFabricante() {
        fb = new Fabricante();
        try {
            System.out.println("Ingrese el nombre del fabricante");
            String nombre = leer.nextLine();
            fb.setNombre(nombre);

            CFab.create(fb);
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

    }

    private void eliminarFabricante() {
        fb = new Fabricante();
        try {
            System.out.println("Ingrese el nombre del fabricante");
            Integer codigo = leer.nextInt();
            fb.setCodigo(codigo);
            CFab.destroy(fb.getCodigo());

            System.out.println("se ha creado el fabricante con exito...");
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

    }

    private void modificarFabricante() {
        fb = new Fabricante();
        try {
            System.out.println("Ingrese el codigo del fabricante");
            Integer codigo = leer.nextInt();
            fb.setCodigo(codigo);
            System.out.println("Ingrese el nombre del fabricante");
            String nombre = leer.nextLine();
            fb.setNombre(nombre);
            CFab.edit(fb);
            System.out.println("se modifico el fabricante con exito ...");
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

    }

}

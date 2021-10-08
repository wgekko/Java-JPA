/*
 * 
 */
package Servicio;

import Controlador.ProductoJpaController;
import Entidad.Producto;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author WALTER GOMEZ
 */
public class ProductoServicio {
    
    Producto pd;
    ProductoJpaController CProd = new ProductoJpaController();
    Scanner leer = new Scanner(System.in, "ISO-8859-1");

    private void Cargar_InfoP() {
        try {            
            Object P[] = null;
            List<Producto> listP = CProd.findProductoEntities();
            for (int i = 0; i < listP.size(); i++) {
                System.out.println("\t Codigo :" + listP.get(i).getCodigo() + " - Nombre : " + listP.get(i).getNombre()
                        + " -  Precio : " + listP.get(i).getPrecio() + " - codigo Fabricante: " + listP.get(i).getCodigoFabricante());                
            }
//         listP.forEach((p) -> {
//             System.out.println(p);
//            });        
        } catch (Exception e) {
            System.out.println(e.getMessage());            
        }        
    }
    
    private void crearProducto() {
        pd = new Producto();
        try {            
            System.out.println("Ingrese el nombre del producto :");
            String nombre = leer.nextLine();            
            System.out.println("Ingrese el precio del producto :");
            Double precio = leer.nextDouble();            
            System.out.println("Ingrese el codigo del fabricante :");
            int codigoFabricante = leer.nextInt();            
            pd.setNombre(nombre);            
            pd.setPrecio(precio);
            pd.setCodigoFabricante(codigoFabricante);            
            CProd.create(pd);
            System.out.println("se ha creado el producto con exito...");
        } catch (Exception e) {
            
            System.out.println(e.getMessage());
        }
    }
      private void eliminarFabricante() {
             pd=new Producto();
        try {     
        System.out.println("Ingrese el nombre del producto");
        Integer codigo = leer.nextInt();     
        pd.setCodigo(codigo);
        CProd.destroy(pd.getCodigo());
            
            System.out.println("se ha creado el fabricante con exito...");
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }
}
    private void modificarProducto() {
        pd = new Producto();
        try {            
            System.out.println("Ingrese el nombre del producto :");
            String nombre = leer.nextLine();            
            System.out.println("Ingrese el precio del producto :");
            Double precio = leer.nextDouble();            
            System.out.println("Ingrese el codigo del fabricante :");
            int codigoFabricante = leer.nextInt();            
            pd.setNombre(nombre);            
            pd.setPrecio(precio);
            pd.setCodigoFabricante(codigoFabricante);            
            CProd.edit(pd);
            System.out.println("se ha modificado el producto con exito...");
        } catch (Exception e) {
            
            System.out.println(e.getMessage());
        }
    }     
      
}

/*
 *
 */
package ebb_java_jpa;

import Controlador.FabricanteJpaController;
import Controlador.ProductoJpaController;
import Servicio.FabricanteServicio;
import Servicio.ProductoServicio;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author WALTER GOMEZ
 */
public class Principal {

    /**
     * @param args the command line arguments
     *
     *
     */
    static Scanner leer = new Scanner(System.in, "ISO-8859-1");
    static ProductoServicio prod = new ProductoServicio();
    static FabricanteServicio fab = new FabricanteServicio();

    public static void main(String[] args) throws AWTException {

        FabricanteJpaController CFab = new FabricanteJpaController();
        ProductoJpaController CProd = new ProductoJpaController();
        int dato = 0;

        do {

            try {

                System.out.println("\033[36m ----------------------------------------------------------------------");
                System.out.println("\t\033[36m   ---   Menu   ---   ");
                System.out.println("\033[36m 1. Listar el nombre de todos los productos");
                System.out.println("\033[36m 2. Listar el nombre y precio de todos los productos");
                System.out.println("\033[36m 3. Listar los productos cuyo precio este entre 120 y 202");
                System.out.println("\033[36m 4. Listar todos los portatiles");
                System.out.println("\033[36m 5. Listar el nombre y precio del producto mas barato");
                System.out.println("\033[36m 6. Crear nuevo producto");
                System.out.println("\033[36m 7. Crear nuevo fabricante");
                System.out.println("\033[36m 8. Editar/modificar producto");
                System.out.println("\033[36m 9. Salir ");
                System.out.println("\033[36m ----------------------------------------------------------------------");
                System.out.print("\033[36m digite su opción : ");
                dato = leer.nextInt();
                retardarSegundo();
                switch (dato) {
                    case 1:
                         robotLimpiador();//System.out.println("\n");    

                        break;
                    case 2:
                        robotLimpiador();//System.out.println("\n");

                        break;
                    case 3:
                        robotLimpiador();//System.out.println("\n");

                        break;
                    case 4:
                         robotLimpiador();//System.out.println("\n");

                        break;
                    case 5:
                        robotLimpiador();//System.out.println("\n");

                        break;
                    case 6:
                        robotLimpiador();//System.out.println("\n");

                        break;
                    case 7:
                        robotLimpiador();//System.out.println("\n");

                        break;
                    case 8:
                        robotLimpiador();//System.out.println("\n");

                        break;
                    case 9:
                        System.out.println("\033[36m SALIO DEL  SISTEMA CORRECTAMENTE .....");
                        break;
                    default:
                        System.out.println("Error, opción  no válida ....");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("NO SE ADMITEN CARACTERES");
                leer.next();
            }
        } while (dato != 9);

    }

    private static void retardarSegundo() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }

    public static void robotLimpiador() throws AWTException {

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_L);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_L);
    }

}

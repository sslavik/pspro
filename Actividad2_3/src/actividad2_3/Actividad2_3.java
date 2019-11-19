/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad2_3;

/**
 *
 * @author Vyacheslav Shylyayev
 */
public class Actividad2_3 {

    final static int NUM_HILOS = 10;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Thread[] hilos = new Thread[NUM_HILOS];

        Contador cont = new Contador();

        for (int i = 0; i < NUM_HILOS; i++) {

            // lanza hilos hilos[i]=new Thread(new Hilo(i, CUENTA_TOTAL / NUM_HILOS,cont));
            hilos[i].start();

        }

        try {

            for (int i = 0; i < NUM_HILOS; i++) {

                hilos[i].join(); // espera a que terminen

            }

        } catch (InterruptedException ex) {

            System.err.println("Hilo interrumpido.");

            ex.printStackTrace();

        }

        System.out.printf("Cuenta global: %s\n", cont.getCuenta());

    }
}

class Contador {

    // CAMPOS
    int valor = 0;

    // METODOS NO ESTATICOS
    void incrementa() {
        valor++;
    }
    String getCuenta(){
        return new String("Cuenta  : " + valor);
    }
}

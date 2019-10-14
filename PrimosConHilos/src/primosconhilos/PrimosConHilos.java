/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primosconhilos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Hilo implements Runnable {

    int desde;
    int hasta;
    List<Integer> primos = new ArrayList<Integer>();

    public Hilo(int desde, int hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    public void run() {

        for (int i = desde; i < hasta - 1; i++) {
            boolean esPrimo = true;
            for (int j = 2; j < i; j++) {
                if (i == 1 || i == 0) {
                    esPrimo = false;
                } else if ((i % j) == 0) {
                    esPrimo = false;
                }
            }
            if (i == 2) {
                PrimosConHilos.primos.add(i);
            } else if (esPrimo) {
                PrimosConHilos.primos.add(i);
            }
        }
        /*
        for (int i = desde; i < hasta - 1; i++) {
            boolean esPrimo = true;
            for (int j = 2; j < i; j++) {
                if(i == 1 || i == 0) esPrimo = false;
                else if((i % j) == 0) esPrimo = false;
            }
            if(i == 2) PrimosConHilos.add(i);
            else if(esPrimo) PrimosConHilos.add(i);
        }*/

    }
}

/**
 *
 * @author usuario
 */
public class PrimosConHilos {

    public static List<Integer> primos = new ArrayList<Integer>();
    public static Boolean[] primosB = new Boolean[100000];
    static int nDatos = 0;

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here

        int hastaPrimo = 100000;
        final int NUM_HILOS = 10;
        int desde = 0;
        int hasta = hastaPrimo / 10;

        System.out.print("Primos : ");

        Thread[] hilos = new Thread[NUM_HILOS];
        for (int i = 0; i < NUM_HILOS; i++) {
            Hilo h = new Hilo(desde, hasta * (i + 1));
            Thread th = new Thread(h);
            hilos[i] = th;
            th.start();
            desde = desde + hasta;
        }
        
        for (Thread h : hilos){
            h.join();
        }
        
        

        System.out.println(primos);
        System.out.println(primos.size());

    }

    static void add(int i) {

        primosB[i] = true;
    }

}

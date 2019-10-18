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
            // Sincronizamos la Lista
            synchronized (PrimosConHilos_Shylyayev_Vyacheslav.primos) {
                if (i == 2) {
                    PrimosConHilos_Shylyayev_Vyacheslav.primos.add(i);
                } else if (esPrimo) {
                    PrimosConHilos_Shylyayev_Vyacheslav.primos.add(i);
                }
            }
        }

    }
}

/**
 *
 * @author Vyacheslav Shylyayev
 */
public class PrimosConHilos_Shylyayev_Vyacheslav {

    public static List<Integer> primos = Collections.synchronizedList(new ArrayList<Integer>());

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
        
        
         // Se imprimen los primos
        System.out.println(primos);
        System.out.println(primos.size());

    }
}

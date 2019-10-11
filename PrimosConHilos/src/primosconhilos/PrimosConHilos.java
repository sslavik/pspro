/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primosconhilos;

import java.util.List;

class Hilo implements Runnable{
    
    int desde;
    int hasta;
    List<Integer> primos = null;
    
    public Hilo(int desde, int hasta){
        this.desde = desde;
        this.hasta = hasta;
    }
    
    @Override
    public void run(){
        
        for (int i = desde; i < hasta - 1; i++) {
            boolean esPrimo = true;
            for (int j = 2; j < i; j++) {
                if(i == 1) continue;
                if((i % j) == 0){
                    esPrimo = false;
                }
            }
            if(i == 2) primos.add(i);
            else if(esPrimo) primos.add(i);
        }
    }
}

/**
 *
 * @author usuario
 */
public class PrimosConHilos {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        List<Integer> totalPrimos = null;
        int hastaPrimo = 100000;
        final int NUM_HILOS = 10;
        int desde = 0;
        int hasta = hastaPrimo / 10;
        
        for (int i = 0; i < NUM_HILOS; i++) {
            Hilo h = new Hilo(desde, hasta * i);
            Thread th = new Thread(h);
            th.start();
            desde = desde + hasta;
            for(int j: h.primos){
                totalPrimos.add(j);
            }
        }
        
        System.out.print("Primos : ");
        for (int primo: totalPrimos){
            System.out.print(" " + primo);
        }
        
        
    }
    
}

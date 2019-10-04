/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author usuario
 */
public class Hilos extends Thread{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        for (int i = 0; i < 10; i++) {
            Thread hilo = new Thread(new HolaDesdeRunnable(i));
            hilo.start();

        }
        
        Hilos h = new Hilos();
        h.start();
        
        System.out.println("tamos bien");
        
    }
    
    @Override
    public void run(){
        try {

            Thread.sleep(10000);
            System.out.print("Hola desde la misma clase con un hilo incorporado");

        } catch (InterruptedException ex) {
            System.out.print(ex.getMessage());
        }
    }
    
}


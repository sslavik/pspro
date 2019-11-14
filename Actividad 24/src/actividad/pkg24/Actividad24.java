/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad.pkg24;

/**
 *Crea un programa de prueba para la clase anterior Contadores. Este programa debe crear y lanzar 10 hilos que,
entre todos y a partes iguales, incrementen cada uno de los dos contadores hasta alcanzar un valor de 1000000.
Cada hilo debe hacer primero todas las operaciones de incremento en el primer contador, y después en el segundo.
Verifica que, tras ejecutar los hilos, ambos contadores alcanzan el valor esperado. ¿Se utiliza en este programa la
sincronización reentrante? ¿Por qué?
 * @author Vyacheslav Shylyayev
 */
public class Actividad24 {

    
    private static final int NUM_HILOS = 10;
    private static final int TOTAL_CUENTA = 1000000;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        
        Contador c1 = new Contador();
        Contador c2 = new Contador();
        
        Thread[] hilos = new Thread[NUM_HILOS];
        
        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new Thread(new Hilo(i,TOTAL_CUENTA/NUM_HILOS, c1, c2));
            hilos[i].start();
        }
        
         for (Thread h : hilos)
            h.join();
         
        System.out.println("Terminado conteo");
        System.out.println("Contador 1  TOTAL : " + c1.getCuenta());
        System.out.println("Contador 2  TOTAL : " + c2.getCuenta());
        
    }
}

class Hilo implements Runnable{
    
    int hilo;
    int total_parte;
    Contador contador1;
    Contador contador2;
    
    public Hilo(int hilo, int total, Contador contador1, Contador contador2){
        this.hilo = hilo;
        this.total_parte = total;
        this.contador1 = contador1;
        this.contador2 = contador2;
    }
    

    @Override
    public void run() {
        for (int i = 0; i < total_parte; i++) {
            contador1.incrementar();
            contador2.incrementar();
        }
        System.out.printf("Hilo %d Terminado \n", hilo);
    }
        
}

class Contador {
    int cuenta = 0;
    
    int getCuenta(){
        return cuenta;
    }
    
    public synchronized void incrementar(){
        cuenta++;
    }
}

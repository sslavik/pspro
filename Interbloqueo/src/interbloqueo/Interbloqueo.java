/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interbloqueo;

/**
 *
 * @author usuario
 */
public class Interbloqueo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        Cuenta c1 = new Cuenta(1000);
        Cuenta c2 = new Cuenta(1000);

        Thread[] hilos = new Thread[2];
        Thread th = new Thread(new TransferenciaPorHilo(c1, c2, 50));
        Thread th2 = new Thread(new TransferenciaPorHilo(c2, c1, 50));
        hilos[0] = th;
        hilos[1] = th2;
        
        for (Thread h : hilos)
            h.join();
        
        System.out.println("Saldo Cuenta 1 : " + c1.getSaldo());
        System.out.println("Saldo Cuenta 2 : " + c2.getSaldo());
    }
    
    public static boolean trasnferencia(Cuenta c1, Cuenta c2, float cantidad){
        try {
            synchronized(c1){
                synchronized(c2){
                    System.out.println(c1.sacar(cantidad));
                    System.out.println(c2.ingresar(cantidad));
                }
            }
                   
            
            return true;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
}
class TransferenciaPorHilo implements Runnable {

    // CAMPOS
    Cuenta c1;
    Cuenta c2;
    float cantidad;
    
    // CONSTRUCTOR
    public TransferenciaPorHilo(Cuenta c1, Cuenta c2, float cantidad){
        this.c1 = c1;
        this.c2 = c2;
        this.cantidad = cantidad;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 10000000; i++) {
            System.out.println(Interbloqueo.trasnferencia(c1, c2, cantidad));
        }
    }
    
}

class Cuenta {
    
    // CAMPOS
    float saldo = 0;
    String nombre = "";
    int CC = 0;
    
    // CONSTRUCTOR
    public Cuenta(float cantidadInicial){
        saldo = cantidadInicial;
    }
    
    // MÉTODOS
    
    float getSaldo(){
        return saldo;
    }
    
    boolean sacar(float cantidad){
        try{
            saldo -= cantidad;
            if (saldo < 0){
                saldo += cantidad;
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    boolean ingresar (float cantidad){
        try {
            saldo += cantidad;
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
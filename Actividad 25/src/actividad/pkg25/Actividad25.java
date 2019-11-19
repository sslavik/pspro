/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad.pkg25;

/**
 *Crea una clase Cuenta que implemente los métodos getSaldo(), sacar() e ingresar(), y que se puede utilizar con el anterior código de ejemplo. 
 * Crea un programa de prueba que intente provocar un interbloqueo al utilizarpor parte de dos o más hilos sobre las mismas cuentas el método transferencia. 
 * Modifica el programa anterior para evitar interbloqueos.Para ello se obtendrá el bloqueo intrínseco primero para lacuenta menor por orden alfabético,
 * y luego para la otra.
 * @author hacka
 */
public class Actividad25 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        // INICIALIZADO
        Cuenta c1 = new Cuenta(1000);
        Cuenta c2 = new Cuenta(1000);
        Thread[] hilos = new Thread[2];
        
        hilos[0] = new Thread(new Hilo(c1, c2, 500));
        hilos[1] = new Thread(new Hilo(c2, c1, 500));
        
        for (Thread h : hilos)
            h.start();
        
        for (Thread h : hilos)
            h.join();
        
        System.out.println("Cuenta 1 saldo : " + c1.getSaldo());
        System.out.println("Cuenta 2 saldo : " + c2.getSaldo());

    }
}

class Hilo implements Runnable{
    
    Cuenta c1;
    Cuenta c2;
    float cantidad;

    public Hilo(Cuenta c1, Cuenta c2, float cantidad) {
        this.c1 = c1;
        this.c2 = c2;
        this.cantidad = cantidad;
    }
    
    
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (c1){
                synchronized(c2){
                    if(c1.sacar(cantidad))
                        c2.ingresar(cantidad);
                }
            }
        }
    }
    
}

class Cuenta {
    float saldo;

    public Cuenta(float saldo) {
        this.saldo = saldo;
    }
    
    public float getSaldo() {
        return saldo;
    }
    
    public boolean sacar(float cantidad){
        if((saldo-cantidad) >= 0){
            saldo -= cantidad;
            return true;
        } 
        return false;
    }
    
    public void ingresar(float cantidad){
        saldo += cantidad;
    }
    
    
}

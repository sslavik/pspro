
package operacionesbanco;

import java.util.concurrent.atomic.AtomicLong;

public class Cuenta{

    static AtomicLong contador = new AtomicLong(0);
    long id;
    String numCuenta;
    int saldo;
    
    public Cuenta(String nCuenta, int saldo) {
        this.numCuenta = nCuenta;
        this.saldo = saldo;
        this.id = contador.getAndIncrement();
    }
    
    public String getNumCuenta() {
        return numCuenta;
    }

    public int getSaldo() {
        return saldo;
    }

    public long getId() {
        return id;
    }
    
    void sacar(int cantidad) {
        saldo -= cantidad;
    }

    void ingresar(int cantidad) {
        saldo += cantidad;
    }
    
}

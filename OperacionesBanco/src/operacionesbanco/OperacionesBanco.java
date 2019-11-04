package operacionesbanco;

import static operacionesbanco.OperacionesBanco.transferir;

class Hilo implements Runnable {
    Cuenta C1;
    Cuenta C2;
    
    @Override
    public void run(){     
        for (int i = 0; i < 10000; i++) {
            transferir(C1, C2, 1);  
        }            
    }
    public Hilo(Cuenta C1, Cuenta C2){
        this.C1 = C1;
        this.C2 = C2;
    }
}

public class OperacionesBanco {
    
    public static void main(String[] args) throws InterruptedException {
        Cuenta C1 = new Cuenta("ES2345234807474", 30000);
        Cuenta C2 = new Cuenta("ES3457642544746", 50000);
        
        /*
        for(int i =1; i <= 5; i++){      
            Thread th =new Thread(new Hilo(C1, C2));      
            th.start();
            th.join();
        }*/
        
        System.out.println("INICIAL: C1 -> " + C1.getSaldo() + ", C2 -> " + C2.getSaldo());
        
        Thread th1 =new Thread(new Hilo(C1, C2));
        Thread th2 =new Thread(new Hilo(C2, C1));
        th1.start();
        th2.start();
        th1.join();
        th2.join();
        
        System.out.println("C1 -> " + C1.getSaldo() + ", C2 -> " + C2.getSaldo());
    }
    
    static boolean transferir(Cuenta C1, Cuenta C2, int cantidad){
        
        Cuenta primeraCuenta;
        Cuenta segundaCuenta;
        
        if(C1.id < C2.id)
        {
            primeraCuenta = C1;
            segundaCuenta = C2;
        }
        else
        {
            primeraCuenta = C2;
            segundaCuenta = C1;
        }
        
        synchronized(primeraCuenta){
            synchronized(segundaCuenta){
                if(C1.getSaldo()>= cantidad){        
                    C1.sacar(cantidad);        
                    C2.ingresar(cantidad);        
                    return true;
                }
            }
        }
        
        return false;
    }
    
}

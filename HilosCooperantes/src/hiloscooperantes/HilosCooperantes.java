package hiloscooperantes;

class Hilo implements Runnable {

    int numHilo, miParte, miCuenta = 0;

    public int getMiCuenta() {
        return miCuenta;
    }

    Hilo(int numHilo, int miParte) {
        this.numHilo = numHilo;
        this.miParte = miParte;
    }

    @Override
    public void run() {
        for (int i = 0; i < miParte; i++) {
            HilosCooperantes.incrementa();
            miCuenta++;
        }
        System.out.printf("Hilo %d terminado, cuenta: %d\n", numHilo,
                getMiCuenta());
    }
}

class HilosCooperantes {

    private static int cuenta = 0;
    private static final int NUM_HILOS = 10;
    private static final int CUENTA_TOTAL = 1000000;
    private static final int ESPERA_MS = 1;

    static void incrementa() {
        cuenta++;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < NUM_HILOS; i++) {
            Thread th = new Thread(new Hilo(i, CUENTA_TOTAL / NUM_HILOS));
            th.start();
        }
        try {
            while(cuenta != CUENTA_TOTAL){
                Thread.sleep(ESPERA_MS);
            }
        } catch (InterruptedException ex) {
            System.err.println("Se interrumpió hilo principal.");
            ex.printStackTrace();
        }
        System.out.printf("Cuenta global: %s\n", cuenta);
    }
}

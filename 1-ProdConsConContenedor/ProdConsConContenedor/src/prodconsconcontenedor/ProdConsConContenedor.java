package prodconsconcontenedor;

/**
 * Programa de ejemplo para problema productor-consumidor con un contenedor
 * sincronizado.
 * Toda la complejidad de la sincronización la gestiona la clase ColaSincronizada,
 * los productores solo utilizan el método put() y los consumidores get().
 * Se puede jugar con el tiempo de espera máximo para el productor y para el
 * consumidor. Si es mayor para el productor, en general se alternará un acceso
 * para el productor y otro para el consumidor, porque el consumidor normalmente
 * esperará : P C P C P C P C ..., y habrá como máximo un objeto en el contenedor.
 * Por acceso de productor se entiende que pone un objeto en la cola, y por acceso
 * de consumidor se entiende que retira un objeto de la cola. Si es mayor para
 * el consumidor, habrá a menudo más de un acceso seguido del productor, y 
 * normalmente habrá más de un objeto en la cola, tanto más cuanto mayor sea el
 * tiempo máximo de acceso para consumidor con respecto al del productor. Por
 * defecto se ha puesto tiempo máximo para consumidor 1000 ms y para productor
 * 500 ms. Si la simulación funciona t segundos con un tiempo de espera máximo
 * tc para consumidor y tp para productor, siendo tp menor que tc, entonces se
 * producirán por término medio 2*t/tp valores (durante t, uno cada tp/2), y se
 * consumirán 2*t/pc. Por ejemplo, si t=20000 ms, tp=500 ms y tc=1000 ms, entonces
 * se producirán 80 valores (20000/250), se consumirán 40, y por tanto quedarán
 * 40, por término medio.
 */
import java.util.Random;

class Productor implements Runnable {

  private final ColaSincronizada cola;

  private final int ESPERA_MAX_PROD = 500;
  private final Random rand;

  Productor(ColaSincronizada cola) {
    this.cola = cola;
    rand = new Random();
  }

  @Override
  public void run() {
    System.out.println(">> Productor empieza");
    try {
      for (int i = 1; true; i++) {
        long espera = rand.nextInt(ESPERA_MAX_PROD);
        System.out.printf("... Productor espera %d ms.\n", espera);
        Thread.sleep(espera);
        cola.put(Integer.valueOf(i));  // En lugar de new Integer(i), deprecated
        System.out.printf("Productor ha introducido valor: %d, ahora hay %d objeto%s en el contenedor.\n",
                i, cola.size(), (cola.size() != 1 ? "s" : ""));
      }
    } catch (InterruptedException ex) {
      System.out.println("# Hilo productor interrumpido.");
    }
  }
}

class Consumidor implements Runnable {

  ColaSincronizada cola;

  private final int ESPERA_MAX_CONS = 1000;
  private Random rand = new Random();

  Consumidor(ColaSincronizada cola) {
    this.cola = cola;
  }

  @Override
  public void run() {
    System.out.println(">> Consumidor empieza");
    try {
      while (true) {
        long espera = rand.nextInt(ESPERA_MAX_CONS);
        System.out.printf("... Consumidor espera %d ms.\n", espera);
        Thread.sleep(espera);
        Object valor = cola.get();
        System.out.printf("Consumidor ha obtenido valor: %s, ahora hay %d objeto%s en el contenedor.\n",
                valor, cola.size(), (cola.size() != 1 ? "s" : ""));
      }
    } catch (InterruptedException ex) {
      System.out.println("# Hilo consumidor interrumpido.");
    }
  }
}

public class ProdConsConContenedor {

  public static void main(String[] args) {

    int maxTiempoSimul = 20000;

    ColaSincronizada cola = new ColaSincronizada();

    Thread hiloProd = new Thread(new Productor(cola));
    Thread hiloCons = new Thread(new Consumidor(cola));

    hiloProd.start();
    hiloCons.start();

    try {
      Thread.sleep(maxTiempoSimul);
      // hiloProd.join();
      // hiloCons.join();
      // Ninguno de los dos hilos termina por sí mismo.
      // Se espera un tiempo y se interrumpen los dos.
      hiloProd.interrupt();
      hiloCons.interrupt();
      System.out.printf("**** Terminada la simulación tras %d milisegundos.\n", maxTiempoSimul);
    } catch (InterruptedException ex) {
      System.out.println("ERROR: interrrumpido programa principal de productor-consumidor.");
      ex.printStackTrace();
    }

  }

}

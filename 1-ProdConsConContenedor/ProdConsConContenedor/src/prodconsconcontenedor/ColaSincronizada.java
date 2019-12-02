package prodconsconcontenedor;

import java.util.ArrayList;

public class ColaSincronizada {

  private ArrayList miContenedor = new ArrayList();

  public Object get() throws InterruptedException {
    Object obj = null;
    while (obj == null) {  // Intenta obtener un objeto de la cola.
      synchronized (miContenedor) {   // Acceso a la cola con exclusión mutua
        while (miContenedor.isEmpty()) {   // Si vacío, espera que alguien ponga algo y avise con notify()
          try {
            miContenedor.wait();
          } catch (InterruptedException ex) {  // Se puede interrumpir, propaga la excepción
            System.out.println("# Interrupción en get() de ColaSincronizada");
            throw (ex);
          }
        }  // Normalmente ahora habrá algo en la cola, obtenerlo.
        try {  // Ahora seguramente hay algo en la cola, pero se contempla la posibilidad de que no lo haya
          obj = miContenedor.remove(miContenedor.size() - 1);
          // No hace falta notify(), los productores pueden producir en cualquier
          // momento, y consumir un objeto de la cola no permite desbloquear a
          // ningún consumidor.        
        }
        catch(IndexOutOfBoundsException ex) {  // Nada en la cola
          obj = null;
        }
      }
    }
    return obj;
  }

  public void put(Object obj) {
    synchronized (miContenedor) {
      // Siempre se puede poner un objeto en la cola, y hay que avisar a algún
      // productor de los que pudieran estar esperando.
      miContenedor.add(0, obj);
      miContenedor.notify();
    }
  }

  public int size() {
    return miContenedor.size();
  }

}

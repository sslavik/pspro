/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tf2_ej1;

import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class TF2_EJ1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}

class Consumidor {
}

class Productor {
    
}

class Cola{
    
    ArrayList<Object> Contenedor = new ArrayList<Object>();
    
    public void put(){
        
    }
    public Object get() throws InterruptedException{
        
        Object obj = null;
       
        while(obj == null){ // BUSCAMOS UN OBJETO (INSTANCIA) INCORPORADO AL CONTENEDOR DE LA COLA
            synchronized(Contenedor){ // SYNCRONIZAMOS EL ACCESO AL ARRAY
                while(Contenedor.isEmpty()){ // ESPERAMOS SI EXISTE ALGUN VALOR DENTRO DE LA COLA
                    Contenedor.wait();
                }
                try{ // COMPROBAMOS QUE SI NO EXISTE VALOR DENTRO DEL ARRAY QUE CONTINUE
                obj = Contenedor.remove(Contenedor.size()-1);
                } catch (IndexOutOfBoundsException e){
                    obj = null;
                }
            }
        }   
        
        return obj;
        
    }
    
}
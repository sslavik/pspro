/*
 *Escribe un programa que permita obtener directamente la dirección IP asociada a la interfaz de loopback 
  (o hablando en propiedad, una cualquiera de las direcciones) utilizando el método getLoopbackAddress() 
  de la clase InetAddress. Amplía el programa para que pueda obtener 
  también todas las direcciones IP asociadas a dicho interfaz utilizando los métodos getNetworkInterfaces() e isLoopback() 
  de la clase NetworkInterface. Utiliza en este mismo programa el método getLocalHost() y muestra el valor obtenido por él.
 */
package actividad34;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 *
 * @author Vyacheslav Shylyayev
 */
public class Actividad34 {

    
    public static void main(String[] args) throws SocketException {
        // TODO code application logic here
        
        Enumeration<NetworkInterface> eni = NetworkInterface.getNetworkInterfaces();
        NetworkInterface networkInterface;
        // RECORREMOS TODAS LAS INTERFACES DE ESTE DISPOSITIVO
        while(eni.hasMoreElements()){
            networkInterface = eni.nextElement();
            // RECOLECTAMOS TODAS LAS IPS
            Enumeration<InetAddress> enumInetAdress = networkInterface.getInetAddresses();
            while (enumInetAdress.hasMoreElements()){
                // OBTENEMOS TODAS LAS IPS Y COMPROBAMOS SI ES UN INET LOOKBACK
                InetAddress ia =  enumInetAdress.nextElement();
                
                if(ia.isLoopbackAddress()){
                    System.out.println("Loopback adress : " + ia.getHostName());
                }
            }
            
        }
        
    }
    
}

/*
 * Mejora el programa desarrollado para una actividad anterior, que averiguaba las direcciones IP alcanzables dentro de la red.
Debe cambiarse para que sea genérico y pueda funcionar en cualquier red, al menos con longitud de máscara de 24 bits. 
Antes de nada debe averiguar cuál es la interfaz de red conectada a una red local, es decir, con direcciones IP privadas. 
Para ello, deber verificar la información de todos los NetworkInterface, 
y en cuáles de ellos existe una dirección IP para una red local (utilizando el método isSiteLocalAddress()). 
Si la máscara de red es de 24 bits, es muy sencillo iterar sobre todas las 254 posibles direcciones posibles 
(28=256 menos la primera, la dirección de la red, y la última, la dirección de bradcast). Si la máscara es de 16 bits,
es un poco más complejo, y también lleva más tiempo, dado que hay muchas más direcciones IP para verificar.
Si la longitud de la máscara no es múltiplo de 8, es decir, no es un número entero de bits, entonces es más complicado,
porque hay que hacer operaciones aritméticas con números binarios.
 */
package actividad35;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 *
 * @author Vyacheslav Shylyayev
 */
public class Actividad35 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException {
        
        // CAMPOS
        Enumeration<NetworkInterface> eni = NetworkInterface.getNetworkInterfaces();
        NetworkInterface networkInterface;
        
        while(eni.hasMoreElements()){
            networkInterface = eni.nextElement(); // OBTENEMOS EL INTERFAZ Y BUSCAMOS DENTRO LA INET QUE SEA LOCAL ( Que no es la Localhost )  
            Enumeration<InetAddress> eia = networkInterface.getInetAddresses();
            while(eia.hasMoreElements()){
                InetAddress ia = eia.nextElement();
            }
        }
        
        
    }
    
}

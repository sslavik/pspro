/*
 * Probablemente tu ordenador está situado en una red local de tipo C con máscara de red de 24 bits. Si es así, escribe un
programa que permita determinar qué direcciones dentro de la red local son alcanzables. Se puede escribir el progra -
ma específicamente para la red particular, es decir, no hace falta que sea genérico y capaz de detectar automáticamente la red. Pero sí hay que explicar cómo has averiguado la red local en la que está situado tu ordenador. El programa
informará previamente del rango de direcciones que va a probar, y escribirá después as direcciones IP alcanzables en
ese rango, utilizando el método isReachable(). Si tu ordenador no está en una red de tipo C, seguramente estará
en una de tipo B, lo que puede complicar un poco las cosas, pero no mucho más (en lugar de un bucle para el último
byte, habrá que hacer un bucle anidado para los dos últimos bytes). También puede complicar algo las cosas que la
máscara de red no sea de 24 o 16 bits. Para crear las InetAddress se puede utilizar el método static InetAddress getByAddress(byte[] addr) de InetAddress. Ten en cuenta que la primera dirección del rango
(bits para host todos a cero) es la dirección de la red, y que la última (bits para host todos a uno) es la dirección de
broadcast, y que por lo tanto no corresponden a ningún host
 */
package actividad33;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 *
 * @author Vyacheslav Shylyayev
 */
public class Actividad33 {

    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        
        String preIP = "192.168.";
        int minIpHost = 96;
        int maxIpHost = 111;
        
        for(int i = minIpHost; i <= maxIpHost; i++){
            for (int j = 1; j < 255; j++) {
                String ip = preIP+i+"."+j;
                InetAddress ia = InetAddress.getByName(preIP+i+"."+j);
                if(ia.isReachable(0)) // NO COMPRUEBA SI LA IP ES ALCANZABLE
                    System.out.println(ip);
            }
        }
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad31;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;
/**
 *
 * @author Vyacheslav Shylyayev
 */
public class Actividad31 {

    public static void main(String[] args) throws SocketException, IOException {
        // TODO code application logic here
        
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        NetworkInterface i;
        while (interfaces.hasMoreElements()){
            i = interfaces.nextElement();
            //Pergunto por la información de la Interfaz (Broadcast)
            System.out.println(i.toString());
            System.out.println("mtu " + i.getMTU());
            System.out.println("MAC " + Arrays.toString(i.getHardwareAddress()));
            Enumeration<InetAddress> adresses = i.getInetAddresses();
            // Actividad 32 Aquí
            while(adresses.hasMoreElements()){
                InetAddress ia = adresses.nextElement();
                // Preguntamos por la InetAdress para saber la info del Host
                if(ia instanceof Inet4Address)
                    System.out.println("inet IPv4 " + ia.getHostAddress());
                if(ia instanceof Inet6Address)
                    System.out.println("inet IPv6 " + ia.getHostAddress());
                System.out.println("MultiCast Global " + ia.isMCGlobal());
                System.out.println("Is Reacheable " + ia.isReachable(0));
            }
        }
    }
    
}

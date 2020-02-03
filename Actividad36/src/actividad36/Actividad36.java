/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad36;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 *
 * @author usuario
 */
public class Actividad36 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        
        final int TIEMPO_ESPERA_RESPUESTA = 50;
        
        for(int i = minIpHost; i <= maxIpHost; i++){
            for (int j = 1; j < 255; j++) {
                String ip = preIP+i+"."+j;
                InetAddress ia = InetAddress.getByName(preIP+i+"."+j);
                if(ia.isReachable(TIEMPO_ESPERA_RESPUESTA)) {// Timeout 0. NO COMPRUEBA SI LA IP ES ALCANZABLE, porque no da tiempo
                    System.out.println("IP ENCONTRADA : " + ip);
                    contadorIps++;
                }
            }
        }
        System.out.println("Se han encontrado : " + contadorIps + " IPs");
    }
    
}

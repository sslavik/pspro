/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_fecha_hora_udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Vyacheslav Shylyayev
 */
public class ServidorFechaHoraUDP {

    private final static int MAX_BYTES = 1024;
    private final static String COD_TEXTO = "UTF-8";
    static String nombreServidor;
    static String ipHost;
    static int numPuerto;

   
    public static void main(String[] args) throws IOException {



        numPuerto = 8100; // CREAMOS EL SERVIDOR EN EL PUERTO 8100


        // CREAMOS EL SERVIDOR UDP
        try (DatagramSocket serverSocket = new DatagramSocket(numPuerto)) {

            System.out.println("Creado socket de datagramas. PUERTO : " + numPuerto );

            while (true) {
                System.out.println("Esperando datagramas. [FORMATO DE HORA]");

                byte[] datosRecibidos = new byte[MAX_BYTES]; // DATAGRAMAS SE GUARDAN AQUI

                DatagramPacket paqueteRecibido
                        = new DatagramPacket(datosRecibidos, datosRecibidos.length);

                serverSocket.receive(paqueteRecibido); // RECIBE EL PAQUETE

                String mensajeRecibido = new String(paqueteRecibido.getData(), COD_TEXTO); // PARSEAMOS LOS DATOS RECIBIDOS

                System.out.println(mensajeRecibido); // IMPRIMIMOS EL FORMATO

                // PARSEAMOS LA FECHA Y SE LA DEVOLVEMOS AL CLIENTE
                String fechaParseada = "";
                try {
                    fechaParseada = new SimpleDateFormat(mensajeRecibido).format(new Date()); // LA GUARDAMOS EN UN STRING
                } catch (Exception e) {
                    fechaParseada = "ERR";
                }
                
                

                InetAddress IPCliente = paqueteRecibido.getAddress();
                int puertoCliente = paqueteRecibido.getPort();
                System.out.println("Recibido datagrama de " + IPCliente.getHostAddress() + ":" + puertoCliente);


                byte[] b = fechaParseada.getBytes(COD_TEXTO);

                DatagramPacket paqueteEnviado = new DatagramPacket(b, b.length, IPCliente, puertoCliente);
                serverSocket.send(paqueteEnviado);
            }
        } catch (SocketException ex) {
            System.out.println("Excepción de sockets");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Excepción de E/S");
            ex.printStackTrace();
        }


    }
}

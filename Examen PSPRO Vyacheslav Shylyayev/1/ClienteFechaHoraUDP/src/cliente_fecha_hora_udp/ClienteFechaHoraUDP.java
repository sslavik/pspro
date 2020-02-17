/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_fecha_hora_udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author Vyacheslav Shylyayev
 */
public class ClienteFechaHoraUDP {

   private final static int MAX_BYTES = 1024;
    private final static String COD_TEXTO = "UTF-8";
    static String nombreServidor;
    static String ipHost;
    static int numPuerto;

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.err.println("ERROR, indicar: HOST NUM_PUERTO. El puerto del ejercicio es : 8100");
            System.exit(1);

        }

        if (args.length == 2) {
            ipHost = args[0];
            numPuerto = Integer.parseInt(args[1]);
        }
        
        try (DatagramSocket clientSocket = new DatagramSocket();
                InputStreamReader isrStdIn = new InputStreamReader(System.in);
                BufferedReader brStdIn = new BufferedReader(isrStdIn)) {
            String lineaLeida;
            System.out.println("Introducir líneas con Formato de fecha \"SimpleDateFormat\". Línea vacía para terminar.");
            System.out.print("Cliente>");
            while ((lineaLeida = brStdIn.readLine()) != null
                    && lineaLeida.length() > 0) { // COMPROBAMOS QUE LOS DATOS INTRODUCIDOS NO SEAN VACIOS
                InetAddress IPServidor = InetAddress.getByName(ipHost);
                byte[] b = lineaLeida.getBytes(COD_TEXTO); // LO PARSEAMOS A BYTE
                DatagramPacket sendPacket = new DatagramPacket(b, b.length,
                        IPServidor, numPuerto);
                clientSocket.send(sendPacket); // ENVIAMOS EL PACKETE CREADO POR UDP
                byte[] datosRecibidos = new byte[MAX_BYTES];
                DatagramPacket paqueteRecibido
                        = new DatagramPacket(datosRecibidos, datosRecibidos.length);
                clientSocket.receive(paqueteRecibido); // RECIBIMOS EL PACKETE PARSEADO DEL SERVIDOR
                String respuesta
                        = new String(paqueteRecibido.getData(), COD_TEXTO);
                System.out.println("respuesta: " + respuesta); // IMPRIMIMOS LA FECHA
                System.out.print("Cliente>");
            }
        }
    }
}

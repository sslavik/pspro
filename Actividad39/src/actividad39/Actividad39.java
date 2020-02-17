/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad39;

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
public class Actividad39 {

    private final static int MAX_BYTES = 1024;
    private final static String COD_TEXTO = "UTF-8";
    static String nombreServidor;
    static String ipHost;
    static int numPuerto;
    static Boolean servidor;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.err.println("ERROR, indicar: puerto.");
            System.exit(1);

        }

        if (args.length == 1) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Escriba el nombre del SERVIDOR : ");
                nombreServidor = br.readLine();

            }
            numPuerto = Integer.parseInt(args[0]);
            servidor = true;
        }

        if (args.length == 2) {
            ipHost = args[0];
            numPuerto = Integer.parseInt(args[1]);
            servidor = false;
        }
        if (servidor) {
            try (DatagramSocket serverSocket = new DatagramSocket(numPuerto)) {

                System.out.println("Creado socket de datagramas.");

                while (true) {
                    System.out.println("Esperando datagramas.");

                    byte[] datosRecibidos = new byte[MAX_BYTES]; // DATAGRAMAS SE GUARDAN AQUI

                    DatagramPacket paqueteRecibido
                            = new DatagramPacket(datosRecibidos, datosRecibidos.length);

                    serverSocket.receive(paqueteRecibido); // RECIBE EL PAQUETE

                    String mensajeRecibido = new String(paqueteRecibido.getData(), COD_TEXTO); // PARSEAMOS LOS DATOS RECIBIDOS

                    System.out.println(mensajeRecibido);

                    // SACAMOS LOS DATOS DEL MENSAJE RECIBIDO
                    mensajeRecibido = mensajeRecibido.replace("#", "");
                    String[] campos = mensajeRecibido.split("@");

                    String mensaje = campos[0];
                    String cliente = campos[1];

                    InetAddress IPCliente = paqueteRecibido.getAddress();
                    int puertoCliente = paqueteRecibido.getPort();
                    System.out.println("Recibido datagrama de " + IPCliente.getHostAddress() + ":" + puertoCliente);

                    String respuesta = "#" + mensaje + "@" + nombreServidor + "#";

                    byte[] b = respuesta.getBytes(COD_TEXTO);

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
        } else {
            try (DatagramSocket clientSocket = new DatagramSocket();
                    InputStreamReader isrStdIn = new InputStreamReader(System.in);
                    BufferedReader brStdIn = new BufferedReader(isrStdIn)) {
                String lineaLeida;
                System.out.println("Introducir líneas. Línea vacía para terminar.");
                System.out.print("Línea>");
                while ((lineaLeida = brStdIn.readLine()) != null
                        && lineaLeida.length() > 0) {
                    InetAddress IPServidor = InetAddress.getByName(ipHost);
                    byte[] b = lineaLeida.getBytes(COD_TEXTO);
                    DatagramPacket sendPacket = new DatagramPacket(b, b.length,
                            IPServidor, numPuerto);
                    clientSocket.send(sendPacket);
                    byte[] datosRecibidos = new byte[MAX_BYTES];
                    DatagramPacket paqueteRecibido
                            = new DatagramPacket(datosRecibidos, datosRecibidos.length);
                    clientSocket.receive(paqueteRecibido);
                    String respuesta
                            = new String(paqueteRecibido.getData(), COD_TEXTO);
                    System.out.println("respuesta: " + respuesta);
                    System.out.print("Línea>");
                }
            }
        }

    }
}

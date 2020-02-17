/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_recopilador_datos;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Vyacheslav Shylyayev
 */
public class ServidorRecopiladorDatos {

    public static void main(String[] args) {
        int numPuerto = 8400; // LE PASAMOS EL PUERTO

        try (ServerSocket socketServidor = new ServerSocket(numPuerto)) {

            while (true) {
                System.out.printf("Creado socket de servidor en puerto %d, esperando conexiones de clientes.\n", numPuerto);
                try (Socket socketComunicacion = socketServidor.accept()) {
                    System.out.println("Cliente conectado desde "
                            + socketComunicacion.getInetAddress().getHostAddress() + ":" + socketComunicacion.getPort());

                    try (InputStream isDeCliente = socketComunicacion.getInputStream();
                            OutputStream osACliente = socketComunicacion.getOutputStream();
                            InputStreamReader isrDeCliente = new InputStreamReader(isDeCliente);
                            BufferedReader brDeCliente = new BufferedReader(isrDeCliente);
                            OutputStreamWriter oswACliente = new OutputStreamWriter(osACliente);
                            BufferedWriter bwACliente = new BufferedWriter(oswACliente);
                            BufferedOutputStream bosACliente = new BufferedOutputStream(osACliente)) {
                        String comandoRecibido;
                        while ((comandoRecibido = brDeCliente.readLine()) != null) {
                            System.out.printf("Comando recibido de %s:%d: %s\n",
                                    socketComunicacion.getInetAddress(), socketComunicacion.getPort(),
                                    comandoRecibido);

                            // OBTENEMOS IDENTIFICADOR Y DATOS A SUMAR
                            String respuesta;
                            try {
                                int sumatorio = 0;
                                int tam = comandoRecibido.split(" ").length;
                                if (tam == 1){
                                    throw new Exception();
                                }
                                for (int i = 0; i < tam; i++) {
                                    if (i == 0) {
                                        System.out.println(" IDENTIFICADOR DEL SUMATORIO : " + comandoRecibido.split(" ")[i]);
                                    } else {
                                        sumatorio += Integer.parseInt(comandoRecibido.split(" ")[i]);
                                    }
                                }
                                respuesta = "OK " + String.valueOf(sumatorio);
                            } catch (NumberFormatException nfe){
                                respuesta = "ERR " + comandoRecibido;
                            } catch (Exception exception){
                                respuesta = "ERR";
                            } 

                            bwACliente.write(respuesta);
                            bwACliente.newLine();
                            bwACliente.flush();

                        }
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println("Excepción de E/S");
            ex.printStackTrace();
            System.exit(1);
        }
        System.out.println("Cliente desconectado.");
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_fecha_hora_tcp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Vyacheslav Shylyayev
 */
public class ClienteFechaHoraTCP {

    public static void main(String[] args) {

        String nomHost = "127.0.0.1"; // HOST DEL SERVIDOR
        int numPuerto = 8200; // PUERTO DEL SERVIDOR
        try (Socket socketComunicacion = new Socket(nomHost, numPuerto)) {
            System.out.printf("Conectado a servidor. HOST : %s PORT : %d \n", nomHost, numPuerto);

            try (OutputStream osAServidor = socketComunicacion.getOutputStream();
                    InputStream isDeServidor = socketComunicacion.getInputStream();
                    InputStreamReader isrDeServidor = new InputStreamReader(isDeServidor);
                    OutputStreamWriter oswAServidor = new OutputStreamWriter(osAServidor);
                    BufferedWriter bwAServidor = new BufferedWriter(oswAServidor);
                    BufferedReader brDeServidor = new BufferedReader(isrDeServidor);
                    InputStreamReader isrStdIn = new InputStreamReader(System.in);
                    BufferedReader brStdIn = new BufferedReader(isrStdIn)) {

                System.out.print("Cliente> ");
                while (true) {
                    // ENVIAMOS UNA RESPUESTA AL SERVIDOR
                    String comando = brStdIn.readLine();  // Lee comando para servidor
                    bwAServidor.write(comando);           // Envía comando a servidor
                    bwAServidor.newLine();
                    bwAServidor.flush();

                    // OBTENEMOS UNA RESPUESTA DEL SERVIDOR
                    String lineaDeRespuesta = brDeServidor.readLine();  // Lee linea: numbytes mensaje

                    if (lineaDeRespuesta.equals("ERR")) {
                        System.out.println("ERROR: El formato \"SimpleDateFormat\" pasado NO es correcto.");
                    } else {
                        System.out.println("Respuesta : " + lineaDeRespuesta);
                    }

                    System.out.println();
                    System.out.print("Cliente> ");
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Host desconocido: " + nomHost);
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("Excepción de E/S");
            ex.printStackTrace();
            System.exit(1);
        }

    }

}

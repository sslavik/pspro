/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_recopilador_datos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Vyacheslav Shylyayev
 */
public class ClienteRecopiladorDatos {

   
    public static void main(String[] args) {
        String nomHost = "127.0.0.1"; // HOST DEL SERVIDOR
        String respuesta = "";
        int numPuerto = 8400; // PUERTO DEL SERVIDOR

        if(args.length < 2){
            System.out.println("ERROR: Argumentos no validos");
            System.out.println("PASAR : HOST IDENTIFICADOR NUM1 NUM2 NUM3 ...");
            System.exit(0);
        }
        if(args.length >= 2){
            for (int i = 0; i < args.length; i++) {
                if(i == 0)
                    nomHost = args[i];
                else if(i == args.length-1)
                    respuesta += args[i];
                else {
                    respuesta += args[i] + " ";
                }
            }
        }
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

                // ENVIAMOS UNA RESPUESTA AL SERVIDOR
                bwAServidor.write(respuesta);           // Envía comando a servidor
                bwAServidor.newLine();
                bwAServidor.flush();

                // OBTENEMOS UNA RESPUESTA DEL SERVIDOR
                String lineaDeRespuesta = brDeServidor.readLine();  // Lee linea: numbytes mensaje

                System.out.println("Respuesta : " + lineaDeRespuesta); // ESCRIBIMOS LA RESPUESTA POR PANTALLA

                
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

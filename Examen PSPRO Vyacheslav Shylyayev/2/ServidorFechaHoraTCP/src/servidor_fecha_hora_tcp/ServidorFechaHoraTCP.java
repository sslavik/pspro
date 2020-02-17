/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_fecha_hora_tcp;

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
public class ServidorFechaHoraTCP {

    public static void main(String[] args) {

        int numPuerto = 8200; // LE PASAMOS EL PUERTO

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

                            // PARSEAMOS A SIMPLEDATEFORMAT LA LINEA RECIBIDA DEL CLIENTE
                            String fechaParseada;
                            try {
                                fechaParseada = new SimpleDateFormat(comandoRecibido).format(new Date());
                            } catch (Exception e) {
                                fechaParseada = "ERR";
                            }

                            bwACliente.write(fechaParseada);
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

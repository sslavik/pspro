/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_fecha_hora_tcp_m;

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vyacheslav Shylyayev
 */
public class ServidorFechaHoraTCP_M implements Runnable {

    final Socket socketComunicacion;
    
    public ServidorFechaHoraTCP_M(Socket socketCom){
        this.socketComunicacion = socketCom;
    }
    
    public static void main(String[] args) {

        int numPuerto = 8200; // LE PASAMOS EL PUERTO
         
        
        
        try (ServerSocket socketServidor = new ServerSocket(numPuerto)) {

            while (true) {
                System.out.printf("Creado socket de servidor en puerto %d, esperando conexiones de clientes.\n", numPuerto);
                Socket socketComunicacionTmp = socketServidor.accept();
                System.out.println("Cliente conectado desde "
                            + socketComunicacionTmp.getInetAddress().getHostAddress() + ":" + socketComunicacionTmp.getPort());
                
                Thread serv = new Thread(new ServidorFechaHoraTCP_M(socketComunicacionTmp));
                serv.start();

            }

        } catch (IOException ex) {
            System.out.println("Excepción de E/S");
            ex.printStackTrace();
            System.exit(1);
        }
        System.out.println("Cliente desconectado.");
    }

    @Override
    public void run() {
        try (InputStream isDeCliente = this.socketComunicacion.getInputStream();
                OutputStream osACliente = this.socketComunicacion.getOutputStream();
                InputStreamReader isrDeCliente = new InputStreamReader(isDeCliente);
                BufferedReader brDeCliente = new BufferedReader(isrDeCliente);
                OutputStreamWriter oswACliente = new OutputStreamWriter(osACliente);
                BufferedWriter bwACliente = new BufferedWriter(oswACliente);
                BufferedOutputStream bosACliente = new BufferedOutputStream(osACliente)) {
            String comandoRecibido;
            while ((comandoRecibido = brDeCliente.readLine()) != null) {
                System.out.printf("Comando recibido de %s:%d: %s\n",
                        this.socketComunicacion.getInetAddress(), this.socketComunicacion.getPort(),
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
        } catch (IOException ex) {
            Logger.getLogger(ServidorFechaHoraTCP_M.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

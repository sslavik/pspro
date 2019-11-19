/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redireccionfichero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author Vyacheslav Shylyayev
 */
public class RedireccionFichero {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        String[] comando = new String[]{"md5sum"};
        ProcessBuilder pb = new ProcessBuilder(comando);
        boolean error = false;

        if (args.length < 1){
            System.out.println("No existe fichero de salida");
            System.exit(0);
        }
        File fSalida = new File(args[0]);
        fSalida.createNewFile();
        
        

        try (BufferedReader br0 = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Introduzca líneas para seleccionar las que contienen la expresión regular.");
            System.out.println("Introduzca una línea vacía para terminar.");
            System.out.print("md5fich>");
            String lin = br0.readLine();
            
            while (lin.length() > 0) {
                
                if(error) {
                    System.out.print("md5fich>");
                    lin = br0.readLine();
                    error = false;
                }
                
                File f = new File(lin);
                
                if (!f.exists()){
                    System.out.println("La ruta pasada no existe");
                    error = true;
                    continue;
                }
                if (!f.isFile()){
                    System.out.println("La ruta pasada no es una archivo");
                    error = true;
                    continue;
                }
                pb.redirectInput(ProcessBuilder.Redirect.from(f));
                pb.redirectOutput(ProcessBuilder.Redirect.appendTo(fSalida));
                pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                Process p = pb.start();

                OutputStream os = p.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

                    bw.write(lin);
                    bw.newLine();

                p.waitFor();
                
                System.out.print("md5fich>");
                lin = br0.readLine();

                

            }

        } catch (Exception e) {
            System.err.println("Error durante ejecución del proceso");
            System.err.println("Información detallada");
            System.err.println("---------------------");
            e.printStackTrace();
            System.err.println("---------------------");
            System.exit(-1);
        }
        
    }
    
}

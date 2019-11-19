/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md5fich;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;

/**
 *
 * @author Vyacheslav Shylyayev
 */
public class MD5Fich {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        String[] comando = new String[]{"md5sum"};
        ProcessBuilder pb = new ProcessBuilder(comando);
        boolean error = false;


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
                pb.redirectInput(Redirect.from(f));
                pb.redirectOutput(Redirect.INHERIT);
                pb.redirectError(Redirect.INHERIT);
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

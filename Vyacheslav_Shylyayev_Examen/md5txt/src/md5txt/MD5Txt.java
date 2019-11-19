/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md5txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
public class MD5Txt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        String[] comando = new String[]{"md5sum"};

        try (BufferedReader br0 = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Introduzca líneas para seleccionar las que contienen la expresión regular.");
            System.out.println("Introduzca una línea vacía para terminar.");
            System.out.print("md5txt>");
            String lin = br0.readLine();

            while (lin.length() > 0) {

                ProcessBuilder pb = new ProcessBuilder(comando);

                // pb.redirectInput(Redirect.INHERIT);
                pb.redirectOutput(Redirect.INHERIT);
                pb.redirectError(Redirect.INHERIT);
                Process p = pb.start();

                try (OutputStream os = p.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        BufferedWriter bw = new BufferedWriter(osw);) {

                    bw.write(lin);
                    bw.newLine();

                }
                p.waitFor();

                System.out.print("md5txt>");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas.processbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hacka
 */
public class PruebasProcessBuilder {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        List<String> comandos = new ArrayList();
        
        if(args.length == 0) {
            System.out.println("Necesita un argumento para ejecutar un proceso");
            System.exit(0);
        }
        try{
            comandos.addAll(Arrays.asList(args));
            
            
            ProcessBuilder pb = new ProcessBuilder(comandos);
            
            
            Process p = pb.start();
            
            
            
            // Process isAlive??
            System.out.println("Is Alive : " + p.isAlive());
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
            String linea;
            
            // Preguntamos la información del proceso
            System.out.println("Process Info : ");
                System.out.println(p.info());
            System.out.println("Process PID : "+p.pid());

            
            while((linea = reader.readLine()) != null){
                System.out.println(linea);
            }
            
            pb.directory(new File("/home/usuario/Escritorio"));
            
            // Where is the Directory ?
            System.out.println("Directory  : " + pb.directory());
            
            // Process Finished?
            p.waitFor();
            System.out.println("Is Alive : " + p.isAlive());
            
        }
        catch (IOException x){
            System.out.println(x.getMessage());
        }
        catch (InterruptedException x){
            System.out.print(x.getMessage());
        }
    }
    
}

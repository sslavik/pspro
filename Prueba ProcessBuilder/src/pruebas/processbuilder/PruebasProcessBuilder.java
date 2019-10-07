/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas.processbuilder;

import java.io.BufferedReader;
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
            ProcessBuilder pb2 = new ProcessBuilder("ls /home");
            
            List<ProcessBuilder> lista = new ArrayList();
            
            List<Process> p = ProcessBuilder.startPipeline(lista);
            
            
            
            // Process isAlive??
            System.out.println("Is Alive : " + p.get(0).isAlive());
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.get(0).getInputStream()));
            BufferedReader errorR = new BufferedReader(new InputStreamReader(p.get(0).getErrorStream()));
            
            String linea;
            
            // Preguntamos la información del proceso
            System.out.println("Process Info : ");
                System.out.println(p.get(0).info());
            System.out.println("Process PID : "+p.get(0).pid());

            
            while((linea = reader.readLine()) != null){
                System.out.println(linea);
            }
            while((linea = errorR.readLine()) != null){
                System.out.println(linea);
            }
            // Where is the Directory ?
            System.out.println("Directory  : " + pb.directory());
            
            // Process Finished?
            p.get(0).waitFor();
            System.out.println("Is Alive : " + p.get(0).isAlive());
            
        }
        catch (IOException x){
            System.out.println(x.getMessage());
        }
        catch (InterruptedException x){
            System.out.print(x.getMessage());
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

/**
 *
 * @author usuario
 */
public class HolaDesdeRunnable implements Runnable {
    private int num;
    public HolaDesdeRunnable(int num) {
        this.num = num;
    }
    @Override
    public void run() {
        System.out.println("Hola desde una clase que implementa Runnable : " + num);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.table;

import edu.martin.dida.proyectofinciclo.App;
import edu.martin.dida.proyectofinciclo.ControladorLogin.ControladorLogin;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 *
 * @author Markyuu
 */
public class ThreadRun implements Runnable{
    
    static byte[] buf = new byte[1000];


    private final ControladorTabPane controller;

    
    public ThreadRun(ControladorTabPane controller) {
        this.controller = controller ;
    }


   @Override
    public void run() {
        while(true){
                try {
                
                    DatagramPacket p = new DatagramPacket(buf, buf.length);
                  App.ms.receive(p);
                  String text = new String(p.getData(), p.getOffset(), p.getLength());
                      controller.recibidos.setStyle("-fx-text-fill: white;");
                      
                          controller.append(text);
                               
                
                } catch (IOException ex) {
                }
                
            
    }
        
    }
}


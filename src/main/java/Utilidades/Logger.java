/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author marti
 */
public class Logger {
        static Path path = Paths.get(System.getProperty("user.dir") + "/log.txt");

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
    static LocalDateTime now = LocalDateTime.now();  
    
    private static Logger log;
    public static Logger logear(){
        if(log == null){
            log = new Logger();
        }
        return log;
    }
    

    
    public static void log(String msg) throws IOException {
        BufferedWriter bw;
        
        boolean exists = Files.exists(path);
        
        if(exists==false){
            bw = Files.newBufferedWriter(path);

            bw.write("New Log: " +dtf.format(now) + "\r\n=> " +msg + "\r\n");
            System.out.println(dtf.format(now) + " => " +msg);
            bw.flush();
            bw.close();
            
        }else{
            bw = Files.newBufferedWriter(path,StandardOpenOption.APPEND);

            bw.write("New Log: " +dtf.format(now) + "\r\n=> " +msg + "\r\n");
            System.out.println(dtf.format(now) + " => " +msg);
            bw.flush();
            bw.close();
        
        }
            

    }
    
        public static void logInfo(String msg) throws IOException {
        
        boolean exists = Files.exists(path);
            System.out.println(exists);
            
        if(exists==false){
           BufferedWriter bw1 = Files.newBufferedWriter(path);

            bw1.write("New Log: " +dtf.format(now) + "\r\n[INFO]=> " +msg+ "\r\n===========================================\r\n");
            System.out.println(dtf.format(now) + " [INFO] => " +msg);
            bw1.flush();
            bw1.close();
            
        }else{
           BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.APPEND);

            bw.write("New Log: " +dtf.format(now) + "\r\n[INFO]=> " +msg + "\r\n===========================================\r\n");
            System.out.println(dtf.format(now) + " [INFO] => " +msg);
            bw.flush();
            bw.close();
        
        }
        }
}

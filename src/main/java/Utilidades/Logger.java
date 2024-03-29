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
import java.util.logging.Level;

/**
 *
 * @author marti
 */
public class Logger {
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
        static Path path = Paths.get(System.getProperty("user.dir") + "/TheBull.log");

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
    static LocalDateTime now = LocalDateTime.now();  
    
    static String ERROR = "[ERROR]";
    static String INFO = "[INFO]";

    
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
    
        public static void logInfo(String msg, int level) throws IOException {
        boolean exists = Files.exists(path);

            if(level==1){
                if(exists==false){
                    BufferedWriter bw1 = Files.newBufferedWriter(path);

                    bw1.write("New Log: " +dtf.format(now) + "\r\n" + INFO + "=> " +msg+ "\r\n===========================================\r\n");
                    bw1.flush();
                    bw1.close();

         }else{
                    BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.APPEND);

                    bw.write("New Log: " +dtf.format(now) + "\r\n" + INFO + " => " +msg + "\r\n===========================================\r\n");
                    bw.flush();
                    bw.close();
        
            }
            }else if (level==2){
                if(exists==false){
                    BufferedWriter bw1 = Files.newBufferedWriter(path);

                    bw1.write("New Log: " +dtf.format(now) + "\r\n" + ERROR + "=> " +msg+ "\r\n===========================================\r\n");
                    bw1.flush();
                    bw1.close();

            }else{
                    BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.APPEND);

                    bw.write("New Log: " +dtf.format(now) + "\r\n" + ERROR + " => " +msg + "\r\n===========================================\r\n");
                    bw.flush();
                    bw.close();
        
            }
            }
        
            

        }
}

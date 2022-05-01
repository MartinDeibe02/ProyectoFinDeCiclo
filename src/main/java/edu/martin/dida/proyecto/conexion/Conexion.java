/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyecto.conexion;


import Utilidades.Logger;
import java.io.IOException;
import javax.sql.DataSource;
import java.sql.*;
import java.sql.Connection;


/**
 *
 * @author Markyuu
 */
public class Conexion {
    
    public static final String URL_CONEXION = "jdbc:h2:./thebull";
    public static final String USUARIO = "root";
    public static final String PASSWORD = "";
    private static Connection conexion;
    
    
    public static Connection getConnection() throws IOException{
        try{
            conexion = DriverManager.getConnection(URL_CONEXION, USUARIO, PASSWORD);
        }catch(SQLException e){
            Logger.logInfo("No se ha podido establecer conexion con la base de datos", 2);
            e.printStackTrace();
        }
        return conexion;
    }
    
    public static void closeConnection(Connection conn) throws IOException{
        try{
            conn.close();
        }catch(SQLException e){
            Logger.logInfo("No se ha podido cerrar la conexion con la base de datos", 2);
            e.printStackTrace();
        }
    }    
    
}

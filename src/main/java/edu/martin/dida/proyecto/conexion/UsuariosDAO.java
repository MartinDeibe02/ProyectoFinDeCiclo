/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyecto.conexion;

import POJO.Usuario;
import Utilidades.Logger;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Markyuu
 */
public class UsuariosDAO {
 
    
    public UsuariosDAO() throws IOException{
        crearTabla();
    }

    private void crearTabla() throws IOException {
        try (Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS usuarios"
                    + "(id INTEGER auto_increment,"
                    + "user VARCHAR(255),"
                    + "email VARCHAR(255),"
                    + "dateBirth VARCHAR(255),"
                    + "password VARCHAR(255),"
                    + "permisos VARCHAR(45));";
            statement.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void saveEdit(Usuario user){
                if(user.getId()==0){
            insert(user);
        }else{
            edit(user);
        }
    }

    public void insertLogin(Usuario user) throws IOException {
        try (Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "INSERT INTO usuarios(user, email, dateBirth, password, permisos)"
                    + "VALUES ('"+ user.getUser()
                    +"','"+ user.getEmail()
                    +"','"+ user.getDateBirth()
                    +"','"+ user.getPassword()
                    +"','"+ "user" +"');";
            
                    statement.executeUpdate(sql);
                    Logger.logear().logInfo("Registrado Satisfactoriamente", 1);

        }catch(SQLException e){
            e.printStackTrace();
        }    }

    private void edit(Usuario user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void insert(Usuario user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

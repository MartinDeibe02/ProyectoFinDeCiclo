/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyecto.conexion;

import POJO.Team;
import POJO.Usuario;
import Utilidades.Logger;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Markyuu
 */
public class UsuariosDAO {
 
    
    public UsuariosDAO() throws IOException{
        crearTabla();
        insertAdmin();
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
    
    public void saveEdit(Usuario user) throws IOException, IOException{
        if(user.getId()==0){
            insert(user);
        }else{
            edit(user);
        }
    }

    public void insertAdmin() throws IOException{
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            
                String sql = "SELECT * FROM usuarios WHERE user LIKE 'ADMIN'";
                ResultSet rs = statement.executeQuery(sql);
                if(!(rs.next())){
                    Statement statement1 = conexion.createStatement();
                    String sql2= "INSERT INTO usuarios(user,email, dateBirth,password, permisos) "
                            + "VALUES ('ADMIN', 'thebullap01@gmail.com','ADMIN','ADMIN','admin')";
                    statement1.executeUpdate(sql2);

                }
            
        }catch(SQLException e){
            Logger.logInfo(e.getMessage(), 2);
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
        }    
    }

    
    private void edit(Usuario user) throws IOException {
        try (Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "UPDATE usuarios SET user ='" + user.getUser()
                        +"',email = '"+ user.getEmail()
                        +"',dateBirth = '"+ user.getDateBirth()
                        +"',password = '"+ user.getPassword()
                        +"',permisos = '"+ user.getPermisos()
                        +"'WHERE id =" + user.getId();

            
                    statement.executeUpdate(sql);
                    Logger.logear().logInfo("Usuario modificado Satisfactoriamente", 1);

        }catch(SQLException e){
            e.printStackTrace();
        }    
    }

    private void insert(Usuario user) throws IOException {
        try (Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "INSERT INTO usuarios(user, email, dateBirth, password, permisos)"
                    + "VALUES ('"+ user.getUser()
                    +"','"+ user.getEmail()
                    +"','"+ user.getDateBirth()
                    +"','"+ user.getPassword()
                    +"','"+ user.getPermisos() +"');";
            
                    statement.executeUpdate(sql);
                    Logger.logear().logInfo("Usuario modificado Satisfactoriamente", 1);

        }catch(SQLException e){
            e.printStackTrace();
        }    
    }
    
    public void deleteUser(Usuario user) throws IOException{
        try (Connection conexion = Conexion.getConnection()){
            Statement statementPlayer = conexion.createStatement();
            Statement statementUser = conexion.createStatement();
            Statement statementTeam = conexion.createStatement();
            
            String sqlPlayer = "UPDATE players SET team = 'Agente Libre', user='ADMIN' WHERE user LIKE '" + user.getUser()+"'";
            String sqlTeam = "DELETE FROM teams WHERE user LIKE '" + user.getUser() + "'";
            String sqlUser = "DELETE FROM usuarios WHERE id=" + user.getId();
            
            statementPlayer.executeUpdate(sqlPlayer);
            statementTeam.executeUpdate(sqlTeam);
            statementUser.executeUpdate(sqlUser);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public int validateLogin(String nombre, String contraseña) throws IOException{
                    try (Connection conexion = Conexion.getConnection()){
                        PreparedStatement prep = conexion.prepareStatement("SELECT * FROM usuarios WHERE user=? and password=? and permisos=?");
                        prep.setString(1, nombre);
                        prep.setString(2, contraseña);
                        prep.setString(3, "user");

                        PreparedStatement prep2 = conexion.prepareStatement("SELECT * FROM usuarios WHERE user=? and password=? and permisos=?");
                        prep2.setString(1, nombre);
                        prep2.setString(2, contraseña);
                        prep2.setString(3, "admin");


                        ResultSet rs = prep.executeQuery();
                        ResultSet rs2 = prep2.executeQuery();

                        if(rs.next()){
                            return 1;
                        }
                        if(rs2.next()){
                           return 2;
                       } 
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                return 3;
    } 
    
    
    public List<Usuario> buscarUser() throws IOException{
        List<Usuario> users = new ArrayList<>();
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT * FROM usuarios WHERE permisos LIKE 'user'";
            
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Usuario user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setUser(rs.getString("user"));
                user.setEmail(rs.getString("email"));
                user.setDateBirth(rs.getString("dateBirth"));
                user.setPassword(rs.getString("password"));
                user.setPermisos(rs.getString("permisos"));
                users.add(user);
            }
        }catch(SQLException e){
            //TODO
        }
        return  users;
    }

    public List<Usuario> buscarAdmin() throws IOException{
        List<Usuario> users = new ArrayList<>();
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT * FROM usuarios WHERE permisos LIKE 'admin'";
            
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Usuario user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setUser(rs.getString("user"));
                user.setEmail(rs.getString("email"));
                user.setDateBirth(rs.getString("dateBirth"));
                user.setPassword(rs.getString("password"));
                user.setPermisos(rs.getString("permisos"));
                users.add(user);
            }
        }catch(SQLException e){
            //TODO
        }
        return  users;
    }
    
    public int countUsers() throws IOException{
         try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT COUNT(*) FROM usuarios";
            
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                int count = rs.getInt(1);
                return count;
            }
        }catch(SQLException e){
            //TODO
        }
         return 0;
    }
}

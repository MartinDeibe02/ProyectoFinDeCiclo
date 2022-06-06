/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyecto.conexion;

import POJO.Player;
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
import java.util.logging.Level;

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
                    + "permisos VARCHAR(45),"
                    + "status VARCHAR(255));";
            statement.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void saveEditUser(Usuario user) throws IOException, IOException{
        if(user.getId()==0){
            insert(user);
        }else{
            edit(user);
        }
    }
    
    public void saveEditPlayer(Player player, String user) throws IOException{
        if(player.getPlayerId() == 0){
            insertPlayer(player, user);
        }else{
            editPlayer(player);
        }
    }
    
    public void saveEditTeam(Team team) throws IOException{
            if(team.getId() == 0){
                insertTeam(team);
            }else{
                editTeam(team);
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
            String sql = "INSERT INTO usuarios(user, email, dateBirth, password, permisos, status)"
                    + "VALUES ('"+ user.getUser()
                    +"','"+ user.getEmail()
                    +"','"+ user.getDateBirth()
                    +"','"+ user.getPassword()
                    +"','"+ "user"
                    +"','"+ "offline" +"');";
                    statement.executeUpdate(sql);
                    Logger.logear().logInfo("Registrado Satisfactoriamente", 1);

        }catch(SQLException e){
            e.printStackTrace();
        }    
    }
    
    public String searchUserPlayer(String team) throws IOException, SQLException{
            String user = "";
                try (Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT user FROM teams WHERE name LIKE '" + team + "'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                user = rs.getString(1);
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
                return user;
    }
    
    
    public List<Player> searchTeams() throws IOException{
        List<Player> playersTeam = new ArrayList<>();
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT * FROM teams";
            
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Player player = new Player();
                player.setPlayerId(rs.getInt("id"));
                player.setPlayerJersey(rs.getInt("jersey"));
                player.setPlayerName(rs.getString("name"));
                player.setPlayerPosition(rs.getString("position"));
                player.setPlayerHeight(rs.getString("height"));
                player.setPlayerWeight(rs.getString("weight"));
                player.setPlayerAge(rs.getInt("age"));
                player.setPlayerTeam(rs.getString("team"));
                player.setPlayerDraft(rs.getString("draft"));
                player.setPlayerCollege(rs.getString("college"));
                player.setPlayerNationality(rs.getString("nationality"));
                player.setPlayerPoints(rs.getDouble("points"));
                player.setPlayerRebbounds(rs.getDouble("rebbounds"));
                player.setPlayerAssist(rs.getDouble("assist"));
                player.setPlayerImage(rs.getString("image"));
                
                playersTeam.add(player);
            }
            
        }catch(SQLException ex){
            Logger.logInfo(ex.getMessage(), 2);
            ex.printStackTrace();
        }
        return playersTeam;
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
    
    
    public static void updateStatusOnline(String user) throws IOException {
        try (Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "UPDATE usuarios SET status = 'online' WHERE user LIKE '" + user + "'";
                    statement.executeUpdate(sql);

        }catch(SQLException e){
            e.printStackTrace();
        }    
    }        
        
    public static void updateStatusOffline(String user) throws IOException {
        try (Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "UPDATE usuarios SET status = 'offline' WHERE user LIKE '" + user + "'";
                    statement.executeUpdate(sql);

        }catch(SQLException e){
            e.printStackTrace();
        }    
    }
        
    public static boolean getStatus(String user) throws IOException{
            try (Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT * FROM usuarios WHERE user LIKE '"+ user + "' AND status LIKE 'online'";
            
                ResultSet rs = statement.executeQuery(sql);
                if(rs.next()){
                    return true;
                }else{
                    return false;
                }

        }catch(SQLException e){
            e.printStackTrace();
        }    
            return false;
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
                    Logger.logear().logInfo("Usuario insertado Satisfactoriamente", 1);

        }catch(SQLException e){
            e.printStackTrace();
        }    
    }
    
    public void deleteUser(Usuario user) throws IOException{
        try (Connection conexion = Conexion.getConnection()){
            Statement statementUser = conexion.createStatement();
            Statement statementTeam = conexion.createStatement();
            
            
            
            String sqlTeam = "DELETE FROM teams WHERE user LIKE '" + user.getUser() + "'";
            String sqlUser = "DELETE FROM usuarios WHERE id=" + user.getId();
            
            statementTeam.executeUpdate(sqlTeam);
            statementUser.executeUpdate(sqlUser);
            Logger.logear().logInfo("Usuario borrado", 1);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    
    public boolean getUserTeams(String user) throws IOException{
        try (Connection conexion = Conexion.getConnection()){
            Statement stmt = conexion.createStatement();
            String sql = "SELECT * FROM TEAMS WHERE user='"+ user +"'";
            ResultSet rs = stmt.executeQuery(sql);
            
            if(rs.next()){
                return true;
            }else{
                return false;
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
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
    
    
    
    
    public List<Usuario> usersJson() throws IOException{
        List<Usuario> users = new ArrayList<>();
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT * FROM usuarios";
            
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
    
    
    public String emailAdmin(String user) throws IOException{
        String email = "";
        try(Connection conexion = Conexion.getConnection()){
            Statement stmt = conexion.createStatement();
            String emailSql = "SELECT email FROM usuarios WHERE user LIKE '"+ user + "'";
            
            ResultSet rs = stmt.executeQuery(emailSql);
            
            while(rs.next()){
                email = rs.getString(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
         return email;
    }

    private void editPlayer(Player player) throws IOException {
        try(Connection conexion = Conexion.getConnection()){
            Statement stmt = conexion.createStatement();
            String sql = "UPDATE players SET jersey ='" + player.getPlayerJersey()
                        +"',name = '"+ player.getPlayerName()
                        +"',position = '"+ player.getPlayerPosition()
                        +"',height = '"+ player.getPlayerHeight()
                        +"',weight = '"+ player.getPlayerWeight()
                        +"',age = '"+ player.getPlayerAge()
                        +"',team = '"+ player.getPlayerTeam()
                        +"',college = '"+ player.getPlayerCollege()
                        +"',draft = '"+ player.getPlayerDraft()
                        +"',nationality = '"+ player.getPlayerNationality()
                        +"',points = '"+ player.getPlayerPoints()
                        +"',rebbounds = '"+ player.getPlayerRebbounds()
                        +"',assist = '"+ player.getPlayerAssist()
                        +"',image = '"+ player.getPlayerImage()
                        +"'WHERE id =" + player.getPlayerId();
            
            stmt.executeUpdate(sql);
            
        }catch(SQLException e){
            Logger.logInfo(e.getMessage(), 2);
        }
    }

    private void insertPlayer(Player player, String user) throws IOException {
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            
                String sql = "INSERT INTO players (jersey, name, position, height, weight, age, team, college, draft, nationality, points, rebbounds, assist,image, user)"
                        +"VALUES ('"+ player.getPlayerJersey()
                        +"','"+ player.getPlayerName()
                        +"','"+ player.getPlayerPosition()
                        +"','"+ player.getPlayerHeight()
                        +"','"+ player.getPlayerWeight()
                        +"','"+ player.getPlayerAge()
                        +"','"+ player.getPlayerTeam()
                        +"','"+ player.getPlayerCollege()
                        +"','"+ player.getPlayerDraft()
                        +"','"+ player.getPlayerNationality()
                        +"','"+ player.getPlayerPoints()
                        +"','"+ player.getPlayerRebbounds()
                        +"','"+ player.getPlayerAssist()
                        +"','"+ player.getPlayerImage()
                        +"','"+ user +"')";
                statement.executeUpdate(sql);
            
        }catch(SQLException e){
            Logger.logInfo(e.getMessage(), 2);
        }
    }
    
    public void deletePlayer(Player player) throws IOException{
            try(Connection conexion = Conexion.getConnection()){
        Statement stmt = conexion.createStatement();
        String sql = "DELETE FROM players WHERE id=" + player.getPlayerId();
        stmt.executeUpdate(sql);
    }   catch (SQLException ex) {
            java.util.logging.Logger.getLogger(PlayersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public List<Team> buscarTeam() throws IOException{
        List<Team> teams = new ArrayList<>();
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT * FROM teams";
            
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Team team = new Team();
                team.setId(rs.getInt("id"));
                team.setName(rs.getString("name"));
                team.setAbbreviation(rs.getString("abbreviation"));
                team.setCity(rs.getString("city"));
                team.setConference(rs.getString("conference"));
                team.setDivision(rs.getString("division"));
                team.setUser(rs.getString("user"));
                teams.add(team);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return  teams;
    }

    
    
    private void insertTeam(Team team) throws IOException {
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            
                String sql = "INSERT INTO teams (name, abbreviation, city, conference, division, user)"
                        +"VALUES ('"+ team.getName()
                        +"','"+ team.getAbbreviation()
                        +"','"+ team.getCity()
                        +"','"+ team.getConference()
                        +"','"+ team.getDivision()
                        +"','"+ team.getUser()+"');";
                statement.executeUpdate(sql);
            
        }catch(SQLException e){
            Logger.logInfo(e.getMessage(), 2);
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void editTeam(Team team) throws IOException {
    try(Connection conexion = Conexion.getConnection()){
                Statement statement = conexion.createStatement();
        String sql = "UPDATE teams SET name = '" + team.getName()
                    + "',abbreviation = '" + team.getAbbreviation()
                    + "',city = '" + team.getCity()
                    + "',conference = '" + team.getConference()
                    + "',division = '" + team.getDivision()
                    + "',user = '" + team.getUser()
                    + "' WHERE id= " + team.getId();
                    statement.executeUpdate(sql);

            }catch(SQLException e){
                Logger.logInfo(e.getMessage(), 2);
            }
    }
    
    public void deleteTeam(Team team) throws IOException{
        try (Connection conexion = Conexion.getConnection()){
            Statement statementPlayer = conexion.createStatement();
            Statement statementTeam = conexion.createStatement();
            
            String sqlPlayer = "UPDATE players SET team = 'Agente Libre' WHERE team LIKE '" + team.getName() +"'";
            String sqlTeam = "DELETE FROM teams WHERE id=" + team.getId();
            
            statementPlayer.executeUpdate(sqlPlayer);
            statementTeam.executeUpdate(sqlTeam);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    
    
}

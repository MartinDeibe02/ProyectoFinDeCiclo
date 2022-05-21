/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyecto.conexion;

import POJO.Player;
import POJO.Team;
import Utilidades.Logger;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Markyuu
 */
public class PlayersDAO {
    
    public PlayersDAO() throws IOException{
        crearTabla();
    }

    private void crearTabla() throws IOException {
        try (Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS players"
                    + "(id INTEGER auto_increment,"
                    +"jersey INTEGER(5)," 
                    +"name VARCHAR(255)," +
                    "position VARCHAR(255)," +
                    "height VARCHAR(255)," +
                    "weight VARCHAR(255)," +
                    "age INTEGER(100)," +
                    "team VARCHAR(255)," +
                    "college VARCHAR(255)," 
                    +"draft VARCHAR(255)," +
                    "nationality VARCHAR(255)," +
                    "points DOUBLE(255)," +
                    "rebbounds DOUBLE(255)," +
                    "assist DOUBLE(255)," +
                    "image VARCHAR(255)," +
                    "user VARCHAR(255),"
                    + "FOREIGN KEY(team) REFERENCES teams(name),"
                    + "FOREIGN KEY(user) REFERENCES usuarios(user))" ;
            statement.executeUpdate(sql);
        }catch(SQLException ex){
            ex.printStackTrace();
        }

    }
    
    public List<Player> buscarPlayer() throws IOException{
        List<Player> players = new ArrayList<>();
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT * FROM players";
            
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
                player.setPlayerUser(rs.getString("user"));
                
                players.add(player);
            }
            
        }catch(SQLException ex){
            
        }
        return players;
    }
    
    public List<Player> buscarPlayerByTeam(String user) throws IOException{
        List<Player> players = new ArrayList<>();
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT * FROM players WHERE user=" + user;
            
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
                player.setPlayerUser(rs.getString("user"));
                
                players.add(player);
            }
            
        }catch(SQLException ex){
            
        }
        return players;
    }
    
    public void exportarCSV() throws IOException{
        Path path = Paths.get(System.getProperty("user.dir") + "/players.csv");

        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT * FROM players";
            
            BufferedWriter bw = Files.newBufferedWriter(path);
            
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                bw.write(rs.getInt("jersey") + ",");
                bw.write(rs.getString("name") + ",");
                bw.write(rs.getString("position") + ",");
                bw.write(rs.getString("height") + ",");
                bw.write(rs.getString("weight") + ",");
                bw.write(rs.getString("age") + ",");
                bw.write(rs.getString("team") + ",");
                bw.write(rs.getString("draft") + ",");
                bw.write(rs.getString("college") + ",");
                bw.write(rs.getString("nationality") + ",");
                bw.write(rs.getString("points") + ",");
                bw.write(rs.getString("rebbounds") + ",");
                bw.write(rs.getString("assist") + ",");
                bw.write(rs.getString("image") + ",");
                bw.write(rs.getString("user") + "\n");
            }
            bw.flush();
            bw.close();
            
        }catch(SQLException e){
            //TODO
        }
    }    
    public void insertarCSV(List<Player> players) throws IOException{
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            for(int i = 0; i<players.size(); i++){
                System.out.println(players.get(i).getPlayerName());
                String sql = "INSERT INTO players (jersey, name, position, height, weight, age, team, college, draft, nationality, points, rebbounds, assist, image, user)"
                        +"VALUES ('"+ players.get(i).getPlayerJersey()
                        +"','"+ players.get(i).getPlayerName()
                        +"','"+ players.get(i).getPlayerPosition()
                        +"','"+ players.get(i).getPlayerHeight()
                        +"','"+ players.get(i).getPlayerWeight()
                        +"','"+ players.get(i).getPlayerAge()
                        +"','"+ players.get(i).getPlayerTeam()
                        +"','"+ players.get(i).getPlayerCollege()
                        +"','"+ players.get(i).getPlayerDraft()
                        +"','"+ players.get(i).getPlayerNationality()
                        +"','"+ players.get(i).getPlayerPoints()
                        +"','"+ players.get(i).getPlayerRebbounds()
                        +"','"+ players.get(i).getPlayerAssist()
                        +"','"+ players.get(i).getPlayerImage()
                        +"','"+ players.get(i).getPlayerUser()+"')";
                statement.executeUpdate(sql);
            }
        
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}

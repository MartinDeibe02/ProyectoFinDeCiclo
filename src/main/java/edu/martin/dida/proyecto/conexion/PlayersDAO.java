/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyecto.conexion;

import POJO.Player;
import POJO.Team;
import Utilidades.Logger;
import edu.martin.dida.proyectofinciclo.ControladorLogin.ControladorLogin;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
                    "user VARCHAR(255),"+
                    "FOREIGN KEY(team) REFERENCES teams(name)," 
                    + "FOREIGN KEY (user) REFERENCES usuarios(user)"
                    + "ON UPDATE CASCADE)" ;
            statement.executeUpdate(sql);
        }catch(SQLException ex){
            Logger.logInfo(ex.getMessage(), 2);
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
                
                players.add(player);
            }
            
        }catch(Exception e){
            e.printStackTrace();
            Logger.logInfo(e.getMessage(), 1);
        }
        return players;
    }
    
    public void saveEditTeam(Player player, String user) throws IOException{
        if(player.getPlayerId()==0){
            insertPlayer(player ,user);
        }else{
            editMyTeam(player);
        }
    }
    
    public List<Player> searchPlayerByTeam(String team) throws IOException{
        List<Player> playersTeam = new ArrayList<>();
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT * FROM players WHERE team LIKE '" + team + "'";
            
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
        }catch(Exception e){
            Logger.logInfo(e.getMessage(), 2);
        }
        return playersTeam;
    }
    
    public void exportarCSV() throws IOException{
        Path path = Paths.get(System.getProperty("user.dir") + "/players.csv");

        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT * FROM players";
            
            BufferedWriter bw;
            
            ResultSet rs = statement.executeQuery(sql);

                bw = Files.newBufferedWriter(path);
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
                bw.write(rs.getString("image") + "\n");
            }
            bw.flush();
            bw.close();
            JOptionPane.showMessageDialog(new JFrame(), "csv successfully exported", "Info", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(SQLException e){
            Logger.logInfo(e.getMessage(), 2);
            JOptionPane.showMessageDialog(new JFrame(), "csv could not be exported", "Error", JOptionPane.ERROR);
        }
    }    
    
    public void insertarCSV(List<Player> players, String user) throws IOException{
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            for(int i = 0; i<players.size(); i++){
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
                        +"','"+ user+"')";
                statement.executeUpdate(sql);
            }

        
        }catch(SQLException ex){
            Logger.logInfo(ex.getMessage(), 2);
            JOptionPane.showMessageDialog(new JFrame(), "csv could not be inserted", "Error", JOptionPane.INFORMATION_MESSAGE);

        }catch(Exception e){
            Logger.logInfo(e.getMessage(), 2);
            JOptionPane.showMessageDialog(new JFrame(), "csv could not be inserted", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void insertPlayer(Player player, String user) throws IOException {
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
                        +"','"+ user+"')";
                statement.executeUpdate(sql);
            
        }catch(SQLException e){
            Logger.logInfo(e.getMessage(), 2);
        }
    }
    
    public void editMyTeam(Player player) throws IOException{
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
    
    public void deleteMyTeam(Player player) throws IOException{
            try(Connection conexion = Conexion.getConnection()){
        Statement stmt = conexion.createStatement();
        String sql = "DELETE FROM players WHERE id=" + player.getPlayerId();
        stmt.executeUpdate(sql);
    }   catch (SQLException ex) {
            java.util.logging.Logger.getLogger(PlayersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void exportMyTeamCSV(String team) throws IOException{
        Path path = Paths.get(System.getProperty("user.dir") + "/MyTeam.csv");

        try(Connection conexion = Conexion.getConnection()){
            Statement stmt = conexion.createStatement();
            String sql = "SELECT * FROM players WHERE team LIKE '" + team + "'";
            
            BufferedWriter bw;
            
            ResultSet rs = stmt.executeQuery(sql);
            

                bw= Files.newBufferedWriter(path);
                while(rs.next()){
                    bw.write(rs.getInt("jersey")+ ",");
                    bw.write(rs.getString("name")+ ",");
                    bw.write(rs.getString("position")+ ",");
                    bw.write(rs.getString("height")+ ",");
                    bw.write(rs.getString("weight")+ ",");
                    bw.write(rs.getInt("age")+ ",");
                    bw.write(rs.getString("team")+ ",");
                    bw.write(rs.getString("college")+ ",");
                    bw.write(rs.getString("draft")+ ",");
                    bw.write(rs.getString("nationality")+ ",");
                    bw.write(rs.getDouble("points")+ ",");
                    bw.write(rs.getDouble("rebbounds")+ ",");
                    bw.write(rs.getDouble("assist")+ ",");
                    bw.write(rs.getString("image")+ "\n");
                }
                bw.flush();
                bw.close();
            

                    
        }catch(SQLException e){
            Logger.logInfo(e.getMessage(), 2);
        }
    }
   
    public void exportMyTeamXML(String team) throws IOException{
        try {
            List<Player> lista = searchPlayerByTeam(team);
            if(lista.size()==0){
                JOptionPane.showMessageDialog(new JFrame(), "This user does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            
            Element players = doc.createElement("Players");
            doc.appendChild(players);
            
            for(int i = 0; i<lista.size(); i++){
                Element playersTeam = doc.createElement("Players");
                players.appendChild(playersTeam);
                    
                Element jersey = doc.createElement("Jersey");
                jersey.setTextContent(String.valueOf(lista.get(i).getPlayerJersey()));
                playersTeam.appendChild(jersey);
                
                Element name = doc.createElement("Name");
                name.setTextContent(lista.get(i).getPlayerName());
                playersTeam.appendChild(name);  
                
                Element position = doc.createElement("Position");
                position.setTextContent(lista.get(i).getPlayerPosition());
                playersTeam.appendChild(position);
                
                Element height = doc.createElement("Height");
                height.setTextContent(lista.get(i).getPlayerHeight());
                playersTeam.appendChild(height);  
                            
                Element weight = doc.createElement("Weight");
                weight.setTextContent(lista.get(i).getPlayerWeight());
                playersTeam.appendChild(weight);
                                
                Element age = doc.createElement("Age");
                age.setTextContent(String.valueOf(lista.get(i).getPlayerAge()));
                playersTeam.appendChild(age);
                
                Element team1 = doc.createElement("Team");
                team1.setTextContent(lista.get(i).getPlayerTeam());
                playersTeam.appendChild(team1);
                
                Element college = doc.createElement("College");
                college.setTextContent(lista.get(i).getPlayerCollege());
                playersTeam.appendChild(college);
            
                Element draft = doc.createElement("Draft");
                draft.setTextContent(lista.get(i).getPlayerDraft());
                playersTeam.appendChild(draft);    
                
                Element nationality = doc.createElement("Nationality");
                nationality.setTextContent(lista.get(i).getPlayerNationality());
                playersTeam.appendChild(nationality);
                                
                Element points = doc.createElement("Points");
                points.setTextContent(String.valueOf(lista.get(i).getPlayerPoints()));
                playersTeam.appendChild(points);
                                
                Element rebbounds = doc.createElement("Rebbounds");
                rebbounds.setTextContent(String.valueOf(lista.get(i).getPlayerRebbounds()));
                playersTeam.appendChild(rebbounds);
                                
                Element assist = doc.createElement("Assist");
                assist.setTextContent(String.valueOf(lista.get(i).getPlayerAssist()));
                playersTeam.appendChild(assist);
                                
                Element image = doc.createElement("Image");
                image.setTextContent(lista.get(i).getPlayerImage());
                playersTeam.appendChild(image);
                
                TransformerFactory fac = TransformerFactory.newDefaultInstance();
                Transformer trans = fac.newTransformer();
                DOMSource som = new DOMSource(doc);
            StreamResult stream = new StreamResult(new File(System.getProperty("user.dir") + "/players " + team + ".xml"));
            trans.transform(som, stream);
            }      
        }      
        } catch (ParserConfigurationException ex) {
            java.util.logging.Logger.getLogger(PlayersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            java.util.logging.Logger.getLogger(PlayersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            java.util.logging.Logger.getLogger(PlayersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
    }
    
    
    

    
    public int ageNbaQuery() throws IOException{
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT AVG(age) FROM players";
            ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    int age = rs.getInt(1);
                    return age;
                }

        }catch(SQLException ex){
            ex.printStackTrace();
            Logger.logInfo(ex.getMessage(), 2);
        }
        return 0;
    }
    
    public int ageAgeMyTeam(String player) throws IOException{
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT AVG(age) FROM players WHERE team LIKE '" + player + "'";
            
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                int age = rs.getInt(1);
                return age;
            }
            
        }catch(SQLException ex){
            Logger.logInfo(ex.getMessage(), 2);
            ex.printStackTrace();
        }
        return 0;
    }

    public int countNbaPlayers() throws IOException{
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT COUNT(*) FROM players";
            ResultSet rs = statement.executeQuery(sql);
            
                while (rs.next()) {
                    int age = rs.getInt(1);
                    return age;
                }

        }catch(SQLException ex){
            ex.printStackTrace();
            Logger.logInfo(ex.getMessage(), 2);
        }
        return 0;    
    }
    
    public int countMyTeamPlayers(String player) throws IOException{
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT COUNT(*) FROM players WHERE team LIKE '" + player + "'";
            
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                int count = rs.getInt(1);
                return count;
            }
            
        }catch(SQLException ex){
            Logger.logInfo(ex.getMessage(), 2);
            ex.printStackTrace();
        }
        return 0;
    }
    
    
    public Map<String, Integer> piePlayers() throws IOException{
        List<Player> players = buscarPlayer();
        Map<String, Integer> jugadoresEquipo = new HashMap<>();
        
        try(Connection conexion = Conexion.getConnection()){
            Statement stmt = conexion.createStatement();
            String sql = "SELECT team, COUNT(*) as cant FROM players GROUP BY team";
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                String equipo = rs.getString("team");
                int cantidadJug = rs.getInt("cant");
                
                for(Player player : players){
                    if(equipo == player.getPlayerTeam()){
                        jugadoresEquipo.put(player.getPlayerTeam(), cantidadJug);
                    }
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return jugadoresEquipo;
    }
}

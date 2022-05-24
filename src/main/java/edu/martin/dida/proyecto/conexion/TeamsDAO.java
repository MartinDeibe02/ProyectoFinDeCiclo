/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyecto.conexion;

import POJO.Team;
import Utilidades.Logger;
import java.io.BufferedWriter;
import java.io.File;
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
public class TeamsDAO {
    
    
        
    public TeamsDAO() throws IOException{
        crearTabla();
    }

    private void crearTabla() throws IOException {
try (Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS teams" +
                            "(id INTEGER auto_increment," +
                            "name VARCHAR(255)," +
                            "abbreviation VARCHAR(255)," +
                            "city VARCHAR(255)," +
                            "conference VARCHAR(255)," +
                            "division VARCHAR(255)," +
                            "user VARCHAR(255),"
                    + "FOREIGN KEY(user) REFERENCES usuarios(user))";
            statement.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }    
    }
    
    public void saveEditTeam(Team team, String user) throws IOException{
        if(team.getId()==0){
            insertTeam(team, user);
        }else{
            editTeam(team);
        }
    }
    
    public void insertarCSV(List<Team> teams, String user) throws IOException, SQLException{
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            for(int i = 0; i<teams.size(); i++){
                String sql = "INSERT INTO teams (id, name, abbreviation, city, conference, division, user)"
                        +"VALUES ('"+ teams.get(i).getId()
                        +"','"+ teams.get(i).getName()
                        +"','"+ teams.get(i).getAbbreviation()
                        +"','"+ teams.get(i).getCity()
                        +"','"+ teams.get(i).getConference()
                        +"','"+ teams.get(i).getDivision()
                        +"','"+ user+"')";

                statement.executeUpdate(sql);
            }
        }catch(SQLException e){
            Logger.logInfo(e.getMessage(), 2);
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
                teams.add(team);
            }
        }catch(SQLException e){
            //TODO
        }
        return  teams;
    }
    
    public void exportarCSV() throws IOException{
        Path path = Paths.get(System.getProperty("user.dir") + "/teams.csv");

        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            String sql = "SELECT * FROM teams";
            
            BufferedWriter bw = Files.newBufferedWriter(path);
            
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                bw.write(rs.getInt("id") + ",");
                bw.write(rs.getString("name") + ",");
                bw.write(rs.getString("abbreviation") + ",");
                bw.write(rs.getString("city") + ",");
                bw.write(rs.getString("conference") + ",");
                bw.write(rs.getString("division") + "\n");
            }
            bw.flush();
            bw.close();
            JOptionPane.showMessageDialog(new JFrame(), "csv export succesfully", "Error", JOptionPane.INFORMATION_MESSAGE);

        }catch(SQLException e){
            JOptionPane.showMessageDialog(new JFrame(), "csv could not be exported", "Error", JOptionPane.INFORMATION_MESSAGE);

        }
    }
        
        
    public void exportarXML() throws IOException{
        try {
            List<Team> lista = buscarTeam();
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            
            Element teams = doc.createElement("Teams");
            doc.appendChild(teams);
            
            for (int i = 0; i<lista.size();i++){
                Element team = doc.createElement("Team");
                teams.appendChild(team);
                
                Element name = doc.createElement("TeamName");
                name.setTextContent(lista.get(i).getName());
                team.appendChild(name);
                
                Element abbreviation = doc.createElement("TeamAbbreviation");
                abbreviation.setTextContent(lista.get(i).getAbbreviation());
                team.appendChild(abbreviation);
                
                Element city = doc.createElement("TeamCity");
                city.setTextContent(lista.get(i).getCity());
                team.appendChild(city);
                
                Element conference = doc.createElement("TeamConference");
                conference.setTextContent(lista.get(i).getConference());
                team.appendChild(conference);
                
                Element division = doc.createElement("TeamDivision");
                division.setTextContent(lista.get(i).getDivision());
                team.appendChild(division);
            }
            
            TransformerFactory fac = TransformerFactory.newDefaultInstance();
            Transformer trans = fac.newTransformer();
            DOMSource dom = new DOMSource(doc);
            StreamResult stream = new StreamResult(new File(System.getProperty("user.dir") + "/teams.xml"));
            JOptionPane.showMessageDialog(new JFrame(), "xml exported sucesgully", "Error", JOptionPane.INFORMATION_MESSAGE);

            try {
                trans.transform(dom, stream);
            } catch (TransformerException ex) {
                Logger.logInfo(ex.toString(), 2);
            }
            } catch (TransformerConfigurationException ex) {
                Logger.logInfo(ex.toString(), 2);
            } catch (ParserConfigurationException ex) {
                Logger.logInfo(ex.toString(), 2);
        }
            
    }

    private void insertTeam(Team team, String user) throws IOException {
        try(Connection conexion = Conexion.getConnection()){
            Statement statement = conexion.createStatement();
            
                String sql = "INSERT INTO teams (name, abbreviation, city, conference, division, user)"
                        +"VALUES ('"+ team.getName()
                        +"','"+ team.getAbbreviation()
                        +"','"+ team.getCity()
                        +"','"+ team.getConference()
                        +"','"+ team.getDivision()
                        +"','"+ user+"')";
                statement.executeUpdate(sql);
            
        }catch(SQLException e){
            Logger.logInfo(e.getMessage(), 2);
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
                    + "' WHERE id= " + team.getId();
                    statement.executeUpdate(sql);

            }catch(SQLException e){
                Logger.logInfo(e.getMessage(), 2);
            }
    }
    
    public void deleteTeam(Team team) throws IOException, SQLException{
    try(Connection conexion = Conexion.getConnection()){
        Statement stmt = conexion.createStatement();
        String sql = "DELETE FROM teams WHERE id=" + team.getId();
        stmt.executeUpdate(sql);
    }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.table;

import POJO.Team;
import edu.martin.dida.proyecto.conexion.TeamsDAO;
import edu.martin.dida.proyectofinciclo.App;
import edu.martin.dida.proyectofinciclo.ControladorLogin.ControladorLogin;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 *
 * @author Markyuu
 */
public class ControladorTabPane  implements Initializable{
    static ArrayList<Team> listTeams = new ArrayList<>();
    
    // <editor-fold defaultstate="collapsed" desc="Chat">

    @FXML
    TextArea recibidos;

    @FXML
    private TextField txtChatSend;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Player">
    @FXML
    private Rectangle prueba;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Team">
    @FXML
    private TextField txtTeamAbbreviation;

    @FXML
    private TextField txtTeamCity;

    @FXML
    private TextField txtTeamConference;

    @FXML
    private TextField txtTeamDivision;

    @FXML
    private TextField txtTeamName;
    
    @FXML
    private TableView tablaTeams;
    
    private int id;
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Player">

    @FXML
    private Label lblAge;

    @FXML
    private Label lblPosition;
        
    @FXML
    private Label lblAssists;

    @FXML
    private Label lblCountry;

    @FXML
    private Label lblDraft;

    @FXML
    private Label lblCollege;
        
    @FXML
    private Label lblHeight;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPoints;

    @FXML
    private Label lblRebounds;

    @FXML
    private Label lblTeam;

    @FXML
    private Label lblWeight;
    // </editor-fold>

    String texto;
    
    ControladorLogin controlLogin = new ControladorLogin();
    TeamsDAO teamsDAO;
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
    static LocalDateTime now = LocalDateTime.now();  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            teamsDAO = new TeamsDAO();
            cargarTeams();
        } catch (IOException ex) {
            Logger.getLogger(ControladorTabPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Mandar mensaje chat">
    public void append(String text){
        recibidos.appendText(text + "\n");
    }
    
    public void send(){
        texto =dtf.format(now) + " " + controlLogin.nombre + " >> " + txtChatSend.getText();

        try {
            DatagramPacket paquete = new DatagramPacket(texto.getBytes(), texto.length(), App.grupo, App.port);
            App.ms.send(paquete);
        } catch (IOException ex) {
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Funciones archivos equipos">

    
    public void insertCSV() {
        BufferedReader br = null;
        try {
            FileChooser f = new FileChooser();
            f.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV FILES", "*.csv"));
            File file = f.showOpenDialog(null);
            
            if(file == null){
                
            }else{

            Path path = Paths.get(file.getAbsolutePath());
            br = Files.newBufferedReader(path);
            Stream<String> lineas = br.lines();
            lineas.forEach(insert->{
                String[] lineaSep = insert.split(",");
                listTeams.add(new Team(Integer.parseInt(lineaSep[0]),lineaSep[1], lineaSep[2], lineaSep[3], lineaSep[4], lineaSep[5]));
                        });
            teamsDAO.insertarCSV(listTeams);
            cargarTeams();
            br.close();

            }
        } catch (IOException ex) {
            Logger.getLogger(ControladorTabPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorTabPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void exportCSV() throws IOException{
        teamsDAO.exportarCSV();
    }
    
    public void exportXML() throws IOException, ParserConfigurationException, TransformerException{
        teamsDAO.exportarXML();
    }
    
        // </editor-fold>


    public void insertTeam() throws IOException{
        Team team = new Team();
        
        if(txtTeamName.getText().isEmpty() || txtTeamAbbreviation.getText().isEmpty() || txtTeamCity.getText().isEmpty() ||
                txtTeamConference.getText().isEmpty() || txtTeamDivision.getText().isEmpty()){
            JOptionPane.showMessageDialog(new JFrame(), "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
        }else {
            team.setId(id);
            team.setName(txtTeamName.getText());
            team.setCity(txtTeamCity.getText());
            team.setAbbreviation(txtTeamAbbreviation.getText());
            team.setConference(txtTeamConference.getText());
            team.setDivision(txtTeamDivision.getText());
            
            teamsDAO.saveEditTeam(team);
            cargarTeams();
            id=0;
            clearTeamFields();
            Utilidades.Logger.logInfo("Equipo insertado " + team.toString(), 1);
        }
    }
    
    public void editTeam(){
        Team team = (Team) tablaTeams.getSelectionModel().getSelectedItem();
        
        if(!(team == null)){
            txtTeamName.setText(team.getName());
            txtTeamAbbreviation.setText(team.getAbbreviation());
            txtTeamCity.setText(team.getCity());
            txtTeamConference.setText(team.getConference());
            txtTeamDivision.setText(team.getDivision());
            
            id= team.getId();
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "You must select a team", "Error", JOptionPane.ERROR_MESSAGE);        
        }
    }
    
    public void deleteTeam() throws IOException, SQLException{
        Team team = (Team) tablaTeams.getSelectionModel().getSelectedItem();
        
        if(team == null){
            JOptionPane.showMessageDialog(new JFrame(), "You must select a team", "Error", JOptionPane.ERROR_MESSAGE);        
        }else{
            teamsDAO.deleteTeam(team);
            cargarTeams();
            clearTeamFields();
            Utilidades.Logger.logInfo("Deleted => " + team.toString() , 1);
        }
    }

    public void cargarTeams() throws IOException {
        ObservableList<Team> teams = FXCollections.observableArrayList();
        List<Team> teams2 = teamsDAO.buscarTeam();
        teams.addAll(teams2);
        tablaTeams.setItems(teams);
    }
    
    //Bordes de imagen + shadow
    private void setimg(Rectangle rec1, Image img1) {
        rec1.setArcWidth(30.0);   // Corner radius
        rec1.setArcHeight(30.0);

        ImagePattern pattern = new ImagePattern(img1);

        rec1.setFill(pattern);
        rec1.setEffect(new DropShadow(20, Color.BLACK));    
    }
    
    public void photo(){
        Image img = new Image("https://bolavip.com/__export/1646958112918/sites/bolavip/img/2022/03/10/lebron_james_nba_la_lakers.jpg_1890075089.jpg");
        setimg(prueba, img);
        
        lblName.setText("6 / Lebron James");
        lblPosition.setText("F");
        lblHeight.setText("6.8");
        lblWeight.setText("250");
        lblTeam.setText("Los Angeles Lakers");
        lblAge.setText("37");
        lblDraft.setText("2003 Rnd 1 Pick 1");
        lblPoints.setText("30.3");
        lblRebounds.setText("8.2");
        lblAssists.setText("6.2");
        lblCollege.setText("St. Vincent St. Mary High School (Ohio)");
        lblCountry.setText("United States");
 
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="pantallas">

    public void goHome(ActionEvent event){
        Scene scene = App.pantallas.get("inicio");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene.setFill(Color.TRANSPARENT);        
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.centerOnScreen();

        stage.show();
    }
    
    public void goManager(ActionEvent event){
        Scene scene = App.pantallas.get("manager");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene.setFill(Color.TRANSPARENT);        
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.centerOnScreen();

        stage.show();
    }
    
    public void exit(){
        System.exit(0);
    }
    
    // </editor-fold>

    private void clearTeamFields() {
        txtTeamName.clear();
        txtTeamCity.clear();
        txtTeamAbbreviation.clear();
        txtTeamConference.clear();
        txtTeamDivision.clear();
    }





}

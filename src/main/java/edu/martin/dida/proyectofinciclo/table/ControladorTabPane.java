/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.table;

import POJO.Player;
import POJO.Team;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import edu.martin.dida.proyecto.conexion.Conexion;
import edu.martin.dida.proyecto.conexion.PlayersDAO;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
    static ArrayList<Player> listPlayer = new ArrayList<>();
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
    
    private int idTeam;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Player">
    
    @FXML
    private TableView tablePlayers;
    
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
    
    @FXML
    private TextField txtfilter;
    
    ObservableList<Player> players;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="MyTeam">

    @FXML
    private TextField txtMyTeamAge;

    @FXML 
    private TextField txtMyTeamImage;
    
    @FXML
    private TextField txtMyTeamAssist;

    @FXML
    private TextField txtMyTeamCollege;

    @FXML
    private TextField txtMyTeamCountry;

    @FXML
    private TextField txtMyTeamDraft;

    @FXML
    private TextField txtMyTeamHeight;

    @FXML
    private TextField txtMyTeamJersey;

    @FXML
    private TextField txtMyTeamName;

    @FXML
    private TextField txtMyTeamPoints;

    @FXML
    private TextField txtMyTeamPosition;

    @FXML
    private TextField txtMyTeamRebbounds;

    @FXML
    private TextField txtMyTeamWeight;
    
    @FXML
    public ComboBox comboTeam;
            
    @FXML
    private Slider sliderAge;
    
    @FXML
    private TableView tableMyTeam;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="DashBoard">
    @FXML
    public ComboBox combodash;
    
    @FXML
    private Label lblagemyteam;

    @FXML
    private Label lblagenba;

    @FXML
    private Label lblnumbermyteam;

    @FXML
    private Label lblnumbernba;
    
    @FXML
    private PieChart pieDash;
    // </editor-fold>
    
    
    
    private int idPlayer;
    
    ObservableList<String> items = FXCollections.observableArrayList();
    ObservableList<Player> playerByTeam;
    
    String texto;
    
    ControladorLogin controlLogin = new ControladorLogin();
    TeamsDAO teamsDAO;
    PlayersDAO playerDAO;
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
    static LocalDateTime now = LocalDateTime.now();  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            teamsDAO = new TeamsDAO();
            playerDAO = new PlayersDAO();
            cargarTeams();
            cargarPlayer();
            loadFilter();
            setLblNbaAge();
            setLblNbaCount();
            showPie();
            
            prueba.setFill(Color.TRANSPARENT);
            prueba.setArcWidth(60.0);   // Corner radius
            prueba.setArcHeight(60.0);       
        
            sliderAge.valueProperty().addListener(new ChangeListener<Number>(){
                @Override
                public void changed(ObservableValue<? extends Number> observar, Number numeroAnterior, Number numeroNuevo) {
                    
                    txtMyTeamAge.setText(String.valueOf((int)Math.round((double) numeroNuevo)));
            }
            });
            
            txtMyTeamAge.textProperty().addListener((observable, oldvalue, newvalue) -> {
                try{
                    sliderAge.setValue(Integer.parseInt(txtMyTeamAge.getText()));
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(new JFrame(), "You cant put letters", "Error", JOptionPane.ERROR_MESSAGE);
                    txtMyTeamAge.clear();
                }
            });
            
        } catch (IOException ex) {
            try {
                Utilidades.Logger.logInfo(ex.toString(), 2);
            } catch (IOException ex1) {
                Logger.getLogger(ControladorTabPane.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }catch(Exception e){
            try {
                Utilidades.Logger.logInfo(e.toString(), 2);
            } catch (IOException ex) {
                Logger.getLogger(ControladorTabPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="Mandar mensaje chat">
    public void append(String text){
        recibidos.appendText(text + "\n");
    }
    
    public void send() throws IOException{
        texto =dtf.format(now) + " " + ControladorLogin.nombre + " >> " + txtChatSend.getText();

        try {
            DatagramPacket paquete = new DatagramPacket(texto.getBytes(), texto.length(), App.grupo, App.port);
            App.ms.send(paquete);
        } catch (Exception ex) {
            Utilidades.Logger.logInfo(ex.toString(), 2);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Funciones archivos equipos">

    
    public void insertCSV() throws IOException  {
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
            teamsDAO.insertarCSV(listTeams, ControladorLogin.nombre);
            cargarTeams();
            
            comboTeam.setItems(chargeTeamsCombo(ControladorLogin.nombre));
            
           
            
            br.close();
            JOptionPane.showMessageDialog(new JFrame(), "csv inserted successfully", "Info", JOptionPane.INFORMATION_MESSAGE);

            }

        } catch (IOException ex) {
            Utilidades.Logger.logInfo(ex.toString(), 2);
            JOptionPane.showMessageDialog(new JFrame(), "csv cannot not be inserted", "Error", JOptionPane.INFORMATION_MESSAGE);
        }        
        catch (Exception ex) {
            Utilidades.Logger.logInfo(ex.toString(), 2);
            JOptionPane.showMessageDialog(new JFrame(), "Invalid CSV format", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void exportCSV() throws IOException{
        teamsDAO.exportarCSV();
    }
    
    public void exportXML() throws IOException, ParserConfigurationException, TransformerException{
        teamsDAO.exportarXML();
    }
    
        // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Team">

    
    public void insertTeam() throws IOException{
        Team team = new Team();
        
        if(txtTeamName.getText().isEmpty() || txtTeamAbbreviation.getText().isEmpty() || txtTeamCity.getText().isEmpty() ||
                txtTeamConference.getText().isEmpty() || txtTeamDivision.getText().isEmpty()){
            JOptionPane.showMessageDialog(new JFrame(), "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
        }else {
            team.setId(idTeam);
            team.setName(txtTeamName.getText());
            team.setCity(txtTeamCity.getText());
            team.setAbbreviation(txtTeamAbbreviation.getText());
            team.setConference(txtTeamConference.getText());
            team.setDivision(txtTeamDivision.getText());
            
            teamsDAO.saveEditTeam(team, ControladorLogin.nombre);
            cargarTeams();
            comboTeam.setItems(chargeTeamsCombo(ControladorLogin.nombre));
            idTeam=0;
            loadFilter();
            cargarPlayer();
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
            
            idTeam= team.getId();
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "You must select a team", "Error", JOptionPane.ERROR_MESSAGE);        
        }
    }
    
    public void deleteTeam() throws IOException, SQLException{
        Team team = (Team) tablaTeams.getSelectionModel().getSelectedItem();
        
        if(team == null){
            JOptionPane.showMessageDialog(new JFrame(), "You must select a team", "Error", JOptionPane.ERROR_MESSAGE);        
        }else if (team.getName().equals("Agente Libre")){
            
            JOptionPane.showMessageDialog(new JFrame(), "You cant delete this team", "Error", JOptionPane.ERROR_MESSAGE);        
        
        }else{
            teamsDAO.deleteTeam(team);
            cargarTeams();
            clearTeamFields();
            
            cargarPlayer();
            loadFilter();

            comboTeam.setItems(chargeTeamsCombo(ControladorLogin.nombre));
            Utilidades.Logger.logInfo("Deleted => " + team.toString() , 1);
        }
    }

    public void cargarTeams() throws IOException {
        ObservableList<Team> teams = FXCollections.observableArrayList();
        List<Team> teams2 = teamsDAO.buscarTeam();
        teams.addAll(teams2);
        tablaTeams.setItems(teams);
    }
    
            // </editor-fold>

    
    
    
    public void refreshTeams() throws IOException{
        cargarTeams();
    }
    
    public void refreshPlayers() throws IOException{
        cargarPlayer();
    }
    
    public void cargarPlayer() throws IOException{
        players = FXCollections.observableArrayList();
        List<Player> player = playerDAO.buscarPlayer();
        players.addAll(player);
        tablePlayers.setItems(players);        
    }

    // <editor-fold defaultstate="collapsed" desc="Myteam">

    public void photo(){
        
        Player player = (Player) tablePlayers.getSelectionModel().getSelectedItem();
        
        if(player ==null){
            JOptionPane.showMessageDialog(new JFrame(), "You must select a player", "Error", JOptionPane.ERROR_MESSAGE);        
        }else{
            Image img = new Image(player.getPlayerImage());
            setimg(prueba, img);
            
        lblName.setText(player.getPlayerJersey() + " / " + player.getPlayerName());
        lblPosition.setText(player.getPlayerPosition());
        lblHeight.setText(player.getPlayerHeight());
        lblWeight.setText(player.getPlayerWeight());
        lblTeam.setText(player.getPlayerTeam());
        lblAge.setText(String.valueOf(player.getPlayerAge()));
        lblDraft.setText(player.getPlayerDraft());
        lblPoints.setText(String.valueOf(player.getPlayerPoints()));
        lblRebounds.setText(String.valueOf(player.getPlayerRebbounds()));
        lblAssists.setText(String.valueOf(player.getPlayerAssist()));
        lblCollege.setText(player.getPlayerCollege());
        lblCountry.setText(player.getPlayerNationality());
        }

    }
    
    public void insertCSVPlayer() throws IOException{
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
                listPlayer.add(new Player(Integer.parseInt(lineaSep[0]),lineaSep[1], lineaSep[2], lineaSep[3], lineaSep[4], Integer.parseInt(lineaSep[5]),
                lineaSep[6], lineaSep[7], lineaSep[8], lineaSep[9], Double.parseDouble(lineaSep[10]), 
                Double.parseDouble(lineaSep[11]),Double.parseDouble(lineaSep[12]), lineaSep[13], lineaSep[14]));
                        });
            playerDAO.insertarCSV(listPlayer);
            cargarPlayer();
            br.close();
            Utilidades.Logger.logInfo("CSV insertado", 1);

            JOptionPane.showMessageDialog(new JFrame(), "csv successfully inserted", "Info", JOptionPane.INFORMATION_MESSAGE);


            }
        } catch (IOException ex) {
            Utilidades.Logger.logInfo(ex.getMessage(), 2);
            JOptionPane.showMessageDialog(new JFrame(), "csv cannot be inserted", "Error", JOptionPane.INFORMATION_MESSAGE);

        }catch(Exception e){
            JOptionPane.showMessageDialog(new JFrame(), "csv cannot be inserted", "Error", JOptionPane.INFORMATION_MESSAGE);
            Utilidades.Logger.logInfo(e.toString(), 2);
        }
    }
    
    public void loadFilter(){
        FilteredList<Player> filterPlayer = new FilteredList<>(players, e -> true);
        txtfilter.setOnKeyReleased(e -> {
            txtfilter.textProperty().addListener((value, oldvalue, newvalue) -> {
                filterPlayer.setPredicate((Predicate<? super Player>) player ->{
                    if(newvalue == null || newvalue.isEmpty()){
                        return true;
                    }else if(player.getPlayerName().toLowerCase().contains(newvalue.toLowerCase())){
                        return true;
                    }else if(player.getPlayerTeam().toLowerCase().contains(newvalue.toLowerCase())){
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Player> sortedList = new SortedList<>(filterPlayer);
            sortedList.comparatorProperty().bind(tablePlayers.comparatorProperty());
            tablePlayers.setItems(sortedList);
        });    
    }

    public void exportarCSVPlayers() throws IOException{
        playerDAO.exportarCSV();
    }
        
    public void showMyTeam() throws IOException{
        playerByTeam = FXCollections.observableArrayList();
        List<Player> player1 = playerDAO.searchPlayerByTeam((String) comboTeam.getSelectionModel().getSelectedItem());
        playerByTeam.addAll(player1);
        tableMyTeam.setItems(playerByTeam);
        
    }
    
    public void insertMyTeam() throws IOException{
        Player player = new Player();
        
        if(txtMyTeamJersey.getText().isEmpty() || txtMyTeamName.getText().isEmpty() || txtMyTeamPosition.getText().isEmpty() || txtMyTeamWeight.getText().isEmpty()
                || txtMyTeamHeight.getText().isEmpty() || txtMyTeamAge.getText().isEmpty()|| txtMyTeamDraft.getText().isEmpty() || txtMyTeamCollege.getText().isEmpty()
                || txtMyTeamCountry.getText().isEmpty() || txtMyTeamPoints.getText().isEmpty() || txtMyTeamRebbounds.getText().isEmpty() || txtMyTeamImage.getText().isEmpty() || txtMyTeamAssist.getText().isEmpty()){
            JOptionPane.showMessageDialog(new JFrame(), "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            
          }  else{
            
                if(txtMyTeamImage.getText().indexOf("cloudinary") != -1 || txtMyTeamImage.getText().indexOf("cdn.nba") != -1){
                    player.setPlayerJersey(Integer.parseInt(txtMyTeamJersey.getText()));
                    player.setPlayerName(txtMyTeamName.getText());
                    player.setPlayerPosition(txtMyTeamPosition.getText());
                    player.setPlayerWeight(txtMyTeamWeight.getText() +"kg");
                    player.setPlayerHeight(txtMyTeamHeight.getText() + "cm");
                    player.setPlayerTeam((String) comboTeam.getSelectionModel().getSelectedItem());
                    player.setPlayerAge(Integer.parseInt(txtMyTeamAge.getText()));
                    player.setPlayerDraft(txtMyTeamDraft.getText());
                    player.setPlayerCollege(txtMyTeamCollege.getText());
                    player.setPlayerNationality(txtMyTeamCountry.getText());
                    player.setPlayerPoints(Double.parseDouble(txtMyTeamPoints.getText()));
                    player.setPlayerRebbounds(Double.parseDouble(txtMyTeamRebbounds.getText()));
                    player.setPlayerAssist(Double.parseDouble(txtMyTeamAssist.getText()));
                    player.setPlayerImage(txtMyTeamImage.getText());  
                    player.setPlayerId(idPlayer);
                }else{
                    player.setPlayerJersey(Integer.parseInt(txtMyTeamJersey.getText()));
                    player.setPlayerName(txtMyTeamName.getText());
                    player.setPlayerPosition(txtMyTeamPosition.getText());
                    player.setPlayerWeight(txtMyTeamWeight.getText() +"kg");
                    player.setPlayerHeight(txtMyTeamHeight.getText() + "cm");
                    player.setPlayerTeam((String) comboTeam.getSelectionModel().getSelectedItem());
                    player.setPlayerAge(Integer.parseInt(txtMyTeamAge.getText()));
                    player.setPlayerDraft(txtMyTeamDraft.getText());
                    player.setPlayerCollege(txtMyTeamCollege.getText());
                    player.setPlayerNationality(txtMyTeamCountry.getText());
                    player.setPlayerPoints(Double.parseDouble(txtMyTeamPoints.getText()));
                    player.setPlayerRebbounds(Double.parseDouble(txtMyTeamRebbounds.getText()));
                    player.setPlayerAssist(Double.parseDouble(txtMyTeamAssist.getText()));
                    player.setPlayerImage(dinary(txtMyTeamImage.getText()));    
                    player.setPlayerId(idPlayer);                
                }

        }
        playerDAO.saveEditTeam(player, ControladorLogin.nombre);        
        JOptionPane.showMessageDialog(new JFrame(), "Player succesfully inserted", "Error", JOptionPane.INFORMATION_MESSAGE);
        cargarPlayer();
        loadFilter();
        showMyTeam();
        clearMyTeamFields();
        idPlayer=0;
        Utilidades.Logger.logInfo("Inserted => " + player.toString() , 1);

  


    }
    
    public void editMyTeam(){
         Player player = (Player) tableMyTeam.getSelectionModel().getSelectedItem();
        
        if(!(player == null)){
            comboTeam.setValue(player.getPlayerTeam());
            
            txtMyTeamJersey.setText(String.valueOf(player.getPlayerJersey()));
            txtMyTeamName.setText(player.getPlayerName());
            txtMyTeamPosition.setText(player.getPlayerPosition());
            txtMyTeamWeight.setText(player.getPlayerWeight());
            txtMyTeamHeight.setText(player.getPlayerHeight());
            txtMyTeamAge.setText(String.valueOf(player.getPlayerAge()));
            txtMyTeamDraft.setText(player.getPlayerDraft());
            txtMyTeamCollege.setText(player.getPlayerCollege());
            txtMyTeamCountry.setText(player.getPlayerNationality());
            txtMyTeamPoints.setText(String.valueOf(player.getPlayerPoints()));
            txtMyTeamRebbounds.setText(String.valueOf(player.getPlayerRebbounds()));
            txtMyTeamAssist.setText(String.valueOf(player.getPlayerAssist()));
            txtMyTeamImage.setText(player.getPlayerImage());

            
            idPlayer= player.getPlayerId();
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "You must select a player", "Error", JOptionPane.ERROR_MESSAGE);        
        }
    }
    
    public void deleteMyTeam() throws IOException{
        Player player = (Player) tableMyTeam.getSelectionModel().getSelectedItem();
        
        if(player == null){
            JOptionPane.showMessageDialog(new JFrame(), "You must select a player", "Error", JOptionPane.ERROR_MESSAGE);        
        }else{
            playerDAO.deleteMyTeam(player);
            cargarPlayer();
            showMyTeam();
            loadFilter();
            Utilidades.Logger.logInfo("Deleted => " + player.toString() , 1);
        }
    }
    
    public void getImageMyTeam(){
            FileChooser f = new FileChooser();
            f.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG FILES", "*.png"));
            File file = f.showOpenDialog(null);
            if(!(file == null)){
                txtMyTeamImage.setText(file.getAbsolutePath());
            }
    }
    
    public ObservableList<String> chargeTeamsCombo(String user) throws IOException{
        try(Connection conexion = Conexion.getConnection()){
            String sql = "SELECT DISTINCT name FROM teams WHERE user LIKE '" + user + "'";
            PreparedStatement prepstmt = conexion.prepareStatement(sql);
            items.removeAll(items);
            ResultSet rs = prepstmt.executeQuery();
            while(rs.next()){
                items.add(rs.getString("name"));
            }
            return items;
        }catch(SQLException e){
            Utilidades.Logger.logInfo(e.getMessage() , 2);
            return null;

        }
    }
    
    public boolean urlValid(String url){
        try{
            new URL(url).toURI();
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public void exportMyTeamCSV() throws IOException{
        if(!(comboTeam.getSelectionModel().getSelectedItem() == null)){
            
            playerDAO.exportMyTeamCSV((String) comboTeam.getSelectionModel().getSelectedItem());
            JOptionPane.showMessageDialog(new JFrame(), "CSV exported succesfully", "Info", JOptionPane.INFORMATION_MESSAGE);
            Utilidades.Logger.logInfo("CSV exported", 1);

        }else{
            JOptionPane.showMessageDialog(new JFrame(), "You must select a team", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void exportMyTeamXML() throws IOException{
        if(!(comboTeam.getSelectionModel().getSelectedItem() == null)){
        playerDAO.exportMyTeamXML((String) comboTeam.getSelectionModel().getSelectedItem());
            JOptionPane.showMessageDialog(new JFrame(), "XML exported succesfully", "Info", JOptionPane.INFORMATION_MESSAGE);
            Utilidades.Logger.logInfo("XML exported", 1);        
        
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "You must select a team", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    
    
    // </editor-fold>
    
    public void setLblNbaAge() throws IOException{
        lblagenba.setText(String.valueOf(playerDAO.ageNbaQuery()));
    }    
    
    public void queryDash() throws IOException{
        lblagemyteam.setText(String.valueOf(playerDAO.ageAgeMyTeam((String) combodash.getSelectionModel().getSelectedItem())));
        lblnumbermyteam.setText(String.valueOf(playerDAO.countMyTeamPlayers((String) combodash.getSelectionModel().getSelectedItem())));
    }
    

    public void setLblNbaCount() throws IOException{
        lblnumbernba.setText(String.valueOf(playerDAO.countNbaPlayers()));
    }       
    
    
    public void showPie() throws IOException{
        Map<String, Integer> jugadoresEquipo = playerDAO.piePlayers();
        
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList();
        
        jugadoresEquipo.forEach((nombrejugador,cantidad) ->{
            PieChart.Data data = new PieChart.Data(nombrejugador,cantidad);
            datos.add(data);
        });
        pieDash.setData(datos);
        
        pieDash.setStyle("-fx-text-fill: white;");

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

    
    
    
    
    
    
    
    
    
    
    
    
    
    public void clearTeamFields() {
        txtTeamName.clear();
        txtTeamCity.clear();
        txtTeamAbbreviation.clear();
        txtTeamConference.clear();
        txtTeamDivision.clear();
        idTeam = 0;
    }

    public void clearMyTeamFields(){
        txtMyTeamJersey.clear();
        txtMyTeamName.clear();
        txtMyTeamPosition.clear();
        txtMyTeamWeight.clear();
        txtMyTeamHeight.clear();
        txtMyTeamAge.clear();
        txtMyTeamCollege.clear();
        txtMyTeamCountry.clear();
        txtMyTeamImage.clear();
        txtMyTeamPoints.clear();
        txtMyTeamDraft.clear();
        txtMyTeamRebbounds.clear();
        txtMyTeamAssist.clear();
        comboTeam.setValue(null);
        idPlayer = 0;
    }
    
    private void setimg(Rectangle rec1, Image img1) {
        rec1.setArcWidth(60.0);   // Corner radius
        rec1.setArcHeight(60.0);

        ImagePattern pattern = new ImagePattern(img1);

        rec1.setFill(pattern);
        rec1.setEffect(new DropShadow(20, Color.BLACK));    
    }

    private String dinary(String url) throws IOException{
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
        "cloud_name", "daolhlyb6",
        "api_key", "853471969531956",
        "api_secret", "yQIl1DHYJbh0Gm3td56uD7d66ts",
        "secure", true));
        
        File file = new File(url);
        
        Map uploadresult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        String a = (String) uploadresult.get("url");
        return a;
    }
    



}

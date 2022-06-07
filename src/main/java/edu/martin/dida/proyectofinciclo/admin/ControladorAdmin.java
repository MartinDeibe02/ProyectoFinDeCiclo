/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.martin.dida.proyectofinciclo.admin;

import POJO.Player;
import POJO.Team;
import POJO.Usuario;
import Utilidades.SendMessage;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.martin.dida.proyecto.conexion.Conexion;
import edu.martin.dida.proyecto.conexion.PlayersDAO;
import edu.martin.dida.proyecto.conexion.UsuariosDAO;
import edu.martin.dida.proyectofinciclo.App;
import edu.martin.dida.proyectofinciclo.ControladorLogin.ControladorLogin;
import edu.martin.dida.proyectofinciclo.ControladorLogin.ControladorRegistro;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Markyuu
 */
public class ControladorAdmin implements Initializable{
    
    
    
    
    @FXML
    private Button btnMinimize;
    
    // <editor-fold defaultstate="collapsed" desc="User">

    
    @FXML
    private TableView tableuser;
    
    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtFilterUser;
    
    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUser;
    
    @FXML
    private CheckBox checkAdmin;

    @FXML
    private Text lblCountUser;

    @FXML
    private TableView tableadmin;    
    boolean decryptBool = true;
    @FXML
    Button btnDecrypt;
    
                // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Player">

    @FXML
    TextField txtJersey;  
    
    @FXML
    TextField txtName; 
    
    @FXML
    TextField txtPosition; 
    
    @FXML
    TextField txtWeight; 
    
    @FXML
    TextField txtHeight;  
    
    @FXML
    Slider slider;
    
    @FXML
    TextField txtAge; 
    
    @FXML
    TextField txtDraft; 
    
    @FXML
    TextField txtCollege;   
    
    @FXML
    TextField txtNationality;    
    
    @FXML
    TableView TablePlayerUser;
    
    @FXML
    TextField txtImage;
        
    @FXML
    TextField txtPoints;
        
    @FXML
    TextField txtAssist;
        
    @FXML
    TextField txtRebbounds;
        
    @FXML
    public ComboBox comboTeam;
                // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Team">

    @FXML
    TextField txtNameTeam;
    
    @FXML
    TextField txtTeamAbbreviation;
            
    @FXML
    TextField txtTeamCity;
                
    @FXML
    TextField txtConference;
    
    @FXML
    TextField txtDivision;
    
    @FXML
    TableView tableTeamsUser;
    
    @FXML
    public ComboBox comboUser;
    
    // </editor-fold>

    @FXML
    TextArea txtlog;
    
    
    int id;
    int idplayer;
    int idTeam;
    PlayersDAO playerDao;
    UsuariosDAO teamDao;
    ObservableList<String> items = FXCollections.observableArrayList();
    ObservableList<String> itemsUser = FXCollections.observableArrayList();
    ObservableList<Usuario> users;
    ObservableList<Usuario> admin;    
    public void close() throws IOException{
        UsuariosDAO.updateStatusOffline(ControladorLogin.nombre);
        System.exit(0);
    }
    
    public void minimize(){
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="UserTab">

    
    public void cargarUser() throws IOException {
        users = FXCollections.observableArrayList();
        List<Usuario> user = ControladorRegistro.userDAO.buscarUser();
        users.addAll(user);
        tableuser.setItems(users);
    }
        
    public void cargarAdmin() throws IOException {
        admin = FXCollections.observableArrayList();
        List<Usuario> user = ControladorRegistro.userDAO.buscarAdmin();
        admin.addAll(user);
        tableadmin.setItems(admin);
    }
    
    public void editUser() throws IOException{
        Usuario user = (Usuario) tableuser.getSelectionModel().getSelectedItem();
        Usuario admin = (Usuario) tableadmin.getSelectionModel().getSelectedItem();
            

        if(!(user == null)){
            if(!(UsuariosDAO.getStatus(user.getUser()) == true)){
                btnDecrypt.setDisable(false);
                txtUser.setText(user.getUser());
                txtEmail.setText(user.getEmail());
                txtPassword.setText(encrypt(user.getPassword()));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String date = user.getDateBirth();
                LocalDate localDate = LocalDate.parse(date, formatter);
                txtDate.setValue(localDate);

                if(user.getPermisos().equals("user")){
                    checkAdmin.setSelected(false);
                }
                
                

                id = user.getId();
            }else{
            JOptionPane.showMessageDialog(new JFrame(), "This user is online", "Error", JOptionPane.ERROR_MESSAGE);        
            }

        
         }else if(user==null && admin!=null){
             if(admin.getUser().equals("ADMIN")){
                    JOptionPane.showMessageDialog(new JFrame(), "You cant edit this user", "Error", JOptionPane.ERROR_MESSAGE);        
             }else{
                 
             
                btnDecrypt.setDisable(false);

                if(!(UsuariosDAO.getStatus(admin.getUser()) == true)){
                    txtUser.setText(admin.getUser());
                txtEmail.setText(admin.getEmail());
                txtPassword.setText(encrypt(admin.getPassword()));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String date = admin.getDateBirth();
                LocalDate localDate = LocalDate.parse(date, formatter);
                txtDate.setValue(localDate);


                if(admin.getPermisos().equals("admin")){
                    checkAdmin.setSelected(true);
                }
                id = admin.getId();
                }else{
                    JOptionPane.showMessageDialog(new JFrame(), "This user is online", "Error", JOptionPane.ERROR_MESSAGE);        

                }

            
            
             }    
            
        }else if (admin == null && user == null){
            JOptionPane.showMessageDialog(new JFrame(), "You must select a user", "Error", JOptionPane.ERROR_MESSAGE);        
        }
         
        decryptBool = true;
      }
    
    public void insertUser() throws IOException{
        Usuario user = new Usuario();
        
        if(!(txtUser.getText().isEmpty() || txtEmail.getText().isEmpty() || txtDate.getValue() == null || txtPassword.getText().isEmpty())){
            if(checkmail(txtEmail.getText())){
                user.setId(id);
                user.setUser(txtUser.getText());
                user.setEmail(txtEmail.getText());

                LocalDate date = txtDate.getValue() ;
                String convertirFecha = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                user.setDateBirth(convertirFecha); 

                user.setPassword(txtPassword.getText());

                if(checkAdmin.isSelected()){
                    user.setPermisos("admin");
                }else{
                    user.setPermisos("user");
                }


                ControladorRegistro.userDAO.saveEditUser(user);
                numberUser();
                cargarAdmin();
                cargarUser();
                cargarUser();
                cargarTeam();
                clearFieldsUser();
                comboUser.setItems(chargeTeamsComboUser());
                btnDecrypt.setDisable(false);
                id=0;
            }else{
                JOptionPane.showMessageDialog(new JFrame(), "Email not valid", "Error", JOptionPane.ERROR_MESSAGE);        
            }
            
        
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "All fields must be covered", "Error", JOptionPane.ERROR_MESSAGE);        
        }
        
    }

    public void deleteUser() throws IOException{
        Usuario user = (Usuario) tableuser.getSelectionModel().getSelectedItem();
        Usuario admin = (Usuario) tableadmin.getSelectionModel().getSelectedItem();

        if(!(user == null)){
            
            if(!(UsuariosDAO.getStatus(user.getUser()) == true)){
                
                if(ControladorRegistro.userDAO.getUserTeams(user.getUser())==true){
                    JOptionPane.showMessageDialog(new JFrame(), "You should delete the user teams, Try again", "Error", JOptionPane.ERROR_MESSAGE);        
                }else{
                    ControladorRegistro.userDAO.deleteUser(user);
                    cargarUser();
                    cargarPlayers();
                    numberUser();
                    cargarUser();
                    cargarTeam();
                    comboUser.setItems(chargeTeamsComboUser());
                }
                
            }else{
            JOptionPane.showMessageDialog(new JFrame(), "This user is online", "Error", JOptionPane.ERROR_MESSAGE);        
            }
        }else if(user==null && admin!=null){
            if(!(UsuariosDAO.getStatus(admin.getUser()) == true)){
                
                if(admin.getUser().equals("ADMIN")){
                    JOptionPane.showMessageDialog(new JFrame(), "You cant delete this user", "Error", JOptionPane.ERROR_MESSAGE);        
                }else{
                    
                
                if(ControladorRegistro.userDAO.getUserTeams(admin.getUser())==true){
                    JOptionPane.showMessageDialog(new JFrame(), "You should delete the user teams, Try again", "Error", JOptionPane.ERROR_MESSAGE);        
                }else{
                
                ControladorRegistro.userDAO.deleteUser(admin);
                cargarAdmin();
                cargarPlayers();
                numberUser();
                cargarUser();
                cargarTeam();
                comboUser.setItems(chargeTeamsComboUser());
                }
                }
            }else{
                
                JOptionPane.showMessageDialog(new JFrame(), "This user is online", "Error", JOptionPane.ERROR_MESSAGE);        
            }

            
        }else if(admin == null && user == null){
            JOptionPane.showMessageDialog(new JFrame(), "You must select a user", "Error", JOptionPane.ERROR_MESSAGE);        
        }

    }
    
    public void numberUser() throws IOException{
        lblCountUser.setText(String.valueOf(ControladorRegistro.userDAO.countUsers()));
    }
    
    public void getJson() throws IOException{
        Path path = Paths.get(System.getProperty("user.dir") + "/TheBull.json");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedWriter bw = Files.newBufferedWriter(path);
        
        String email = ControladorRegistro.userDAO.emailAdmin(ControladorLogin.nombre);
        List<Usuario> listuser = ControladorRegistro.userDAO.usersJson();
        
        for(int i = 0; i < listuser.size(); i++){
            bw.write(gson.toJson(listuser.get(i)) + "\n");
        }
        
        bw.flush();
        bw.close();
        
        File file = new File(System.getProperty("user.dir") + "/TheBull.json");
        
        
        ExecutorService service = Executors.newFixedThreadPool(4);
            service.submit(new Runnable() {
                public void run() {
                        SendMessage.sendFile(email, file);
                            }
                        });
            JOptionPane.showMessageDialog(new JFrame(), "Email with users sent to " + email, "Info", JOptionPane.INFORMATION_MESSAGE);        
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Player">

    public void cargarPlayers() throws IOException {
        playerDao = new PlayersDAO();

        ObservableList players = FXCollections.observableArrayList();
        List<Player> player = playerDao.buscarPlayer();
        players.addAll(player);
        TablePlayerUser.setItems(players);
    }
    
    
    public ObservableList<String> chargeTeamsCombo() throws IOException{
        try(Connection conexion = Conexion.getConnection()){
            String sql = "SELECT DISTINCT name FROM teams";
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
    
    public void editPlayer(){
        Player player = (Player) TablePlayerUser.getSelectionModel().getSelectedItem();
        
        if(player == null){
            JOptionPane.showMessageDialog(new JFrame(), "You must select a player", "Error", JOptionPane.ERROR_MESSAGE);        
        }else{
            txtJersey.setText(String.valueOf(player.getPlayerJersey()));
            txtName.setText(player.getPlayerName());
            txtPosition.setText(player.getPlayerPosition());
            txtWeight.setText(player.getPlayerWeight());
            txtHeight.setText(player.getPlayerHeight());
            txtAge.setText(String.valueOf(player.getPlayerAge()));
            txtDraft.setText(player.getPlayerDraft());
            txtCollege.setText(player.getPlayerCollege());
            txtNationality.setText(player.getPlayerNationality());
            txtPoints.setText(String.valueOf(player.getPlayerPoints()));
            txtRebbounds.setText(String.valueOf(player.getPlayerRebbounds()));
            txtAssist.setText(String.valueOf(player.getPlayerAssist()));
            txtImage.setText(player.getPlayerImage());        
            comboTeam.setValue(player.getPlayerTeam());
            idplayer = player.getPlayerId();
        }
    }
    
    public void insertPlayer() throws IOException{
         Player player = new Player();
        playerDao = new PlayersDAO();
        String user = null;
        if (txtJersey.getText().isEmpty() || txtName.getText().isEmpty() || txtPosition.getText().isEmpty() || txtWeight.getText().isEmpty() || txtHeight.getText().isEmpty()
                || txtAge.getText().isEmpty() || txtDraft.getText().isEmpty() || txtCollege.getText().isEmpty() || txtNationality.getText().isEmpty() || txtPoints.getText().isEmpty()
                || txtAssist.getText().isEmpty() || txtRebbounds.getText().isEmpty() || comboTeam.getValue()==null || txtImage.getText().isEmpty()){
            JOptionPane.showMessageDialog(new JFrame(), "All fields must be covered", "Error", JOptionPane.ERROR_MESSAGE);        
        }else{
                if(txtImage.getText().indexOf("cloudinary") != -1 || txtImage.getText().indexOf("cdn.nba") != -1){
                    player.setPlayerJersey(Integer.parseInt(txtJersey.getText()));
                    player.setPlayerName(txtName.getText());
                    player.setPlayerPosition(txtPosition.getText());
                    player.setPlayerWeight(txtWeight.getText() +"kg");
                    player.setPlayerHeight(txtHeight.getText() + "cm");
                    player.setPlayerTeam((String) comboTeam.getSelectionModel().getSelectedItem());
                    player.setPlayerAge(Integer.parseInt(txtAge.getText()));
                    player.setPlayerDraft(txtDraft.getText());
                    player.setPlayerCollege(txtCollege.getText());
                    player.setPlayerNationality(txtNationality.getText());
                    player.setPlayerPoints(Double.parseDouble(txtPoints.getText()));
                    player.setPlayerRebbounds(Double.parseDouble(txtRebbounds.getText()));
                    player.setPlayerAssist(Double.parseDouble(txtAssist.getText()));
                    player.setPlayerImage(txtImage.getText());  
                    player.setPlayerId(idplayer);
                    
                    ControladorRegistro.userDAO.saveEditPlayer(player, user);
                        cargarPlayers();
                        clearPlayer();
                        idplayer = 0;
                        JOptionPane.showMessageDialog(new JFrame(), "Inserted", "Error", JOptionPane.INFORMATION_MESSAGE);        
                }else{
                    try {
                        player.setPlayerJersey(Integer.parseInt(txtJersey.getText()));
                        player.setPlayerName(txtName.getText());
                        player.setPlayerPosition(txtPosition.getText());
                        player.setPlayerWeight(txtWeight.getText() +"kg");
                        player.setPlayerHeight(txtHeight.getText() + "cm");
                        player.setPlayerTeam((String) comboTeam.getSelectionModel().getSelectedItem());
                        player.setPlayerAge(Integer.parseInt(txtAge.getText()));
                        player.setPlayerDraft(txtDraft.getText());
                        player.setPlayerCollege(txtCollege.getText());
                        player.setPlayerNationality(txtNationality.getText());
                        player.setPlayerPoints(Double.parseDouble(txtPoints.getText()));
                        player.setPlayerRebbounds(Double.parseDouble(txtRebbounds.getText()));
                        player.setPlayerAssist(Double.parseDouble(txtAssist.getText()));
                        player.setPlayerImage(dinary(txtImage.getText()));
                        player.setPlayerId(idplayer);
                        user = ControladorRegistro.userDAO.searchUserPlayer(player.getPlayerTeam());
                                                
                        clearPlayer();
                        ControladorRegistro.userDAO.saveEditPlayer(player, user);
                        cargarPlayers();
                        idplayer = 0;
                        JOptionPane.showMessageDialog(new JFrame(), "Inserted", "Info", JOptionPane.INFORMATION_MESSAGE);        
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
        }

            }
    
    public void deletePlayer() throws IOException{
        Player player = (Player) TablePlayerUser.getSelectionModel().getSelectedItem();
        
        if(player == null){
            JOptionPane.showMessageDialog(new JFrame(), "You must select a player", "Error", JOptionPane.ERROR_MESSAGE);        
   
        }else{
            ControladorRegistro.userDAO.deletePlayer(player);
            JOptionPane.showMessageDialog(new JFrame(), "Player deleted", "Error", JOptionPane.INFORMATION_MESSAGE);       
            cargarPlayers();

        }
    }
    
    public void getImage(){
            FileChooser f = new FileChooser();
            f.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG FILES", "*.png"));
            File file = f.showOpenDialog(null);
            if(!(file == null)){
                txtImage.setText(file.getAbsolutePath());
            }
    }
                // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Team">
        
    public void cargarTeam() throws IOException {
        teamDao = new UsuariosDAO();
        ObservableList teams = FXCollections.observableArrayList();
        List<Team> team = teamDao.buscarTeam();
        teams.addAll(team);
        tableTeamsUser.setItems(teams);
    }
    
    public void insertTeam() throws IOException{
        Team team = new Team();
        
        if(txtNameTeam.getText().isEmpty() || txtTeamAbbreviation.getText().isEmpty() || txtTeamCity.getText().isEmpty() || txtConference.getText().isEmpty() ||
                txtDivision.getText().isEmpty() || comboUser.getValue() == null){
            JOptionPane.showMessageDialog(new JFrame(), "All fields must be covered", "Error", JOptionPane.ERROR_MESSAGE);        
        }else{
            team.setName(txtNameTeam.getText());
            team.setAbbreviation(txtTeamAbbreviation.getText());
            team.setCity(txtTeamCity.getText());
            team.setConference(txtConference.getText());
            team.setDivision(txtDivision.getText());
            team.setUser((String) comboUser.getSelectionModel().getSelectedItem());
            team.setId(idTeam);
            
            ControladorRegistro.userDAO.saveEditTeam(team);
            App.controller1.comboTeam.setItems(App.controller1.chargeTeamsCombo());
            cargarTeam();
            
            clearTeam();
            idTeam = 0;
        }
    }
    
    public void editTeam(){
        Team team = (Team) tableTeamsUser.getSelectionModel().getSelectedItem();
        
        if(team == null){
            JOptionPane.showMessageDialog(new JFrame(), "You must select a Team", "Error", JOptionPane.ERROR_MESSAGE);        
        }else{
            if(team.getName().equals("Agente Libre")){
                JOptionPane.showMessageDialog(new JFrame(), "You cant edit this team", "Error", JOptionPane.ERROR_MESSAGE);        
            }else{
                txtNameTeam.setText(team.getName());
                txtTeamAbbreviation.setText(team.getAbbreviation());
                txtTeamCity.setText(team.getCity());
                txtConference.setText(team.getConference());
                txtDivision.setText(team.getDivision());
                comboUser.setValue(team.getUser());
                idTeam = team.getId();
           }
        }
    }    
    
    public void deleteTeam() throws IOException{
        Team team = (Team) tableTeamsUser.getSelectionModel().getSelectedItem();
        
        if(team.getName().equals("Agente Libre")){
                JOptionPane.showMessageDialog(new JFrame(), "You cant delete this team", "Error", JOptionPane.ERROR_MESSAGE);        
        }else{
            ControladorRegistro.userDAO.deleteTeam(team);
            cargarTeam();
            cargarPlayers();
            App.controller1.comboTeam.setItems(App.controller1.chargeTeamsCombo());
            JOptionPane.showMessageDialog(new JFrame(), "Team deleted succesfully", "Info", JOptionPane.INFORMATION_MESSAGE); 
            idTeam = 0;
        }
    }
    
    public ObservableList<String> chargeTeamsComboUser() throws IOException{
        try(Connection conexion = Conexion.getConnection()){
            String sql = "SELECT DISTINCT user FROM usuarios";
            PreparedStatement prepstmt = conexion.prepareStatement(sql);
            itemsUser.removeAll(itemsUser);
            ResultSet rs = prepstmt.executeQuery();
            while(rs.next()){
                itemsUser.add(rs.getString("user"));
            }
            return itemsUser;
        }catch(SQLException e){
            Utilidades.Logger.logInfo(e.getMessage() , 2);
            return null;

        }
    }

    // </editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc="Log">
    
        public void getLog() throws IOException{
            Path path = Paths.get(System.getProperty("user.dir") + "/TheBull.log");
            File file = new File(path.toString());
            
            if(file.exists()){
                txtlog.clear();
                BufferedReader br = Files.newBufferedReader(path);
                String line = "";
            while ((line = br.readLine()) != null) {
                txtlog.appendText(line + "\n");
            }
            br.close();
            
            }

        }
        
    // </editor-fold>
    
        
    public void desencriptar(){
                if(!txtPassword.getText().isEmpty()){
                    txtPassword.setText(decrypt(txtPassword.getText()));
                }
                btnDecrypt.setDisable(true);
        
    }
    
    private static String decrypt(String pass){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(pass);
                 
        return new String(bytes);
    }
    
    private String encrypt(String pass){
        Encoder encoder = Base64.getEncoder();
        String encodedString = encoder.encodeToString(pass.getBytes());
 
            return encodedString;
        }
    
    private boolean checkmail(String mail){
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(mail);
        boolean match = matcher.matches();
        
        return match;
        
    }
    
    public void loadFilterUser(){
            FilteredList<Usuario> filterPlayer = new FilteredList<>(users, e -> true);
        txtFilterUser.setOnKeyReleased(e -> {
            txtFilterUser.textProperty().addListener((value, oldvalue, newvalue) -> {
                filterPlayer.setPredicate((Predicate<? super Usuario>) user ->{
                    if(newvalue == null || newvalue.isEmpty()){
                        return true;
                    }else if(user.getUser().toLowerCase().contains(newvalue.toLowerCase())){
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Usuario> sortedList = new SortedList<>(filterPlayer);
            sortedList.comparatorProperty().bind(tableuser.comparatorProperty());
            tableuser.setItems(sortedList);
        });     
    }
    
    
    private void clearFieldsUser(){
        txtUser.clear();
        txtEmail.clear();
        txtDate.setValue(null);
        txtPassword.clear();
        id=0;
    }
    
    private void clearTeam(){
        txtNameTeam.clear();
        txtTeamAbbreviation.clear();
        txtTeamCity.clear();
        txtConference.clear();
        txtDivision.clear();
        comboUser.setValue(null);
        idTeam=0;
    }
    
    private void clearPlayer(){
            txtJersey.clear();
            txtName.clear();
            txtPosition.clear();
            txtWeight.clear();
            txtHeight.clear();
            txtAge.clear();
            txtDraft.clear();
            txtCollege.clear();
            txtNationality.clear();
            txtPoints.clear();
            txtRebbounds.clear();
            txtAssist.clear();
            txtImage.clear();    
            comboTeam.setValue(null);
            idplayer=0;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            slider.valueProperty().addListener(new ChangeListener<Number>(){
                @Override
                public void changed(ObservableValue<? extends Number> observar, Number numeroAnterior, Number numeroNuevo) {
                    
                    txtAge.setText(String.valueOf((int)Math.round((double) numeroNuevo)));
            }
            });
            
            txtAge.textProperty().addListener((observable, oldvalue, newvalue) -> {
                try{
                    slider.setValue(Integer.parseInt(txtAge.getText()));
                }catch(NumberFormatException e){
                    
                }
            });    }


public void deselect() throws IOException{
    cargarAdmin();
    cargarUser();
}



}

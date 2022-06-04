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
import edu.martin.dida.proyectofinciclo.ControladorLogin.ControladorLogin;
import edu.martin.dida.proyectofinciclo.ControladorLogin.ControladorRegistro;
import edu.martin.dida.proyectofinciclo.inicio.ControladorInicio;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
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
public class ControladorAdmin {
    
    
    
    
    @FXML
    private Button btnMinimize;
    
    // <editor-fold defaultstate="collapsed" desc="User">

    
    @FXML
    private TableView tableuser;
    
    @FXML
    private DatePicker txtDate;

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

    int id;
    int idplayer;
    PlayersDAO playerDao;
    ObservableList<String> items = FXCollections.observableArrayList();

    
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
        ObservableList<Usuario> users = FXCollections.observableArrayList();
        List<Usuario> user = ControladorRegistro.userDAO.buscarUser();
        users.addAll(user);
        tableuser.setItems(users);
    }
        
    public void cargarAdmin() throws IOException {
        ObservableList<Usuario> users = FXCollections.observableArrayList();
        List<Usuario> user = ControladorRegistro.userDAO.buscarAdmin();
        users.addAll(user);
        tableadmin.setItems(users);
    }
    
    public void editUser() throws IOException{
        Usuario user = (Usuario) tableuser.getSelectionModel().getSelectedItem();
        Usuario admin = (Usuario) tableadmin.getSelectionModel().getSelectedItem();
            

        if(!(user == null)){
            if(!(UsuariosDAO.getStatus(user.getUser()) == true)){
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
                if(!(UsuariosDAO.getStatus(admin.getUser()) == true)){
                    txtUser.setText(admin.getUser());
                txtEmail.setText(admin.getEmail());
                txtPassword.setText(encrypt(admin.getPassword()));



                if(admin.getPermisos().equals("admin")){
                    checkAdmin.setSelected(true);
                }
                id = admin.getId();
                }else{
                    JOptionPane.showMessageDialog(new JFrame(), "This user is online", "Error", JOptionPane.ERROR_MESSAGE);        

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
                clearFieldsUser();
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
                ControladorRegistro.userDAO.deleteUser(user);
                cargarUser();
                cargarPlayers();
                numberUser();
            }else{
            JOptionPane.showMessageDialog(new JFrame(), "This user is online", "Error", JOptionPane.ERROR_MESSAGE);        
            }
        }else if(user==null && admin!=null){
            if(!(UsuariosDAO.getStatus(admin.getUser()) == true)){
                ControladorRegistro.userDAO.deleteUser(admin);
                cargarAdmin();
                cargarPlayers();
                numberUser();
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
                        idplayer = 0;
                        JOptionPane.showMessageDialog(new JFrame(), "Inserted", "Error", JOptionPane.ERROR_MESSAGE);        
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
                        
                        ControladorRegistro.userDAO.saveEditPlayer(player, user);
                        cargarPlayers();
                        idplayer = 0;
                        JOptionPane.showMessageDialog(new JFrame(), "Inserted", "Info", JOptionPane.ERROR_MESSAGE);        
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
    
    
    
    public void desencriptar(){
        if(decryptBool){
                if(!txtPassword.getText().isEmpty()){
                    txtPassword.setText(decrypt(txtPassword.getText()));
                }
                decryptBool=false;
        }else{
            if(!txtPassword.getText().isEmpty()){
                    txtPassword.setText(encrypt(txtPassword.getText()));
                }
            decryptBool = true;
        }

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
    
    private void clearFieldsUser(){
        txtUser.clear();
        txtEmail.clear();
        txtDate.setValue(null);
        txtPassword.clear();
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

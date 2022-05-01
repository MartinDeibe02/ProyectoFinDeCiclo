/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Markyuu
 */
public class Usuario {
    private int id;
    private String user;
    private String email;
    private String dateBirth;
    private String password;
    private String permisos;

    public Usuario(int id, String user, String email, String dateBirth, String password, String permisos) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.dateBirth = dateBirth;
        this.password = password;
        this.permisos = permisos;
    }

    public Usuario(int id, String user, String email, String dateBirth, String password) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.dateBirth = dateBirth;
        this.password = password;
    }

    
    
    public Usuario() {}   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }
    
    
}

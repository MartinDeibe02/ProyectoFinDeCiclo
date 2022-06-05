/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Markyuu
 */
public class Team {
    private int id;
    private String name;
    private String abbreviation;
    private String city;
    private String conference;
    private String division;
    private String user;

    public Team(int id, String name, String abbreviation, String city, String conference, String division) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.city = city;
        this.conference = conference;
        this.division = division;
    }

    public Team(int id, String name, String abbreviation, String city, String conference, String division, String user) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.city = city;
        this.conference = conference;
        this.division = division;
        this.user = user;
    }
    
    

    public Team() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    

    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", name=" + name + ", abbreviation=" + abbreviation + ", city=" + city + ", conference=" + conference + ", division=" + division + '}';
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Markyuu
 */
public class Player {
    private int playerId;
    private int playerJersey;
    private String playerName;
    private String playerPosition;
    private String playerHeight;
    private String playerWeight;
    private int playerAge;
    private String playerTeam;
    private String playerCollege;
    private String playerDraft;
    private String playerNationality;
    private double playerPoints;
    private double playerRebbounds;
    private double playerAssist;
    private String playerImage;

    public Player(int playerId, int playerJersey, String playerName, String playerPosition, String playerHeight, String playerWeight, int playerAge, String playerTeam, String playerCollege, String playerDraft, String playerNationality, double playerPoints, double playerRebbounds, double playerAssist, String playerImage) {
        this.playerId = playerId;
        this.playerJersey = playerJersey;
        this.playerName = playerName;
        this.playerPosition = playerPosition;
        this.playerHeight = playerHeight;
        this.playerWeight = playerWeight;
        this.playerAge = playerAge;
        this.playerTeam = playerTeam;
        this.playerCollege = playerCollege;
        this.playerDraft = playerDraft;
        this.playerNationality = playerNationality;
        this.playerPoints = playerPoints;
        this.playerRebbounds = playerRebbounds;
        this.playerAssist = playerAssist;
        this.playerImage = playerImage;
    }

    public Player() {
    }

    public Player(int playerJersey, String playerName, String playerPosition, String playerHeight, String playerWeight, int playerAge, String playerTeam, String playerCollege, String playerDraft, String playerNationality, double playerPoints, double playerRebbounds, double playerAssist, String playerImage) {
        this.playerJersey = playerJersey;
        this.playerName = playerName;
        this.playerPosition = playerPosition;
        this.playerHeight = playerHeight;
        this.playerWeight = playerWeight;
        this.playerAge = playerAge;
        this.playerTeam = playerTeam;
        this.playerCollege = playerCollege;
        this.playerDraft = playerDraft;
        this.playerNationality = playerNationality;
        this.playerPoints = playerPoints;
        this.playerRebbounds = playerRebbounds;
        this.playerAssist = playerAssist;
        this.playerImage = playerImage;
    }
    
    

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerJersey() {
        return playerJersey;
    }

    public void setPlayerJersey(int playerJersey) {
        this.playerJersey = playerJersey;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    public String getPlayerHeight() {
        return playerHeight;
    }

    public void setPlayerHeight(String playerHeight) {
        this.playerHeight = playerHeight;
    }

    public String getPlayerWeight() {
        return playerWeight;
    }

    public void setPlayerWeight(String playerWeight) {
        this.playerWeight = playerWeight;
    }

    public int getPlayerAge() {
        return playerAge;
    }

    public void setPlayerAge(int playerAge) {
        this.playerAge = playerAge;
    }

    public String getPlayerTeam() {
        return playerTeam;
    }

    public void setPlayerTeam(String playerTeam) {
        this.playerTeam = playerTeam;
    }

    public String getPlayerCollege() {
        return playerCollege;
    }

    public void setPlayerCollege(String playerCollege) {
        this.playerCollege = playerCollege;
    }

    public String getPlayerDraft() {
        return playerDraft;
    }

    public void setPlayerDraft(String playerDraft) {
        this.playerDraft = playerDraft;
    }

    public String getPlayerNationality() {
        return playerNationality;
    }

    public void setPlayerNationality(String playerNationality) {
        this.playerNationality = playerNationality;
    }

    public double getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(double playerPoints) {
        this.playerPoints = playerPoints;
    }

    public double getPlayerRebbounds() {
        return playerRebbounds;
    }

    public void setPlayerRebbounds(double playerRebbounds) {
        this.playerRebbounds = playerRebbounds;
    }

    public double getPlayerAssist() {
        return playerAssist;
    }

    public void setPlayerAssist(double playerAssist) {
        this.playerAssist = playerAssist;
    }

    public String getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(String playerImage) {
        this.playerImage = playerImage;
    }


    @Override
    public String toString() {
        return "Player{" + "playerJersey=" + playerJersey + ", playerName=" + playerName + ", playerPosition=" + playerPosition + ", playerHeight=" + playerHeight + ", playerWeight=" + playerWeight + ", playerAge=" + playerAge + ", playerTeam=" + playerTeam + ", playerCollege=" + playerCollege + ", playerDraft=" + playerDraft + ", playerNationality=" + playerNationality + ", playerPoints=" + playerPoints + ", playerRebbounds=" + playerRebbounds + ", playerAssist=" + playerAssist + ", playerImage=" + playerImage + '}';
    }
    
    


    
    
}

package swfm.model;

public class PlayerRegClasifGral {


    private int season;
    private String player; 
    private  int totalPoints;
    private  int numJornadas;
    private int numVotos;
    private  float average;


    //******************************************************
    public PlayerRegClasifGral(int season, String player, int totalPoints, int numJornadas) {
        this.season = season;
        this.player = player;
        this.totalPoints = totalPoints;
        this.numJornadas = numJornadas;
    } 

    public PlayerRegClasifGral(int season, String player, int totalPoints, int numVotos, float average) {
        this.season = season;
        this.player = player;
        this.totalPoints = totalPoints;
        this.numVotos = numVotos;
        this.average = average;
    } 

    
    //******************************************************
    public int getSeason() {
        return season;
    }


    public String getPlayer() {
        return player;
    }


    public int getTotalPoints() {
        return totalPoints;
    }


    public int getNumVotos() {
        return numVotos;
    }


    public int getNumJornadas() {
        return numJornadas;
    }


    public float getAverage() {
        return average;
    }

}

package swfm.model;

public class PlayerGame {


    private int season;
    private int jornada;
    private int numOrden;
    private String player;
    private int totalPoints;
    private float average;


    //******************************************************
    public PlayerGame(int season, int jornada, String player, int totalPoints, float average) {
        this.season = season;
        this.jornada = jornada;
        this.player = player;
        this.totalPoints = totalPoints;
        this.average = average;
    }


    //******************************************************
    public int getSeason() {
        return season;
    }


    public int getJornada() {
        return jornada;
    }


    public int getNumOrden() {
        return numOrden;
    }


    public String getPlayer() {
        return player;
    }


    public int getTotalPoints() {
        return totalPoints;
    }


    public float getAverage() {
        return average;
    }


}

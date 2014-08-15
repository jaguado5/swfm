package swfm.model;

public class Jornada {


    private int season;
    private int jornada;
    private int numPLayers;
    private int numVoters;
    private String game;
    private String score;
    private int qNumUsers;
    private String qWinners;


    // *****************************************************************
    public Jornada(int season, int jornada, int numPLayers, int numVoters, String game, String score) {
        this.season = season;
        this.jornada = jornada;
        this.numPLayers = numPLayers;
        this.numVoters = numVoters;
        this.game = game;
        this.score = score;
    }


    public Jornada(int season, int jornada, int qNumUsers, String qWinners) {
        this.season = season;
        this.jornada = jornada;
        this.qNumUsers = qNumUsers;
        this.qWinners = qWinners;
    }


    // *****************************************************************
    public int getJornada() {
        return jornada;
    }


    // *****************************************************************
    public int getNumPLayers() {
        return numPLayers;
    }


    // *****************************************************************
    public int getNumVoters() {
        return numVoters;
    }


    // *****************************************************************
    public String getGame() {
        return game;
    }


    // *****************************************************************
    public String getScore() {
        return score;
    }


    // *****************************************************************
    public int getQNumUsers() {
        return qNumUsers;
    }


    // *****************************************************************
    public String getQWinners() {
        return qWinners;
    }


}

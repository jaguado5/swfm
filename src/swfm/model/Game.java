package swfm.model;

public class Game {


    private int season;
    private int jornada;
    private String team1;
    private String team2;
    private String score;
    private String escudo1;
    private String escudo2;


    //*****************************************************************
    public Game(int season, int jornada, String team1, String team2, String score) {
        this.season = season;
        this.jornada = jornada;
        this.team1 = team1;
        this.team2 = team2;
        this.score = score;
    }


    //*****************************************************************
    public int getSeason() {
        return season;
    }


    public int getJornada() {
        return jornada;
    }


    public String getTeam1() {
        return team1;
    }


    public String getTeam2() {
        return team2;
    }


    public String getScore() {
        return score;
    }


    public String getEscudo1() {
        return escudo1;
    }


    public String getEscudo2() {
        return escudo2;
    }


    public void setEscudo1(String escudo1) {
        this.escudo1 = escudo1;
    }


    public void setEscudo2(String escudo2) {
        this.escudo2 = escudo2;
    }

}

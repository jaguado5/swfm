package swfm.model;

public class GameMcf {


    private int season;
    private int jornada;
    private String game;
    private String score;


    //******************************************************
    public GameMcf(int season, int jornada, String game, String score) {
        super();
        this.season = season;
        this.jornada = jornada;
        this.game = game;
        this.score = score;
    }


    //******************************************************
    public int getSeason() {
        return season;
    }



    public int getJornada() {
        return jornada;
    }



    public String getGame() {
        return game;
    }



    public String getScore() {
        return score;
    }



}

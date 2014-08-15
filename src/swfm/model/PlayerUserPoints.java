package swfm.model;

public class PlayerUserPoints {

    private int season;
    private int jornada;
    private String user;
    private String player;
    private int puntos;



    public PlayerUserPoints(int season, int jornada, String user,String player, int puntos) {
        this.season = season;
        this.jornada = jornada;
        this.user = user;
        this.player = player;
        this.puntos = puntos;
    }


    public int getSeason() {
        return season;
    }


    public int getJornada() {
        return jornada;
    }


    public String getUser() {
        return user;
    }


    public String getPlayer() {
        return player;
    }


    public int getPuntos() {
        return puntos;
    }



}

package swfm.view;

public class BestPlayerClasifGeneralRegPlayer {


    private String player;
    private int totalPoints;
    private int numVotos;
    private int numJornadas;
    private String avgJornada;
    private String avgVotante;
    private String picture;
    private String promedio1;
    private String promedio2;
    private String totalPromedio;


    //******************************************************
    public BestPlayerClasifGeneralRegPlayer(String player, String picture, int totalPoints, int numJornadas, int numVotos, String avgJornada, String avgVotante) {

        this.player = player;
        this.picture = picture;
        this.totalPoints = totalPoints;
        this.numJornadas = numJornadas;
        this.numVotos = numVotos;
        this.avgJornada = avgJornada;
        this.avgVotante = avgVotante;
    }


    public BestPlayerClasifGeneralRegPlayer(String player, String picture, int totalPoints, int numJornadas, int numVotos, String avgVotante) {

        this.player = player;
        this.picture = picture;
        this.totalPoints = totalPoints;
        this.numJornadas = numJornadas;
        this.numVotos = numVotos;
        this.avgVotante = avgVotante;
    }


    public BestPlayerClasifGeneralRegPlayer(String player, String picture, int totalPoints, int numJornadas, int numVotos, String avgVotante,
                                            String prom1, String prom2, String totalProm) {

        this.player = player;
        this.picture = picture;
        this.totalPoints = totalPoints;
        this.numJornadas = numJornadas;
        this.numVotos = numVotos;
        this.avgVotante = avgVotante;
        this.promedio1 = prom1;
        this.promedio2 = prom2;
        this.totalPromedio = totalProm;
    }


    //******************************************************
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


    public String getAvgJornada() {
        return avgJornada;
    }


    public String getAvgVotante() {
        return avgVotante;
    }


    public String getPicture() {
        return picture;
    }


    public void setPicture(String picture) {
        this.picture = picture;
    }


    public String getPromedio1() {
        return promedio1;
    }


    public String getPromedio2() {
        return promedio2;
    }


    public String getTotalPromedio() {
        return totalPromedio;
    }


}

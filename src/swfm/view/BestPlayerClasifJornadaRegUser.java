package swfm.view;

public class BestPlayerClasifJornadaRegUser {
    
    private int puesto;
    private String user;
    private String[] puntos;
    private String totalPoints;
    private String average;


    //******************************************************
    public BestPlayerClasifJornadaRegUser(int puesto, String user, String[] puntos, String totalPoints, String average) {
        this.puesto = puesto;
        this.user = user;
        this.puntos = puntos;
        this.totalPoints = totalPoints;
        this.average = average;
    }


    //******************************************************
    public int getPuesto() {
        return puesto;
    }


    public String getUser() {
        return user;
    }


    public String[] getPuntos() {
        return puntos;
    }


    public String getTotalPoints() {
        return totalPoints;
    }


    public String getAverage() {
        return average;
    }
    
    

}

package swfm.model;

/*********************************************************
*                   Clase RegClasifJornada
*********************************************************/

public class RegClasifJornada {

    private int puesto;
    private String user;
    private int aciertos;
    private int tj;
    private char[] games;
    
    
    RegClasifJornada(int puesto, String user, int aciertos, int tj, char[] games) {
        this.puesto = puesto;
        this.user = user;
        this.aciertos = aciertos;
        this.tj = tj;
        this.games = games;
    }


    
    public int getPuesto() {
        return puesto;
    }


    public String getUser() {
        return user;
    }


    public int getAciertos() {
        return aciertos;
    }


    public int getTj() {
        return tj;
    }


    public char[] getGames() {
        return games;
    }

    public char getGame(int nGame) {
        return games[nGame];
    }

}

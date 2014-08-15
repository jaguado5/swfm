package swfm.model;

/*********************************************************
*                   Clase RegClasifGeneral
*********************************************************/

public class RegClasifGeneral {

    private int posAnterior;
    private char flecha;
    private int posActual;
    private String user;
    private int puntosTotal;
    private int tj;
    private int qj;
    private int partidos;
    private float porcentaje;
    private int puntosAnterior;
    private int puntosActual;
    private int total;
    
    
    RegClasifGeneral(int posAnterior, char flecha, int posActual, String user, int puntosTotal, int tj, int qj, 
                        int partidos, float porcentaje, int puntosAnterior, int puntosActual, int total) {
        this.posAnterior = posAnterior;
        this.flecha = flecha;
        this.posActual = posActual;
        this.user = user;
        this.puntosTotal = puntosTotal;
        this.tj = tj;
        this.qj = qj;
        this.partidos = partidos;
        this.porcentaje = porcentaje;
        this.puntosAnterior = puntosAnterior;
        this.puntosActual = puntosActual;
        this.total = total;
    }


    
    public int getPosAnterior() {
        return posAnterior;
    }


    public char getFlecha() {
        return flecha;
    }


    public int getPosActual() {
        return posActual;
    }


    public String getUser() {
        return user;
    }


    public int getPuntosTotal() {
        return puntosTotal;
    }


    public int getTj() {
        return tj;
    }


    public int getQj() {
        return qj;
    }


    public int getPartidos() {
        return partidos;
    }


    public String getPorcentaje() {
        String average= this.porcentaje + "";
        
        if (average.length()>=5)
            return average.substring(0,5);
        else {
            average += "0"; //add '0' at the end of the string 
            return average.substring(0,average.length());
        }
    }


    public int getPuntosAnterior() {
        return puntosAnterior;
    }


    public int getPuntosActual() {
        return puntosActual;
    }


    public int getTotal() {
        return total;
    }


}

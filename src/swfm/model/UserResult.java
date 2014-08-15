package swfm.model;


import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory; 

public class UserResult { 

    private transient static final Log log=LogFactory.getLog(UserResult.class); 
    
    private int season = 0; 
    private int jornada = 0; 
    private String usr = ""; 
    private char prediction[]; 
    private int success=0; 
    private int numPredictions=0;
    private int puestoJornada=0; 
    private int puestoTotal=0; 
    private char flecha; 


    //****************************************************** 
    public UserResult(int season, int jornada, String usr) { 
        this.season = season; 
        this.jornada = jornada; 
        this.usr = usr; 
        this.prediction = new char[15];
    } 


    UserResult(int season, int jornada, String usr, int puestoTotal) {
        this.season = season;
        this.usr = usr; 
        this.jornada = jornada; 
        this.puestoTotal = puestoTotal; 
    }


    UserResult(int season, int jornada, String usr, char prediction[]) {
        this.season = season;
        this.usr = usr; 
        this.jornada = jornada; 
        this.prediction = prediction; 
    }


    //****************************************************** 
    public void setPrediction(int gameId, char result) { 
        prediction[gameId] = result; 
    } 



    //****************************************************** 
    public int getSeason() { 
        return season; 
    }


    //****************************************************** 
    public int getJornada() { 
        return jornada; 
    }


    //****************************************************** 
    public String getUsr() { 
        return usr; 
    } 


    //****************************************************** 
    public char[] getPrediction() { 
        return prediction; 
    } 


    //****************************************************** 
    public int getSuccess() { 
        return success; 
    } 


    //****************************************************** 
    public int getNumPredictions() {
        return numPredictions;
    }


    //****************************************************** 
    public void setNumPredictions(int numPredictions) {
        this.numPredictions = numPredictions;
    }


    //****************************************************** 
    public int getPuestoJornada() { 
        return puestoJornada; 
    } 


    //****************************************************** 
    public int getPuestoTotal() { 
        return puestoTotal; 
    } 


    //****************************************************** 
    public char getFlecha() { 
        return flecha; 
    }


    //****************************************************** 
    public void computeSuccess(MasterResult mResult) { 
        
        char results[] = mResult.getGameResults(); 
        for (int i=0; i<results.length; i++) { 
            if (results[i]==prediction[i]) 
                this.success++; 
        } 
        
        log.info("/"+usr+"/ has " + this.success + " OK of " + numPredictions); 
    } 
} 
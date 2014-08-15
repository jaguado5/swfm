
//***************************************************************** 

package swfm.model;

public class MasterResult { 

    int season = 0;
    int jornada = 0; 
    char gameResults[]; 


    //****************************************************** 
    public MasterResult(int season, int jornada) { 
        this.season = season; 
        this.jornada = jornada; 
        gameResults = new char[15]; 
    } 


    //****************************************************** 
    void setGame(int gameId, char result) { 
        gameResults[gameId] = result; 
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
    public char[] getGameResults() { 
        return gameResults; 
    }


    //****************************************************** 
    public char getGame(int nGame) {
        return gameResults[nGame];
    }


    //****************************************************** 
    public void setGameResult(char g[]) {
        this.gameResults = g;
    }


} 
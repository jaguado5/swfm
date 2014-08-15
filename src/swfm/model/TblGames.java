package swfm.model;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import swfm.controller.CONST;

public class TblGames {


    private Connection conn;


    // *****************************************************************
    public TblGames(Connection conn) {
        this.conn = conn;
    }


    // *****************************************************************
    public GameMcf getUsersJornada(int season, int jornada) throws Exception {

        GameMcf g = null;
        
        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select game, score " + 
                              " from games_mcf " + 
                              " where season="+ season+" and jornada="+jornada +" ;";

            ResultSet rSet = stmt.executeQuery(query); 

            if (rSet.next()==true) {
                String game = rSet.getString(1); 
                String score = rSet.getString(2); 

                g = new GameMcf(season, jornada, game, score);
            }

        } catch (Exception e) { 
            throw e; 
        } 

        return g;
    }


    // *****************************************************************
    public List<Game> getGamesList(int season, int jornada) throws Exception {

        List<Game> gamesList = new ArrayList<Game>();
        
        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select team1, team2, score " + 
                              " from games " + 
                              " where jornada="+ jornada+" and season="+season + " order by numOrden asc;";

            ResultSet rSet = stmt.executeQuery(query); 

            while (rSet.next()==true) {
                String team1 = rSet.getString(1);
                String team2 = rSet.getString(2);
                String score = rSet.getString(3);

                String escudo1 = getEscudo(team1.toUpperCase());
                String escudo2 = getEscudo(team2.toUpperCase());

                Game pg = new Game(season, jornada, team1, team2, score);
                pg.setEscudo1(escudo1);
                pg.setEscudo2(escudo2);
                
                gamesList.add(pg);
            }

        } catch (Exception e) { 
            throw e; 
        } 

        return gamesList;
    }


    // *****************************************************************
    public int peristGamesList(List<Game> gamesList) throws Exception {

        int numRegInserted = 0;

        try { 
            Statement stmt = conn.createStatement();

            int numOrden = 0;
            Iterator<Game> it = gamesList.iterator();
            while (it.hasNext()) {

                Game g = it.next();
                
                int season = g.getSeason();
                int jornada = g.getJornada();
                numOrden++;
                String team1 = g.getTeam1();
                String team2 = g.getTeam2();
                String score = g.getScore();

                String query = "INSERT INTO games VALUES ( "+ season+","+ jornada+","+ numOrden +",'"+team1+"','" +team2+ "','"+score+"');";

                int result = stmt.executeUpdate(query);

                if (result != 1) {
                    throw new Exception();
                } else {
                    numRegInserted++;
                }
            }

        } catch (Exception e) { 
            throw e; 
        }

        System.out.println("peristGamesList; inserted " + numRegInserted + " register in games table");
        
        return numRegInserted;

    }


    // *****************************************************************
    public String getEscudo(String team) throws Exception {

        String imageFileName = null;

        try {
            Statement stmt = conn.createStatement();

            String query = "SELECT escudo,literal from equipos " +
                                " WHERE team='" + team + "';";

            ResultSet res = stmt.executeQuery(query);
            if (res.next()) {
                Blob imagen = res.getBlob(1);
                String literal = res.getString(2);

                if (imagen != null) {
                    InputStream is = imagen.getBinaryStream();

                    String fileName = CONST.TEAMS_PATH_TMP + "/" + literal +".png";
                    FileOutputStream fw = new FileOutputStream(CONST.PATH_TOMCAT + fileName);

                    //Write image read from BBDD in a file
                    byte bytes[] = new byte[1024];
                    int nBytes = is.read(bytes);
                    while (nBytes>0) {
                        fw.write(bytes);
                        nBytes = is.read(bytes);
                    }

                    fw.close();
                    is.close();
                    imageFileName = fileName;
                }
            }

        } catch (Exception e) { 
            throw e; 
        }

        return imageFileName;
    }



}

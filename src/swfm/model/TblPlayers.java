package swfm.model;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import swfm.controller.CONST;


public class TblPlayers {
    
    
    private Connection conn;

    // *****************************************************************
    public TblPlayers(Connection conn) {
        this.conn = conn;
    }


    // *****************************************************************
    public int persistPlayerGame( int season, int jornada,int numOrden, String player) throws Exception {

        int result=0; 
        try { 
            Statement stmt = conn.createStatement(); 

            // Inserta registro en BBDD 
            String query = "INSERT players_games VALUES (" +season+ "," +jornada+ ",'" +player+"', "+ numOrden + ", 0, 0);";

          result = stmt.executeUpdate(query); 
          // result debe ser = 1 (1 row affecteds: 1 inserted) 
          
          if (result != 1) 
            throw new Exception(); 

        } catch (Exception e) { 
            throw e; 
        } 

        return result; 
    }


    // *****************************************************************
    public int persistPlayerUserPoints(PlayerUserPoints pup) throws Exception {

        int result=0; 
        try { 
            Statement stmt = conn.createStatement(); 

            // Inserta registro en BBDD 
            int season = pup.getSeason();
            int jornada = pup.getJornada();
            String user = pup.getUser();
            String player = pup.getPlayer();
            int puntos = pup.getPuntos();
     
            String query = "INSERT players_users_points VALUES (" +season+ "," +jornada+ ",'" +user+"', '" + player + "', "+ puntos + ");";

            result = stmt.executeUpdate(query);

            // result debe ser = 1 (1 row affecteds: 1 inserted) 
            if (result != 1) 
                throw new Exception(); 

        } catch (Exception e) { 
            throw e; 
        } 

        return result; 
    }


    // *****************************************************************
    public int persistPlayerPoints(PlayerGame pg) throws Exception {

        int result=0;
        int season = pg.getSeason();
        int jornada = pg.getJornada();
        String player= pg.getPlayer();
        int totalPoints = pg.getTotalPoints();
        float avg = pg.getAverage();
        
        try { 
            Statement stmt = conn.createStatement(); 

            // Inserta registro en BBDD 
            String query = "UPDATE players_games SET totalPoints=" + totalPoints + ", average=" + avg +
                                " WHERE season=" + season + 
                                    " and jornada=" + jornada + 
                                    " and player='" + player + "';";

            result = stmt.executeUpdate(query);
          
            // result debe ser = 1 (1 row affecteds: 1 inserted) 
            if (result != 1) 
                throw new Exception(); 

        } catch (Exception e) { 
            throw e; 
        } 

        return result; 
    }


    // *****************************************************************
    public List<PlayerGame> getPlayersJornada(int jornada, int season) throws Exception {

        List<PlayerGame> playersList = new ArrayList<PlayerGame>();
        
        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select player, totalPoints, average " + 
                              " from players_games " + 
                              " where jornada="+ jornada+" and season="+season + " order by numOrden asc;";

            ResultSet rSet = stmt.executeQuery(query); 

            while (rSet.next()==true) { 
                String player = rSet.getString(1); 
                int totalPoints = rSet.getInt(2);
                float average = (float)rSet.getFloat(3); 
                
                PlayerGame pg = new PlayerGame(season, jornada, player, totalPoints, average);
                playersList.add(pg);
            }

        } catch (Exception e) { 
            throw e; 
        } 

        return playersList;
    }


    // *****************************************************************
    public int getPlayerNumJornadasUpto(int season, int jornada, String player) throws Exception{

        int numJornadas = 0;

        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select count(*) " + 
                              " from players_games " + 
                              " where season="+season + " and jornada<=" + jornada +" and player='" + player + "';";

            ResultSet rSet = stmt.executeQuery(query); 

            if (rSet.next()==true) {
                numJornadas = rSet.getInt(1);
            }

        } catch (Exception e) { 
            throw e; 
        } 

        return numJornadas;
        
    }


    // *****************************************************************
    public int getPlayerNumVotosUpto(int season, int jornada, String player) throws Exception{

        int numJornadas = 0;

        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select count(*) " + 
                              " from players_users_points " + 
                              " where season="+season + " and jornada<=" + jornada +" and player='" + player + "' and puntos>=0;";

            ResultSet rSet = stmt.executeQuery(query); 

            if (rSet.next()==true) {
                numJornadas = rSet.getInt(1);
            }

        } catch (Exception e) { 
            throw e; 
        } 

        return numJornadas;
        
    }


    // *****************************************************************
    public List<PlayerRegClasifGral> getPlayersClasifGeneralTotalPoints(int season, int jornada) throws Exception {

        List<PlayerRegClasifGral> playersList = new ArrayList<PlayerRegClasifGral>();

        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select player, sum(totalPoints) Total, count(*) numJornadas " +
                                " from players_games" + 
                                " where season=" + season + " and jornada<=" + jornada +
                                " group by player order by Total desc;";

            ResultSet rSet = stmt.executeQuery(query); 

            while (rSet.next()==true) { 
                String player = rSet.getString(1); 
                int totalPoints = rSet.getInt(2);
                int numJornadas = rSet.getInt(3); 
                
                PlayerRegClasifGral p = new PlayerRegClasifGral(season, player, totalPoints, numJornadas);
                playersList.add(p);
            }

        } catch (Exception e) { 
            throw e; 
        } 

        return playersList;
    }


    // *****************************************************************
    public List<PlayerRegClasifGral> getPlayersClasifJornadaTotalPoints(int season, int jornada) throws Exception {

        List<PlayerRegClasifGral> playersList = new ArrayList<PlayerRegClasifGral>();

        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select player, sum(totalPoints) Total, count(*) numJornadas " +
                                " from players_games" + 
                                " where season=" + season + " and jornada=" + jornada +
                                " group by player order by Total desc;";

            ResultSet rSet = stmt.executeQuery(query); 

            while (rSet.next()==true) { 
                String player = rSet.getString(1); 
                int totalPoints = rSet.getInt(2);
                int numJornadas = rSet.getInt(3); 
                
                PlayerRegClasifGral p = new PlayerRegClasifGral(season, player, totalPoints, numJornadas);
                playersList.add(p);
            }

        } catch (Exception e) { 
            throw e; 
        } 

        return playersList;
    }


    // *****************************************************************
    public  List<PlayerRegClasifGral>  getPlayersClasifGeneralAvg(int season, int jornada) throws Exception {

        List<PlayerRegClasifGral> playersList = new ArrayList<PlayerRegClasifGral>();

        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select player, count(*) Votos, sum(puntos) Ptos, sum(puntos)/count(*) Avg " + 
                                " from players_users_points " +
                                " where season="+season+" and jornada<=" + jornada + " and puntos>=0 "+
                                " group by player order by Avg desc;";

            ResultSet rSet = stmt.executeQuery(query); 

            while (rSet.next()==true) {
                String player = rSet.getString(1);
                int numVotos = rSet.getInt(2);
                int totalPoints = rSet.getInt(3);
                float avg = rSet.getFloat(4); 
                
                PlayerRegClasifGral p = new PlayerRegClasifGral(season, player, totalPoints, numVotos, avg);
                playersList.add(p);
            }

        } catch (Exception e) { 
            throw e; 
        } 
 
        return playersList;
    }
 
 
    // *****************************************************************
    public List<PlayerRegClasifGral>  getPlayersClasifJornadaAvg(int season, int jornada) throws Exception {

        List<PlayerRegClasifGral> playersList = new ArrayList<PlayerRegClasifGral>();

        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select player, count(*) Votos, sum(puntos) Ptos, sum(puntos)/count(*) Avg " + 
                                " from players_users_points " +
                                " where season="+season+" and jornada=" + jornada + " and puntos>=0 "+
                                " group by player order by Avg desc;";

            ResultSet rSet = stmt.executeQuery(query); 

            while (rSet.next()==true) {
                String player = rSet.getString(1);
                int numVotos = rSet.getInt(2);
                int totalPoints = rSet.getInt(3);
                float avg = rSet.getFloat(4); 
                
                PlayerRegClasifGral p = new PlayerRegClasifGral(season, player, totalPoints, numVotos, avg);
                playersList.add(p);
            }

        } catch (Exception e) { 
            throw e; 
        } 
 
        return playersList;
    }


    // *****************************************************************
    public float getPlayerAveragePerVotante(int season, int jornada, String player) throws Exception {
        
        float avg = 0;
        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select count(*) Vot, sum(puntos) Ptos, sum(puntos)/count(*) " + 
                                " from players_users_points " + 
                                " where season="+season+" and jornada<="+ jornada+" and puntos>=0 and player='" + player + "';";

            ResultSet rSet = stmt.executeQuery(query); 

            if (rSet.next()==true) {
                int numVotos = rSet.getInt(1);
                int totalPoints = rSet.getInt(2);
                avg = rSet.getFloat(3); 
            }

        } catch (Exception e) { 
            throw e; 
        }
        
        return avg;
    }


    // *****************************************************************
    public List<String> getUsersJornada(int jornada, int season) throws Exception {

        List<String> usersList = new ArrayList<String>();
        
        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select user, sum(puntos) Ptos " + 
                              " from players_users_points " + 
                              " where jornada="+ jornada+" and season="+season + 
                              " group by user order by Ptos desc;";

            ResultSet rSet = stmt.executeQuery(query); 

            while (rSet.next()==true) { 
                String user = rSet.getString(1); 
                usersList.add(user);
            }

        } catch (Exception e) { 
            throw e; 
        } 

        return usersList;
    }


    // *****************************************************************
    public List<Integer> getPlayerPoints(int season, int jornada, String player) throws Exception {

        List<Integer> playerPointsList = new ArrayList<Integer>();

        try { 
            Statement stmt = conn.createStatement(); 

            String query = " select puntos from players_users_points " + 
                                  " where player= '"+player+"' and jornada="+jornada+" and season="+season+";";

            ResultSet rSet = stmt.executeQuery(query); 
            
            while (rSet.next()==true) {
                int points = rSet.getInt(1);
                playerPointsList.add(points);
            }

        } catch (Exception e) { 
            throw e; 
        } 

        return playerPointsList;
    }


    // *****************************************************************
    public Hashtable<String, PlayerUserPoints> getUserPoints(String user, int jornada, int season) throws Exception {
        
        Hashtable<String, PlayerUserPoints> pupTbl = new Hashtable<String, PlayerUserPoints>();
        
        try { 
            Statement stmt = conn.createStatement(); 

            String query = " select player,puntos  from players_users_points where user= '"+user+"' and jornada="+jornada+" and season="+season+";";

            ResultSet rSet = stmt.executeQuery(query); 
            
            while (rSet.next()==true) {
                String player = rSet.getString(1);
                int puntos = rSet.getInt(2);
                PlayerUserPoints pup = new PlayerUserPoints(season, jornada, user, player, puntos);
                pupTbl.put(player, pup);
                
            }

        } catch (Exception e) { 
            throw e; 
        } 

        return pupTbl;
    }


    // *****************************************************************
    // Returns a List, containing number of users who voted in each
    // weekly votation.
    // *****************************************************************
    public List<Jornada> getInfoJornadas(int season) throws Exception {

        List<Jornada> jornadasList = new ArrayList<Jornada>();

        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select jornada, " +
                                " (select count(*) from players_games where season=" + season + " and jornada=k.jornada) numPlayers, " +
                                " count(*) / (select count(*) from players_games where season=" + season + " and jornada=k.jornada) numVoters " +
                                " from players_users_points k group by jornada;";

            /*
            String query = "select jornada, round(count(*)/14) from players_users_points " +
                                " where season=" + season + " and " + 
                                " jornada in (select jornada from players_games k where season=" + season + " group by jornada) " + 
                                " group by jornada;";*/

            ResultSet rSet = stmt.executeQuery(query); 
            
            while (rSet.next()==true) {
                int jornada = rSet.getInt(1);
                int numPlayers = rSet.getInt(2);
                int numVoters = rSet.getInt(3);
                String game = "";
                String score = "";

                // Get corresponding game 
                Statement stmt2 = conn.createStatement();
                query = "select game, score from games_mcf where season=" + season + " and jornada=" + jornada + ";";
                ResultSet rSet2 = stmt2.executeQuery(query); 
                if (rSet2.next()==true) {
                    game = rSet2.getString(1);
                    score = rSet2.getString(2);
                }

                stmt2.close();

                jornadasList.add(new Jornada(season, jornada, numPlayers, numVoters, game, score));
            }

            stmt.close();

        } catch (Exception e) {
            throw e;
        } 

        return jornadasList;
    }



    // *****************************************************************
    // Returns the maximum number of points than a player could get 
    // if he would played all games.
    // For that, get the number of voters of each week, and multiply
    // then by 10 (the maximum points that a player can received from a
    // voter).
    // *****************************************************************
    public int getMaxPossiblePoints(int season) throws Exception {

        int maxPoints = 0;

        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select jornada, " +
                                " count(*) / (select count(*) from players_games where season=" + season + " and jornada=k.jornada) numVoters " +
                                " from players_users_points k group by jornada;";

            ResultSet rSet = stmt.executeQuery(query); 

            while (rSet.next()==true) {
                int jornada = rSet.getInt(1);
                int numVoters = rSet.getInt(2);
                maxPoints += (numVoters*10);
            }

            stmt.close();

        } catch (Exception e) {
            throw e;
        } 

        return maxPoints;
    }



    // *****************************************************************
    public String getPicture(String player) throws Exception {

        String imageFileName = null;

        try {
            Statement stmt = conn.createStatement();

            String query = "SELECT picture,literal from players_mcf " +
                                " WHERE name='" + player + "';";

            ResultSet res = stmt.executeQuery(query);
            if (res.next()) {
                Blob imagen = res.getBlob(1);
                String literal = res.getString(2);

                if (imagen != null) {
                    InputStream is = imagen.getBinaryStream();

                    String fileName = CONST.PLAYERS_PATH_TMP + "/" + literal +".jpg";
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

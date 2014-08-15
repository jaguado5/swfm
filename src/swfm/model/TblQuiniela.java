package swfm.model;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import swfm.controller.CONST;

/*********************************************************
*                   Clase TblUserResult
*********************************************************/
public class TblQuiniela {

    private transient static final Log log=LogFactory.getLog(TblQuiniela.class);

    private Connection conn;

    // *****************************************************************
    public TblQuiniela(Connection conn) {
        this.conn = conn;
    }


    //******************************************************
    //***************************************************************** 
    public int persistMasterResult(MasterResult mResult) throws Exception { 
        
        int result=0; 
        try { 
            Statement stmt = conn.createStatement(); 

            // Inserta registro en BBDD 
            String query = "INSERT master_result VALUES (" +  mResult.getSeason() + "," +
                                                              mResult.getJornada() + ",'" +
                                                              mResult.getGameResults()[0] + "','" + 
                                                              mResult.getGameResults()[1] + "','" + 
                                                              mResult.getGameResults()[2] + "','" + 
                                                              mResult.getGameResults()[3] + "','" + 
                                                              mResult.getGameResults()[4] + "','" + 
                                                              mResult.getGameResults()[5] + "','" + 
                                                              mResult.getGameResults()[6] + "','" + 
                                                              mResult.getGameResults()[7] + "','" + 
                                                              mResult.getGameResults()[8] + "','" + 
                                                              mResult.getGameResults()[9] + "','" + 
                                                              mResult.getGameResults()[10] + "','" + 
                                                              mResult.getGameResults()[11] + "','" + 
                                                              mResult.getGameResults()[12] + "','" + 
                                                              mResult.getGameResults()[13] + "','" + 
                                                              mResult.getGameResults()[14] + "' ); "; 

          result = stmt.executeUpdate(query); 
          // result debe ser = 1 (1 row affecteds: 1 inserted) 
          
          if (result != 1) 
            throw new Exception(); 

        } catch (Exception e) { 
            throw e; 
        } 

        return result; 
    } 


    //***************************************************************** 
    public int persistUserResult(UserResult usrResult) throws Exception { 
        
        int result=0; 
        try { 
            Statement stmt = conn.createStatement(); 

            // Inserta registro en BBDD 
            String query = "INSERT user_result VALUES (" + usrResult.getSeason() + ",'" + 
                                                              usrResult.getUsr() + "'," + 
                                                              usrResult.getJornada() + ",'" + 
                                                              usrResult.getPrediction()[0] + "','" + 
                                                              usrResult.getPrediction()[1] + "','" + 
                                                              usrResult.getPrediction()[2] + "','" + 
                                                              usrResult.getPrediction()[3] + "','" + 
                                                              usrResult.getPrediction()[4] + "','" + 
                                                              usrResult.getPrediction()[5] + "','" + 
                                                              usrResult.getPrediction()[6] + "','" + 
                                                              usrResult.getPrediction()[7] + "','" + 
                                                              usrResult.getPrediction()[8] + "','" + 
                                                              usrResult.getPrediction()[9] + "','" + 
                                                              usrResult.getPrediction()[10] + "','" + 
                                                              usrResult.getPrediction()[11] + "','" + 
                                                              usrResult.getPrediction()[12] + "','" + 
                                                              usrResult.getPrediction()[13] + "','" + 
                                                              usrResult.getPrediction()[14] + "'," + 
                                                              usrResult.getSuccess() + "," + 
                                                              usrResult.getNumPredictions() + "," +
                                                              usrResult.getPuestoJornada() + "," +
                                                              usrResult.getPuestoTotal() +  ",'" + 
                                                              usrResult.getFlecha() + "'); "; 

          result = stmt.executeUpdate(query); 
          // result debe ser = 1 (1 row affecteds: 1 inserted) 
          
          if (result != 1) 
            throw new Exception(); 

        } catch (Exception e) { 
            throw e; 
        } 

        return result; 
    } 



    //***************************************************************** 
    public int persistSuccess(UserResult usrResult) throws Exception {

        int result=0; 
        try { 
            Statement stmt = conn.createStatement(); 

            int season =  usrResult.getSeason();
            int jornada = usrResult.getJornada();
            String usr = usrResult.getUsr();
            int success = usrResult.getSuccess();
            
            // Inserta registro en BBDD 
            String query = "UPDATE user_result SET success=" + success +
                                    " WHERE season=" + season + " AND user='"+usr+"' AND jornada="+jornada + "; "; 

            result = stmt.executeUpdate(query); 
            // result debe ser = 1 (1 row affecteds: 1 inserted) 

            if (result != 1) 
                throw new Exception(); 

        } catch (Exception e) { 
            throw e; 
        } 

        return result; 
    }



    //***************************************************************** 
    public void persistPuestoTotal(UserResult usrResult) throws Exception { 

        int result=0;
        int season = usrResult.getSeason();
        int jornada = usrResult.getJornada(); 
        String usr = usrResult.getUsr(); 
        int puestoTotal = usrResult.getPuestoTotal(); 
        
        try { 
            Statement stmt = conn.createStatement(); 

            // Comprueba que el usuario efectuó pronóstico para esta jornada; 
            // en caso contrario, añadir registro vacío 
            String query = "select user from user_result where user='" + usr + "' and jornada = "+ jornada; 
            ResultSet rSet = stmt.executeQuery(query); 
            if (rSet.next()==false) { 
                query = "INSERT user_result VALUES (" + season + ",'" + usr + "'," + jornada + 
                                                    ",'-','-','-','-','-','-','-','-','-','-','-','-','-','-','-', 0, 0, 0, 0, 'x'); "; 
                log.info(query); 
                result = stmt.executeUpdate(query); 
                // result debe ser = 1 (1 row affecteds: 1 inserted) 
                if (result != 1) 
                    throw new Exception(); 
            } 

            // -------------- 
            // CAMPO 'puesto' 
            //--------------- 
            // Actualiza campo 'puesto' en el registro llegado en BBDD 
            query = "UPDATE user_result SET puestoTotal=" + puestoTotal + 
                                        " WHERE season=" + season + " AND user='" + usr + "' AND jornada="+ jornada +";"; 
            log.info(query); 
            result = stmt.executeUpdate(query); 
            // result debe ser = 1 (1 row affecteds: 1 inserted) 
            if (result != 1) 
                throw new Exception(); 


            // -------------- 
            // CAMPO 'flecha' 
            //--------------- 
            // Obtener posicion de la jornada anterior 
            int jornadaBefore = jornada -1; 
            int posicionBefore = 9999; 
            query = "select puestoTotal from user_result where season=" + season + " AND user='" + usr + "' and jornada = "+ jornadaBefore; 
            rSet = stmt.executeQuery(query); 
            if (rSet.next()==true) { 
                posicionBefore = rSet.getInt(1); 
            } 

            // Calcular campo 'flecha' 
            String flecha  = "x"; 
            if (posicionBefore>puestoTotal) 
                flecha = "^"; 
            else if (posicionBefore<puestoTotal) 
                flecha = "V"; 
            else 
                flecha = "="; 

            // Actualizar campo 'flecha' en el registro llegado en BBDD 
            query = "UPDATE user_result SET flecha='" + flecha + 
                                        "' WHERE season=" + season + " AND user='" + usr + "' AND jornada="+ jornada +";"; 
            log.info(query); 
            result = stmt.executeUpdate(query); 
            // result debe ser = 1 (1 row affecteds: 1 inserted) 
            if (result != 1) 
                throw new Exception(); 

        } catch (Exception e) { 
            throw e; 
        } 
    } 



    //***************************************************************** 
    public void persistPuestoJornada(int season, int jornada) throws Exception { 

        int result=0; 
        
        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select user, success, " +
                             "(select count(*) from user_result where season=" + season + " and jornada<="+ jornada +" and puestoJornada=1 and user=k.user) TJ " +
                             " from user_result k where season=" + season + " and jornada = "+ jornada + "  order by success desc, TJ desc;";
            
            
            ResultSet rSet = stmt.executeQuery(query); 

            int numPuesto = 0; 
            int numRegistro = 0; 
            int numSuccessWinner = 0; 
            
            while (rSet.next()==true) { 
                numRegistro++; 
                String user = rSet.getString(1); 
                int numSuccess = rSet.getInt(2); 
                
                if (numRegistro==1)
                    numSuccessWinner = numSuccess;
                
                if (numSuccess==numSuccessWinner) {
                    numPuesto = 1; 
                } else {
                    numPuesto = numRegistro;
                }

                log.info("user: "+user + " numSuccess:"+numSuccess+ " puesto: "+numPuesto); 

                query = "UPDATE user_result SET puestoJornada=" + numPuesto + 
                                        " WHERE user='" + user + "' AND season=" + season + " and jornada="+ jornada +";"; 
                log.info(query); 

                Statement stmt2 = conn.createStatement(); 
                result = stmt2.executeUpdate(query); 
                // result debe ser = 1 (1 row affecteds: 1 inserted) 
                if (result != 1) 
                    throw new Exception(); 
            } 

        } catch (Exception e) { 
            throw e; 
        }
    }



    //***************************************************************** 
    public List<UserResult> getClasificacion(int season, int jornada) throws Exception { 

        ArrayList<UserResult> usrListResult = new ArrayList<UserResult>(); 
        
        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select user, sum(success) Total, " +
                                " (select count(*) from user_result where season=" + season + " and jornada<="+ jornada +" and puestoJornada=1 and user=k.user) TJ " +
                                 " from user_result k where season=" + season + " and jornada <= "+ jornada +
                                 " group by user order by Total desc, TJ desc;";
            
            ResultSet rSet = stmt.executeQuery(query); 
            
            int numRegistro = 0; 

            while (rSet.next()==true) { 
                numRegistro++; 
                String user = rSet.getString(1); 
                int numSuccess = rSet.getInt(2); 

                log.info("user: "+user + " numSuccess:"+numSuccess+ " puesto: "+numRegistro); 

                usrListResult.add(new UserResult(season, jornada, user, numRegistro)); 
            }

        } catch (Exception e) { 
            throw e; 
        } 

        return usrListResult; 
    } 



    //***************************************************************** 
    public List<RegClasifGeneral> getClasifGeneral(int season, int jornada) throws Exception {
        
        int antJornada = jornada-1;
        
        ArrayList<RegClasifGeneral> clasifGral = new ArrayList<RegClasifGeneral>(); 
        
        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select  (select puestoTotal from user_result where season="+CONST.SEASON+" and jornada ="+antJornada+" and user=k.user) Anterior, " +
                           "(select flecha from user_result where season="+CONST.SEASON+" and jornada ="+jornada+" and user=k.user) Flecha, " +
                           "(select puestoTotal from user_result where season="+CONST.SEASON+" and jornada ="+jornada+" and user=k.user) Puesto, " +
                           "user Usuario, "+
                           "(select sum(success) from user_result where season="+CONST.SEASON+" and jornada<="+jornada+" and user=k.user) Puntos , " +
                           "(select count(*) from user_result where season="+CONST.SEASON+" and jornada<="+jornada+" and puestoJornada=1 and user=k.user) 'T.J.'," + 
                           "(select count(*) from user_result where season="+CONST.SEASON+" and jornada<="+jornada+" and numPredictions>0 and user=k.user) 'Q.J.', "+
                           "sum(numPredictions) Partidos, "+
                           "sum(success)*100/sum(numPredictions) '%Aciert.', " +
                           "(select sum(success)  from user_result where season="+CONST.SEASON+" and jornada<"+jornada+" and user=k.user) 'P.Anter.' , " + 
                           "(select success from user_result where season="+CONST.SEASON+" and jornada="+jornada+" and user=k.user) 'J.Actual'  ," + 
                           "(select sum(success) from user_result where season="+CONST.SEASON+" and jornada<="+jornada+" and user=k.user) Total " +
                           "from user_result k group by user order by Puesto asc;";


            ResultSet rSet = stmt.executeQuery(query); 
            
            while (rSet.next()==true) {
                int i=1;
                int posAnterior = rSet.getInt(i++);
                String flecha = rSet.getString(i++);
                if (flecha == null)
                    flecha = "?";
                int posActual = rSet.getInt(i++);
                String user = rSet.getString(i++);
                int puntosTotal = rSet.getInt(i++);
                int tj = rSet.getInt(i++);
                int qj = rSet.getInt(i++);
                int partidos = rSet.getInt(i++);
                float porcentaje = rSet.getFloat(i++);
                int puntosAnterior = rSet.getInt(i++);
                int puntosActual = rSet.getInt(i++);
                int total = rSet.getInt(i++);

                RegClasifGeneral rcg = new RegClasifGeneral(posAnterior, flecha.charAt(0), posActual, user, puntosTotal, 
                                                             tj, qj, partidos, porcentaje, puntosAnterior, puntosActual, total);
                
                clasifGral.add(rcg); 
            } 

        } catch (Exception e) { 
            throw e; 
        } 

        return clasifGral; 
    }



    //***************************************************************** 
    public List<RegClasifJornada> getClasifJornada(int jornada) throws Exception {

        ArrayList<RegClasifJornada> clasifJornada = new ArrayList<RegClasifJornada>(); 
        
        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select (select puestoJornada from user_result where jornada="+ jornada +" and user=k.user) Pto, "+ 
                                    "user Usuario, "+ 
                                    "success Aciertos, "+
                                    "(select count(*) from user_result where jornada<="+ jornada +" and puestoJornada=1 and user=k.user) TJ, " +
                                    "g1,g2,g3,g4,g5,g6,g7,g8,g9,g10,g11,g12,g13,g14,g15 " + 
                                    "from user_result k where (jornada="+ jornada + " and numPredictions>0) order by Pto asc ;";

            ResultSet rSet = stmt.executeQuery(query); 
            
            while (rSet.next()==true) {
                int i=1;
                int puesto = rSet.getInt(i++);
                String user = rSet.getString(i++);
                int aciertos = rSet.getInt(i++);
                int tj = rSet.getInt(i++);
                char[] games = new char[15];
                for (int j=0; j<15; j++) {
                    char pronostico = rSet.getString(i++).charAt(0);
                    if (pronostico=='1' || pronostico=='X' || pronostico=='2') {
                        games[j] = pronostico;
                    }
                    else {
                        games[j] = '-';
                    }
                }

                RegClasifJornada rcg = new RegClasifJornada(puesto, user, aciertos, tj, games);
                
                clasifJornada.add(rcg); 
            } 

        } catch (Exception e) { 
            throw e; 
        } 

        return clasifJornada; 
    }


    //***************************************************************** 
    public MasterResult getMasterResult(int season, int jornada) throws Exception { 

        MasterResult masterResult = new MasterResult(season, jornada); 
        
        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select * from master_result  where jornada = "+ jornada + " ;"; 
            
            ResultSet rSet = stmt.executeQuery(query); 
            if (rSet.next()==true) {
                int i =3;
                char[] games = new char[15];
                for (int j=0; j<15; j++) {
                    games[j] = rSet.getString(i++).charAt(0);
                    masterResult.setGame(j, games[j]);
                }
            } 
        } catch (Exception e) { 
            throw e; 
        } 

        return masterResult; 
    } 


    //***************************************************************** 
    public List<UserResult> getPlayersPredicions(int season, int jornada) throws Exception { 

        ArrayList<UserResult> usrListResult = new ArrayList<UserResult>(); 
        
        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select * from user_result  where season=" + season + " and jornada = "+ jornada +" ;"; 
            
            ResultSet rSet = stmt.executeQuery(query);

            while (rSet.next()==true) {

                int i=1;
                int tmpSeason = rSet.getInt(i++);
                String user = rSet.getString(i++);
                int tmpJornada = rSet.getInt(i++);

                char prediction[] = new char[15];
                for (int j=0; j<prediction.length; j++) {
                    prediction[j] = rSet.getString(i++).charAt(0);
                }

                usrListResult.add(new UserResult(season, jornada, user, prediction));
            }


        } catch (Exception e) { 
            throw e;
        }

        return usrListResult; 
    } 


    // *****************************************************************
    // Returns a List, containing number of users who played in each
    // weekly pool and winners.
    // *****************************************************************
    public List<Jornada> getInfoJornadas(int season) throws Exception {

        List<Jornada> jornadasList = new ArrayList<Jornada>();

        try { 
            Statement stmt = conn.createStatement(); 

            String query = "select jornada, count(*) from user_result where season=" + season + " and numPredictions>0 group by jornada;";
            ResultSet rSet = stmt.executeQuery(query); 

            while (rSet.next()==true) {
                int jornada = rSet.getInt(1);
                int qNumUsers = rSet.getInt(2);

                // Get corresponding winners 
                Statement stmt2 = conn.createStatement();
                query = " select user from user_result where season=" + season + " and jornada=" + jornada + " and puestoJornada=1;";
                ResultSet rSet2 = stmt2.executeQuery(query);
                String qWinners = "";

                while (rSet2.next()==true) {
                    qWinners += rSet2.getString(1);

                    if (rSet2.isLast()==false) {
                        qWinners += ", ";
                    }
                }

                stmt2.close();

                jornadasList.add(new Jornada(season, jornada, qNumUsers, qWinners));
            }

            stmt.close();

        } catch (Exception e) {
            throw e;
        } 

        return jornadasList;
    }



}

package swfm.view;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import swfm.controller.CONST;
import swfm.controller.Dispatcher;
import swfm.controller.MultipartForm;
import swfm.controller.Util;
import swfm.model.GameMcf;
import swfm.model.PlayerGame;
import swfm.model.PlayerRegClasifGral;
import swfm.model.PlayerUserPoints;
import swfm.model.TblGames;
import swfm.model.TblPlayers;



/*************************************************************************/
//                         Class BestPlayerDispClasifJornadaServlet
/*************************************************************************/
public class BestPlayerDispClasifJornadaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private transient static final Log log=LogFactory.getLog(Dispatcher.class);

    private TblPlayers tblPlayersMng;


    //******************************************************
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //Get session parameters
        HttpSession session = request.getSession();
        Connection conn = (Connection)session.getAttribute("mysqlConn");
        String pageType = (String) session.getAttribute("pageType");

        // Get form parameters 
        MultipartForm mform = (MultipartForm)request.getAttribute("form"); 
        String jornadaStr = mform.getParamValue("jornada");
        log.info("(BestPlayerDispClasifJornadaServlet/doPost) jornada:" + jornadaStr);

        try {
            tblPlayersMng = new TblPlayers(conn);
            TblGames tblGames = new TblGames(conn);
            int jornada = Integer.parseInt(jornadaStr);

            // List of registers for the View
            List<BestPlayerClasifJornadaRegPlayer> playerRegList = new ArrayList<BestPlayerClasifJornadaRegPlayer>(); 
            List<BestPlayerClasifJornadaRegUser> bodyRegList = new ArrayList<BestPlayerClasifJornadaRegUser>(); 

            // Get players who played that game
            List<PlayerGame> playersList = tblPlayersMng.getPlayersJornada(jornada, CONST.SEASON);

            // ------------------------------------------------------------
            // Build User Register to send to view (First Body of the View)
            // ------------------------------------------------------------

            // Get users who voted for that game
            List<String> usersList = tblPlayersMng.getUsersJornada(jornada, CONST.SEASON);
            
            // Get points given for each user
            Iterator<String> itUsr = usersList.iterator();
            int puesto = 1;
            while (itUsr.hasNext()) {

                // Get user name
                String user = itUsr.next();
                Hashtable<String, PlayerUserPoints> pupTbl = tblPlayersMng.getUserPoints(user, jornada, CONST.SEASON);


                String[] puntosStr = new String[14];
                int index=0;
                float totalPuntos = 0;
                float numPlayer = 0;
                Iterator<PlayerGame> itPlayer = playersList.iterator();
                while (itPlayer.hasNext()) {
                    String player = itPlayer.next().getPlayer();
                    PlayerUserPoints pup = pupTbl.get(player);
                    
                    if (pup!=null) {
                        int playerPoint = pup.getPuntos();
                        
                        if (playerPoint>=0) {
                            puntosStr[index++] = playerPoint + "";
                            totalPuntos += pup.getPuntos();
                            numPlayer++;
                        }
                        else {
                            puntosStr[index++] = "sc";
                        }
                    }
                    // User didn't vote for that player
                    else {
                        puntosStr[index++] = "nv";
                    }
                    
                }

                String average = (totalPuntos/numPlayer) + "";
                average += "00000";
                
                BestPlayerClasifJornadaRegUser regView = new BestPlayerClasifJornadaRegUser(puesto++, user, puntosStr, totalPuntos+"", average.substring(0,4)); 

                bodyRegList.add(regView);
            }


            // ---------------------------------------------------------
            // Build Player Register to send to view (Header of the View)
            // ---------------------------------------------------------
            int teamTotalPoints = 0;
            float teamAverage = 0;
            
            Iterator<PlayerGame> itPlayer = playersList.iterator();
            
            while (itPlayer.hasNext()) {
                PlayerGame pg = itPlayer.next();
                String player = pg.getPlayer();
                int totalPoints = pg.getTotalPoints();
                String average = pg.getAverage()+"00000";

                teamTotalPoints += totalPoints;
                teamAverage += pg.getAverage();
                
                BestPlayerClasifJornadaRegPlayer regPlayer = new BestPlayerClasifJornadaRegPlayer(player, totalPoints, average.substring(0,4));
                playerRegList.add(regPlayer);
            }
            
            String teamTotalPointsStr = teamTotalPoints + "";
            String teamAverageStr = (teamAverage / playersList.size()) + "0000";

            // ---------------------------------------------------------
            // Build game
            // ---------------------------------------------------------
            GameMcf gameMcf = tblGames.getUsersJornada(CONST.SEASON, jornada);



            // ------------------------------------------------------------
            // Build Player Classification of the Game ordering the players 
            // by the vote average (Second Body of the View)
            // ------------------------------------------------------------
            List<BestPlayerClasifGeneralRegPlayer> playerClasifOrderByAvg = buildPlayerClasifGameAvg(jornada);


            // ------------------------------------------------------------
            // Build Player Classification of the Game ordering the players 
            // by total number of votes (Rhird Body of the View)
            // ------------------------------------------------------------
            //buildPlayerClasifGamePtos();


            // ---------------------------------------------------------
            //Send all built elements to the View
            // ---------------------------------------------------------
            RequestDispatcher view = request.getRequestDispatcher(pageType+"bpDispClasifJornada.jsp");
            session.setAttribute("header", Util.generateRandomHeader());
            session.setAttribute("jornada", jornada);
            session.setAttribute("gameMcf", gameMcf);
            session.setAttribute("teamTotalPoints", teamTotalPointsStr);
            session.setAttribute("teamAverage", teamAverageStr.substring(0,4));
            session.setAttribute("playerRegList", playerRegList);
            session.setAttribute("bodyRegList", bodyRegList);
            session.setAttribute("playerClasifOrderByAvg", playerClasifOrderByAvg);
            view.forward(request, response);


        } catch (Exception e) {
            log.error("(BestPlayerDispClasifJornadaServlet/doPost) exception: <"+e.getMessage()+">");
            request.setAttribute("errorMsg", "Error inesperado: " + e.getMessage()); 
            request.setAttribute("recomen", e.getMessage()); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
            view.forward(request, response); 

        }
    }


    // *****************************************************************
    // Build Player Classification order by Avgerage
    // *****************************************************************
    List<BestPlayerClasifGeneralRegPlayer> buildPlayerClasifGameAvg(int jornada) throws Exception {

        List<BestPlayerClasifGeneralRegPlayer> playerListOrderByAvg = new ArrayList<BestPlayerClasifGeneralRegPlayer>(); 
                    
        List<PlayerRegClasifGral> playerModelList = tblPlayersMng.getPlayersClasifJornadaAvg(CONST.SEASON, jornada);

        Iterator<PlayerRegClasifGral> itPlayer = playerModelList.iterator();

        while (itPlayer.hasNext()) {
            PlayerRegClasifGral p = itPlayer.next();

            String player = p.getPlayer();
            String picture = tblPlayersMng.getPicture(player);
            int totalPoints = p.getTotalPoints();
            int numVotos = p.getNumVotos();
            int numJornadas = 1;
            float avg = p.getAverage();

            BestPlayerClasifGeneralRegPlayer regPlayer = new BestPlayerClasifGeneralRegPlayer(player, picture, totalPoints, numJornadas, 
                                                                    numVotos, (avg+"000").substring(0,5));

            playerListOrderByAvg.add(regPlayer);
        }

        return playerListOrderByAvg;
    }


}
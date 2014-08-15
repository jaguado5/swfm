
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
import swfm.model.PlayerGame;
import swfm.model.PlayerRegClasifGral;
import swfm.model.PlayerUserPoints;
import swfm.model.TblPlayers;



/*************************************************************************/
//                         Class BestPlayerDispClasifGeneralServlet
/*************************************************************************/
public class BestPlayerDispClasifGeneralServlet extends HttpServlet {

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
        log.info("(BestPlayerDispClasifGeneralServlet/doPost) jornada:" + jornadaStr);

        try {
            tblPlayersMng = new TblPlayers(conn);
            int season = CONST.SEASON;
            int jornada = Integer.parseInt(jornadaStr);

            // List of registers from the Model
            List<PlayerRegClasifGral> playerModelList = new ArrayList<PlayerRegClasifGral>(); 

            // List of registers for the View
            List<BestPlayerClasifGeneralRegPlayer> playerListOrderByPoints = new ArrayList<BestPlayerClasifGeneralRegPlayer>(); 
            List<BestPlayerClasifGeneralRegPlayer> playerListOrderByAvg = new ArrayList<BestPlayerClasifGeneralRegPlayer>(); 
            List<BestPlayerClasifGeneralRegPlayer> playerListPondered = new ArrayList<BestPlayerClasifGeneralRegPlayer>(); 


            // ---------------------------------------------------------
            // Build Classification order by Points 
            // ---------------------------------------------------------

            // Get players who played any game
            playerModelList = tblPlayersMng.getPlayersClasifGeneralTotalPoints(CONST.SEASON, jornada);

            Iterator<PlayerRegClasifGral> itPlayer = playerModelList.iterator();
            
            while (itPlayer.hasNext()) {
                PlayerRegClasifGral p = itPlayer.next();

                String player = p.getPlayer();
                String picture = tblPlayersMng.getPicture(player);
                int totalPoints = p.getTotalPoints();
                int numJornadas = p.getNumJornadas();
                int numVotos = tblPlayersMng.getPlayerNumVotosUpto(season, jornada, player);

                float avgJornada = (float)totalPoints/numJornadas;
                String avgJornadaStr = avgJornada +"00000";

                float avgVotante = tblPlayersMng.getPlayerAveragePerVotante(season, jornada, player);

                BestPlayerClasifGeneralRegPlayer regPlayer = new BestPlayerClasifGeneralRegPlayer(player, picture, totalPoints, numJornadas, numVotos,
                                                                    avgJornadaStr.substring(0,6), (avgVotante+"000").substring(0,5));

                playerListOrderByPoints.add(regPlayer);
            }


            // ---------------------------------------------------------
            // Build Classification order by Average 
            // ---------------------------------------------------------
            playerModelList = tblPlayersMng.getPlayersClasifGeneralAvg(season, jornada);

            itPlayer = playerModelList.iterator();

            while (itPlayer.hasNext()) {
                PlayerRegClasifGral p = itPlayer.next();

                String player = p.getPlayer();
                String picture = tblPlayersMng.getPicture(player);
                int totalPoints = p.getTotalPoints();
                int numVotos = p.getNumVotos();
                int numJornadas = tblPlayersMng.getPlayerNumJornadasUpto(season, jornada, player);
                float avg = p.getAverage();

                BestPlayerClasifGeneralRegPlayer regPlayer = new BestPlayerClasifGeneralRegPlayer(player, picture, totalPoints, numJornadas, 
                                                                    numVotos, (avg+"000").substring(0,5));

                playerListOrderByAvg.add(regPlayer);
            }


            // ---------------------------------------------------------
            // Build Ponderated Classification
            // ---------------------------------------------------------
            playerListPondered = buildPonderatedClasif(season, jornada);


            // ---------------------------------------------------------
            //Send all built elements to the View
            // ---------------------------------------------------------
            session.setAttribute("header", Util.generateRandomHeader());
            session.setAttribute("jornada", jornada);
            session.setAttribute("playerListPondered", playerListPondered);
            session.setAttribute("playerListOrderByAvg", playerListOrderByAvg);
            session.setAttribute("playerListOrderByPoints", playerListOrderByPoints);

            // Forward JSP to the View
            RequestDispatcher view = request.getRequestDispatcher(pageType+"bpDispClasifGeneral.jsp");
            view.forward(request, response);
            
            

        } catch (Exception e) {
            log.error("(BestPlayerDispClasifGeneralServlet/doPost) exception: <"+e.getMessage()+">");
            request.setAttribute("errorMsg", "Error inesperado: " + e.getMessage()); 
            request.setAttribute("recomen", e.getMessage()); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
            view.forward(request, response); 

        }
    }



    //******************************************************
    // Build Ponderated Classification
    //******************************************************
    private List<BestPlayerClasifGeneralRegPlayer> buildPonderatedClasif(int season, int jornada)  throws Exception {

        List<BestPlayerClasifGeneralRegPlayer> playerList = new ArrayList<BestPlayerClasifGeneralRegPlayer>(); 

        double maxPossiblePoints = tblPlayersMng.getMaxPossiblePoints(CONST.SEASON);

        List<PlayerRegClasifGral> playerModelList = tblPlayersMng.getPlayersClasifGeneralAvg(season, jornada);
        Iterator<PlayerRegClasifGral> itPlayer = playerModelList.iterator();

        while (itPlayer.hasNext()) {
            PlayerRegClasifGral p = itPlayer.next();

            String player = p.getPlayer();
            String picture = tblPlayersMng.getPicture(player);
            int numJornadas = tblPlayersMng.getPlayerNumJornadasUpto(CONST.SEASON, jornada, player);
            int totalPoints = p.getTotalPoints();
            int numVotos = p.getNumVotos();
            float avg = p.getAverage();

            double promedio1 = avg*0.5;
            double promedio2 = (totalPoints/maxPossiblePoints)*5;
            double totalPromedio = promedio1 + promedio2;

            BestPlayerClasifGeneralRegPlayer regPlayer = new BestPlayerClasifGeneralRegPlayer(player, picture, totalPoints, numJornadas,
                                                                    numVotos,
                                                                    (avg+"000").substring(0,5),
                                                                    (promedio1+"000").substring(0,5),
                                                                    (promedio2+"000").substring(0,5),
                                                                    (totalPromedio+"000").substring(0,5));

            playerList.add(regPlayer);

            System.out.println(player + "/ Avg:" + avg + "/ Points:" + totalPoints + "/ Prom1:" + promedio1 + "/ Prom2:"+ promedio2 + "/ Total:" + totalPromedio);
        }

        // Sort list by totalPromedio field
        Object[] playerArray = sort(playerList.toArray());

        // Convert Object[] to List
        List<BestPlayerClasifGeneralRegPlayer> playerListOrdered = new ArrayList<BestPlayerClasifGeneralRegPlayer>(); 
        for (int i=0; i<playerArray.length; i++) {
            BestPlayerClasifGeneralRegPlayer p = (BestPlayerClasifGeneralRegPlayer) playerArray[i];
            playerListOrdered.add(p);
        }

        return playerListOrdered;
    }



    //******************************************************
    // Order input list by totalPromedio field. 
    //******************************************************
    private Object[] sort(Object[] list) {

        for (int i=0; i<list.length-1; i++) {


            for (int j=i+1; j<list.length; j++) {
                BestPlayerClasifGeneralRegPlayer reg1 = (BestPlayerClasifGeneralRegPlayer) list[i];
                BestPlayerClasifGeneralRegPlayer reg2 = (BestPlayerClasifGeneralRegPlayer) list[j];

                double total1 = Double.parseDouble(reg1.getTotalPromedio());
                double total2 = Double.parseDouble(reg2.getTotalPromedio());

                if (total2 > total1) {
                    BestPlayerClasifGeneralRegPlayer tmp = (BestPlayerClasifGeneralRegPlayer) list[i];
                    list[i] = list[j];
                    list[j] = tmp;
                }
            }
        }

        return list;
    }


}
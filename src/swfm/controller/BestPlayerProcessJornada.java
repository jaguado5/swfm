package swfm.controller;

import java.io.IOException;
import java.sql.Connection;
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

import swfm.model.PlayerGame;
import swfm.model.TblPlayers;

/*************************************************************************/
//                         Class BestPlayerProcessJornada
/*************************************************************************/
public class BestPlayerProcessJornada extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private transient static final Log log=LogFactory.getLog(BestPlayerProcessJornada.class);


    //******************************************************
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //Get session parameters
        HttpSession session = request.getSession();
        Connection conn = (Connection)session.getAttribute("mysqlConn");
        String pageType = (String) session.getAttribute("pageType");

        // Get form parameters 
        MultipartForm mform = (MultipartForm)request.getAttribute("form"); 
        String jornadaStr = mform.getParamValue("jornada");
        log.info("(BestPlayerProcessJornada/doPost) jornada:" + jornadaStr);

        try {
            int jornada = Integer.parseInt(jornadaStr);
            TblPlayers tblPlayersMng = new TblPlayers(conn);

            // -----------------------------------------
            // Calcular y Persistir Total Puntos y Porcentaje 
            // de cada Jugador de la Jornada a partir de las
            // votaciones de los usuarios
            // -----------------------------------------
            
            // Obtener players de la jornada
            List<PlayerGame> playersList = tblPlayersMng.getPlayersJornada(jornada, CONST.SEASON);

            // Para cada player, obtener las puntuaciones otorgadas por todos los usuarios
            Iterator<PlayerGame> it = playersList.iterator(); 
            while (it.hasNext()) {
                
                // Obtener nombre del player
                PlayerGame pg =  it.next();
                String player = pg.getPlayer();

                // Obtener sus puntuaciones
                List<Integer> playerPointsList = tblPlayersMng.getPlayerPoints(CONST.SEASON, jornada, player);
                
                // Sumarlas y obtener porcentaje (no sumar puntuaciones='-1'
                int totalPoints = 0;
                float average = 0;
                int numVotos = 0;
                Iterator<Integer> itPoints = playerPointsList.iterator(); 
                while (itPoints.hasNext()) {
                    int points = itPoints.next();
                    if (points>=0) {
                        totalPoints += points;
                        numVotos++;
                    }
                }

                if (playerPointsList.size()>0 && numVotos>0) {
                    average = (float)totalPoints/numVotos;
                }
                PlayerGame pg2 = new PlayerGame(CONST.SEASON, jornada, player, totalPoints, average);

                // Persist totalPoints & average
                tblPlayersMng.persistPlayerPoints(pg2);
            }

            //Send all registers found to view
            RequestDispatcher view = request.getRequestDispatcher(pageType + "OperationOk.jsp");
            request.setAttribute("infoMsg", "Best Player; Jornada Procesada correctamente.");
            view.forward(request, response);

        } catch (Exception e) {
            log.error("(BestPlayerProcessJornada/doPost) exception: <"+e.getMessage()+">");
            request.setAttribute("errorMsg", "Error inesperado: " + e.getMessage()); 
            request.setAttribute("recomen", e.getMessage()); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp");
            view.forward(request, response);
        }

    }


}

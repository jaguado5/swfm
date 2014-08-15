package swfm.view;

import java.io.IOException;
import java.sql.Connection;
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
import swfm.controller.MultipartForm;
import swfm.controller.Util;
import swfm.model.Game;
import swfm.model.MasterResult;
import swfm.model.RegClasifJornada;
import swfm.model.TblGames;
import swfm.model.TblQuiniela;

public class QuinielaDispClasifJornadaServlet extends HttpServlet {



    private static final long serialVersionUID = 1L;

    private transient static final Log log=LogFactory.getLog(QuinielaDispClasifJornadaServlet.class);


    //******************************************************
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //Get session parameters
        HttpSession session = request.getSession();
        Connection conn = (Connection)session.getAttribute("mysqlConn");
        String pageType = (String) session.getAttribute("pageType");
        int season = CONST.SEASON;


        // Get form parameters 
        MultipartForm mform = (MultipartForm)request.getAttribute("form"); 
        String jornadaStr = mform.getParamValue("jornada");
        log.info("(QuinielaDispClasifJornadaServlet/doPost) jornada:" + jornadaStr);

        try {
            int jornada = Integer.parseInt(jornadaStr);
            TblQuiniela tblUserResultMng = new TblQuiniela(conn);
            TblGames tblGamesMng = new TblGames(conn);

            List<RegClasifJornada> clasifJornada = tblUserResultMng.getClasifJornada(jornada);

            // Get resuls of the day
            MasterResult mr = tblUserResultMng.getMasterResult(season, jornada);

            // Get games list of the day
            List<Game> gamesList = tblGamesMng.getGamesList(season, jornada);


            //Send all registers found to view
            RequestDispatcher view = request.getRequestDispatcher(pageType+"qDispClasifJornada.jsp");
            session.setAttribute("header", Util.generateRandomHeader());
            session.setAttribute("jornada", jornada);
            session.setAttribute("clasifJornada", clasifJornada);
            session.setAttribute("masterResult", mr);
            session.setAttribute("gamesList", gamesList);
            view.forward(request, response);

        } catch (Exception e) {
            log.error("(QuinClasifJornadaServlet/doPost) exception: <"+e.getMessage()+">");
            request.setAttribute("errorMsg", "Error inesperado: " + e.getMessage()); 
            request.setAttribute("recomen", e.getMessage()); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
            view.forward(request, response);
        }

    }


}

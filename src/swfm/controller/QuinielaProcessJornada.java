//*****************************************************************
// SWFM: Servicios Web de FMS.
//
// Autor:                     Juan José Aguado
// Fecha creación:            01/Sep/2013
// Fecha última modificación: 15/Oct/2013
//
// clase: QuinielaProcessJornada
//
//*****************************************************************

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

import swfm.model.MasterResult;
import swfm.model.TblQuiniela;
import swfm.model.UserResult;


/*************************************************************************/
//                         Class QuinielaProcessJornada
/*************************************************************************/
public class QuinielaProcessJornada extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private transient static final Log log=LogFactory.getLog(QuinielaProcessJornada.class);


    //******************************************************
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //Get session parameters
        HttpSession session = request.getSession();
        Connection conn = (Connection)session.getAttribute("mysqlConn");
        String pageType = (String)session.getAttribute("pageType");
        int season = CONST.SEASON;


        // Get form parameters 
        MultipartForm mform = (MultipartForm)request.getAttribute("form"); 
        String jornadaStr = mform.getParamValue("jornada");
        log.info("(QuinielaProcessJornada/doPost) jornada:" + jornadaStr);

        try {
            int jornada = Integer.parseInt(jornadaStr);
            TblQuiniela tblUserResultMng = new TblQuiniela(conn);

            // -----------------------------------------
            // Calcular y Persistir Aciertos de cada 
            // Usuario de la Jornada
            // -----------------------------------------

            // Obtener masterResult de la jornada
            MasterResult mResult = tblUserResultMng.getMasterResult(season, jornada);

            //Obtener y persistir aciertos de cada usuario
            List<UserResult> usrResultList = tblUserResultMng.getPlayersPredicions(season, jornada);
            Iterator<UserResult> it = usrResultList.iterator(); 
            while (it.hasNext()) { 
                UserResult usrResult =  it.next();
                usrResult.computeSuccess(mResult);

                tblUserResultMng.persistSuccess(usrResult);
            }

            // -----------------------------------------
            // Calcular y Persistir Puesto de la Jornada
            // -----------------------------------------
            tblUserResultMng.persistPuestoJornada(season, jornada);

            // -----------------------------------------
            // Calcular Clasificacion General tras 
            // disputar la Jornada
            // -----------------------------------------
            usrResultList = tblUserResultMng.getClasificacion(season, jornada); 

            // -----------------------------------------
            // Persistir Puesto de la General
            // -----------------------------------------
            it = usrResultList.iterator(); 
            while (it.hasNext()) { 
                UserResult usrResult =  it.next(); 
                tblUserResultMng.persistPuestoTotal(usrResult); 
            }

            // -----------------------------------------
            // Send operation result to the View
            // -----------------------------------------
            request.setAttribute("title1", "Juego de la Quiniela");
            request.setAttribute("title2", "Temporada 2013/14");
            request.setAttribute("infoMsg", "Jornada " + jornada + " Procesada correctamente.");
            request.setAttribute("detailMsg", "");
            RequestDispatcher view = request.getRequestDispatcher(pageType + "OperationOk.jsp");
            view.forward(request, response);

        } catch (Exception e) {
            log.error("(QuinielaProcessJornada/doPost) exception: <"+e.getMessage()+">");
            request.setAttribute("errorMsg", "An expected error: " + e.getMessage()); 
            request.setAttribute("recomen", ""); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
            view.forward(request, response);
        }

    }


}

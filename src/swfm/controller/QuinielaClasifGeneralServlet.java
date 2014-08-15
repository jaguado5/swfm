//*****************************************************************
// SWBD: Servicios Web de Base de Datos.
//
// Autor:                     Juan Jos� Aguado
// Fecha creaci�n:            09/Jul/2010
// Fecha �ltima modificaci�n: 27/Jul/2010
//
// clase: QuinClasifGeneralServlet
//
//*****************************************************************

package swfm.controller;

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

import swfm.model.RegClasifGeneral;
import swfm.model.TblQuiniela;


/*************************************************************************/
//                         Class QuinClasifGeneralServlet
/*************************************************************************/
public class QuinielaClasifGeneralServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private transient static final Log log=LogFactory.getLog(QuinielaClasifGeneralServlet.class);


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
        log.info("(QuinClasifGeneralServlet/doPost) jornada:" + jornadaStr);

        try {
            int jornada = Integer.parseInt(jornadaStr);
            TblQuiniela tblUserResultMng = new TblQuiniela(conn);
            List<RegClasifGeneral> clasifGral = tblUserResultMng.getClasifGeneral(season, jornada);

            //Send all registers found to view
            RequestDispatcher view = request.getRequestDispatcher(pageType + "qDispClasifGral.jsp");
            session.setAttribute("header", Util.generateRandomHeader());
            session.setAttribute("jornada", jornada);
            session.setAttribute("clasifGral", clasifGral);
            view.forward(request, response);

        } catch (Exception e) {
            String errorMsg = e.getMessage();
            log.error("(QuinClasifJornadaServlet/doPost) exception: <"+e.getMessage()+">");
            request.setAttribute("errorMsg", errorMsg);
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp");
            view.forward(request, response);
        }

    }


}

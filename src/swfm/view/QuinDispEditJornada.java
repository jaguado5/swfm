//*****************************************************************
// SWBD: Servicios Web de Base de Datos.
//
// Autor:                     Juan José Aguado
// Fecha creación:            09/Jul/2010
// Fecha última modificación: 27/Jul/2010
//
// clase: QuinDispJornada
//
//*****************************************************************

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

import swfm.controller.MultipartForm;
import swfm.model.RegClasifGeneral;
import swfm.model.TblQuiniela;


/*************************************************************************/
//                         Class QuinDispJornada
/*************************************************************************/
public class QuinDispEditJornada extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private transient static final Log log=LogFactory.getLog(QuinDispEditJornada.class);


    //******************************************************
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //Get session parameters
        HttpSession session = request.getSession();
        Connection conn = (Connection)session.getAttribute("mysqlConn");

        // Get form parameters 
        MultipartForm mform = (MultipartForm)request.getAttribute("form"); 
        String jornadaStr = mform.getParamValue("jornada");
        log.info("(QuinDispJornada/doPost) jornada:" + jornadaStr);

        try {
            int jornada = Integer.parseInt(jornadaStr);
            TblQuiniela tblUserResultMng = new TblQuiniela(conn);

            //Send all registers found to view
            RequestDispatcher view = request.getRequestDispatcher("qDispEditJornada.jsp");
            session.setAttribute("jornada", jornada);
            view.forward(request, response);

        } catch (Exception e) {
            String errorMsg = e.getMessage();
            log.error("(QuinDispJornada/doPost) exception: <"+e.getMessage()+">");
            RequestDispatcher view = request.getRequestDispatcher("UsrError.jsp");
            request.setAttribute("errorMsg", errorMsg);
            view.forward(request, response);
        }

    }


}

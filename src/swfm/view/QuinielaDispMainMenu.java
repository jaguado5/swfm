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
import swfm.controller.Util;
import swfm.model.Jornada;
import swfm.model.TblQuiniela;


public class QuinielaDispMainMenu extends HttpServlet {



    private static final long serialVersionUID = 1L;

    private transient static final Log log=LogFactory.getLog(QuinielaDispMainMenu.class);


    //******************************************************
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //Get session parameters
        HttpSession session = request.getSession();
        Connection conn = (Connection)session.getAttribute("mysqlConn");
        String pageType = (String) session.getAttribute("pageType");

        try {
            TblQuiniela tblQuinielaMng = new TblQuiniela(conn);

            // Get general info stored in BD about weekly pools 
            List<Jornada> jornadasList = tblQuinielaMng.getInfoJornadas(CONST.SEASON); 

            //Send all registers found to view
            session.setAttribute("header", Util.generateRandomHeader());
            session.setAttribute("jornadasList", jornadasList);

            // Forward JSP to the View
            RequestDispatcher view = request.getRequestDispatcher(pageType+"qMenu.jsp");
            view.forward(request, response);


        } catch (Exception e) {
            log.error("(QuinielaDispMainMenu/doPost) exception: <"+e.getMessage()+">");
            request.setAttribute("errorMsg", "Error inesperado: " + e.getMessage()); 
            request.setAttribute("recomen", e.getMessage()); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
            view.forward(request, response);
        }

    }


}

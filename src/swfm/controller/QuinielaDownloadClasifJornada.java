//*****************************************************************
// Autor:                     Juan Jose Aguado
// Fecha creacion:            30/Jun/2010
// Fecha última modificacion: 14/Oct/2013
//
// clase: QuinielaDownloadClasifJornada
//
//*****************************************************************

package swfm.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import swfm.model.MasterResult;
import swfm.model.RegClasifJornada;


/*************************************************************************/
//                         Class QuinielaDownloadClasifJornada
/*************************************************************************/
public class QuinielaDownloadClasifJornada extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private transient static final Log log=LogFactory.getLog(QuinielaDownloadClasifJornada.class);
    
    private String SEPARATOR = "|";


    /*
    �* This Method Handles Post
    �*
    �* @param HttpServletRequest request
    �* @param HttpServletResponse response
    �*/
    public void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
        log.info("(DownloadFileToClient/doPost) :");
        doGet(request, response);
    }



    /*
     * This Method Handles Get
     *
     * @param HttpServletRequest request
     * @param HttpServletResponse response
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Get session parameters
        HttpSession session = request.getSession();
        String pageType = (String) session.getAttribute("pageType");

        try {

            // More session parameters
            List<RegClasifJornada> clasifJornada = (List<RegClasifJornada>)session.getAttribute("clasifJornada");
            MasterResult masterResult = (MasterResult) session.getAttribute("masterResult");
            int jornada = (Integer) session.getAttribute("jornada");
            String fileType = (String) session.getAttribute("fileType");
            
            // Build Master Header
            String txt = "";
            txt += "+-------+----------------------+----------+------+----+----+----+----+----+----+----+----+----+----+----+----+----+----+----+\n";
            txt += "|                                                | P1 | P2 | P3 | P4 | P5 | P6 | P7 | P8 | P9 |P10 |P11 |P12 |P13 |P14 |P15 |\n";
            txt += "+-------+----------------------+----------+------+----+----+----+----+----+----+----+----+----+----+----+----+----+----+----+\n";
            txt += "|                                                |";
            for (int i=0; i<15; i++) {
                txt += "  "+ masterResult.getGame(i)+" |";
            }
            txt += "\n";

            // Build User Header
            txt += "+-------+----------------------+----------+------+----+----+----+----+----+----+----+----+----+----+----+----+----+----+----+\n";
            txt += "|Puesto | Usuario              | Aciertos | T.J. | P1 | P2 | P3 | P4 | P5 | P6 | P7 | P8 | P9 |P10 |P11 |P12 |P13 |P14 |P15 |\n";
            txt += "+-------+----------------------+----------+------+----+----+----+----+----+----+----+----+----+----+----+----+----+----+----+\n";

            // Get info to download
            Iterator<RegClasifJornada> it = clasifJornada.iterator();
            while (it.hasNext()) {
                
                RegClasifJornada rcj = it.next();
                
                txt += Util.formatField(rcj.getPuesto()+"", 7, "left") + SEPARATOR;
                txt += Util.formatField(rcj.getUser()+"", 21, "right") + SEPARATOR;
                txt += Util.formatField(rcj.getAciertos()+"", 9, "left") + SEPARATOR;
                txt += Util.formatField(rcj.getTj()+"", 5, "left") + SEPARATOR;
                for (int i=0; i<15; i++) {
                    txt += Util.formatField(rcj.getGame(i)+"", 3, "left") + SEPARATOR;
                }
                txt += "\n";
            }
            txt += "+-------+----------------------+----------+------+----+----+----+----+----+----+----+----+----+----+----+----+----+----+----+\n";
            txt +=  " \n";
            txt +=  "\n";

            //Prepare output stream
            response.reset();
            ServletOutputStream sOutStream = response.getOutputStream();
            if (fileType.compareTo("txt")==0)
                Util.streamBinaryData(txt, jornada, sOutStream, response);
            else
                Util.streamBinaryData(txt, jornada, sOutStream, response);

        } catch (Exception e) {
            String errorMsg = e.getMessage();
            log.info("(QuinielaDownloadClasifJornada/doPost) exception: <"+e.getMessage()+">");
            request.setAttribute("errorMsg", errorMsg+ " Fichero no encontrado.");
            request.setAttribute("recomen", ""); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
            view.forward(request, response);
        }
    }


}

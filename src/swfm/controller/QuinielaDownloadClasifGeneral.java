//*****************************************************************
// Autor:                     Juan Jose Aguado
// Fecha creacion:            30/Jun/2010
// Fecha última modificacion: 14/Oct/2013
//
// clase: QuinielaDownloadClasifGeneral
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

import swfm.model.RegClasifGeneral;


/*************************************************************************/
//                         Class QuinielaDownloadClasifGeneral
/*************************************************************************/
public class QuinielaDownloadClasifGeneral extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private transient static final Log log=LogFactory.getLog(QuinielaDownloadClasifGeneral.class);
    
    private String SEPARATOR = "|";


    /*
    �* This Method Handles Post
    �*
    �* @param HttpServletRequest request
    �* @param HttpServletResponse response
    �*/
    public void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
        log.info("(QuinielaDownloadClasifGeneral/doPost) :");
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
            List<RegClasifGeneral> clasifGral = (List<RegClasifGeneral>)session.getAttribute("clasifGral");
            int jornada = (Integer) session.getAttribute("jornada");
            String fileType = (String) session.getAttribute("fileType");
            
            // Build Header
            String txt = "";
            txt += "+----------+--------+--------+----------------------+--------+------+------+----------+----------+----------+----------+-------+\n";
            txt += "| Anterior | Flecha | Puesto | Usuario              | Puntos | T.J. | Q.J. | Partidos | %Aciert. | P.Anter. | J.Actual | Total |\n";
            txt += "+----------+--------+--------+----------------------+--------+------+------+----------+----------+----------+----------+-------+\n";

            // Get info to download
            Iterator<RegClasifGeneral> it = clasifGral.iterator();
            while (it.hasNext()) {
                
                RegClasifGeneral rcg = it.next();
                
                txt += formatField(rcg.getPosAnterior()+"", 10, "left") + SEPARATOR;
                txt += formatField(rcg.getFlecha()+"", 7, "left") + SEPARATOR;
                txt += formatField(rcg.getPosActual()+"", 7, "left") + SEPARATOR;
                txt += formatField(rcg.getUser()+"", 21, "right") + SEPARATOR;
                txt += formatField(rcg.getPuntosTotal()+"", 7, "left") + SEPARATOR;
                txt += formatField(rcg.getTj()+"", 5, "left") + SEPARATOR;
                txt += formatField(rcg.getQj()+"", 5, "left") + SEPARATOR;
                txt += formatField(rcg.getPartidos()+"", 9, "left") + SEPARATOR;
                txt += formatField(rcg.getPorcentaje(), 9, "right") + SEPARATOR;
                txt += formatField(rcg.getPuntosAnterior()+"", 9, "left") + SEPARATOR;
                txt += formatField(rcg.getPuntosActual()+"", 9, "left") + SEPARATOR;
                txt += formatField(rcg.getTotal()+"", 6, "left") + SEPARATOR;
                txt += "\n";
            }
            txt += "+----------+--------+--------+----------------------+--------+------+------+----------+----------+----------+----------+-------+\n";
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
            log.info("(QuinielaDownloadClasifGeneral/doPost) exception: <"+e.getMessage()+">");
            request.setAttribute("errorMsg", errorMsg+ " Fichero no encontrado.");
            request.setAttribute("recomen", ""); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
            view.forward(request, response); 
        }
    }


    //*****************************************************************
    String formatField(String data, int lengthField, String side) {
        
        String field;
        int dataLength = data.length();
        
        String pad = "";
        for (int i=0; i<(lengthField-dataLength); i++) {
            pad += " ";
            
        }
        
        if (side.equals("left")) {
            field = pad + data + " ";
        }
        else {
            field = " " + data + pad;
        }
            
        return field;
        
    }

}

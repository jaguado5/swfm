package swfm.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
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

import swfm.model.TblPlayers;



/*************************************************************************/
//                         Class BestPlayerUploadTemplateFile
/*************************************************************************/

public class BestPlayerUploadTemplateFile extends HttpServlet {
    
    
    private static final long serialVersionUID = 1L;

    private transient static final Log log=LogFactory.getLog(BestPlayerUploadTemplateFile.class);


    //******************************************************
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //Get session parameters
        HttpSession session = request.getSession();
        Connection conn = (Connection)session.getAttribute("mysqlConn");
        String pageType = (String)session.getAttribute("pageType");

        // Get form parameters 
        MultipartForm mform = (MultipartForm)request.getAttribute("form"); 
        String jornadaStr = mform.getParamValue("jornada");
        String uploadFile = mform.getParamValue("uploadFile"); 
        log.info("(BestPlayerUploadTemplateFile/doPost) jornada:" + jornadaStr);

        // Check received file
        if (checkFile(uploadFile) == false) {
            request.setAttribute("errorMsg", "Fichero inválido"); 
            RequestDispatcher view = request.getRequestDispatcher("UsrError.jsp");
            view.forward(request, response);
            return; 
        }


        try {

            // Parser Player Template file, line to line and extract player names 
            BufferedReader fRd = new BufferedReader(new InputStreamReader(new FileInputStream(uploadFile), "UTF8")); 
            String line; 
            String[] tokens = new String[2];
            List<String> playerList = new ArrayList<String>();

            while ((line=fRd.readLine()) != null) {
    
                if (line.contains(":")) {
                    
                    tokens = line.split(":");
                    String player = tokens[0].trim();
                    playerList.add(player);
                }
            }
            fRd.close();

            // Persist player names in BBDD
            int season = CONST.SEASON;
            int jornada = Integer.parseInt(jornadaStr);
            TblPlayers tblPlayersMng = new TblPlayers(conn);

            int numPlayersInserted = 0;
            Iterator<String> it = playerList.iterator();
            int numOrden = 1;
            while (it.hasNext()) {
                tblPlayersMng.persistPlayerGame(season, jornada, numOrden, it.next());
                numOrden++;
                numPlayersInserted++;
            }

            // -----------------------
            // Prepare View
            //-------------------------

            // Prepare detail message for the View
            String detailMsg = "Registers inserted in BD: " + numPlayersInserted + "<br><br>";

            it = playerList.iterator();
            int index = 0;
            while (it.hasNext()) {
                String player = it.next();
                detailMsg += season + "-J" + jornada + "-" + (++index) + " / " +player +"/"+ "<br>";
            }

            // Prepare rest of fields
            session.setAttribute("header", Util.generateRandomHeader());
            request.setAttribute("infoMsg", "El fichero ha sido introducido correctamente.");
            request.setAttribute("detailMsg", detailMsg);

            // Forward JSP to the View
            RequestDispatcher view = request.getRequestDispatcher(pageType + "OperationOk.jsp");
            view.forward(request, response);


        } catch (Exception e) {
            log.error("(BestPlayerUploadTemplateFile/doPost) exception: <"+e.getMessage()+">");
            request.setAttribute("errorMsg", "An expected error: " + e.getMessage()); 
            request.setAttribute("recomen", ""); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
            view.forward(request, response); 
        }

    }


    // ***************************************************************** 
    // Check received file 
    // ***************************************************************** 
    private boolean checkFile(String fName) {
        boolean ret = true;
        //Check file 
        File f = new File(fName); 
        if (f.length()<=0) { 
            ret = false;
        }

        return ret;
    }



}

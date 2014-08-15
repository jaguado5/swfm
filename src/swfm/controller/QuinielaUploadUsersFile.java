//*****************************************************************
// SWBD: Servicios Web de Base de Datos.
//
// Autor:                     Juan Jos� Aguado
// Fecha creaci�n:            09/Jul/2010
// Fecha �ltima modificaci�n: 27/Jul/2010
//
// clase: UploadUserFile
//
//*****************************************************************

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

import swfm.model.TblQuiniela;
import swfm.model.UserResult;


/*************************************************************************/
//                         Class QuinielaUploadUsersFile
/*************************************************************************/
public class QuinielaUploadUsersFile extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private transient static final Log log=LogFactory.getLog(QuinielaUploadUsersFile.class);


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
        String uploadFile = mform.getParamValue("uploadFile"); 
        log.info("(UploadUserFile/doPost) jornada:" + jornadaStr);

        // Check received file
        if (checkFile(uploadFile) == false) {
            request.setAttribute("errorMsg", "Fichero inv&aacute;lido"); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp");
            view.forward(request, response);
            return; 
        }


        try {
            int jornada = Integer.parseInt(jornadaStr);
            TblQuiniela tblUserResultMng = new TblQuiniela(conn);

            List<UserResult> usrResultList = readUserResults(uploadFile, season, jornada);
            Iterator<UserResult> it = usrResultList.iterator();
            int numUsersInserted = 0;

            while (it.hasNext()) { 
                UserResult usrResult =  it.next();
                tblUserResultMng.persistUserResult(usrResult);
                numUsersInserted++;
            } 

            // -----------------------
            // Prepare View
            //-------------------------

            // Prepare detail message for the View
            String detailMsg = "Registers inserted in BD: " + numUsersInserted + "<br><br>";
            it = usrResultList.iterator();
            int index = 0;
            while (it.hasNext()) {

                UserResult u = it.next();

                detailMsg += "J" + u.getJornada() + "-" + (++index) + 
                                " / " + u.getUsr() + "/ predictions: " + u.getNumPredictions()+ "<br>";
            }

            // Prepare rest of fields
            request.setAttribute("title1", "Juego de la Quiniela");
            request.setAttribute("title2", "Temporada 2013/14");
            session.setAttribute("header", Util.generateRandomHeader());
            request.setAttribute("infoMsg", "El fichero ha sido introducido correctamente.");
            request.setAttribute("detailMsg", detailMsg);

            // Forward JSP to the View
            RequestDispatcher view = request.getRequestDispatcher(pageType + "OperationOk.jsp");
            view.forward(request, response);


        } catch (Exception e) {
            log.error("(UploadUserFile/doPost) exception: <"+e.getMessage()+">");
            request.setAttribute("errorMsg", "An expected error: " + e.getMessage()); 
            request.setAttribute("recomen", ""); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
            view.forward(request, response);
        }

    }


    //****************************************************** 
    List<UserResult> readUserResults(String fName, int season, int jornada) throws Exception{ 

        BufferedReader fRd = new BufferedReader(new InputStreamReader(new FileInputStream(fName), "UTF8")); 
        
        ArrayList<UserResult> usrListResult = new ArrayList<UserResult>(); 

        String reg=""; 
        String usrToken = "onectado"; 

        while ( (reg=fRd.readLine())!=null  ) { 

            if (reg.contains(usrToken)==true) { 
                
                String[] tokens = new String[2];
                tokens = reg.split(usrToken);
                String usr = tokens[1].trim(); 

//                String usr = reg.substring(1, reg.length()); 
                UserResult usrResult = new UserResult(season, jornada, usr); 
                
                System.out.println("Reading predictions of /" + usr + "/"); 
                log.info("Reading predictions of /" + usr + "/"); 

                int gameId=1; 
                String gameToken = gameId+".";
                int numPredictions = 0;

                while ( (reg=fRd.readLine())!=null  && gameId<16) { 

                    String reg1 = reg.trim().toUpperCase(); 
                    if (reg1.contains(gameToken)==true) { 
                        
                        try { 
                            char prediction = getPrediction(reg1);
                            numPredictions++;
                            usrResult.setPrediction(gameId-1, prediction);
                        } catch (Exception e) { 
                            System.out.println(usr + " prediction NOT found:  /" + reg1 + "/"); 
                            log.error(usr + " prediction NOT found:  /" + reg1 + "/"); 
                        } 

                        // Search next token 
                        gameId++; 
                        gameToken = gameId+"."; 
                    } 
                } 

                usrResult.setNumPredictions(numPredictions);
                usrListResult.add(usrResult); 

            } 

        } 
      
        fRd.close(); 

        return usrListResult; 
    } 


    //****************************************************** 
    private char getPrediction(String line) throws Exception { 

        if (line.contains(":")==false) { 
            throw new Exception(); 
        } else { 
            int posStart = line.toUpperCase().indexOf(":"); 
            char tmp; 
            
            for (int i=posStart; i<line.length(); i++) { 
                tmp = line.charAt(i); 
                if (tmp=='1' || tmp=='2' || tmp=='X') 
                    return tmp; 
            } 
        } 

        throw new Exception(); 
        
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

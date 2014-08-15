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

import swfm.model.PlayerUserPoints;
import swfm.model.TblPlayers;
import swfm.model.UserResult;



/*************************************************************************/
//                         Class BestPlayerUploadUserFile
/*************************************************************************/

public class BestPlayerUploadUserFile extends HttpServlet {
    
    
    private static final long serialVersionUID = 1L;

    private transient static final Log log=LogFactory.getLog(BestPlayerUploadUserFile.class);


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
        log.info("(BestPlayerUploadUserFile/doPost) jornada:" + jornadaStr);

        // Check received file
        if (checkFile(uploadFile) == false) {
            request.setAttribute("errorMsg", "Fichero inválido"); 
            RequestDispatcher view = request.getRequestDispatcher("UsrError.jsp");
            view.forward(request, response);
            return; 
        }


        try {

            // Parser Player User Point file, line to line and extract player score 
            int season = CONST.SEASON;
            int jornada = Integer.parseInt(jornadaStr);
            List<PlayerUserPoints> pupList = readPlayerUserPointFile(uploadFile, jornada, season);
            
            // Persist player names in BBDD
            TblPlayers tblPlayersMng = new TblPlayers(conn);

            int numVotesInserted = 0;
            Iterator<PlayerUserPoints> it = pupList.iterator();
            while (it.hasNext()) {
                PlayerUserPoints pup = it.next();
                tblPlayersMng.persistPlayerUserPoints(pup);
                numVotesInserted++;
            }

            // -----------------------
            // Prepare View
            //-------------------------

            // Prepare detail message for the View
            String detailMsg = "Registers inserted in BD: " + numVotesInserted + "<br><br>";

            it = pupList.iterator();
            int index = 0;
            while (it.hasNext()) {
                PlayerUserPoints pup = it.next();
                detailMsg += season + "-J" + jornada + "-" + (++index) + " /" +pup.getUser() +"/"+ pup.getPlayer() + "/" + pup.getPuntos() + "/ <br>";

                if (index%14==0) {
                    detailMsg += "<hr>";
                }
            }

            // Prepare rest of fields
            session.setAttribute("header", Util.generateRandomHeader());
            request.setAttribute("infoMsg", "El fichero ha sido introducido correctamente.");
            request.setAttribute("detailMsg", detailMsg);

            // Forward JSP to the View
            RequestDispatcher view = request.getRequestDispatcher(pageType + "OperationOk.jsp");
            request.setAttribute("infoMsg", "El fichero ha sido introducido correctamente.");
            view.forward(request, response);


        } catch (Exception e) {
            log.error("(BestPlayerUploadUserFile/doPost) exception: <"+e.getMessage()+">");
            request.setAttribute("errorMsg", "An expected error: " + e.getMessage()); 
            request.setAttribute("recomen", ""); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
            view.forward(request, response);
        }

    }


    //****************************************************** 
    List<PlayerUserPoints> readPlayerUserPointFile(String fName, int jornada, int season) throws Exception{ 

        BufferedReader fRd = new BufferedReader(new InputStreamReader(new FileInputStream(fName), "UTF8")); 
        
        ArrayList<PlayerUserPoints> playerUserPointList = new ArrayList<PlayerUserPoints>(); 

        String line=""; 
        String usrToken = "onectado"; 

        while ( (line=fRd.readLine())!=null  ) { 

            if (line.contains(usrToken)==true) { 
                
                String[] tokens = new String[2];
                tokens = line.split(usrToken);
                String usr = tokens[1].trim(); 

                System.out.println("Reading predictions of /" + usr + "/"); 
                log.info("Reading predictions of /" + usr + "/"); 

                String gameToken = ":";
                int numPredictions = 0;

                boolean endUserLoop=false;
                while ( (line=fRd.readLine())!=null && endUserLoop==false) {
                    
                    if (line.contains("Borrar mensaje")) {
                        endUserLoop = true;
                    }

                    else {
                        String reg = line.trim().toUpperCase(); 
                        if (reg.contains(gameToken)==true) { 
                            
                            try {
                                tokens = reg.split(gameToken);
                                
                                String firstWord = tokens[0].trim();
                                
                                if (firstWord.equals("ASUNTO") || firstWord.equals("MENSAJES") || firstWord.equals("REGISTRADO") || 
                                        firstWord.contains("ZOOM") || firstWord.equals("UBICACIÓN") || firstWord.equals("SPOILER") ||
                                        firstWord.contains("LISTADO JUGADORES") ) {
                                    System.out.println(" Descarto linea que empieza por :  /" + firstWord + "/"); 
                                    log.error(" Descarto linea que empieza por :  /" + firstWord + "/"); 
                                }
                                else {
                                    String player = firstWord;
                                    int points = getPoints(reg);
                                    PlayerUserPoints pup = new PlayerUserPoints(season, jornada, usr, player, points);
                                    playerUserPointList.add(pup);
                                }
                                
                            } catch (Exception e) { 
                                System.out.println(usr + " Puntuacion NOT found:  /" + reg + "/"); 
                                log.error(usr + " Puntuacion NOT found:  /" + reg + "/"); 
                            }
                        }
                    }
                } 
            } 

        } 
      
        fRd.close(); 

        return playerUserPointList; 
    } 


    //****************************************************** 
    static int getPoints(String line) throws Exception {

        if (line.contains(":")==false) { 
            throw new Exception(); 
        } else { 
            int posStart = line.toUpperCase().indexOf(":"); 
            char tmp0, tmp1;

            for (int i=posStart; i<line.length(); i++) { 
                tmp0 = line.charAt(i);

                if (Character.isDigit(tmp0)) {
                    
                    i++;
                    if (i<line.length()) {
                        tmp1 = line.charAt(i);
                        if (tmp0=='1' && tmp1=='0')
                            return 10;
                        else
                            return Character.getNumericValue(tmp0);
                    }
                    else
                        return Character.getNumericValue(tmp0);
                }
            }

            return -1;
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

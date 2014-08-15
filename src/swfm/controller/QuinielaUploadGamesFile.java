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

import swfm.model.Game;
import swfm.model.MasterResult;
import swfm.model.TblGames;
import swfm.model.TblQuiniela;

public class QuinielaUploadGamesFile extends HttpServlet  {
    
    
    private static final long serialVersionUID = 1L;

    private transient static final Log log=LogFactory.getLog(QuinielaUploadGamesFile.class);

    private int jornada;
    private List<Game> gamesList;


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
        String uploadFile = mform.getParamValue("uploadFile"); 
        log.info("(QuinielaUploadGamesFile/doPost) jornada:" + jornadaStr);

        // Check received file
        if (checkFile(uploadFile) == false) {
            request.setAttribute("errorMsg", "Fichero inv&aacute;lido"); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp");
            view.forward(request, response);
            return; 
        }


        try {
            jornada = Integer.parseInt(jornadaStr);
            gamesList = new ArrayList<Game>();

            TblQuiniela tblUserResultMng = new TblQuiniela(conn);
            TblGames tblGamesMng = new TblGames(conn);

            // Parser Games File
            char gamesResult[] = parserGameFile(uploadFile);

            // Build Pools Result
            MasterResult mResult = new MasterResult(season, jornada);
            mResult.setGameResult(gamesResult);

            // -----------------------
            // Persist everything in DB
            //-------------------------

            // Persist games teams & results
            int numGamesInserted = 0;
            if (gamesList.size()==15) {
                numGamesInserted = tblGamesMng.peristGamesList(gamesList);
            } else {
                request.setAttribute("errorMsg", "An expected error reading Game File;"); 
                request.setAttribute("recomen",  "Expected 15 games and read "+ gamesList.size()); 
                RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
                view.forward(request, response);
            }

            // Persist pools results
            tblUserResultMng.persistMasterResult(mResult);


            // -----------------------
            // Prepare View
            //-------------------------

            // Prepare detail message for the View
            String detailMsg = "Registers inserted in BD: " + numGamesInserted + "<br><br>";
            Iterator<Game> it = gamesList.iterator();
            int index = 0;
            while (it.hasNext()) {

                Game g = it.next();

                detailMsg += g.getSeason() + "-J" + g.getJornada() + "-" + (index+1) + 
                                " / " + g.getTeam1() + "-" + g.getTeam2() + ": " + mResult.getGame(index++) +
                                " (" + g.getScore() +")"+ "<br>";
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
            log.error("(QuinielaUploadGamesFile/doPost) exception: <"+e.getMessage()+">");
            request.setAttribute("errorMsg", "An expected error: " + e.getMessage()); 
            request.setAttribute("recomen", ""); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
            view.forward(request, response);
        }

    }


    // ***************************************************************** 
    // Parser Game File, line to line 
    // ***************************************************************** 
    private char[] parserGameFile(String fName) throws Exception {
        // Parser Master Result file, line to line 
        BufferedReader fRd = new BufferedReader(new InputStreamReader(new FileInputStream(fName), "UTF8")); 
        String line; 
        String[] tokens = new String[40];
        int lineNumber=0;
        int gameNumber = 0;
        char gamesResult[] = new char[15];

        while ((line=fRd.readLine()) != null) { 
            lineNumber++; 
            tokens = line.split(":");

            if (tokens.length==3) {

                // Teams
                String[] teams = tokens[0].split("-");
                String team1 = getTeam1(teams[0].trim());
                String team2 = teams[1].trim();

                // Pools
                char result = tokens[1].trim().toUpperCase().charAt(0);
                gamesResult[gameNumber++] = result;

                // Score
                String score = tokens[2].trim();

                // Add game to Games List
                gamesList.add(new Game(CONST.SEASON, jornada, convertToLowerCase(team1), convertToLowerCase(team2), score));
                System.out.println("/"+convertToLowerCase(team1)+"/"+convertToLowerCase(team2)+"/"+result+"/"+score+"/");
            }
        }
        
        return gamesResult;
    }


    // ***************************************************************** 
    private String getTeam1(String cadena) {

        String team1 = "";

        int start = cadena.indexOf(". ") + 2;
        
        int end = cadena.length();

        team1 = (String) cadena.subSequence(start, end);

        return team1;
    }


    // ***************************************************************** 
    private String convertToLowerCase(String cadena) {

        // Convert all the string to lower case
        String tmp = cadena.toLowerCase();

        // Get the first char of the string
        String firstChar = ((Character)tmp.charAt(0)).toString();

        // Convert the first char to upper case
        String result = tmp.replaceFirst(firstChar, firstChar.toUpperCase());

        // If there is any space, convert to upper case the following char
        int index = result.indexOf(" ");
        if (index>=0) {
            tmp = result;
            String nextChar = ((Character)tmp.charAt(index+1)).toString();
            result = tmp.replaceFirst(" "+nextChar, " "+nextChar.toUpperCase());
        }

        return result;
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

//*****************************************************************
// SWBD: Servicios Web de Base de Datos.
//
// Autor:                     Juan Jos� Aguado
// Fecha creaci�n:            30/Jun/2010
// Fecha �ltima modificaci�n: 27/Jul/2010
//
// clase: Dispatcher
//
//*****************************************************************

package swfm.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import swfm.view.BestPlayerDispClasifGeneralServlet;
import swfm.view.BestPlayerDispClasifJornadaServlet;
import swfm.view.BestPlayerDispMainMenu;
import swfm.view.QuinDispEditJornada;
import swfm.view.QuinielaDispClasifJornadaServlet;
import swfm.view.QuinielaDispMainMenu;


/*************************************************************************/
//                         Class Dispatcher
/*************************************************************************/
public class Dispatcher extends HttpServlet {


    private static final long serialVersionUID = 1L;
    
    private transient static final Log log=LogFactory.getLog(Dispatcher.class);
    
    //Session
    private HttpSession session;


    // *****************************************************************
    public void dispatcher(String operation, HttpServletRequest request, HttpServletResponse response) 
                                                                          throws IOException, ServletException {

        session = request.getSession();
        String backPage = (String) session.getAttribute("backPage");
        String pageType = (String) session.getAttribute("pageType");

        
        log.info("(Dispatcher/doPost) getRequestURI: " + request.getRequestURI());
        log.info("(Dispatcher/doPost) backPage: " + backPage);
        log.info("(Dispatcher/doPost) operation: /" + operation+ "/");


        // ---------------------------------
        // Login User
        // ---------------------------------
        if (operation.compareTo(CONST.OPERATION_LOGIN_USER) == 0) {
            session.setAttribute("backPage", "login.jsp");
            UserLoginServlet s = new UserLoginServlet();
            s.doPost(request, response);
        }


        /*** OLD Login
        // ---------------------------------
        // Login User
        // ---------------------------------
        if (operation.compareTo(CONST.OPERATION_LOGIN_USER) == 0) {
            session.setAttribute("backPage", "login.jsp");
            LDAP_UserLoginServlet s = new LDAP_UserLoginServlet();
            s.doPost(request, response);
        } ***/

        
        // ---------------------------------
        // QUINIELA 
        // ---------------------------------

        // Petición mostrar Menu Quiniela.
        else if (operation.compareTo(CONST.OPERATION_QUINIELA_MENU) == 0) {
            dispatchToServlet(request, response, new QuinielaDispMainMenu());
            //dispatchToJsp(request, response, pageType+"qMenu.jsp");
        }

        // Mostrar Clasificaci�n Jornada
        else if (operation.compareTo(CONST.OPERATION_QUINIELA_SHOW_CLASIF_JORNADA) == 0) {
            dispatchToServlet(request, response, new QuinielaDispClasifJornadaServlet());
        }

        // Mostrar Clasificaci�n General
        else if (operation.compareTo(CONST.OPERATION_QUINIELA_SHOW_CLASIF_GENERAL) == 0) {
            dispatchToServlet(request, response, new QuinielaClasifGeneralServlet());
        }

        // Exportar Clasificaci�n General a Fichero Formato Texto
        else if (operation.compareTo(CONST.OPERATION_QUINIELA_DOWNLOAD_CLASIF_GRAL_TXT) == 0) {
            session.setAttribute("fileType", "txt");
            dispatchToServlet(request, response, new QuinielaDownloadClasifGeneral());
        }

        // Exportar Clasificaci�n General a Fichero Formato HTML
        else if (operation.compareTo(CONST.OPERATION_QUINIELA_DOWNLOAD_CLASIF_GRAL_HTML) == 0) {
            session.setAttribute("fileType", "html");
            dispatchToServlet(request, response, new QuinielaDownloadClasifGeneral());
        }

        // Exportar Clasificaci�n Jornada a Fichero Formato Texto
        else if (operation.compareTo(CONST.OPERATION_QUINIELA_DOWNLOAD_CLASIF_JORNADA_TXT) == 0) {
            session.setAttribute("fileType", "txt");
            dispatchToServlet(request, response, new QuinielaDownloadClasifJornada());
        }

        // Exportar Clasificaci�n Jornada a Fichero Formato HTML
        else if (operation.compareTo(CONST.OPERATION_QUINIELA_DOWNLOAD_CLASIF_JORNADA_HTML) == 0) {
            session.setAttribute("fileType", "html");
            dispatchToServlet(request, response, new QuinielaDownloadClasifJornada());
        }

        // Importar Resultados Oficiales de una Jornada desde Fichero
        else if (operation.compareTo(CONST.OPERATION_QUINIELA_UPLOAD_MASTER_FILE) == 0) {
            dispatchToServlet(request, response, new QuinielaUploadGamesFile());
        }

        // Importar Pron�sticos de Usuario de una Jornada desde Fichero
        else if (operation.compareTo(CONST.OPERATION_QUINIELA_UPLOAD_USER_FILE) == 0) {
            dispatchToServlet(request, response, new QuinielaUploadUsersFile());
        }

        // Procesar una Jornada (una vez que los ficheros master y user han sido introducidos en el sistema)
        else if (operation.compareTo(CONST.OPERATION_QUINIELA_PROCESS_JORNADA) == 0) {
            dispatchToServlet(request, response, new QuinielaProcessJornada());
        }

        // Petici�n mostrar Formulario Definir/Modificar Jornada.
        else if (operation.compareTo(CONST.OPERATION_QUINIELA_EDIT_JORNADA_VIEW) == 0) {
            dispatchToServlet(request, response, new QuinDispEditJornada());
        }




        // ---------------------------------
        // BEST PLAYER 
        // ---------------------------------

        // Best Player: Petición mostrar Menu Best Player.
        else if (operation.compareTo(CONST.OPERATION_BEST_PLAYER_MENU) == 0) {
            dispatchToServlet(request, response, new BestPlayerDispMainMenu());
            //dispatchToJsp(request, response, pageType+"bpMenu.jsp");
        }

        // Best Player: Importar Plantilla de Jugadores de una Jornada desde Fichero
        else if (operation.compareTo(CONST.OPERATION_BEST_PLAYER_UPLOAD_MASTER_FILE) == 0) {
            dispatchToServlet(request, response, new BestPlayerUploadTemplateFile());
        }

        // Best Player: Importar Pron�sticos de Usuario de una Jornada desde Fichero
        else if (operation.compareTo(CONST.OPERATION_BEST_PLAYER_UPLOAD_USER_FILE) == 0) {
            dispatchToServlet(request, response, new BestPlayerUploadUserFile());
        }

        // Best Player: Procesar una Jornada (una vez que los ficheros master y user han sido introducidos en el sistema)
        else if (operation.compareTo(CONST.OPERATION_BEST_PLAYER_PROCESS_JORNADA) == 0) {
            dispatchToServlet(request, response, new BestPlayerProcessJornada());
        }

        // Best Player: Mostrar Clasificaci�n Jornada
        else if (operation.compareTo(CONST.OPERATION_BEST_PLAYER_SHOW_CLASIF_JORNADA) == 0) {
            dispatchToServlet(request, response, new BestPlayerDispClasifJornadaServlet());
        }

        // Best Player: Mostrar Clasificaci�n General
        else if (operation.compareTo(CONST.OPERATION_BEST_PLAYER_SHOW_CLASIF_GENERAL) == 0) {
            dispatchToServlet(request, response, new BestPlayerDispClasifGeneralServlet());
        }



        // ---------------------------------
        // PORRA 
        // ---------------------------------

        // Petici�n mostrar Menu Porra.
        else if (operation.compareTo(CONST.OPERATION_PORRA_MENU) == 0) {
            request.setAttribute("header", "Seleccionar servidor");
            //request.setAttribute("nextAction", CONST.MYSQL_MONITOR_SERVER_DO);
            dispatchToJsp(request, response, "qPorra.jsp");
        }



        // --------------------------------------------------------------------
        // NAVEGACION
        // --------------------------------------------------------------------

        // Peticion "Volver". No se necesita estar registrado
        else if (operation.compareTo(CONST.OPERATION_GO_BACK) == 0) {
            dispatchToJsp(request, response, pageType+backPage);
        }

        
        

        // --------------------------------------------------------------------
        // Peticion desconocida
        // --------------------------------------------------------------------

        else {
            String errorMsg = "Operaci&oacute;n no soportada!!";
            request.setAttribute("errorMsg", errorMsg);
            dispatchToJsp(request, response, pageType + "showError.jsp");
        }


        /**
        // Petición "Registrar Usuario".
        else if (operation.compareTo(CONST.OPERATION_MOD_EXP_USR) == 0) {
            AddStudentServlet s = new AddStudentServlet();
            session.setAttribute("backPage", pageType + "addStudent.jsp");
            s.doPost(request, response);
        }

        // Petición "Acceder al Sistema".
        else if (operation.compareTo(CONST.OPERATION_MOD_EXP_USR) == 0) {
            session.setAttribute("backPage", pageType + "login.jsp");
            LoginUserServlet s = new LoginUserServlet();
            s.doPost(request, response);
        }

        **/

    }


    // *****************************************************************
    private void dispatchToJsp(HttpServletRequest req, HttpServletResponse resp, String jspPage)
                                                         throws IOException, ServletException {

        log.info("(Dispatcher/dispatchToJsp) jspPage: " + jspPage);
        RequestDispatcher view = req.getRequestDispatcher(jspPage);
        view.forward(req, resp);
    }


    // *****************************************************************
    private void dispatchToServlet(HttpServletRequest req, HttpServletResponse resp, HttpServlet s)
                                                         throws IOException, ServletException {
        s.service(req, resp);
    }


}

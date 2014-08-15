//*****************************************************************
// SWBD: Servicios Web de Base de Datos.
//
// Autor:                     Juan Jos� Aguado
// Fecha creaci�n:            30/Jun/2010
// Fecha �ltima modificaci�n: 22/Jul/2010
//
// clase: FrontalController
//
//*****************************************************************
package swfm.controller;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import swfm.model.MySQL;


/*********************************************************
*                 Class FrontalController
*********************************************************/
public class FrontalController extends HttpServlet {

    private static final long serialVersionUID = 4901983754446763778L;

    private transient static final Log log=LogFactory.getLog(FrontalController.class);

    // MySQL Connection
    private MySQL mysql;



    //******************************************************
    public void init(ServletConfig cfg) throws ServletException {

        System.out.println("(FrontalController/init) ******************** version:"+ serialVersionUID);

        log.info("(FrontalController/init) ******************** version:"+ serialVersionUID);
        
        try {
            //Leer fichero de configuraci�n de la aplicaci�n
            Util.readCfg();

            // Conectar a BD MySQL
            mysql = new MySQL();
            mysql.connectBD();

        } catch (Exception e) {
            log.info("(FrontalController/init) exception: <" + e.getMessage() + ">");
            throw (ServletException)e;
        }
  }



    // *****************************************************************
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }



    //******************************************************
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Session
        HttpSession session = request.getSession();

        // Page Type
        String pageType = "/pages/";

        //Check MySQL Data Base connection is alive
        try {
            if (mysql.getConnection().isClosed()==true) {
                mysql.reconnect();
            }
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            log.info("(FrontalController/doPost) exception: <"+e.getMessage()+">");
            RequestDispatcher view = request.getRequestDispatcher("UsrError.jsp");
            request.setAttribute("errorMsg", errorMsg);
            view.forward(request, response);
        }

        // ---------------------------------------------
        // Determinar operaci�n a realizar.
        // La forma de capturar el par�metro 'operacion'
        // depende de si el formulario en multipart
        // (utilizado para enviar archivos) o no 
        // ----------------------------------------------
        String operation = "";
        boolean b = ServletFileUpload.isMultipartContent(request);
        
        // Formulario Multipart (contiene un archivo) 
        if (b == true) {
            try { 
                MultipartForm mform = new MultipartForm(request); 
                operation = mform.getParamValue("operation"); 
                request.setAttribute("form", mform); 
            } catch (Exception e) { 
                log.error("(doPost/ exception:" + e.getMessage());
            } 
        } else { 
            operation = request.getParameter("operation");
        }

        String remoteAddr = request.getRemoteHost();
        log.info("(FrontalController/doPost) remoteAddr: " + remoteAddr + "operation: "+operation);

        // -----------------------------------------
        // Dispatch operation
        // -----------------------------------------
        if (operation==null) {
            return;
        }
        else if (operation.compareTo(CONST.OPERATION_EXIT) == 0) {
            //userProfile = CONST.USER_NOT_REGISTERED;
            RequestDispatcher view = request.getRequestDispatcher(pageType + "login.jsp"); 
            view.forward(request, response); 
        }
        else {

            // Set attributes for others servlet
            request.setAttribute("mysqlConn", mysql.getConnection());
            session.setAttribute("mysqlConn", mysql.getConnection());
            session.setAttribute("pageType", pageType);
  
            // Dispatch request received
            Dispatcher d = new Dispatcher();
            d.dispatcher(operation, request, response);
        }

    }



    //*****************************************************************
    public void destroy() {
        log.info("(FrontalController/destroy) **SWBD END** " );

        try {
            mysql.close();
        } catch (Exception e) {
            log.error("(FrontalController/destroy) exception: <"+e.getMessage()+">");
            return;
        }
    }



}

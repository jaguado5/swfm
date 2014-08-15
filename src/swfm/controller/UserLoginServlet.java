package swfm.controller; 

import javax.servlet.*; 
import javax.servlet.http.*; 

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory; 
import java.io.*; 
import java.sql.Connection; 
import swfm.model.TblUsers; 
import swfm.model.User; 

/*************************************************************************/ 
//                         CLASE UserLoginServlet 
/*************************************************************************/ 
public class UserLoginServlet extends HttpServlet { 

    private static final long serialVersionUID = 1287502737194510693L; 
    private transient static final Log log=LogFactory.getLog(UserLoginServlet.class); 


    // ***************************************************************** 
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { 

        User user; 

        //Get session parameters
        HttpSession session = request.getSession();
        Connection dbConn = (Connection) session.getAttribute("mysqlConn"); 
        String pageType = (String)session.getAttribute("pageType");
        String remoteAddr = request.getRemoteHost(); 

        try { 

            String kk = session.getServletContext().getContextPath();
            String kk1 = session.getServletContext().getRealPath("/");

            TblUsers usersManager = new TblUsers(dbConn); 

            // Retrieve form parameters
            String login = request.getParameter("login");
            String pwd = request.getParameter("pwd");

            log.info("(doPost/"+remoteAddr+") User:" + login + " pwd:" + pwd); 

            // Get user data 
            user = usersManager.getUser(login, pwd);

            // Login OK porque no salta una excepcion 
            log.debug("(doPost/"+remoteAddr+") clave correcta"); 

            // Actualiza en la BD, tabla 'usuarios', el campo 'fechaUltimoLogin' 
            usersManager.updateFechaUltimoLogin(user); 

            // Guarda el objeto 'usuario' en el objeto sesion 
            session.setAttribute("user", user); 

            // Lanza pagina menu, dependiendo del usuario (Webmaster, Moderador o Usuario) 
            String menuPage = ""; 
            
            if (user.getProfile().compareTo("Webmaster") == 0) { 
                menuPage = "menuRoot.jsp"; 
                log.info("(doPost/"+remoteAddr+") loginOK: Webmaster"); 
            } 
            else if (user.getProfile().compareTo("Moderador") == 0) { 
                menuPage = "menuRoot.jsp"; 
                log.info("(doPost/"+remoteAddr+") loginOK: root"); 
            } 
            else { 
                menuPage = "menuStudent.jsp"; 
                log.info("(doPost/"+remoteAddr+") loginOK: student"); 
            } 

            RequestDispatcher view = request.getRequestDispatcher(pageType + menuPage); 
            view.forward(request, response); 

        } catch (MyException e) { 
            
            request.setAttribute("errorMsg", e.getMessage()); 
            if (e.getCode() == ErrorCode.USER_NOT_EXIST) {
                request.setAttribute("recomen", "Registrese desde la pagina principal"); 
                log.info("(doPost/"+remoteAddr+") USER_NOT_EXIST"); 
            }
            else if (e.getCode() == ErrorCode.PWD_INVALID) {
                request.setAttribute("recomen", "Contraseña inválida; vuelva a intentarlo"); 
                log.info("(doPost/"+remoteAddr+") PWD_INVALID"); 
            }
            else {
                request.setAttribute("recomen", "");
            }

            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
            view.forward(request, response); 
            
        } catch (RuntimeException e) { 
            request.setAttribute("errorMsg", "An expected error: " + e.getMessage()); 
            request.setAttribute("recomen", ""); 
            RequestDispatcher view = request.getRequestDispatcher(pageType + "showError.jsp"); 
            view.forward(request, response); 
        } 

  } 

}

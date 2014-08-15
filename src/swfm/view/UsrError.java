//*****************************************************************
// MyMU: MySQL Monitoring Utilities.
//
// Autor:                     Juan José Aguado
// Fecha creación:            30/Jun/2010
// Fecha última modificación: 30/Jun/2010
//
// clase: UsrError
//
//*****************************************************************
package swfm.view;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;


/*********************************************************
*                 Clase UsrError
*********************************************************/
public class UsrError extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    generateView(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    generateView(request, response);
  }

  public void generateView(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //Set page title
    String pageTitle = "MySQL Monitoring Utilities";

    //Retrieve the "file name" from the request scope
    String errorMsg = (String)request.getAttribute("errorMsg");

    //Generate HTML response
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>"+pageTitle+"</title></head>");
    out.println("<body bgcolor='white'>");
    out.println("<!-- Page Heading -->");
    out.println("<table border='1' cellpadding='5' cellspacing='0' width='600'>");
    out.println("  <tr bgcolor='green' align='center' valign='center' height='20'>");
    out.println("    <td><h1>Servicios Web de Gestión de Expedientes (SWGE)</h1></td>");
    out.println("  </tr>");
    out.println("</table>");
    out.println("<br>");
    out.println("<br>");
    out.println("<h1 style=\"color:green\">Visualización de Información de Usuario</h1>");
    out.println("<br>");
    out.println("<br>");
    out.println("<h2 style=\"color:red\">"+errorMsg+"</h2>");
    out.println("</body>");
    out.println("</html>");
  }//END of generateView method

}//END of UsrError class

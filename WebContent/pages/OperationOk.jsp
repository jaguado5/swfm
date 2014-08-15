<%@ page language='java'
                contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
                %>


<!-- OperationOk.jsp -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">


<HTML>
  <HEAD>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type">
    <LINK media=all href='/swfm/pages/styles/estilos.css' type=text/css rel=StyleSheet>
    <title> SWFM - Utilidades de Administración del Foro Malaguista </title>
  </HEAD>

  <BODY>
    
    <%
      String title1 = (String)request.getAttribute("title1");
      String title2 = (String)request.getAttribute("title2");
      String header = (String)session.getAttribute("header");
    %>

    <!------------
       CABECERA
    ------------>
    <div style="text-align: center;">
      <img alt="cabecera" src="<%out.print(header);%>" >
    </div>
    <br>

    <TABLE class='divInicio' width='100%' cellSpacing='0' cellPadding='15'>
      <TR>
        <TD nowrap align='left'>
          <% out.print(title1); %>
        </TD>
        <TD nowrap align='right'>
          <% out.print(title2); %>
        </TD>
      </TR>
    </TABLE>

    <BR>


<!-- ----------- ENTRADA DE DATOS ----------- -->
    <TABLE class='divIntermedio' width='100%'>
      <TR>
        <TD nowrap width='75%' align='right'>
          <a href='/swfm/pages/menuRoot.jsp'><img src='/swfm/pages/styles/menu.gif' border='none' alt='Visualizar menú principal'></a>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </TD>
      </TR>
      <TR>
        <TD nowrap align='right'>Menú Principal&nbsp;&nbsp;&nbsp;</TD>
      </TR>
    </TABLE>


<!-- ----------- INFORMACION DE USUARIO ----------- -->
    <BR>

    <TABLE class='divInfo'>
      <TR>
        <TD>
          <%
            String infoMsg = (String)request.getAttribute("infoMsg");
            out.print(infoMsg);
          %>
        </TD>
      </TR>
    </TABLE>

    <br>

<!-- ----------- INFORMACION DE DETALLE ----------- -->
    <TABLE class='divAcciones' width='100%'>
      <tr>
        <td> <br> </td>
      </tr>
      
      <TR>
        <TD>
          <%
            String detailMsg = (String)request.getAttribute("detailMsg");
            out.print(detailMsg);
          %>
        </TD>
      </TR>
      
    </TABLE>

    <br>
    <br>
  </BODY>
</HTML>

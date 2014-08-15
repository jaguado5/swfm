<!-- UsrError.jsp -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">


<HTML>
  <HEAD>
    <META http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>
    <LINK media=all href='styles/estilos.css' type=text/css rel=StyleSheet>
    <TITLE>MySQL Monitoring Utilities</TITLE>
  </HEAD>

  <BODY>
    <!-- ----------- CABECERA ----------- -->
    <a href='index.html'>
      <IMG SRC='styles/logo_swfm.gif' alt='Volver a la p&aacute;gina de inicio de SWFM' border='none'>
    </a>

    <TABLE class='divInicio' width='100%' cellSpacing='0' cellPadding='15'>
      <TR>
        <TD nowrap align='left'>Error</TD>
        <TD nowrap align='right'>SGEXPL BBDD MySQL</TD>
      </TR>
    </TABLE>

    <BR>

    <!-- ----------- ENTRADA DE DATOS ----------- -->
    <TABLE class='divIntermedio' width='100%'>
      <TR>
        <TD nowrap width='75%' align='right'>
          <a href='menu.jsp'><img src='styles/menu.gif' border='none' alt='Visualizar menú principal'></a>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </TD>
      </TR>
      <TR>
        <TD nowrap align='right'>Menú Principal&nbsp;&nbsp;&nbsp;</TD>
      </TR>
    </TABLE>

    <!-- ----------- INFORMACION DE USUARIO ----------- -->
    <BR>

    <TABLE class='textoError'>
      <TR>
        <TD>
          <%
            String errorMsg = (String)request.getAttribute("errorMsg");
            out.print(errorMsg);
          %>
        </TD>
      </TR>
    </TABLE>
  </BODY>
</HTML>

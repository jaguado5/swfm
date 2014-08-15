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
    <IMG SRC='styles/logo_swge.gif' alt='Volver a la p&aacute;gina de inicio de SWGE' border='none'>
</a>

<TABLE class='divInicio' width='100%' cellSpacing='0' cellPadding='15'>
    <TR>
        <TD nowrap align='left'>
            Error</TD>
        <TD nowrap align='right'>
            SGEXPL BBDD MySQL</TD>
    </TR>
</TABLE>    

<BR>


<!-- ----------- INFORMACION DE USUARIO ----------- -->

<BR>

<TABLE class='textoError'>
<TR><TD>
  <%
    String errorMsg = (String)request.getAttribute("errorMsg");
    out.print(errorMsg);
  %>
</TD></TR></TABLE>

</BODY>
</HTML>

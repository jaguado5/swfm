<!-- txDataFile.jsp -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">


<!--**********************************************************-->

<script language='JavaScript'>

  <!-- Comprobar datos de entrada del formulario: puerto y esquema -->
  function checkInputData(fileName) {

    if (fileName=='') {
      window.alert('No se ha introducido nombre de fichero!')
      return false;
    }

    return true;
  }

</script>

<!--**********************************************************-->


<HTML>
  <HEAD>
    <META http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>
    <LINK media=all href='styles/estilos.css' type=text/css rel=StyleSheet>
    <TITLE>MySQL Monitoring Utilities</TITLE>
  </HEAD>

  <BODY>
  
    <!-- ----------- Cabecera Intermedia ----------- -->
  
    <a href='index.html'>
      <IMG SRC='styles/logo_swbd.gif' alt='Volver a la p&aacute;gina de inicio de SWBD' border='none'>
    </a> 
  
    <%
      String nextAction = (String)request.getAttribute("nextAction");
      String header = (String)request.getAttribute("header");
    %>

    <TABLE class='divInicio' width='100%' cellSpacing='0' cellPadding='15'>
      <TR>
        <TD nowrap align='left'> <%out.println(header);%> </TD>
        <TD nowrap align='right'> SGEXPL BBDD MySQL </TD>
      </TR>
    </TABLE>    
  
    <BR>
  
    <!------------------------->
    <!-- Introducir Datos    -->
    <!------------------------->

    <%
      HttpSession s = request.getSession();
      String machine = (String)s.getAttribute("machine");
      String port = (String)s.getAttribute("port");
      String squema = (String)s.getAttribute("squema");
      String table = (String)s.getAttribute("table");
      String targetDir = (String)s.getAttribute("targetDir");
    %>

    
    <!-- ----------- ENTRADA DE DATOS ----------- -->
    <form action="/swbd/FrontalController" method='POST' 
          onSubmit="return checkInputData(document.getElementById('fileName').value);">
      <TABLE class='divIntermedio' width='100%'>
        <TR>
          <td align="left"> 
            <b> <%out.print("ORIGEN (MySQL):"); %> </b>
          </td>
          <TD nowrap width='75%' align='right'>
             <a href='menu.jsp'><img src='styles/menu.gif' border='none' alt='Visualizar menú principal' align='center'></a>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>
        </TR>

        <TR>
          <TD nowrap width='20%' align='left' valign='middle' >
            <b> M&aacute;quina: </b> &nbsp;&nbsp;&nbsp;
            <select name="machineFrom" style="width:100px">
              <option value="mysqldesa"> mysqldesa
              <option value="mysqlpre"> mysqlpre
              <option value="mysqlpro1"> mysqlpro1
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>
          <TD nowrap align='right'>
            Menú Principal&nbsp;&nbsp;&nbsp;
          </TD>
        </TR>

        
        <TR>
          <td width="40%" align="left">  
            <b> <%out.print("Directorio: ");%> </b> <%out.print(targetDir);%>
          </td>
          <TD nowrap align='right'>
            &nbsp;&nbsp;&nbsp;
          </TD>
        </TR>

        <TR>
          <TD width="40%" align="left">  
            <b> <%out.print("Nombre de fichero: ");%> </b> 
            <input type='text' name='fileName' id='fileName' size='30' class='fondoBlanco' />
          </TD>
        </TR>

        
        <TR>
          <TD nowrap  align='left' valign='middle'>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>
        </TR>
  
        <TR>
          <td width="40%" align="left">  
            <b> <%out.print("DESTINO (IQ): ");%> </b> 
          </td>
          <TD nowrap align='right'>
            &nbsp;&nbsp;&nbsp;
          </TD>
        </TR>
 
        <TR>
          <TD nowrap width='20%' align='left' valign='middle' >
            <b> M&aacute;quina: </b> &nbsp;&nbsp;&nbsp;
            <select name="machineTo" style="width:100px">
              <option value="abeto"> abeto
              <option value="haya"> haya
              <option value="sauce"> sauce
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>
        </TR>

        <TR>
          <td width="40%" align="left">  
            <b> <%out.print("Directorio destino: ");%> </b> <%out.print(targetDir);%>
          </td>
          <TD nowrap align='right'>
            &nbsp;&nbsp;&nbsp;
          </TD>
        </TR>
 
        <TR>
          <TD nowrap  align='left' valign='middle'>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>
        </TR>
  
        <TR>
          <TD align='center'>
            <input type="submit" value="  Tx  " />
            <input type='hidden' name="operation" value='txDataFile.do'>
          </TD>
        </TR>
  
      </TABLE>
    </form>
  </BODY>
</HTML>

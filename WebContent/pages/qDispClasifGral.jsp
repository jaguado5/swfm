<%@ page language='java'
                contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
                import='java.util.*'
                import='swfm.model.RegClasifGeneral'%>

<!-- qDispClasifGral.jsp  2013/Oct/08 -->

<html>

  <HEAD>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type">
  	<LINK media=all href='styles/estilos.css' type=text/css rel=StyleSheet>
    <title> SWFM - Utilidades de Administración del Foro Malaguista </title>
  </HEAD>

  <body>
  
    <%
      boolean errorExp = false;
      boolean errorUsr = false;
      int  jornada = (Integer)session.getAttribute("jornada");
      List<RegClasifGeneral> clasifGral= (List<RegClasifGeneral>)session.getAttribute("clasifGral");
      int numRegClasifGral = clasifGral.size();
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
          Quiniela: Clasificación General</TD>
        <TD nowrap align='right'>
          <%out.print("Jornada: "+jornada); %>
        </TD>
      </TR>
    </TABLE>

    <br>
    <br>

    <!----------------------------------->
    <!-- COMPROBAR SI HAY REGISTROS    -->
    <!----------------------------------->
    <%
      if (numRegClasifGral==0) {
        errorExp = true;
    %>
        <TABLE class='divError' width='100%'>
          <tr>
            <td align="left"> </td>
          </tr>
          <tr>
            <td align="left"> <b> <%out.print("Clasif Gral No ha podido ser generada!!"); %> </b>  </td>
          </tr>
          <tr>
            <td align="left"> </td>
          </tr>
        </TABLE>

        <br>
        <br>
    <% 
      }
    %>


    <!------------------------->
    <!-- TOTALES A MODIFICAR -->
    <!------------------------->
    <TABLE class='divIntermedio' width='100%'>
      <tr>
        <td align="left"> <b> <%out.print("Total Registros a Modificar:"); %> </b>  </td>
        <TD nowrap width='75%' align='right'>
          <a href='pages/menuRoot.jsp'><img src='styles/menu.gif' border='none' alt='Visualizar menú principal' align='center'></a>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </TD>
      </tr>
      <tr>
        <td width="40%" align="left"> <%out.print("Total Participantes: "+numRegClasifGral);%> </td>
        <TD nowrap align='right'>Menú Principal
          &nbsp;&nbsp;&nbsp;
        </TD>
      </tr>
      <tr>
        <td align="left"> <%out.print("  ");%> </td>
      </tr>
    </TABLE>

    <BR>
    <br>


    <!---------------------------------------->
    <!-- VISUALIZAR DATOS SI NO HAY ERRORES -->
    <!---------------------------------------->
    <%
      if (errorExp==false && errorUsr==false) {
    %>


      <!------------------------------------------------->
      <!-- TABLA MySQL SEGUIMIENTO Registros Filtrados -->
      <!------------------------------------------------->
  
      <TABLE class='divIntermedio3' width='100%'>

          <%
            Iterator it = clasifGral.iterator();
            if (it.hasNext()) {
          %>
          <tr>
            <td align="center"><b><u><i>Anterior</i></u></b></td>
            <td align="center"><b><u><i>Tendencia</i></u></b></td>
            <td align="center"><b><u><i>Puesto</i></u></b></td>
            <td align="left"><b><u><i>Usuario</i></u></b></td>
            <td align="center"><b><u><i>Puntos</i></u></b></td>
            <td align="center"><b><u><i>T.J.</i></u></b></td>
            <td align="center"><b><u><i>Q.J.</i></u></b></td>
            <td align="center"><b><u><i>Partidos</i></u></b></td>
            <td align="center"><b><u><i>%Aciert.</i></u></b></td>
            <td align="center"><b><u><i>P.Anter.</i></u></b></td>
            <td align="center"><b><u><i>J.Actual</i></u></b></td>
            <td align="center"><b><u><i>Total</i></u></b></td>
          </tr>
  
          <% while (it.hasNext()) {
               RegClasifGeneral reg = (RegClasifGeneral)it.next(); %>
              <tr>
                <td width="5%" align="center"> <%out.print(reg.getPosAnterior());%> </td>
                <td width="2%" align="center"> <%out.print(reg.getFlecha());%> </td>
                <td width="5%" align="center"> <%out.print(reg.getPosActual());%> </td>
                <td width="5%" align="left"> <%out.print(reg.getUser());%> </td>
                <td width="5%" align="center"> <%out.print(reg.getPuntosTotal());%> </td>
                <td width="5%" align="center"> <%out.print(reg.getTj());%> </td>
                <td width="5%" align="center"> <%out.print(reg.getQj());%> </td>
                <td width="5%" align="center"> <%out.print(reg.getPartidos());%> </td>
                <td width="5%" align="center"> <%out.print(reg.getPorcentaje());%> </td>
                <td width="5%" align="center"> <%out.print(reg.getPuntosAnterior());%> </td>
                <td width="7%" align="center"> <%out.print(reg.getPuntosActual());%> </td>
                <td width="5%" align="center"> <%out.print(reg.getTotal());%> </td>
              </tr>
          <% } %>
  
        <%
          }
          else { %>
            <tr>
              <td width="50%" align="left"> No hay sido generada para esta jornada </td>
            </tr>
        <%}%>
  
      </TABLE>

      <br>
      <br>





      <!----------------------->
      <!-- POSIBLES ACCIONES -->
      <!----------------------->  
      <TABLE class='divAcciones' width='100%'>
        <tr>
          <td align="left"> <b> <%out.print("Acciones: "); %> </b>  </td>
        </tr> 

        <tr>
          <td> <br> </td>
        </tr>

        <tr>
          <form action="/swfm/FrontalController" method='POST' name=Index>
            <td width="20%" align="left"> 
              <input type="submit" value="Exportar a Fichero Texto" />
              <input type='hidden' name="operation" value='qDownloadClasifGralTxt.do'>
            </td>
          </form>
        </tr>

        <tr>
          <td> <br> </td>
        </tr>

        <tr>
          <form action="/swfm/FrontalController" method='POST' name=Index>
            <td width="20%" align="left"> 
              <input type="submit" value="Exportar a Fichero HTML" />
              <input type='hidden' name="operation" value='qDownloadClasifGralHtml.do'>
            </td>
          </form>
        </tr>

        <tr>
          <td align="left"> <br> </td>
        </tr>

        <tr>
          <td align="left"> <br> </td>
        </tr>


      </TABLE>

    <% 
      }
    %>

  </body>
</html>
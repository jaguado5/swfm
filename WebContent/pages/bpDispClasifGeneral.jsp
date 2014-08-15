<%@ page language='java'
                contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
                import='java.util.*'
                import='swfm.view.BestPlayerClasifGeneralRegPlayer'%>

                
<!-- bpDispClasifGeneral.jsp   2013/Oct/14 v01.0g -->

<html>

  <head>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type">
    <LINK media=all href='/swfm/pages/styles/estilos.css' type=text/css rel=StyleSheet>
    <title> SWFM - Utilidades de Administración del Foro Malaguista </title>
  </head>  
  
  
  <body>

    <%
      boolean errorClasif = false;
      int  jornada = (Integer)session.getAttribute("jornada");
      List<BestPlayerClasifGeneralRegPlayer> playerListPondered= (List<BestPlayerClasifGeneralRegPlayer>)session.getAttribute("playerListPondered");
      List<BestPlayerClasifGeneralRegPlayer> playerListOrderByPoints= (List<BestPlayerClasifGeneralRegPlayer>)session.getAttribute("playerListOrderByPoints");
      List<BestPlayerClasifGeneralRegPlayer> playerListOrderByAvg= (List<BestPlayerClasifGeneralRegPlayer>)session.getAttribute("playerListOrderByAvg");
      int numReg = playerListOrderByPoints.size();
      String header = (String)session.getAttribute("header");
    %>


    <!---------------->
    <!--  CABECERA  -->
    <!---------------->
    <div style="text-align: center;">
      <img alt="cabecera" src="<%out.print(header);%>" >
    </div>
    <br>

    <TABLE class='divInicio' width='100%' cellSpacing='0' cellPadding='15'>
      <TR>
        <TD nowrap align='left'>
          Mejor Jugador: Clasificación General</TD>
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
      if (numReg==0) {
        errorClasif = true;
    %>
        <TABLE class='divError' width='100%'>
          <tr>
            <td align="left"> </td>
          </tr>
          <tr>
            <td align="left"> <b> <%out.print("Clasificación NO ha podido ser generada!!"); %> </b>  </td>
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


    <!------------------->
    <!-- SUB-CABECERA  -->
    <!------------------->
    <TABLE class='divIntermedio' width='100%'>
    
      <tr>
        <td align="left" width='1%'> 
        </td>
        
        <td align="left" width='40%'> 
          <font size="4"> <b>  <%out.print("   " + "Número de jugadores: "+numReg);%> </b> </font> 
        </td>
        <TD nowrap width='60%' align='right'>
          <a href='pages/menuRoot.jsp'><img src='/swfm//pages/styles/menu.gif' border='none' alt='Visualizar menú principal'></a>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </TD>
      </tr>
      
      <tr>
        <td align="left">  </td>
        <td align="left">  </td>
        <TD nowrap align='right'>Menú Principal
          &nbsp;&nbsp;&nbsp;
        </TD>
      </tr>
    </TABLE>

    <BR>
    <br>


    <!---------------------------------------->
    <!-- VISUALIZAR DATOS SI NO HAY ERRORES -->
    <!---------------------------------------->
    <%
      if (errorClasif==false) {
    %>

      <!---------------------------------------------->
      <!-- Cabecera de Clasificación Ponderada      -->
      <!---------------------------------------------->
      <TABLE class='divAmarillo' width='100%'>

        <!-- PRIMERA FILA -->
        <tr>
            <td> <br> </td>
        </tr>

        <!-- SEGUNDA FILA -->
        <tr>
            <td align="center">  <font size="4"> <b>Clasificación General Ponderada</b> </font> </td>
        </tr>

        <!-- TERCERA FILA -->
        <tr>
            <td> <br> </td>
        </tr>

      </TABLE>

      <br>
      <br>

      <!----------------------------------->
      <!-- Clasificación Ponderada       -->
      <!----------------------------------->
  
      <TABLE class='divPlayers' width='80%' align="center" border='1' > 
  
        <%
          Iterator<BestPlayerClasifGeneralRegPlayer> it = playerListPondered.iterator();
          if (it.hasNext()) {
		    int puesto = 0;
        %>
          <tr>
            <td> </td>
            <td align="center"> <b>Puesto</b> </td>
            <td align="center"> <b>Jugador</b> </td>
            <td align="center"> <b>Total Promedio</b> </td>
            <td align="center"> <b>Porcentaje</b> </td>
            <td align="center"> <b>Promedio1</b> </td>
            <td align="center"> <b>Total Puntos</b> </td>
            <td align="center"> <b>Promedio2</b> </td>
            <td align="center"> <b>Número Jornadas</b> </td>
          </tr>
  
          <% while (it.hasNext()) {
               BestPlayerClasifGeneralRegPlayer reg = it.next(); 
			   puesto++;
			   String imgName = reg.getPicture();
          %>
              <tr>
			  
			  	<% if (imgName!=null) { %>
					<td width="5%" align="center"> <img src="<%out.print("/swfm/" + imgName);%>"> </td>
			    <% } else { %>
                    <td width="5%" align="center">  </td>
		        <% }%>
                <!-- <td width="5%" align="center"> <img src="/swfm//pages/images/players/tmp/WIL.jpg"> </td> -->
                <td width="5%" align="center">  <%out.print(puesto + "");%> </td>
                <td width="15%" align="center"> <%out.print(reg.getPlayer());%> </td>
                <td width="10%" align="center"> <%out.print(reg.getTotalPromedio());%> </td>
                <td width="10%" align="center"> <%out.print(reg.getAvgVotante());%> </td>
                <td width="10%" align="center"> <%out.print(reg.getPromedio1());%> </td>
                <td width="10%" align="center"> <%out.print(reg.getTotalPoints());%> </td>
                <td width="10%" align="center"> <%out.print(reg.getPromedio2());%> </td>
                <td width="10%" align="center"> <%out.print(reg.getNumJornadas());%> </td>
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

	  
      <!---------------------------------------------->
      <!-- Cabecera de Clasificación por Porcentaje -->
      <!---------------------------------------------->
      <TABLE class='divAmarillo' width='100%'>

        <!-- PRIMERA FILA -->
        <tr>
            <td> <br> </td>
        </tr>

        <!-- SEGUNDA FILA -->
        <tr>
            <td align="center">  <font size="4"> <b>Clasificación por Porcentaje</b> </font> </td>
        </tr>

        <!-- TERCERA FILA -->
        <tr>
            <td> <br> </td>
        </tr>

      </TABLE>

      <br>
      <br>

      <!----------------------------------->
      <!-- Clasificación por Porcentaje  -->
      <!----------------------------------->
  
      <TABLE class='divPlayers' width='80%' align="center" border='1' > 

        <%
          it = playerListOrderByAvg.iterator();
          if (it.hasNext()) {
            int puesto = 0;
        %>
          <tr>
            <td> </td>
            <td align="center"> <b>Puesto</b> </td>
            <td align="center"> <b>Jugador</b> </td>
            <td align="center"> <b>Porcentaje</b> </td>
            <td align="center"> <b>Número Jornadas</b> </td>
            <td align="center"> <b>Número Votos</b> </td>
            <td align="center"> <b>Total Puntos</b> </td>
          </tr>
  
          <% while (it.hasNext()) {
               BestPlayerClasifGeneralRegPlayer reg = it.next(); 
               puesto++;
               String imgName = reg.getPicture();
          %>
              <tr>
              
                  <% if (imgName!=null) { %>
                    <td width="5%" align="center"> <img src="<%out.print("/swfm/" + imgName);%>"> </td>
                <% } else { %>
                    <td width="5%" align="center">  </td>
                <% }%>
                <!-- <td width="5%" align="center"> <img src="/swfm//pages/images/players/tmp/WIL.jpg"> </td> -->
                <td width="8%" align="center">  <%out.print(puesto + "");%> </td>
                <td width="20%" align="center"> <%out.print(reg.getPlayer());%> </td>
                <td width="14%" align="center"> <%out.print(reg.getAvgVotante());%> </td>
                <td width="10%" align="center"> <%out.print(reg.getNumJornadas());%> </td>
                <td width="10%" align="center"> <%out.print(reg.getNumVotos());%> </td>
                <td width="10%" align="center"> <%out.print(reg.getTotalPoints());%> </td>
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

      
      <!------------------------------------------>
      <!-- Cabecera de Clasificación por Puntos -->
      <!------------------------------------------>
      <TABLE class='divAmarillo' width='100%'>

        <!-- PRIMERA FILA -->
        <tr>
            <td> <br> </td>
        </tr>

        <!-- SEGUNDA FILA -->
        <tr>
            <td align="center"> <font size="4"> <b>Clasificación por Puntos</b> </font> </td>
        </tr>

        <!-- TERCERA FILA -->
        <tr>
            <td> <br> </td>
        </tr>

      </TABLE>

      <br>
      <br>

      <!------------------------------->
      <!-- Clasificación por Puntos  -->
      <!------------------------------->
  
      <TABLE class='divPlayers' width='80%' align="center" border='1' > 
  
        <%
          it = playerListOrderByPoints.iterator();
          if (it.hasNext()) {
            int puesto = 0;
        %>
          <tr>
            <td> </td>
            <td align="center"> <b>Puesto</b> </td>
            <td align="center"> <b>Jugador</b> </td>
            <td align="center"> <b>Total Puntos</b> </td>
            <td align="center"> <b>Número Jornadas</b> </td>
            <td align="center"> <b>Número Votos</b> </td>
            <td align="center"> <b>Puntos / Jornada</b> </td>
            <td align="center"> <b>Puntos / Votante</b> </td>
          </tr>
  
          <% while (it.hasNext()) {
               BestPlayerClasifGeneralRegPlayer reg = it.next(); 
               puesto++;
               String imgName = reg.getPicture();
               %>
              <tr>
                  <% if (imgName!=null) { %>
                    <td width="5%" align="center"> <img src="<%out.print("/swfm/" + imgName);%>"> </td>
                <% } else { %>
                    <td width="5%" align="center">  </td>
                <% }%>
                <td width="10%" align="center"> <%out.print(puesto + "");%> </td>
                <td width="25%" align="center"> <%out.print(reg.getPlayer());%> </td>
                <td width="15%" align="center"> <%out.print(reg.getTotalPoints());%> </td>
                <td width="15%" align="center"> <%out.print(reg.getNumJornadas());%> </td>
                <td width="15%" align="center"> <%out.print(reg.getNumVotos());%> </td>
                <td width="8%" align="center"> <%out.print(reg.getAvgJornada());%> </td>
                <td width="8%" align="center"> <%out.print(reg.getAvgVotante());%> </td>
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


    <% 
      }
    %>

  </body>
</html>
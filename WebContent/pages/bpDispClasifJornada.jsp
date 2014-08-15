<%@ page language='java'
                contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
                import='java.util.*'
                import='swfm.model.GameMcf'
                import='swfm.view.BestPlayerClasifGeneralRegPlayer'
                import='swfm.view.BestPlayerClasifJornadaRegPlayer'
                import='swfm.view.BestPlayerClasifJornadaRegUser'%>

<!-- bpDispClasifJornada.jsp 2013/Oct/14 v01.0f -->

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
      GameMcf  gameMcf = (GameMcf)session.getAttribute("gameMcf");
      String  teamTotalPoints = (String)session.getAttribute("teamTotalPoints");
      String  teamAverage = (String)session.getAttribute("teamAverage");
      List<BestPlayerClasifJornadaRegPlayer> playerRegList= (List<BestPlayerClasifJornadaRegPlayer>)session.getAttribute("playerRegList");
      List<BestPlayerClasifJornadaRegUser> bodyRegList= (List<BestPlayerClasifJornadaRegUser>)session.getAttribute("bodyRegList");
      List<BestPlayerClasifGeneralRegPlayer> playerClasifOrderByAvg= (List<BestPlayerClasifGeneralRegPlayer>)session.getAttribute("playerClasifOrderByAvg");
      int numRegClasifJornada = bodyRegList.size();
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
          Mejor Jugador de la Jornada</TD>
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
      if (numRegClasifJornada==0) {
        errorClasif = true;
    %>
        <TABLE class='divError' width='100%'>
          <tr>
            <td align="left"> </td>
          </tr>
          <tr>
            <td align="left"> <b> <%out.print("Clasif Jornada NO ha podido ser generada!!"); %> </b>  </td>
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
        
        <td align="left" width='24%'> 
          <font size="4"> <b> <%out.print(gameMcf.getGame()+": "+ gameMcf.getScore());%> </b></font>
        </td>
        
        <TD nowrap width='75%' align='right'>
          <a href='pages/menuRoot.jsp'><img src='/swfm/pages/styles/menu.gif' border='none' alt='Visualizar men� principal' align="center"></a>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </TD>
      </tr>
      
      <tr>
        <td align="left"> 
        </td>

        <td align="left">
          <font size="3"> <%out.print("   " + "Número de votaciones: "+numRegClasifJornada);%> </font>
        </td>

        <TD nowrap align='right'>Menú Principal
          &nbsp;&nbsp;&nbsp;
        </TD>
      </tr>
      
      <tr>
          <td> <br> </td>
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

      <!------------------------>
      <!-- DATOS DE JUGADORES -->
      <!------------------------>

      <TABLE class='divAmarillo' width='100%'>

        <!-- PRIMERA FILA -->
        <tr>
            <td width="1%" align="center">  </td>
            <td width="9%" align="left">   </td>
          
          <%
          Iterator<BestPlayerClasifJornadaRegPlayer> itPlayersName = playerRegList.iterator();
          while (itPlayersName.hasNext()) {
            BestPlayerClasifJornadaRegPlayer playerReg = itPlayersName.next();
            String player = playerReg.getPlayer();
            
            int length=0;
            if (player.length()>=14) {
              length = 14;
            }
            else {
              length = player.length();
            }
          %>
            <td width="6%" align="center"> <b><%out.print(player.substring(0,length));%> </b></td>
          <%
          }
          %>

            <td width="3%" align="center"> <b><%out.print("Team");%></b> </td>
            <td width="3%" align="center"> <b><%out.print("Team");%></b> </td>
        </tr>

        <!-- SEGUNDA FILA -->
        <tr>
            <td width="1%" align="center">  </td>
            <td width="9%" align="left">  <b>Total Ptos:</b> </td>
          
          <%
          Iterator<BestPlayerClasifJornadaRegPlayer> itPlayersPtos = playerRegList.iterator();
          while (itPlayersPtos.hasNext()) {
            BestPlayerClasifJornadaRegPlayer playerReg = itPlayersPtos.next();
            int puntos = playerReg.getTotalPuntos();
          %>
            <td width="6%" align="center"> <%out.print(puntos);%> </td>
          <%
          }
          %>

            <td width="3%" align="center"> <%out.print(teamTotalPoints);%> </td>
            <td width="3%" align="center"> <%out.print(" ");%> </td>
        </tr>

        <!-- TERCERA FILA -->
        <tr>
            <td width="1%" align="center">  </td>
            <td width="9%" align="left"> <b>Promedio:</b> </td>
          
          <%
          Iterator<BestPlayerClasifJornadaRegPlayer> itPlayersAvrg = playerRegList.iterator();
          while (itPlayersAvrg.hasNext()) {
            BestPlayerClasifJornadaRegPlayer playerReg = itPlayersAvrg.next();
            String average = playerReg.getAverage();
          %>
            <td width="6%" align="center"> <%out.print(average);%> </td>
          <%
          }
          %>

            <td width="3%" align="center"> <%out.print("");%> </td>
            <td width="3%" align="center"> <%out.print(teamAverage);%> </td>
        </tr>

      </TABLE>

      <br>
      <br>

      <!----------------------->
      <!-- DATOS DE USUARIOS -->
      <!----------------------->
  
      <TABLE class='divUsers' width='100%'>
      
        <%
          Iterator<BestPlayerClasifJornadaRegUser> it = bodyRegList.iterator();
          if (it.hasNext()) {
        %>
          <tr>
            <td align="left"><b><u><i>Usuario</i></u></b></td>
            <td align="center"><b><u><i>J1</i></u></b></td>
            <td align="center"><b><u><i>J2</i></u></b></td>
            <td align="center"><b><u><i>J3</i></u></b></td>
            <td align="center"><b><u><i>J4</i></u></b></td>
            <td align="center"><b><u><i>J5</i></u></b></td>
            <td align="center"><b><u><i>J6</i></u></b></td>
            <td align="center"><b><u><i>J7</i></u></b></td>
            <td align="center"><b><u><i>J8</i></u></b></td>
            <td align="center"><b><u><i>J9</i></u></b></td>
            <td align="center"><b><u><i>J10</i></u></b></td>
            <td align="center"><b><u><i>J11</i></u></b></td>
            <td align="center"><b><u><i>J12</i></u></b></td>
            <td align="center"><b><u><i>J13</i></u></b></td>
            <td align="center"><b><u><i>J14</i></u></b></td>
            <td align="center"><b><u><i>Tot</i></u></b></td>
            <td align="center"><b><u><i>Avg</i></u></b></td>
          </tr>
  
          <% while (it.hasNext()) {
               BestPlayerClasifJornadaRegUser reg = (BestPlayerClasifJornadaRegUser)it.next(); 
               String[] puntos = reg.getPuntos();
               %>
              <tr>
                <td width="10%" align="left"> <%out.print(reg.getUser());%> </td>
                <td width="6%" align="center"> <%out.print(puntos[0]);%> </td>
                <td width="6%" align="center"> <%out.print(puntos[1]);%> </td>
                <td width="6%" align="center"> <%out.print(puntos[2]);%> </td>
                <td width="6%" align="center"> <%out.print(puntos[3]);%> </td>
                <td width="6%" align="center"> <%out.print(puntos[4]);%> </td>
                <td width="6%" align="center"> <%out.print(puntos[5]);%> </td>
                <td width="6%" align="center"> <%out.print(puntos[6]);%> </td>
                <td width="6%" align="center"> <%out.print(puntos[7]);%> </td>
                <td width="6%" align="center"> <%out.print(puntos[8]);%> </td>
                <td width="6%" align="center"> <%out.print(puntos[9]);%> </td>
                <td width="6%" align="center"> <%out.print(puntos[10]);%> </td>
                <td width="6%" align="center"> <%out.print(puntos[11]);%> </td>
                <td width="6%" align="center"> <%out.print(puntos[12]);%> </td>
                <td width="6%" align="center"> <%out.print(puntos[13]);%> </td>
                <td width="3%" align="center"> <%out.print(reg.getTotalPoints());%> </td>
                <td width="3%" align="center"> <%out.print(reg.getAverage());%> </td>
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



      <!---------------------------------->
      <!-- Clasificación de Jugadores   -->
      <!-- participantes en la Jornada. -->
      <!---------------------------------->
      <TABLE class='divIntermedio' width='100%'>

        <tr>
          <td> <br> </td>
        </tr>

        <tr>
          <td align="left" width='1%'> 
          </td>
          <td align="left" width='99%'>
             <font size="4"> <b> Clasificación de la Jornada</b> </font>
          </td>
        </tr>

        <tr>
          <td align="left" width='1%'> 
          <td width="40%" align="left"> 
             <font size="3"><%out.print("Jugadores participantes en el partido: "+playerClasifOrderByAvg.size());%></font> 
          </td>
        </tr>

        <tr>
          <td> <br> </td>
        </tr>
      </TABLE>

      <br>
      <br>

      
      <!----------------------------------->
      <!-- Clasificación por Porcentaje  -->
      <!----------------------------------->
  
      <!-- Cabecera de Clasificación por Porcentaje -->
      <TABLE class='divAmarillo' width='100%'>
        <!-- PRIMERA FILA -->
        <tr>
            <td> <br> </td>
        </tr>

        <!-- SEGUNDA FILA -->
        <tr>
            <td align="center"> <font size="4"> <b>Clasificación por Porcentaje:</b> </font> </td>
        </tr>

        <!-- TERCERA FILA -->
        <tr>
            <td> <br> </td>
        </tr>

      </TABLE>

      <br>
      <br>

      <!-- Body de Clasificaci�n por Porcentaje -->
      <TABLE class='divPlayers' width='80%' align="center" border='1' > 

        <%
          Iterator<BestPlayerClasifGeneralRegPlayer> itPlyAvg = playerClasifOrderByAvg.iterator();
          if (itPlyAvg.hasNext()) {
            int puesto = 0;
        %>
          <tr>
            <td> </td>
            <td align="center"> <b>Puesto</b> </td>
            <td align="center"> <b>Jugador</b> </td>
            <td align="center"> <b>Porcentaje</b> </td>
            <td align="center"> <b>Número Votos</b> </td>
            <td align="center"> <b>Total Puntos</b> </td>
          </tr>
  
          <% while (itPlyAvg.hasNext()) {
               BestPlayerClasifGeneralRegPlayer reg = itPlyAvg.next(); 
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
                <td width="20%" align="center"> <%out.print(reg.getPlayer());%> </td>
                <td width="10%" align="center"> <%out.print(reg.getAvgVotante());%> </td>
                <td width="10%" align="center"> <%out.print(reg.getNumVotos());%> </td>
                <td width="10%" align="center"> <%out.print(reg.getTotalPoints());%> </td>
              </tr>
          <% } %>
  
        <%} else {%>
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
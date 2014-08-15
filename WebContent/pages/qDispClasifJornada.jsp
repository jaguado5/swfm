<%@ page language='java'
                contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
                import='java.util.*'
                import='swfm.model.RegClasifJornada'
                import='swfm.model.Game'
                import='swfm.model.MasterResult'%>

<!-- qDispClasifJornada.jsp -->

<html>

  <HEAD>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type">
    <LINK media=all href='styles/estilos.css' type=text/css rel=StyleSheet>
    <title> SWFM - Utilidades de Administración del Foro Malaguista </title>
  </HEAD>

  <body>

    <%
      boolean errorClasif = false;
      int  jornada = (Integer)session.getAttribute("jornada");
      List<RegClasifJornada> clasifJornada= (List<RegClasifJornada>)session.getAttribute("clasifJornada");
      MasterResult masterResut = (MasterResult)session.getAttribute("masterResult");
      List<Game> gamesList= (List<Game>)session.getAttribute("gamesList");
      int numRegClasifJornada = clasifJornada.size();
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
          Quiniela: Clasificación de la Jornada</TD>
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


    <!------------------->
    <!-- SUB-CABECERA  -->
    <!------------------->
    <TABLE class='divIntermedio' width='100%'>
      <tr>
         <td align="left" width='1%'> 
        </td>
        
        <td align="left" width='24%'> 
          <font size="4"> <b> <%out.print("Jornada: "+jornada); %> </b> </font>
        </td>
        
        <TD nowrap width='75%' align='right'>
          <a href='pages/menuRoot.jsp'><img src='styles/menu.gif' border='none' alt='Visualizar menú principal' align="center"></a>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </TD>
      </tr>


      <tr>
        <td align="left">  
        </td>
        <td align="left"> 
           <font size="3"> <%out.print("Número de participantes: "+numRegClasifJornada);%> </font>
        </td>
        <TD nowrap align='right'>Menú Principal
          &nbsp;&nbsp;&nbsp;
        </TD>
      </tr>

      <tr>
        <td align="left">  </td>
        <td align="left">  </td>
        <td align="left">  </td>
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

      <!------------------------------------------------->
      <!-- Cabecera con los Resultados de los Pastidos -->
      <!------------------------------------------------->
	  <!-- <td align="center" width="5%"> <img alt="cabecera" src="/swfm/pages/images/teams/grn.png"> </td> -->

	  
      <TABLE class='divIntermedio2' width='100%'>

	    <!-- First Row: Escudo Team1 -->
        <tr>
          <td width="5%" align="center"> </td>
          <td width="10%" align="left">  </td>
          <td width="5%" align="center">  </td>
          <td width="5%" align="center">  </td>

          <% for (int i=0; i<15; i++) { 
               String imgName = gamesList.get(i).getEscudo1();
		    if (imgName!=null) { %>
		      <td align="center" width="5%"> <img alt="cabecera" src="<%out.print("/swfm/" + imgName);%>"> </td>
		    <% } else { %>
              <td width="5%" align="center">  </td>
		    <% }%>
          <% } %>

		  </tr>

	    <!-- Second Row-->
		<tr>
          <td width="5%" align="center">  </td>
          <td width="10%" align="center">   </td>
          <td width="5%" align="center">  </td>
          <td width="5%" align="center">  </td>

            <% for (int i=0; i<15; i++) { %>
                <td align="center" width="5%"> <font face="arial" size="1"> <b><i><%out.print(gamesList.get(i).getTeam1());%></i></b> </font> </td>
            <% } %>
        </tr>

	    <!-- Third Row-->
        <tr>
            <% for (int i=0; i<4; i++) { %>
                <td align="center"></td>
            <% } %>
            <% for (int i=0; i<15; i++) { %>
                <td align="center"> <font face="arial" size="1"> <b><i><%out.print(gamesList.get(i).getScore());%></i></b> </font> </td>
            <% } %>
        </tr>

	    <!-- Fourth Row-->
        <tr>
            <% for (int i=0; i<4; i++) { %>
                <td align="center"></td>
            <% } %>
            <% for (int i=0; i<15; i++) { %>
                <td align="center"> <font face="arial" size="1"> <b><i><%out.print(gamesList.get(i).getTeam2());%></i></b> </font> </td>
            <% } %>
          </tr>

	    <!-- Fifth Row: Escudo Team2 -->
        <tr>
          <td width="5%" align="center"> </td>
          <td width="10%" align="left">  </td>
          <td width="5%" align="center">  </td>
          <td width="5%" align="center">  </td>

          <% for (int i=0; i<15; i++) { 
		    String imgName = gamesList.get(i).getEscudo2();
		    if (imgName!=null) { %>
		      <td align="center" width="5%"> <img alt="cabecera" src="<%out.print("/swfm/" + imgName);%>"> </td>
		    <% } else { %>
              <td width="5%" align="center">  </td>
		    <% }%>
          <% } %>
        </tr>

	    <!-- Sixth Row-->
        <tr>
            <% for (int i=0; i<4; i++) { %>
                <td align="center"></td>
            <% } %>
            <% for (int i=0; i<15; i++) { %>
                <td align="center"> <hr> </td>
            <% } %>
        </tr>

	    <!-- Seventh Row-->
		  <tr>
          <td width="5%" align="center">  </td>
          <td width="10%" align="left">  <b> Resultados </b>  </td>
          <td width="5%" align="center">  </td>
          <td width="5%" align="center">  </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(0));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(1));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(2));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(3));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(4));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(5));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(6));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(7));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(8));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(9));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(10));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(11));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(12));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(13));%> </td>
          <td width="5%" align="center"> <%out.print(masterResut.getGame(14));%> </td>
        </tr>

        <tr>
          <td width="5%" align="center"> </td>
        </tr>

      </TABLE>

      <br>
      <br>

      <!--------------------------------->
      <!-- CLASIFICACION DE LA JORNADA -->
      <!--------------------------------->
  
      <TABLE class='divIntermedio3' width='100%'>
  
        <%
          Iterator<RegClasifJornada> it = clasifJornada.iterator();
          if (it.hasNext()) {
        %>
          <tr>
            <td align="center"><b><u><i>Puesto</i></u></b></td>
            <td align="left"><b><u><i>Usuario</i></u></b></td>
            <td align="center"><b><u><i>Aciertos</i></u></b></td>
            <td align="center"><b><u><i>T.J.</i></u></b></td>
            <td align="center"><b><u><i>P1</i></u></b></td>
            <td align="center"><b><u><i>P2</i></u></b></td>
            <td align="center"><b><u><i>P3</i></u></b></td>
            <td align="center"><b><u><i>P4</i></u></b></td>
            <td align="center"><b><u><i>P5</i></u></b></td>
            <td align="center"><b><u><i>P6</i></u></b></td>
            <td align="center"><b><u><i>P7</i></u></b></td>
            <td align="center"><b><u><i>P8</i></u></b></td>
            <td align="center"><b><u><i>P9</i></u></b></td>
            <td align="center"><b><u><i>P10</i></u></b></td>
            <td align="center"><b><u><i>P11</i></u></b></td>
            <td align="center"><b><u><i>P12</i></u></b></td>
            <td align="center"><b><u><i>P13</i></u></b></td>
            <td align="center"><b><u><i>P14</i></u></b></td>
            <td align="center"><b><u><i>P15</i></u></b></td>
          </tr>
  
          <% while (it.hasNext()) {
               RegClasifJornada reg = (RegClasifJornada)it.next(); %>
              <tr>
                <td width="5%" align="center"> <%out.print(reg.getPuesto());%> </td>
                <td width="10%" align="left"> <%out.print(reg.getUser());%> </td>
                <td width="5%" align="center"> <%out.print(reg.getAciertos());%> </td>
                <td width="5%" align="center"> <%out.print(reg.getTj());%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(0));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(1));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(2));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(3));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(4));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(5));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(6));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(7));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(8));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(9));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(10));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(11));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(12));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(13));%> </td>
                <td width="5%" align="center"> <%out.print(reg.getGame(14));%> </td>
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
              <input type='hidden' name="operation" value='qDownloadClasifJornadaTxt.do'>
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
              <input type='hidden' name="operation" value='qDownloadClasifJornadaHtml.do'>
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

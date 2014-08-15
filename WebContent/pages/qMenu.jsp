<%@ page language='java'
                contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
                import='java.util.*'
                import='swfm.model.Jornada'%>


<!-- qMenu.jsp  2013/Oct/08 -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<script language='JavaScript'>
  function visualizar() {
    var usrCode = document.getElementById('usrCode').value;
    <!-- Mostrar mensaje de aviso si el campo esta vacio -->
    if (usrCode=='') window.alert('No se ha introducido ningún código de usuario!')
    else this.location = '/swfm/FrontalController?usrCode=' + usrCode;
  }

  <!-- Comprueba si se ha pulsado la tecla ENTER, en cuyo caso llama a visualizar() -->
  function checkEnter(event) {
    var tecla = (document.all)?event.keyCode:event.which;
    if (tecla==13) visualizar();
  }

  <!-- Comprueba si se ha pulsado la tecla ENTER, en cuyo caso llama a visualizar() -->
  function checkEnter2(event) {
    window.alert('Mouse pressed!');
  }


  <!------------------------------------------------------->
  <!-- Comprobar que el argumento de entrada es un número-->
  <!------------------------------------------------------->
  function checkInputNum(data) {
    <!-- Comprobar que el campo es número, que no está vacío y que no es una cadena de blancos -->
    if (isNaN(data) || data.length==0 || /^\s+$/.test(data) ) {
        window.alert('Jornada Inválida!');
      return false;
    }
    else {
      return true;
    }
  }


  <!------------------------------->
  <!-- Fija operación a realizar -->
  <!------------------------------->
  function setOperation(op) {
    if (checkInputNum(document.getElementById('jornada').value)==false) {
      return false;
    }

    document.getElementById('myOper').value=op;
    document.myForm.submit();
  }

</script>



  <HEAD>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type">
   <LINK media=all href='styles/estilos.css' type=text/css rel=StyleSheet>
    <title> SWFM - Utilidades de Administración del Foro Malaguista </title>
  </HEAD>

  <body>

    <%
      List<Jornada> jornadasList= (List<Jornada>)session.getAttribute("jornadasList");
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
          Juego de la Quiniela
        </TD>
        <TD nowrap align='right'>
          Temporada 2013/14
        </TD>
      </TR>
    </TABLE>	
    
    <BR>
    <br>
    <br>


<!-- ----------- ENTRADA DE DATOS ----------- -->


    <form action="/swfm/FrontalController" method='POST' name='myForm' enctype="multipart/form-data">
      <TABLE class='divIntermedio' width='100%'>
        <TR>
          <TD nowrap width='15%' align='left' valign='middle' >
            &nbsp;&nbsp;&nbsp;
            Jornada:
            &nbsp;&nbsp;
            <input type='text' name='jornada' id='jornada' size='7' class='fondoBlanco' onkeypress='checkEnter(event)'/>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>

          <TD nowrap width='85%' align='right'>
            <a href='/swfm/pages/menuRoot.jsp'><img src='styles/menu.gif' border='none' alt='Visualizar men&uacute; principal' align='center'></a>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>
        </TR>


        <TR>
          <TD nowrap align='left' valign='middle' rowspan='2'>
            &nbsp;&nbsp;&nbsp;
          </TD>

          <TD nowrap align='right'>Men&uacute; Principal
              &nbsp;&nbsp;&nbsp;
          </TD>
        </TR>


        <TR>
          <TD nowrap  align='left' valign='middle'>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>
        </TR>






      <!----------------------->
      <!-- POSIBLES ACCIONES -->
      <!----------------------->  
      <TABLE class='divAcciones' width='100%'>

        <TR>
          <TD nowrap  align='left' valign='middle'>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>
        </TR>


        <TR>
          <TD>
            &nbsp;&nbsp;&nbsp;
            <input type="button" value="Clasif Jornada" onclick="setOperation('qShowClasifJornada.do')">
            <input type='hidden' id="myOper" value="operation" name="operation">
          </TD>

          <TD>
            <input type="button" value="Clasif General" onclick="setOperation('qShowClasifGeneral.do')">
            <input type='hidden' id="myOper" value="operation"  name="operation">

            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type=button value="Importar Master  File" onclick="setOperation('qUploadMasterFile.do')">

            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value="Importar Users File" onclick="setOperation('qUploadUserFile.do')">
            
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value="Procesar Jornada" onclick="setOperation('qProcessJornada.do')">
            
          </TD>

        </TR>


        <TR>
          <TD nowrap  align='left' valign='middle'>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>
        </TR>

        <tr>
          <td>
            &nbsp;&nbsp;&nbsp;
            <span class="Estilo3"><b>Fichero a Importar: </b></span>
          </td>
          <td>
            <input type=file  name="uploadFile">
          </td>
        </tr>


        <TR>
          <TD nowrap  align='left' valign='middle'>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>
        </TR>

        <TR>
          <TD>
            &nbsp;&nbsp;&nbsp;
            <input type="button" value="Def/Mod Jornada" onclick="setOperation('qEditJornada.view')">
            <input type='hidden' id="myOper" value="operation" name="operation">
          </TD>
        </TR>


        <TR>
          <TD nowrap  align='left' valign='middle'>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>
        </TR>



      </TABLE>
    </form>


	
    <!---------------------------------->
    <!-- VISUALIZACiÓN DE INFORMACIÓN -->
    <!-- YA CONTENIDA EN LA BD.       -->
    <!---------------------------------->  
	<TABLE class='divIntermedio2' width='100%'>
	
		<tr>
          <td nowrap  align='left' valign='middle'>
            &nbsp;&nbsp;
          </td>
        </tr>

		<tr>
          <td nowrap  align='left' valign='middle'>
            <font size="4"> <b>Informaci&oacute;n almacenada en BD</b> </font>
          </td>
        </tr>

		<tr>
          <td nowrap  align='left' valign='middle'>
            &nbsp;&nbsp;
          </td>
        </tr>

        <%
          Iterator<Jornada> it = jornadasList.iterator();
          if (it.hasNext()) {
        %>

		  <tr>
            <td width="20%" align="center"></td>
            <td width="10%" align="center"> <b><u><i>Jornada</i></u></b> </td>
            <td width="15%" align="center"> <b><u><i>Participantes</i></u></b> </td>
            <td width="35%" align="left"> <b><u><i>Ganadores</i></u></b> </td>
            <td width="20%" align="center"></td>
          </tr>

          <% while (it.hasNext()) {
              Jornada j = (Jornada)it.next(); %>
              <tr>
                <td></td>
                <td align="center"> <%out.print(j.getJornada());%> </td>
                <td align="center"> <%out.print(j.getQNumUsers());%> </td>
                <td align="left"> <%out.print(j.getQWinners());%> </td>
                <td></td>
              </tr>
          <%}%>
		
        <%}%>

		<tr>
          <td nowrap  align='left' valign='middle'>
            &nbsp;&nbsp;
          </td>
        </tr>

    </TABLE>

  </body>

</html>

<%@ page contentType="text/html;charset=windows-1252"%>

<!-- qPorra.jsp  2013/Sep/16 -->

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
    <META http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>
    <LINK media=all href='styles/estilos.css' type=text/css rel=StyleSheet>
    <TITLE>Servicios Web de Gesti&oacute;n de Expedientes</TITLE>
  </HEAD>

  <body>

    <!-- ----------- CABECERA ----------- -->
    
    <a href='index.html'>
      <IMG SRC='styles/logo_swfm.gif' alt='Volver a la p&aacute;gina de inicio de SWGE' border='none'>
    </a>
    
    <TABLE class='divInicio' width='100%' cellSpacing='0' cellPadding='15'>
      <TR>
        <TD nowrap align='left'>
          PPPPPPPPP</TD>
        <TD nowrap align='right'>
          RRRRRRRRRR</TD>
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
            <a href='menu.jsp'><img src='styles/menu.gif' border='none' alt='Visualizar menú principal' align='center'></a>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>
        </TR>


        <TR>
          <TD nowrap align='left' valign='middle' rowspan='2'>
            &nbsp;&nbsp;&nbsp;
          </TD>

          <TD nowrap align='right'>Menú Principal
              &nbsp;&nbsp;&nbsp;
          </TD>
        </TR>


        <TR>
          <TD nowrap  align='left' valign='middle'>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </TD>
        </TR>


      </TABLE>

      <br>
      <br>





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


      </TABLE>
    </form>
  </body>
</html>

<%@ page contentType="text/html;charset=windows-1252"%>

<!-- menu.jsp -->

<html>

  <head>
    <META http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>
    <LINK media=all href='styles/estilos.css' type=text/css rel=StyleSheet>
    <title>MySQL Monitoring Utilities</title>
  </head>


  <body>

    <a href='index.html'>
      <IMG SRC='styles/logo_swfm.gif' alt='Volver a la p&aacute;gina de inicio de SWBD' border='none'>
    </a>

    <TABLE class='divInicio' width='100%' cellSpacing='0' cellPadding='15'>
      <TR>
        <TD nowrap align='left'>
          Men&uacute; Principal
    </TD>
        <TD nowrap align='right'>
          SGEXPL BBDD MySQL
    </TD>
      </TR>
    </TABLE>

    <br>
    <br>

    <TABLE class='divIntermedio' width='100%'>
      <tr>
        <td width="20%" align="center">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
      </tr>

      <tr>
        <td width="20%" align="center">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
      </tr>

      <tr>
        <form action="/swbd/FrontalController" method='POST' name=f1>
          <td width="20%" align="center">    
            <input type="submit" value="Monitorizar Servidor MySQL" />
            <input type='hidden' name="operation" value='mysqlMonitorServer.view'>
          </td>
        </form>
      </tr>

        <tr>
            <td width="20%" align="center">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
        </tr>

        <tr>
            <form action="/swbd/FrontalController" method='POST' name=f2>
                <td width="20%" align="center"> 
                    <input type="submit" value="Seleccionar Esquema MySQL DESARROLLO" />
                    <input type='hidden' name="operation" value='mysqlSelectSchemaDesa.view'>
                </td>
                </form>
        </tr>

        <tr>
            <td width="20%" align="center">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
        </tr>

        <tr>
            <form action="/swbd/FrontalController" method='POST' name=f2>
                <td width="20%" align="center"> 
                    <input type="submit" value="Seleccionar Esquema MySQL PRE" />
                    <input type='hidden' name="operation" value='mysqlSelectSchemaPre.view'>
                </td>
                </form>
        </tr>

        <tr>
            <td width="20%" align="center">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
        </tr>

        <tr>
            <form action="/swbd/FrontalController" method='POST' name=f2>
                <td width="20%" align="center"> 
                    <input type="submit" value="Seleccionar Esquema MySQL PRODUCCION" />
                    <input type='hidden' name="operation" value='mysqlSelectSchemaPro.view'>
                </td>
                </form>
        </tr>

        <tr>
            <td width="20%" align="center">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
        </tr>

      <tr>
        <form action="/swbd/FrontalController" method='POST' name=f3>
          <td width="20%" align="center"> 
            <input type="submit" value="Espacio en Disco" />
            <input type='hidden' name="operation" value='diskSpace.do'>
          </td>
        </form>
      </tr>

        <tr>
            <td width="20%" align="center">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
        </tr>

      <tr>
        <td width="20%" align="center">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
      </tr>

      <tr>
        <form action="/swfm/FrontalController" method='POST' name=f3>
          <td width="20%" align="center"> 
            <input type="submit" value="QQQ" />
            <input type='hidden' name="operation" value='qMenu.view'>
          </td>
        </form>
      </tr>

      <tr>
        <td width="20%" align="center">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
      </tr>

      <tr>
        <form action="/swfm/FrontalController" method='POST' name=f3>
          <td width="20%" align="center"> 
            <input type="submit" value="JJJJJJ" />
            <input type='hidden' name="operation" value='qBestPlayer.view'>
          </td>
        </form>
      </tr>


      <tr>
        <td width="20%" align="center">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
      </tr>

      <tr>
        <form action="/swfm/FrontalController" method='POST' name=f3>
          <td width="20%" align="center"> 
            <input type="submit" value="PPPPP" />
            <input type='hidden' name="operation" value='qPorra.view'>
          </td>
        </form>
      </tr>

      <tr>
        <td width="20%" align="center">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
      </tr>

      <tr>
        <td width="20%" align="center">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
      </tr>

    </TABLE>
  </body>
</html>

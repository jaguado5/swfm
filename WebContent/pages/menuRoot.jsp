<%@ page language='java' contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!-menuRoot.jsp-->


<html>

  <head>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type">
    <title> swfm - Men&uacute; de Administrador </title>
  </head>

  <body>
    <jsp:include page="/pages/shared/header.html" flush="true"/>

    <br>

    <table width="100%">

      <tr align="left" valign="top">

        <td width="10%">
        <jsp:include page="/pages/shared/navigationRoot.html" flush="true"/>
        </td>

        <td width="90%" align="center">
          <br>
          <h1>Men&uacute; de Administrador</h1>
          <blockquote>
            <p align="left"><strong>Seleccione una opci&oacute;n del men&uacute;:</strong></p>
          </blockquote>
          <div align="left">
              <ul>
                <li><strong>Introducir Asignatura</strong>: introduce una nueva asignatura en el sistema.</li>
                <li><strong>Consultar Asignatura</strong>: consulta y/o modifica una asignatura actualmente existente en el sistema. </li>
                <li><strong>Introducir Profesor</strong>: registra un nuevo profesor en el sistema.</li>
                <li><strong>Consultar Profesores</strong>: visualiza los profesores actualmentes registrados en el sistema. </li>
                <li><strong>Salir</strong>: para salir de la aplicaci&oacute;n. </li>
              </ul>
          </div>
          <p align="left">&nbsp;</p>
        </td>

      </tr>

    </table>

    <jsp:include page="/pages/shared/footer.html" flush="true"/>	

  </body>

</html>


<%@ page language='java' contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
		import='java.util.*'
		import = 'javax.servlet.*' %>

<!-showError.jsp-->

<html>

  <head>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type">
    <title> GenEx - Error </title>
    <style type="text/css">
<!--
.Estilo1 {color: #FF0000}
-->
    </style>
  </head>

  <body>
    <jsp:include page="/pages/shared/header.html" flush="true"/>
    <br>

    <table width="100%">
      <tr align="left" valign="top">
        <td width="15%">
        </td>
        <td width="70%" align="left">
          <span class="Estilo1">
          <center>
            <h1><%out.print((String)request.getAttribute("errorMsg")); %></h1>
            <br>
            <h3><%out.print((String)request.getAttribute("recomen")); %></h3>
            <!-- Reenviamos a la pagina de la que venimos -->
            <form action='frontal-control.do' method='POST'>
              <input type='hidden' name="operation" value='go_page.do'>
              <input type="image" SRC="/swfm/pages/images/back.jpg" border=0 alt="Submit">
            </form>
          </center>
          </span>
        </td>
        <td width="15%">
        </td>
      </tr>
    </table>
    <br><br>
    <jsp:include page="/pages/shared/footer.html" flush="true"/>
  </body>
</html>

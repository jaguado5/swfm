<%@ page language="java" 
         contentType="text/html;charset=UTF-8" 
         pageEncoding="UTF-8"%>

<!-login.jsp-->

<html>
  <head>
    <meta content="text/html; charset=UTF-8" http-equiv="content-type">
  	<LINK media=all href='styles/estilos.css' type=text/css rel=StyleSheet>
    <title>MySQL Monitoring Utilities</title>
  </head>

  <body>

    <jsp:include page="/pages/shared/header.html" flush="true"/>
    <br>

    <TABLE class='divInicio' width='100%' cellSpacing='0' cellPadding='15'>
    	<TR>
    		<TD nowrap align='left'>
    			Inicio</TD>
    		<TD nowrap align='right'>
    			SGEXPL BBDD MySQL</TD>
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
          <td width="20%" align="center">
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <!-- Inicio de Tabla de Login -->                    
              <center>
                <table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0 
                       style='border-collapse:collapse;border:none;
                              mso-border-alt:solid windowtext .5pt;
                              mso-yfti-tbllook:480;
                              mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
                              mso-border-insideh:.5pt solid windowtext;mso-border-insidev:.5pt solid windowtext'>
                  <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;height:14.7pt'>
                    <td width=100% style='border:solid windowtext 1.0pt;
                              mso-border-alt: solid windowtext .5pt;background:#CCDDEE;padding:0cm 5.4pt 0cm 5.4pt; height:14.7pt'>
                      <p align=center valign="middle" style='margin-bottom:10.0pt;text-align:center'><span style='color:black'><b>Login</b><o:p></o:p></span></p>
                    </td>
                  </tr>
                  <tr style='mso-yfti-irow:2;mso-yfti-lastrow:yes;height:8.75pt'>
                    <td width=100% style='border-top:none;border-left:solid windowtext 1.0pt; border-bottom:solid windowtext .5pt;
                                          border-right:solid windowtext 1.0pt;mso-border-left-alt:solid windowtext .5pt;
                                          mso-border-right-alt:solid windowtext .5pt;
                                          background: #E6E6E6;padding:0cm 5.4pt 0cm 5.4pt;height:8.75pt'>
                       <br>

                       <form action="/swfm/FrontalController" method='POST' name='login_user'>
                         <div style="text-align: left;">
                           &nbsp Usuario:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                           <input type='text' name='login' size='15'>  &nbsp &nbsp
                           <br/> <br/>
                           &nbsp Contraseña:&nbsp
                           <input type='password' name='pwd' size='15'>
                           <br/> <br/> 
                         </div>
                         <div style="text-align: center;">
                           <input type='hidden' name="operation" value='login_user.do'> 
                           <input type='submit' value="Acceder"> <br/>
                         </div>
                       </form>
                       </i></b><o:p></o:p></span></p>
                                    
                    </td>
                  </tr>
                </table>
              </center>
              <!-- Fin de Tabla de Login -->
          </td>
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
          <td width="20%" align="center">
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </td>
      </tr>

    </TABLE>

  </body>
  
</html>

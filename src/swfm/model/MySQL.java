//*****************************************************************
// SWBD: Servicios Web de Base de Datos.
//
// Autor:                     Juan Jos� Aguado
// Fecha creaci�n:            30/Jun/2010
// Fecha �ltima modificaci�n: 28/Jul/2010
//
// clase: Sybase
//        Conexi�n a MySQL.
//
//*****************************************************************
package swfm.model;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import swfm.controller.Util;


/*********************************************************
*                   Clase MySQL
*********************************************************/
public class MySQL {

    private transient static final Log log=LogFactory.getLog(MySQL.class);

    protected Connection connection = null;
    protected int MYSQL_TIMEOUT = 20;

    //******************************************************
    // Conexi�n a la Base de Datos.
    //******************************************************
    public Connection connectBD() throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://"+Util.MYSQL_HOST+":"+Util.MYSQL_PORT+"/"+Util.MYSQL_DB;

        Class.forName(driver);
        connection = DriverManager.getConnection(url, Util.MYSQL_USR, Util.MYSQL_PWD);
        
        return connection;
    }


    //******************************************************
  public Connection getConnection() {
    return connection;
  }


    //******************************************************
    // Reconnect to BD
    //******************************************************
    public void reconnect() throws Exception {
        log.info("(MySQL/reconnect) Connection loose with DB, trying to reconnect ...");
        connectBD();
    }


  //*****************************************************************
  /*
  public boolean isValid() {
    boolean isValid=true;
    try {
      Statement stmt = connection.createStatement();
//      String query = "select count(*) from "+Util.MYSQL_DB+".ENTIDADES; "; 
      String query = "select count(*) from "+Util.MYSQL_DB+".CAS_CONFIG; "; 
      log.info("(MySQL/isValid) /"+query+"/");     
      stmt.executeQuery(query);
    } catch (Exception e) {
      isValid = false;
    }

    return isValid;
  }*/

    //******************************************************
    // Cierra conexi�n a la Base de Datos (mysql).
    //******************************************************
    public void close() throws Exception {
        connection.close();
    }

}

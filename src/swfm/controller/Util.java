//*****************************************************************
// SWBD: Servicios Web de Base de Datos.
//
// Autor:                     Juan Jos� Aguado
// Fecha creaci�n:            30/Jun/2010
// Fecha �ltima modificaci�n: 19/Jul/2010
//
// clase: Util
//        Funciones miscel�neas utilizadas por el resto de clases.
//        
//*****************************************************************

package swfm.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


/*********************************************************
*                  Clase Util
*********************************************************/
public class Util {

    static String MYSQL_DOWNLOAD_TABLE_SERVER_DIR = "/backup/mysql/swbd";

    public static String MYSQL_HOST      = "xxxxxx.dit.aeat";
    public static String MYSQL_PORT      = "3599";
    public static String MYSQL_DB        = "JJA";
    public static String MYSQL_USR       = "f009938h";
    public static String MYSQL_PWD       = "f009938h";
    public static String MYSQL_TBL       = "CRLS_SEGMENTED_LDAP";
    public static String DOWNLOAD_DIR    = "xxxx";
    public static String DOWNLOAD_FILE   = "xxxx";
    public static String DATE_SINCE      = "2013-06-17";
    public static String FIELD_SEPARATOR = "}}{{";
    public static String REG_SEPARATOR   = "@@\n";


    //******************************************************
    //Leer configuraci�n de la aplicaci�n desde un fichero
    //de Properties.
    //******************************************************
    static void readCfg() throws Exception {

        //Leer fichero de configuracion
        FileInputStream fis = null;
        File f = new File(CONST.CFG_FILE);
        try {
          fis = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            throw e;
        }

        Properties prop = new Properties();
        prop.load(fis);

        //Obtener variables de entorno del fichero de Properties
        String mysqlHost   = prop.getProperty("MYSQL_HOST");
        String mysqlPort   = prop.getProperty("MYSQL_PORT");
        String mysqlDb     = prop.getProperty("MYSQL_DB");
        String mysqlUsr    = prop.getProperty("MYSQL_USR");
        String mysqlPwd    = prop.getProperty("MYSQL_PWD");
        String mysqlTbl    = prop.getProperty("MYSQL_TBL");
        String dowloadDir  = prop.getProperty("DOWNLOAD_DIR");
        String dowloadFile = prop.getProperty("DOWNLOAD_FILE");
        String fieldSep    = prop.getProperty("FIELD_SEPARATOR");
        String regSep      = prop.getProperty("REG_SEPARATOR");

        if (mysqlHost!=null)
          MYSQL_HOST = mysqlHost;
        
        if (mysqlPort!=null)
          MYSQL_PORT = mysqlPort;
        
        if (mysqlDb!=null)
          MYSQL_DB = mysqlDb;
        
        if (mysqlUsr!=null)
          MYSQL_USR = mysqlUsr;
        
        if (mysqlPwd!=null)
          MYSQL_PWD = mysqlPwd;
    
        if (mysqlTbl!=null)
          MYSQL_TBL = mysqlTbl;
    
        if (dowloadDir!=null)
          DOWNLOAD_DIR = dowloadDir;
    
        if (dowloadFile!=null)
          DOWNLOAD_FILE = dowloadFile;
    
        if (fieldSep!=null)
          FIELD_SEPARATOR = fieldSep;
    
        if (regSep!=null)
          REG_SEPARATOR = regSep;

  }


    //******************************************************
    //Borra el fichero recibido como par�metro.
    //******************************************************
    static void deleteFile(String fName) throws Exception {
        File f = new File(fName);
        boolean errDelete = f.delete();
        if (errDelete==false) {
            throw new Exception("Util/deleteFile  <"+fName+">");
        }
    }


  //******************************************************
  //Obtener fecha y hora actual en formato String.
  //******************************************************
  public static String getDateNow() {
    Calendar now = Calendar.getInstance();
    int day = now.get(Calendar.DAY_OF_MONTH);
    int month = now.get(Calendar.MONTH)+1;
    int hour = now.get(Calendar.HOUR_OF_DAY);
    int min = now.get(Calendar.MINUTE);
    int sec = now.get(Calendar.SECOND);
    int year = now.get(Calendar.YEAR);
    String nowStr = year+"-"+month+"-"+day+" "+hour+":"+min+":"+sec;
    return nowStr;
  }//getDateNow 


  //******************************************************
  //Obtener fecha y hora como sufijo para anexar a un  
  //nombre de fichero.
  //******************************************************
  public static String getDateSufix() {
    Calendar now = Calendar.getInstance();
    int day = now.get(Calendar.DAY_OF_MONTH);
    int month = now.get(Calendar.MONTH)+1;
    int hour = now.get(Calendar.HOUR_OF_DAY);
    int min = now.get(Calendar.MINUTE);
    int sec = now.get(Calendar.SECOND);
    int year = now.get(Calendar.YEAR);
    String nowStr = year+""+month+""+day+"-"+hour+min+sec;
    return nowStr;
  }//getDateSufix 



    //*****************************************************************
    static void streamBinaryData(String txt, int jornada, ServletOutputStream outstr, HttpServletResponse resp)
                                throws IOException {
        String ErrorStr = null;
        String clientFile = "j"+ jornada+"-"+Util.getDateSufix()+".sql";

        try {
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            int txtLength = txt.length();
            InputStream input = new ByteArrayInputStream(txt.getBytes("UTF8"));
            bis = new BufferedInputStream(input);

            try { 
                //Asignamos el tipo de fichero zip
                resp.setContentType("application/force-download"); //Cualquier mime type
                //Fijar la longitud de la respuesta
                resp.setContentLength(txtLength);
                resp.setHeader("Content-Disposition", "attachment;filename=\"" + clientFile + "\"");

                bos = new BufferedOutputStream(outstr);

                // Bucle para leer de un fichero y escribir en el otro.
                byte[] array = new byte[1000];
                int leidos = bis.read(array);
                while (leidos > 0) {
                  bos.write(array, 0, leidos);
                  leidos = bis.read(array);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrorStr = "Error Streaming the Data";
                outstr.print(ErrorStr);
            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
                if (outstr != null) {
                    outstr.flush();
                    outstr.close();
                }
            }
        } catch (IOException e) { 
            throw e;
        }
  }


    //*****************************************************************
    static String formatField(String data, int lengthField, String side) {
        
        String field;
        int dataLength = data.length();
        
        String pad = "";
        for (int i=0; i<(lengthField-dataLength); i++) {
            pad += " ";
            
        }
        
        if (side.equals("left")) {
            field = pad + data + " ";
        }
        else {
            field = " " + data + pad;
        }
            
        return field;
        
    }


    // *****************************************************************
    static public String getDateTimeStr() {
        Calendar now = Calendar.getInstance();

        // Mes
        int month = now.get(Calendar.MONTH) + 1;
        String monthStr = month + "";
        if (month < 10)
            monthStr = "0" + monthStr;

        // Día
        int day = now.get(Calendar.DAY_OF_MONTH);
        String dayStr = day + "";
        if (day < 10)
            dayStr = "0" + dayStr;

        String nowStr = now.get(Calendar.YEAR) + "-" + monthStr + "-" + dayStr
                + " " + now.get(Calendar.HOUR_OF_DAY) + ":"
                + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND);
        return nowStr;
    }


    // *****************************************************************
    // Generate a Random Header (from 1 to 3) to insert in the JSP pages.
    // *****************************************************************
    static public String generateRandomHeader() {
        int higherNumber = 3;
        int randomNumber = (int)(Math.random() * higherNumber) + 1;
        return "/swfm/pages/images/header" + randomNumber +".jpg";
    }


}//Util

package swfm.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList; 
import java.util.Iterator; 
import java.util.List; 
import javax.servlet.http.HttpServletRequest; 
import org.apache.commons.fileupload.FileItem; 
import org.apache.commons.fileupload.FileUploadException; 
import org.apache.commons.fileupload.disk.DiskFileItemFactory; 
import org.apache.commons.fileupload.servlet.ServletFileUpload; 


/*************************************************************************/ 
//                      CLASE MultipartForm 
/*************************************************************************/ 

public class MultipartForm { 

  private List<String> fieldNameList = new ArrayList<String>(); 
  private List<String> fieldValueList = new ArrayList<String>(); 
  
  MultipartForm(HttpServletRequest request) throws FileUploadException, IOException { 
    
    String fieldName; 
    String fieldValue; 
    String fieldContentType; 
    
    ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
    servletFileUpload.setHeaderEncoding("UTF-8");
    List<FileItem> fileItemsList = (List<FileItem>)servletFileUpload.parseRequest(request);
    
    Iterator<FileItem> it = fileItemsList.iterator(); 
    
    while (it.hasNext()) {
      FileItem fileItem = (FileItem) it.next();
      fieldName = fileItem.getFieldName();
      fieldNameList.add(fieldName);
      
      fieldContentType = fileItem.getContentType();
      //Not file parameter
      if (fieldContentType==null) {       
        //Set parameter value
        fieldValue = fileItem.getString();
        String fValueUtf8 = new String(fieldValue.getBytes("ISO-8859-1"), "UTF-8");
        fieldValueList.add(fValueUtf8); 
      }
      //File parameter
      else {
        try {
//          String fileName = CONST.PATH_TOMCAT + CONST.PATH_TMP + "/" + fileItem.getName();
        // OJO!! SOLO PARA EJECUTAR EN LA AEAT
          String fileName = fileItem.getName();
          //Write received file as a temporal file in server Hard Disk
          InputStream is = fileItem.getInputStream();
          FileOutputStream fos = new FileOutputStream(fileName);
          int car;
          while ((car=is.read()) != -1) {
            fos.write(car);
          }
          fos.close();
          
          File f = new File(fileName);
          //File received
          if (f.length()>0)   
            fieldValueList.add(fileName);
          //Not file received
          else
            fieldValueList.add("");
        } catch(Exception e) {
          fieldValueList.add("");
        }
      }
    } 
  } 
  
  
  /*************************************************************************/ 
  public String getParamValue(String paramName) { 
    int pos = fieldNameList.indexOf(paramName); 
    if (pos>=0)
      return fieldValueList.get(pos); 
    else
      return null;
  } 
  
  
}// END of MultipartForm class 


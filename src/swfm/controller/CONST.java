//*****************************************************************
// SWBD: Servicios Web de Base de Datos.
//
// Autor:                     Juan Jos� Aguado
// Fecha creaci�n:            30/Jun/2010
// Fecha �ltima modificaci�n: 27/Jul/2010
//
// clase: CONST
//       Constantes comunes a toda la aplicaci�n.
//
//*****************************************************************

package swfm.controller;

public interface CONST {

    static final int OK=0;
    static final int ERR=-1; 
  
    //Variables de entorno y valores por defecto
    //  static String HOME_DIR         = "/opt/tomcat/instances/swfm";
    //static String HOME_DIR         = "/AEAT/workspace/swfm/webapp/WEB-INF";
    static String HOME_DIR         = "/home/juanjo/workspace/swfm/WebContent/";
    static String LOG_DIR          = HOME_DIR+"/logs";
    static String CFG_DIR          = HOME_DIR+"/properties";
    static String DATA_DIR         = HOME_DIR+"/data";
    static String CFG_FILE         = CFG_DIR+"/swfm.conf"; 
    static String VERSION          = "0Xx";


    //Production
    //AEAT
    //static final String PATH_TOMCAT = "C:\\AEAT\\workspace\\swfm\\webapp";
    //home
    static final String PATH_TOMCAT = "/home/juanjo/workspace/swfm/WebContent";
    //Development Linux
    //static final String PATH_TOMCAT = "/usr/share/tomcat5.5-webapps";
    //Development Windows
    //static final String PATH_TOMCAT = "C:\\Archivos de programa\\Apache Software Foundation\\Tomcat 5.5\\webapps";

    static final String TEAMS_PATH_TMP    = "/pages/images/teams/tmp";  
    static final String PLAYERS_PATH_TMP  = "/pages/images/players/tmp";  



    // Temporada
    static int SEASON = 2015;

    // Login Users
    static final String OPERATION_LOGIN_USER = "login_user.do";

    // Navegaci�n
    static final String OPERATION_EXIT = "exit.do";
    static final String OPERATION_GO_BACK = "go_page.do";

    
    // -------------------------
    // Visualizar Menu Quiniela
    // -------------------------
    static final String OPERATION_QUINIELA_MENU             = "qMenu.view";

    // Mostrar clasificaci�n de la jornada
    static final String OPERATION_QUINIELA_SHOW_CLASIF_JORNADA = "qShowClasifJornada.do";
    static final String OPERATION_QUINIELA_SHOW_CLASIF_GENERAL = "qShowClasifGeneral.do";

    // Procesar una Jornada (una vez que los ficheros master y user han sido introducidos en el sistema)
    static final String OPERATION_QUINIELA_PROCESS_JORNADA     = "qProcessJornada.do";

    // Exportar Clasificaci�n General a Fichero (formatos texto o html)
    static final String OPERATION_QUINIELA_DOWNLOAD_CLASIF_GRAL_TXT  = "qDownloadClasifGralTxt.do";
    static final String OPERATION_QUINIELA_DOWNLOAD_CLASIF_GRAL_HTML = "qDownloadClasifGralHtml.do";

    // Exportar Clasificaci�n Jornada a Fichero (formatos texto o html)
    static final String OPERATION_QUINIELA_DOWNLOAD_CLASIF_JORNADA_TXT  = "qDownloadClasifJornadaTxt.do";
    static final String OPERATION_QUINIELA_DOWNLOAD_CLASIF_JORNADA_HTML = "qDownloadClasifJornadaHtml.do";

    // Importar Resultados Oficiales de una Jornada desde Fichero
    static final String OPERATION_QUINIELA_UPLOAD_MASTER_FILE  = "qUploadMasterFile.do";

    // Importar Pron�sticos de Usuario de una Jornada desde Fichero
    static final String OPERATION_QUINIELA_UPLOAD_USER_FILE  = "qUploadUserFile.do";

    // Definir / Modificar una Jornada
    static final String OPERATION_QUINIELA_EDIT_JORNADA_VIEW = "qEditJornada.view";
    static final String OPERATION_QUINIELA_EDIT_JORNADA_DO   = "qEditJornada.do";



    // -----------------------------
    // Visualizar Menu Mejor Jugador
    // -----------------------------
    static final String OPERATION_BEST_PLAYER_MENU = "bpMenu.view";

    // Mostrar clasificaci�n de la jornada
    static final String OPERATION_BEST_PLAYER_SHOW_CLASIF_JORNADA = "bpShowClasifJornada.do";
    static final String OPERATION_BEST_PLAYER_SHOW_CLASIF_GENERAL = "bpShowClasifGeneral.do";

    // Procesar una Jornada (una vez que los ficheros master y user han sido introducidos en el sistema)
    static final String OPERATION_BEST_PLAYER_PROCESS_JORNADA = "bpProcessJornada.do";

    // Exportar Clasificaci�n General a Fichero (formatos texto o html)
    static final String OPERATION_BEST_PLAYER_DOWNLOAD_CLASIF_GRAL_TXT  = "bpDownloadClasifGralTxt.do";
    static final String OPERATION_BEST_PLAYER_DOWNLOAD_CLASIF_GRAL_HTML = "bpDownloadClasifGralHtml.do";

    // Exportar Clasificaci�n Jornada a Fichero (formatos texto o html)
    static final String OPERATION_BEST_PLAYER_DOWNLOAD_CLASIF_JORNADA_TXT = "bpDownloadClasifJornadaTxt.do";
    static final String OPERATION_BEST_PLAYER_DOWNLOAD_CLASIF_JORNADA_HTML = "bpDownloadClasifJornadaHtml.do";

    // Importar Resultados Oficiales de una Jornada desde Fichero
    static final String OPERATION_BEST_PLAYER_UPLOAD_MASTER_FILE  = "bpUploadMasterFile.do";

    // Importar Pron�sticos de Usuario de una Jornada desde Fichero
    static final String OPERATION_BEST_PLAYER_UPLOAD_USER_FILE = "bpUploadUserFile.do";

    // Definir / Modificar una Jornada
    static final String OPERATION_BEST_PLAYER_EDIT_JORNADA_VIEW = "bpEditJornada.view";
    static final String OPERATION_BEST_PLAYER_EDIT_JORNADA_DO = "bpEditJornada.do";



    // -------------------------
    // Visualizar Menu Porra
    // -------------------------
    static final String OPERATION_PORRA_MENU             = "pMenu.view";

}

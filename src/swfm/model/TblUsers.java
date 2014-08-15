package swfm.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import swfm.controller.ErrorCode;
import swfm.controller.MyException;
import swfm.controller.Util;


/*************************************************************************/
//                         CLASE TblUsers
/*************************************************************************/
public class TblUsers {

    private Connection conn;

    // *****************************************************************
    public TblUsers(Connection conn) {
        this.conn = conn;
    }


  // *****************************************************************
  public User getUser(int codUser) throws MyException {
    User user = null;
    try {
      Statement stmt = conn.createStatement();
      ResultSet res = stmt.executeQuery("SELECT * FROM users WHERE codUsuario="+codUser);
      if (res.next()) {
        String login = res.getString("login");
        String pwd = res.getString("clave");
        String name = res.getString("nombre");
        String surname = res.getString("apellidos");
        String email = res.getString("email");
        String profile = res.getString("perfil");
        String fechaRegistro = res.getString("fechaRegistro");
        String fechaUltimoLogin = res.getString("fechaUltimoLogin");
        user = new User(codUser, login, pwd, name, surname, email, profile, fechaRegistro, fechaUltimoLogin);
      }
    } catch (SQLException e) {
        throw new MyException(ErrorCode.SQL_ERROR, "SQLException: " + e.getMessage());
    }

    return user;
  }// END of getUser method




    // *****************************************************************
    // Get user data which 'login' name and 'password' is received as
    // input parameter.
    // *****************************************************************
    public User getUser(String login, String pwd) throws MyException {

        User user = null;
        try {
            Statement stmt = conn.createStatement();
            
            // Check if user exist
            String query = "SELECT * from users WHERE login='" + login + "';";

            ResultSet res = stmt.executeQuery(query);

            if (res.next()) {

                // OK user exist, check now if pwd is correct
                int codUser         = res.getInt("codUsuario");
                String pwdGold       = res.getString("clave");
                String name          = res.getString("nombre");
                String surname       = res.getString("apellidos");
                String email         = res.getString("email");
                String profile       = res.getString("perfil");
                String dateRegister  = res.getString("fechaRegistro");
                String dateLastLogin = res.getString("fechaUltimoLogin");

                // If no data register
                if (pwd.equals(pwdGold)==false) {
                    throw new MyException(ErrorCode.PWD_INVALID, "Clave incorrecta");
                }
                // User OK
                else {
                    user = new User(codUser, login, pwd, name, surname, email, profile, dateRegister, dateLastLogin);
                }
            }

            // User doesn't exist
            else {
                throw new MyException(ErrorCode.USER_NOT_EXIST, "Usuario inválido");
            }

        } catch (SQLException e) {
            throw new MyException(ErrorCode.SQL_ERROR, "SQLException: " + e.getMessage());
        }

        return user;
    }




  // *****************************************************************
  public List<User> getUserList() throws MyException {
    List<User> users = new ArrayList<User>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet res = stmt.executeQuery("SELECT * FROM users ORDER by codUsuario");
      while (res.next()) {
        int codUser = res.getInt("codUsuario");
        String login = res.getString("login");
        String pwd = res.getString("clave");
        String name = res.getString("nombre");
        String surname = res.getString("apellidos");
        String email = res.getString("email");
        String profile = res.getString("perfil");
        String fechaRegistro = res.getString("fechaRegistro");
        String fechaUltimoLogin = res.getString("fechaUltimoLogin");
        User user = new User(codUser, login, pwd, name, surname, email,  profile, fechaRegistro, fechaUltimoLogin);
        users.add(user);
      }
    } catch (SQLException e) {
      throw new MyException(ErrorCode.SQL_ERROR, "SQLException: " + e.getMessage());
    }

    return users;
  }// END of getUserList method


   // *****************************************************************
  public List<User> getTeachersList() throws MyException {
    List<User> users = new ArrayList<User>();
    try {
      Statement stmt = conn.createStatement();
      ResultSet res = stmt.executeQuery("SELECT * FROM users WHERE perfil='Profesor' ORDER by codUsuario");
      while (res.next()) {
        int codUser = res.getInt("codUsuario");
        String login = res.getString("login");
        String pwd = res.getString("clave");
        String name = res.getString("nombre");
        String surname = res.getString("apellidos");
        String email = res.getString("email");
        String profile = res.getString("perfil");
        String fechaRegistro = res.getString("fechaRegistro");
        String fechaUltimoLogin = res.getString("fechaUltimoLogin");
        User user = new User(codUser, login, pwd, name, surname, email, profile, fechaRegistro, fechaUltimoLogin);
        users.add(user);
      }
    } catch (SQLException e) {
      throw new MyException(ErrorCode.SQL_ERROR, "SQLException: " + e.getMessage());
    }

    return users;
  }// END of getTeachersList method


  // *****************************************************************
  public String getUserLogin(int codUser) throws MyException {
    String login = "";
    try {
      Statement stmt = conn.createStatement();
      ResultSet res = stmt.executeQuery("SELECT * FROM users WHERE codUsuario=" + codUser);
      while (res.next()) {
        login = res.getString("login");
      }
    } catch (SQLException e) {
      throw new MyException(ErrorCode.SQL_ERROR, "SQLException: " + e.getMessage());
    }

    return login;
  }// END of getUserLogin method

  // *****************************************************************
  public void addUser(User user) throws MyException {

    try {
      //Comprobar que el usuario no existe en la BBDD
      Statement stmt = conn.createStatement();
      String query = "SELECT * from users WHERE codUsuario=" + user.getCodUser();
      ResultSet res = stmt.executeQuery(query);
      if (res.next()) {
        throw new MyException(ErrorCode.USER_ALREADY_EXIST, "Usuario ya existe");
      }

      // Inserta registro en BBDD
      query = "INSERT users SET codUsuario=" + user.getCodUser() + ", login='" + user.getLogin() + "'," +
                                  "clave='" + user.getPwd() + "', nombre='"  + user.getName() + "'," +
                                  "apellidos='" + user.getSurname()  + "', email='" + user.getEmail() + "', " +
                                  "perfil='" + user.getProfile() + "', fechaRegistro='" + Util.getDateTimeStr() + "'";

      System.out.println("(TblUsers/addUser) query: " + query);

      int result = stmt.executeUpdate(query);
      // result debe ser = 1 (1 row affecteds: 1 inserted)
      if (result != 1)
        throw new MyException(ErrorCode.TBL_USERS_INSERT_ERROR, "Error el insertar registro en tabla users");
    } catch (SQLException e) {
        throw new MyException(ErrorCode.SQL_ERROR, "SQLException: " + e.getMessage());
    }

  }// END of addUser method


  // *****************************************************************
  public void chkUser(User user) throws MyException {

    try {
      Statement stmt = conn.createStatement();
      String query = "SELECT * from users WHERE codUsuario=" + user.getCodUser();

      ResultSet res = stmt.executeQuery(query);
      if (res.next()) {
        query = "SELECT * from users WHERE codUsuario="+user.getCodUser()+" and clave='"+user.getPwd()+ "'";
        res = stmt.executeQuery(query);
        if (res.next()) {
          int codUser = res.getInt("codUsuario");
          String name = res.getString("nombre");
          String surname = res.getString("apellidos");
          String profile = res.getString("perfil");
          user.setCodUser(codUser);
          user.setName(name);
          user.setSurname(surname);
          user.setProfile(profile);
        } else {
            throw new MyException(ErrorCode.PWD_INVALID, "Clave incorrecta");
        }
      } else {
          throw new MyException(ErrorCode.USER_NOT_EXIST, "Usuario NO existe");
      }
    } catch (SQLException e) {
        throw new MyException(ErrorCode.SQL_ERROR, "SQLException: " + e.getMessage());
    }

  }// END of chkUser method



  // *****************************************************************
  // Update 'fechaUltimoLogin' field of the user received as input
  // parameter
  // *****************************************************************
  public void updateFechaUltimoLogin(User user) throws MyException {

    try {
      Statement stmt = conn.createStatement();

      String query = "UPDATE users SET fechaUltimoLogin='" + Util.getDateTimeStr() + "' " +
                           " WHERE codUsuario=" + user.getCodUser();
      int result = stmt.executeUpdate(query);
      
      // result should be = 1 (1 row affected: 1 inserted)
      if (result != 1)
        throw new MyException(ErrorCode.TBL_USERS_UPDATE_ERROR,
                                 "Error el actualizar registro en tabla usuatios");
    } catch (SQLException e) {
        throw new MyException(ErrorCode.SQL_ERROR, "SQLException: " + e.getMessage());
    }

  }



  // *****************************************************************
  // Devuelve el numero de users registrados.
  // *****************************************************************
  public int getNumUsersRegistered() throws MyException {
    int numUsersRegistered = 0;
    try {
      Statement stmt = conn.createStatement();
      ResultSet res = stmt.executeQuery("SELECT COUNT(*) FROM users");
      if (res.next()) {
        numUsersRegistered = res.getInt(1);
      }
    } catch (SQLException e) {
        throw new MyException(ErrorCode.SQL_ERROR, "SQLException: " + e.getMessage());
    }

    return numUsersRegistered;
  }// END of getNumUsersRegistered method

}// END of TblUsers class

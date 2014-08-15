package swfm.model;

/*************************************************************************/
//                         CLASE User
/*************************************************************************/

public class User {
    
    private int codUser;
    private String login;
    private String pwd;
    private String name;
    private String surname;
    private String email;
    private String profile;
    private String fechaRegistro;
    private String fechaUltimoLogin;


    // *****************************************************************
    public User(int codUser, String login, String pwd, String name,
            String surname, String email, String profile, String fechaRegistro,
            String fechaUltimoLogin) {
        
        this.codUser = codUser;
        this.login = login;
        this.pwd = pwd;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.profile = profile;
        this.fechaRegistro = fechaRegistro;
        this.fechaUltimoLogin = fechaUltimoLogin;
    }


    // *****************************************************************
    void setCodUser(int codUser) {
        this.codUser = codUser;
    }

    // *****************************************************************
    void setName(String name) {
        this.name = name;
    }

    // *****************************************************************
    void setSurname(String surname) {
        this.surname = surname;
    }

    // *****************************************************************
    void setProfile(String profile) {
        this.profile = profile;
    }

    // *****************************************************************
    public int getCodUser() {
        return codUser;
    }

    // *****************************************************************
    public String getLogin() {
        return login;
    }

    // *****************************************************************
    public String getPwd() {
        return pwd;
    }

    // *****************************************************************
    public String getName() {
        return surname + " "+ name;
    }

    // *****************************************************************
    public String getSurname() {
        return surname;
    }

    // *****************************************************************
    public String getEmail() {
        return email;
    }

    // *****************************************************************
    public String getProfile() {
        return profile;
    }

    // *****************************************************************
    public String getFechaRegistro() {
        return fechaRegistro;
    }

    // *****************************************************************
    public String getFechaUltimoLogin() {
        return fechaUltimoLogin;
    }

}// END of User class

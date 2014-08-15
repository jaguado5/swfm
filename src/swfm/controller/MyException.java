package swfm.controller;

public class MyException extends Exception {


    private static final long serialVersionUID = 1L;
    
    
    private int errCod;
    private String errMsg;

    public MyException(int errCod, String errMsg) {
        this.errCod = errCod;
        this.errMsg = errMsg;
    }

    public String getMessage() {
        return errMsg;
    }

    public int getCode() {
        return errCod;
    }
    
}


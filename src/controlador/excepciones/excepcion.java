package controlador.excepciones;


public class excepcion extends Exception{
    private String msg;

    public excepcion(String msg) {
        this.msg = msg;
    }
    
    public String getMessage() {
        return msg;
    }
}

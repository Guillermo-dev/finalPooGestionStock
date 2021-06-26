package controlador.excepciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class excepcion extends Exception{
    private String msg;

    public excepcion(String msg) {
        this.msg = msg;
    }
    
    public String getMessage() {
        return msg;
    }
    
    public static void comprobarEmail(String email) throws excepcion{
        Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
        Matcher mather = pattern.matcher(email);
        if (!mather.find()){
            throw new excepcion("El email ingresado no es valido");
        }
    }
    
    public static void comprobarTextos (String texto, String dato) throws excepcion{
        //Compruebo si hay un caracter especial o numero
        String REG_EXP = "\\¿+|\\?+|\\°+|\\¬+|\\|+|\\!+|\\#+|\\$+|" +
        "\\%+|\\&+|\\+|\\=+|\\’+|\\¡+|\\++|\\*+|\\~+|\\[+|\\]" +
        "+|\\{+|\\}+|\\^+|\\<+|\\>+|\\@|\\_+|\\-+|[0-9]";
        Pattern pattern = Pattern.compile(REG_EXP);
        Matcher matcher = pattern.matcher(texto);
        if(matcher.find()){
            throw new excepcion("El "+ dato +" no puede tener caracteres especiales ni numeros");
        };
    }
}

package controlador.excepciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Excepcion extends Exception{
    private String msg;

    public Excepcion(String msg) {
        this.msg = msg;
    }
    
    public String getMessage() {
        return msg;
    }
    
    public static void comprobarEmail(String email) throws Excepcion{
        Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
        Matcher mather = pattern.matcher(email);
        if (!mather.find()){
            throw new Excepcion("El email ingresado no es valido");
        }
    }
    
    public static void comprobarTextos (String texto, String dato) throws Excepcion{
        //Compruebo si hay un caracter especial o numero
        String REG_EXP = "\\¿+|\\?+|\\°+|\\¬+|\\|+|\\!+|\\#+|\\$+|" +
        "\\%+|\\&+|\\+|\\=+|\\’+|\\¡+|\\++|\\*+|\\~+|\\[+|\\]" +
        "+|\\{+|\\}+|\\^+|\\<+|\\>+|\\@|\\_+|\\-+|[0-9]";
        Pattern pattern = Pattern.compile(REG_EXP);
        Matcher matcher = pattern.matcher(texto);
        if(matcher.find()){
            throw new Excepcion("El "+ dato +" no puede tener caracteres especiales ni numeros");
        };
    }
    
    public static void comprobarStocks (int stock_min, int stock)throws Excepcion{
        if (stock_min > stock){
            throw new Excepcion("El stock minimo no puede tener un valor mayor al stock");
        }
    }
}

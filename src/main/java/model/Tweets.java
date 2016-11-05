package model;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tweets {
    private String text;
    private String usuario;
    private Date fecha;

    public void setText(String text){
        this.text = text;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getText() {
        return this.text;
    }
    public String getUsuario() {
        return this.usuario;
    }
    public Date getFecha() {
        return this.fecha;
    }


    public Boolean obtenerValoracion(){
        List<String> tokens = new ArrayList<String>();
        tokens.add("pesimo");
        tokens.add("pesima");
        tokens.add("reclamo");
        tokens.add("mal");
        tokens.add("mala");
        tokens.add("malo");
        tokens.add("penca");
        tokens.add("porqueria");
        tokens.add("reclamo");

        String patronString = "\\b(" + StringUtils.join(tokens, "|") + ")\\b";
        Pattern patron = Pattern.compile(patronString);
        Matcher buscador = patron.matcher(this.text);

        if (buscador.find()) {
            return true;
        }
        return false;
    }
    
}
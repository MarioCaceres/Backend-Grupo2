package model;

import java.util.Date;

public class TweetsM {
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
    
}
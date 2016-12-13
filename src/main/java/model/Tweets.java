package model;

import java.util.List;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tweets {
    private String text;
    private String usuario;
    private Date fecha;
    private String id;

    public void setText(String text){
        this.text = text;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setId(String id) {
        this.id = id;
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
    public String getId() {
        return this.id;
    }


    //0 = Malo
    //1 = Bueno
    //2 = Neutro
    public int obtenerValoracion(){

        SSS analizador = new SSS();
        String[] tokens = this.text.split("\\s");

        //Contadores para las palabras del tweet
        int buenas = 0, malas = 0, neutras = 0;

        for (String palabra:tokens) {
            //Primero se verifica si la palabra es una stopword
            int verificadorNeutra = 0;
            for (String palabraStop:analizador.getBolsaStopwords()) {
                if(palabra.equals(palabraStop)){
                    neutras++;
                    verificadorNeutra =1;
                    break;
                }
            }
            if(verificadorNeutra==1){
                continue;
            }

            //Luego se verifica si la palabra es mala
            int verificadorMala = 0;
            for (String palabraMala:analizador.getBolsaMalos()) {
                if(palabra.equals(palabraMala)){
                    malas++;
                    verificadorMala =1;
                    break;
                }
            }
            if(verificadorMala==1){
                continue;
            }
            //Aqui se verifica mediante espacios metricos si la palabra es mala
            //la distancia a la cual se considera dentro de las malas se establece en 2
            else{
                for (String palabraMala:analizador.getBolsaMalos()) {
                    if(SSS.calcularDistancia(palabra,palabraMala)<=2){
                        malas++;
                        verificadorMala = 1;
                        break;
                    }
                }
            }
            if(verificadorMala==1){
                continue;
            }


            //Luego se verifica si la palabra es buena
            int verificadorBuena = 0;
            for (String palabraBuena:analizador.getBolsaBuenos()) {
                if(palabra.equals(palabraBuena)){
                    buenas++;
                    verificadorBuena =1;
                    break;
                }
            }
            if(verificadorBuena==1){
                continue;
            }
            //Aqui se verifica mediante espacios metricos si la palabra es buena
            //la distancia a la cual se considera dentro de las buenas se establece en 2
            else{
                for (String palabraBuena:analizador.getBolsaBuenos()) {
                    if(SSS.calcularDistancia(palabra,palabraBuena)<=2){
                        buenas++;
                        verificadorBuena = 1;
                        break;
                    }
                }
            }
            if(verificadorBuena==1){
                continue;
            }

            //Caso en que ninguna entre
            if(verificadorBuena == 0 && verificadorMala ==0 && verificadorNeutra == 0){
                neutras++;
            }
        }
        double b = buenas, m = malas, n = neutras;
        //Calculo de la formula, aun no se sabe como interpretar el valor obtenido
        double valoracion = m/4 + b/2 + n/8;

        //Se retorna la valoracion del tweet a traves del maximo de palabras obtenidas en cada categoria
        return maximo(malas,buenas,neutras);
    }

    private int maximo(int malas,int buenas, int neutras){
        int max = malas;
        int retorno = 0;
        if (buenas > max) {
            max = buenas;
            retorno = 1;
        }

        if (neutras > max) {
            max = neutras;
            retorno = 2;
        }

        return retorno;
    }


}
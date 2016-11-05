package model;

import java.util.List;

/**
 * Created by mario on 05-11-16.
 */
public class Indice {
    private double proporcion;
    private double desaprobacion;
    private double buenos;
    private double malos;

    public double getDesaprobacion() {
        return desaprobacion;
    }

    public void setDesaprobacion(double desaprobacion) {
        this.desaprobacion = desaprobacion;
    }

    public double getProporcion() {
        return proporcion;
    }

    public void setProporcion(double proporcion) {
        this.proporcion = proporcion;
    }

    public void obtenerValores(List<Tweets> tweets) {
        double malos = 0;
        double buenos = 0;
        for (Tweets tweet:tweets) {
            if(tweet.obtenerValoracion() == true){
                malos=malos+1;
            }
            else{
                buenos=buenos+1;
            }
        }
        this.setBuenos(buenos);
        this.setMalos(malos);
        double total = buenos+malos;
        double tempP = this.buenos/this.malos;
        double tempD = this.malos/total;
        this.proporcion = tempP;
        this.desaprobacion = tempD;
    }

    public double getBuenos() {
        return buenos;
    }

    public void setBuenos(double buenos) {
        this.buenos = buenos;
    }

    public double getMalos() {
        return malos;
    }

    public void setMalos(double malos) {
        this.malos = malos;
    }
}

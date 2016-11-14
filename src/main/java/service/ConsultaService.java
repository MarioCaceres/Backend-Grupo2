package service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import model.Indice;
import model.Tweets;
import model.Mongo;

@Path("/tweets")
public class ConsultaService { 
    Mongo mongo = new Mongo();
    @GET
    @Produces({"application/xml", "application/json"})
    public List <Tweets> findAll(){
        return mongo.findAll();
    }

    @GET
    @Path("/compañias/{compañia}")
    @Produces({"application/xml", "application/json"})
    public List <Tweets> findCompañia(@PathParam("compañia") String compañia){
        return mongo.searchCompañia(compañia);
    }

    @GET
    @Path("/compañias/{compañia}/comunas/{comuna}")
    @Produces({"application/xml", "application/json"})
    public List <Tweets> findComuna(@PathParam("compañia") String compañia,
                                    @PathParam("comuna") String comuna){
        return mongo.searchComuna(compañia,comuna);
    }

    @GET
    @Path("/compañias/{compañia}/periodos/inicio/{dia0}.{mes0}.{año0}/fin/{dia1}.{mes1}.{año1}")
    @Produces({"application/xml", "application/json"})
    public List <Tweets> findPeriodo(@PathParam("compañia") String compañia,
                                     @PathParam("dia0") Integer dia0,
                                     @PathParam("mes0") Integer mes0,
                                     @PathParam("año0") Integer año0,
                                     @PathParam("dia1") Integer dia1,
                                     @PathParam("mes1") Integer mes1,
                                     @PathParam("año1") Integer año1){
        String inicio= (dia0-1)+"-"+mes0+"-"+año0;
        String fin= (dia1+1)+"-"+mes1+"-"+año1;
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaInicio = null;
        try {
            fechaInicio = (Date) formatter.parse(inicio);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaFin = null;
        try {
            fechaFin = (Date) formatter.parse(fin);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mongo.searchPeriodo(compañia,fechaInicio,fechaFin);
    }

    @GET
    @Path("/compañias/{compañia}/periodos/inicio/{dia0}.{mes0}.{año0}")
    @Produces({"application/xml", "application/json"})
    public List <Tweets> findInicio(@PathParam("compañia") String compañia,
                                    @PathParam("dia0") Integer dia0,
                                    @PathParam("mes0") Integer mes0,
                                    @PathParam("año0") Integer año0){
        return mongo.searchInicio(compañia,dia0,mes0,año0);
    }

    @GET
    @Path("/fix_fechas")
    @Produces({"application/xml", "application/json"})
    public String fechas(){
        mongo.convertirFechas();
        return "OK";
    }

    @GET
    @Path("/indices/compañias/{compañia}")
    @Produces({"application/xml", "application/json"})
    public Indice findDesaprobacion(@PathParam("compañia") String compañia){
        List <Tweets> tweets = mongo.searchCompañia(compañia);
        Indice indice = new Indice();
        indice.obtenerValores(tweets);
        return indice;
    }

    @GET
    @Path("/indices/compañias/{compañia}/periodos/inicio/{dia0}.{mes0}.{año0}/fin/{dia1}.{mes1}.{año1}")
    @Produces({"application/xml", "application/json"})
    public List <Indice> findDesaprobacionPeriodo(@PathParam("compañia") String compañia,
                                     @PathParam("dia0") Integer dia0,
                                     @PathParam("mes0") Integer mes0,
                                     @PathParam("año0") Integer año0,
                                     @PathParam("dia1") Integer dia1,
                                     @PathParam("mes1") Integer mes1,
                                     @PathParam("año1") Integer año1){


        String inicioFuncion= (dia0-1)+"-"+mes0+"-"+año0;
        String finFuncion= (dia1+1)+"-"+mes1+"-"+año1;
        String inicio= dia0+"-"+mes0+"-"+año0;
        String fin= dia1+"-"+mes1+"-"+año1;
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaInicioFuncion = null;
        Date fechaFinFuncion = null;
        Date fechaInicio = null;
        Date fechaFin = null;
        try {
            fechaInicio = (Date) formatter.parse(inicio);
            fechaFin = (Date) formatter.parse(fin);

            fechaInicioFuncion = (Date) formatter.parse(inicioFuncion);
            fechaFinFuncion = (Date) formatter.parse(finFuncion);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Tweets> tweets = mongo.searchPeriodo(compañia,fechaInicioFuncion,fechaFinFuncion);
        List<Indice> IndicesPeriodo = new ArrayList<> ();

        LocalDate start = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        for (LocalDate date =start; !date.isAfter(end); date = date.plusDays(1)) {
            List<Tweets> temp = new ArrayList<> ();
            for (Tweets tweet:tweets) {
                LocalDate fechatemp = tweet.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if(fechatemp.compareTo(date) == 0){
                    temp.add(tweet);
                }
            }
            Indice indice = new Indice();
            indice.obtenerValoresRandom(temp);
            IndicesPeriodo.add(indice);
        }


        return IndicesPeriodo;
    }
    
}
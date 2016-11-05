package service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import model.Indice;
import model.Tweets;
import model.Mongo;

@Path("/tweets")
public class ConsultaService { 
    
    @GET
    @Produces({"application/xml", "application/json"})
    public List <Tweets> findAll(){
        Mongo mongo = new Mongo();
        return mongo.findAll();
    }

    @GET
    @Path("/compañias/{compañia}")
    @Produces({"application/xml", "application/json"})
    public List <Tweets> findCompañia(@PathParam("compañia") String compañia){
        Mongo mongo = new Mongo();
        return mongo.searchCompañia(compañia);
    }

    @GET
    @Path("/compañias/{compañia}/comunas/{comuna}")
    @Produces({"application/xml", "application/json"})
    public List <Tweets> findComuna(@PathParam("compañia") String compañia,
                                    @PathParam("comuna") String comuna){
        Mongo mongo = new Mongo();
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
        Mongo mongo = new Mongo();
        return mongo.searchPeriodo(compañia,dia0,mes0,año0,dia1,mes1,año1);
    }

    @GET
    @Path("/compañias/{compañia}/periodos/inicio/{dia0}.{mes0}.{año0}")
    @Produces({"application/xml", "application/json"})
    public List <Tweets> findInicio(@PathParam("compañia") String compañia,
                                    @PathParam("dia0") Integer dia0,
                                    @PathParam("mes0") Integer mes0,
                                    @PathParam("año0") Integer año0){
        Mongo mongo = new Mongo();
        return mongo.searchInicio(compañia,dia0,mes0,año0);
    }

    @GET
    @Path("/fix_fechas")
    @Produces({"application/xml", "application/json"})
    public String fechas(){
        Mongo mongo = new Mongo();
        mongo.convertirFechas();
        return "OK";
    }

    @GET
    @Path("/indices/compañias/{compañia}")
    @Produces({"application/xml", "application/json"})
    public Indice findDesaprobacion(@PathParam("compañia") String compañia){
        Mongo mongo = new Mongo();
        List <Tweets> tweets = mongo.searchCompañia(compañia);
        Indice indice = new Indice();
        indice.obtenerValores(tweets);
        return indice;
    }
    
}
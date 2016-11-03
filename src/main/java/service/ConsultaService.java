package service;

import java.util.List;
import java.util.Collection;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import model.TweetsM;
import model.MongoM;

@Path("/tweets")
public class ConsultaService { 
    
    @GET
    @Produces({"application/xml", "application/json"})
    public List <TweetsM> findAll(){
        MongoM mongo = new MongoM();
        return mongo.findAll();
    }

    @GET
    @Path("/Comp")
    @Produces({"application/xml", "application/json"})
    public List <TweetsM> findCompa(){
        MongoM mongo = new MongoM();
        return mongo.searchCompa();
    }



    @GET
    @Path("/compañia/{compañia}")
    @Produces({"application/xml", "application/json"})
    public List <TweetsM> findCompañia(@PathParam("compañia") String compañia){
        MongoM mongo = new MongoM();
        return mongo.searchCompañia(compañia);
    }

    @GET
    @Path("/compañia/{compañia}/comuna/{comuna}")
    @Produces({"application/xml", "application/json"})
    public List <TweetsM> findComuna(@PathParam("compañia") String compañia,
                                       @PathParam("comuna") String comuna){
        MongoM mongo = new MongoM();
        return mongo.searchComuna(compañia,comuna);
    }

    @GET
    @Path("/compañia/{compañia}/periodo/inicio/{dia0}.{mes0}.{año0}/fin/{dia1}.{mes1}.{año1}")
    @Produces({"application/xml", "application/json"})
    public List <TweetsM> findPeriodo(@PathParam("compañia") String compañia,
                                       @PathParam("dia0") Integer dia0,
                                       @PathParam("mes0") Integer mes0,
                                       @PathParam("año0") Integer año0,
                                       @PathParam("dia1") Integer dia1,
                                       @PathParam("mes1") Integer mes1,
                                       @PathParam("año1") Integer año1){
        MongoM mongo = new MongoM();
        return mongo.searchPeriodo(compañia,dia0,mes0,año0,dia1,mes1,año1);
    }

    @GET
    @Path("/compañia/{compañia}/periodo/inicio/{dia0}.{mes0}.{año0}")
    @Produces({"application/xml", "application/json"})
    public List <TweetsM> findInicio(@PathParam("compañia") String compañia,
                                       @PathParam("dia0") Integer dia0,
                                       @PathParam("mes0") Integer mes0,
                                       @PathParam("año0") Integer año0){
        MongoM mongo = new MongoM();
        return mongo.searchInicio(compañia,dia0,mes0,año0);
    }

    @GET
    @Path("/fix_fechas")
    @Produces({"application/xml", "application/json"})
    public String fechas(){
        MongoM mongo = new MongoM();
        mongo.convertirFechas();
        return "OK";
    }
    
}
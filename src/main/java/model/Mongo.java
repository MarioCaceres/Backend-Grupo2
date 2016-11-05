package model;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.*;


public class Mongo {
    private com.mongodb.Mongo mongo;
    private  DB db;
    private DBObject textSearchCommand;
    private DBCollection collection;

    public Mongo() {
        this.mongo = new com.mongodb.Mongo("localhost", 27017);
        this.textSearchCommand = new BasicDBObject();
        this.db = mongo.getDB("pruebaFiltro");
        this.collection=db.getCollection("tweets4");
    }

    public List<Tweets> findAll(){
        List<Tweets> list = new ArrayList<>();
        DBCursor cursor = this.collection.find();
        list = obtenerDatos(cursor);
        return list;
    }


    public List<Tweets> searchComuna(String compañia, String comuna){
        List<Tweets> list = new ArrayList<>();
        String consulta = "\""+compañia+"\" \""+comuna+"\"";
        DBCursor cursor = this.collection.find(new BasicDBObject("$text", new BasicDBObject("$search", consulta)));
        list = obtenerDatos(cursor);
        return list;
    }

    public List<Tweets> searchCompañia(String Compañia){
        List<Tweets> list = new ArrayList<>();
        DBCursor cursor = this.collection.find(new BasicDBObject("$text", new BasicDBObject("$search", Compañia)));
        list = obtenerDatos(cursor);
        return list;
    }

    public List<Tweets> searchPeriodo(String compañia, int dia0, int mes0, int año0, int dia1, int mes1, int año1){
        List<Tweets> list = new ArrayList<>();

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


        DBObject queryFecha = new BasicDBObject("created_at",
                new BasicDBObject("$gte", fechaInicio).append("$lte", fechaFin))
                .append("$text", new BasicDBObject("$search", compañia));
        DBCursor cursor = this.collection.find(queryFecha);
        list = obtenerDatos(cursor);
        return list;
    }

    public List<Tweets> searchInicio(String compañia, int dia0, int mes0, int año0){
        List<Tweets> list = new ArrayList<>();

        String inicio= (dia0-1)+"-"+mes0+"-"+año0;
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaInicio = null;
        try {
            fechaInicio = (Date) formatter.parse(inicio);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DBObject queryFecha = new BasicDBObject("created_at",
                new BasicDBObject("$gte", fechaInicio))
                .append("$text", new BasicDBObject("$search", compañia));
        DBCursor cursor = this.collection.find(queryFecha);
        list = obtenerDatos(cursor);
        return list;
    }

    public void convertirFechas(){
        DBCursor cursor = this.collection.find();
        while(cursor.hasNext()){
            DBObject document = cursor.next();
            DBObject updated = new BasicDBObject();
            updated.put("$set", new BasicDBObject("created_at",new Date((String)document.get("created_at"))));
            this.collection.update(document,updated);
        }
    }

    private List<Tweets> obtenerDatos(DBCursor cursor){
        List<Tweets> list = new ArrayList<>();
        while (cursor.hasNext()) {
            DBObject document = cursor.next();
            Tweets data = new Tweets();
            data.setText((String) document.get("text"));
            data.setUsuario((String) ((DBObject)document.get("user")).get("screen_name"));
            Date fecha = (Date)document.get("created_at");
            data.setFecha(fecha);
            list.add(data);
        }
        return list;
    }

}





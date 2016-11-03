package model;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mongodb.*;
import model.TweetsM;


public class MongoM   {
    private Mongo mongo;
    private  DB db;
    private DBObject textSearchCommand;
    private DBCollection collection;

    public MongoM() {
        this.mongo = new Mongo("localhost", 27017);
        this.textSearchCommand = new BasicDBObject();
        this.db = mongo.getDB("pruebaFiltro");
        this.collection=db.getCollection("tweets4");
    }

    public List<TweetsM> findAll(){
        List<TweetsM> list = new ArrayList<>();
        DBCursor cursor = this.collection.find();
        while (cursor.hasNext()) {
            DBObject document = cursor.next();
            TweetsM  data = new TweetsM();
            data.setText((String) document.get("text"));
            data.setUsuario((String) ((DBObject)document.get("user")).get("screen_name"));
            Date fecha = (Date)document.get("created_at");
            data.setFecha(fecha);
            list.add(data);
        }

        return list;
    }
    
 	public List<TweetsM> searchCompa(){
        List<TweetsM> list = new ArrayList<>();
        String searchString = "VTR Claro Movistar WOM";
        DBCursor cursor = this.collection.find(new BasicDBObject("$text", new BasicDBObject("$search", searchString)));
        while (cursor.hasNext()) {
            DBObject document = cursor.next();
            TweetsM  data = new TweetsM();
            data.setText((String) document.get("text"));
            list.add(data);
        }

        return list;     
    }

    public List<TweetsM> searchComuna(String compañia,String comuna){
        List<TweetsM> list = new ArrayList<>();
        String consulta = "\""+compañia+"\" \""+comuna+"\"";
        DBCursor cursor = this.collection.find(new BasicDBObject("$text", new BasicDBObject("$search", consulta)));
        while (cursor.hasNext()) {
            DBObject document = cursor.next();
            TweetsM  data = new TweetsM();
            data.setText((String) document.get("text"));
            data.setUsuario((String) ((DBObject)document.get("user")).get("screen_name"));
            Date fecha = (Date)document.get("created_at");
            data.setFecha(fecha);
            list.add(data);
        }

        return list;
    }

    public List<TweetsM> searchCompañia(String Compañia){
        List<TweetsM> list = new ArrayList<>();
        DBCursor cursor = this.collection.find(new BasicDBObject("$text", new BasicDBObject("$search", Compañia)));
        while (cursor.hasNext()) {
            DBObject document = cursor.next();
            TweetsM  data = new TweetsM();
            data.setText((String) document.get("text"));
            data.setUsuario((String) ((DBObject)document.get("user")).get("screen_name"));
            Date fecha = (Date)document.get("created_at");
            data.setFecha(fecha);
            list.add(data);
        }

        return list;
    }

    public List<TweetsM> searchPeriodo(String compañia,int dia0,int mes0,int año0,int dia1,int mes1,int año1){
        List<TweetsM> list = new ArrayList<>();

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
        while (cursor.hasNext()) {
            DBObject document = cursor.next();
            TweetsM  data = new TweetsM();
            data.setText((String) document.get("text"));
            data.setUsuario((String) ((DBObject)document.get("user")).get("screen_name"));
            Date fecha = (Date)document.get("created_at");
            data.setFecha(fecha);
            list.add(data);
        }
        return list;
    }

    public List<TweetsM> searchInicio(String compañia,int dia0,int mes0,int año0){
        List<TweetsM> list = new ArrayList<>();

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
        while (cursor.hasNext()) {
            DBObject document = cursor.next();
            TweetsM  data = new TweetsM();
            data.setText((String) document.get("text"));
            data.setUsuario((String) ((DBObject)document.get("user")).get("screen_name"));
            Date fecha = (Date)document.get("created_at");
            data.setFecha(fecha);
            list.add(data);
        }
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

}





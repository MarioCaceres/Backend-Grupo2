package model;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.neo4j.driver.v1.*;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Created by mario on 09-12-16.
 */
public class Ranking {

    public List<String> calcularRanking(){
        List<String> ranking = new ArrayList<>();
	Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "tbd2016" ) );
			Session session = driver.session();
        //AQUI HACE LA FUNCION PARA CALCULAR EL RANKING DE LOS USUARIOS
        //OBTIENE EL NOMBRE DE LOS USUARIOS DEL RANKING Y AGREGALOS A LA LISTA
        //CON ranking.add(nombre)
	StatementResult result = session.run("match p = ()-[r3:MENTION]->(b), ()-[r2:RETWEET]->(b), ()-[r1:REPLY]->(b)  return b.screen_name as n, (log(sum(r3.count)+sum(r2.count)+sum(r1.count))+count(r3))/(sum(r1.count)) as rank order by rank desc limit 10");
	while(result.hasNext()){
		Record record = result.next();
		String s = record.get("n").asString().replace("u_","");
		ranking.add(s);
		System.out.println(s);
	}
	session.close();
	driver.close();
        return ranking;
    }
}

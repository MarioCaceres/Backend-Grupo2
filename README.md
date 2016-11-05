asadmin start-domain  
asadmin deploy build/libs/grupo_tbd2-master.war  

/tweets  
(Todos los tweets)  
/tweets/compañias/{compañia}  
(Tweets de una compañia especifica)  
/tweets/compañias/{compañia}/comunas/{comuna}  
(tweets de una compañia para cierta comuna)  
/tweets/compañias/{compañia}/periodos/inicio/{dia}.{mes}.{año}/fin/{dia}.{mes}.{año}  
(tweets dentro de un periodo de tiempo)  
/tweets/compañias/{compañia}/periodos/inicio/{dia}.{mes}.{año}  
(tweets desde una cierta fecha)  
/indices/compañias/{compañia}  
(indice para una compañia)  
/fix_fechas (Para cambiar el formato de las fechas en mongodb)  
/indices/compañias/{compañia}/periodos/inicio/{dia0}.{mes0}.{año0}/fin/{dia1}.{mes1}.{año1}  
(indice en un determinado periodo de tiempo)  


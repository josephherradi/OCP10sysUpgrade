# Améliorez le système d’information de la bibliothèque
Les développements effectués concernent tickets suivants ouverts par le tech Lead:
* Ajoutez un système de réservation d’ouvrages (par le client)
* Bug dans la gestion des prolongations de prêt
* Mettez en place une stratégie de tests


## Lancer les modules à l'aide du plugin Maven

Il est possible de compiler et d'executer rapidement l'application avec la commande suivante maven

```
mvn spring-boot:run
```

## Compiler en JAR/WAR

La compilation des livrables se fait avec la commande suivante à l'aide de maven selon la balise <packaging>war</packaging> du pom.xml
```
mvn clean package
```

## Lancer les modules compilés JAR/WAR
Le lancement des modules batch et Api Rest se fait par la commande suivante
```
java -jar batch.jar
```
Pour déployer les applications WAR client-library.war et client-lecteurs sur Tomcat, il est nécessaire de copier le fichier WAR dans le dossier webapps de Tomcat et de personnaliser les champs docBase et port des balises suivantes de server.xml (dans le dossier conf de Tomcat)
Dans docBase, indiquez client-lecteurs pour client-lecteurs.WAR
```
 <Host name="localhost"  appBase="webapps" unpackWARs="true" autoDeploy="true">
    <Context docBase="client-library" path="" reloadable="true" />
 </Host>

```

L'application sera déployée sur le port 8082
```
<Connector connectionTimeout="20000" port="8082" protocol="HTTP/1.1" redirectPort="8443"/>
```
Démarrez Tomcat et activez les logs:
```
./startup.sh
tail -f logs/catalina.out

```


## URLs

L'Api rest webservices est déployée sur le port 9092
Pour consulter la liste des livres au format JSON
```
http://localhost:9092/livres
```
Rest Api Spring security basic authentication :
login= utilisateur
pwd= mdp

Le client lecteurs est accessible à:

```
http://localhost:8082/login
```
Utilisez pour la démo
login: "lecteurTest" ou "userTest"
mdp: 12345

Le client admin est accessible à:
```
http://localhost:8080/livres
```

## Auteur

Joseph Herradi

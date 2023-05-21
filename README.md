# Springboot Photobook Demo App


## Quickstart 

Prerequisites :
* JDK 17+ (Tested with Amazon Corretto 17.0.7.7.1) 
* Apache Maven 3.9.2


### 1. Mongo db

you will need mongo db, an easy way to do it is with a docker image, for instance : 

```
docker pull mongo:6.0.5
docker run -p 27017:27017 --name MONGO6 -d mongo:6.0.5
```

TODO: must add instruction for mongo db init


This will start a mongo db linked on the default port and with the default username/password (root/example).


### 2. Start the application with maven

```
mvn spring-boot:run
```


### 3. Test api REST 

[http://localhost:7080/photobook-demo/api/photobook/view/list](http://localhost:7080/photobook-demo/api/photobook/view/list)



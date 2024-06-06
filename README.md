# Springboot Photobook Demo App

## Introduction

Recently I followed some [Mongo DB courses](https://learn.mongodb.com/) and attended the [Spring I/O 2023](https://2023.springio.net/).  
So I decided to practice a bit. This project is the result.  
Currently is just the result of about 6/8 hours of work, so I just a POC integration of Mongo DB and Spring Boot.

There is a live version at the link [https://springio23.fugerit.org/photobook-demo/home/index.html](https://springio23.fugerit.org/photobook-demo/home/index.html)

TODO: (If I will have time)
* Add mongodb setup instruction
* Add i18n and pagination
* Better user interface

## Quickstart 

Prerequisites :
* JDK 21+ (Tested with Oracle GraalVM 21) 
* Apache Maven (Tested with 3.9.6)
* Node js 20 (Tested with 20.11.0)

### 1. Mongo db

you will need mongo db, an easy way to do it is with a docker image, for instance : 

```shell
docker pull mongo:8.0.0-rc7
```

```shell
docker run -p 27017:27017 --name MONGO8 -d -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=example mongo:8.0.0-rc7
```

This will start a mongo db linked on the default port and with the default username/password (root/example).

NOTE:
Two collections must be created : 
photobook_meta (sample data in src/test/resources/sample-photobooks/spring-io-2023.json, property photobookMeta)
photobook_images (sample data in src/test/resources/sample-photobooks/spring-io-2023.json, property photobookImages)
It is possible to add the indexes too : src/test/resources/mongo-db/create-index.js


### 2. Start the application with maven

```
mvn clean install -P buildreact
mvn spring-boot:run
```


### 3. Access home page

[http://localhost:7080/photobook-demo/home/index.html](http://localhost:7080/photobook-demo/home/index.html)



## Native compilation

The code has been set for native compilation with [GraalVM](https://www.graalvm.org/) (tested with GraalVM 22.3 CE).  

It is possible to compile :

```
mvn -Pnative native:compile
```

Or build an image : 

```
mvn -Pnative spring-boot:build-image
```

Refer to [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html) for more informations.


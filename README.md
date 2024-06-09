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

[![Java runtime version](https://img.shields.io/badge/run%20on-java%2021+-%23113366.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java21.html)
[![Java build version](https://img.shields.io/badge/build%20on-GraalVM%2021+-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/gvm21.html)
[![Apache Maven](https://img.shields.io/badge/Apache%20Maven-3.9.0+-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)](https://universe.fugerit.org/src/docs/versions/maven3_9.html)
[![Node JS](https://img.shields.io/badge/Node%20JS-20+-1AC736?style=for-the-badge&logo=node.js&logoColor=white)](https://universe.fugerit.org/src/docs/versions/maven3_9.html)

### 1. Mongo db

you will need mongo db, an easy way to do it is with a docker image, for instance : 

```shell
docker pull mongo:8.0.0-rc7
```

```shell
docker run -p 27017:27017 --name MONGO8 -rm mongo:8.0.0-rc7
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

```shell
mvn -Pnative native:compile
```

Or build an image : 

```shell
mvn -Pnative spring-boot:build-image
```

Refer to [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html) for more informations.


## Docker image

Building the native image : 

```shell
docker build -t springboot-photobook -f src/main/docker/Dockerfile.native .
```

Running the container :

```shell
docker run -it -p 8080:8080 --name springboot-photobook springboot-photobook
```
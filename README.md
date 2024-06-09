# Springboot Photobook Demo App

[![Keep a Changelog v1.1.0 badge](https://img.shields.io/badge/changelog-Keep%20a%20Changelog%20v1.1.0-%23E05735)](https://github.com/fugerit-org/springboot-photobook/blob/master/CHANGELOG.md)
[![license](https://img.shields.io/badge/License-Apache%20License%202.0-teal.svg)](https://opensource.org/licenses/Apache-2.0)
[![code of conduct](https://img.shields.io/badge/conduct-Contributor%20Covenant-purple.svg)](https://github.com/fugerit-org/fj-universe/blob/main/CODE_OF_CONDUCT.md)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_springboot-photobook&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fugerit-org_springboot-photobook)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_springboot-photobook&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fugerit-org_springboot-photobook)
[![Docker images](https://img.shields.io/badge/dockerhub-images-important.svg?logo=Docker)](https://hub.docker.com/repository/docker/fugeritorg/springboot-photobook/general)

## Introduction

Recently I followed some [Mongo DB courses](https://learn.mongodb.com/) and attended the [Spring I/O 2023](https://2023.springio.net/).  
So I decided to practice a bit. This project is the result.  
Currently is just the result of about 6/8 hours of work, so I just a POC integration of Mongo DB and Spring Boot.

There is a live version at the link [https://springio23.fugerit.org/photobook-demo/home/index.html](https://springio23.fugerit.org/photobook-demo/home/index.html)

## Quickstart 

[![Java runtime version](https://img.shields.io/badge/run%20on-java%2021+-%23113366.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java21.html)
[![Java build version](https://img.shields.io/badge/build%20on-GraalVM%2021+-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/gvm21.html)
[![Apache Maven](https://img.shields.io/badge/Apache%20Maven-3.9.0+-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)](https://universe.fugerit.org/src/docs/versions/maven3_9.html)
[![Node JS](https://img.shields.io/badge/Node%20JS-20+-1AC736?style=for-the-badge&logo=node.js&logoColor=white)](https://universe.fugerit.org/src/docs/versions/maven3_9.html)

### 1. Mongo db

Create mongo db instance with db initialization (script src/test/resources/mongo-db/mongo-init.js) :

```shell
docker run --rm -p 27017:27017 --name MONGO8 -v `pwd`/src/test/resources/mongo-db/mongo-init.js:/docker-entrypoint-initdb.d/mongo-init.js mongo:8.0.0-rc7
```

This will start a mongo db linked on the default port and with the default username/password (root/example).

### 2. Start the application with maven

build

```shell
mvn clean install -P buildreact
```
And start

```shell
mvn spring-boot:run
```

### 3. Access home page

[http://localhost:8080/photobook-demo/home/index.html](http://localhost:7080/photobook-demo/home/index.html)

## environment variables

| key         | dedault                                  | 
|-------------|------------------------------------------|
| MONGODB_URL | mongodb://localhost:27017/photobook_demo |

## Native compilation

The code has been set for native compilation with [GraalVM](https://www.graalvm.org/) (tested with GraalVM 22.3 CE).  

It is possible to compile :

```shell
mvn -Pnative,buildreact native:compile
```

Or build an image : 

```shell
mvn -Pnative,buildreact spring-boot:build-image
```

Refer to [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html) for more informations.

## Docker image

### docker container (jvm)

Build spring application (jar)

```shell
mvn clean package -P buildreact
```

Build container openjdk

```shell
docker build -t springboot-photobook-jvm -f src/main/docker/Dockerfile.jvm .
```

Running the container :

```shell
docker run -it -p 8080:8080 --name springboot-photobook-jvm springboot-photobook-jvm
```

### docker container (native)

Building the native image : 

```shell
docker build -t springboot-photobook-native -f src/main/docker/Dockerfile.native .
```

Running the container :

```shell
docker run -it -p 8080:8080 --name springboot-photobook-native springboot-photobook-native
```
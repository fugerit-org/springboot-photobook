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
Currently is just a simple POC integration of Mongo DB and Spring Boot.

There is a live version at the link [https://springio23.fugerit.org/photobook-demo/home/index.html](https://springio23.fugerit.org/photobook-demo/home/index.html)

## Prerequisites

| software                                                                                                                                                                                                 | docker compose | local build and run |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|---------------------|
| [![Java runtime version](https://img.shields.io/badge/run%20on-java%2021+-%23113366.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java21.html)   | no             | yes                 |
| [![Java build version](https://img.shields.io/badge/build%20on-GraalVM%2021+-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/gvm21.html) | no             | yes                 |
| [![Apache Maven](https://img.shields.io/badge/Apache%20Maven-3.9.0+-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)](https://universe.fugerit.org/src/docs/versions/maven3_9.html)       | no             | yes                 |
| [![Node JS](https://img.shields.io/badge/Node%20JS-20+-1AC736?style=for-the-badge&logo=node.js&logoColor=white)](https://universe.fugerit.org/src/docs/versions/node.html)                               | no             | yes                 |
| [![Docker](https://img.shields.io/badge/docker-26+-1266E7?style=for-the-badge&logo=docker&logoColor=white)](https://universe.fugerit.org/src/docs/versions/docker.html)                                  | yes            | no                  |

## Quickstart 

### Start via docker compose

```shell
docker-compose -f src/main/docker/docker-compose.yml up -d
```

access home page

<http://localhost:8080/photobook-demo/home/index.html>

### Start in dev mode

1. Create mongo db instance with db initialization (script src/test/resources/mongo-db/mongo-init.js) :

```shell
docker run --rm -p 27017:27017 --name MONGO8 -v `pwd`/src/test/resources/mongo-db/mongo-init.js:/docker-entrypoint-initdb.d/mongo-init.js mongo:8.0.0-rc7
```

This will start a mongo db linked on the default port and with the default username/password (root/example).

2. Start the application in dev mode

The back end

```shell
mvn spring-boot:run
```

And front end

```shell
cd src/main/react
npm install
npm run start
```

3. Access home page

<http://localhost:8080/photobook-demo/home/index.html>

## environment variables

In case you want a custom mongo db connection :

| key         | dedault                                  | 
|-------------|------------------------------------------|
| MONGODB_URL | mongodb://localhost:27017/photobook_demo |

## Spring Boot package

It is possible to compile the application to a single jar package :

```shell
mvn package -Pbuildreact
```

And then run

```shell
java -jar ./target/springboot-photobook-*.jar
```

## Native image compilation

The code has been set for native compilation with [GraalVM](https://www.graalvm.org/) (tested with GraalVM 22.3 CE).  

It is possible to compile :

```shell
mvn -Pbuildreact,native clean native:compile
```

And then run

```shell
./target/springboot-photobook
```

Refer to [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html) for more informations.

## Docker image

### docker container (jvm)

Build spring application (jar)

```shell
mvn package -P buildreact
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
mvn -Pbuildreact,native clean native:compile
```

Building the container image : 

```shell
docker build -t springboot-photobook-native -f src/main/docker/Dockerfile.native .
```

Running the container :

```shell
docker run -it -p 8080:8080 --name springboot-photobook-native springboot-photobook-native
```

## Native optimization : PGO

This section is based on <https://github.com/alina-yur/native-spring-boot>.

One of the most powerful performance optimizations in Native Image is profile-guided optimizations (PGO).

1. Build an instrumented image:

```shell
mvn -Pnative,instrumented native:compile
```

2. Run the app and apply relevant workload:

```shell
./target/springboot-photobook-instrumented
```

```shell
hey -n=30000 http://localhost:8080/photobook-demo/api/photobook/view/list
```

```shell
hey -n=30000 http://localhost:8080/photobook-demo/api/photobook/view/images/springio23/language/it/current_page/1/page_size/5
```

after you shut down the app, you'll see an `iprof` file in your working directory.

3. Build an app with profiles (they are being picked up via `<buildArg>--pgo=${project.basedir}/default.iprof</buildArg>`):

```shell
mvn -Pnative,optimized native:compile
```

## Benchmark scripts

Prerequisites :
- running mongo db
- `hey` installed
- `psrecord` installed

At the end of a run, an image will be plotted in the `target` folder.

### 1. Benchmark JIT

```shell
mvn clean package
```

```shell
./src/main/script/bench-jit.sh
```

### 2. Benchmark native

Follow steps in 'Native optimization : PGO' section

```shell
./src/main/script/bench-native.sh
```

### 3. Benchmark result

Sample result of JIT benchmark run : 

![JIT Benchmark Result](src/main/docs/images/bench-result-jit-20240617.png)

Sample result of native (AOT) benchmark run :

![Native Benchmark Result](src/main/docs/images/bench-result-native-20240617.png)

## application stack

| Layer             | 2023 version     | 2024 version     |
|-------------------|------------------|------------------|
| Persistence       | MongoDB 6        | MongoDB 8        |
| Java version      | GraalVM 17       | GraalVM 21       |
| API REST          | SpringBoot 3.0.0 | SpringBoot 3.3.0 |
| Node JS           | Node 18          | Node 20          |
| Front end package | React scripts    | Vite             |
| Front end UI      | React 18.2       | React 18.3       |
# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- test containers test with mongo 8.0.0-rc8
- use of virtual threads

### Changed

- java version 21
- based on spring boot 3.3.0
- registered classes for reflection
- mongo db url set to ${MONGODB_URL:mongodb://localhost:27017/photobook_demo}
- default port set to 8080
- readme (added docker-compose info)

### Fixed

- build.properties metadata in native image
- buildreact profile in native amd64 workflow
- buildreact phases

## [1.0.0] - 2023-05-29 (Spring I/O 2023)

### Added

- version after spring [Spring I/O 2023](https://2023.springio.net/)
- using springboot 3.0, java 17 and graalvm

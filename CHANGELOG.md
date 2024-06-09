# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- native arm container build workflow (self hosted)

### Fixed

- build.properties metadata in native image
- buildreact profile in native amd64 workflow

## [1.0.0] - 2024-06-09

### Added

- dockerfile for native image and openjdk image
- test containers configuration

### Changed

- registered classes for reflection
- mongo db url set to ${MONGODB_URL:mongodb://localhost:27017/photobook_demo}
- default port set to 8080
- fj-core set to 8.6.2
- java version 21
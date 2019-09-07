# package-challenge
> 

[![Build Status](https://travis-ci.org/thiagoferrax/package-challenge.svg?branch=master)](https://travis-ci.org/thiagoferrax/package-challenge)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.mobiquityinc%3Apackage-challenge&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.mobiquityinc%3Apackage-challenge)
<a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg"></a>

## About

Assignment: send a friend a package with different things. Each thing has an index, weight and cost, and the package has a weight limit. The goal is to select the things maximizing the total cost and according to the package limit.

## Overview

#### Project structure
```
├── package_file.txt
├── Packaging Challenge.pdf
├── pom.xml
├── README.md
└── src
    ├── main
    │   └── java
    │       └── com
    │           └── mobiquityinc
    │               ├── builders
    │               │   ├── ItemBuilder.java
    │               │   └── PackageBuilder.java
    │               ├── decorator
    │               │   └── PackageDecorator.java
    │               ├── exception
    │               │   └── APIException.java
    │               ├── factory
    │               │   ├── DynamicProgrammingPackageSolver.java
    │               │   ├── PackageSolverFactory.java
    │               │   └── PackageSolver.java
    │               ├── packer
    │               │   └── Packer.java
    │               ├── parser
    │               │   ├── PackageFile.java
    │               │   ├── PackageFileParser.java
    │               │   └── PackageFileRow.java
    │               ├── pojos
    │               │   ├── Item.java
    │               │   └── Package.java
    │               └── util
    │                   └── PackageUtil.java
    └── test
        └── java
            └── com
                └── mobiquityinc
                    ├── packer
                    │   └── PackerTest.java
                    └── util
                        ├── PackageFileParserTest.java
                        └── PackageUtilTest.java
```

#### Tech stack
* [Java](https://www.java.com/) as the programming language
* [Mockito](https://site.mockito.org/) for testing
* [Maven](https://maven.apache.org/) for managing the project's build

## Install
#### Download the repository
```sh
$ git clone https://github.com/thiagoferrax/package-challenge.git
```
#### Run tests
```sh
$ cd package-challenge && mvn clean test
```

## License

MIT © [thiagoferrax](https://github.com/thiagoferrax).

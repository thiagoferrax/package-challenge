# package-challenge
> 

[![Build Status](https://travis-ci.org/thiagoferrax/package-challenge.svg?branch=master)](https://travis-ci.org/thiagoferrax/package-challenge)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.mobiquityinc%3Apackage-challenge&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.mobiquityinc%3Apackage-challenge)
<a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg"></a>

## About

The assignment: solve the package problem - send a friend a package with different items. Each item has an index, weight and cost, and the package has a weight limit. The goal is to select the items that maximize the total cost, respecting the package limit.

The key requirements that guided the design and implementation of the package-challenge:

- Write a Packer class with a static method called pack that accepts the absolute path to a test file (UTF-8 format) as a String. The pack method returns the solution as a String;
- It must be legible, sustainable and extensible where appropriate;
- The implementation must be in Java.

## Overall solution and patterns

The package challenge was developed using Test Driven Development, and its source code was enhanced with the help of [Sonar reports](https://sonarcloud.io/dashboard?id=com.mobiquityinc%3Apackage-challenge). Current coverage currently exceeds 85%. In addition, some design patterns like Factory, Decorator and Builder were applied to increase code quality.

About the solution:
- A file parser was created (PackageFileParser) to handle the file, validating its data according to the specified constraints;
- Builders of Package and Item were created using FluentBuilder pattern to keep the core code and unit tests more readable and easy to maintain;
- Because the package problem could be solved using different approaches, the Factory pattern was implemented by planning future additions of new solutions. Therefore, an interface (PackageSolver) was defined and implemented (DynamicProgrammingPackageSolver) and the factory (PackageSolverFactory) is prepared to receive more solutions;
- The Decorator pattern was used to wrap StringBuilder by adding a new append method that could get the list of items, extracting their indexes and appending them as comma-separated values.

The concern has always been to create very understandable and functional code.

## Strategy and algorithm

The strategy was to create a recursive structure to solve the package problem using Dynamic Programming approach. The implemented algorithm uses an array of two dimensions to keep the results of the (sub) instances of the problem.

The following code presents the core of the solution: 
- After creating an array to store costs, there is a loop for available items and a loop for weights (starting at weight one and going to the package weight limit);
- Each array element begins to receive the calculated maximum cost of previus item for the exact weight, or zero, in the case there is no previus item;
- Then, if the current item fits the package, a new maximum cost is calculated using the item cost and the array of costs;
- If this new maximum is greater than or equal to the previus one, the element will be updated;
- Finally, there is another algorithm that uses the costs array to evaluate the items that were added to the package.

```
...
    int weightLimit = round(aPackage.getWeightLimit());
    BigDecimal[][] costs = new BigDecimal[nItems + 1][weightLimit + 1];
    for (int i = 1; i <= nItems; i++) {

        Item item = items.get(i - 1);
        for (int w = 1; w <= weightLimit; w++) {

            costs[i][w] = getPreviousItemMaximumCost(costs, i, w);

            if (doesItemFitInPackage(item, w)) {
                BigDecimal itemMaximumCost = getItemMaximumCost(costs, item, i, w);

                // Does item have maximum cost greater than or equal to previous one?
                if (itemMaximumCost.compareTo(costs[i][w]) >= 0) {
                    costs[i][w] = itemMaximumCost;
                }
            }
        }
    }
    aPackage.setItems(getPackageItems(items, weightLimit, costs));
...
```

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
    │               │   └── StringBuilderDecorator.java
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
    │               │   ├── PackageFileRow.java
    │               │   ├── PackageFileValidator.java
    │               │   └── ParserConstants.java
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
                    ├── parser
                    │   └── PackageFileValidatorTest.java
                    └── util
                        ├── PackageFileParserTest.java
                        └── PackageUtilTest.java
```
#### Sequence diagram
![PackageChallenge](https://user-images.githubusercontent.com/43149895/64691700-b8e23d80-d469-11e9-9c00-2ac0e2733804.png)

#### Tech stack
* [Java](https://www.java.com/) as the programming language
* [Mockito](https://site.mockito.org/) for unit testing
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

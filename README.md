# package-challenge
> 

[![Build Status](https://travis-ci.org/thiagoferrax/package-challenge.svg?branch=master)](https://travis-ci.org/thiagoferrax/package-challenge)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.mobiquityinc%3Apackage-challenge&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.mobiquityinc%3Apackage-challenge)
<a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg"></a>

## About

Assignment: send a friend a package with different things. Each thing has an index, weight and cost, and the package has a weight limit. The goal is to select the things maximizing the total cost and according to the package limit.

The main requirements that guided the design and implementation of package-challenge:

- Write a class Packer with a static method named pack that accepts the absolute path to a test file (UTF-8 format) as a String. The pack method does return the solution as a String
- It should be readable, maintainable, and extensible where appropriate
- The implementation should be in Java

## Strategy and algorithm

The strategy was to create a recursive structure to solve the problem using Dynamic Programming approach. The implemented algorithm used an array of two dimensions to keep the results of the (sub) instances of the problem.

The following code presents the core of the solution: 
- After creating an array to store the costs, there is a loop through the available items and another one through the weights (starting at weight one and going until the package weight limit). 
- Each array element start receiving the calculated maximum cost of previus item, or zero, in the case there is no previus item.
- Then, if the current item fits in the package, a new maximum cost is then calculated using the current item cost and the array of costs.
- If this new maximum is greater than or equal to previus one, the element it is updated.
- Finally, there is another algorithm that uses the fullfiled costs array to evaluate the items that were added to the package.
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
![PackageChallenge](https://user-images.githubusercontent.com/43149895/64495729-29c00480-d274-11e9-9803-95ebc3d8d2f3.png)

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

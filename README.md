![Crawling crow logo](assets/logo.png)
# Crawling crow

[![Build Status](https://travis-ci.com/inf112-v20/crawling-crow.svg?branch=master)](https://travis-ci.com/inf112-v20/crawling-crow) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/1ef3b79326324c30a5b5b61d5addef5b)](https://www.codacy.com/gh/inf112-v20/crawling-crow?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inf112-v20/crawling-crow&amp;utm_campaign=Badge_Grade)

Currently a simple and not functional implementation of Robo Rally.
[JavaDoc](https://github.com/inf112-v20/crawling-crow/blob/gh-pages/documentation/javadoc/index.html)

## Deliverables
-   [Obligatorisk oppgave 1](Deliverables/ObligatoriskOppgave1.md)
-   [Obligatorisk oppgave 2](Deliverables/ObligatoriskOppgave2.md)
-   [Obligatorisk oppgave 3](Deliverables/ObligatoriskOppgave3.md)
-   [Obligatorisk oppgave 4](Deliverables/ObligatoriskOppgave4.md)

## Setup
1.  Install [Maven](https://maven.apache.org/download.cgi)
2.  Clone the repository, `git clone git@github.com:inf112-v20/crawling-crow.git`
3.  Build game with dependencies, `mvn clean verify assembly:single`

## How to run
For now, you have to run the game manually.

1.  Run `Main.java` located in `src/main/java/roborally` in your IDE of choice.
    -   Alternatively use the command `java -jar target/roborally-crawlingcrow-1.0-SNAPSHOT-jar-with-dependencies.jar`

2.  Move around using the [controls](/../../wiki/Hvordan-spille-spillet)

3.  When you want to exit, press <kbd>ESC</kbd> or exit the game manually by clicking on the **X**-button.

### How to play
See [How to play the game](/../../wiki/Hvordan-spille-spillet)

## Tests
See [Wikipage on testing](/../../wiki/Tester)

## Known bugs
See [Isses with Bug label](/../../issues?q=is%3Aissue+is%3Aopen+laser+label%3Abug)

### From Maven template
Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.

## Class diagram
![Oblig3UML](https://user-images.githubusercontent.com/59846048/77762946-98ab5200-703a-11ea-8993-dce653775105.jpg)
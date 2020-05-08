![Crawling crow logo](assets/logo.png)
# Crawling crow

[![Build Status](https://travis-ci.com/inf112-v20/crawling-crow.svg?branch=master)](https://travis-ci.com/inf112-v20/crawling-crow) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/1ef3b79326324c30a5b5b61d5addef5b)](https://www.codacy.com/gh/inf112-v20/crawling-crow?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inf112-v20/crawling-crow&amp;utm_campaign=Badge_Grade)

A Java implementation of Robo Rally.

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

2.  Move around using the [controls](https://github.com/inf112-v20/crawling-crow/wiki/Hvordan-spille-spillet#kontroll-i-spillet)

3.  When you want to exit, press <kbd>ESC</kbd> or exit the game manually by clicking on the **X**-button.

### How to play
See [How to play the game](https://github.com/inf112-v20/crawling-crow/wiki/Hvordan-spille-spillet)

## Tests
See [Wikipage on testing](https://github.com/inf112-v20/crawling-crow/wiki/Tester)

## Known bugs
See [Isses with Bug label](https://github.com/inf112-v20/crawling-crow/issues?q=is%3Aissue+is%3Aopen+label%3Abug+sort%3Aupdated-desc)

### From Maven template
Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.

## Class diagram
![klassediagram-oblig4](https://user-images.githubusercontent.com/59846048/81407446-b391db80-913b-11ea-8244-0b5d6b1d3707.png)

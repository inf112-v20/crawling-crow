![Crawling crow logo](assets/logo.png)

[![Build Status](https://travis-ci.com/inf112-v20/crawling-crow.svg?branch=master)](https://travis-ci.com/inf112-v20/crawling-crow) ![Codacy Badge](https://api.codacy.com/project/badge/Grade/1ef3b79326324c30a5b5b61d5addef5b)

Currently a simple and not functional implementation of Robo Rally.

## How to run
For now, you have to run the game manually.

1.  Run `Main.java` located in `src/main/java/roborally`.
2.  Move around using the [controls](#controls)
3.  When you want to exit, press <kbd>ESC</kbd> or exit the game manually by clicking on the **X**-button.

### How to play
#### Controls
##### Movement
Since you can't program the robot yet, you can move the robot with the following keys:

-   Forward: <kbd>&#8593;</kbd>
-   Backward: <kbd>&#8595;</kbd>
-   Turn left: <kbd>&#8592;</kbd>
-   Turn right: <kbd>&#8594;</kbd>

##### Combat
-   Fire laser: <kbd>F</kbd>

##### Other
-   Exit game: <kbd>ESC</kbd>
-   Start game: <kbd>ENTER</kbd>
-   Check if someone won: <kbd>W</kbd>
-   Register flag: <kbd>SPACE</kbd>

## Tests
### Automatic tests
All of our automatic tests can be found in `src/test/java/roborally`.

### Manual tests (while running the game)
-   Movement: please see the [controls for movement](#movement) above.
    -   We use these to see if the robot behaves as expected

-   Combat: please see the [controls for combat](#combat) above
    -   Currently only for lasers, check if

## Known bugs
-   If no `Robot`s have been generated, you will get a `NullPointerException`. See [#38](https://github.com/inf112-v20/crawling-crow/issues/38).
-   There are two bugs in `AssetsManager.java`. You have to call specific methods. See [#37](https://github.com/inf112-v20/crawling-crow/issues/37).
-   Only when a robot is pushed ontop of a **hole** or a **flag** will it change state (texture), but the robot being controlled will not change it's state. See [#87](https://github.com/inf112-v20/crawling-crow/issues/87).

### From Maven template
Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.

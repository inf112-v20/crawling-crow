![Crawling crow logo](assets/logo.png)

[![Build Status](https://travis-ci.com/inf112-v20/crawling-crow.svg?branch=master)](https://travis-ci.com/inf112-v20/crawling-crow) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/1ef3b79326324c30a5b5b61d5addef5b)](https://www.codacy.com/gh/inf112-v20/crawling-crow?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inf112-v20/crawling-crow&amp;utm_campaign=Badge_Grade)

Currently a simple and not functional implementation of Robo Rally.

## Deliverables
-   [Obligatorisk oppgave 1](Deliverables/ObligatoriskOppgave1.md)
-   [Obligatorisk oppgave 2](Deliverables/ObligatoriskOppgave2.md)
-   [Obligatorisk oppgave 3](Deliverables/ObligatoriskOppgave3.md)

## Setup
1.  Install [Maven](https://maven.apache.org/download.cgi)
2.  Clone the repository, `git clone git@github.com:inf112-v20/crawling-crow.git`
3.  Build game with dependencies, `mvn clean verify assembly:single`

## How to run
For now, you have to run the game manually.

1.  Run `Main.java` located in `src/main/java/roborally` in your IDE of choice.
    -   Alternatively use the command `java -jar target/roborally-crawlingcrow-1.0-SNAPSHOT-jar-with-dependencies.jar`

2.  Move around using the [controls](#controls)

3.  When you want to exit, press <kbd>ESC</kbd> or exit the game manually by clicking on the **X**-button.

### How to play
#### Menu Controls
Use mouse to navigate the menus. Here you can
-   Start a new game
-   Change map
-   Exit the game

Certain settings can be changed in the menu: 
-   Game volume
-   Play a song
-   Activate "Fun Mode"
-   Set game speed
-   Set laser travel speed

#### In-game Controls
-   Open game menu: <kbd>M</kbd>
-   Quick exit game (rage quit): <kbd>ESC</kbd>
-   Bring up your programming cards: <kbd>ENTER</kbd>

##### Program robot
Use your cursor to select the programming cards you wish to place in your register. If you wish to deselect a card, 
simply click on it again. Press "Done" when you are happy with your selection of cards.

#### Debug Controls
##### Movement
-   Forward: <kbd>&#8593;</kbd>
-   Backward: <kbd>&#8595;</kbd>
-   Turn left: <kbd>&#8592;</kbd>
-   Turn right: <kbd>&#8594;</kbd>

##### Combat
-   Fire your laser: <kbd>F</kbd>
-   Fire everyone's lasers: <kbd>A</kbd>

##### Other
-   Force one round, and one phase to run, expect programming your robot: <kbd>T</kbd>
-   End game and return to menu: <kbd>W</kbd>
-   Force Register flag: <kbd>SPACE</kbd>

## Fun mode
Fills the map with robots, otherwise plays like a normal game.

## Tests
### Automatic tests
All of our automatic tests can be found in `src/test/java/roborally`.

### Manual tests (while running the game)
#### General tests
-   When the game has started, verify that pressing <kbd>ESC</kbd> quits the game.

#### Menu tests
-   Verify that "Start New Game" starts a new game
-   When in a game, press <kbd>M</kbd> and verify that menu is visible again
-   When looking at the menu, during a game, verify that clicking "continue" will return to the game
-   Use cursor to click "Change Map" in the Menu, verify that "change map"-section is visible
-   Use cursor to click on arrows to look at different maps. Verify that visible map changes
-   In "Change map", decide a map, use cursor to click "Pick this map", and start the game. Verify that the selected map is used for the new game

#### Sound tests
-   In menu, press "Play a song", verify that a song plays
-   In menu, change volume, verify that game volume changes depending on your choice
-   Fire laser, verify laser sound
-   Move robots, verify movement sound

#### Optional settings tests
-   Change "Game Speed", verify that a round is played faster or slower, depending on your choice.
-   Change "Laser Speed", verify that lasers move faster or slower, depending on your choice.

#### FunMode tests
-   In the menu, Start New Game with "FunMode" set it to "On". Verify that board is filled with robots.
-   Start New Game with "FunMode" set it to "On", when already in a normal Game. Verify that board is filled with robots.
-   Start New Game with "FunMode" set it to "Off", when already in a FunMode Game. Verify that board has 8 robots.
-   A FunMode game should run as normal, only difference being many more robots. 

#### Movement test
Please see the [controls for movement](#movement) above. 
-   Check that `Robot` moves as expected with arrow keys
-   Push other `Robot`s around.
-   Check that your `Robot` cannot go outside game board (This will be changed later, because of the game rules).
-   Move `Robot` into wall to see if collision works.
-   Move into a stationary laser and verify that laser stop on the `Robot`.
-   Move out of a stationary laser and verify that laser reappears if the `Robot` was blocking it.
-   Push `Robot`s into walls to see if collision works.
-   Push `Robot`s outside of game bord, to see if they go outside

#### Robot's State changes
-   Check that you can push another `Robot` out of the map, `Robot` should be destroyed.
-   Check that you can push another `Robot` into holes, `Robot` should be destroyed.
-   Check that you can push another `Robot` onto flags and force check flag positions, <kbd>SPACE</kbd>. The `Robot` 
should now have updated its number of visited flags.

#### Robot's Texture changes
-   Push other `Robot`s ontop of _flags_ to see if their texture changes.
-   When your `Robot` is ontop of _flags_, check  if their texture changes.
-   Push other `Robot`s ontop of _hole_ to see if their texture changes.
-   When your `Robot` is ontop of _hole_, check  if their texture changes.

#### Laser tests
Please see the [controls for combat](#combat) above
-   Check if lasers fire when looking in all four directions by pressing <kbd>F</kbd>. 
-   Check if laser stops on `Robot`s and walls.
-   Check that lasers cross each other fine.

#### Rebooting and archive marker test
-   When a `Robot` is destroyed, it should reboot to `archiveMarker`.
-   When a `Robot` have rebooted 3 times, it should be not able to reboot again. i.e. The robot must be destroyed four 
times for it to be out of the game.

#### Flags and winner tests
-   Move `Robot` to **flag 1**, press <kbd>SPACE</kbd>, check console print `A flag has been visited` appears.
    -   Continue for the other flags in ascending order, pressing <kbd>SPACE</kbd> on each visist. 
    -   When all flags has been visited as explained above, the console should write a sentence telling you have visisted all flags.

#### Conveyor belt tests
-   For each direction
    -   For both `Normal conveyor belt` and `Express conveoyer bolt` 
        -   Move a `Robot` onto on a `conveyor belt`, facing that direction
        -   Press <kbd>T</kbd> to force one round (with one single phase)
        -   Verify that the `conveyor belt` moves the `robot` in its direction
-   For each possible turn
    -   For both `Normal conveyor belt` and `Express conveoyer bolt` 
        -   Move a `Robot` onto on a `conveyor belt`, _next-to and before_ the turn we want to test.
        -   Press <kbd>T</kbd> to force one round (with one single phase)
        -   Verify that the `conveyor belt` moves the `Robot` onto the turn, and rotates the `Robot` 90 degrees in the 
        direction of the turn
-   For each possible turn
    -   For both `Normal conveyor belt` and `Express conveoyer bolt` 
        -   Move a `Robot` onto on a `conveyor belt`, _onto_ the turn we want to test
        -   Press <kbd>T</kbd> to force one round (with one single phase)
        -   Verify that the `conveyor belt` moves the `Robot` to next conveyor belt, but does _not_ rotates the `Robot`.

#### Cog tests
-   Move a `Robot` onto on a `Clockwise turning cog`
    -   Press <kbd>T</kbd> to force one round (with one single phase)
    -   Verify that the cog rotates the robot clockwise
-   Move a `Robot` onto on a `Counter-clockwise turning cog`
    -   Press <kbd>T</kbd> to force one round (with one single phase)
    -   Verify that the cog rotates the robot counter-clockwise

#### Programming cards tests
-   Press <kbd>ENTER</kbd> to verify that the `programming cards` becomes visible on the screen
-   Using the cursor, press a `programming card` and verify a visual indication for the cards position in the register
-   Try to select six `programming cards` and verify that this is not possible
-   Press "Done" to trigger the next round to play
    -   Do this test for several rounds, such that all cards are tested.
        -   Move 1
        -   Move 2
        -   Move 3
        -   Rotate Left
        -   Rotate Right
        -   U-Turn
        -   Move backwards 1 
    -   Verify that your robot moves and rotates according to the cards you selected, in order.
    -   Verify that the number of cards on your hand is dependant on your robots health.
    -   Verify that can only play a number of cards equal to the minimum of (5, 9-number of damage tokens)

## Known bugs
See [Isses with Bug label](/../../issues?q=is%3Aissue+is%3Aopen+laser+label%3Abug)

### From Maven template
Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.

## Class diagram
![Oblig3UML](https://user-images.githubusercontent.com/59846048/77762946-98ab5200-703a-11ea-8993-dce653775105.jpg)
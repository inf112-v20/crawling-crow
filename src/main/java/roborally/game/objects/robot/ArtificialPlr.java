package roborally.game.objects.robot;


import roborally.game.objects.cards.IProgramCards;
import roborally.game.objects.cards.ProgramCards;
import roborally.game.objects.gameboard.IFlag;
import roborally.game.objects.gameboard.IGameBoard;

import java.util.ArrayList;
import java.util.Comparator;

public class ArtificialPlr {
    private Programmable robot;
    private IGameBoard gameBoard;
    private ArrayList<IFlag> flags;

    public ArtificialPlr(Programmable robot, IGameBoard gameBoard) {
        this.robot = robot;
        this.gameBoard = gameBoard;
        this.flags = this.gameBoard.findAllFlags();

    }

    public void printAllCardsAndFlags() {
        printAllCards();
        printAllFlags();
    }

    private ArrayList<ProgramCards.Card> printAllCards() {
        int totalMoves = 0;
        int totalRight = 0;
        int totalLeft = 0;
        ArrayList<ProgramCards.Card> temp = robot.getModel().getCards();
        temp.sort(Comparator.comparing(IProgramCards.Card::getCardType));
        for (ProgramCards.Card card : temp) {
            System.out.print(card.getCard() + " ");
            if(card.getValue()==1 || card.getValue() == 2 || card.getValue() == 3)
                totalMoves += card.getValue();
            else if(card.getValue() == 90)
                totalRight += card.getValue();
            else if(card.getValue() < 0)
                totalLeft += card.getValue();
            else
                totalMoves += card.getValue();
        }
        System.out.println();
        System.out.println(totalMoves);
        System.out.println(totalLeft);
        System.out.println(totalRight);
        return temp;
    }

    private void printAllFlags() {
        this.flags.sort(Comparator.comparing(IFlag::getID));
        for (IFlag flag : flags) {
            System.out.println(flag.getPosition() + " " + flag.getID());
            System.out.println(robot.getPosition().dst(flag.getPosition()));
        }
        System.out.println();
    }


    public int runRobotCore() {
        // Calculates a good move.
        return 123456789;
    }
}
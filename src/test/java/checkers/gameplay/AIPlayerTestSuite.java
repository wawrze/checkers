package checkers.gameplay;

import checkers.board.Board;
import checkers.figures.None;
import checkers.figures.Pawn;
import checkers.figures.Queen;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class AIPlayerTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests() {
        System.out.println("InGameUI tests: started");
    }

    @AfterClass
    public static void afterTests() {
        System.out.println("InGameUI tests: finished");
    }

    @Before
    public void before() {
        System.out.println("Test #" + counter + ": started");
    }

    @After
    public void after() {
        System.out.println("Test #" + counter + ": finished");
        counter++;
    }

    @Test
    public void testEndOfGameDrawEvaluation() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        RulesSet rulesSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        AIPlayer1 aiPlayer1;
        AIPlayer2 aiPlayer2;
        //When
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++)
                board.setFigure((char) (i + 64), j, new None(true));
        }
        board.setFigure('A', 8, new Queen(true));
        board.setFigure('H', 7, new Queen(false));
        aiPlayer1 = new AIPlayer1(board, true, rulesSet, 14, 14);
        aiPlayer2 = new AIPlayer2(board, false, rulesSet, 14, 14);
        aiPlayer1.getAIMove();
        aiPlayer2.getAIMove();
        //Then
    }

    @Test
    public void testGameAIvsAIStandardVictoryConditions() throws IncorrectMoveException, IncorrectMoveFormat {
        //Given
        RulesSet rulesSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        Game game = new Game("", rulesSet, true, true);
        ByteArrayInputStream in = new ByteArrayInputStream("x\n".getBytes());
        InGameUI inGameUI;
        //When
        inGameUI = new InGameUI(new Scanner(in));
        game.play(inGameUI);
        //Then
    }

    @Test
    public void testGameAIvsAIReversedVictoryConditions() throws IncorrectMoveException, IncorrectMoveFormat {
        //Given
        RulesSet rulesSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        Game game = new Game("", rulesSet, true, true);
        ByteArrayInputStream in = new ByteArrayInputStream("x\n".getBytes());
        InGameUI inGameUI;
        //When
        inGameUI = new InGameUI(new Scanner(in));
        game.play(inGameUI);
        //Then
    }

    @Test
    public void testEqualEvaluation() throws IncorrectMoveException, IncorrectMoveFormat {
        //Given
        RulesSet rulesSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        Game game = new Game("", rulesSet, true, true);
        ByteArrayInputStream in = new ByteArrayInputStream("x\n".getBytes());
        InGameUI inGameUI;
        //When
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++)
                game.getBoard().setFigure((char) (i + 64), j, new None(true));
        }
        game.getBoard().setFigure('A', 4, new Pawn(true));
        game.getBoard().setFigure('H', 3, new Pawn(false));
        inGameUI = new InGameUI(new Scanner(in));
        game.play(inGameUI);
        //Then
    }

}
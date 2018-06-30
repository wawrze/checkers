package checkers.gameplay;

import checkers.figures.None;
import checkers.figures.Pawn;
import checkers.figures.Queen;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class GameTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("InGameUI tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("InGameUI tests: finished");
    }

    @Before
    public void before(){
        System.out.println("Test #" + counter + ": started");
    }

    @After
    public void after(){
        System.out.println("Test #" + counter + ": finished");
        counter++;
    }

    @Test
    public void testSaveGame() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        ByteArrayInputStream in1 = new ByteArrayInputStream("s\nGame name\n".getBytes());
        ByteArrayInputStream in2 = new ByteArrayInputStream("x\n".getBytes());
        RulesSet rulesSet = new RulesSet(true, true, true,
                true, true, true,
                "", "");
        Game game = new Game("", rulesSet, false, false);
        InGameUI inGameUI;
        boolean result1, result2;
        //When
        inGameUI = new InGameUI(new Scanner(in1));
        result1 = game.play(inGameUI);
        inGameUI = new InGameUI(new Scanner(in2));
        result2 = game.play(inGameUI);
        //Then
        Assert.assertTrue(result1);
        Assert.assertFalse(result2);
    }

    @Test
    public void testNullOrIncorrectMove() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        ByteArrayInputStream in1 = new ByteArrayInputStream("h\n\n\n\nx\n".getBytes());
        ByteArrayInputStream in2 = new ByteArrayInputStream("A1-Q9\n\nx\n".getBytes());
        ByteArrayInputStream in3 = new ByteArrayInputStream("Abc\n\nx\n".getBytes());
        ByteArrayInputStream in4 = new ByteArrayInputStream("A1-A2\n\nx\n".getBytes());
        RulesSet rulesSet = new RulesSet(true, true, true,
                true, true, true,
                "", "");
        Game game = new Game("", rulesSet, false, false);
        InGameUI inGameUI;
        //When
        inGameUI = new InGameUI(new Scanner(in1));
        game.play(inGameUI);
        inGameUI = new InGameUI(new Scanner(in2));
        game.play(inGameUI);
        inGameUI = new InGameUI(new Scanner(in3));
        game.play(inGameUI);
        inGameUI = new InGameUI(new Scanner(in4));
        game.play(inGameUI);
        //Then
    }

    @Test
    public void testQueenMovesCounter() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        ByteArrayInputStream in = new ByteArrayInputStream("H1-G2\n\nA8-B7\n\nx\n".getBytes());
        RulesSet rulesSet = new RulesSet(true, true, true,
                true, true, true,
                "", "");
        Game game = new Game("", rulesSet, false, false);
        InGameUI inGameUI;
        int blackQueenMovesCounter;
        int whiteQueenMovesCounter;
        //When
        for(int i = 1;i < 9;i++) {
            for(int j = 1;j < 9;j++)
                game.getBoard().setFigure((char) (i + 64), j, new None(true));
        }
        game.getBoard().setFigure('A', 8, new Queen(true));
        game.getBoard().setFigure('H', 1, new Queen(false));
        inGameUI = new InGameUI(new Scanner(in));
        game.play(inGameUI);
        blackQueenMovesCounter = game.getBlackQueenMoves();
        whiteQueenMovesCounter = game.getWhiteQueenMoves();
        //Then
        Assert.assertEquals(1, whiteQueenMovesCounter);
        Assert.assertEquals(1, blackQueenMovesCounter);
    }

    @Test
    public void testPawnToQueen() throws IncorrectMoveException, IncorrectMoveFormat {
        //Given
        ByteArrayInputStream in = new ByteArrayInputStream("B7-A8\n\nG2-H1\n\nx\n".getBytes());
        RulesSet rulesSet = new RulesSet(true, true, true,
                true, true, true,
                "", "");
        Game game = new Game("", rulesSet, false, false);
        InGameUI inGameUI;
        //When
        for(int i = 1;i < 9;i++) {
            for(int j = 1;j < 9;j++)
                game.getBoard().setFigure((char) (i + 64), j, new None(true));
        }
        game.getBoard().setFigure('B', 7, new Pawn(false));
        game.getBoard().setFigure('G', 2, new Pawn(true));
        inGameUI = new InGameUI(new Scanner(in));
        game.play(inGameUI);
        //Then
        Assert.assertTrue(game.getBoard().getFigure('A', 8) instanceof Queen);
        Assert.assertTrue(game.getBoard().getFigure('H', 1) instanceof Queen);
    }

    @Test
    public void testMultiCapture() throws IncorrectMoveException, IncorrectMoveFormat {
        //Given
        ByteArrayInputStream in = new ByteArrayInputStream("e8-c6\nc6-d5\n\nc6-a8\n\nx\n".getBytes());
        RulesSet rulesSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        Game game = new Game("", rulesSet, false, true);
        InGameUI inGameUI;
        //When
        for(int i = 1;i < 9;i++) {
            for(int j = 1;j < 9;j++)
                game.getBoard().setFigure((char) (i + 64), j, new None(true));
        }
        game.getBoard().setFigure('B', 3, new Pawn(true));
        game.getBoard().setFigure('D', 3, new Pawn(true));
        game.getBoard().setFigure('E', 8, new Pawn(true));
        game.getBoard().setFigure('F', 5, new Pawn(true));
        game.getBoard().setFigure('B', 7, new Pawn(false));
        game.getBoard().setFigure('D', 7, new Pawn(false));
        game.getBoard().setFigure('G', 6, new Pawn(false));
        inGameUI = new InGameUI(new Scanner(in));
        game.play(inGameUI);
        //Then
    }

    @Test
    public void testMultiCapture2() throws IncorrectMoveException, IncorrectMoveFormat {
        //Given
        ByteArrayInputStream in = new ByteArrayInputStream("g6-e8\nh\n\ne8-c6\nc6-b7\n\nc6-a4\n\nx\n".getBytes());
        RulesSet rulesSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        Game game = new Game("", rulesSet, true, false);
        InGameUI inGameUI;
        //When
        for(int i = 1;i < 9;i++) {
            for(int j = 1;j < 9;j++)
                game.getBoard().setFigure((char) (i + 64), j, new None(true));
        }
        game.getBoard().setFigure('B', 3, new Pawn(true));
        game.getBoard().setFigure('B', 5, new Pawn(true));
        game.getBoard().setFigure('D', 7, new Pawn(true));
        game.getBoard().setFigure('F', 7, new Pawn(true));
        game.getBoard().setFigure('C', 2, new Pawn(false));
        game.getBoard().setFigure('E', 2, new Pawn(false));
        game.getBoard().setFigure('G', 2, new Pawn(false));
        game.getBoard().setFigure('G', 6, new Pawn(false));
        inGameUI = new InGameUI(new Scanner(in));
        game.play(inGameUI);
        //Then
    }

}
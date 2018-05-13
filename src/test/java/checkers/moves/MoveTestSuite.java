package checkers.moves;

import checkers.board.Board;
import checkers.figures.*;
import exceptions.IncorrectMoveFormat;
import org.junit.*;

public class MoveTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("Move tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("Move tests: finished");
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
    public void testConstructorCorrect(){
        //Given
        Board board = new Board();
        Move move;
        boolean result;
        //When
        try{
            move = new Move('A',1,'H',8);
            result = true;
        }
        catch (IncorrectMoveFormat e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testConstructorRowOutOfBound(){
        //Given
        Board board = new Board();
        Move move;
        boolean result;
        //When
        try{
            move = new Move('R',1,'H',8);
            result = true;
        }
        catch (IncorrectMoveFormat e){
            result = false;
        }
        //Then
        Assert.assertFalse(result);
    }

    @Test
    public void testConstructorColToLow(){
        //Given
        Board board = new Board();
        Move move;
        boolean result;
        //When
        try{
            move = new Move('A',0,'H',8);
            result = true;
        }
        catch (IncorrectMoveFormat e){
            result = false;
        }
        //Then
        Assert.assertFalse(result);
    }

    @Test
    public void testConstructorColToHigh(){
        //Given
        Move move;
        boolean result;
        //When
        try{
            move = new Move('A',1,'H',10);
            result = true;
        }
        catch (IncorrectMoveFormat e){
            result = false;
        }
        //Then
        Assert.assertFalse(result);
    }

    @Test
    public void testGetRowsCols() throws IncorrectMoveFormat{
        //Given
        Move move;
        //When
        move = new Move('A',1,'H',8);
        //Then
        Assert.assertEquals('A',move.getRow1());
        Assert.assertEquals(1,move.getCol1());
        Assert.assertEquals('H',move.getRow2());
        Assert.assertEquals(8,move.getCol2());
    }

    @Test
    public void testGetRowsInt() throws IncorrectMoveFormat{
        //Given
        Move move;
        //When
        move = new Move('A',1,'H',8);
        //Then
        Assert.assertEquals(1,move.getRow1int());
        Assert.assertEquals(8,move.getRow2int());
    }

    @Test
    public void testMakeMovePawn() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'B',3);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('A',2,pawn);
        move.makeMove(board);
        //Then
        Assert.assertEquals(pawn,board.getFigure('B',3));
        Assert.assertTrue(board.getFigure('A',2) instanceof None);
    }

    @Test
    public void testMakeMoveQueen() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'G',8);
        Queen queen = new Queen(true);
        //When
        board.setFigure('A',2,queen);
        move.makeMove(board);
        //Then
        Assert.assertEquals(queen,board.getFigure('G',8));
        Assert.assertTrue(board.getFigure('A',2) instanceof None);
    }

    @Test
    public void testMakeMovePawnToQueenBlack() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('G',2,'H',3);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('G',2,pawn);
        move.makeMove(board);
        //Then
        Assert.assertTrue(board.getFigure('H',3) instanceof Queen);
    }

    @Test
    public void testMakeMovePawnToQueenWhite() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('B',1,'A',2);
        Pawn pawn = new Pawn(false);
        //When
        board.setFigure('B',1,pawn);
        move.makeMove(board);
        //Then
        Assert.assertTrue(board.getFigure('A',2) instanceof Queen);
    }

    @Test
    public void testMakeCaptureRightDown() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'C',4);
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(false);
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('B',3,pawn2);
        move.makeCapture(board);
        //Then
        Assert.assertEquals(pawn1,board.getFigure('C',4));
        Assert.assertTrue(board.getFigure('B',3) instanceof None);
    }

    @Test
    public void testMakeCaptureRightUp() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('C',2,'A',4);
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(false);
        //When
        board.setFigure('C',2,pawn1);
        board.setFigure('B',3,pawn2);
        move.makeCapture(board);
        //Then
        Assert.assertEquals(pawn1,board.getFigure('A',4));
        Assert.assertTrue(board.getFigure('B',3) instanceof None);
    }

    @Test
    public void testMakeCaptureLeftUp() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('C',4,'A',2);
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(false);
        //When
        board.setFigure('C',4,pawn1);
        board.setFigure('B',3,pawn2);
        move.makeCapture(board);
        //Then
        Assert.assertEquals(pawn1,board.getFigure('A',2));
        Assert.assertTrue(board.getFigure('B',3) instanceof None);
    }

    @Test
    public void testMakeCaptureLeftDown() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('A',4,'C',2);
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(false);
        //When
        board.setFigure('A',4,pawn1);
        board.setFigure('B',3,pawn2);
        move.makeCapture(board);
        //Then
        Assert.assertEquals(pawn1,board.getFigure('C',2));
        Assert.assertTrue(board.getFigure('B',3) instanceof None);
    }

}

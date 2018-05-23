package checkers.moves;

import checkers.board.*;
import checkers.figures.*;
import exceptions.*;
import org.junit.*;

public class MoveValidatorTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("MoveValidator tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("MoveValidator tests: finished");
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
    public void testCorrectPawnMove() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'B',3);
        boolean result;
        Pawn pawn = new Pawn(true);
        board.setFigure('A',2,pawn);
        //When
        try{
            MoveValidator.validateMove(move,board,true);
            result = true;
        }
        catch(IncorrectMoveException | CaptureException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testNoFigureToMove() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'B',3);
        boolean result;
        //When
        result = isIncorrectMove(board, move,true, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testTargetOccupied() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'B',3);
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        boolean result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('B',3,pawn2);
        result = isIncorrectMove(board, move, true, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testNoBias() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'B',2);
        Pawn pawn = new Pawn(true);
        boolean result;
        //When
        board.setFigure('A',2,pawn);
        result = isIncorrectMove(board, move, true, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testOtherPlayersFigure() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'B',3);
        Pawn pawn = new Pawn(true);
        boolean result;
        //When
        board.setFigure('A',2,pawn);
        result = isIncorrectMove(board, move, false, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testPawnMoreThanOneFieldRange() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'D',5);
        Pawn pawn = new Pawn(true);
        boolean result;
        //When
        board.setFigure('A',2,pawn);
        result = isIncorrectMove(board, move, true, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testPawnTwoFieldsNoCapture() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'C',4);
        Pawn pawn = new Pawn(true);
        boolean result;
        //When
        board.setFigure('A',2,pawn);
        result = isIncorrectMove(board, move, true, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testPawnTwoFieldsAndCapture() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'C',4);
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(false);
        boolean result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('B',3,pawn2);
        result = isIncorrectMove(board, move, true, true);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testWhitePawnDirection() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'B',3);
        Pawn pawn = new Pawn(false);
        boolean result;
        //When
        board.setFigure('A',2,pawn);
        result = isIncorrectMove(board, move, false, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testBlackPawnDirection() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('B',3,'A',2);
        Pawn pawn = new Pawn(true);
        boolean result;
        //When
        board.setFigure('B',3,pawn);
        result = isIncorrectMove(board, move, true, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testCorrectQueenMoveRightDown() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'G',8);
        boolean result;
        Queen queen = new Queen(true);
        //When
        board.setFigure('A',2,queen);
        try{
            MoveValidator.validateMove(move,board,true);
            result = true;
        }
        catch(IncorrectMoveException | CaptureException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testFigureOnWayNoCaptureRightDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'G',8);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('A',2,queen);
        board.setFigure('B',3,pawn);
        result = isIncorrectMove(board, move, true, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testFigureOnWayNoCapture2RightDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'G',8);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('A',2,queen);
        board.setFigure('F',7,pawn);
        result = isIncorrectMove(board, move, true, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testFigureOnWayAndCaptureRightDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',2,'G',8);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        //When
        board.setFigure('A',2,queen);
        board.setFigure('F',7,pawn);
        result = isIncorrectMove(board, move, true, true);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testCorrectQueenMoveRightUp() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('H',1,'A',8);
        boolean result;
        Queen queen = new Queen(true);
        //When
        board.setFigure('H',1,queen);
        try{
            MoveValidator.validateMove(move,board,true);
            result = true;
        }
        catch(IncorrectMoveException | CaptureException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testFigureOnWayNoCaptureRightUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',1,'A',8);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('H',1,queen);
        board.setFigure('G',2,pawn);
        result = isIncorrectMove(board, move, true, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testFigureOnWayNoCapture2RightUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',1,'A',8);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('H',1,queen);
        board.setFigure('B',7,pawn);
        result = isIncorrectMove(board, move, true, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testFigureOnWayAndCaptureRightUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',1,'A',8);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        //When
        board.setFigure('H',1,queen);
        board.setFigure('B',7,pawn);
        result = isIncorrectMove(board, move, true, true);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testCorrectQueenMoveLeftUp() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('H',7,'B',1);
        boolean result;
        Queen queen = new Queen(true);
        //When
        board.setFigure('H',7,queen);
        try{
            MoveValidator.validateMove(move,board,true);
            result = true;
        }
        catch(IncorrectMoveException | CaptureException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testFigureOnWayNoCaptureLeftUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',7,'B',1);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('H',7,queen);
        board.setFigure('G',6,pawn);
        result = isIncorrectMove(board, move, true, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testFigureOnWayNoCapture2LeftUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',7,'B',1);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('H',7,queen);
        board.setFigure('C',2,pawn);
        result = isIncorrectMove(board, move, true, false);
        //Then
        Assert.assertTrue(result);
    }


    @Test
    public void testFigureOnWayAndCaptureLeftUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('H',7,'B',1);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        //When
        board.setFigure('H',7,queen);
        board.setFigure('C',2,pawn);
        result = isIncorrectMove(board, move, true, true);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testCorrectQueenMoveLeftDown() throws IncorrectMoveFormat{
        //Given
        Board board = new Board();
        Move move = new Move('A',8,'H',1);
        boolean result;
        Queen queen = new Queen(true);
        //When
        board.setFigure('A',8,queen);
        try{
            MoveValidator.validateMove(move,board,true);
            result = true;
        }
        catch(IncorrectMoveException | CaptureException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testFigureOnWayNoCaptureLeftDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',8,'H',1);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('A',8,queen);
        board.setFigure('B',7,pawn);
        result = isIncorrectMove(board, move, true, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testFigureOnWayNoCapture2LeftDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',8,'H',1);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('A',8,queen);
        board.setFigure('G',2,pawn);
        result = isIncorrectMove(board, move, true, false);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testFigureOnWayAndCaptureLeftDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A',8,'H',1);
        boolean result;
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(false);
        //When
        board.setFigure('A',8,queen);
        board.setFigure('G',2,pawn);
        result = isIncorrectMove(board, move, true, true);
        //Then
        Assert.assertTrue(result);
    }

    private boolean isIncorrectMove(Board board, Move move, boolean player, boolean capture) {
        boolean result;
        try{
            MoveValidator.validateMove(move,board,player);
            result = false;
        }
        catch(IncorrectMoveException e){
            result = !capture ? true : false;
        }
        catch(CaptureException e){
            result = capture ? true : false;
        }
        return result;
    }



}

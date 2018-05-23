package checkers.moves;

import checkers.board.*;
import checkers.figures.*;
import exceptions.*;
import org.junit.*;

public class CapturePossibilityValidatorTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("CapturePossibilityValidator tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("CapturePossibilityValidator tests: finished");
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
    public void testNoCapturesOnBoardWhiteTurn(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Queen queen = new Queen(false);
        Pawn pawn8 = new Pawn(false);
        boolean result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,queen);
        board.setFigure('H',7,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,false);
            result = true;
        }
        catch(CapturePossibleException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testNoCapturesOnBoardBlackTurn(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Queen queen = new Queen(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        boolean result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,queen);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,true);
            result = true;
        }
        catch(CapturePossibleException e){
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testOneCaptureForBlackPawns(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('D',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,true);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(1,result);
    }

    @Test
    public void testTwoCapturesForBlackPawns(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('D',3,pawn5);
        board.setFigure('D',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,true);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(2,result);
    }

    @Test
    public void testThreeCapturesForBlackPawns(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('D',3,pawn5);
        board.setFigure('D',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('D',5,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,true);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(3,result);
    }

    @Test
    public void testOneCaptureForWhitePawns(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('E',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,false);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(1,result);
    }

    @Test
    public void testTwoCapturesForWhitePawns(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('E',2,pawn3);
        board.setFigure('E',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,false);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(2,result);
    }

    @Test
    public void testThreeCapturesForWhitePawns(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('E',4,pawn2);
        board.setFigure('E',2,pawn3);
        board.setFigure('E',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,false);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(3,result);
    }

    @Test
    public void testCaptureForBlackPawnInWhiteTurn(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('D',3,pawn5);
        board.setFigure('D',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('D',5,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,false);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(3,result);
    }

    @Test
    public void testCaptureForWhitePawnInBlackTurn(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('E',4,pawn2);
        board.setFigure('E',2,pawn3);
        board.setFigure('E',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,true);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(3,result);
    }

    @Test
    public void testOneCaptureForWhiteQueen(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Queen queen = new Queen(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',5,queen);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,false);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(1,result);
    }

    @Test
    public void testTwoCapturesForWhiteQueen(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Queen queen1 = new Queen(false);
        Queen queen2 = new Queen(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('F',3,queen1);
        board.setFigure('F',5,queen2);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,false);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(3,result);
    }

    @Test
    public void testCaptureForWhiteQueenAndWhitePawn(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(false);
        Queen queen = new Queen(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('E',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',5,queen);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,false);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(2,result);
    }

    @Test
    public void testOneCaptureForBlackQueen(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Queen queen = new Queen(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,queen);
        board.setFigure('G',2,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('H',7,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,true);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(1,result);
    }

    @Test
    public void testTwoCapturesForBlackQueen(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Queen queen1 = new Queen(true);
        Queen queen2 = new Queen(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,queen1);
        board.setFigure('C',6,queen2);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('D',3,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,true);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(6,result);
    }

    @Test
    public void testCaptureForBlackQueenAndBlackPawn(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Queen queen = new Queen(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,queen);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('D',3,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibility(board,true);
            result = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result = sArray.length;
        }
        //Then
        Assert.assertEquals(3,result);
    }

    @Test
    public void testOneFigureValidator(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Queen queen2 = new Queen(true);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        int result1, result2,result3;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('A',6,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('C',6,queen2);
        board.setFigure('F',3,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',3,pawn7);
        board.setFigure('D',3,pawn8);
        try{
            CapturePossibilityValidator.validateCapturePossibilityForOneFigure(board,'C',6);
            result1 = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result1 = sArray.length;
        }
        try{
            CapturePossibilityValidator.validateCapturePossibilityForOneFigure(board,'C',2);
            result2 = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result2 = sArray.length;
        }
        try{
            CapturePossibilityValidator.validateCapturePossibilityForOneFigure(board,'A',2);
            result3 = 0;
        }
        catch(CapturePossibleException e){
            String[] sArray = e.getMessage().split(" ");
            result3 = sArray.length;
        }
        //Then
        Assert.assertEquals(2,result1);
        Assert.assertEquals(1,result2);
        Assert.assertEquals(0,result3);
    }

}
package checkers.moves;

import checkers.board.Board;
import checkers.figures.None;
import checkers.figures.Pawn;
import checkers.figures.Queen;
import exceptions.IncorrectMoveFormat;
import org.junit.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ThrowablePrintedToSystemOut")
public class MoveTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests() {
        System.out.println("Move tests: started");
    }

    @AfterClass
    public static void afterTests() {
        System.out.println("Move tests: finished");
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
    public void testConstructorCorrect() {
        //Given
        boolean result;
        //When
        try {
            new Move('A', 1, 'H', 8);
            result = true;
        } catch (IncorrectMoveFormat e) {
            result = false;
        }
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testConstructorRowOutOfBound() {
        //Given
        boolean result1, result2;
        //When
        try {
            new Move('R', 1, 'A', 8);
            result1 = true;
        } catch (IncorrectMoveFormat e) {
            result1 = false;
        }
        try {
            new Move('A', 1, 'R', 8);
            result2 = true;
        } catch (IncorrectMoveFormat e) {
            result2 = false;
        }
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
    }

    @Test
    public void testGetRowInt() throws IncorrectMoveFormat {
        //Given
        Move move1 = new Move('A', 1, 'B', 1);
        Move move2 = new Move('C', 1, 'D', 1);
        Move move3 = new Move('E', 1, 'F', 1);
        Move move4 = new Move('G', 1, 'H', 1);
        Move moveMock = mock(Move.class);
        when(moveMock.getRow1()).thenReturn('I');
        int result1, result2, result3, result4, result5, result6, result7, result8, result9;
        //When
        result1 = move1.getRow1int();
        result2 = move1.getRow2int();
        result3 = move2.getRow1int();
        result4 = move2.getRow2int();
        result5 = move3.getRow1int();
        result6 = move3.getRow2int();
        result7 = move4.getRow1int();
        result8 = move4.getRow2int();
        result9 = moveMock.getRow1int();
        //Then
        Assert.assertEquals(1, result1);
        Assert.assertEquals(2, result2);
        Assert.assertEquals(3, result3);
        Assert.assertEquals(4, result4);
        Assert.assertEquals(5, result5);
        Assert.assertEquals(6, result6);
        Assert.assertEquals(7, result7);
        Assert.assertEquals(8, result8);
        Assert.assertEquals(0, result9);
    }

    @Test
    public void testToUpperCase() throws IncorrectMoveFormat {
        //Given
        Move move;
        //When
        move = new Move('a', 1, 'b', 2);
        //Then
        Assert.assertEquals('A', move.getRow1());
        Assert.assertEquals('B', move.getRow2());
    }

    @Test
    public void testToString() throws IncorrectMoveFormat {
        //Given
        Move move = new Move('A', 1, 'B', 2);
        String s;
        //When
        s = "" + move;
        //Then
        Assert.assertEquals("A1-B2", s);
    }

    @Test
    public void testConstructorColToLow() {
        //Given
        boolean result;
        //When
        try {
            new Move('A', 0, 'H', 9);
            result = true;
        } catch (IncorrectMoveFormat e) {
            result = false;
        }
        //Then
        Assert.assertFalse(result);
    }

    @Test
    public void testConstructorColToHigh() {
        //Given
        boolean result;
        //When
        try {
            new Move('A', 1, 'H', 10);
            result = true;
        } catch (IncorrectMoveFormat e) {
            result = false;
        }
        //Then
        Assert.assertFalse(result);
    }

    @Test
    public void testGetRowsCols() throws IncorrectMoveFormat {
        //Given
        Move move;
        //When
        move = new Move('A', 1, 'H', 8);
        //Then
        Assert.assertEquals('A', move.getRow1());
        Assert.assertEquals(1, move.getCol1());
        Assert.assertEquals('H', move.getRow2());
        Assert.assertEquals(8, move.getCol2());
    }

    @Test
    public void testGetRowsInt() throws IncorrectMoveFormat {
        //Given
        Move move;
        //When
        move = new Move('A', 1, 'H', 8);
        //Then
        Assert.assertEquals(1, move.getRow1int());
        Assert.assertEquals(8, move.getRow2int());
    }

    @Test
    public void testMakeMovePawn() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A', 2, 'B', 3);
        Pawn pawn = new Pawn(true);
        //When
        board.setFigure('A', 2, pawn);
        move.makeMove(board);
        //Then
        Assert.assertEquals(pawn, board.getFigure('B', 3));
        Assert.assertTrue(board.getFigure('A', 2) instanceof None);
    }

    @Test
    public void testMakeMoveQueen() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A', 2, 'G', 8);
        Queen queen = new Queen(true);
        //When
        board.setFigure('A', 2, queen);
        move.makeMove(board);
        //Then
        Assert.assertEquals(queen, board.getFigure('G', 8));
        Assert.assertTrue(board.getFigure('A', 2) instanceof None);
    }

    @Test
    public void testMakeCaptureRightDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A', 2, 'C', 4);
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(false);
        //When
        board.setFigure('A', 2, pawn1);
        board.setFigure('B', 3, pawn2);
        move.makeCapture(board, 'B', 3);
        //Then
        Assert.assertEquals(pawn1, board.getFigure('C', 4));
        Assert.assertTrue(board.getFigure('B', 3) instanceof None);
    }

    @Test
    public void testMakeCaptureRightUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('C', 2, 'A', 4);
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(false);
        //When
        board.setFigure('C', 2, pawn1);
        board.setFigure('B', 3, pawn2);
        move.makeCapture(board, 'B', 3);
        //Then
        Assert.assertEquals(pawn1, board.getFigure('A', 4));
        Assert.assertTrue(board.getFigure('B', 3) instanceof None);
    }

    @Test
    public void testMakeCaptureLeftUp() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('C', 4, 'A', 2);
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(false);
        //When
        board.setFigure('C', 4, pawn1);
        board.setFigure('B', 3, pawn2);
        move.makeCapture(board, 'B', 3);
        //Then
        Assert.assertEquals(pawn1, board.getFigure('A', 2));
        Assert.assertTrue(board.getFigure('B', 3) instanceof None);
    }

    @Test
    public void testMakeCaptureLeftDown() throws IncorrectMoveFormat {
        //Given
        Board board = new Board();
        Move move = new Move('A', 4, 'C', 2);
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(false);
        //When
        board.setFigure('A', 4, pawn1);
        board.setFigure('B', 3, pawn2);
        move.makeCapture(board, 'B', 3);
        //Then
        Assert.assertEquals(pawn1, board.getFigure('C', 2));
        Assert.assertTrue(board.getFigure('B', 3) instanceof None);
    }

    @Test
    public void testIncorrectMoveFormat() {
        //Given
        char x1 = 'A';
        char x2 = '.';
        int y1 = 1;
        int y2 = 100;
        boolean result1, result2;
        //When
        try {
            new Move(x1, y1, x1, y2);
            result1 = false;
        } catch (IncorrectMoveFormat e) {
            System.out.println(e);
            result1 = true;
        }
        try {
            new Move(x2, y1, x1, y1);
            result2 = false;
        } catch (IncorrectMoveFormat e) {
            result2 = true;
        }
        //Then
        Assert.assertTrue(result1);
        Assert.assertTrue(result2);
    }

}

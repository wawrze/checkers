package checkers.gameplay;

import checkers.board.Board;
import checkers.moves.Move;
import exceptions.IncorrectMoveFormat;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(VictoryValidator.class)
public class InGameUITestSuite {

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
    public void testPrintMoveHistory() throws IncorrectMoveFormat {
        //Given
        Move move1 = new Move('A', 1, 'B', 2);
        Move move2 = new Move('A', 1, 'B', 2);
        Move move3 = new Move('A', 1, 'B', 2);
        List<String> list = new ArrayList<>();
        InGameUI inGameUI = new InGameUI(new Scanner(System.in));
        //When
        inGameUI.printMoveHistory(list);
        list.add("" + move1);
        list.add("" + move2);
        list.add("" + move3);
        inGameUI.printMoveHistory(list);
        //Then
    }

    @Test
    public void testGetGameName() {
        //Given
        String name = "GameName";
        ByteArrayInputStream in = new ByteArrayInputStream(("\n" + name + "\n").getBytes());
        System.setIn(in);
        InGameUI inGameUI = new InGameUI(new Scanner(System.in));
        String result;
        //When
        result = inGameUI.getGameName();
        //Then
        Assert.assertEquals(name, result);
        //CleanUp
        System.setIn(System.in);
    }

    @Test
    public void testSideMenuSimple() {
        //Given
        String move = "A1-B2";
        List<String> list = new ArrayList<>();
        InGameUI inGameUI = new InGameUI(new Scanner(System.in));
        //When
        for (int i = 1; i < 27; i++)
            inGameUI.sideMenuSimple(i, list);
        for (int j = 0; j < 10; j++) {
            list.add(move);
            for (int i = 16; i < 27; i++)
                inGameUI.sideMenuSimple(i, list);
        }
        //Then
    }

    @Test
    public void testMakingMove() {
        //Given
        ByteArrayInputStream in = new ByteArrayInputStream(("\n\n\n\n\n\n\n").getBytes());
        System.setIn(in);
        InGameUI inGameUI = new InGameUI(new Scanner(System.in));
        boolean isItAITurn = false;
        boolean simplePrint = false;
        //When
        do {
            do {
                inGameUI.printMakingMove(simplePrint, 'A', 1, 'B', 2, isItAITurn);
                inGameUI.printMoveDone(simplePrint, isItAITurn);
                inGameUI.printCaptureDone(simplePrint, isItAITurn);
                inGameUI.printIncorrectMove("abc", simplePrint, isItAITurn);
                inGameUI.printCapture("abc", simplePrint, isItAITurn);
                inGameUI.printMultiCapture("abc", simplePrint, isItAITurn);
                inGameUI.printCaptureObligatory(simplePrint, isItAITurn);
                inGameUI.printIncorrectMoveFormat(simplePrint, isItAITurn);
                simplePrint = !simplePrint;
            } while (simplePrint);
            isItAITurn = !isItAITurn;
        } while (isItAITurn);
        //CleanUp
        System.setIn(System.in);
    }

    @Test
    public void testGetMoveOrOption() {
        //Given
        ByteArrayInputStream in1 = new ByteArrayInputStream("h\n".getBytes());
        ByteArrayInputStream in2 = new ByteArrayInputStream("A1-B2\n".getBytes());
        ByteArrayInputStream in3 = new ByteArrayInputStream("B2-A1\n\n\n".getBytes());
        ByteArrayInputStream in4 = new ByteArrayInputStream("Abc\n\n\n".getBytes());
        ByteArrayInputStream in5 = new ByteArrayInputStream("Abc-Def\n\n\n".getBytes());
        InGameUI inGameUI;
        String[] result1;
        String[] result2;
        String[] result3;
        String[] result4;
        String[] result5;
        String captures1 = "";
        String captures2 = "A1-B2";
        //When
        System.setIn(in1);
        inGameUI = new InGameUI(new Scanner(System.in));
        result1 = inGameUI.getMoveOrOption(captures1, false, false);
        System.setIn(in2);
        inGameUI = new InGameUI(new Scanner(System.in));
        result2 = inGameUI.getMoveOrOption(captures1, true, false);
        System.setIn(in3);
        inGameUI = new InGameUI(new Scanner(System.in));
        result3 = inGameUI.getMoveOrOption(captures2, false, false);
        System.setIn(in4);
        inGameUI = new InGameUI(new Scanner(System.in));
        result4 = inGameUI.getMoveOrOption(captures1, false, false);
        System.setIn(in5);
        inGameUI = new InGameUI(new Scanner(System.in));
        result5 = inGameUI.getMoveOrOption(captures1, false, false);
        //Then
        Assert.assertEquals(1, result1.length);
        Assert.assertEquals("h", result1[0]);
        Assert.assertEquals(4, result2.length);
        Assert.assertEquals("A", result2[0]);
        Assert.assertEquals("1", result2[1]);
        Assert.assertEquals("B", result2[2]);
        Assert.assertEquals("2", result2[3]);
        Assert.assertNull(result3);
        Assert.assertNull(result4);
        Assert.assertNull(result5);
        //CleanUp
        System.setIn(System.in);
    }

    @Test
    public void testEndOfGame() {
        //Given
        ByteArrayInputStream in1 = new ByteArrayInputStream("h\n\ns\n".getBytes());
        ByteArrayInputStream in2 = new ByteArrayInputStream("x\n".getBytes());
        ByteArrayInputStream in3 = new ByteArrayInputStream("x\n".getBytes());
        ByteArrayInputStream in4 = new ByteArrayInputStream("x\n".getBytes());
        ByteArrayInputStream in5 = new ByteArrayInputStream("x\n".getBytes());
        ByteArrayInputStream in6 = new ByteArrayInputStream("x\n".getBytes());
        PowerMockito.mockStatic(VictoryValidator.class);
        InGameUI inGameUI;
        List<String> moves = new ArrayList<>();
        Board board = new Board();
        //When
        moves.add("A1-B2");
        moves.add("B2-C3");
        moves.add("C3-D4");
        System.setIn(in1);
        inGameUI = new InGameUI(new Scanner(System.in));
        BDDMockito.given(VictoryValidator.isDraw()).willReturn(true);
        inGameUI.endOfGame(board, false, moves, true);
        System.setIn(in2);
        inGameUI = new InGameUI(new Scanner(System.in));
        inGameUI.endOfGame(board, true, moves, true);
        BDDMockito.given(VictoryValidator.isDraw()).willReturn(false);
        BDDMockito.given(VictoryValidator.getWinner()).willReturn(true);
        System.setIn(in3);
        inGameUI = new InGameUI(new Scanner(System.in));
        inGameUI.endOfGame(board, false, moves, true);
        System.setIn(in4);
        inGameUI = new InGameUI(new Scanner(System.in));
        inGameUI.endOfGame(board, true, moves, true);
        BDDMockito.given(VictoryValidator.getWinner()).willReturn(false);
        System.setIn(in5);
        inGameUI = new InGameUI(new Scanner(System.in));
        inGameUI.endOfGame(board, false, moves, true);
        System.setIn(in6);
        inGameUI = new InGameUI(new Scanner(System.in));
        inGameUI.endOfGame(board, true, moves, true);
        //Then
        //CleanUp
        System.setIn(System.in);
    }

}
package checkers;

import checkers.gameplay.RulesSet;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.File;

public class MenuTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("Menu tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("Menu tests: finished");
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
    public void testPrintingRulesSets() {
        //Given
        ByteArrayInputStream in;
        Menu menu = new Menu();
        RulesSet rulesSet1 = new RulesSet(true, true,true,
        true, true,true , "Sample rules set",
        "Sample description");
        RulesSet rulesSet2 = new RulesSet(true, false,true,
                true, true,true,
                ".","Sample description description long for two lines of text.");
        RulesSet rulesSet3 = new RulesSet(true, true,false,
                true, true,true,
                "Sample rules set", ".");
        RulesSet rulesSet4 = new RulesSet(true, true,true,
                false, true,true,
                "Sample rules set","Sample description long for more than three lines of text" +
                "..................................................................................................." +
                "...................................................................................................");
        RulesSet rulesSet5 = new RulesSet(true, true,true,
                true, false,true,
                "Sample rules set","Sample description long forthree lines of text" +
                "...................................................................................................");
        RulesSet rulesSet6 = new RulesSet(true, true,true,
                true, true,false,
                "Sample rules set name longer than forty six characters","Sample description");
        //When
        menu.printRulesSet(rulesSet1);
        menu.printRulesSet(rulesSet2);
        menu.printRulesSet(rulesSet3);
        menu.printRulesSet(rulesSet4);
        menu.printRulesSet(rulesSet5);
        menu.printRulesSet(rulesSet6);
        menu.cls();
        in = new ByteArrayInputStream("\n".getBytes());
        System.setIn(in);
        menu.waitForEnter();
        System.setIn(System.in);
        //Then
    }

    @Test
    public void testStart() throws IncorrectMoveFormat, IncorrectMoveException {
        File file = new File("games.dat");
        file.renameTo(new File("tmp.dat"));
        ByteArrayInputStream in = new ByteArrayInputStream(("s\nGame name\n1\nn\nn\n" +
                "s\nGame name\nGame name 2\n1\ny\ny\n" +
                "l\nGame name\nc\nGame name\nd\n" +
                "n\nl\n" +
                "l\nx\n" +
                "x\n").getBytes());
        System.setIn(in);
        Menu menu = new Menu();
        in = new ByteArrayInputStream(("s\nx\ns\nx\n").getBytes());
        System.setIn(in);
        menu.start();
        System.setIn(System.in);
        file = new File("games.dat");
        file.delete();
        file = new File("tmp.dat");
        file.renameTo(new File("games.dat"));
    }

    @Test
    public void testMain() throws IncorrectMoveException, IncorrectMoveFormat {
        ByteArrayInputStream in = new ByteArrayInputStream("x\n".getBytes());
        System.setIn(in);
        Checkers.main(null);
        System.setIn(System.in);
    }

}
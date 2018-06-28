package checkers;

import checkers.gameplay.Game;
import checkers.gameplay.RulesSet;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

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
    public void testPrintingRulesSets()
            throws IOException, ClassNotFoundException, IncorrectMoveException, IncorrectMoveFormat {
        //Given
        ByteArrayInputStream in = new ByteArrayInputStream("r\n\nx\nx\n".getBytes());
        System.setIn(in);
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
        menu.getRules().add(rulesSet1);
        menu.getRules().add(rulesSet2);
        menu.getRules().add(rulesSet3);
        menu.getRules().add(rulesSet4);
        menu.getRules().add(rulesSet5);
        menu.getRules().add(rulesSet6);
        menu.start();
        menu.cls();
        System.setIn(System.in);
        //Then
    }

    @Test
    public void testStart() throws IncorrectMoveFormat, IncorrectMoveException, IOException, ClassNotFoundException {
        File file = new File("games.dat");
        file.renameTo(new File("tmp.dat"));
        ByteArrayInputStream in = new ByteArrayInputStream(("s\nGame name\n1\nn\nn\n" +
                "s\ns\nGame name\nGame name 2\n1\ny\ny\n" +
                "s\nl\nGame name\nc\nGame name\nd\n" +
                "x\nx\nx\n").getBytes());
        System.setIn(in);
        Menu menu = new Menu();
        menu.start();
        System.setIn(System.in);
        in = new ByteArrayInputStream(("l\nGame name 2\nl\np\nx\nx\nx\n").getBytes());
        System.setIn(in);
        menu = new Menu();
        menu.start();
        System.setIn(System.in);
        file = new File("games.dat");
        file.delete();
        file = new File("tmp.dat");
        file.renameTo(new File("games.dat"));
    }

    @Test
    public void testStartNoDataFiles()
            throws IncorrectMoveFormat, IncorrectMoveException, IOException, ClassNotFoundException {
        File file1 = new File("games.dat");
        file1.renameTo(new File("tmp1.dat"));
        File file2 = new File("rules.dat");
        file2.renameTo(new File("tmp2.dat"));
        ByteArrayInputStream in = new ByteArrayInputStream("x\n".getBytes());
        System.setIn(in);
        Menu menu = new Menu();
        in = new ByteArrayInputStream(("s\nx\ns\nx\n").getBytes());
        System.setIn(in);
        menu.start();
        System.setIn(System.in);
        file1 = new File("games.dat");
        file1.delete();
        file1 = new File("tmp1.dat");
        file1.renameTo(new File("games.dat"));
        file2 = new File("rules.dat");
        file2.delete();
        file2 = new File("tmp2.dat");
        file2.renameTo(new File("rules.dat"));
    }

    @Test
    public void testStartWithSavedGame()
            throws IncorrectMoveFormat, IncorrectMoveException, IOException, ClassNotFoundException {
        File file = new File("games.dat");
        file.renameTo(new File("tmp.dat"));
        ByteArrayInputStream in = new ByteArrayInputStream("x\n".getBytes());
        System.setIn(in);
        Menu menu = new Menu();
        RulesSet rulesSet = new RulesSet(true, true, true,
                true, true, true, "",
                "");
        menu.getGames().put("test", new Game("test", rulesSet, false, false));
        menu.start();
        in = new ByteArrayInputStream("l\ntest\nl\np\ns\nx\nx\n".getBytes());
        System.setIn(in);
        menu = new Menu();
        menu.start();
        System.setIn(System.in);
        file = new File("games.dat");
        file.delete();
        file = new File("tmp.dat");
        file.renameTo(new File("games.dat"));
    }

    @Test
    public void testMain() throws IncorrectMoveException, IncorrectMoveFormat, IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream("x\n".getBytes());
        System.setIn(in);
        Checkers.main(null);
        System.setIn(System.in);
    }

}
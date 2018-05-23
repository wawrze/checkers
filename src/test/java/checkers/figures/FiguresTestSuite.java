package checkers.figures;

import org.junit.*;

public class FiguresTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("BoardRow tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("BoardRow tests: finished");
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
    public void testNoneToString(){
        //Given
        Figure none = new None();
        String s;
        //When
        s = "" + none;
        //Then
        Assert.assertEquals("  ",s);
    }

    @Test
    public void testPawnToString(){
        //Given
        Figure pawn1 = new Pawn(true);
        Figure pawn2 = new Pawn(false);
        String s1,s2;
        //When
        s1 = "" + pawn1;
        s2 = "" + pawn2;
        //Then
        Assert.assertEquals("PP",s1);
        Assert.assertEquals("pp",s2);
    }

    @Test
    public void testQueenToString(){
        //Given
        Figure queen1 = new Queen(true);
        Figure queen2 = new Queen(false);
        String s1,s2;
        //When
        s1 = "" + queen1;
        s2 = "" + queen2;
        //Then
        Assert.assertEquals("QQ",s1);
        Assert.assertEquals("qq",s2);
    }

}

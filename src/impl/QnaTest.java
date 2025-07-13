import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QnaTest {

    @Test
    public void testStringTooLong() {
        // GIVEN
        String s = "";
        for (int i = 0; i <= Qna.MAX_CHARS; i++) { //TODO: getter for the constant 
            s = s + "x";
        }
        // WHEN
        boolean result = Qna.stringLengthOk(s, "Question");

        // THEN --> expected state after execution
        Assert.assertFalse(result);
    }

    @Test
    public void testStringLengthOk() {
        // GIVEN
        String s = "";
        for (int i = 0; i < Qna.MAX_CHARS; i++) {
            s = s + "x";
        }
        // WHEN
        boolean result = Qna.stringLengthOk(s, "Question");

        // THEN --> expected state after execution
        Assert.assertTrue(result);
    }

    @Test
    public void testExistingQuestion() {
        // GIVEN
        String message = "Does this work?";
        Map<String, List<String>> map = new HashMap<>();
        map.put(message, new ArrayList<>(Arrays.asList("a", "b", "c", "d")));
        Qna.setQnaStorage(map);

        // WHEN
        boolean answersFound = Qna.provideAnswers(message);

        // THEN --> expected state after execution
        Assert.assertTrue(answersFound);
    }

    @Test
    public void testMissingQuestion() {
        // GIVEN
        String message = "Does this also work";
        Map<String, List<String>> map = new HashMap<>();
        map.put(message + " with some suffix?", new ArrayList<String>(Arrays.asList("a", "b")));
        Qna.setQnaStorage(map);

        // WHEN
        boolean answersFound = Qna.provideAnswers(message);

        // THEN --> expected state after execution
        Assert.assertFalse(answersFound);
    }

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        // Redirect System.out to a ByteArrayOutputStream
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void restoreSystemOut() {
        // Restore the original System.out after each test
        System.setOut(originalOut);
    }

    @Test
    public void testPrintlnOutputMissingQuestion() {
        // GIVEN
        String message = "Does this work";
        Map<String, List<String>> map = new HashMap<>();
        map.put(message + " with some suffix?", new ArrayList<String>(Arrays.asList("a", "b")));
        Qna.setQnaStorage(map);
        
        // WHEN
        Qna.provideAnswers(message);

        // // Capture and verify the output
        String expectedOutput = "\t" + '■' + " the answer to life, universe and everything is 42" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testPrintAnswers() {
        // GIVEN
        List<String> answers = Arrays.asList("1", "2");

        // WHEN
        Qna.printAnswers(answers);

        // // Capture and verify the output
        String expectedOutput = "\t" + '■' + " 1" + System.lineSeparator() + "\t" + '■' + " 2" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testprocessMessageSaveAnswers() {
        // GIVEN
        String message = "Question ? \"answer 1\" \"answer 2\"";

        // WHEN
        assertEquals(Qna.getQnaStorage().size(), 0);
        Qna.processInput(message);

        // // Capture and verify the output
        assertEquals(Qna.getQnaStorage().size(), 1);
    }
}

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

    Qna qna;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        qna = new Qna(); // fresh instance for each test
        System.setOut(new PrintStream(outputStream)); // Redirect System.out to a ByteArrayOutputStream
    }

    @After
    public void restoreSystemOut() {
        System.setOut(originalOut); // Restore the original System.out after each test
    }

    @Test
    public void testPrintlnOutputMissingQuestion() {
        // GIVEN
        String message = "Does this work";
        Map<String, List<String>> map = new HashMap<>();
        map.put(message + " with some suffix?", new ArrayList<String>(Arrays.asList("a", "b")));
        qna.setQnaStorage(map);

        // WHEN
        qna.provideAnswers(message);

        // // Capture and verify the output
        String expectedOutput = "\t" + '■' + " the answer to life, universe and everything is 42"
                + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testStringTooLong() {
        // GIVEN
        String s = "";
        for (int i = 0; i <= qna.getMaxChars(); i++) {
            s = s + "x";
        }
        // WHEN
        boolean result = qna.stringLengthOk(s, "Question");

        // THEN --> expected state after execution
        Assert.assertFalse(result);
    }

    @Test
    public void testStringLengthOk() {
        // GIVEN
        String s = "";
        for (int i = 0; i < qna.getMaxChars(); i++) {
            s = s + "x";
        }

        // WHEN
        boolean result = qna.stringLengthOk(s, "Question");

        // THEN --> expected state after execution
        Assert.assertTrue(result);
    }

    @Test
    public void testExistingQuestion() {
        // GIVEN
        String message = "Does this work?";
        Map<String, List<String>> map = new HashMap<>();
        map.put(message, new ArrayList<>(Arrays.asList("a", "b", "c", "d")));
        qna.setQnaStorage(map);

        // WHEN
        boolean answersFound = qna.provideAnswers(message);

        // THEN --> expected state after execution
        Assert.assertTrue(answersFound);
    }

    @Test
    public void testMissingQuestion() {
        // GIVEN
        String message = "Does this also work";
        Map<String, List<String>> map = new HashMap<>();
        map.put(message + " with some suffix?", new ArrayList<String>(Arrays.asList("a", "b")));
        qna.setQnaStorage(map);

        // WHEN
        boolean answersFound = qna.provideAnswers(message);

        // THEN --> expected state after execution
        Assert.assertFalse(answersFound);
    }

    @Test
    public void testPrintAnswers() {
        // GIVEN
        List<String> answers = Arrays.asList("1", "2");

        // WHEN
        qna.printAnswers(answers);

        // // Capture and verify the output
        String expectedOutput = "\t" + '■' + " 1" + System.lineSeparator() + "\t" + '■' + " 2" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testProcessMessageSaveAnswers() {
        // GIVEN
        String message = "Question ? \"answer 1\" \"answer 2\"";

        // WHEN
        int sizeBefore = qna.getQnaStorage().size();
        qna.processInput(message);

        // // Capture and verify the output
        assertEquals(qna.getQnaStorage().size(), sizeBefore + 1);
    }

    @Test
    public void testInputSyntaxCorrectProblem() {
        // GIVEN
        String message = "Question ? \"answer 1\"\" \"answer 2\"";

        // WHEN
        boolean inputOk = Main.inputSyntaxCorrect(message);

        // // Capture and verify the output
        assertEquals(inputOk, false);
    }

    @Test
    public void testInputSyntaxCorrectOk() {
        // GIVEN
        String message = "Question ? \"answer 1\" \"answer 2\"";

        // WHEN
        boolean inputOk = Main.inputSyntaxCorrect(message);

        // // Capture and verify the output
        assertEquals(inputOk, true);
    }
}

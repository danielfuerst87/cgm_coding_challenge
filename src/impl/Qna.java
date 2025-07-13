
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Qna {
    public static final int MAX_CHARS = 255;
    private static Map<String, List<String>> qnaStorage = new HashMap<>();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\nPlease ask a question or provide answers to a question.");
            String message = "";
            try {
                message = readInputFromSystemIn();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (inputSyntaxCorrect(message)) {
                    processInput(message);
                } else {
                    // System.out.println("\nPlease check input syntax!");
                    throw new ArrayIndexOutOfBoundsException("Odd number of \" detected!");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                System.out.println("\nPlease check input syntax!");

            }
        }
    }

    /**
     * Sets the private map container from outside Qna the class
     * 
     * @param map container that is assigned to the private map inside the Qna class
     */
    public static void setQnaStorage(Map<String, List<String>> map) {
        qnaStorage = map;
    }

    /**
     * Gets the value of the private map container from outside the Qna class
     * 
     * @return The value of the private map inside the Qna class
     */
    public static Map<String, List<String>> getQnaStorage() {
        return qnaStorage;
    }

    /**
     * Read input from System.In
     * 
     * @return String that contains the data entered by the user
     * @throws IOException - if the given data could not be handeled
     */
    public static String readInputFromSystemIn() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    /***
     * Interpretes the given message and delegates to the respective method for the
     * usecase
     * 
     * @param message - the given user input data. In dependency of the content the
     *                processed use case will be determined.
     */
    public static void processInput(String message) {
        message = message.strip();
        if (message.endsWith("?")) {
            provideAnswers(message);
        } else {
            saveAnswersForQuestion(message);
        }
    }

    /**
     * Reads the answers values out of the map with "question" as key and prints
     * them formatted. If no value was found for a key a default information will be
     * returned.
     * 
     * @param question - the key to get the answers from the map.
     * @return - "true" if answers where found for the given question
     *         - "false" otherwise
     */
    public static boolean provideAnswers(String question) {
        List<String> answers = qnaStorage.get(question);
        if (answers == null) {
            printAnswers(Arrays.asList("the answer to life, universe and everything is 42"));
            return false;
        } else {
            printAnswers(answers);
            return true;
        }
    }

    /****
     * Formats and prints the answers
     * 
     * @param answers - List of answer strings that need to be printed in a formated
     *                way
     */
    public static void printAnswers(List<String> answers) {
        for (String answer : answers) {
            System.out.println("\t" + '■' + " " + answer);
        }
    }

    /**
     * Saves a question with its anwers to the map container. Questions and answers
     * will be separated and checked for the defined length limit. If any part of
     * the message exceeds the length limit, the user will be informed and the
     * message will be discarded.
     *
     * @param message - the user input data string containing a question and one or
     *                more answers.
     */
    public static void saveAnswersForQuestion(String message) {
        String[] splitMessage = message.split("\\? ");
        String question = splitMessage[0] + "?";
        if (!stringLengthOk(question, "Question")) {
            return;
        }
        String answers = splitMessage[1];

        String[] splitAnswers = answers.split("\" ");

        for (int i = 0; i < splitAnswers.length; i++) {
            splitAnswers[i] = splitAnswers[i].replace("\"", "");
            if (!stringLengthOk(splitAnswers[i], "Answer " + (i + 1))) {
                return;
            }
        }
        qnaStorage.put(question, new ArrayList<String>(Arrays.asList(splitAnswers)));
    }

    /**
     * Checks if the given string exceeds the defined length limit
     * 
     * @param string - the given string to check
     * @param name   - identifier to improve user feedback
     * @return - "true" if given string length is below or equal MAX_CHARS
     *         - "false" otherwise
     */
    public static boolean stringLengthOk(String string, String name) {
        if (string.length() > MAX_CHARS) {
            System.out.println(
                    name + " exceeded " + MAX_CHARS + " chars, please shorten it and try again.");
            return false;
        }
        return true;
    }

    public static boolean inputSyntaxCorrect(String message) {
        long count = message.chars().filter(ch -> ch == '"').count();
        if (count % 2 != 0) {
            return false;
        }
        return true;
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        Qna qna = new Qna();

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
                    qna.processInput(message);
                } else {
                    throw new ArrayIndexOutOfBoundsException("Odd number of \" detected!");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                System.out.println("\nPlease check input syntax!");

            }
        }
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

    /**
     * Checks if the input message is valid -> if the number of " is even
     * 
     * @param message The string to check
     * @return - "true" if message is valid
     *         - "false" otherwise
     */
    public static boolean inputSyntaxCorrect(String message) {
        long count = message.chars().filter(ch -> ch == '"').count();
        if (count % 2 != 0) {
            return false;
        }
        return true;
    }

}

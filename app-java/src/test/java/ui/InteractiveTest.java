package ui;

import models.WordHint;
import models.Difficulty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InteractiveTest {
    private Interactive interactive = Interactive.withoutScanner();
    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }
    @Test
    void testGetInput() {
        Scanner scanner = new Scanner("Lop aTa");
        Interactive interactiveWithScanner = Interactive.withScanner(scanner);

        assertEquals("lop ata", interactiveWithScanner.getInput());
    }

    @Test
    void testRemoveCaseSensitivity() {
        assertEquals("qqqq qqqq", interactive.removeCaseSensitivity("qQqQ qQqQ"));
    }

    @Test
    void testShowResult() {
        interactive.showResult("result");
        assertEquals("result\n", output.toString());
    }

    @Test
    void testShowMessageWithoutArgument() {
        interactive.showMessage("message");
        assertEquals("message\n", output.toString());
    }

    @Test
    void testShowMessageWithArgument() {
        interactive.showMessage(Difficulty.EASY);
        assertEquals("EASY\n", output.toString());
    }

    @Test
    void showGameOver_ShouldPrintCongratulations_IfWordGuessed() {
        interactive.showGameOver(true, new WordHint(""));
        assertEquals("Слово угадано!\n", output.toString());
    }

    @Test
    void showGameOver_ShouldPrintAnswer_IfWordNotGuessed() {
        interactive.showGameOver(false, new WordHint("bugaga"));
        assertEquals("Эх, анлак, братишка. Слово было: bugaga\n", output.toString());
    }

    @Test
    void testGetAnAttempt() {
        Scanner scanner = new Scanner("тест\n \nб");
        Interactive interactiveWithScanner = Interactive.withScanner(scanner);
        assertEquals("б", interactiveWithScanner.getAnAttempt());
    }

    @AfterEach
    void cleanUpStreams() {
        System.setOut(null);
    }
}

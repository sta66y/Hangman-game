package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NonInteractiveUITest {
    NonInteractiveUI nonInteractiveUI = new NonInteractiveUI("naruto");
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @Test
    void testRemoveCaseSensitivity() {
        assertEquals("result", nonInteractiveUI.removeCaseSensitivity("result"));
    }

    @Test
    void testGetInput() {
        assertEquals("naruto", nonInteractiveUI.getInput());
    }

    @Test
    void testShowResult() {
        nonInteractiveUI.showResult("result");
        assertEquals("result;", output.toString());
    }

    @Test
    void showGameOver_ShouldReturnPOS_IfWordGuessed() {
        nonInteractiveUI.showGameOver(true, "sasuke");
        assertEquals("POS", output.toString());
    }

    @Test
    void showGameOver_ShouldReturnNEG_IfWordUnGuessed() {
        nonInteractiveUI.showGameOver(false, "sasuke");
        assertEquals("NEG", output.toString());
    }

    @Test
    void testGetAnAttempt() {
        assertEquals("naruto", nonInteractiveUI.getAnAttempt());
    }

    @AfterEach
    void cleanUpStreams() {
        System.setOut(null);
    }
}

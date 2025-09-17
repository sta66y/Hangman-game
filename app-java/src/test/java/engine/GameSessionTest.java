package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GameUI;
import ui.Interactive;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameSessionTest {
    private GameUI mockUI;

    @BeforeEach
    void setup() {
        mockUI = mock(GameUI.class);
    }

    @Test
    void constructor_shouldThrowException_IfWordIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            new GameSession(null, mockUI));
        assertEquals("Слова не обнаружено", ex.getMessage());
    }

    @Test
    void constructor_shouldThrowException_IfUIIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            new GameSession("word", null));
        assertEquals("Интерфейс не обнаружен", ex.getMessage());
    }

    @Test
    void start_interactiveGame_guessesCorrectly() {
        mockUI = mock(Interactive.class);
        when(mockUI.getAnAttempt())
            .thenReturn("h")
            .thenReturn("e")
            .thenReturn("l")
            .thenReturn("p");

        GameSession session = new GameSession("help", mockUI, 4);
        session.start();

        verify(mockUI, atLeastOnce()).showMessage(anyString());
        verify(mockUI, times(4)).showResult(anyString());
        verify(mockUI).showGameOver(true, "help");
    }

    @Test
    void start_interactiveGame_guessesIncorrectly() {
        mockUI = mock(Interactive.class);
        when(mockUI.getAnAttempt())
            .thenReturn("n")
            .thenReturn("i")
            .thenReturn("g")
            .thenReturn("r");

        GameSession session = new GameSession("help", mockUI, 4);
        session.start();

        verify(mockUI, atLeastOnce()).showMessage(anyString());
        verify(mockUI, times(4)).showResult(anyString());
        verify(mockUI).showGameOver(false, "help");
    }

    @Test
    void start_nonInteractiveGame_correctGuess() {
        when(mockUI.getAnAttempt()).thenReturn("test");

        GameSession session = new GameSession("test", mockUI);
        session.start();

        verify(mockUI).showResult("test");
        verify(mockUI).showGameOver(true, "test");
    }

    @Test
    void start_nonInteractiveGame_incorrectGuess() {
        when(mockUI.getAnAttempt()).thenReturn("fail");

        GameSession session = new GameSession("test", mockUI);
        session.start();

        verify(mockUI).showResult("****");
        verify(mockUI).showGameOver(false, "test");
    }

    @Test
    void start_nonInteractiveGame_wrongLength_throws() {
        when(mockUI.getAnAttempt()).thenReturn("too long");

        GameSession session = new GameSession("word", mockUI);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, session::start);
        assertEquals("Длина загаданного слова не эквивалентна длине попытки", ex.getMessage());
    }

}

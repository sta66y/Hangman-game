package engine;

import models.WordHint;
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
    void constructor_shouldThrowException_IfWordIsReserved() {
        assertThrows(IllegalArgumentException.class, () -> new GameSession(new WordHint("подсказка"), mockUI));
    }

    @Test
    void constructor_shouldThrowException_IfUIIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            new GameSession(new WordHint("word"), null));
        assertEquals("Интерфейс не обнаружен", ex.getMessage());
    }

    @Test
    void start_interactiveGame_guessesCorrectly() {
        WordHint word = new WordHint("help");
        mockUI = mock(Interactive.class);
        when(mockUI.getAnAttempt())
            .thenReturn("h")
            .thenReturn("e")
            .thenReturn("l")
            .thenReturn("p");

        GameSession session = new GameSession(word, mockUI, 4);
        session.start();

        verify(mockUI, atLeastOnce()).showMessage(anyString());
        verify(mockUI, times(4)).showResult(anyString());
        verify(mockUI).showGameOver(true, word);
    }

    @Test
    void start_interactiveGame_guessesIncorrectly() {
        WordHint word = new WordHint("help");
        mockUI = mock(Interactive.class);
        when(mockUI.getAnAttempt())
            .thenReturn("n")
            .thenReturn("i")
            .thenReturn("g")
            .thenReturn("r");

        GameSession session = new GameSession(word, mockUI, 4);
        session.start();

        verify(mockUI, atLeastOnce()).showMessage(anyString());
        verify(mockUI, times(4)).showResult(anyString());
        verify(mockUI).showGameOver(false, word);
    }

    @Test
    void start_interactiveGame_givesRightHint() {
        WordHint word = new WordHint("help", "тебе не нужна помощь");
        mockUI = mock(Interactive.class);
        when(mockUI.getAnAttempt())
            .thenReturn("подсказка")
            .thenReturn("h")
            .thenReturn("e")
            .thenReturn("l")
            .thenReturn("p");

        GameSession session = new GameSession(word, mockUI, 4);
        session.start();

        verify(mockUI).showMessage("тебе не нужна помощь");
        verify(mockUI, times(4)).showResult(anyString());
        verify(mockUI).showGameOver(true, word);
    }

    @Test
    void start_nonInteractiveGame_correctGuess() {
        WordHint word = new WordHint("test");
        when(mockUI.getAnAttempt()).thenReturn("test");

        GameSession session = new GameSession(word, mockUI);
        session.start();

        verify(mockUI).showResult("test");
        verify(mockUI).showGameOver(true, word);
    }

    @Test
    void start_nonInteractiveGame_incorrectGuess() {
        WordHint word = new WordHint("help");
        when(mockUI.getAnAttempt()).thenReturn("fail");

        GameSession session = new GameSession(word, mockUI);
        session.start();

        verify(mockUI).showResult("****");
        verify(mockUI).showGameOver(false, word);
    }

    @Test
    void start_nonInteractiveGame_wrongLength_throws() {
        when(mockUI.getAnAttempt()).thenReturn("too long");

        GameSession session = new GameSession(new WordHint("test"), mockUI);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, session::start);
        assertEquals("Длина загаданного слова не эквивалентна длине попытки", ex.getMessage());
    }

}

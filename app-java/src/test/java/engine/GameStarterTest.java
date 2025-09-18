package engine;

import model.Category;
import model.Difficulty;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import ui.GameUI;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameStarterTest {
    private Random mockRandom = mock(Random.class);
    private GameUI mockUI = mock(GameUI.class);
    private Dictionary mockDictionary = mock(Dictionary.class);

    private GameStarter gameStarter = new GameStarter(mockUI, mockRandom, mockDictionary);

    @Test
    void constructor_ShouldThrowException_IfGameUIIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            new GameStarter(null));
        assertEquals("GameUI не может быть null", ex.getMessage());
    }

    @Test
    void start_withValidEnumInputs_createsGameSessionWithChosenValues() {
        WordHint wordHint = new WordHint("dog");
        when(mockUI.getInput())
            .thenReturn("easy")
            .thenReturn("animals");

        when(mockDictionary.getRandomWord(Category.ANIMALS, Difficulty.EASY)).thenReturn(wordHint);

        AtomicReference<Object[]> ctorArgs = new AtomicReference<>();

        try (MockedConstruction<GameSession> mocked = mockConstruction(GameSession.class,
            (mock, context) -> {
                ctorArgs.set(context.arguments().toArray());
            })) {

            gameStarter.start();

            assertEquals(1, mocked.constructed().size());

            Object[] args = ctorArgs.get();
            assertNotNull(args);

            assertEquals(wordHint, args[0]);

            assertSame(mockUI, args[1]);
        }
    }

    @Test
    void start_whenDifficultyEmpty_usesRandomChoice() {
        WordHint wordHint = new WordHint("monkey");
        when(mockUI.getInput())
            .thenReturn("")
            .thenReturn("animals");

        when(mockRandom.nextInt(anyInt())).thenReturn(1);

        when(mockDictionary.getRandomWord(Category.ANIMALS, Difficulty.MEDIUM)).thenReturn(wordHint);

        AtomicReference<Object[]> ctorArgs = new AtomicReference<>();

        try (MockedConstruction<GameSession> mocked = mockConstruction(GameSession.class,
            (mock, context) -> {
                ctorArgs.set(context.arguments().toArray());
            })) {

            gameStarter.start();

            verify(mockRandom, atLeastOnce()).nextInt(anyInt());
            verify(mockDictionary).getRandomWord(Category.ANIMALS, Difficulty.MEDIUM);

            assertEquals(1, mocked.constructed().size());
            verify(mocked.constructed().get(0)).start();

            Object[] args = ctorArgs.get();
            assertEquals(wordHint, args[0]);
            assertSame(mockUI, args[1]);
        }
    }

    @Test
    void start_withInvalidThenValidInputs_loopsUntilValid() {
        WordHint wordHint = new WordHint("dog");
        when(mockUI.getInput())
            .thenReturn("incorrect input")
            .thenReturn("easy")
            .thenReturn("incorrect input")
            .thenReturn("animals");

        when(mockDictionary.getRandomWord(Category.ANIMALS, Difficulty.EASY)).thenReturn(wordHint);

        AtomicReference<Object[]> ctorArgs = new AtomicReference<>();

        try (MockedConstruction<GameSession> mocked = mockConstruction(GameSession.class,
            (mock, context) -> {
                ctorArgs.set(context.arguments().toArray());
            })) {

            gameStarter.start();

            assertEquals(1, mocked.constructed().size());

            Object[] args = ctorArgs.get();
            assertNotNull(args);

            assertEquals(wordHint, args[0]);

            assertSame(mockUI, args[1]);
        }

        verify(mockUI, atLeast(2)).showMessage(contains("Некорректный ввод"));
    }

}

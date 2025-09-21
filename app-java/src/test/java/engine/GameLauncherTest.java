package engine;

import models.WordHint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import ui.GameUI;
import ui.Interactive;
import ui.NonInteractiveUI;

import java.util.concurrent.atomic.AtomicReference;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameLauncherTest {

    private GameLauncher gameLauncher;

    @BeforeEach
    void setup() {
        gameLauncher = new GameLauncher();
    }

    @Test
    void start_withTwoArguments_startsNonInteractiveMode() {
        AtomicReference<Object[]> ctorArgs = new AtomicReference<>();

        try (MockedConstruction<GameSession> mockedSession = mockConstruction(GameSession.class,
            (mock, context) -> ctorArgs.set(context.arguments().toArray()))) {

            gameLauncher.start(new String[]{"word", "guess"});

            assertEquals(1, mockedSession.constructed().size());
            verify(mockedSession.constructed().get(0)).start();

            Object[] argsUsed = ctorArgs.get();
            assertEquals("word", ((WordHint) argsUsed[0]).getWord());

            assertTrue(argsUsed[1] instanceof NonInteractiveUI);
        }
    }

    @Test
    void start_withZeroArguments_startsInteractiveMode() {
        Scanner scannerMock = mock(Scanner.class);
        when(scannerMock.nextLine()).thenReturn("не д");

        try (MockedConstruction<Interactive> mockedInteractive = mockConstruction(Interactive.class,
            (mock, context) -> when(mock.getInput(anyString())).thenReturn("не д"));
             MockedConstruction<GameStarter> mockedStarter = mockConstruction(GameStarter.class,
                 (mock, context) -> {})) {

            gameLauncher.start(new String[]{});

            assertFalse(mockedInteractive.constructed().isEmpty());
            assertFalse(mockedStarter.constructed().isEmpty());
            verify(mockedStarter.constructed().get(0)).start();
        }
    }

    @Test
    void start_withInvalidNumberOfArguments_throwsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> gameLauncher.start(new String[]{"qwerty"}));
        assertEquals("Количество аргументов должно быть 0 или 2", ex.getMessage());
    }

}

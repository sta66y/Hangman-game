package ui;

import engine.WordHint;

public interface GameUI {
    String getInput();
    void showResult(String result);
    void showMessage(String message);
    void showGameOver(boolean isWordGuess, WordHint answer);
    String removeCaseSensitivity(String input);
    void showMessage(Object o);
    String getAnAttempt();
}

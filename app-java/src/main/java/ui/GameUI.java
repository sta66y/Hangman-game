package ui;

import models.WordHint;

public interface GameUI {
    String getInput();
    String getInput(String message);
    void showResult(String result);
    void showMessage(String message);
    void showGameOver(boolean isWordGuess, WordHint answer);
    String removeCaseSensitivity(String input);
    void showMessage(Object o);
    String getAnAttempt();
}

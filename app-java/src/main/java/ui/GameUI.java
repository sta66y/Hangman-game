package ui;

public interface GameUI {
    String getInput();
    void showResult(String result);
    void showMessage(String message);
    void showGameOver(boolean isWordGuess, String answer);
    String removeCaseSensitivity(String input);
    void showMessage(Object o);
}

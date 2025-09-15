package engine;

import config.AppConfig;
import ui.GameUI;
import ui.Interactive;
import java.util.HashSet;
import java.util.Set;

public class GameSession {
    private final String word;
    private final Set<Character> letters = new HashSet<>();
    private final int maxAttempts = AppConfig.MAX_ATTEMPTS;
    private int attemptsUsed = 0;
    private final Set<Character> usedLetters = new HashSet<>();
    private final Set<Character> guessedLetters = new HashSet<>();
    private final GameUI gameUI;
    private boolean isWordGuessed = false;
    private final boolean isInteractive;
    private final HangmanRenderer hangmanRenderer = new HangmanRenderer();

    public GameSession(String word, GameUI gameUI) {
        if (word == null) throw new IllegalArgumentException("Слова не обнаружено");
        if (gameUI == null) throw new IllegalArgumentException("Интерфейс не обнаружен");

        this.word = word.toLowerCase();
        this.gameUI = gameUI;

        for (char ch : word.toCharArray()) {
            letters.add(ch);
        }

        isInteractive = gameUI instanceof Interactive;
    }

    public void start() {
        showInitialInfo();
        renderHangman();

        while (!isGameOver()) {
            String input = gameUI.getInput();
            String result = isInteractive ? attemptInteractive(input) : attemptNonInteractive(input);
            gameUI.showResult(result);
        }
        gameUI.showGameOver(isWordGuessed, word);
    }

    private void showInitialInfo() {
        gameUI.showMessage("Максимальное количество попыток: " + maxAttempts);
        if (isInteractive) gameUI.showMessage("Количество букв в загаданном слове: " + word.length());
    }



    private String attemptInteractive(String input) {
        if (input.length() != 1) throw new IllegalArgumentException("Некорректный ввод");
        char letter = input.charAt(0);

        if (guessedLetters.contains(letter) || usedLetters.contains(letter)) return getResultForInteractive();

        if (letters.contains(letter)) {
            guessedLetters.add(letter);
            if (guessedLetters.size() == letters.size()) isWordGuessed = true;
        } else {
            usedLetters.add(letter);
            attemptsUsed++;
            renderHangman();
            gameUI.showMessage("Осталось " + (maxAttempts - attemptsUsed) + " попыток");
        }

        return getResultForInteractive();
    }

    private String getResultForInteractive() {
        StringBuilder sb = new StringBuilder();
        for (char ch : word.toCharArray()) {
            sb.append(guessedLetters.contains(ch) ? ch : "-");
        }
        return sb.toString();
    }




    private String attemptNonInteractive(String input) {
        if (input.length() != word.length()) throw new IllegalArgumentException(
            "Длина загаданного слова не эквивалентна длине попытки");

        attemptsUsed = maxAttempts;
        return getResultForNonInteractive(input);
    }

    private String getResultForNonInteractive(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            sb.append(input.charAt(i) == word.charAt(i) ? word.charAt(i) : '*');
        }

        isWordGuessed = word.equals(input);
        return sb.toString();
    }

    private boolean isGameOver() {
        return attemptsUsed >= maxAttempts || isWordGuessed;
    }

    private void renderHangman() {
        gameUI.showMessage(hangmanRenderer.render(attemptsUsed, maxAttempts));
    }

}

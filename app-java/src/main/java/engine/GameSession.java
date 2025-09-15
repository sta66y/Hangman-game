package engine;

import config.AppConfig;
import ui.GameUI;
import ui.Interactive;
import java.util.HashSet;
import java.util.Set;

public class GameSession {
    private final String word;
    private final Set<Character> letters;
    private final int maxAttempts = AppConfig.MAX_ATTEMPTS;
    private int attemptsUsed = 0;
    private Set<Character> usedLetters = new HashSet<>();
    private Set<Character> guessedLetters = new HashSet<>();
    private final GameUI gameUI;
    private boolean isWordGuessed = false;
    private final boolean isInteractive;
    private final HangmanRenderer hangmanRenderer = new HangmanRenderer();

    public GameSession(String word, GameUI gameUI) {
        if (word == null) throw new IllegalArgumentException("Слова не обнаружено");
        if (gameUI == null) throw new IllegalArgumentException("Интерфейс не обнаружен");

        this.word = word.toLowerCase();
        this.gameUI = gameUI;

        letters = new HashSet<>();
        for (char ch : word.toCharArray()) {
            letters.add(ch);
        }

        isInteractive = gameUI instanceof Interactive;
    }

    public void start() {
        gameUI.showMessage("Максимальное количество попыток: " + maxAttempts);
        gameUI.showMessage("Количество букв в загаданном слове: " + word.length());
        gameUI.showMessage(hangmanRenderer.render(0, maxAttempts));
        while (!isGameOver()) {
            gameUI.showResult(attempt());
        }
        gameUI.showGameOver(isWordGuessed, word);
    }

    private String attempt() {
        String input = gameUI.getInput();
        if (isInteractive) return attemptInteractive(input);
        else return attemptNonInteractive(input);
    }

    private String attemptInteractive(String input) {
        if (input.length() != 1) throw new IllegalArgumentException("Некорректный ввод");
        char letter = input.toCharArray()[0];
        if (letters.contains(letter)) {
            guessedLetters.add(letter);
            if (guessedLetters.size() == letters.size()) isWordGuessed = true;
        } else {
            if (!usedLetters.contains(letter)) attemptsUsed++;
            usedLetters.add(letter);
            gameUI.showMessage(hangmanRenderer.render(attemptsUsed, maxAttempts));
            gameUI.showMessage("Осталось " + (maxAttempts - attemptsUsed) + " попыток(ка)(ки)");
        }

        return getResultAttemptForInteractive();
    }

    private String attemptNonInteractive(String input) {
        if (input.length() != word.length()) throw new IllegalArgumentException("Длина загаданного слова не эквивалентна длине попытки");
        attemptsUsed = maxAttempts;
        return getResultAttemptForNonInteractive(input);
    }

    private String getResultAttemptForNonInteractive(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == word.charAt(i)) sb.append(word.charAt(i));
            else sb.append('*');
        }

        isWordGuessed = word.equals(input);

        return sb.toString();
    }

    private String getResultAttemptForInteractive() {
        StringBuilder sb = new StringBuilder();
        for (char ch : word.toCharArray()) {
            if (guessedLetters.contains(ch)) sb.append(ch);
            else sb.append("-");
        }
        return sb.toString();
    }

    private boolean isGameOver() {
        return attemptsUsed >= maxAttempts || isWordGuessed;
    }
}

package engine;

import config.AppConfig;
import models.WordHint;
import ui.GameUI;
import ui.Interactive;
import java.util.HashSet;
import java.util.Set;

public class GameSession {
    private final WordHint word;
    private final int maxAttempts;
    private int attemptsUsed = 0;
    private final Set<Character> usedLetters = new HashSet<>();
    private final Set<Character> guessedLetters = new HashSet<>();
    private final GameUI gameUI;
    private boolean isWordGuessed = false;
    private final HangmanRenderer hangmanRenderer = new HangmanRenderer();

    public GameSession(WordHint word, GameUI gameUI) {
        this(word, gameUI, AppConfig.MAX_ATTEMPTS);
    }

    public GameSession(WordHint word, GameUI gameUI, int maxAttempts) {
        if (word == null) throw new IllegalArgumentException("Слова не обнаружено");
        if (word.getWord().equals("подсказка")) throw new IllegalArgumentException("эээ, это слово зарезервировано");
        if (gameUI == null) throw new IllegalArgumentException("Интерфейс не обнаружен");

        this.word = word;
        this.gameUI = gameUI;
        this.maxAttempts = maxAttempts;
    }

    public void start() {
        showInitialInfo();
        renderHangman();

        while (!isGameOver()) {
            String input = input();
            String result = (gameUI instanceof Interactive) ? attemptInteractive(input) : attemptNonInteractive(input);
            gameUI.showResult(result);
        }
        gameUI.showGameOver(isWordGuessed, word);
    }

    private void showInitialInfo() {
        gameUI.showMessage("Максимальное количество попыток: " + maxAttempts);
        gameUI.showMessage("Количество букв в загаданном слове: " + word.getLength());
        gameUI.showMessage("Если нужна подсказка, пиши - подсказка");
    }



    private String attemptInteractive(String input) {
        Set<Character> lettersInWord = getLetters();

        char letter = input.charAt(0);

        if (guessedLetters.contains(letter) || usedLetters.contains(letter)) return getResultForInteractive();

        if (lettersInWord.contains(letter)) {
            guessedLetters.add(letter);
            if (isWordGuessed()) isWordGuessed = true;
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
        for (char ch : word.getWord().toCharArray()) {
            sb.append(guessedLetters.contains(ch) ? ch : "-");
        }
        return sb.toString();
    }




    private String attemptNonInteractive(String input) {
        if (input.length() != word.getLength()) throw new IllegalArgumentException(
            "Длина загаданного слова не эквивалентна длине попытки");
        Set<Character> letters = getLetters();

        for (char ch : input.toCharArray()) {
            if (letters.contains(ch))
                guessedLetters.add(ch);
        }
        attemptsUsed = maxAttempts;
        return getResultForNonInteractive();
    }

    private String getResultForNonInteractive() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.getWord().length(); i++) {
            sb.append(guessedLetters.contains(word.getWord().charAt(i)) ? word.getWord().charAt(i) : '*');
        }

        isWordGuessed = isWordGuessed();
        return sb.toString();
    }

    private boolean isGameOver() {
        return attemptsUsed >= maxAttempts || isWordGuessed;
    }

    private void renderHangman() {
        gameUI.showMessage(hangmanRenderer.render(attemptsUsed, maxAttempts));
    }

    private String input() {
        while (true) {
            String input = gameUI.getAnAttempt();
            if (input.equals("подсказка")) {
                gameUI.showMessage(word.getHint());
            } else return input;
        }
    }


    private boolean isWordGuessed() {
        for (char ch : word.getWord().toCharArray()) {
            if (!guessedLetters.contains(ch)) {
                return false;
            }
        }
        return true;
    }

    private Set<Character> getLetters() {
        Set<Character> lettersInWord = new HashSet<>();

        for (char ch : word.getWord().toCharArray()) {
            lettersInWord.add(ch);
        }

        return lettersInWord;
    }
}

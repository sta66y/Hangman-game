package ui;

import engine.InputValidator;
import models.WordHint;
import java.util.Scanner;

public class Interactive implements GameUI {
    private final Scanner scanner;

    public Interactive(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getInput() {
        return removeCaseSensitivity(scanner.nextLine());
    }

    @Override
    public String getInput(String message) {
        showMessage(message);
        return getInput();
    }

    @Override
    public void showResult(String result) {
        System.out.println(result);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showGameOver(boolean isWordGuess, WordHint answer) {
        if (isWordGuess) {
            System.out.println("Слово угадано!");
        } else {
            System.out.println("Эх, анлак, братишка. Слово было: " + answer.getWord());
        }
    }

    @Override
    public String removeCaseSensitivity(String input) {
        return input.toLowerCase();
    }

    @Override
    public void showMessage(Object o) {
        System.out.println(o);
    }

    @Override
    public String getAnAttempt() {
        while (true) {
            String input = getInput();
            if (InputValidator.isHint(input)) {
                return "подсказка";
            }
            try {
                char letter = InputValidator.validateLetter(input);
                return String.valueOf(letter);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}

package ui;

import engine.WordHint;
import java.util.Scanner;

public class Interactive implements GameUI {
    private final Scanner scanner;

    public Interactive(){
        scanner = new Scanner(System.in);
    }

    public Interactive(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getInput() {
        return removeCaseSensitivity(scanner.nextLine());
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
            if (input.matches("[а-я]") || input.equals("подсказка")) {
                return input;
            } else {
                System.out.println("Неверный ввод! Введи ток одну русскую букву: ");
            }
        }
    }

}

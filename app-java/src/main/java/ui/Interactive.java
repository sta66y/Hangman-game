package ui;

import java.util.Scanner;

public class Interactive implements GameUI {
    Scanner scanner = new Scanner(System.in);

    @Override
    public String getInput() {
        return scanner.nextLine();
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
    public void showGameOver(boolean isWordGuess, String answer) {
        if (isWordGuess) {
            System.out.println("Слово угадано!");
        } else {
            System.out.println("Эх, анлак, братишка. Слово было: " + answer);
        }
    }

}

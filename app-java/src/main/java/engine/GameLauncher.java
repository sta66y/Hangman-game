package engine;

import models.WordHint;
import ui.GameUI;
import ui.Interactive;
import ui.NonInteractiveUI;
import java.util.Scanner;

public class GameLauncher {

    public void start(String[] args) {
        if (args.length == 2) {
            startNonInteractive(args[0], args[1]);
        } else if (args.length == 0) {
            startInteractive();
        } else {
            throw new IllegalArgumentException("Количество аргументов должно быть 0 или 2");
        }
    }

    private void startNonInteractive(String wordArg, String guessArg) {
        WordHint word = new WordHint(wordArg);
        GameUI ui = new NonInteractiveUI(guessArg);
        GameSession session = new GameSession(word, ui);
        session.start();
    }

    private void startInteractive() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            GameUI ui = new Interactive(scanner);
            GameStarter starter = GameStarter.withGameUI(ui);
            starter.start();

            if (!ui.getInput("Сыграть еще раз? д/н").equals("д")) break;
        }
    }

}

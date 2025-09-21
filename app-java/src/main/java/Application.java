import engine.GameSession;
import engine.GameStarter;
import models.WordHint;
import ui.GameUI;
import ui.Interactive;
import ui.NonInteractiveUI;

public class Application {
    public static void main(String[] args) {
        if (args.length == 2) {
            WordHint word = new WordHint(args[0]);
            GameUI nonInteractiveUI = new NonInteractiveUI(args[1]);
            GameSession gameSession = new GameSession(word, nonInteractiveUI);
            gameSession.start();
        } else if (args.length == 0) {
            GameUI interactiveUI = Interactive.withoutScanner();
            GameStarter gameStarter = GameStarter.withGameUI(interactiveUI);
            gameStarter.start();
        } else {
            throw new IllegalArgumentException("Количество аргументов должно быть 0 или 2");
        }
    }
}

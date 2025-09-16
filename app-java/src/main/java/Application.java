import engine.GameSession;
import engine.GameStarter;
import ui.GameUI;
import ui.Interactive;
import ui.NonInteractiveUI;

public class Application {
    public static void main(String[] args) {
        if (args.length == 2) {
            String word = args[0];
            GameUI nonInteractiveUI = new NonInteractiveUI(args[1]);
            GameSession gameSession = new GameSession(word, nonInteractiveUI);
            gameSession.start();
        } else if (args.length == 0) {
            GameUI interactiveUI = new Interactive();
            GameStarter gameStarter = new GameStarter(interactiveUI);
            gameStarter.start();
        } else {
            throw new IllegalArgumentException("Количество аргументов должно быть 0 или 2");
        }
    }
}

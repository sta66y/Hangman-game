import engine.GameSession;
import engine.GameStarter;
import ui.GameUI;
import ui.NonInteractiveUI;

public class Application {
    public static void main(String[] args) {
//        Неинтерактивный режим
        if (args.length == 2) {
            String word = args[0];
            GameUI nonInteractiveUI = new NonInteractiveUI(args[1]);
            GameSession gameSession = new GameSession(word, nonInteractiveUI);
            gameSession.start();
        }
//        Интерактивный режим
        else {
            GameStarter gameStarter = new GameStarter();
            gameStarter.start();
        }
    }
}

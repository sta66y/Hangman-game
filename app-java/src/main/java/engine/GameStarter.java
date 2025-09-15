package engine;

import model.Category;
import model.Difficulty;
import ui.GameUI;
import java.util.Random;

public class GameStarter {
    private final Dictionary dictionary = new Dictionary();
    private final Random random = new Random();
    private final GameUI gameUI;

    private String word;
    private Difficulty difficulty;
    private Category category;

    public GameStarter(GameUI gameUI) {
        if (gameUI == null) throw new IllegalArgumentException("GameUI не может быть null");
        this.gameUI = gameUI;
    }

    public void start() {
        difficulty = chooseEnum("Выбери сложность (для рандома enter): ", Difficulty.values());
        category = chooseEnum("Выбери категорию (для рандома enter): ", Category.values());

        word = dictionary.getRandomWord(category, difficulty);

        GameSession gameSession = new GameSession(word, gameUI);
        gameSession.start();
    }

    private <T extends Enum<T>> T chooseEnum(String message, T[] values) {
        gameUI.showMessage(message);
        for (T val : values) gameUI.showMessage(val);

        while (true) {
            String input = gameUI.getInput();
            if (input.isEmpty()) {
                T randomChoice = values[random.nextInt(values.length)];
                gameUI.showMessage("Выбран рандом: " + randomChoice);
                return randomChoice;
            }
            try {
                return Enum.valueOf(values[0].getDeclaringClass(), input.toUpperCase());
            } catch (IllegalArgumentException e) {
                gameUI.showMessage("Некорректный ввод, попробуй снова: ");
            }
        }
    }
}

package engine;

import model.Category;
import model.Difficulty;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameStarter {
    private final Dictionary dictionary;
    private String word;
    private Difficulty difficulty;
    private Category category;
    private Scanner scanner = new Scanner(System.in);
    private final Random random;

    public GameStarter() {
        dictionary = new Dictionary();
        random = new Random();
    }

    public void start() {
        chooseDifficulty();
        chooseCategory();
        getWord();
        System.out.println(word);
    }

    private void chooseDifficulty() {
        System.out.println("Выбери сложность (для рандома enter): ");

        for (Difficulty d : Difficulty.values()) {
            System.out.println(d);
        }

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Сложность выбирается рандомно...");
                difficulty = randomizeDifficulty();
                System.out.println(difficulty);
                break;
            }

            try {
                Difficulty dif = Difficulty.valueOf(input.toUpperCase());
                difficulty = dif;
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректная сложность! Попробуй снова: ");
            }
        }
    }

    private void chooseCategory() {
        System.out.println("Выбери категорию (для рандома enter): ");

        for (Category с : Category.values()) {
            System.out.println(с);
        }

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Категория выбирается рандомно...");
                category = randomizeCategory();
                System.out.println(category);
                break;
            }

            try {
                Category cat = Category.valueOf(input.toUpperCase()); // убрать после реализации ConsoleUI
                category = cat;
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректная категория! Попробуй снова: ");
            }
        }
    }

    private void getWord() {
        word = dictionary.getRandomWord(category, difficulty);
    }

    private Difficulty randomizeDifficulty() {
        List<Difficulty> difficulties = new ArrayList<>();
        for (Difficulty dif : Difficulty.values()) {
            difficulties.add(dif);
        }
        return difficulties.get(random.nextInt(difficulties.size()));
    }

    private Category randomizeCategory() {
        List<Category> categories = new ArrayList<>();
        for (Category cat : Category.values()) {
            categories.add(cat);
        }
        return categories.get(random.nextInt(categories.size()));
    }

}

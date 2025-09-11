package engine;

import model.Category;
import model.Difficulty;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Dictionary {
    private static Map<Category, Map<Difficulty, List<String>>> WORDS = new HashMap<>();
    static {
        // Фрукты
        WORDS.put(Category.FRUITS, new HashMap<>() {{
            put(Difficulty.EASY, Arrays.asList("apple", "pear", "plum", "kiwi", "mango"));
            put(Difficulty.MEDIUM, Arrays.asList("banana", "orange", "grapes", "peach", "apricot"));
            put(Difficulty.HARD, Arrays.asList("pineapple", "pomegranate", "dragonfruit"));
        }});

        // Животные
        WORDS.put(Category.ANIMALS, new HashMap<>() {{
            put(Difficulty.EASY, Arrays.asList("cat", "dog", "fox", "deer", "bear"));
            put(Difficulty.MEDIUM, Arrays.asList("tiger", "elephant", "giraffe", "zebra", "monkey"));
            put(Difficulty.HARD, Arrays.asList("rhinoceros", "crocodile", "hippopotamus"));
        }});

        // Страны
        WORDS.put(Category.COUNTRIES, new HashMap<>() {{
            put(Difficulty.EASY, Arrays.asList("cuba", "chad", "mali", "peru", "fiji"));
            put(Difficulty.MEDIUM, Arrays.asList("brazil", "france", "germany", "russia", "canada"));
            put(Difficulty.HARD, Arrays.asList("australia", "argentina", "madagascar"));
        }});
    }
    private final Map<Category, Map<Difficulty, List<String>>> words;
    private final Random random;

    public Dictionary() {
        this.random = new Random();
        this.words = WORDS;
    }

    public Dictionary(Random random) {
        this.random = random;
        this.words = WORDS;
    }

    public Dictionary(Map<Category, Map<Difficulty, List<String>>> words) {
        this.random = new Random();
        this.words = words;
    }

    public String getRandomWord(Category category, Difficulty difficulty) {
        if (category == null || difficulty == null) {
            throw new IllegalArgumentException("Не выбрана сложность или категория");
        }

        Map<Difficulty, List<String>> difficulties = words.getOrDefault(category, Collections.emptyMap());
        List<String> wordsList = difficulties.getOrDefault(difficulty, Collections.emptyList());

        int size = wordsList.size();

        if (size == 0) {
            throw new RuntimeException("Слов нет, гуляй");
        }

        return wordsList.get(random.nextInt(size));
    }

}

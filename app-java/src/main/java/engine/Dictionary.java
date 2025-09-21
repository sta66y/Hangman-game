package engine;

import models.Category;
import models.Difficulty;
import models.WordHint;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Dictionary {
    private final static Map<Category, Map<Difficulty, List<WordHint>>> DEFAULT_WORDS = new HashMap<>();
    static {
        DEFAULT_WORDS.put(Category.FRUITS, Map.of(
            Difficulty.EASY, List.of(
                new WordHint("яблоко", "Красное или зелёное, растёт на дереве"),
                new WordHint("груша", "Фрукты грушеобразной формы"),
                new WordHint("слива", "Сладкая фрукта"),
                new WordHint("киви", "Зелёный внутри, коричневый снаружи"),
                new WordHint("манго", "Тропический фрукт с желтой мякотью")
            ),
            Difficulty.MEDIUM, List.of(
                new WordHint("банан", "Жёлтый и изогнутый фрукт"),
                new WordHint("апельсин", "Цитрус, богат витамином C"),
                new WordHint("виноград", "Мелкие ягоды на лозе"),
                new WordHint("персик", "Фрукты с мягкой кожурой и косточкой"),
                new WordHint("абрикос", "Оранжевый фрукт")
            ),
            Difficulty.HARD, List.of(
                new WordHint("ананас", "Тропический фрукт с шипованной кожурой"),
                new WordHint("гранат", "Фрукт с множеством семян"),
                new WordHint("драконийфрукт", "Экзотический фрукт с яркой кожурой")
            )
        ));

        DEFAULT_WORDS.put(Category.ANIMALS, Map.of(
            Difficulty.EASY, List.of(
                new WordHint("кот", "Мурлыкающий домашний питомец"),
                new WordHint("собака", "Лает и любит гулять"),
                new WordHint("лиса", "Хитрая и рыжая"),
                new WordHint("олень", "Рога и грациозность"),
                new WordHint("медведь", "Большое лесное животное")
            ),
            Difficulty.MEDIUM, List.of(
                new WordHint("тигр", "Полосатый хищник"),
                new WordHint("слон", "Самое большое сухопутное животное"),
                new WordHint("жираф", "Длинная шея"),
                new WordHint("зебра", "Полосатое животное"),
                new WordHint("обезьяна", "Весёлое животное, любит лазать")
            ),
            Difficulty.HARD, List.of(
                new WordHint("носорог", "Животное с рогом"),
                new WordHint("крокодил", "Хищник в воде и на суше"),
                new WordHint("бегемот", "Большое животное, любит воду")
            )
        ));

        DEFAULT_WORDS.put(Category.COUNTRIES, Map.of(
            Difficulty.EASY, List.of(
                new WordHint("куба", "Карибский остров"),
                new WordHint("чад", "Африканская страна"),
                new WordHint("мали", "Страна в Западной Африке"),
                new WordHint("перу", "Мачу-Пикчу находится здесь"),
                new WordHint("фижи", "Островное государство в Тихом океане")
            ),
            Difficulty.MEDIUM, List.of(
                new WordHint("бразилия", "Самая большая страна Южной Америки"),
                new WordHint("франция", "Эйфелева башня здесь"),
                new WordHint("германия", "Октоберфест и Бавария"),
                new WordHint("россия", "Самая большая страна мира"),
                new WordHint("канада", "Ледники и кленовый лист")
            ),
            Difficulty.HARD, List.of(
                new WordHint("австралия", "Кенгуру и Сиднейский оперный театр"),
                new WordHint("аргентина", "Танго и Патагония"),
                new WordHint("мадагаскар", "Остров с лемурами")
            )
        ));

    }
    private final Map<Category, Map<Difficulty, List<WordHint>>> words;
    private final Random random;

    private Dictionary(Map<Category, Map<Difficulty, List<WordHint>>> words, Random random) {
        this.random = random;
        this.words = words;
    }

    public static Dictionary defaultDictionary() {
        return new Dictionary(DEFAULT_WORDS, new Random());
    }

    public static Dictionary withRandom(Random random) {
        return new Dictionary(DEFAULT_WORDS, random);
    }

    public static Dictionary withWords(Map<Category, Map<Difficulty, List<WordHint>>> words) {
        return new Dictionary(words, new Random());
    }

    public WordHint getRandomWord(Category category, Difficulty difficulty) {
        if (category == null || difficulty == null) {
            throw new IllegalArgumentException("Не выбрана сложность или категория");
        }

        Map<Difficulty, List<WordHint>> difficulties = words.getOrDefault(category, Collections.emptyMap());
        List<WordHint> wordsList = difficulties.getOrDefault(difficulty, Collections.emptyList());

        int size = wordsList.size();

        if (size == 0) {
            throw new RuntimeException("Слов нет, гуляй");
        }

        return wordsList.get(random.nextInt(size));
    }

}

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
    private static Map<Category, Map<Difficulty, List<WordHint>>> WORDS = new HashMap<>();
    static {
        WORDS.put(Category.FRUITS, new HashMap<>() {{
            put(Difficulty.EASY, Arrays.asList(
                new WordHint("яблоко", "Красное или зелёное, растёт на дереве"),
                new WordHint("груша", "Фрукты грушеобразной формы"),
                new WordHint("слива", "Сладкая фрукта"),
                new WordHint("киви", "Зелёный внутри, коричневый снаружи"),
                new WordHint("манго", "Тропический фрукт с желтой мякотью")
            ));
            put(Difficulty.MEDIUM, Arrays.asList(
                new WordHint("банан", "Жёлтый и изогнутый фрукт"),
                new WordHint("апельсин", "Цитрус, богат витамином C"),
                new WordHint("виноград", "Мелкие ягоды на лозе"),
                new WordHint("персик", "Фрукты с мягкой кожурой и косточкой"),
                new WordHint("абрикос", "Оранжевый фрукт")
            ));
            put(Difficulty.HARD, Arrays.asList(
                new WordHint("ананас", "Тропический фрукт с шипованной кожурой"),
                new WordHint("гранат", "Фрукт с множеством семян"),
                new WordHint("драконийфрукт", "Экзотический фрукт с яркой кожурой")
            ));
        }});

        WORDS.put(Category.ANIMALS, new HashMap<>() {{
            put(Difficulty.EASY, Arrays.asList(
                new WordHint("кот", "Мурлыкающий домашний питомец"),
                new WordHint("собака", "Лает и любит гулять"),
                new WordHint("лиса", "Хитрая и рыжая"),
                new WordHint("олень", "Рога и грациозность"),
                new WordHint("медведь", "Большое лесное животное")
            ));
            put(Difficulty.MEDIUM, Arrays.asList(
                new WordHint("тигр", "Полосатый хищник"),
                new WordHint("слон", "Самое большое сухопутное животное"),
                new WordHint("жираф", "Длинная шея"),
                new WordHint("зебра", "Полосатое животное"),
                new WordHint("обезьяна", "Весёлое животное, любит лазать")
            ));
            put(Difficulty.HARD, Arrays.asList(
                new WordHint("носорог", "Животное с рогом"),
                new WordHint("крокодил", "Хищник в воде и на суше"),
                new WordHint("бегемот", "Большое животное, любит воду")
            ));
        }});

        WORDS.put(Category.COUNTRIES, new HashMap<>() {{
            put(Difficulty.EASY, Arrays.asList(
                new WordHint("куба", "Карибский остров"),
                new WordHint("чад", "Африканская страна"),
                new WordHint("мали", "Страна в Западной Африке"),
                new WordHint("перу", "Мачу-Пикчу находится здесь"),
                new WordHint("фижи", "Островное государство в Тихом океане")
            ));
            put(Difficulty.MEDIUM, Arrays.asList(
                new WordHint("бразилия", "Самая большая страна Южной Америки"),
                new WordHint("франция", "Эйфелева башня здесь"),
                new WordHint("германия", "Октоберфест и Бавария"),
                new WordHint("россия", "Самая большая страна мира"),
                new WordHint("канада", "Ледники и кленовый лист")
            ));
            put(Difficulty.HARD, Arrays.asList(
                new WordHint("австралия", "Кенгуру и Сиднейский оперный театр"),
                new WordHint("аргентина", "Танго и Патагония"),
                new WordHint("мадагаскар", "Остров с лемурами")
            ));
        }});

    }
    private final Map<Category, Map<Difficulty, List<WordHint>>> words;
    private final Random random;

    public Dictionary() {
        this.random = new Random();
        this.words = WORDS;
    }

    public Dictionary(Random random) {
        this.random = random;
        this.words = WORDS;
    }

    public Dictionary(Map<Category, Map<Difficulty, List<WordHint>>> words) {
        this.random = new Random();
        this.words = words;
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

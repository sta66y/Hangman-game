package engine;

public class InputValidator {

    public static boolean isHint(String input) {
        return "подсказка".equalsIgnoreCase(input);
    }

    public static boolean isSingleRussianLetter(String input) {
        return input.matches("[а-яА-ЯёЁ]");
    }

    public static char validateLetter(String input) {
        if (!isSingleRussianLetter(input)) {
            throw new IllegalArgumentException("Неверный ввод! Ожидается одна русская буква");
        }
        return Character.toLowerCase(input.charAt(0));
    }
}

package engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    @Test
    void isHint_ShouldReturnTrue_ForCorrectInput() {
        assertTrue(InputValidator.isHint("подсказка"));
        assertTrue(InputValidator.isHint("Подсказка"));
    }

    @Test
    void isHint_ShouldReturnFalse_ForIncorrectInput() {
        assertFalse(InputValidator.isHint("asd"));
        assertFalse(InputValidator.isHint(""));
    }

    @Test
    void isSingleRussianLetter_ShouldReturnTrue_ForValidLetter() {
        assertTrue(InputValidator.isSingleRussianLetter("а"));
        assertTrue(InputValidator.isSingleRussianLetter("Ё"));
    }

    @Test
    void isSingleRussianLetter_ShouldReturnFalse_ForInvalidInput() {
        assertFalse(InputValidator.isSingleRussianLetter("ab"));
        assertFalse(InputValidator.isSingleRussianLetter("1"));
        assertFalse(InputValidator.isSingleRussianLetter(""));
    }

    @Test
    void validateLetter_ShouldReturnLowercase_ForValidInput() {
        assertEquals('а', InputValidator.validateLetter("А"));
        assertEquals('ё', InputValidator.validateLetter("ё"));
    }

    @Test
    void validateLetter_ShouldThrowException_ForInvalidInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            InputValidator.validateLetter("ab")
        );
        assertEquals("Неверный ввод! Ожидается одна русская буква", exception.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
            InputValidator.validateLetter("")
        );
        assertEquals("Неверный ввод! Ожидается одна русская буква", exception2.getMessage());
    }
}

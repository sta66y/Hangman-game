package engine;

import models.Category;
import models.Difficulty;
import models.WordHint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DictionaryTest {
    private Random mockRandom;
    private Dictionary dictionary;

    @BeforeEach
    void setup() {
        mockRandom = mock(Random.class);
        dictionary = Dictionary.withRandom(mockRandom);
    }

    @Test
    void getRandomWord_ShouldReturnCorrectWord() {
        when(mockRandom.nextInt(anyInt())).thenReturn(1);
        WordHint word = dictionary.getRandomWord(Category.FRUITS, Difficulty.EASY);
        assertEquals("груша", word.getWord());
        verify(mockRandom, times(1)).nextInt(anyInt());
    }

    @Test
    void getRandomWord_ShouldThrowsException_IfCategoryIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            dictionary.getRandomWord(null, Difficulty.EASY));
        assertEquals("Не выбрана сложность или категория", ex.getMessage());
    }

    @Test
    void getRandomWord_ShouldThrowsException_IfDifficultyIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            dictionary.getRandomWord(Category.FRUITS, null));
        assertEquals("Не выбрана сложность или категория", ex.getMessage());
    }

    @Test
    void getRandomWord_ShouldThrowsException_IfNoWords() {
        Dictionary emptyDictionary = Dictionary.withWords(new HashMap<>());
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            emptyDictionary.getRandomWord(Category.FRUITS, Difficulty.EASY));
        assertEquals("Слов нет, гуляй", ex.getMessage());
    }
}

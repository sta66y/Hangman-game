package model;

import java.util.Objects;

public class WordHint {
    private final String word;
    private final String hint;

    public WordHint(String word, String hint) {
        this.word = word.toLowerCase();
        this.hint = hint;
    }

    public WordHint(String word) {
        this.word = word.toLowerCase();
        this.hint = null;
    }

    public String getWord() { return word; }
    public String getHint() { return hint; }
    public int getLength() { return word.length(); }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WordHint wordHint = (WordHint) o;
        return Objects.equals(word, wordHint.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }
}

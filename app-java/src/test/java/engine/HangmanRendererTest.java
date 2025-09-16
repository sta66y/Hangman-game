package engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HangmanRendererTest {
    private final HangmanRenderer hangmanRenderer = new HangmanRenderer();

    @Test
    void render_ShouldReturnLastPicture_IfMaxAttempts() {
        assertEquals("""
              +---+
              O   |
             /|\\  |
             / \\  |
                 ===
            """, hangmanRenderer.render(10,10));
    }

    @Test
    void render_ShouldReturnFirstPicture_IfZeroAttempts() {
        assertEquals("""
              +---+
                  |
                  |
                  |
                 ===
            """, hangmanRenderer.render(0,10));
    }

    @Test
    void render_ShouldReturnRightPicture_OnIntermediateStages() {
        assertEquals("""
              +---+
              O   |
                  |
                  |
                 ===
            """, hangmanRenderer.render(1,10));

        assertEquals(
            """
              +---+
              O   |
             /|\\  |
                  |
                 ===
            """, hangmanRenderer.render(9,10));

        assertEquals(
            """
              +---+
              O   |
             /|   |
                  |
                 ===
            """, hangmanRenderer.render(5,10));
    }


}

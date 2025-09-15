package engine;

public class HangmanRenderer {
    private final String[] stages;

    public HangmanRenderer() {
        stages = new String[] {
            """
              +---+
                  |
                  |
                  |
                 ===
            """,
            """
              +---+
              O   |
                  |
                  |
                 ===
            """,
            """
              +---+
              O   |
              |   |
                  |
                 ===
            """,
            """
              +---+
              O   |
             /|   |
                  |
                 ===
            """,
            """
              +---+
              O   |
             /|\\  |
                  |
                 ===
            """,
            """
              +---+
              O   |
             /|\\  |
             / \\  |
                 ===
            """
        };
    }

    public String render(int cntAttempts, int maxAttempts) {
        if (cntAttempts <= 0) return stages[0];
        if (cntAttempts >= maxAttempts) return stages[stages.length - 1];

        int index = (int) Math.round(((double) cntAttempts / maxAttempts) * (stages.length - 1));
        return stages[index];
    }
}

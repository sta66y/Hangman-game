package ui;

public class NonInteractiveUI implements GameUI {
    private final String ARG;

    public  NonInteractiveUI(String arg) {
        ARG = arg;
    }

    @Override
    public String getInput() {
        return ARG;
    }

    @Override
    public void showResult(String result) {
        System.out.print(result + ";");
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showGameOver(boolean isWordGuess, String answer) {
        System.out.print((isWordGuess) ? "POS" : "NEG");
    }
}

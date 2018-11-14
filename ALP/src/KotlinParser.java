import java.util.List;

public class KotlinParser {

    private List<Token> tokens;
    private int currentTokenIndex = 0;
    private Token currentToken = null;

    public KotlinParser(List<Token> tokens) {
        this.tokens = tokens;

        currentToken = tokens.get(currentTokenIndex);
    }

    private void print(String text, boolean jumpLine) {
        if (jumpLine)
            System.out.println(text);
        else
            System.out.print(text);
    }

    private void updateCurrentToken() {
        currentTokenIndex++;
        currentToken = tokens.get(currentTokenIndex);
    }

    private Token peekToken(int step) {
        return tokens.get(currentTokenIndex + step);
    }

    public boolean ParseToKotlin() {
        parseClassDeclaration();

        // TODO: Here parse class methods and check if parsing was good or not...


        return true;
    }

    private boolean parseClassDeclaration() {
        if (currentToken.id == "A" && peekToken(1).type == "class") {
            updateCurrentToken();
            print("class " + currentToken.id, true);
        }

        return true;
    }
}

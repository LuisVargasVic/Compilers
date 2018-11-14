import java.util.List;

public class KotlinParser {

    private List<Token> tokens;
    private int currentTokenIndex = 0;
    private Token currentToken = null;

    public KotlinParser(List<Token> tokens) {
        this.tokens = tokens;

        currentToken = tokens.get(currentTokenIndex);
    }

    public boolean ParseToKotlin() {
        return true;
    }
}

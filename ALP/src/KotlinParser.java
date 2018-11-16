import java.util.ArrayList;
import java.util.HashSet;
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

        while (currentToken.id != "He" || currentToken.id != "She" || currentToken.id != "To") {
            updateCurrentToken();
        }

        if (currentToken.id == "To") { return true; }

        // TODO: Check attributes
        if (peekToken(1).id == "has") {
            updateCurrentToken();
            while (currentToken.id != ".") {
                updateCurrentToken();
                String attributeName = currentToken.id;
                String attributeType = currentToken.type;
                updateCurrentToken();
                updateCurrentToken();

                String attributeVal = "";
                if (attributeType == "pair") {
                    while (currentToken.id != ")") {
                        attributeVal += currentToken.id;
                        updateCurrentToken();
                    }
                }
                else {
                    attributeVal = currentToken.id;
                }

                print(attributeType + " " + attributeName + " = " + attributeVal, true);
                updateCurrentToken();
            }

            updateCurrentToken();
        }

        if (peekToken(1).id == "can") {
            updateCurrentToken();
            HashSet<String> methods = new HashSet<>();
            while (currentToken.id != ".") {
                updateCurrentToken();

                String methodName = currentToken.id;
                methods.add(methodName);

                updateCurrentToken();
            }
        }

        return true;
    }
}

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class KotlinParser {

    private List<Token> tokens;
    private int currentTokenIndex = 0;
    private Token currentToken = null;
    private List<String> finalList = new ArrayList<>();

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
    	//System.out.println(currentToken.id);

    	
        while(currentToken.type.equals("to") && peekToken(1) != null) {
        	parseFunction();
        	//System.out.println(currentToken.id + "/" + currentToken.line);
        	if(currentTokenIndex + 1 < tokens.size()) {
        		updateCurrentToken();
        	}
        	
        	print("", true);
        }
        // TODO: Here parse class methods and check if parsing was good or not...
       // print("}", true);
        return true;
    }
    
    
    private boolean parseFunction() {
    		updateCurrentToken();
	    	if (currentToken.type == "identifier") {
	    		finalList.add("fun"+" "+ currentToken.id);
	    		updateCurrentToken();
	    		if(currentToken.type == "pronoun" && peekToken(1).type == "needs") {
	    			updateCurrentToken();
	    			updateCurrentToken();
	    			print("("+currentToken.id+":"+ "Any" +")"+""+"{",false);
	    			updateCurrentToken();
	    		}
	    		else {
	    			print("()"+ "" + "{", true );
	    		}
	    		print("\t", false);
	    	}
	    	while(currentToken.type != "end") {
	    		if(currentToken.type == "pronoun" && peekToken(1).type == "used") {
		    		updateCurrentToken();
		    		updateCurrentToken();
		    		print(currentToken.id, false);
		    		updateCurrentToken();
		    		if(currentToken.type == "opening-parenthesis") {
		    			print(currentToken.id, false);
		    			updateCurrentToken();
		    			while(currentToken.type != "closing-parenthesis") {
		    				print(currentToken.id, false);
		    				updateCurrentToken();
		    			}
		    			print(currentToken.id, false);
		    			updateCurrentToken();
		    		}
		    	}
	    		if(currentToken.type == "coma") {
		    		print("", true);
		    		print("\t", false);
		    		updateCurrentToken();
		    	}
	    		if(currentToken.type == "possesive") {
		    		updateCurrentToken();
		    		print("var"+" ", false);
		    		do {
		    			print(currentToken.id, false);
		    			updateCurrentToken();
		    		} while(currentToken.type != "is" && currentToken.type != "res" && currentToken.type != "sum" );
		    		if(currentToken.type == "res") {
		    			print(" -", false);
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type == "sum") {
		    			print(" +", false);
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type == "by" || currentToken.type == "is") {
		    			print(" =", false);
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type == "in") {
		    			print("=", false);
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type == "identifier") {
		    			print("var"+" "+currentToken.id, false);
		    		}
		    		print(" "+currentToken.id, false);
		    		updateCurrentToken();
		    	}
    			//System.out.println(currentToken.id);
	    	
	    		
	    		if(currentToken.type == "return") {
	    			print("return ", false);
	    			updateCurrentToken();
	    			if(currentToken.type != "end") {
	    				print(currentToken.id, false);
	    				updateCurrentToken();
	    			}
	    		}
	    		if(currentToken.type == "pronoun") {
	    			updateCurrentToken();
	    			if(currentToken.type =="print" && peekToken(1).type == "opening-parenthesis") {
	    				updateCurrentToken();
	    				print("print(", false);
	    				updateCurrentToken();
	    				do {
	    					print(currentToken.id, false);
	    					updateCurrentToken();
	    				} while(currentToken.type != "closing-parenthesis");
	    				print(currentToken.id, false);
	    				updateCurrentToken();
	    			}
	    		} 
	    	}
	    	print("\n", false);
    		print("}", false);
	    	print(" ", true);
	    		return true;
    }

    private boolean parseClassDeclaration() {
        print(currentToken.id + " - " + peekToken(1).type, true);
        if (currentToken.id.equals("A") && peekToken(1).type.equals("class")) {
            updateCurrentToken();
            print("public class " + currentToken.id + " {", true);
        }
        else
            return false;

        while (!currentToken.id.contains("He") && !currentToken.id.contains("She") && !currentToken.id.contains("To")) {
            updateCurrentToken();
        }

        if (currentToken.id.equals("To")) { 
        		print("hishishis", true);
	        	updateCurrentToken();
	    		return true;
        	}
        
        if(currentToken.type == "to") {
        		print("si entre", true);
        }

        // TODO: Check attributes
        if (peekToken(1).id.contains("has")) {
            updateCurrentToken();
            while (!currentToken.id.contains(".")) {
                updateCurrentToken();
                String attributeName = currentToken.id;
                String attributeType = currentToken.type;

                updateCurrentToken();
                updateCurrentToken();

                StringBuilder attributeVal = new StringBuilder();
                if (attributeType.contains("pairID")) {
                    while (!currentToken.id.contains(")")) {
                        attributeVal.append(currentToken.id);
                        updateCurrentToken();
                    }
                    attributeVal.append(")");
                }
                else {
                    attributeVal = new StringBuilder(currentToken.id);
                }

                print("     " + attributeType + " " + attributeName + " = " + attributeVal, true);
                updateCurrentToken();
            }

            updateCurrentToken();
        }

        print(currentToken.id, true);
        if (peekToken(1).id.contains("can")) {
            updateCurrentToken();
            HashSet<String> methods = new HashSet<>();
            while (!currentToken.id.equals(".")) {
                updateCurrentToken();

                String methodName = currentToken.id;
                methods.add(methodName);

                updateCurrentToken();
            }
        }

        updateCurrentToken();

        return true;
    }
}

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
    		String buffer = "";
    	    List<String> finalList = new ArrayList<>();

    		updateCurrentToken();
	    	if (currentToken.type.equals("identifier")) {
	    		buffer+=("fun"+" "+ currentToken.id);
	    		updateCurrentToken();
	    		if(currentToken.type.equals("pronoun") && peekToken(1).type.equals("needs")) {
	    			updateCurrentToken();
	    			updateCurrentToken();
	    			buffer+="("+currentToken.id+":"+ "Any" +")";
	    			//finalList.add(buffer);
	    			updateCurrentToken();
	    		}
	    		else {
	    			buffer+=("()"+ "" );
	    			finalList.add(buffer);
	    			buffer = "";
	    		}
	    		buffer+=("\t");
	    		finalList.add(buffer);
	    		buffer = "";
	    		
	    	}
	    	while(currentToken.type != "end") {
	    		if(currentToken.type.equals("pronoun") && peekToken(1).type.equals ("used")) {
		    		updateCurrentToken();
		    		updateCurrentToken();
		    		buffer+=(currentToken.id);
		    		updateCurrentToken();
		    		if(currentToken.type == "opening-parenthesis") {
		    			buffer+=(currentToken.id);
		    			updateCurrentToken();
		    			while(currentToken.type != "closing-parenthesis") {
		    				buffer+=(currentToken.id);
		    				updateCurrentToken();
		    			}
		    			buffer+=(currentToken.id);
		    			updateCurrentToken();
		    		}
	    			finalList.add(buffer);
	    			buffer="";

		    	}
	    		if(currentToken.type.equals( "coma")) {
		    		buffer=("");
		    		buffer+=("\t");
	    			finalList.add(buffer);
		    		updateCurrentToken();
		    		buffer = "";
		    	}
	    		if(currentToken.type.equals( "possesive")) {
		    		updateCurrentToken();
		    		//buffer+=("var"+" ");
		    		do {
		    			buffer+=(currentToken.id);
		    			updateCurrentToken();
		    		} while(currentToken.type != "is" && currentToken.type != "res" && currentToken.type != "sum" );
		    		if(currentToken.type.equals( "res")) {
		    			buffer+=("-");
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type.equals( "sum")) {
		    			buffer+=("+");
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type.equals( "by")) {
		    			buffer+=(" =");
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type.equals("is")){
		    			buffer+=(" =");
		    			buffer = "var " + buffer; 
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type.equals("in")) {
		    			buffer+=("=");
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type.equals("identifier")) {
		    			buffer+=("var"+" "+currentToken.id);

		    		}
		    		buffer+=(" "+currentToken.id);
	    			finalList.add(buffer);
		    		updateCurrentToken();
		    		buffer = "";
		    	}
	    		
    			//System.out.println(currentToken.id);
	    	
	    		if(currentToken.type.equals("end")) {
					finalList.set(0, finalList.get(0) + "{");
	    		}
	    		
	    		if(currentToken.type.equals( "return")) {
	    			buffer+=("return ");
	    			updateCurrentToken();
	    			if(currentToken.type != "end") {
	    				buffer+=(currentToken.id);
	    				if(currentToken.type.equals("string")) {
	    					finalList.set(0, finalList.get(0) + ": String" + "{");
	    				}else if(currentToken.type.equals("integer")){
	    					finalList.set(0, finalList.get(0) + ": Int" + "{");
	    				}
	    				updateCurrentToken();
	    			}
	    			finalList.add(buffer);
	    			buffer = "";
	    			

	    		}
	    		
	    		
	    		if(currentToken.type.equals( "pronoun")) {
	    			updateCurrentToken();
	    			if(currentToken.type.equals("print") && peekToken(1).type.equals( "opening-parenthesis")) {
	    				updateCurrentToken();
	    				buffer+=("print(");
		    			//finalList.add(buffer);
	    				updateCurrentToken();
	    				do {
	    					buffer+=(currentToken.id);
	    					updateCurrentToken();
	    				} while(currentToken.type != "closing-parenthesis");
	    				buffer+=(currentToken.id);
	    		    	//System.out.print(buffer);
	    				updateCurrentToken();
	    			}

	    		} 
    			finalList.add(buffer);
    			buffer = "";

	    	}
	    	buffer+=("\n");
    		buffer+=("}");
			finalList.add(buffer);
	    	buffer=(" ");
	    	
	    	for(int i=0; i < finalList.size(); i++) {
	    		System.out.println(finalList.get(i));
	    		
	    	}
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

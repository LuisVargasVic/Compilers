import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KotlinParser {

    private List<Token> tokens;
    private int currentTokenIndex = 0;
    private Token currentToken = null;
	BufferedWriter writer;

	private Set<String> attributesForClass = new HashSet<>();
	private Set<String> functionsForClass = new HashSet<>();
	private String currentClassName = "";

    public KotlinParser(List<Token> tokens) {
        this.tokens = tokens;
        currentToken = tokens.get(currentTokenIndex);
    }

    private void print(String text, boolean jumpLine) {
		try {
			if (writer == null) {
				writer = new BufferedWriter(new FileWriter("samplefire1.kt"));
			}

			if (jumpLine)
				writer.write(text + "\n");
			else
				writer.write(text);

		}
		catch (IOException e) {
			e.printStackTrace();
		}
    }

    private void updateCurrentToken() {
        currentTokenIndex++;
        currentToken = tokens.get(currentTokenIndex);
    }

    private Token peekToken(int step) {
    	return tokens.get(currentTokenIndex + step);	
    }
    

    public boolean ParseToKotlin() {
    	while (currentToken.id.equals("A")) {
			parseClassDeclaration();

			print("\n", false);

			while(currentToken.type.equals("to")/* && peekToken(1) != null*/) {
				if (!parseFunction()) {
					return false;
				}

				if(currentTokenIndex + 1 < tokens.size()) {
					updateCurrentToken();
				}

				print("\n", false);
			}

			print("}\n\n", true);

			if (functionsForClass.size() > 0) {
				System.out.println("Not all methods for class " + currentClassName + " are implemented, wrong parsing.");
				return false;
			}
		}


		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return true;
    }
    
    
    private boolean parseFunction() {
    		String buffer = "\t";
    	    List<String> finalList = new ArrayList<>();

    		updateCurrentToken();
	    	if (currentToken.type.equals("function")) {

	    		if (functionsForClass.contains(currentToken.id)) {
	    			functionsForClass.remove(currentToken.id);
				}

	    		buffer+=("fun "+ currentToken.id);
	    		updateCurrentToken();
	    		if(currentToken.type.equals("pronoun") && peekToken(1).type.equals("needs")) {
	    			updateCurrentToken();
	    			updateCurrentToken();
	    			buffer+="("+currentToken.id+":"+ " Any" +")";
	    			//finalList.add(buffer);
	    			updateCurrentToken();
	    		}
	    		else {
	    			buffer+=("()");
	    			finalList.add(buffer);
	    			buffer = "";
	    		}
	    		//buffer+=("\t");
	    		finalList.add(buffer);
	    		buffer = "\t\t";
	    		
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
	    			buffer="\t\t";

		    	}
	    		if(currentToken.type.equals( "coma")) {
		    		buffer=("");
		    		buffer+=("\t");
	    			finalList.add(buffer);
		    		updateCurrentToken();
	    			buffer="\t\t";
		    	}
	    		if(currentToken.type.equals( "possesive")) {
	    			String attributeName = "";
	    			if (!attributesForClass.contains(peekToken(1).id) && peekToken(2).type != "is") {
						System.out.println("Global Attribute " + peekToken(1).id + " is not declared in class.");
						return false;
					}
	    		

		    		updateCurrentToken();
		    		
		    		attributeName = currentToken.id;
		    		
		    		do {
		    			buffer+=(currentToken.id);
		    			updateCurrentToken();
		    		} while(currentToken.type != "is" && currentToken.type != "res" && currentToken.type != "sum" );
		    		if(currentToken.type.equals( "res")) {
		    			buffer+=(" -");
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type.equals( "sum")) {
		    			buffer+=(" +");
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type.equals( "by")) {
		    			buffer+=("=");
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type.equals("is")){
		    			buffer+=(" =");
		    			String buf = "";
		    			buf = buffer.substring(2);
		    			if (attributesForClass.contains(attributeName))
		    				buffer = "\t\t" + buf; 
		    			else 
		    				buffer = "\t\tvar " + buf; 
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type.equals("in")) {
		    			buffer+=("=");
		    			updateCurrentToken();
		    		}
		    		if(currentToken.type.equals("identifier")) {
		    			if (!attributesForClass.contains(currentToken.id)) {
		    				System.out.println("Global Attribute " + currentToken.id + " is not declared in class.");
		    				return false;
						}

		    			buffer+=(" this."+currentToken.id);
						updateCurrentToken();
		    		}
		    		if (currentToken.type != "coma") {
						buffer+=(" "+currentToken.id);
						updateCurrentToken();

					}
	    			finalList.add(buffer);
		    		buffer = "";
		    	}
	    		
    			//System.out.println(currentToken.id);
	    	
	    		if(currentToken.type.equals("end")) {
					finalList.set(0, finalList.get(0) + " {");
	    		}
	    		
	    		if(currentToken.type.equals( "return")) {
	    			buffer+=("return ");
	    			updateCurrentToken();
	    			if(currentToken.type != "end") {
	    				buffer+=(currentToken.id);
	    				if(currentToken.type.equals("string")) {
	    					finalList.set(0, finalList.get(0) + ": String" + " {");
	    				}else if(currentToken.type.equals("integer")){
	    					finalList.set(0, finalList.get(0) + ": Int" + " {");
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
	    	//buffer+=("\n");
    		buffer+=("\t}");
			finalList.add(buffer);
	    	buffer=(" ");
	    	
	    	for(int i=0; i < finalList.size(); i++) {
	    		//System.out.println(finalList.get(i));
	    		print(finalList.get(i), true);
	    	}

	    	return true;
    }


	private boolean parseClassDeclaration() {
    	functionsForClass = new HashSet<>();
    	attributesForClass = new HashSet<>();

		if (currentToken.id.equals("A") && peekToken(1).type.equals("class")) {
			updateCurrentToken();
			//codeGenerated.append("public class " + currentToken.id + " {\n");
			currentClassName = currentToken.id;
			print("public class " + currentToken.id + " {", true);
		}
		else
			return false;

		while (!currentToken.id.contains("He") && !currentToken.id.contains("She") && !currentToken.id.contains("To")) {
			updateCurrentToken();
		}

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
				//codeGenerated.append("\tvar " + attributeName + " = " + attributeVal + "\n");
				print("\tvar " + attributeName + " = " + attributeVal, true);
				attributesForClass.add(attributeName);
				updateCurrentToken();
			}
			updateCurrentToken();
		}

		if (peekToken(1).id.contains("can")) {
			updateCurrentToken();
			while (!currentToken.id.equals(".")) {
				updateCurrentToken();
				String methodName = currentToken.id;
				functionsForClass.add(methodName);

				updateCurrentToken();
			}
		}

		updateCurrentToken();
		return true;
	}
}

import java.util.ArrayList;
import java.util.List;

public class Parenthesis {
	
	private static boolean balanceChain(char[] objects) {
		
        boolean balance = true;
        List<Character> parenthesis = new ArrayList<Character>();

        for (int i = 0; i < objects.length; i++) {

            System.out.println("Parenthesis: " + objects[i]);
        	if (objects[i] == '(' ) {
        		parenthesis.add(objects[i]);
            } else if (objects[i] == ')') {
            	if (parenthesis.isEmpty()){
					balance = false;
				} else {
					parenthesis.remove((parenthesis.size()- 1));
				}
            }
			else {
				System.out.println("No acceptable");
			}
            System.out.println("Stack: " + parenthesis);
        }
        
        if (parenthesis.size() > 0) {
        	balance = false;
        }
        
        return balance;
    }
	
	public static void main(String args[]) {
    	String chain = "(())";
	    char[] objects = chain.toCharArray();
	    System.out.println(balanceChain(objects));
    }
}

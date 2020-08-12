// T15_37_4687_Amr_Mohamed
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
public class LeftRecursion {
	static String LREinput = "";
	static String [] CFG;
	static String output;
	static int countVariables;
	static boolean RECFound;
	static int RecCounter;
	static String [] variables;
	static Stack<String> stackVariables;
	static int countRecursion;
	
	public static boolean checkForSubstitution(Stack<String> stackVariables, String firstCharacter, String variable) {
		boolean found = false;
		Stack<String> tempStackVariables = (Stack<String>) stackVariables.clone();
		
		while(! tempStackVariables.isEmpty()) {
			String poped = tempStackVariables.pop();
			if(poped.equals(firstCharacter) & ! poped.equals(variable)) {
				found = true;
				break;
			}

		}
		return found;
	}
	
	public static String returnTheStateShouldBeSubstituted(String variable) {
		String [] states = output.split(";");
		String result = "";
//		System.out.println(output);
		for(int i = 0; i < states.length; i++) {
			if(states[i].substring(0,1).equals(variable)) {
//				System.out.println(states[i]);
				if(states[i].length()>2) {
					result = states[i].substring(2);
				}
				
				break;
			}
		}
		return result;
	}
	
	public static String LRE(String input) {
		LREinput = input;
		CFG = LREinput.split(";");
		output = "";
		countVariables = CFG.length;
		countRecursion = 0;
		variables = new String[countVariables];
		RECFound = false;
		RecCounter = 0;
		stackVariables = new Stack<String>();
//		int a = 0;
//		for(int i = 0; i < CFG.length; i++) {
//			if(! CFG[i].substring(1,2).equals("'")) {
//				variables[a] = CFG[i].substring(0,1);
//				a++;
//			}
//		}
		for(int i = 0; i < CFG.length; i++) {
			String [] currentState = CFG[i].split(",");
			String variable = currentState[0];
			stackVariables.push(variable);
			String dashOutput = "";
			String noDashOutput = "";
			boolean recursionFound = false;
			int countOfFound = 0;
			noDashOutput += variable+",";
			//boolean foundRecursion = checkForRecursion(currentState, variable);
			//boolean foundSubstitution = checkForSubstitution(currentVariables, firstCharacter, variable);
			Stack<String> stack = new Stack<String>();
			for(int j = 1; j < currentState.length; j++) {
				stack.add(currentState[j]);
			}
			output += variable;
			while(! stack.isEmpty()) {
				String popedString = stack.pop();
				String firstCharacter = popedString.substring(0, 1);
				boolean foundSubstitution = checkForSubstitution(stackVariables, firstCharacter, variable);
				if(foundSubstitution) {
				    String result = returnTheStateShouldBeSubstituted(firstCharacter);
					if(! result.equals("")) {
						String [] strsShouldBeSubstituted = result.split(",");
						for(int c = 0; c < strsShouldBeSubstituted.length; c++) {
							if(popedString.length() > 1) {
								stack.push(strsShouldBeSubstituted[c]+popedString.substring(1));
							}
							else {
								stack.push(strsShouldBeSubstituted[c]);
							}
							
						}
					}
				}
				boolean checkMoreSubstitution = true;
				if(! stack.isEmpty()){
					checkMoreSubstitution = checkForSubstitution(stackVariables, stack.peek().substring(0, 1), variable);
				}

				if(foundSubstitution & ! checkMoreSubstitution) {
					output += ","+stack.pop();
				}
			    if(! foundSubstitution) {
					output += ","+popedString;
				}	
			}
			output += ";";
			//System.out.println("OUPUT  "+output);
			
			String [] OuputStates = output.split(";");
			String [] CurrentOuputStates = new String[] {};
			CurrentOuputStates = OuputStates[RecCounter].split(",");
			output = "";
			for(int k = 0; k < OuputStates.length-1; k++) {
				output += OuputStates[k]+";";
			}
			for(int j = 1; j < CurrentOuputStates.length; j++) {
				//System.out.println("CurrentOuputStates   " + CurrentOuputStates[j]);
				String firstTerminal = CurrentOuputStates[j].substring(0,1);
				if(variable.equals(firstTerminal)) {
					recursionFound = true;
					countOfFound++;
					if(countOfFound == 1) {
						dashOutput += variable+"'"+",";
					}
					dashOutput += CurrentOuputStates[j].substring(1, CurrentOuputStates[j].length())+variable+"'"+",";		
				}
				else {
					if(recursionFound == true) {
						noDashOutput += CurrentOuputStates[j]+variable+"'"+",";
					}
					
					else {
						noDashOutput += CurrentOuputStates[j]+",";
					}	
				}
			}
			
			if(recursionFound == true) {
				boolean entered = false;
				String [] checkForDash = noDashOutput.split(",");
				for(int k = 1; k < checkForDash.length; k++) {
					if(! checkForDash[k].substring(checkForDash[k].length()-1,checkForDash[k].length()).equals("'")) {
						entered = true;
						checkForDash[k] += variable+"'";
					}
				}
				
				if(entered) {
					noDashOutput = "";
					for(int k = 0; k < checkForDash.length; k++) {
						noDashOutput += checkForDash[k] + ",";
					}
				}
				if(noDashOutput.equals(variable+",")) {
					output += dashOutput + ";";
					stackVariables.pop();
					RecCounter -= 1;
				}
				else {
				output += noDashOutput.substring(0, noDashOutput.length()-1) + ";" + dashOutput + ";";
				}
				RECFound = true;
				RecCounter += 2;
			    //System.out.println("Entered   " + noDashOutput.substring(0, noDashOutput.length()-1) + ";" + dashOutput + ";");
			}
			
			else {
				output += noDashOutput.substring(0, noDashOutput.length()-1) + ";";
				RECFound = false;
				RecCounter += 1;
				//System.out.println("NotEntered   " + noDashOutput.substring(0, noDashOutput.length()-1) + ";");
			}	
		}
		output = output.substring(0, output.length()-1);
		return output;
	}

	// MAIN METHOD
	public static void main(String[] args) {
		String input = "S,ScT,T;T,aSb,iaLb,i;L,SdL,S";  //S,ScT,T;T,aSb,iaLb,i;L,SdL,S
		String output = LRE(input);
		System.out.println(output);
	}
}

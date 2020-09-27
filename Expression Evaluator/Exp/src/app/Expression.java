package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	String name = "";
    	boolean skip = false;
    	for (int i = 0;i<expr.length();i++) {
    		char curr = expr.charAt(i);
    		
			//System.out.println(num);
			if((curr==' ')||(skip==true) ){
				if(curr==']') {skip=false;}
				//more=false;
				continue;
			}
			if (Character.isLetter(curr)) {	

					name+=curr;
					//more=true;
				
			}
			
			else if(curr=='[') {
				boolean add = true;
				for(int j=0;j<arrays.size();j++) {
					if (name.equals(arrays.get(j).name)){
						add=false;
						break;
					}
				}
				if (add) {
					arrays.add(new Array(name));
				}
				
					name="";
				
				//skip =true;
			}
			else if (name.isEmpty()==false && curr =='+' ||curr=='-'||curr=='*'||curr=='/'||curr=='(') {
				boolean add = true;
				for(int j=0;j<vars.size();j++) {
					if (name.equals(vars.get(j).name)){
						add=false;
						break;
					}
				}
				if (add) {
					vars.add(new Variable(name));
				}
				name="";
			}
    	}
    	if (name.isEmpty()==false) {
    		boolean add = true;
			for(int j=0;j<vars.size();j++) {
				if (name.equals(vars.get(j).name)){
					add=false;
					break;
				}
			}
			if (add) {
				vars.add(new Variable(name));
			}
    	}
    }
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    //public static Stack <String> operator = new Stack <String>();
	//public static Stack <Float> operand = new Stack<Float>();
    public static float 
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	expr = expr.replaceAll("\\s+", "");// removes all unnecessary spaces from expr
    	// make tokens based on pemdas
    	while(expr.contains(")")){
    		expr=paren(expr, vars, arrays);
    	}
    	
    	if ((expr).contains("]")) {
    		expr = brack(expr,vars,arrays);
    	}
    	
    	System.out.println(expr);
    	return evalnoparen(expr, vars, arrays);
    	
    	 }
    
    private static String brack(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	StringTokenizer add = new StringTokenizer(expr,"[]", true);
    	
    	String inter="",fin="",temp="";
    	while (add.hasMoreElements()) {
    		int counter = 0;
    		temp = add.nextToken();
    		if(temp.equals("[")) {
    			counter=1;
	    		while(true) {
	    			temp = add.nextToken();
		    		if ((temp).equals("[")) {counter++;}
		    		else if ((temp).equals("]")) {
		    			counter--;
		    			if(counter==0) { inter =""+ (int)evaluate(inter,vars,arrays);fin+="["+inter+"]";break;}
		    		}
		    		inter+=temp;
		    		
				}
	    		inter="";
    		}
    		else {
    			fin += temp;
    		}
		}
    	/*String non="";
    	if ((expr).contains("]")) {
    		return non+=expr.substring(0,expr.indexOf("[")+1)+(int)evaluate(expr.substring(expr.indexOf("[")+1,expr.lastIndexOf("]")), vars, arrays)+expr.substring(expr.lastIndexOf("]"),expr.length());
    	} 
    	return expr;*/
    	return fin;
    }
	private static String paren(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	
    	StringTokenizer add = new StringTokenizer(expr,"()", true);
    	
    	String inter="",fin="",temp="";
    	while (add.hasMoreElements()) {
    		int counter = 0;
    		temp = add.nextToken();
    		if(temp.equals("(")) {
    			counter=1;
	    		while(true) {
	    			temp = add.nextToken();
		    		if ((temp).equals("(")) {counter++;}
		    		else if ((temp).equals(")")) {
		    			counter--;
		    			if(counter==0) { inter =""+ evaluate(inter,vars,arrays);fin+=inter;break;}
		    		}
		    		inter+=temp;
				}
	    		inter="";
    		}
    		else {
    			fin += temp;
    		}
		}
    	
		/*String non="";
    	if ((expr).contains(")")) {
    		return non+=expr.substring(0,expr.indexOf("("))+(int)evaluate(expr.substring(expr.indexOf("(")+1,expr.lastIndexOf(")")), vars, arrays)+expr.substring(expr.lastIndexOf(")")+1,expr.length());
    	} 
    	return expr;*/
    	return fin;
    }
	
  
    private static float evalnoparen(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays){
    	
    	try {
    		return Float.parseFloat(expr);//converts string numbers to floats
    	}
    	catch (NumberFormatException nfe) {
    	}
    	for(int i=0; i< vars.size();i++) { //replaces variables with their values
    		if (expr.equals(vars.get(i).name)) {
    			return vars.get(i).value;
    		}
    	}
    	for(int i=0; i< arrays.size();i++) { //replaces variables with their values
    		if(expr.indexOf("[")==-1) {break;}
    		if (expr.substring(0,expr.indexOf("[")).equals(arrays.get(i).name)&&expr.contains("+")==false&&expr.contains("-")==false&&expr.contains("*")==false&&expr.contains("/")==false) {
    			return arrays.get(i).values[Integer.parseInt(expr.substring(expr.indexOf("[")+1, expr.indexOf("]")))];
    		}
    	}
    	
    	
    	//Stack <String> operator = new Stack <String>();
    	Stack <Float> operand = new Stack<Float>();
    	StringTokenizer add = new StringTokenizer(expr,"+", true);
    	if(add.countTokens()>1) {
	    	while(add.hasMoreElements()) {
	    		String temp = add.nextToken();
	    		//System.out.println(temp);
	    		if (temp.equals("+")==false) {
	    			operand.push(evalnoparen(temp,vars,arrays));
	    			
	    		}
	    		if(operand.size()==2) {operand.push(operand.pop()+operand.pop());}
	    	}
	    	
	    	return operand.peek();
    	}
    	String [] doublemult = expr.split("\\*-");
    	String [] doublediv = expr.split("/-");
    	String [] doublesub = expr.split("--");
    	if(doublesub.length>1) {
    		for (int i=0;i<doublesub.length;i++) {
	    		String temp = doublesub[i];
	    		if (temp.equals("--")==false) {
	    			operand.push(evalnoparen(temp,vars,arrays));
	    		}
	    		if(operand.size()==2) {
	    			float a = operand.pop(), b = operand.pop();
	        		//System.out.println(a+" "+b);
	    			operand.push(b+a);
	        		//System.out.println(a+" "+b+" "+operand.peek());
	    		}
	    	}
        	return operand.peek();
    	}
    	
    	
    	
    	/*StringTokenizer sub = new StringTokenizer(expr,"-", true);
    	if(sub.countTokens()>1) {
    		while(sub.hasMoreElements()) {
	    		String temp = sub.nextToken();
	    		if (temp.equals("-")==false) {
	    			operand.push(evalnoparen(temp,vars,arrays));
	    		}
	    		if(operand.size()==2) {
	    			float a = operand.pop(), b = operand.pop();
	        		operand.push(b-a);
	        		//System.out.println(a+" "+b+" "+operand.peek());
	    		}
	    	}
    		
        	return operand.peek();
    	}*/
    	
    	StringTokenizer s = new StringTokenizer(expr,"\\*/-", true);//"\\*-|/-|-"
    	//String [] s = expr.split("((?<=\\*-)|(?=\\*-))|((?<=/-)|(?=/-))|((?<=-)|(?=-))");
    	//String[]s = expr.split("\\*-)");
    	ArrayList<String> sub = new ArrayList<String>();
    	int cs=0;
    	String t="";
    	//for(int i = 0;i<s.length;i++) {System.out.print(s[i]+" ");}
    	if(s.countTokens()>1) {	//.countTokens()>1
    		String prev="";
    		while(s.hasMoreElements()) { //s.hasMoreElements()
    			String temp = s.nextToken(); //s.nextToken()
	    		if(temp.equals("-")==false||((prev.equals("*")||prev.equals("/"))&&temp.contentEquals("-"))) {
	    			t+=temp;
	    		}
	    		else {
	    			sub.add(t);
	    			sub.add(temp);
	    			//System.out.println(t+" "+temp);
	    			t="";
	    		}
	    		prev=temp;
			}
    		sub.add(t);
	    }
    	/*if(s.length>1) {
    		t+=s[0];
	    	for (int i =1; i<s.length;i++) {
	    		
	    		if(s[i].equals("-")==false||((s[i-1].equals("*"))||(s[i-1].equals("/"))&&  s[i].equals("-"))) {
	    			t+=s[i];
	    		}
	    		else {
	    			sub.add(t);
	    			sub.add(s[i]);
	    			//System.out.println(t+" "+s[i]);
	    			t="";
	    		}
	    	}
    	//System.out.println(sub.size());
    	}*/
	    if(sub.size()>1) {
    		for (int i=0;i<sub.size();i++) {
	    		System.out.println(sub.get(i));
	    		if (sub.get(i).equals("-")==false) {
	    			operand.push(evalnoparen(sub.get(i),vars,arrays));
	    		}
	    		
	    		if(operand.size()==2) {
	    			float a = operand.pop(), b = operand.pop();
	        		//System.out.println(a+" "+b);
	    			
	        		operand.push(b-a);
	        		//System.out.println(a+" "+b+" "+operand.peek());
	    		}
	    		
	    	}
    		return operand.peek();
    	}
    	
    	
    	if(doublemult.length>1) {
    		for (int i=0;i<doublemult.length;i++) {
	    		String temp = doublemult[i];
	    		if (temp.equals("*-")==false) {
	    			operand.push(evalnoparen(temp,vars,arrays));
	    		}
	    		if(operand.size()==2) {
	    			float a = operand.pop(), b =-1*  operand.pop();
	        		System.out.println(a+" "+b);
	    			operand.push(b*a);
	        		//System.out.println(a+" "+b+" "+operand.peek());
	    		}
	    	}
        	return operand.peek();
    	}
    	StringTokenizer mult = new StringTokenizer(expr,"*", true);
    	if(mult.countTokens()>1) {
    		while(mult.hasMoreElements()) {
	    		String temp = mult.nextToken();
	    		if (temp.equals("*")==false) {
	    			operand.push(evalnoparen(temp,vars,arrays));
	    		}
	    		if(operand.size()==2) {operand.push(operand.pop()*operand.pop());}
    		}
    		
	        	return operand.peek();
    		
    	}
    	//String [] doublediv = expr.split("/-");
    	if(doublediv.length>1) {
    		for (int i=0;i<doublediv.length;i++) {
	    		String temp = doublediv[i];
	    		if (temp.equals("/-")==false) {
	    			operand.push(evalnoparen(temp,vars,arrays));
	    		}
	    		if(operand.size()==2) {
	    			float a =  operand.pop(), b = -1*operand.pop();
	        		System.out.println(a+" "+b);
	    			operand.push(b/a);
	        		//System.out.println(a+" "+b+" "+operand.peek());
	    		}
	    	}
        	return operand.peek();
    	}
    	StringTokenizer div = new StringTokenizer(expr,"/", true);
    	if(div.countTokens()>1) {
    		while(div.hasMoreElements()) {
	    		String temp = div.nextToken();
	    		if (temp.equals("/")==false) {
	    			operand.push(evalnoparen(temp,vars,arrays));
	    		}
	    		if(operand.size()==2) {
	    			float a = operand.pop(), b = operand.pop();
	        		operand.push(b/a);
	    		}
	    		
    		}
    		
        	return operand.peek();
    	}
    	
    	// BY this point the parsing has been solved
    	
    	return operand.peek();

    }
}

package edu.wit.cs.comp2000;
import com.sun.javafx.fxml.expression.BinaryExpression;

import javax.security.auth.callback.CallbackHandler;
import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.nio.charset.CharacterCodingException;
import java.util.Scanner;

public class InfixEvaluator {

	// Calculates the result of the exp parameter and returns the result
	public static int evalExpression(String exp) {
		//STUB
		/* take in an expression as str, iterate through string (checking for chars[-,+,/,*,()]
		* if char matches pop, chars will be in case statement in order of preference
		* GEMDAS[(),*,/,+,-] eval expression */

		// Init empty stacks
		StackInterface<Character> operatorStack = new VectorStack<>();
		StackInterface<Integer> valueStack = new VectorStack<>();

		// Iterate through entire expression and add chars to proper stacks to later be evaluated.
		for (int idx = 0; idx < exp.length(); idx++ ) {
			char nextCharacter = exp.charAt(idx);
			// Checks if char is digit and adds to value bag if true.
			if (Character.isDigit(nextCharacter)) {
				valueStack.push(Character.getNumericValue(nextCharacter));
			}
			// Capture groups and push onto stack.
			else if (nextCharacter == '(') {
				operatorStack.push(nextCharacter);
			}
			// Evaluate groups captured first.
			else if (nextCharacter == ')') {
				while (operatorStack.peek() != '(')
					valueStack.push(getOperationResult(operatorStack.pop(), valueStack.pop(), valueStack.pop()));
				operatorStack.pop();
			}
			// Next evaluate operators that aren't grouping symbols in order of precedence.
			else if (nextCharacter == '+' || nextCharacter == '-' || nextCharacter == '*' || nextCharacter == '/') {
				while (!operatorStack.isEmpty() && hasPrecedence(nextCharacter, operatorStack.peek()))
					valueStack.push(getOperationResult(operatorStack.pop(), valueStack.pop(), valueStack.pop()));
				operatorStack.push(nextCharacter);
			}
		}
		// Once groups have been captured and parsed, evaluate the remaining values and operators.
		while (!operatorStack.isEmpty())
			valueStack.push(getOperationResult(operatorStack.pop(),valueStack.pop(),valueStack.pop()));
		return valueStack.pop();
	}

	// Check the precedence of operators.
	private static boolean hasPrecedence(char opTwo, char opOne) {
		if (opOne =='(' || opTwo==')')
			return false;
        return (opOne != '+' && opOne != '-') || (opTwo != '*' && opTwo != '/');
	}

	// Used to calculate the operands once they have been captured
	private static int getOperationResult(char c, int opTwo, int opOne) {
		int result=0;
		if (c=='+')
			result = opOne + opTwo;
		else if (c=='-')
			result = opOne - opTwo;
		else if (c=='*')
			result = opOne * opTwo;
		else if (c=='/')
			result = opOne / opTwo;

		return result;
	}
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		// Prompts user on how to use the calc, evals the expression
		// then waits for next expression or waits for quit signal.
		while (true) {
			System.out.print("Calculate an expression using stacks and infix notation.\n" +
					"Valid operations are [+,-,*,/,()]\n"
					+ "Enter 'Q' or 'quit' to exit\n"
					+ "Enter expression to compute:\n");
			String inExpression = s.nextLine();
			if (inExpression.equals("quit") || inExpression.equals("Q")) {
				System.out.println("Calc exiting, Goodbye!");
				System.exit(0);
			}
            try {
            	// verify that expression does not contain any word chars, multi-digit integers
				// or negative numbers
            	if (inExpression.matches("[a-zA-Z]+|\\d{2,}|-\\d+")) {
					System.out.printf("Expression %s is not valid\n",inExpression);
					break;
				}
				int total = evalExpression(inExpression);
				System.out.printf("Your expression: %s = %s\n\n", inExpression, total);

            } catch (Exception err) {
            	System.out.printf("%s CAUSED EXCEPTION %s\n\n", inExpression, err);
        	}
		}
	}
}
//Authored and commented by Orion Collins
package com.company;

import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

    static boolean isDelim(char ch) {
        return ch == ' ';
    }

    static boolean isOperator(char ch) {
        return (ch == '*' || ch == '+' || ch == '-' || ch == '/');
    }

    static int getPriority(char ch) {
        switch (ch) {
            case '*':
            case '/':
                return 1;
            case '+':
            case '-':
                return 2;
            case '(':
                return 3;
            default:
                return -1;
        }
    }

    public static String calculateExpression(String expression) {
        Stack<Integer> stack = new Stack<Integer>();
        for (char token : expression.toCharArray()) {
            if (!isOperator(token)) {
                stack.push((int) token - '0');
            }
            else {
                Integer operand2 = stack.pop();
                Integer operand1 = stack.pop();
                switch (token) {
                    case '*' -> stack.push(operand1 * operand2);
                    case '/' -> stack.push(operand1 / operand2);
                    case '+' -> stack.push(operand1 + operand2);
                    case '-' -> stack.push(operand1 - operand2);
                }
            }
        }
        if (stack.size() != 1)
            throw new IllegalArgumentException("Expression syntax error.");
        return stack.pop().toString();
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        Stack<Character> st = new Stack();

        String ans = new String();
        for (int i = 0; i < s.length(); ++i) {
            char cur = s.charAt(i);
            if (!isDelim(cur)) {
                if (cur == '(') {
                    st.push(cur);
                } else if (isOperator(cur)) {
                    while (!st.empty() && getPriority(st.peek()) <= getPriority(cur)) {
                        ans += st.pop();
                    }
                    st.push(cur);
                } else if (cur == ')') {
                    while (!st.empty() && st.peek() != '(') {
                        ans += st.pop();
                    }
                    if (!st.empty()) st.pop();
                } else {
                    ans += cur;
                }
            }
        }
        while (!st.empty()) ans += st.pop();
        System.out.println(calculateExpression(ans));
    }
}

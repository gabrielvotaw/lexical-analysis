package lexicalanalysis;

// Name: Gabriel Votaw
// Project Partner's Name: Austin Kirk

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

public class Lexer {   
    
    public static void tokenize(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            TreeMap tokenMap = new TreeMap();
            loadCharMap(tokenMap);
            
            int nextChar  = br.read();
            
            while (nextChar != -1) {
                StringBuilder token = new StringBuilder();
                
                if (Character.isLetter(nextChar)) {
                    
                    while (!isSymbol(nextChar) && !Character.isWhitespace(nextChar) && nextChar != -1) {
                        token.append((char)nextChar);
                        nextChar = br.read();
                    }
                    
                    if (tokenMap.get(token.toString()) != null) {
                        System.out.println(tokenMap.get(token.toString()));
                    } else {
                        System.out.println("IDENT:" + token.toString());
                    }
                    
                } else if (Character.isDigit(nextChar)) {
                    
                    boolean firstDigit = true;
                    
                    while (Character.isDigit(nextChar)) {
                        token.append((char)nextChar);
                        nextChar = br.read();
                        
                        if (firstDigit && Character.isLetter(nextChar)) {
                            System.out.println("SYNTAX ERROR: INVALID IDENTIFIER NAME");
                            System.exit(0);
                        }
                        firstDigit = false;
                    }
                    System.out.println("INT_LIT:" + token.toString());
                    
                } else if (isSymbol(nextChar)) {
                    
                    int max = 0;
                    
                    while (isSymbol(nextChar) && max < 2) {
                        token.append((char)nextChar);
                        nextChar = br.read();
                        max++;
                    }
                    if (tokenMap.get(token.toString()) != null) {
                        System.out.println(tokenMap.get(token.toString()));
                    } else {
                        System.out.println(tokenMap.get(Character.toString(token.charAt(0))));
                        System.out.println(tokenMap.get(Character.toString(token.charAt(1))));
                    }
                    
                } else if (Character.isWhitespace(nextChar)) {
                   
                    nextChar = br.read();
                    
                } else {
                    
                    System.out.println("SYNTAX ERROR: INVALID CHARACTER");
                    System.exit(0);
                    
                }
            }
        }
    }
    
    private static boolean isSymbol(int ch) {
        return (ch == '=' || ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || 
                ch == '>' || ch == '<' || ch == '(' || ch == ')' || ch == '{' || ch == '}' ||
                ch == '|' || ch == '&' || ch == '!' || ch == ',' || ch == ';');
    }
   
    private static void loadCharMap(TreeMap map) {
        map.put("if", "IF");
        map.put("for", "FOR");
        map.put("while", "WHILE");
        map.put("function", "FUNCTION");
        map.put("return", "RETURN");
        map.put("int", "INT");
        map.put("else", "ELSE");
        map.put("do", "DO");
        map.put("break", "BREAK");
        map.put("end", "END");
        map.put("=", "ASSIGN");
        map.put("+", "ADD");
        map.put("-", "SUB");
        map.put("*", "MUL");
        map.put("/", "DIV");
        map.put("%", "MOD");
        map.put(">", "GT");
        map.put("<", "LT");
        map.put(">=", "GE");
        map.put("<=", "LE");
        map.put("++", "INC");
        map.put("(", "LP");
        map.put(")", "RP");
        map.put("{", "LB");
        map.put("}", "RB");
        map.put("|", "OR");
        map.put("&", "AND");
        map.put("==", "EE");
        map.put("!", "NEG");
        map.put(",", "COMMA");
        map.put(";", "SEMI");
    }
}

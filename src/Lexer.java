package lexicalanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

/** 
 * MIT License
 *
 * Copyright (c) 2022 Gabriel Votaw
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

// simple lexical analyzer for a possible compiler
public class Lexer {   

    /**
     * tokenize
     * tokenizes code from a file into tokens and outputs tokens to the terminal
     * @param fileName path to the file containing code to be tokenized
     */
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
    
    /**
     * isSymbol
     * checks if a character is a valid symbol in the language
     * @param ch the character to be checked
     * @return if the character is a symbol
     */
    private static boolean isSymbol(int ch) {
        return (ch == '=' || ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || 
                ch == '>' || ch == '<' || ch == '(' || ch == ')' || ch == '{' || ch == '}' ||
                ch == '|' || ch == '&' || ch == '!' || ch == ',' || ch == ';');
    }
   
    /**
     * loadCharMap
     * loads a TreeMap with valid characters for the language and their relative tokens
     * @param map the TreeMap to be loaded
     */
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

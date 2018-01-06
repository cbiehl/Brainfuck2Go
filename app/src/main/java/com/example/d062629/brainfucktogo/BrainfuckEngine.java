package com.example.d062629.brainfucktogo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public class BrainfuckEngine {

    private String output = "";

    protected byte[] data;
    protected int dataPointer = 0;
    protected int charPointer = 0;
    protected BufferedReader fileReader;
    protected InputStreamReader consoleReader;
    protected OutputStream outWriter;

    protected int lineCount = 0;
    protected int columnCount = 0;

    protected static class Token {
        public final static char NEXT = '>';
        public final static char PREVIOUS = '<';
        public final static char PLUS = '+';
        public final static char MINUS = '-';
        public final static char OUTPUT = '.';
        public final static char INPUT = ',';
        public final static char BRACKET_LEFT = '[';
        public final static char BRACKET_RIGHT = ']';
    }


    public BrainfuckEngine(int cells) {
        this(cells, new PrintStream(System.out), System.in);
    }

    public BrainfuckEngine(int cells, OutputStream out) {
        this(cells, out, System.in);
    }

    public BrainfuckEngine(int cells, OutputStream out, InputStream in) {
        initate(cells);
        outWriter = out;
        consoleReader = new InputStreamReader(in);
    }

    protected void initate(int cells) {
        data = new byte[cells];
        dataPointer = 0;
        charPointer = 0;
    }


    public String interpret(String str) throws Exception {
        for (; charPointer < str.length(); charPointer++)
            interpret(str.charAt(charPointer), str.toCharArray());
        initate(data.length);

        return output;
    }

    protected void interpret(char c, char[] chars) throws Exception {
        switch (c) {
            case Token.NEXT:
                // Increment the data pointer (to point to the next cell to the right).
                if ((dataPointer + 1) > data.length) {
                    throw new Exception("Error on line " + lineCount + ", column " + columnCount + ":"
                            + "data pointer (" + dataPointer
                            + ") on postion " + charPointer + "" + " out of range.");
                }
                dataPointer++;
                break;
            case Token.PREVIOUS:
                // Decrement the data pointer (to point to the next cell to the left).
                if ((dataPointer - 1) < 0) {
                    throw new Exception("Error on line " + lineCount + ", column " + columnCount + ":"
                            + "data pointer (" + dataPointer
                            + ") on postion " + charPointer + " " + "negative.");
                }
                dataPointer--;
                break;
            case Token.PLUS:
                // Increment (increase by one) the byte at the data pointer.
        /*if ((((int) data[dataPointer]) + 1) > Integer.MAX_VALUE) {
            throw new Exception("Error on line " + lineCount + ", column " + columnCount + ":"
                    + "byte value at data pointer ("
                    + dataPointer + ") " + " on postion " + charPointer
                    + " higher than byte max value.");
        }*/
                data[dataPointer]++;
                break;
            case Token.MINUS:
                // Decrement (decrease by one) the byte at the data pointer.
        /*if ((data[dataPointer] - 1) < 0) {
            throw new Exception("Error on line " + lineCount + ", column " + columnCount + ":"
                    + "at data pointer " + dataPointer
                    + " on postion " + charPointer
                    + ": Value can not be lower than zero.");
        }*/
                data[dataPointer]--;
                break;
            case Token.OUTPUT:
                // Output the byte at the current index in a character.
                output += ((char) data[dataPointer]);
                break;
            case Token.INPUT: //TODO: implement continous input from console!
                // Accept one byte of input, storing its value in the byte at the data pointer.
                data[dataPointer] = (byte) consoleReader.read();
                break;
            case Token.BRACKET_LEFT:
                if (data[dataPointer] == 0) {
                    int i = 1;
                    while (i > 0) {
                        char c2 = chars[++charPointer];
                        if (c2 == Token.BRACKET_LEFT)
                            i++;
                        else if (c2 == Token.BRACKET_RIGHT)
                            i--;
                    }
                }
                break;
            case Token.BRACKET_RIGHT:
                int i = 1;
                while (i > 0) {
                    char c2 = chars[--charPointer];
                    if (c2 == Token.BRACKET_LEFT)
                        i--;
                    else if (c2 == Token.BRACKET_RIGHT)
                        i++;
                }
                charPointer--;
                break;
        }
        columnCount++;
    }
}

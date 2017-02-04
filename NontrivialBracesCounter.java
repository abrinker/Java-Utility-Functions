/**
 * Created by Alex on 2/4/17.
 * Larry Patrizio
 * Vivek Sah
 * Alex Rinker
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Nontrivial braces counter
 */
public class NontrivialBracesCounter {

    /**
     * Returns the raw string of the input program File
     * @param filename the File's name
     * @return the raw String of the input file
     */
    public static String readStringFromProgram(String filename) {
        String contents = "";
        try {
            contents = new Scanner(new File(filename)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + filename + "\" was not found. System exiting");
            System.exit(0);
        }
        return contents;
    }

    /**
     * Returns the number of nontrivial left braces in the input program string
     * @param programString the program in raw String form
     * @return the int value of nontrivial left braces
     */
    public static int getNumNontrivialLeftBraces(String programString) {
        int numBraces = 0;
        boolean inLineComment = false;
        boolean inBlockComment = false;
        boolean inString = false;
        boolean inChar = false;
        char[] programChars = programString.toCharArray();
        for (int i=0; i<programChars.length; i++ ) {
            char c = programChars[i];

            //Handle Ignore cases "\"
            if( c == '\\') { i+=1; } //Skip the next char if "\"

            //Exit out of Comments if applicable;
            else if (inBlockComment) {
                if( c == '*' && programChars[i+1] == '/') {
                    inBlockComment = false;
                    i+=1;
                }
            }
            else if(inLineComment) {
                if( c == '\n' ) {
                    inLineComment = false;
                }
            }

            //Checking for Strings and Chars
            else if( c == '\"') {inString = !inString;}
            else if( c == '\'' && !inString) {inChar = !inChar;}
            else if( inString || inChar ) { continue; }

            //Check for Comments
            else if( c == '/') {
                //Line Comment
                if( programChars[i+1] == '/') {
                    inLineComment = true;
                    i+=1;
                }
                //Block Comment
                else if(programChars[i+1] == '*') {
                    inBlockComment = true;
                    i+=1;
                }
            }
            //If we are not in any special case, count the braces
            else if( c == '{') { numBraces++; }
        }
        return numBraces;
    }

    /**
     * The main method of the program
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(getNumNontrivialLeftBraces(readStringFromProgram(
                "/Users/Alex/Documents/CS361/Left Braces/src/NontrivialBracesCounter.java"
        )));
    }
}

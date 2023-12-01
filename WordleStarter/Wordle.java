/*
 * File: Wordle.java
 * -----------------
 * This module is the starter file for the Wordle assignment.
 * BE SURE TO UPDATE THIS COMMENT WHEN YOU COMPLETE THE CODE.
 */

import edu.willamette.cs1.wordle.WordleDictionary;
import edu.willamette.cs1.wordle.WordleGWindow;

public class Wordle {


    public void run() {
        randomWord = WordleDictionary.FIVE_LETTER_WORDS[(int) (Math.random() * (WordleDictionary.FIVE_LETTER_WORDS.length - 1))].toUpperCase();
        gw = new WordleGWindow();
        gw.addEnterListener((s) -> enterAction(s));
    }

/*
 * Called when the user hits the RETURN key or clicks the ENTER button,
 * passing in the string of characters on the current row.
 */

    public void enterAction(String s) {
        if (isValidWord(s)) {

            String hint = getHint(s, randomWord);
            
            int row = gw.getCurrentRow(); 
            
            for (int col = 0; col < WordleGWindow.N_COLS; col++) {
                char hintChar = hint.charAt(col);
                if (hintChar == '*') {
                    gw.setSquareColor(row, col, WordleGWindow.MISSING_COLOR);
                } else if (Character.isUpperCase(hintChar)) {
                    gw.setSquareColor(row, col, WordleGWindow.CORRECT_COLOR);
                } else {
                    gw.setSquareColor(row, col, WordleGWindow.PRESENT_COLOR);
                }
            }

            for (int i = 0; i < s.length(); i++) {
                char hintChar = hint.charAt(i);
                
                if (Character.isUpperCase(hintChar)) {
                    gw.setKeyColor(s.substring(i, i + 1), WordleGWindow.CORRECT_COLOR);
                } 
                if (gw.getKeyColor(s.substring(i, i + 1)) != WordleGWindow.CORRECT_COLOR && Character.isLowerCase(hintChar)) {
                    gw.setKeyColor(s.substring(i, i + 1), WordleGWindow.PRESENT_COLOR);
                }
                if (gw.getKeyColor(s.substring(i, i + 1)) != WordleGWindow.CORRECT_COLOR && gw.getKeyColor(s.substring(i, i + 1)) != WordleGWindow.PRESENT_COLOR && hintChar == '*') {
                    gw.setKeyColor(s.substring(i, i + 1), WordleGWindow.MISSING_COLOR);
                }
            }
        

    
            if (hint.equals(randomWord)) {
                gw.showMessage("Congratulations! You've guessed the word.");
                for (int i=0; i<WordleGWindow.N_COLS; i++) {
                    gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.Red_COLOR);
                    //setSquareColor(int row, int col, Color color);
                    /*
                    public static final Color Red_COLOR = new Color(0xe06666);

                    public static final Color Orange_COLOR = new Color(0xf6b26b);

                    public static final Color Yellow_COLOR = new Color(0xffd966);

                    public static final Color Green_COLOR = new Color(0x93c47d);

                    public static final Color Blue_COLOR = new Color(0x6fa8dc);

                    public static final Color Purple_COLOR = new Color(0x8e7cc3);
                    */
                }
            } else {
                int currentRow = gw.getCurrentRow();
                if (currentRow < WordleGWindow.N_ROWS - 1) {
                    gw.setCurrentRow(currentRow + 1);
                    gw.showMessage("Nice try.");
                } else {
                    gw.showMessage("Game over! The correct word was: " + randomWord);
                }
            }
        } else {
            gw.showMessage("Not in word list");
        }
    }

    private boolean isValidWord(String word) {

        word = word.toLowerCase();

        String[] wordList = WordleDictionary.FIVE_LETTER_WORDS;

        for (int i = 0; i < wordList.length; i++) {
            if (wordList[i].toLowerCase().equals(word)) {
                return true;
            }
        }

        return false;
    }

    public String getHint(String guess, String word) {

        String hint = "*****";
        String check = word;
        char guessedChar;
        char wordChar;

        
        for (int i = 0; i < guess.length(); i++) {
            guessedChar = guess.charAt(i);
            wordChar = word.charAt(i);

            
            if (guessedChar == wordChar) {
                check = check.replaceFirst(String.valueOf(guessedChar), "");

                hint = hint.substring(0,i) + guessedChar + hint.substring(i+1);
            }
        }
        
        for (int j = 0; j < guess.length(); j++) {

            guessedChar = guess.charAt(j);
            wordChar = word.charAt(j);
            


            if (check.contains(String.valueOf(guessedChar))) {

                if (hint.charAt(j) != '*') continue;

                check = check.replaceFirst(String.valueOf(guessedChar), "");
                hint = hint.substring(0,j) + Character.toLowerCase(guessedChar) + hint.substring(j+1);
            }


        }
    
        return hint;

    }

/* Startup code */

    public static void main(String[] args) {
        new Wordle().run();
    }

/* Private instance variables */

    private WordleGWindow gw;
    private String randomWord;

}

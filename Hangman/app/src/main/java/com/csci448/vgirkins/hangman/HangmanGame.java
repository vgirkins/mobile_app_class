package com.csci448.vgirkins.hangman;

/**
 * Created by Tori on 3/3/2018.
 */

public class HangmanGame {
    private String word;
    private int length;
    private String userWord;
    private int guessesLeft;
    private String guessedLetters;
    private boolean gameIsOver;

    public HangmanGame(String word) {
        this.word = word;
        length = word.length();
        userWord = "";
        for (int i = 0; i < length; i++) {
            userWord += "_";
        }
        guessesLeft = 10;
        guessedLetters = "";
        gameIsOver = false;
    }

    public String userDisplayWord() {
        String printThis = "";
        for (int i = 0; i < userWord.length(); i++) {
            printThis += userWord.charAt(i)+ " ";
        }

        return printThis;
    }

    // Value of return indicates whether user has just won
    public boolean checkGuess(String guess) {
        if (guess.length() > 1) {
            guessesLeft--;
            if (guess == word) {
                userWord = word;
            }
        }
        else {
            if (guessedLetters.contains(guess)) {
                // User already guessed this letter
                // TODO
            }
            else {
                char guessChar = guess.charAt(0);   // We already know it's only a single character
                guessedLetters += guess;
                if (word.contains(guess)) {
                    if (!userWord.contains(guess)) {
                        String newUserWord = "";
                        for (int i = 0; i < word.length(); i++) {
                            if (word.charAt(i) == guessChar) {
                                newUserWord += guessChar;
                            }
                            else {
                                newUserWord += userWord.charAt(i);
                            }
                        }
                    }
                }
                else {
                    guessesLeft--;
                }
            }
        }

        if (userWord == word) {
            gameIsOver = true;
            return true;
        }
        else if (guessesLeft == 0) {
            gameIsOver = true;
            return false;
        }

        return false;
    }
}

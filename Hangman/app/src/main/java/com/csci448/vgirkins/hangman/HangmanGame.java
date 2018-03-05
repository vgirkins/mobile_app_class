package com.csci448.vgirkins.hangman;

import android.util.Log;

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

    public HangmanGame(String word, int numGuesses) {
        this.word = word;
        length = word.length();
        userWord = "";
        for (int i = 0; i < length; i++) {
            userWord += "_";
        }
        guessesLeft = numGuesses;
        guessedLetters = "";
        gameIsOver = false;
    }

    // For recreating a game lost after rotation
    public HangmanGame(String word, String userWord, int numGuesses, String guessedLetters, boolean gameIsOver) {
        this.word = word;
        length = word.length();
        this.userWord = userWord;
        this.guessesLeft = numGuesses;
        this.guessedLetters = guessedLetters;
        this.gameIsOver = gameIsOver;
    }

    public String getWord() { return word; }

    public String getGuessedLetters() { return guessedLetters; }

    public String getUserWord() { return userWord; }

    public boolean isGameOver() {
        return gameIsOver;
    }

    public int getNumGuessesLeft() {
        return guessesLeft;
    }

    public String getWordToDisplay() {
        String printThis = "";
        for (int i = 0; i < userWord.length(); i++) {
            printThis += userWord.charAt(i)+ " ";
        }

        return printThis;
    }

    // Value of return indicates whether user has just won. False doesn't necessarily mean they lost.
    // Just means they did not win with this particular guess.
    public boolean checkGuess(String guess) {
        if (guess.length() > 1) {
            guessesLeft--;
            if (guess.equals(word)) {
                userWord = word;
            }
        }
        else {
            if (!guessedLetters.contains(guess)) {  // User hasn't guessed this already
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
                        userWord = newUserWord;
                    }
                }
                else {
                    guessesLeft--;
                }
            }
        }

        if (userWord.equals(word)) {
            gameIsOver = true;
            return true;
        }
        else if (guessesLeft == 0) {
            userWord = word;
            gameIsOver = true;
            return false;
        }

        return false;
    }
}

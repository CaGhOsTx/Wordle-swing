package wordle.model;


import wordle.exceptions.*;
import wordle.view.LetterBoxStyle;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static wordle.view.LetterBoxStyle.*;

public class Model {
    private static Model MODEL;
    int row = 0, column = 0, tries = 6;
    String wordToGuess;
    char[][] letters = new char[6][5];
    Dictionary words = new Dictionary(Path.of("words.txt"));

    private Model() throws WrongPathException {
        setRandomWord();
    }

    public int getTries() {
        return tries;
    }

    public static Model getInstance() throws WrongPathException {
        return MODEL == null ? MODEL = new Model() : MODEL;
    }

    public void updateCurrentLetter(char letter) {
        letters[row][column] = letter;
    }

    public boolean columnWithinBounds() {
        return column < letters[row].length;
    }

    public char getCurrentLetter() {
        return letters[row][column];
    }

    public void setRandomWord() {
        wordToGuess = words.getRandomWord();
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public List<LetterBoxStyle> processWord() throws WordTooShortException, InvalidWordException {
        String word = getCurrentWord();
        if(word.length() != 5) throw new WordTooShortException();
        if(!words.isValidWord(word)) throw new InvalidWordException();
        return getStyles(word);
    }

    private String getCurrentWord() {
        StringBuilder word = new StringBuilder();
        for(int i = 0; i < column; i++)
            word.append(letters[row][i]);
        return word.toString();
    }

    private List<LetterBoxStyle> getStyles(String word) {
        List<LetterBoxStyle> colors = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            if(lettersAreEqualAtIndex(word, i))
                colors.add(GREEN);
            else if (wordToGuessContainsLetter(word, i))
                colors.add(YELLOW);
            else colors.add(GRAY);
        }
        return colors;
    }

    private boolean wordToGuessContainsLetter(String word, int i) {
        return wordToGuess.contains(word.subSequence(i, i + 1));
    }

    private boolean lettersAreEqualAtIndex(String word, int i) {
        return wordToGuess.charAt(i) == word.charAt(i);
    }

    public void incRow() {
        column = 0;
        if(row < letters.length) {
            row++;
        }
    }

    public void incColumn() {
        if(columnWithinBounds()) {
            column++;
        }
    }

    public void decColumn() {
        if(column > 0) {
            --column;
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void analyse(List<LetterBoxStyle> styles) throws WonException, LostException, IncorrectWordException {
        int count = countGreenLetters(styles);
        if(count == 5) throw new WonException();
        else if(--tries == 0) throw new LostException();
        else throw new IncorrectWordException();
    }

    private int countGreenLetters(List<LetterBoxStyle> styles) {
        int count = 0;
        for(LetterBoxStyle style : styles)
            if(style == GREEN) count++;
        return count;
    }

    public void restart() {
        tries = 6;
        setRandomWord();
        row = 0;
        column = 0;
    }
}

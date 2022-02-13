package wordle.model;


import wordle.exceptions.*;
import wordle.view.LetterBoxStyle;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static wordle.view.LetterBoxStyle.*;

/**
 * Model for the Wordle game, access the singleton through {@link Model#getSingleton()} method.
 */
public class Model {
    private static Model MODEL;
    private int row = 0, column = 0, tries = 6;
    private String wordToGuess;
    private final char[][] letters = new char[6][5];
    private final Dictionary files = new Dictionary(Path.of("src/resources/words.txt"), Path.of("src/resources/wordles.txt"));

    private List<String> wordles = files.getWordles();

    private Model() throws IOException {
        setRandomWord();
    }

    public int getTries() {
        return tries;
    }

    /**
     * Singleton factory for {@link Model}.
     * @return the singleton instance of {@link Model}
     * @throws IOException if the words.txt file cannot be accessed for any reason.
     */
    public static Model getSingleton() throws IOException {
        return MODEL == null ? MODEL = new Model() : MODEL;
    }

    public void updateCurrentLetter(char letter) {
        letters[row][column] = letter;
    }

    public boolean columnWithinBounds() {
        return column < letters[row].length;
    }

    public void setRandomWord() {
        wordToGuess = files.getRandomWord();
        System.out.println("Word to guess: " + wordToGuess);
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public List<LetterBoxStyle> processWord() throws WordTooShortException, InvalidWordException {
        String word = getCurrentWord();
        if(word.length() != 5) throw new WordTooShortException();
        if(!files.isValidWord(word)) throw new InvalidWordException();
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
            int finalI = i;
            if(lettersAreEqualAtIndex(word, i)) {
                colors.add(GREEN);
                wordles.removeIf(s -> s.charAt(finalI) != word.charAt(finalI));
            }
            else if (wordToGuessContainsLetter(word, i)) {
                colors.add(YELLOW);
                wordles.removeIf(s -> s.charAt(finalI) == word.charAt(finalI)
                        && !s.contains(word.substring(finalI, finalI + 1)));
            }
            else {
                colors.add(GRAY);
                wordles.removeIf(s -> s.contains(word.substring(finalI, finalI + 1)));
            }
        }
        return colors;
    }

    public String getSuggestion() {
        return wordles.get(ThreadLocalRandom.current().nextInt(0, wordles.size()));
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
        wordles = files.getWordles();
    }
}

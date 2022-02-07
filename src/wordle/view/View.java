package wordle.view;

import wordle.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static wordle.view.LetterBoxStyle.PRESET;
import static wordle.view.Palette.BEIGE;

public final class View extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(View::startOrCrashIfWordsAreMissing);
    }

    private static void startOrCrashIfWordsAreMissing() {
        try {
            Controller.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static View VIEW;

    static Font ROBOTO = new Font("Roboto", Font.PLAIN, 42);
    public final JLabel DESCRIPTOR = new JLabel();
    public final JLabel TRIES = new JLabel();

    public final GridContainer GRID = new GridContainer();

    private View() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        getContentPane().setBackground(BEIGE.get());
        add(GRID);
        add(DESCRIPTOR);
        add(TRIES);
        initialiseLabels();
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(bufferedMinSize());
        setVisible(true);
    }

    private Dimension bufferedMinSize() {
        return new Dimension(getSize().width + 100, getSize().height);
    }

    public static View getInstance() {
        return VIEW == null ? VIEW = new View() : VIEW;
    }

    public void changeStyle(int i, int j, LetterBoxStyle style) {
        style.applyStyle(GRID.letters[i][j]);
    }

    public void delete(int i, int j) {
        write(i, j, "");
    }

    public void write(int i, int j, String letter) {
        GRID.letters[i][j].setText(letter);
    }

    public void highlightRow(int row) {
        GRID.highlightRow(row);
    }

    public void removeHighlight() {
        GRID.removeHighlight();
    }

    public void setTries(int i) {
        TRIES.setText(i + " guesses remaining!");
    }

    public void restart() {
        initialiseLabels();
        GRID.restart();
    }

    private void initialiseLabels() {
        initialiseDescriptor();
        initialiseTries();
    }

    private void initialiseDescriptor() {
        DESCRIPTOR.setFont(ROBOTO.deriveFont(Font.PLAIN, 42));
        DESCRIPTOR.setText("Try to guess the right word!");
        DESCRIPTOR.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void initialiseTries() {
        TRIES.setFont(ROBOTO.deriveFont(Font.PLAIN, 28));
        TRIES.setAlignmentX(Component.CENTER_ALIGNMENT);
        TRIES.setMinimumSize(new Dimension(getWidth(), 250));
        TRIES.setVisible(true);
    }

    private final class GridContainer extends JPanel {

        final JPanel gridPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        final WordBox[] words = new WordBox[6];
        final LetterBox[][] letters = new LetterBox[6][5];

        GridContainer() {
            setMaximumSize(new Dimension(600,750));
            setPreferredSize(new Dimension(600, 750));
            setBackground(BEIGE.get());
            gridPanel.setBackground(BEIGE.get());
            initialiseGrid();
            add(gridPanel);
            setPreferredSize(getPreferredSize());
        }

        void highlightRow (int row) {
            for (int i = 0; i < words.length; i++) {
                if(i == row) words[i].setFocus();
                else words[i].removeFocus();
            }
        }

        void initialiseGrid() {
            addLetterBoxesToWordBoxes();
            addWordPanelsToGrid();
        }

        private void addLetterBoxesToWordBoxes() {
            for(int i = 0; i < letters.length; i++) {
                words[i] = new WordBox();
                for(int j = 0; j < letters[i].length; j++)
                    words[i].add(letters[i][j] = new LetterBox());
            }
            words[0].setFocus();
        }

        void addWordPanelsToGrid() {
            for(JPanel word : words)
                gridPanel.add(word);
        }

         void restart() {
            for (int i = 0; i < letters.length; i++)
                for (int j = 0; j < letters[i].length; j++)
                    changeStyle(i, j, PRESET);
            removeHighlight();
            words[0].setFocus();
        }

        private void removeHighlight() {
            highlightRow(-1);
        }
    }
}

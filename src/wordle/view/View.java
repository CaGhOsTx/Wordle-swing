package wordle.view;

import wordle.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import static wordle.view.LetterBoxStyle.PRESET;
import static wordle.view.Palette.BEIGE;

public final class View extends JFrame {

    public static void main(String[] args) throws IOException, InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(View::startOrCrashIfWordsAreMissing);
        String retry = "r";
        while(retry.equals("r")) {
            Player p = new Player(Controller.getInstance());
            p.guess(100, 1);
            retry = new Scanner(System.in).nextLine();
        }
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
    public final JLabel DESCRIPTOR = new CustomLabel();
    public final JLabel TRIES = new CustomLabel();
    public final JLabel HELPER = new CustomLabel();
    public final JButton ENABLE_HELPER = new HelperButton();


    public final GridContainer GRID = new GridContainer();

    private View() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        getContentPane().setBackground(BEIGE.get());
        add(ENABLE_HELPER);
        add(HELPER);
        add(GRID);
        add(DESCRIPTOR);
        add(TRIES);
        initialiseButton();
        initialiseLabels();
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(bufferedMinSize());
        setVisible(true);
    }

    private Dimension bufferedMinSize() {
        return new Dimension(getSize().width + 100, getSize().height + 75);
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

    public void removeHelper() {
        HELPER.setVisible(false);
    }

    public void setTries(int i) {
        TRIES.setText(i + " guesses remaining!");
    }

    public void restart() {
        initialiseLabels();
        GRID.restart();
    }

    private void initialiseLabels() {
        initialiseHelper();
        initialiseDescriptor();
        initialiseTries();
    }

    private void initialiseButton() {
        ENABLE_HELPER.addActionListener(e -> HELPER.setVisible(!HELPER.isVisible()));
    }


    private void initialiseHelper() {
        HELPER.setText("Try: adieu");
        HELPER.setVisible(true);
    }

    private void initialiseDescriptor() {
        DESCRIPTOR.setText("Try to guess the right word!");
    }

    private void initialiseTries() {
        TRIES.setMinimumSize(new Dimension(getWidth(), 250));
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

package wordle.view;

import wordle.controller.Controller;

import javax.swing.*;
import java.awt.*;

import static wordle.view.LetterBoxStyle.PRESET;
import static wordle.view.Palette.BEIGE;

public final class View extends JFrame {

    private static View VIEW;
    static Font ROBOTO = new Font("Roboto", Font.PLAIN, 42);

    public final JLabel DESCRIPTOR = new JLabel();
    public final JLabel TRIES = new JLabel();
    public final LetterBoxContainer GRID = new LetterBoxContainer();

    private View() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setBackground(BEIGE.get());
        add(GRID);
        add(DESCRIPTOR);
        add(TRIES);
        initialiseLabels();
        pack();
        setVisible(true);
    }

    public static View getInstance() {
        return VIEW == null ? VIEW = new View() : VIEW;
    }

    public void changeStyle(int i, int j, LetterBoxStyle style) {
        style.apply(GRID.letters[i][j]);
    }

    public void delete(int i, int j) {
        write(i, j, "");
    }

    public void write(int i, int j, String letter) {
        GRID.letters[i][j].setText(letter);
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
        DESCRIPTOR.setFont(ROBOTO.deriveFont(Font.PLAIN, 24));
        DESCRIPTOR.setText("Try to guess the right word!");
        DESCRIPTOR.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void initialiseTries() {
        TRIES.setFont(ROBOTO.deriveFont(Font.PLAIN, 24));
        TRIES.setAlignmentX(Component.CENTER_ALIGNMENT);
        TRIES.setVisible(true);
    }

    public void setTries(int i) {
        TRIES.setText(i + " guesses remaining!");
    }

    public static void main(String[] args) {
        Controller.getInstance();
    }

    private final class LetterBoxContainer extends JPanel {

        final JPanel gridPanel = new JPanel(new GridLayout(6,5, 10, 10));
        final LetterBox[][] letters = new LetterBox[6][5];

        LetterBoxContainer() {
            initialiseLetterLabels();
            add(gridPanel);
            setPreferredSize(getPreferredSize());
        }

        void initialiseLetterLabels() {
            for(int i = 0; i < letters.length; i++) {
                for(int j = 0; j < letters[i].length; j++) {
                    letters[i][j] = new LetterBox();
                }
            }
            addLetterBoxesToPanel();
        }

        void addLetterBoxesToPanel() {
            for(JLabel[] word : letters) {
                for(JLabel letter : word)
                    gridPanel.add(letter);
            }
        }

         void restart() {
            for (int i = 0; i < letters.length; i++)
                for (int j = 0; j < letters[i].length; j++)
                    changeStyle(i, j, PRESET);
        }
    }
}

package wordle.view;

import javax.swing.*;
import java.awt.*;

import static wordle.view.Palette.BROWN;
import static wordle.view.Palette.WHITE;
import static wordle.view.View.ROBOTO;

final class LetterBox extends JLabel {
    LetterBox() {
        setOpaque(true);
        setPreferredSize(new Dimension(100, 100));
        setHorizontalAlignment(CENTER);
        setBackground(BROWN.get());
        setForeground(WHITE.get());
        setFont(ROBOTO);
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    @Override
    public void setText(String letter) {
        super.setText(letter.toUpperCase());
    }
}

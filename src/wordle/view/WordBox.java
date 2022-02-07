package wordle.view;

import javax.swing.*;

import java.awt.*;

import static wordle.view.Palette.*;

public class WordBox extends JPanel {

    public WordBox() {
        super(new GridLayout(1, 5, 5, 5));
        setBackground(BEIGE.get());
    }

    public void setFocus() {
        setBorder(BorderFactory.createDashedBorder(BLACK.get(), 5, 4, 1, true));
    }

    public void removeFocus() {
        setBorder(null);
    }
}

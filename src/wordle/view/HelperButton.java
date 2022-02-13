package wordle.view;

import javax.swing.*;
import java.awt.*;

import static wordle.view.Palette.*;
import static wordle.view.View.ROBOTO;

public class HelperButton extends JButton {

    public HelperButton() {
        setFocusable(false);
        setBorder(BorderFactory.createDashedBorder(RED.get(), 4, 8, 2, false));
        setFont(ROBOTO.deriveFont(Font.PLAIN, 42));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(BEIGE.get());
        setText("helper");
    }
}

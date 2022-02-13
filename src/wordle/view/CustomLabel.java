package wordle.view;

import javax.swing.*;
import java.awt.*;

import static wordle.view.View.ROBOTO;

public class CustomLabel extends JLabel {

    public CustomLabel() {
        setFont(ROBOTO.deriveFont(Font.PLAIN, 42));
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}

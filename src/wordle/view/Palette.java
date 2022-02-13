package wordle.view;

import java.awt.*;

public enum Palette {
    BEIGE(212, 185, 150),
    BROWN(160, 120, 85),
    WHITE(252, 246, 245),
    GREEN(76, 187, 23),
    YELLOW(253, 219, 39),
    GRAY(105, 105, 105),
    RED(255,0,0),
    BLACK(36,31,28);

    private final Color hex;

    Palette(int r, int g, int b) {
        hex = new Color(r, g, b);
    }

    public Color get() {
        return hex;
    }
}

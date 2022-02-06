package wordle.view;

import java.awt.*;

public enum Palette {
    BEIGE(212, 185, 150),
    BROWN(160, 120, 85),
    WHITE(252, 246, 245),
    GREEN(151, 188, 98),
    YELLOW(253, 219, 39),
    GRAY(28, 28, 27);

    Color hex;

    Palette(int r, int g, int b) {
        hex = new Color(r, g, b);
    }

    public Color get() {
        return hex;
    }
}

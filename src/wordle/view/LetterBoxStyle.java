package wordle.view;

import static wordle.view.Palette.BROWN;

public enum LetterBoxStyle {

    PRESET {
        @Override
        void apply(LetterBox label) {
            label.setText("");
            label.setBackground(BROWN.get());
            label.repaint();
        }
    },
    GREEN {
        @Override
        void apply(LetterBox label) {
            label.setBackground(Palette.GREEN.get());
            label.repaint();
        }
    },
    YELLOW {
        @Override
        void apply(LetterBox label) {
            label.setBackground(Palette.YELLOW.get());
            label.repaint();
        }
    },
    GRAY {
        @Override
        void apply(LetterBox label) {
            label.setBackground(Palette.GRAY.get());
            label.repaint();
        }
    };

    abstract void apply(LetterBox label);
}

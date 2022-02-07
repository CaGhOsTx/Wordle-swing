package wordle.view;

import static wordle.view.Palette.BROWN;

public enum LetterBoxStyle {

    PRESET {
        @Override
        void applyStyle(LetterBox label) {
            label.setText("");
            label.setBackground(BROWN.get());
            label.repaint();
        }
    },
    GREEN {
        @Override
        void applyStyle(LetterBox label) {
            label.setBackground(Palette.GREEN.get());
            label.repaint();
        }
    },
    YELLOW {
        @Override
        void applyStyle(LetterBox label) {
            label.setBackground(Palette.YELLOW.get());
            label.repaint();
        }
    },
    GRAY {
        @Override
        void applyStyle(LetterBox label) {
            label.setBackground(Palette.GRAY.get());
            label.repaint();
        }
    },
    BLUE {
        @Override
        void applyStyle(LetterBox label) {
            label.setBackground(Palette.YELLOW.get());
        }
    };

    abstract void applyStyle(LetterBox label);
}

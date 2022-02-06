package wordle.controller;

import wordle.model.Model;
import wordle.exceptions.*;
import wordle.view.LetterBoxStyle;

import java.awt.event.KeyEvent;
import java.util.List;

enum ControllerAction implements WordleAction {
    LETTER {
        char letter;
        @Override
        public boolean isTriggeredBy(KeyEvent e) {
            letter = e.getKeyChar();
            return Character.isLetter(e.getKeyChar());
        }

        @Override
        public void execute(Controller controller) {
            System.out.println("pressed " + letter);
            Model m = controller.MODEL;
            if(m.columnWithinBounds()) {
                m.updateCurrentLetter(letter);
                controller.VIEW.write(m.getRow(), m.getColumn(), String.valueOf(letter));
                m.incColumn();
            }
        }


    },
    ENTER {
        @Override
        public boolean isTriggeredBy(KeyEvent e) {
            return e.getKeyCode() == KeyEvent.VK_ENTER;
        }

        @Override
        public void execute(Controller controller) {
            System.out.println("pressed enter");
            try {
                List<LetterBoxStyle> styles = controller.MODEL.processWord();
                applyStyleToLetterContainers(controller, styles);
                controller.MODEL.analyse(styles);
            } catch (WordleException e) {
                e.resolve(controller);
            }
        }

        private void applyStyleToLetterContainers(Controller controller, List<LetterBoxStyle> styles) throws WordTooShortException, InvalidWordException {
            int row = controller.MODEL.getRow(), column = 0;
            for(LetterBoxStyle style : styles)
                controller.VIEW.changeStyle(row, column++, style);
        }
    },
    BACKSPACE {
        @Override
        public boolean isTriggeredBy(KeyEvent e) {
            return e.getKeyCode() == KeyEvent.VK_BACK_SPACE;
        }

        @Override
        public void execute(Controller controller) {
            System.out.println("pressed backspace");
            Model m = controller.MODEL;
            m.decColumn();
            m.updateCurrentLetter('\0');
            controller.VIEW.delete(m.getRow(), m.getColumn());
        }
    };

}

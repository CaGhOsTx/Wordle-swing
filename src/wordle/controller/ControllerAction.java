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
                analyzeWordAndApplyStyle(controller);
            } catch (WordleException exception) {
                exception.resolve(controller);
            }
        }

        private void analyzeWordAndApplyStyle(Controller controller) throws WordTooShortException, InvalidWordException, WonException, LostException, IncorrectWordException {
            List<LetterBoxStyle> styles = controller.MODEL.processWord();
            updateView(controller, styles);
            controller.MODEL.analyse(styles);
        }

        private void updateView(Controller controller, List<LetterBoxStyle> styles) {
            int row = controller.MODEL.getRow(), column = 0;
            for(LetterBoxStyle style : styles)
                controller.VIEW.changeStyle(row, column++, style);
            controller.VIEW.HELPER.setText("Try: " + controller.MODEL.getSuggestion());
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

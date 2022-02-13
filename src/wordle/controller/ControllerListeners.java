package wordle.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Function;

enum ControllerListeners {
    MAIN(controller -> new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            for(ControllerAction action : ControllerAction.values()) {
                if(action.isTriggeredBy(e))
                    action.execute(controller);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }),
    RESTART(controller -> new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                controller.VIEW.restart();
                controller.MODEL.restart();
                controller.updateTries();
                controller.VIEW.removeKeyListener(this);
                controller.VIEW.addKeyListener(controller.MAIN);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    });
    private final Function<Controller, KeyListener> LISTENER;

    ControllerListeners(Function<Controller, KeyListener> LISTENER) {
        this.LISTENER = LISTENER;
    }

    KeyListener linkTo(Controller controller) {
        return LISTENER.apply(controller);
    }
}

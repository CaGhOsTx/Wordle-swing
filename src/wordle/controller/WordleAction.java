package wordle.controller;

import java.awt.event.KeyEvent;

public interface WordleAction {
     default boolean isTriggeredBy(KeyEvent e) {
        throw new UnsupportedOperationException();
     }
     void execute(Controller controller);
}

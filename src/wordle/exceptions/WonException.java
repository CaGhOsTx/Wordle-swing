package wordle.exceptions;

import wordle.controller.Controller;

public class WonException extends WordleException {
    @Override
    public void resolve(Controller controller) {
        controller.VIEW.DESCRIPTOR.setText("You Won!");
        controller.VIEW.TRIES.setText("Press space to play again!");
        controller.VIEW.removeKeyListener(controller.MAIN);
        controller.VIEW.addKeyListener(controller.RESTART);
        controller.VIEW.removeHighlight();
    }
}

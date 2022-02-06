package wordle.exceptions;

import wordle.controller.Controller;

public class WonException extends WordleException {
    @Override
    public void resolve(Controller controller) {
        controller.VIEW.DESCRIPTOR.setText("<html> <p> You Won! <br/> Press space to play again</p> </html>");
        controller.VIEW.TRIES.setVisible(false);
        controller.VIEW.removeKeyListener(controller.MAIN);
        controller.VIEW.addKeyListener(controller.RESTART);
    }
}

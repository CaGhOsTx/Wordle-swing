package wordle.exceptions;

import wordle.controller.Controller;

public class LostException extends WordleException {
    @Override
    public void resolve(Controller controller) {
        controller.VIEW.DESCRIPTOR.setText("" +
                "<html><p>You lost! The word was <br/>"
                + controller.MODEL.getWordToGuess().toUpperCase()
                + "<br/>Press space to play again</p></html");
        controller.VIEW.TRIES.setVisible(false);
        controller.VIEW.removeKeyListener(controller.MAIN);
        controller.VIEW.addKeyListener(controller.RESTART);
    }
}

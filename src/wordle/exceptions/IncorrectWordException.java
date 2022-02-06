package wordle.exceptions;

import wordle.controller.Controller;

public class IncorrectWordException extends WordleException{
    @Override
    public void resolve(Controller controller) {
        controller.VIEW.DESCRIPTOR.setText("Incorrect word!");
        controller.updateTries();
        controller.MODEL.incRow();
    }
}

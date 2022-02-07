package wordle.exceptions;

import wordle.controller.Controller;

public class WordTooShortException extends WordleException {
    @Override
    public void resolve(Controller controller) {
        controller.VIEW.DESCRIPTOR.setText("Word is too short!");
        controller.VIEW.highlightRow(controller.MODEL.getRow());
    }
}

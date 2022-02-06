package wordle.exceptions;

import wordle.controller.Controller;

public class InvalidWordException extends WordleException {
    @Override
    public void resolve(Controller controller) {
        controller.VIEW.DESCRIPTOR.setText("Word doesn't exist!");
    }
}

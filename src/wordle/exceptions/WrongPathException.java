package wordle.exceptions;

import wordle.controller.Controller;

import java.awt.*;

public class WrongPathException extends WordleException {

    @Override
    public void resolve(Controller controller) {
        controller.VIEW.DESCRIPTOR.setText("Words file could not be found!");
        controller.VIEW.DESCRIPTOR.setForeground(Color.RED);
        controller.VIEW.TRIES.setVisible(false);
    }
}

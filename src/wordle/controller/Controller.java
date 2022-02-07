package wordle.controller;

import wordle.model.Model;
import wordle.view.View;

import java.awt.event.KeyListener;
import java.io.IOException;

public final class Controller {
    private static Controller CONTROLLER;
    public  View VIEW = View.getInstance();
    public  Model MODEL;
    public final KeyListener MAIN = ControllerListeners.MAIN.linkTo(this);
    public final KeyListener RESTART = ControllerListeners.RESTART.linkTo(this);

    private Controller() throws IOException {
        MODEL = Model.getSingleton();
        VIEW.addKeyListener(MAIN);
    }

    public static Controller getInstance() throws IOException {
        CONTROLLER = CONTROLLER == null ? new Controller() : CONTROLLER;
        CONTROLLER.updateTries();
        return CONTROLLER;
    }

    public void updateTries() {
        CONTROLLER.VIEW.setTries(CONTROLLER.MODEL.getTries());
    }
}
